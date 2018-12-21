package com.routon.plcloud.admin.activiti.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.routon.plcloud.admin.activiti.model.ProcessConst;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.UserProject;

/**
 * 
 * @author wangxiwei
 *
 */

public class ActivitiInfoQuery {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	static Logger logger = Logger.getLogger(ActivitiInfoQuery.class);
	
	public void QueryActivitiInfo(Model model, String userId, int page, int startIndex, int pageSize,
			int claimed_page, int claimed_startIndex, int claimed_pageSize,
			int allTask_page, int allTask_startIndex, int allTask_pageSize, String processTag){
		PagingBean<Map<String,String>> taskQueryPageBean = new PagingBean<Map<String,String>>();
		PagingBean<Map<String,String>> claimedPageBean = new PagingBean<Map<String,String>>();
		PagingBean<Map<String,String>> allTaskPageBean = new PagingBean<Map<String,String>>();
		
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().active().orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.list();
        
/*      long taskQuerySize = taskService.createTaskQuery().taskCandidateUser(userId.toString()).count();
		List<Task> taskQuery = taskService.createTaskQuery().taskCandidateUser(userId.toString())
				.orderByTaskCreateTime().desc().listPage(startIndex, pageSize);*/
		
/*		long claimedSize = taskService.createTaskQuery().taskAssignee(userId.toString()).count();
		List<Task> claimedTaskList = taskService.createTaskQuery().taskAssignee(userId.toString())
				.orderByTaskCreateTime().desc().listPage(claimed_startIndex, claimed_pageSize);*/
		
/*		long allTaskSize = taskService.createTaskQuery().active().count();
		List<Task> allTask = taskService.createTaskQuery().active().
				orderByTaskCreateTime().desc().listPage(allTask_startIndex, allTask_pageSize);*/
        String condition = null;
		if(processTag.equals("Project")){
			condition = "AND res.proc_def_id_ = '" + ProcessConst.proc_def_id_NewProject + "' ";
		} else if(processTag.equals("Order")){
			condition = "AND (res.proc_def_id_ = '" + ProcessConst.proc_def_id_NewAndRenewOrder + "' OR res.proc_def_id_ = '" + ProcessConst.proc_def_id_OpenAndCloseOrder + "') ";
		} else{
			condition = "";
		}
		String taskQuerySql = "SELECT res.* FROM act_ru_task res "
				+ "LEFT JOIN act_ru_identitylink I ON I.task_id_ = res.id_ "
				+ "LEFT JOIN act_ru_variable v on res.proc_inst_id_ = v.proc_inst_id_ "
				+ "WHERE res.assignee_ IS NULL " + condition 
				+ "AND v.name_ = 'projectId' "
				+ "AND CAST (v.text_ AS INTEGER) IN "
				+ "(select projectid from userproject where userid='"+ userId +"') "
				+ "AND I.group_id_ IN "
				+ "(SELECT G .group_id_ FROM act_id_membership G WHERE G .user_id_ = '"+ userId +"') order by res.create_time_ desc";
		NativeTaskQuery nativeTaskQuery_taskQuery = taskService.createNativeTaskQuery().sql(taskQuerySql);
		List<Task> taskQuery = nativeTaskQuery_taskQuery.listPage(startIndex, pageSize);
		long taskQuerySize = nativeTaskQuery_taskQuery.sql("select count(*) from ("+ taskQuerySql +") as foo").count();
		
		String claimedTaskListSql = "SELECT res.* FROM act_ru_task res "
				+ "LEFT JOIN act_ru_variable v on res.proc_inst_id_ = v.proc_inst_id_ "
				+ "WHERE res.assignee_ = '"+ userId +"' " + condition
				+ "AND v.name_ = 'projectId' "
				+ "AND CAST (v.text_ AS INTEGER) IN "
				+ "(select projectid from userproject where userid='" + userId + "') order by res.create_time_ desc";
		NativeTaskQuery nativeTaskQuery_claimed = taskService.createNativeTaskQuery().sql(claimedTaskListSql);
		List<Task> claimedTaskList = nativeTaskQuery_claimed.listPage(claimed_startIndex, claimed_pageSize);
		long claimedSize = nativeTaskQuery_claimed.sql("select count(*) from ("+ claimedTaskListSql +") as foo").count();
		
		String allTaskSql = "SELECT res.* FROM act_ru_task res "
				+ "LEFT JOIN act_ru_variable v on res.proc_inst_id_ = v.proc_inst_id_ "
				+ "WHERE v.name_ = 'projectId' " + condition
				+ "AND CAST (v.text_ AS INTEGER) IN "
				+ "(select projectid from userproject where userid='" + userId + "') order by res.create_time_ desc";
		NativeTaskQuery nativeTaskQuery_allTask = taskService.createNativeTaskQuery().sql(allTaskSql);
		List<Task> allTask = nativeTaskQuery_allTask.listPage(allTask_startIndex, allTask_pageSize);
		long allTaskSize = nativeTaskQuery_allTask.sql("select count(*) from ("+ allTaskSql +") as foo").count();
		
		//List<UserProject> userProjectList = userProjectMapper.selectBySql("select * from userproject where userid=" + userId);
		
		List<Map<String,String>> formList = generateFormData(taskQuery, list);		
		List<Map<String,String>> claimedTaskMap = generateFormData(claimedTaskList, list);
		List<Map<String,String>> allList = generateFormData(allTask, list);
		
		taskQueryPageBean.setDatas(formList);
		taskQueryPageBean.setTotalCount((int)taskQuerySize);
		taskQueryPageBean.setStart(startIndex);
		taskQueryPageBean.setLimit(pageSize);
		
		claimedPageBean.setDatas(claimedTaskMap);
		claimedPageBean.setTotalCount((int)claimedSize);
		claimedPageBean.setStart(claimed_startIndex);
		claimedPageBean.setLimit(claimed_pageSize);
		
		allTaskPageBean.setDatas(allList);
		allTaskPageBean.setTotalCount((int)allTaskSize);
		allTaskPageBean.setStart(allTask_startIndex);
		allTaskPageBean.setLimit(allTask_pageSize);
		
		int maxpage = (int) Math.ceil(taskQuerySize
				/ (double) pageSize);
		if (taskQuerySize == 0) {
			maxpage = 0;
		}
		
		int claimed_maxpage = (int) Math.ceil(claimedSize
				/ (double) claimed_pageSize);
		if (claimedSize == 0) {
			claimed_maxpage = 0;
		}
		
		int allTask_maxpage = (int) Math.ceil(allTaskSize
				/ (double) allTask_pageSize);
		if (allTaskSize == 0) {
			allTask_pageSize = 0;
		}
		
		model.addAttribute("task_no_received_page", page);
		model.addAttribute("task_no_received_maxpage", maxpage);
		model.addAttribute("taskQuerySize", taskQuerySize);
		
		model.addAttribute("claimed_maxpage", claimed_maxpage);
		model.addAttribute("claimedSize", claimedSize);
		model.addAttribute("claimed_page", claimed_page);
		
		model.addAttribute("pageList_process", list);
		model.addAttribute("List", taskQueryPageBean);
		model.addAttribute("claimedTaskList", claimedPageBean);
		
		model.addAttribute("allTaskSize", allTaskSize);
		model.addAttribute("allTask_maxpage", allTask_maxpage);
		model.addAttribute("allTask_page", allTask_page);
		model.addAttribute("AllList", allTaskPageBean);
	}
	
