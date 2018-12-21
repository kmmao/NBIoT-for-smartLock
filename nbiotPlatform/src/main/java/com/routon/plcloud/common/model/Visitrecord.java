package com.routon.plcloud.common.model;

import java.util.Date;

public class Visitrecord implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3067002432190762582L;

	private long face_id;
	
	private String name;
	
	private long roomnum;
	
	private Date visit_time;
		
	private String operationtype;
	
	private String doorstate;
	
	private String lockstate;
	
	private String validtime;

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

	public long getRoomnum() {
		return roomnum;
	}

	public void setRoomnum(long roomnum) {
		this.roomnum = roomnum;
	}

	public Date getVisit_time() {
		return visit_time;
	}

	public void setVisit_time(Date visit_time) {
		this.visit_time = visit_time;
	}

	public String getOperationtype() {
		return operationtype;
	}

	public void setOperationtype(String operationtype) {
		this.operationtype = operationtype;
	}

	public String getDoorstate() {
		return doorstate;
	}

	public void setDoorstate(String doorstate) {
		this.doorstate = doorstate;
	}

	public String getLockstate() {
		return lockstate;
	}

	public void setLockstate(String lockstate) {
		this.lockstate = lockstate;
	}

	public String getValidtime() {
		return validtime;
	}

	public void setValidtime(String validtime) {
		this.validtime = validtime;
	}
}
