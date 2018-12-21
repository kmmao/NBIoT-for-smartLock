package com.routon.plcloud.common.model;

import java.util.Date;

/**
 * 
 * @author huanggang
 * 订单与子订单关联model
 */
public class OrderSuborder implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8374815292190667441L;

	private long id;
	
	//订单id
	private long orderid;
	
	//子订单id
	private long suborderid;
	
	private Date modifytime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public long getSuborderid() {
		return suborderid;
	}

	public void setSuborderid(long suborderid) {
		this.suborderid = suborderid;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
}
