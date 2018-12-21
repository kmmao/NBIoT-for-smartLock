/*package com.routon.plcloud.test;

import java.util.List;

import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

*//**
 * 
 * @author wangxiwei
 *
 *//*
public class MyBusinessProcessTest extends ActivitiTestCase{

	  
	  
	@Override
	public String getConfigurationResource() {
		return "activiti/activiti-config-test.xml";
	}

	
	@Test
	@Deployment
	public void testAssigned() {
	    //runtimeService.startProcessInstanceByKey("process_pool3");

	    //List<Task> task = taskService.createTaskQuery().list();
	    List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned("1").list();

	    for(Task tasksingle : taskQuery){
		    System.out.println("Task ID["+ tasksingle.getId()+"],Task node name:" + tasksingle.getName());
		    //assertEquals("立项", tasksingle.getName());

		    //taskService.complete(tasksingle.getId());
		    //assertEquals(1, runtimeService.createProcessInstanceQuery().count());
	    }
	  }
	
	@Test
	@Deployment
	public void testSimpleProcess() {
	    runtimeService.startProcessInstanceByKey("process_pool3");

	    List<Task> task = taskService.createTaskQuery().list();
	    //List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned("1").list();

	    for(Task tasksingle : task){
		    System.out.println("Task ID["+ tasksingle.getId()+"],Task node name:" + tasksingle.getName());
		    //assertEquals("立项", tasksingle.getName());

		    //taskService.complete(tasksingle.getId());
		    List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned("1").list();
		    System.out.println("1");
		    //assertEquals(1, runtimeService.createProcessInstanceQuery().count());
	    }
	  }
}
*/