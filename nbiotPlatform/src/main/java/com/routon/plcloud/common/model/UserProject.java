package com.routon.plcloud.common.model;

public class UserProject implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3093062236991348671L;

	private long id;
	
	private long userId;

	private long projectId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	
	
}
