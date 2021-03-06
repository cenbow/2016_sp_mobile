package com.shangpin.mobileShangpin.platform.vo;

import java.util.ArrayList;
import java.util.List;

public class LogisticeVO {

	private String orderId;
	private String ticketNo;
	private String statusDesc;
	private String express;
	private String date;
	private String desc;
	private String address;
	private String totalcount;
	
	private List<MerchandiseVO> merchandiseVO=new ArrayList<MerchandiseVO>();
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}
	public List<MerchandiseVO> getMerchandiseVO() {
		return merchandiseVO;
	}
	public void setMerchandiseVO(List<MerchandiseVO> merchandiseVO) {
		this.merchandiseVO = merchandiseVO;
	}
	
	
}
