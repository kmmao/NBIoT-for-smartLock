package com.routon.plcloud.common.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class Company implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -395674530174091572L;
	
	private Long id;
	
	//@NotEmpty(message="请填写公司名称")
	@NotEmpty(message="请填写公司名称")
	private String companyname;
	
	private String companyregname;
	
	private String companyrep;
	
	private String englishname;
	
	private String namespell;
	
//	private String identifynum;
//	
//	private String regnum;
	
	@NotEmpty(message="请填写组织机构代码")
	private String organizationcode;
	
	private String trade;
	
	private String status;
	
	@NotEmpty(message="请填写联系人姓名")
	private String contactname;
	
	@NotEmpty(message="请填写联系人电话")
	private String contactphone;
	
	private String contactfixedphone;
	
	private String email;
	
	@NotEmpty(message="请填写销售人员姓名")
	private String salename;
	
	private String realename;
	
	private String country;
	
	private String province;
	
	private String city;
	
	private String area;
	
	private String areaname;
	
	private String address;
	
	private String zipcode;
	
	private Date moditytime;
	
	private Date createtime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyregname() {
		return companyregname;
	}

	public void setCompanyregname(String companyregname) {
		this.companyregname = companyregname;
	}

	public String getCompanyrep() {
		return companyrep;
	}

	public void setCompanyrep(String companyrep) {
		this.companyrep = companyrep;
	}

	public String getEnglishname() {
		return englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getNamespell() {
		return namespell;
	}

	public void setNamespell(String namespell) {
		this.namespell = namespell;
	}

//	public String getIdentifynum() {
//		return identifynum;
//	}
//
//	public void setIdentifynum(String identifynum) {
//		this.identifynum = identifynum;
//	}
//
//	public String getRegnum() {
//		return regnum;
//	}
//
//	public void setRegnum(String regnum) {
//		this.regnum = regnum;
//	}

	public String getOrganizationcode() {
		return organizationcode;
	}

	public void setOrganizationcode(String organizationcode) {
		this.organizationcode = organizationcode;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getContactphone() {
		return contactphone;
	}

	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}

	public String getContactfixedphone() {
		return contactfixedphone;
	}

	public void setContactfixedphone(String contactfixedphone) {
		this.contactfixedphone = contactfixedphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalename() {
		return salename;
	}

	public void setSalename(String salename) {
		this.salename = salename;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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

	public String getRealename() {
		return realename;
	}

	public void setRealename(String realename) {
		this.realename = realename;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	
     
}
