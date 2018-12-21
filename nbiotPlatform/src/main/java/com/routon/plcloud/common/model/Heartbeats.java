package com.routon.plcloud.common.model;

import java.util.Date;

public class Heartbeats {
	
	private long id;

    private String device_id;

    private String whitefacelist;

    private String manangerfacelist;

    private int isreportwhite;

    private int isreportmanager;

    private String sn;

    private Date update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getWhitefacelist() {
		return whitefacelist;
	}

	public void setWhitefacelist(String whitefacelist) {
		this.whitefacelist = whitefacelist;
	}

	public String getManangerfacelist() {
		return manangerfacelist;
	}

	public void setManangerfacelist(String manangerfacelist) {
		this.manangerfacelist = manangerfacelist;
	}

	public int getIsreportwhite() {
		return isreportwhite;
	}

	public void setIsreportwhite(int isreportwhite) {
		this.isreportwhite = isreportwhite;
	}

	public int getIsreportmanager() {
		return isreportmanager;
	}

	public void setIsreportmanager(int isreportmanager) {
		this.isreportmanager = isreportmanager;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
    
}
