package com.routon.plcloud.common.model;

import java.util.Date;

public class CommandLog implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4699731153487975463L;
	
	private long id;
	
	private String deviceid;
	
	private long commandtype;
	
	private String commandstate;
	
	private Date commandtime;
	
	private String commandid;
	
	private String commandstr;
	
	private long face_id;
	
	private String sn;
	
	private int report_white;
	
	private int report_manager;
	
	private int report_sendwhite;
	
	public int getReport_sendwhite() {
		return report_sendwhite;
	}

	public void setReport_sendwhite(int report_sendwhite) {
		this.report_sendwhite = report_sendwhite;
	}

	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public long getCommandtype() {
		return commandtype;
	}

	public void setCommandtype(long commandtype) {
		this.commandtype = commandtype;
	}

	public String getCommandstate() {
		return commandstate;
	}

	public void setCommandstate(String commandstate) {
		this.commandstate = commandstate;
	}

	public Date getCommandtime() {
		return commandtime;
	}

	public void setCommandtime(Date commandtime) {
		this.commandtime = commandtime;
	}

	public String getCommandid() {
		return commandid;
	}

	public void setCommandid(String commandid) {
		this.commandid = commandid;
	}

	public String getCommandstr() {
		return commandstr;
	}

	public void setCommandstr(String commandstr) {
		this.commandstr = commandstr;
	}

	public long getFace_id() {
		return face_id;
	}

	public void setFace_id(long face_id) {
		this.face_id = face_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getReport_white() {
		return report_white;
	}

	public void setReport_white(int report_white) {
		this.report_white = report_white;
	}

	public int getReport_manager() {
		return report_manager;
	}

	public void setReport_manager(int report_manager) {
		this.report_manager = report_manager;
	}

	
	
}
