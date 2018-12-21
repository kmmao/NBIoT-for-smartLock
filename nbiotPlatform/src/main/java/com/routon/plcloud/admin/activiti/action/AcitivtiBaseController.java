package com.routon.plcloud.admin.activiti.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.activiti.model.ProcessConst;
import com.routon.plcloud.admin.order.model.ActivitiBean;
import com.routon.plcloud.admin.order.service.OrderService;
import com.routon.plcloud.admin.order.service.ProjectService;
import com.routon.plcloud.admin.order.service.ProjectServiceImpl;
import com.routon.plcloud.admin.privilege.service.RoleService;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.User;
import com.routon.plcloud.common.persistence.ProjectMapper;
import com.routon.plcloud.common.persistence.UserMapper;
import com.routon.plcloud.common.utils.JsonMsgBean;

import net.sf.json.JSONObject;


/**
 * 
 * @author wangxiwei
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class AcitivtiBaseController {
	
	 @Autowired
	 private RepositoryService repositoryService;
	 
	 @Autowired
	 private RuntimeService runtimeService;
	 
	 @Autowired
	 private TaskService taskService;
	 
	 @Autowired
	 private RoleService roleService;
	 
	 @Autowired
	 private FormService formService;
	 
	 @Autowired
	 private IdentityService identityService;
	 
	 @Autowired
	 private HistoryService historyService;
	 
	 @Autowired
	 private UserMapper userMapper;
	 
	 @Autowired
	 private OrderService orderService;
	 
	 @Autowired
	 private ProjectMapper projectMapper;
	 
	 @Resource(name = "projectServiceImpl")
	 private ProjectService projectService;
	
	@RequestMapping(value ="/activiti/show")
	public String list(Model model){
		
		// 读取所有流程
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().active().orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.list();
        model.addAttribute("pageList", list);
		return "activiti/ProcessShow";
		
	}
	
	@RequestMapping(value ="/activiti/getProcessImageAndXml")
	public void showProcess(HttpServletResponse response, String processDefinitionId, String resourceType) throws IOException{
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
	}
	
	
	// 20180312需求更改：新建订单后自动启动流程，此处废弃，见订单保存。
	@RequestMapping(value ="/activiti/startPrpces")
	public String startProcess(Model model, @ModelAttribute("userProfile") UserProfile user, ActivitiBean activitiBean){
		identityService.setAuthenticatedUserId(user.getCurrentUserId().toString());
		String startUser = user.getCurrentUserRealName();
		JsonMsgBean jsonMsg = null;
		//ProcessInstance instance = runtimeService.startProcessInstanceById(activitiBean.getProcessDefinitionId());
		Map<String,String> variables = new HashMap<String,String>();
		variables.put("projectName", activitiBean.getProjectName());
		variables.put("authorNums", activitiBean.getAuthorNums());
		variables.put("remarks", activitiBean.getRemarks());
		variables.put("applyUser", startUser);
		ProcessInstance instance = formService.submitStartFormData(activitiBean.getProcessDefinitionId(), variables);
		if(instance == null){
			jsonMsg = new JsonMsgBean(0, CVal.Fail, "该流程不存在!");
			model.addAttribute("jsonMsg", jsonMsg);
			return "common/jsonTextHtml";
		}
		jsonMsg = new JsonMsgBean(0, CVal.Success, "启动成功!");
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}
	
	@RequestMapping(value ="/activiti/showRuntimeProcess")
	public String queryRuntimeProcess(Model model, @ModelAttribute("userProfile") UserProfile user){
		ProcessInstanceQuery querylist = runtimeService.createProcessInstanceQuery()
				.orderByProcessInstanceId().desc().active();
		List<ProcessInstance> list = querylist.list();
		
		Map<String,String> map = roleService.queryRoleByUserId(user.getCurrentUserId().toString());
		
		String username = user.getCurrentUserRealName();
		
		List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();
		
/*		List<Task> taskQuery = new ArrayList<Task>();
		for (String key : map.keySet()) {
			   System.out.println("key= "+ key + " and value= " + map.get(key));
			  }*/
