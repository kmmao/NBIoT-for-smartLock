package com.routon.plcloud.common.model;

import java.util.Date;

/**
 * 
 * @author huanggang
 *
 */
public class OperaterHardware implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9144929345671107439L;

	private long id;

	private long operatingsystemID;

	private long hardwarestationID;

	private Date modifyTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOperatingsystemID() {
		return operatingsystemID;
	}

	public void setOperatingsystemID(long operatingsystemID) {
		this.operatingsystemID = operatingsystemID;
	}

	public long getHardwarestationID() {
		return hardwarestationID;
	}

	public void setHardwarestationID(long hardwarestationID) {
		this.hardwarestationID = hardwarestationID;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
