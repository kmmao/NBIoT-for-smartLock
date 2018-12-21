package com.routon.plcloud.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author wangxiwei
 *
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6346393264957757737L;
	
	private Long id;
	
	/**
	 * 订单号
	 */
	private String ordernum;
	
	/**
	 * 软件ERP编码
	 */
	private String softwareerpnumber;
	
	/**
	 * 产品名称及版本号
	 */
	private String softwaretypeversion;
	
	/**
	 * 需求数量
	 */
//	private int demandquantity;
	private String demandquantity;
	
	//子订单需求数量
	private String SuborderRenewnum;
	/**
	 * 已授权数量
	 */
	private String authorizedNum;
	/**
	 * 授权类型
	 */
	private String licensetype;
	
	/**
	 * 到期提醒天数
	 */
//	private int reminderdays;
	private String reminderdays;
	
	/**
	 * 绑定方式
	 */
	private String bindingmode;
	
	/**
	 * 有效期月份
	 */
//	private int month;
	private String month;
	
	/**
	 * 有效期起始时间
	 */
	private String starttime;
	
	/**
	 * 有效期结束时间
	 */
	private String endtime;
	
	/**
	 * 有效期默认时间
	 */
	private String tacitstarttime;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 修改时间
	 */
	private Date moditytime;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	private String createtimeStr;

//	private int projectid;
	
	private int status;
	
	/**
	 * 判断是否审核
	 */
	private int verify;
	
	//订单续订次数
	private String renewCount;
	
	private String orderActiveStatus;
	
	private String companyid;
	
	private String costomerBusinessMan;
	
	private String projectid;
	
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public String getSoftwareerpnumber() {
		return softwareerpnumber;
	}

	public void setSoftwareerpnumber(String softwareerpnumber) {
		this.softwareerpnumber = softwareerpnumber;
	}

	public String getSoftwaretypeversion() {
		return softwaretypeversion;
	}

	public void setSoftwaretypeversion(String softwaretypeversion) {
		this.softwaretypeversion = softwaretypeversion;
	}

//	public int getDemandquantity() {
//		return demandquantity;
//	}
//
//	public void setDemandquantity(int demandquantity) {
//		this.demandquantity = demandquantity;
//	}

	public String getLicensetype() {
		return licensetype;
	}

	public void setLicensetype(String licensetype) {
		this.licensetype = licensetype;
	}

//	public int getReminderdays() {
//		return reminderdays;
//	}
//
//	public void setReminderdays(int reminderdays) {
//		this.reminderdays = reminderdays;
//	}

	public String getBindingmode() {
		return bindingmode;
	}

	public void setBindingmode(String bindingmode) {
		this.bindingmode = bindingmode;
	}

//	public int getMonth() {
//		return month;
//	}
//
//	public void setMonth(int month) {
//		this.month = month;
//	}

	public String getStarttime() {
		return starttime;
	}

	public String getDemandquantity() {
		return demandquantity;
	}

	public void setDemandquantity(String demandquantity) {
		this.demandquantity = demandquantity;
	}

	public String getReminderdays() {
		return reminderdays;
	}

	public void setReminderdays(String reminderdays) {
		this.reminderdays = reminderdays;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTacitstarttime() {
		return tacitstarttime;
	}

	public void setTacitstarttime(String tacitstarttime) {
		this.tacitstarttime = tacitstarttime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getModitytime() {
		return moditytime;
	}

	public void setModitytime(Date moditytime) {
		this.moditytime = moditytime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAuthorizedNum() {
		return authorizedNum;
	}

	public void setAuthorizedNum(String authorizedNum) {
		this.authorizedNum = authorizedNum;
	}

//	public int getOrderActiveStatus() {
//		return orderActiveStatus;
//	}
//
//	public void setOrderActiveStatus(int orderActiveStatus) {
//		this.orderActiveStatus = orderActiveStatus;
//	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderActiveStatus() {
		return orderActiveStatus;
	}

	public void setOrderActiveStatus(String orderActiveStatus) {
		this.orderActiveStatus = orderActiveStatus;
	}

	public int getVerify() {
		return verify;
	}

	public void setVerify(int verify) {
		this.verify = verify;
	}

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

	public String getCostomerBusinessMan() {
		return costomerBusinessMan;
	}

	public void setCostomerBusinessMan(String costomerBusinessMan) {
		this.costomerBusinessMan = costomerBusinessMan;
	}

	public String getRenewCount() {
		return renewCount;
	}

	public void setRenewCount(String renewCount) {
		this.renewCount = renewCount;
	}

	public String getSuborderRenewnum() {
		return SuborderRenewnum;
	}

	public void setSuborderRenewnum(String suborderRenewnum) {
		SuborderRenewnum = suborderRenewnum;
	}

//	public int getProjectid() {
//		return projectid;
//	}
//
//	public void setProjectid(int projectid) {
//		this.projectid = projectid;
//	}

	
}
