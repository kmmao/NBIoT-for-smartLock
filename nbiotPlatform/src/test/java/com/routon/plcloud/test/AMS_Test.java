/*package com.routon.plcloud.test;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AMS_Test {
	
	@Resource(name = "runtimeService")
	public RuntimeService runtimeService;
	
	@Resource(name = "taskService")
	public TaskService taskService;
	
	@Test
	public void startProcess() {
	    runtimeService.startProcessInstanceByKey("process_pool3");

	    Task task = taskService.createTaskQuery().singleResult();

	    System.out.println("Task ID["+ task.getId()+"],Task node name:" + task.getName());
	    
	  }
	
	
	@Test
	public void completeTask(){
		Task task = taskService.createTaskQuery().singleResult();
		
		taskService.complete(task.getId());

	    System.out.println("Task ID["+ task.getId()+"],Task node name:" + task.getName());
	}
}
*/