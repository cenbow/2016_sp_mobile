package com.shangpin.wireless.view.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.shangpin.wireless.base.action.BaseAction;
import com.shangpin.wireless.domain.Channel;
import com.shangpin.wireless.domain.ErrorLog;
import com.shangpin.wireless.domain.Product;
import com.shangpin.wireless.domain.ReturnObject;
import com.shangpin.wireless.util.JsonUtil;
import com.shangpin.wireless.util.SqlHelper;

/**
 * 错误日志管理Action
 * 
 * @Author  zhouyu
 * @CreatDate  2012-07-31
 */
@Controller
@Scope("prototype")
public class ErrorLogAction extends BaseAction<ErrorLog> {
	private static final long serialVersionUID = 1L;
	protected final Log log = LogFactory.getLog(ErrorLogAction.class);
	private int page = 1;
	private int rows;
	private String ids;
	private File upload;
	private String uploadFileName;
	private String startDate;
	private String endDate;
	/**
	 * 跳转至错误日志列表
	 * 
	 * @Author zhouyu
	 * @CreatDate 2012-07-31
	 * @param
	 * @Return String
	 */
	public String list() {
		try {
			List<Product> productList = productService.findAll();
			ActionContext.getContext().put("productList", productList);
			List<Channel> channelList = channelService.findAll();
			ActionContext.getContext().put("channelList", channelList);
		} catch (Exception e) {
			log.error("ErrorLogAction-list()" + e);
		}
		return "list";
	}

	/**
	 * 错误日志列表
	 * 
	 * @Author  zhouyu
	 * @CreatDate  2012-07-31
	 * @param
	 * @Return String
	 */
	public String query() {
		try {
			// 1，构建查询条件
			SqlHelper sqlQueryListHelper = new SqlHelper("errorLog", "e");
			SqlHelper sqlQueryCountHelper = new SqlHelper("errorLog");
			sqlQueryListHelper.addColumn("e.id");
			sqlQueryListHelper.addColumn("e.platform");// 运行平台
			sqlQueryListHelper.addColumn("e.productNum");// 产品编号
			sqlQueryListHelper.addColumn("e.productVersion");// 产品版本
			sqlQueryListHelper.addColumn("e.channelNum");// 渠道编号
			sqlQueryListHelper.addColumn("e.shortmsg");// 简短信息
			sqlQueryListHelper.addColumn("e.longmsg");// 详细信息
			sqlQueryListHelper.addColumn("e.phoneType");// 手机型号
			sqlQueryListHelper.addColumn("e.imei");// IMEI号
			sqlQueryListHelper.addColumn("e.systemVersoin");// 系统版本
			sqlQueryListHelper.addColumn("e.apn");// 接入点
			sqlQueryListHelper.addColumn("e.createTime");// 创建时间
			sqlQueryCountHelper.addColumn("count(*)");
			sqlQueryListHelper.addOrderByProperty(true, "e.createTime", false);
			// ------------------组织查询条件 end-----------------
			if (model.getId() != null) {
				sqlQueryListHelper.addWhereCondition("e.id=" + model.getId());
				sqlQueryCountHelper.addWhereCondition("id=" + model.getId());
			}
			if (model.getPlatform() != null && !"".equals(model.getPlatform().trim())) {
				sqlQueryListHelper.addWhereCondition("e.platform='" + model.getPlatform() + "'");
				sqlQueryCountHelper.addWhereCondition("platform='" + model.getPlatform() + "'");
			}
			if (model.getProductNum() != null) {
				sqlQueryListHelper.addWhereCondition("e.productNum=" + model.getProductNum());
				sqlQueryCountHelper.addWhereCondition("productNum=" + model.getProductNum());
			}
			if (model.getProductVersion() != null && !"".equals(model.getProductVersion().trim())) {
				sqlQueryListHelper.addWhereCondition("e.productVersion='" + model.getProductVersion() + "'");
				sqlQueryCountHelper.addWhereCondition("productVersion='" + model.getProductVersion() + "'");
			}
			if (model.getChannelNum() != null) {
				sqlQueryListHelper.addWhereCondition("e.channelNum=" + model.getChannelNum());
				sqlQueryCountHelper.addWhereCondition("channelNum=" + model.getChannelNum());
			}
			if (startDate != null && !"".equals(startDate.trim())) {
				sqlQueryListHelper.addWhereCondition("e.createTime>='" + startDate + " 00:00:00" + "'");
				sqlQueryCountHelper.addWhereCondition("createTime>='" + startDate + " 00:00:00" + "'");
			}
			if (endDate != null && !"".equals(endDate.trim())) {
				sqlQueryListHelper.addWhereCondition("e.createTime<='" + endDate + " 23:59:59" + "'");
				sqlQueryCountHelper.addWhereCondition("createTime<='" + endDate + " 23:59:59" + "'");
			}
			// ------------------查询条件 end-----------------
			// 2，查询并准备分页信息
			List<HashMap> queryList = errorLogService.executeSqlToMap(sqlQueryListHelper.getQueryListSql(), page, rows);
			List<HashMap> recordList = new ArrayList<HashMap>(queryList.size());
			for (int i=0;i<queryList.size();i++) {
				HashMap record = new HashMap();
				HashMap log = queryList.get(i);
				record.put("id", log.get("id"));
				record.put("summary", new StringBuilder()
									.append("产品号：").append(log.get("productNum")).append("<br/>")
									.append("渠道号：").append(log.get("channelNum")).append("<br/>")
									.append("版本号：").append(log.get("productVersion")).append("<br/>")
									.append("<br/>")
									.append("手机型号：").append(log.get("phoneType")).append("<br/>")
									.append("系统：").append(log.get("platform")).append(log.get("systemVersoin")).append("<br/>")
									.append("接入点：").append(log.get("apn")).append("<br/>")
									.append("IMEI：").append(log.get("imei")).append("<br/>")
									.append("地区：").append(log.get("city")).append("<br/>")
									.toString());
				record.put("shortmsg", log.get("shortmsg"));
				record.put("longmsg", log.get("longmsg"));
				record.put("createTime", log.get("createTime"));
				recordList.add(record);
			}
			Integer count = errorLogService.executeSqlCount(sqlQueryCountHelper.getQueryListSql());
			ReturnObject returnObject = new ReturnObject();
			returnObject.setTotal(count);
			returnObject.setRows(recordList);
			ServletActionContext.getResponse().getWriter().print(JsonUtil.convertToJSonStr(returnObject));

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ErrorLogAction-list() " + e);
		}
		return null;
	}

