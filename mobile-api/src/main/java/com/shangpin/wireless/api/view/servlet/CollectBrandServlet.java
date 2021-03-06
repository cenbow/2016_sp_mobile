package com.shangpin.wireless.api.view.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.shangpin.base.service.CommonService;
import com.shangpin.wireless.api.util.ChannelNoUtil;
import com.shangpin.wireless.api.util.FileUtil;
import com.shangpin.wireless.api.util.StringUtil;
import com.shangpin.wireless.api.util.WebUtil;
/**
 * 收藏品牌接口
 * 
 * @author liling 
 * @date 2014-8-27
 */
public class CollectBrandServlet  extends BaseServlet {



	private static final long serialVersionUID = -5285008258606747906L;
	protected final Log log = LogFactory.getLog(CollectBrandServlet.class);
	@Autowired 
	CommonService commonService;
    @Override
    public void init() throws ServletException {
		commonService = (CommonService) getBean(CommonService.class);
	}
	

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String productNo = request.getHeader("p");;
        final String imei = request.getHeader("imei");;
        final String userId = request.getParameter("userId");
        final String brandId = request.getParameter("brandId");
        String channelNo = ChannelNoUtil.getChannelNo(request);//获取渠道号
    
        if (StringUtil.isNotEmpty(imei, productNo, userId,brandId)) {
            try {
                String data =commonService.collectBrandObj(userId, brandId);
                JSONObject obj = JSONObject.fromObject(data);
                response.getWriter().print(obj.toString());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("ColletBrandServlet：" + e);
                try {
                    WebUtil.sendApiException(response);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            // 记录访问日志
            FileUtil.addLog(request, "colletBrand", channelNo,
            		"imei", imei, 
            		"productNo", productNo, 
            		"userId", userId, 
            		"brandId",brandId);
        } else {
            try {
                WebUtil.sendErrorInvalidParams(response);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
