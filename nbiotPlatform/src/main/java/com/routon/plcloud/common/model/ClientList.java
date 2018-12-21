package com.routon.plcloud.common.model;

import java.io.Serializable;

public class ClientList  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8555349399114039381L;
	
	private long id;
	
	private String province;
	
	private String city;
	
	private String district;
	
	private String companyname;
	
	private String projectname;
	
	private String orderid;
	
	private String client_code;
	
	private String client_name;
	
	private String contact;
	
	private String telno;
	
	private String address;
		
	private String remark;
	
	private String term_code;
	
	private String term_sn;
	
//	“term_licence”:”xxxx”,
//	“request_type”:0,	
	
	private String term_licence;
	
	private String request_type;
	
	private String expire;
	 
	private String time;

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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}


	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTerm_code() {
		return term_code;
	}

	public String getExpire() {
		return expire;
	}

	public String getTime() {
		return time;
	}

	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}

	public String getTerm_sn() {
		return term_sn;
	}

	public void setTerm_sn(String term_sn) {
		this.term_sn = term_sn;
	}

	public String getTerm_licence() {
		return term_licence;
	}

	public String getRequest_type() {
		return request_type;
	}

	public void setTerm_licence(String term_licence) {
		this.term_licence = term_licence;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	

}
