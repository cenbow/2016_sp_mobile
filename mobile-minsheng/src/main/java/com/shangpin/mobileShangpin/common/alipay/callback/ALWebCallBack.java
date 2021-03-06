/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2008 All Rights Reserved.
 */
package com.shangpin.mobileShangpin.common.alipay.callback;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shangpin.mobileShangpin.common.alipay.base.PartnerConfig;
import com.shangpin.mobileShangpin.common.alipay.security.RSASignature;
import com.shangpin.mobileShangpin.common.alipay.util.ParameterUtil;
import com.shangpin.mobileShangpin.common.util.FileUtil;

/**
 * 
 * 支付成功后支付宝同步调用url
 * 
 * @author 3y
 * @version $Id: CallBack.java, v 0.1 2011-8-25 下午05:16:26 3y Exp $
 */
public class ALWebCallBack extends HttpServlet {
	private static final long serialVersionUID = -2234271646410251381L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// 获得通知签名
		String sign = request.getParameter("sign");
		String result = request.getParameter("result");
		String requestToken = request.getParameter("request_token");
		String outTradeNo = request.getParameter("out_trade_no");
		String tradeNo = request.getParameter("trade_no");
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("result", result);
		resMap.put("request_token", requestToken);
		resMap.put("out_trade_no", outTradeNo);
		resMap.put("trade_no", tradeNo);
		String verifyData = ParameterUtil.getSignData(resMap);
		boolean verified = false;
		// 得到应用服务器地址
		// String path = request.getContextPath();
		// String basePath = request.getScheme() + "://" + request.getLocalAddr() + ":" + request.getServerPort() + path + "/";
		// 使用支付宝公钥验签名
		try {
			verified = RSASignature.doCheck(verifyData, sign, PartnerConfig.RSA_ALIPAY_PUBLIC, "");
			if (!verified || !result.equals("success")) {
				// TODO 修改为主站提供的支付失败跳转地址
				response.sendRedirect("http://www20.shangpin.com/cart/comfirmpayfailure/" + outTradeNo);
				System.out.println("Illegal sign!!");
				// 记录访问日志
				FileUtil.addLog(request, "/alipay/alwebcallBack","success", "0");
			} else {
				// TODO 修改为主站提供的支付成功跳转地址
				response.sendRedirect("http://www20.shangpin.com/cart/confirmordermsg/" + outTradeNo);
				System.out.println("RSA signature verification success!!");
				// 记录访问日志
				FileUtil.addLog(request, "/alipay/alwebcallBack","success", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