	/**
	 * 错误日志详细信息
	 * 
	 * @Author  zhouyu
	 * @CreatDate  2012-07-31
	 * @param  
	 * @Return 
	 */
	public String detail() {
		try {
			// 1，从数据库中获取原对象
			ErrorLog errorLog = errorLogService.getById(model.getId());
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[] { "id", "operateTime", "reserve0", "reserve1", "reserve2", "reserve3", "reserve4", "reserve5" });
			ServletActionContext.getResponse().getWriter().print(JSONObject.fromObject(errorLog, jsonConfig).toString());
		} catch (Exception e) {
			log.error("ErrorLogAction-detail " + e);
		}
		return null;
	}

	/**
	 * 添加错误日志
	 * 
	 * @Author  zhouyu
	 * @CreatDate  2012-07-31
	 * @param  
	 * @Return 
	 */
	public String add() {
		try {
			
			for (int i = 0; i < 50; i++) {
				ErrorLog errorLog = new ErrorLog();
				errorLog.setPlatform("IOS");
				errorLog.setSystemVersoin("5.1");
				errorLog.setPhoneType("4S");
				errorLog.setImei("ms-2s-c" + i);
				errorLog.setApn("WIFI");
				errorLog.setProductNum(Long.parseLong(i + ""));
				errorLog.setChannelNum(Long.parseLong(i + 1 + ""));
				errorLog.setProductVersion("1.1." + i);
				errorLog.setShortmsg("简短错误" + i);
				errorLog.setLongmsg("详细错误" + i);
				errorLog.setCreateTime(new Date());// 操作时间
				errorLogService.save(errorLog);
				Thread.sleep(4000);
			}
		} catch (Exception e) {
			log.error("ErrorLogAction-add()" + e);
		}
		return null;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
