package com.routon.plcloud.admin.activiti.service;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author wangxiwei
 *
 */
@Service
public class ActivitiCommonServiceImpl implements ActivitiCommonService {

	@Autowired
	private TaskService taskService;
	
	@Override
	public String getCurrentTaskName(String orderNum) {
		String result = null;
		String taskQuerySql = "SELECT res.* FROM act_ru_task res LEFT JOIN act_ru_variable v on res.proc_inst_id_ = v.proc_inst_id_ "
				+ "WHERE v.name_ = 'orderNum' AND v.text_ = '" + orderNum+ "';";
		List<Task> taskList = taskService.createNativeTaskQuery().sql(taskQuerySql).list();
		if(taskList.size() == 0){
			result =  "current Order no task";
		} else{
			result =  taskList.get(0).getName();
		}
		return result;
	}
}