/*		String roleid = map.get("id").toString();
		
		for(int i = 0; i < rolelist.size(); i++){
			//HashMap<Object,String> map = rolelist.get(i);
			//String roleid = map.get("id").toString();
			List<Task> partlist = taskService.createTaskQuery().taskCandidateOrAssigned("1").list();
			taskQuery.addAll(partlist);
		}*/
		model.addAttribute("pageList", taskQuery);
		return "activiti/RuntimeProcess";
	}
	
	@RequestMapping(value ="/activiti/finisheTask")
	@ResponseBody public String finisheTask(String taskId, @ModelAttribute("userProfile") UserProfile user){
		
/*		List<Task> task = taskService.createTaskQuery().list();
		for(Task result : task){
			System.out.println(result.getId());
		}*/
		
		taskService.claim(taskId, user.getCurrentUserId().toString());
		
		taskService.complete(taskId);
		return "success";
	}
	
	@RequestMapping(value ="/activiti/finisheTaskCondition")
	@ResponseBody
	public String finisheTaskCondition(String taskId, String checkInfo, String checkLable,
			Boolean pass, String instanceId, @ModelAttribute("userProfile") UserProfile user, String processId){
		Map<String, String> variables =  new HashMap<String,String>();
		String verifyStatus = null;
		String verifyInfo = null;
		if(checkLable.equals("销售助理订单审核")){
			verifyStatus = "ownSaleVeify";
			verifyInfo = "SalesAssistantverifyInfo";
		} else if(checkLable.equals("客户订单审核")){
			verifyStatus = "customerBussinessVeify";
			verifyInfo = "customerVerifyInfo";
		} else if(checkLable.equals("财务审核")){
			verifyStatus = "FinanceVeify";
			verifyInfo = "FinanceInfo";
		} else if(checkLable.equals("我方项目管理人员审核")){
			verifyStatus = "ProjectManagerVeify";
			verifyInfo = "ProjectManagerInfo";
		} else if(checkLable.equals("客户商务人员审核")){
			verifyStatus = "customerBusinessmanVeify";
			verifyInfo = "customerBusinessmanInfo";
		} else{
			return "error";
		}
		Map<String, String> taskQuryMap = new HashMap<String, String>();
		List<HistoricDetail> historicList = historyService.createHistoricDetailQuery().processInstanceId(instanceId).list();
		for(HistoricDetail historicDetail : historicList){
				if(historicDetail instanceof HistoricFormProperty){
					HistoricFormProperty field = (HistoricFormProperty) historicDetail;
					taskQuryMap.put(field.getPropertyId() , field.getPropertyValue());
				}
		}
		String OrderStatus = taskQuryMap.get("OrderStatus");
		String companyName = taskQuryMap.get("companyName");
		String companyId = taskQuryMap.get("companyId");
		String projectId = taskQuryMap.get("projectId");
		String orderNum = taskQuryMap.get("orderNum");
		if(pass && processId.equals(ProcessConst.proc_def_id_NewAndRenewOrder) && checkLable.equals("财务审核")){
			if(OrderStatus.equals("unsubsribe")){
				String status = orderService.changeRetretOrderVerify(orderNum, user.getCurrentUserId());
				JSONObject jsStr = JSONObject.fromObject(status);
				int result = jsStr.getInt("result");
				if(result != 0){
					return "error";
				}
			} else{
				try{
					String status = orderService.changeVerify(companyId, projectId, orderNum, user.getCurrentUserId());
					JSONObject jsStr = JSONObject.fromObject(status);
					int result = jsStr.getInt("result");
					if(result != 0){
						return "error";
					}
				} catch (Exception e){
					e.printStackTrace();
					return "error";
				}
			}
		}
		if(pass && processId.equals(ProcessConst.proc_def_id_NewProject) && checkLable.equals("客户商务人员审核")){
			Project project = projectMapper.selectById(Integer.valueOf(projectId));
			String[] passport = getProperties().split(":");
			try {
				String status = projectService.ProjectVerify(project, Integer.valueOf(companyId), passport[0], passport[1]);
				System.out.println(status);
				JSONObject jsStr = JSONObject.fromObject(status);
				int result = jsStr.getInt("result");
				if(result != 0){
					return "error";
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		if(pass && processId.equals(ProcessConst.proc_def_id_OpenAndCloseOrder) && checkLable.equals("财务审核") && OrderStatus.equals("open")){
			String status = orderService.changeVerify(companyId, projectId, orderNum, user.getCurrentUserId());
			JSONObject jsStr = JSONObject.fromObject(status);
			int result = jsStr.getInt("result");
			if(result != 0){
				return "error";
			}
		}
		variables.put(verifyInfo, checkInfo);
		if(pass){
			variables.put(verifyStatus, "true");
		} else if(processId.equals(ProcessConst.proc_def_id_OpenAndCloseOrder) && checkLable.equals("财务审核")){
			if(OrderStatus.equals("open")){
				variables.put(verifyStatus, "NoOpen");
			}
			if(OrderStatus.equals("close")){
				variables.put(verifyStatus, "Noclose");
			}
		} else {
			variables.put(verifyStatus, "false");
		}
		taskService.claim(taskId, user.getCurrentUserId().toString());
		formService.submitTaskFormData(taskId, variables);
		return "success";
	}
	
	@RequestMapping(value ="/activiti/viewHistory")
	@ResponseBody
	public List<Map<String,String>> viewHistory(String processInstanceId){
		List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery().
				processInstanceId(processInstanceId).list();
		/*List<HistoricVariableInstance> variableInstance = historyService.createHistoricVariableInstanceQuery().
				processInstanceId(processInstanceId).list();*/
/*		List<HistoricDetail> historicList = historyService.createHistoricDetailQuery().processInstanceId(processInstanceId).list();
		for(HistoricDetail historicDetail : historicList){
			if(historicDetail instanceof HistoricFormProperty){
				HistoricFormProperty field = (HistoricFormProperty) historicDetail;
				variableMaps.put(field.getPropertyId() , field.getPropertyValue());
			}

		}*/
		List<Map<String,String>> taskInfo = new ArrayList<Map<String,String>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i = 0; i < activityInstances.size(); i++){
			User user = null;
			Map<String,String> maps = new HashMap<String,String>();
			Date date = activityInstances.get(i).getEndTime();
			String userid = activityInstances.get(i).getAssignee();
			maps.put("activityName", activityInstances.get(i).getActivityName());
			if(date == null){
				maps.put("endTime", "");
			} else{
				maps.put("endTime", sdf.format(date));
			}
			if(userid == null){
				maps.put("assignee", "");
			} else{
				user = userMapper.selectById2(Integer.parseInt(userid));
				maps.put("assignee", user.getRealName());
			}
			List<HistoricDetail> historicList = historyService.createHistoricDetailQuery().activityInstanceId(activityInstances.get(i).getId()).list();
			for(HistoricDetail historicDetail : historicList){
				if(historicDetail instanceof HistoricFormProperty){
					HistoricFormProperty field = (HistoricFormProperty) historicDetail;
					String propertyId = field.getPropertyId();
					String propertyValue = field.getPropertyValue();
					if(i == 0){
						//maps.put("assignee", historicDetail.get("applyUser"));
						maps.put("result", "开启流程");
						if(propertyId.equals("applyUser")){
							maps.put("assignee", field.getPropertyValue());
						}
					}
					if(propertyId.equals("ownSaleVeify") 
							|| propertyId.equals("customerBussinessVeify") 
							|| propertyId.equals("FinanceVeify") 
							|| propertyId.equals("ProjectManagerVeify") 
							|| propertyId.equals("CustomerBusinessmanVeify")){
						if(propertyValue.equals("true")){
							maps.put("result", "通过");
						} else if(propertyValue.equals("false")){
							maps.put("result", "否决");
						}
					}
					if(propertyId.equals("SalesAssistantverifyInfo") 
							|| propertyId.equals("customerVerifyInfo") 
							|| propertyId.equals("FinanceInfo") 
							|| propertyId.equals("ProjectManagerInfo") 
							|| propertyId.equals("CustomerBusinessmanInfo")){
						maps.put("tips", propertyValue);
					}
					
				}

			}
			taskInfo.add(maps);
		}
		
		return taskInfo;
	}
	
	public String getProperties() {
		Properties pro = new Properties();
		InputStream in = getClass().getResourceAsStream("/handshake.properties");
		try {
			pro.load(in);
		} catch (IOException e) {
			System.err.println("读取配置握手文件失败");
		}
		String username = pro.getProperty("username");
		String password = pro.getProperty("password");
		return username + ":" + password;
	}
}
