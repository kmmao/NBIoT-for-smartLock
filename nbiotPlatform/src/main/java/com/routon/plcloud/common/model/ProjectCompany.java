package com.routon.plcloud.common.model;

import java.util.Date;

public class ProjectCompany implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5920405331947735045L;
	
	private long id;
	
	private long companyid;
	
	private long projectid;
	
	private Date modifytime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompanyid() {
		return companyid;
	}

	public void setCompanyid(long companyid) {
		this.companyid = companyid;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}



}
