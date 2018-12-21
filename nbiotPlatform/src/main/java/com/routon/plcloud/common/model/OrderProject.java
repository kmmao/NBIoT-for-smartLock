package com.routon.plcloud.common.model;

import java.util.Date;

/**
 * 
 * @author huanggang
 *
 */
public class OrderProject implements java.io.Serializable{
	

	private static final long serialVersionUID = 2241725373876740336L;

	private long id;
	
	private long projectid;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	private long orderid;
	
	private Date modifytime;

}
