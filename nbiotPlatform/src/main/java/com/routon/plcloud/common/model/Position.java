package com.routon.plcloud.common.model;

public class Position {
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String deviceid;
	private String addr;
	private Integer roomnum;
	private String name;
	private String phone;
	private String jurisdiction;
	private String jurisdictioncon;
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Integer getRoomnum() {
		return roomnum;
	}
	public void setRoomnum(Integer roomnum) {
		this.roomnum = roomnum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}
	public String getJurisdictioncon() {
		return jurisdictioncon;
	}
	public void setJurisdictioncon(String jurisdictioncon) {
		this.jurisdictioncon = jurisdictioncon;
	}
	
	
}