	public List<Map<String, String>> generateFormData(List<Task> taskQuery,  List<ProcessDefinition> list){
		List<Map<String, String>> formList = new ArrayList<Map<String, String>>();
		//List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for(int i = 0; i < taskQuery.size(); i++){
			Map<String, String> taskQuryMap = new HashMap<String, String>();
			for(int j = 0; j < list.size(); j++){
				if(taskQuery.get(i).getProcessDefinitionId().equals(list.get(j).getId())){
					taskQuryMap.put("processName" , list.get(j).getName());
					break;
				}
			}
			List<HistoricDetail> historicList = historyService.createHistoricDetailQuery().
					processInstanceId(taskQuery.get(i).getProcessInstanceId()).list();
			taskQuryMap.put("id" , taskQuery.get(i).getId());
			taskQuryMap.put("processInstanceId" , taskQuery.get(i).getProcessInstanceId());
			taskQuryMap.put("processDefinitionId" , taskQuery.get(i).getProcessDefinitionId());
			taskQuryMap.put("name" , taskQuery.get(i).getName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			taskQuryMap.put("createTime" , sdf.format(taskQuery.get(i).getCreateTime()));
			for(HistoricDetail historicDetail : historicList){
				if(taskQuery.get(i).getProcessInstanceId().equals(historicDetail.getProcessInstanceId())){
					if(historicDetail instanceof HistoricFormProperty){
						HistoricFormProperty field = (HistoricFormProperty) historicDetail;
						taskQuryMap.put(field.getPropertyId() , field.getPropertyValue());
					}
				}
				//historicVarList.addAll(list);
			}
			formList.add(taskQuryMap);
		}
		//resultList = filterData(formList, userProjectList);
		return formList;
	}
	
	//根据用户所在项目过滤数据
	public List<Map<String, String>> filterData(List<Map<String, String>> formList, List<UserProject> userProjectList){
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for(int i = 0; i < formList.size(); i++){
			String currentProjectId = formList.get(i).get("projectId");
			for(int j = 0; j < userProjectList.size(); j++){
				String projectid = String.valueOf(userProjectList.get(j).getProjectId());
				if(currentProjectId.equals(projectid)){
					resultList.add(formList.get(i));
					break;
				}
			}
		}
		return resultList;
	}
}
