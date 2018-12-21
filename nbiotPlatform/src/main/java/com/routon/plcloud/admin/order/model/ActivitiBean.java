package com.routon.plcloud.admin.order.model;

/**
 * 
 * @author wangxiwei
 *
 */
public class ActivitiBean {

	private String processDefinitionId;
	
	private String projectName;
	
	private String authorNums;
	
	private String remarks;

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAuthorNums() {
		return authorNums;
	}

	public void setAuthorNums(String authorNums) {
		this.authorNums = authorNums;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
