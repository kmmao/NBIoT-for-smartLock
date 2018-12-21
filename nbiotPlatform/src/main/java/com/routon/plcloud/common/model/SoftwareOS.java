package com.routon.plcloud.common.model;

import java.io.Serializable;
import java.util.Date;

public class SoftwareOS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 969216272144419372L;
	
	private long id;
	
	private long softwareid;
	
	private long osid;
	
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

	public long getOsid() {
		return osid;
	}

	public void setOsid(long osid) {
		this.osid = osid;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	
	
}
