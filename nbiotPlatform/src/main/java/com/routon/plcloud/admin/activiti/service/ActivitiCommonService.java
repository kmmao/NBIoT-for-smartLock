package com.routon.plcloud.admin.activiti.service;

/**
 * 
 * @author wangxiwei
 *
 */
public interface ActivitiCommonService {
	
	/**
	 * ACTIVITI获取当前任务名称
	 * @return taskName:任务名
	 */
	public String getCurrentTaskName(String orderNum);
	
}
