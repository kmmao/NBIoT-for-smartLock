package com.routon.plcloud.common.model;

import java.util.Date;

/**
 * 
 * @author wangxiwei
 *
 */
public class Software implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7638456967451593826L;

	private long id;
	
	private String softwareName;
	
	private String softwareVersion;
	
	private String erpCode;
	
	private String softwareCopyrightNumber;
	
	private Date uploadTime;
	
	private String size;
	
	private String uploadUser;
	
	private String customerName;
	
	private String correspondProject;
	
	private String correspondTerminalType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}


	public String getSoftwareCopyrightNumber() {
		return softwareCopyrightNumber;
	}

	public void setSoftwareCopyrightNumber(String softwareCopyrightNumber) {
		this.softwareCopyrightNumber = softwareCopyrightNumber;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCorrespondProject() {
		return correspondProject;
	}

	public void setCorrespondProject(String correspondProject) {
		this.correspondProject = correspondProject;
	}

	public String getCorrespondTerminalType() {
		return correspondTerminalType;
	}

	public void setCorrespondTerminalType(String correspondTerminalType) {
		this.correspondTerminalType = correspondTerminalType;
	}
	
	
}
