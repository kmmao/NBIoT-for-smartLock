package com.routon.plcloud.common.model;

import java.io.Serializable;
import java.util.Date;

public class SoftwareHardware implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9004333752310976435L;
	
	private long id;
	
	private long softwareid;
	
	private long hardwareid;
	
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

	public long getHardwareid() {
		return hardwareid;
	}

	public void setHardwareid(long hardwareid) {
		this.hardwareid = hardwareid;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	
	
}
