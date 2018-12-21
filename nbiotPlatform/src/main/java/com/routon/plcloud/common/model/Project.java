package com.routon.plcloud.common.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author wangzhuo
 *
 */
public class Project implements java.io.Serializable{

	
	private static final long serialVersionUID = -6209579899576001913L;
	
	private Long id;
	
	@NotEmpty(message="请填写项目名称")
	private String projectname;
	
	private String projectregname;
	
	private Date modifytime;
	
	private Date createtime;
	
	@NotEmpty(message="请填写项目地址")
	private String projectadd;
	
	@NotEmpty(message="请填写客户项目负责人员姓名")
	private String cusprojectname;
	
	@NotEmpty(message="请填写客户项目负责人员电话")
	private String cusprojectphone;
	
	@NotEmpty(message="请填写拟需求总数量")
	private String demandquantity;
	
	private String ordersale;

	private String restnum;

	@NotEmpty(message="请填写需求类型")
	private String requirementtype;
	
	@NotEmpty(message="请填写所属行业")
	private String industry;
	
	private String sdkname;
	
	private String softwareerpnumber;
	
	private String softwaretypeversion;
	
	@NotEmpty(message="请填写授权秘钥")
	private String licensekey;
	
//	@NotEmpty(message="请填写有效期限月数")
//	private String  month;
//
//	private String endtime;
//	
//	@NotEmpty(message="请填写生效日期")
//	private String starttime;
//	
//	private String tacitstarttime;
	
	private int status;
	
	private String address;
	
	private int companyid;
	
	private String authentication;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getProjectregname() {
		return projectregname;
	}

	public void setProjectregname(String projectregname) {
		this.projectregname = projectregname;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getProjectadd() {
		return projectadd;
	}

	public void setProjectadd(String projectadd) {
		this.projectadd = projectadd;
	}

	public String getCusprojectname() {
		return cusprojectname;
	}

	public void setCusprojectname(String cusprojectname) {
		this.cusprojectname = cusprojectname;
	}

	public String getCusprojectphone() {
		return cusprojectphone;
	}

	public void setCusprojectphone(String cusprojectphone) {
		this.cusprojectphone = cusprojectphone;
	}

	public String getDemandquantity() {
		return demandquantity;
	}

	public void setDemandquantity(String demandquantity) {
		this.demandquantity = demandquantity;
	}
	
	public String getOrdersale() {
		return ordersale;
	}

	public void setOrdersale(String ordersale) {
		this.ordersale = ordersale;
	}
	
	public String getRestnum() {
		return restnum;
	}

	public void setRestnum(String restnum) {
		this.restnum = restnum;
	}

	public String getRequirementtype() {
		return requirementtype;
	}

	public void setRequirementtype(String requirementtype) {
		this.requirementtype = requirementtype;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSdkname() {
		return sdkname;
	}

	public void setSdkname(String sdkname) {
		this.sdkname = sdkname;
	}

	public String getSoftwareerpnumber() {
		return softwareerpnumber;
	}

	public String getSoftwaretypeversion() {
		return softwaretypeversion;
	}

	public void setSoftwareerpnumber(String softwareerpnumber) {
		this.softwareerpnumber = softwareerpnumber;
	}

	public void setSoftwaretypeversion(String softwaretypeversion) {
		this.softwaretypeversion = softwaretypeversion;
	}

	public String getLicensekey() {
		return licensekey;
	}

	public void setLicensekey(String licensekey) {
		this.licensekey = licensekey;
	}
	
//	public String getMonth() {
//		return month;
//	}
//
//	public void setMonth(String month) {
//		this.month = month;
//	}
//
//
//	public String getEndtime() {
//		return endtime;
//	}
//
//	public void setEndtime(String endtime) {
//		this.endtime = endtime;
//	}
//
//	public String getStarttime() {
//		return starttime;
//	}
//
//	public void setStarttime(String starttime) {
//		this.starttime = starttime;
//	}
//
//	public String getTacitstarttime() {
//		return tacitstarttime;
//	}
//
//	public void setTacitstarttime(String tacitstarttime) {
//		this.tacitstarttime = tacitstarttime;
//	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	
	
}
