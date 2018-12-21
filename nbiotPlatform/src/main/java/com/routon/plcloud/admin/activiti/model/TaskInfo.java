package com.routon.plcloud.admin.activiti.model;

/**
 * 
 * @author wangxiwei
 *
 */
public class TaskInfo {
	
	private String projectName;
	
	private String authorNums;
	
	private String remarks;
	
	private String processInstanceId;
	
	private String processDefinitionId;
	
	private String name;

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

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
