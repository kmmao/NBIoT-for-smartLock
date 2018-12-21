package com.routon.plcloud.common.model;

import java.io.Serializable;
import java.util.Date;

public class SoftwareHardwarestation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3624722434265255766L;
	
	private long id;
	
	private long softwareid;
	
	private long hardwarestationid;
	
	private Date modifytime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSoftwareid() {
		return softwareid;
	}

	public void setSoftwareid(long softwareid) {
		this.softwareid = softwareid;
	}

	public long getHardwarestationid() {
		return hardwarestationid;
	}

	public void setHardwarestationid(long hardwarestationid) {
		this.hardwarestationid = hardwarestationid;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	
	
}
