package com.routon.plcloud.admin.order.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.routon.plcloud.admin.activiti.common.ActivitiInfoQuery;
import com.routon.plcloud.admin.activiti.model.ProcessConst;
import com.routon.plcloud.admin.activiti.service.ActivitiCommonService;
import com.routon.plcloud.admin.order.service.OrderService;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.privilege.service.RoleService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.SubOrder;
import com.routon.plcloud.common.model.User;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.OrderMapper;
import com.routon.plcloud.common.persistence.ProjectMapper;
import com.routon.plcloud.common.persistence.SuborderMapper;
import com.routon.plcloud.common.persistence.UserMapper;
import com.routon.plcloud.common.utils.JsonMsgBean;

import net.sf.json.JSONArray;

/**
 * 
 * @author wangxiwei
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class OrderController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private FormService formService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private SuborderMapper suborderMapper;
	
	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private ActivitiCommonService activitiCommonService;

	/*
	 * @Autowired private OrderProjectMapper OrderProjectMapper;
	 */

	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	@Resource(name = "ActivitiInfoQueryBean")
	protected ActivitiInfoQuery activitiInfoQuery;

	@RequestMapping(value = "/order/show")
	public String list(HttpServletRequest request, @ModelAttribute("userProfile") UserProfile user, Model model,
			Order queryCondition, String orderNum, String projectid, String treeNodeTid, String companyid,
			String status,Long requirementtype,String searchname, String task_no_page, String claimed_page, String allTask_page, String tabStatus,
			String currentTab, String pageOrder) throws Exception {

		Long loginUserId = user.getCurrentUserId();

		Company company = null;
		Project project = null;
		int page = 0;
		if(pageOrder == null || "".equals(pageOrder)){
			 page = NumberUtils.toInt(request.getParameter("page"), 1);
		} else{
			 page = Integer.parseInt(pageOrder);
		}
		
		int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 10);
		int startIndex = (page - 1) * pageSize;

		int task_no_received_page = NumberUtils.toInt(task_no_page, 1);
		int task_no_received_pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 3);
		int task_no_received_startIndex = (task_no_received_page - 1) * task_no_received_pageSize;

		int claimed_page_var = NumberUtils.toInt(claimed_page, 1);
		int claimed_pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 3);
		int claimed_startIndex = (claimed_page_var - 1) * claimed_pageSize;

		int allTask_page_var = NumberUtils.toInt(allTask_page, 1);
		int allTask_pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 3);
		int allTask_startIndex = (allTask_page_var - 1) * allTask_pageSize;

		activitiInfoQuery.QueryActivitiInfo(model, user.getCurrentUserId().toString(), task_no_received_page,
				task_no_received_startIndex, task_no_received_pageSize, claimed_page_var, claimed_startIndex,
				claimed_pageSize, allTask_page_var, allTask_startIndex, allTask_pageSize, "Order");

		if(projectid == null || projectid.equals("")){
			projectid = null;
		}
		if(companyid == null || companyid.equals("")){
			companyid = null;
		}
		/*
		 * ProcessDefinitionQuery query =
		 * repositoryService.createProcessDefinitionQuery().active().
		 * orderByDeploymentId().desc(); List<ProcessDefinition> list =
		 * query.list(); Long userId = user.getCurrentUserId(); List<Task>
		 * taskQuery =
		 * taskService.createTaskQuery().taskCandidateUser(userId.toString()).
		 * list(); List<Task> claimedTaskList =
		 * taskService.createTaskQuery().taskAssignee(userId.toString()).list();
		 * 
		 * List<Map<String,String>> formList = generateFormData(taskQuery,
		 * list); List<Map<String,String>> claimedTaskMap =
		 * generateFormData(claimedTaskList, list);
		 */

		/*
		 * for(int i = 0 ; i < taskQuery.size(); i++){
		 * taskService.claim(taskQuery.get(i).getId(), userId.toString());
		 * System.out.println("任务["+ taskQuery.get(i).getId()
		 * +"]已被"+userId+"签收"); }
		 */

		// test
		/*
		 * List<Task> task60 =
		 * taskService.createTaskQuery().taskCandidateUser("60").list();
		 * List<Task> task274 =
		 * taskService.createTaskQuery().taskCandidateUser("274").list();
		 */

		/*
		 * //查询所有历史活动记录 List<HistoricActivityInstance> activityInstance =
		 * historyService.createHistoricActivityInstanceQuery()
		 * .processInstanceId("12501") .list(); //查询历史流程实例
		 * HistoricProcessInstance historicProcessInstance =
		 * historyService.createHistoricProcessInstanceQuery()
		 * .processInstanceId("12501") .singleResult();
		 */
		//获取订单树
//		List<TreeBean> menuTreeBeansResult = orderService.getMenuTrees(loginUserId);
		List<TreeBean> menuTreeBeansResult = orderService.getMenuTrees(loginUserId,requirementtype,searchname);

		// PagingBean<Order> pageBean = orderService.queryALL(/*startIndex,
		// pageSize, */orderNum, startIndex, pageSize, projectid);

		// PagingBean<Order> pageBean = orderService.paging(startIndex,
		// pageSize, request.getParameter("sort"),
		// request.getParameter("dir"), queryCondition, projectid,null, null,
		// loginUserId, request.getParameter("exportflag") !=
		// null&&request.getParameter("exportflag").equals("true")?true:false);

		PagingBean<Order> pageBean = orderService.paging(startIndex, pageSize, request.getParameter("sort"),
				request.getParameter("dir"), queryCondition, status, projectid, companyid, null, null, loginUserId,
				request.getParameter("exportflag") != null && request.getParameter("exportflag").equals("true") ? true
						: false);

		if (companyid != null) {
			company = orderService.queryCompanyById(companyid);
			//返回ztree点击节点的id（公司）
			model.addAttribute("tmpcompanyid", companyid);
		}
		if (projectid != null) {
			project = orderService.queryProjectById(projectid);
			//返回ztree点击节点的id（项目）
			model.addAttribute("tmpprojectid", projectid);
		}

//		List<String> softwareERPcodeList = orderService.querySoftwareErpCodeAll();

		int maxpage = (int) Math.ceil(pageBean.getTotalCount() / (double) pageSize);
		if (pageBean.getTotalCount() == 0) {
			maxpage = 0;
		}

		// 订单号查询回填
		if (StringUtils.isNotBlank(queryCondition.getOrdernum())) {
			model.addAttribute("ordernum", queryCondition.getOrdernum());
		}
		// //订单状态查询回填
		if (queryCondition.getStatus() == 1) {
			model.addAttribute("stau", queryCondition.getStatus());
		} else if (queryCondition.getStatus() == 2) {
			model.addAttribute("stau", queryCondition.getStatus());
		}else if (queryCondition.getStatus() == 3) {
			model.addAttribute("stau", queryCondition.getStatus());
		}
		// 订单状态查询回填
		// if(status != null){
		// if (Integer.valueOf(status) == 1) {
		// model.addAttribute("stau", Integer.valueOf(status));
		// } else if (Integer.valueOf(status) == 2) {
		// model.addAttribute("stau", Integer.valueOf(status));
		// }
		// }

		// model.addAttribute("pageList_process", list);
		// 查询订单已授权数量
		// ArrayList<Project> projects = (ArrayList<Project>)
		// pagingBean.getDatas();
		List<Order> orders = pageBean.getDatas();
		for (Order systemorder : orders) {
			// String command = "LicenceNumQuery";
			// int version = 256;
			//
			// String sql="select * from company a LEFT JOIN projectcompany b on
			// a.id = b.companyid LEFT JOIN project c on b.projectid = c.id "
			// + "LEFT JOIN orderproject d on c.id = d.projectid LEFT JOIN
			// \"order\" e on d.orderid = e.id where e.id = " +
			// systemorder.getId();
			// List<Company> temcompany = companyMapper.selectBySql(sql);
			// String company_name = temcompany.get(0).getCompanyname();
			//
			// String sql1 ="select * from project a left join orderproject b on
			// a.id = b.projectid left join \"order\" c on b.orderid = c.id "
			// + "where c.id = " + systemorder.getId();
			// List<Project> projects = projectMapper.selectBysql(sql1);
			// String project_name = projects.get(0).getProjectname();
			//
			// String orderid = systemorder.getOrdernum();
			// String output = orderService.LicenceNumQuery(command, version,
			// company_name, project_name, orderid);
			//
			// JSONObject jsStr = JSONObject.fromObject(output);
			// String term_num = jsStr.getString("term_num");
			// String demandquantity = systemorder.getDemandquantity();
			// String restnum =
			// Integer.toString(Integer.parseInt(demandquantity) -
			// Integer.parseInt(term_num));
			String term_num = orderService.querytermNum(systemorder.getId());
			String renewcount = orderService.queryrenewCount(systemorder.getId());
			systemorder.setAuthorizedNum(term_num);
			systemorder.setRenewCount(renewcount);
			// systemorder.setRestnum(restnum);
		}
		pageBean.setDatas(orders);

		// model.addAttribute("List", formList);
		// model.addAttribute("claimedTaskList", claimedTaskMap);
		model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeansResult).toString());
		model.addAttribute("pageList", pageBean);
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("page", page);
		model.addAttribute("treeNodeTid", treeNodeTid);
		model.addAttribute("company", company);
		model.addAttribute("project", project);
//		model.addAttribute("softwareERPcodeList", softwareERPcodeList);
		model.addAttribute("tabStatus", tabStatus);
		model.addAttribute("currentTab", currentTab);
		return "order/list";
	}

	// 子订单列表显示
	@RequestMapping(value = "/order/childordershow", produces = { "text/html;charset=UTF-8;", "application/json;" })
	@ResponseBody
	public String childlist(HttpServletRequest request, Model model, Integer id) throws Exception {

		int page = NumberUtils.toInt(request.getParameter("page"), 1);
		int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 10);
		int startIndex = (page - 1) * pageSize;

		PagingBean<SubOrder> pageBean = orderService.pagingchildOrder(startIndex, pageSize, id);
		List<SubOrder> list = pageBean.getDatas();

		for (int i = 0; i < list.size(); i++) {
			// for(Order order : list){
			Date date = list.get(i).getCreatetime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String creattime = sdf.format(date);
			// System.out.println(creattime+":creattime");
			list.get(i).setCreatetimeStr(creattime);
		}

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		// System.out.println(json);
		return json;
		// JSONObject json = new JSONObject();
		// json.put("order", list);
		// System.out.println(json.get("order"));
		// return json.get("order").toString();
		// int maxpage = (int) Math.ceil(pageBean.getTotalCount()/ (double)
		// pageSize);
		// if (pageBean.getTotalCount() == 0) {
		// maxpage = 0;
		// }

		// model.addAttribute("childpageList", pageBean);
		// model.addAttribute("childpageList", list);
		// request.setAttribute("childpageList", list);
		// model.addAttribute("maxpage", maxpage);
		// model.addAttribute("page", page);
		// return "order/list";
	}

	public List<Map<String, String>> generateFormData(List<Task> taskQuery, List<ProcessDefinition> list) {
		List<Map<String, String>> formList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < taskQuery.size(); i++) {
			Map<String, String> taskQuryMap = new HashMap<String, String>();
			for (int j = 0; j < list.size(); j++) {
				if (taskQuery.get(i).getProcessDefinitionId().equals(list.get(j).getId())) {
					taskQuryMap.put("processName", list.get(j).getName());
					break;
				}
			}
			List<HistoricDetail> historicList = historyService.createHistoricDetailQuery()
					.processInstanceId(taskQuery.get(i).getProcessInstanceId()).list();
			taskQuryMap.put("id", taskQuery.get(i).getId());
			taskQuryMap.put("processInstanceId", taskQuery.get(i).getProcessInstanceId());
			taskQuryMap.put("processDefinitionId", taskQuery.get(i).getProcessDefinitionId());
			taskQuryMap.put("name", taskQuery.get(i).getName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			taskQuryMap.put("createTime", sdf.format(taskQuery.get(i).getCreateTime()));
			for (HistoricDetail historicDetail : historicList) {
				if (taskQuery.get(i).getProcessInstanceId().equals(historicDetail.getProcessInstanceId())) {
					if (historicDetail instanceof HistoricFormProperty) {
						HistoricFormProperty field = (HistoricFormProperty) historicDetail;
						taskQuryMap.put(field.getPropertyId(), field.getPropertyValue());
					}
				}
				// historicVarList.addAll(list);
			}
			formList.add(taskQuryMap);
		}
		return formList;
	}

	//保存订单
	@RequestMapping(value = "/order/save")
	public String save(Order order, @ModelAttribute("userProfile") UserProfile user, Model model, Boolean activitiTag,
			String companyName, String projectName, String demandquantity, String remark, String companyId,
			String renew, String subordercontact) {

		JsonMsgBean jsonMsg = null;
		Long id = null;
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (order.getOrderActiveStatus() == "1" || order.getOrderActiveStatus().equals("1")) {
			if (order.getStarttime() != null) {
				try {
					if (order.getMonth() != null) {
						Date str = sdf.parse(order.getStarttime());
						GregorianCalendar gc = new GregorianCalendar();
						gc.setTime(str);
						gc.add(2, Integer.parseInt(order.getMonth()));
						String endtime = sdf.format(gc.getTime());
						order.setEndtime(endtime);
						order.setCreatetime(new Date());
						order.setModitytime(new Date());
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} else if (order.getOrderActiveStatus() == "2" || order.getOrderActiveStatus().equals("2")) {
			if (order.getTacitstarttime() != null) {
				if (order.getMonth() != null) {
					order.setCreatetime(new Date());
					order.setModitytime(new Date());
				}
			}
		} else if (order.getOrderActiveStatus() == "3" || order.getOrderActiveStatus().equals("3")) {
			if (order.getEndtime() != null) {
				order.setCreatetime(new Date());
				order.setModitytime(new Date());
			}
		}

		if (order.getId() != null && order.getId() > 0) {
			id = orderService.edit(order, user, renew, subordercontact);

			// if(id>0){
			// jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
			// }else if(id == -1){
			// jsonMsg = new JsonMsgBean(0, CVal.Fail, "订单已审核通过，不能修改!");
			// }else {
			// jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存订单失败!");
			// }
		} else {
			id = orderService.save(order, user);

			// if(id>0){
			// jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
			// }else {
			// jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存订单失败!");
			// }
		}

		if (id > 0) {
			jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
		} else {
			jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存订单失败!");
		}

		if (activitiTag == null) {
			activitiTag = false;
		}
		if (activitiTag) {
			//String processDefinitionId = "NewAndRenewProcess:1:240010";
			String orderNum = orderMapper.selectById1(id);
			identityService.setAuthenticatedUserId(user.getCurrentUserId().toString());
			String startUser = user.getCurrentUserRealName();
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("projectName", projectName);
			variables.put("authorNums", demandquantity);
			variables.put("remarks", remark);
			variables.put("applyUser", startUser);
			variables.put("companyName", companyName);
			variables.put("orderNum", orderNum);
			variables.put("projectId", order.getProjectid());
			variables.put("companyId", companyId);
			variables.put("OrderStatus", "new");
/*			if(renew == null || renew.equals("")){
				variables.put("New", "true");
			} else{
				variables.put("New", "false");
			}*/
			ProcessInstance instance = formService.submitStartFormData(ProcessConst.proc_def_id_NewAndRenewOrder, variables);
			if (instance == null) {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "订单[" + id + "]对应的流程不存在!");
				model.addAttribute("jsonMsg", jsonMsg);
				return "common/jsonTextHtml";
			}
		}

		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";

	}

	//保存子订单
	@RequestMapping(value = "/order/subordersave")
	public String subordersave(Order order, @ModelAttribute("userProfile") UserProfile user, Model model,
			String renew, String subordercontact,String renewverify, String companyName,
			String projectName, String demandquantity, String remark) {

		JsonMsgBean jsonMsg = null;
		Long id = null;
		
		if (renewverify!=null && !renewverify.equals("") && renewverify.equals("2")) {
			id = orderService.editSuborder(order, user);
		} else {
			String orderStatus = null;
			id = orderService.saveSuborder(order, user, renew, subordercontact);
			// activiti added by wangxiwei in 20180627, do not delete
			Order order1 = orderMapper.selectById(order.getId());
			Map<String,Object> orderinfo = orderMapper.
					selectByCustomSql("SELECT P.id AS projectid, pc.companyid "
							+ "FROM project P LEFT JOIN orderproject op ON P.id = op.projectid "
							+ "LEFT JOIN projectcompany pc ON P .id = pc.projectid WHERE op.orderid = '"+ order.getId() +"'");
			identityService.setAuthenticatedUserId(user.getCurrentUserId().toString());
			String startUser = user.getCurrentUserRealName();
			Map<String,String> variables = new HashMap<String, String>();
			String companyid = orderinfo.get("companyid").toString();
			String projectid = orderinfo.get("projectid").toString();
			variables.put("projectName", projectName);
			variables.put("authorNums", demandquantity);
			variables.put("remarks", remark);
			variables.put("applyUser", startUser);
			variables.put("companyName", companyName);
			variables.put("orderNum", order1.getOrdernum());
			variables.put("projectId", projectid);
			variables.put("companyId", companyid);
			if(renew.equals("xuding")){
				orderStatus = "renew";
			} else if(renew.equals("tuiding")){
				orderStatus = "unsubsribe";
			}
			variables.put("OrderStatus", orderStatus);
			ProcessInstance instance = formService.submitStartFormData(ProcessConst.proc_def_id_NewAndRenewOrder, variables);
			if (instance == null) {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "订单[" + id + "]对应的流程不存在!");
				model.addAttribute("jsonMsg", jsonMsg);
				return "common/jsonTextHtml";
			}
		}

		if(id>0){
			jsonMsg = new JsonMsgBean(0, CVal.Success, "子订单保存成功!");
		}else{
			jsonMsg = new JsonMsgBean(0, CVal.Fail, "子订单保存失败!");
		}

		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";

	}
	
	// @RequestMapping(value = "/order/save")
	// public String save(Order order, @ModelAttribute("userProfile")
	// UserProfile user, Model model, String orderActiveStatus,
	// String startTimeByContract, String startTimeByActive, String
	// startDate_createTime, String month_1, String month_2,
	// String projectid, String companyid,Integer updateId){
	//
	// JsonMsgBean jsonMsg = null;
	//// Long id = null;
	//
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// if(orderActiveStatus == "1" || orderActiveStatus.equals("1")){
	// if(startTimeByContract != null){
	// order.setStarttime(startTimeByContract);
	// try {
	// if(month_1 != null){
	// Date str = sdf.parse(startTimeByContract);
	// GregorianCalendar gc = new GregorianCalendar();
	// gc.setTime(str);
	// gc.add(2, Integer.parseInt(month_1));
	// String endtime = sdf.format(gc.getTime());
	// order.setEndtime(endtime);
	// order.setMonth(month_1);
	// order.setCreatetime(new Date());
	// }
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// }
	// } else if(orderActiveStatus == "2" || orderActiveStatus.equals("2")){
	// if(startTimeByActive != null){
	// order.setTacitstarttime(startTimeByActive);
	// if(month_2 != null){
	// order.setMonth(month_2);
	// order.setCreatetime(new Date());
	// }
	// }
	// }else if(orderActiveStatus == "3" || orderActiveStatus.equals("3")){
	// if(startDate_createTime != null){
	// order.setEndtime(startDate_createTime);
	// order.setCreatetime(new Date());
	// }
	// }
	//
	// if(updateId == null){
	// Long id=orderService.save(order,projectid,companyid);
	// if (id > 0) {
	// jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
	// }
	// else {
	// jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败!");
	// }
	// }else{
	// Long id=orderService.edit(updateId,order);
	// if (id > 0) {
	// jsonMsg = new JsonMsgBean(0, CVal.Success, "编辑成功!");
	// }
	// else {
	// jsonMsg = new JsonMsgBean(0, CVal.Fail, "编辑失败!");
	// }
	// }
	//
	//
	// model.addAttribute("jsonMsg", jsonMsg);
	// return "common/jsonTextHtml";
	//
	// }

	@RequestMapping(value = "/order/ordershow")
	@ResponseBody
	public JsonMsgBean orderShow(@ModelAttribute("userProfile") UserProfile user, Integer id, String orderNum) {
		JsonMsgBean jsonMsg = null;
		Long loginUserId = user.getCurrentUserId();

		// modified by wangxiwei in 20180223
/*		String currentTaskName = activitiCommonService.getCurrentTaskName("000280000600003");
		System.out.println("orderNum["+ orderNum +"] taskName:"+currentTaskName);*/
		if (orderNum != null) {
			Long orderid = orderMapper.selectByOrdernum(orderNum).getId();
			id = orderid.intValue();
		}
		
		String subordersql="select a.* from \"order\" b left join order_suborder c on b.id=c.orderid left join suborder a on "
				+ "c.suborderid=a.id where b.id="+ id +" order by a.createtime desc";
		List<SubOrder> suborders =suborderMapper.selectBysql(subordersql);
		String renewnum ="";
		if(suborders.get(0)!=null){
			renewnum = suborders.get(0).getRenewnum();
		}
		
		Order order = orderMapper.selectById(id);
		if(!renewnum.equals("") && renewnum!=null){
			order.setSuborderRenewnum(renewnum);
		}
		
		String sql = "select * from project a left join orderproject b on a.id = b.projectid left join \"order\" c on b.orderid = c.id "
				+ "where c.id = " + id;
		List<Project> projects = projectMapper.selectBysql(sql);
		Project project = projects.get(0);

		String sql1 = "select * from company a LEFT JOIN projectcompany b on a.id = b.companyid LEFT JOIN project c on b.projectid = c.id "
				+ "LEFT JOIN orderproject d on c.id = d.projectid LEFT JOIN \"order\" e on d.orderid = e.id where e.id = "
				+ id;
		List<Company> companys = companyMapper.selectBySql(sql1);
		Company company = companys.get(0);

		// 判断该用户是否有编辑权限
		int editpri = 0;
		String editprisql = "select DISTINCT a.* from users a left join userrole b on a.id = b.userid left join rolemenu c on b.roleid = c.roleid where c.menuid ='90000302' and a.id = "
				+ loginUserId;
		List<User> user2 = userMapper.selectBySql(editprisql);
		if (user2 != null && user2.size() > 0) {
			editpri = 1;
		}

		String activityStatu = activitiCommonService.getCurrentTaskName(order.getOrdernum());
//		String activityStatu = "新建订单/续订订单/提交订单申请";
		jsonMsg = new JsonMsgBean(company, project, order, editpri,activityStatu);
		return jsonMsg;
	}

	@RequestMapping(value = "/order/querySoftwareNameByERPcode", produces = "text/html;charset=UTF-8;")
	public @ResponseBody String querySoftwareNameByERPcode(HttpServletRequest request, String erpCode)
			throws UnsupportedEncodingException {

		String result = orderService.querySoftwareNameByERPcode(erpCode);
		return result;

	}

	@RequestMapping(value = "order/closeOrder", method = RequestMethod.POST)
	public String disableOrder(Integer id, Model model, @ModelAttribute("userProfile") UserProfile user) {
		
		JsonMsgBean jsonMsg = null;
		
		long result = orderService.disableOrder(id, user);

		if(result == 0){
			int processResult = openProcess(id, user.getCurrentUserId().toString(), user.getCurrentUserRealName(), "close");
			if(processResult == 0){
				jsonMsg = new JsonMsgBean(0, CVal.Success, "强制停止订单成功!");
			} else{
				jsonMsg = new JsonMsgBean(0, CVal.Success, "开启对应流程失败!");
			}
		}else{
			jsonMsg = new JsonMsgBean(0, CVal.Fail, "强制停止订单失败!");
		}

//		JsonMsgBean jsonMsg = null;
//		try {
//			long result = orderService.disableOrder(id, user);
//
//			if (result == 1) {
//				jsonMsg = new JsonMsgBean(0, CVal.Success, "停止订单成功!");
//
//			} else if (result == -1) {
//				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选订单与终端关联,不能停止!");
//
//			} else {
//				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选订单部分停止成功,还有部分因与终端关联,不能停止!");
//
//			}
//
//		} catch (Exception e) {
//			jsonMsg = new JsonMsgBean(0, CVal.Exception, "停止订单异常!");
//
//		}

		// try {
		// String orderID_array[] = id.split(",");
		// String disableOrderIds = "";
		// for(String orderId : orderID_array){
		// if(disableOrderIds.equals("")){
		// disableOrderIds += orderId;
		// }
		// else{
		// disableOrderIds += ",";
		// disableOrderIds += orderId;
		// }
		// }
		// orderService.disableOrder(disableOrderIds);
		// jsonMsg = new JsonMsgBean(0, CVal.Success, "");
		// } catch (Exception e) {
		// jsonMsg = new JsonMsgBean(0, CVal.Exception, "关闭订单异常!");
		// }

		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}

	@RequestMapping(value = "order/openOrder", method = RequestMethod.POST)
	public String openOrder(Integer id, Model model, @ModelAttribute("userProfile") UserProfile user) {
		
		JsonMsgBean jsonMsg = null;
		
		long result = orderService.openOrder(id, user);

		if(result == 0){
			int processResult = openProcess(id, user.getCurrentUserId().toString(), user.getCurrentUserRealName(), "open");
			if(processResult == 0){
				jsonMsg = new JsonMsgBean(0, CVal.Success, "开启订单成功!");
			} else{
				jsonMsg = new JsonMsgBean(0, CVal.Success, "开启对应流程失败!");
			}
			
		} else{
			jsonMsg = new JsonMsgBean(0, CVal.Fail, "开启订单失败!");
		}

		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}
	
	@RequestMapping(value = "order/initActivitiDb", method = RequestMethod.POST)
	@ResponseBody
	public String initActivitiDb(Model model) {
		/*
		 * User user1 = identityService.newUser("60"); user1.setFirstName("吴");
		 * user1.setLastName("梦华"); User user2 = identityService.newUser("281");
		 * user2.setFirstName("黄"); user2.setLastName("祥己"); User user3 =
		 * identityService.newUser("1"); user3.setFirstName("财务");
		 * user3.setLastName("测试账号"); identityService.saveUser(user1);
		 * identityService.saveUser(user2); identityService.saveUser(user3);
		 * Group group1 = identityService.newGroup("salesAssistant");
		 * group1.setName("我方销售助理"); group1.setType("salesAssistant"); Group
		 * group2 = identityService.newGroup("CustomerBussinessman");
		 * group2.setName("客户商务人员"); group2.setType("CustomerBussinessman");
		 * identityService.saveGroup(group1); identityService.saveGroup(group2);
		 * identityService.createMembership("60", "salesAssistant");
		 * identityService.createMembership("281", "CustomerBussinessman");
		 * identityService.createMembership("1", "salesAssistant");
		 */

		Group group1 = identityService.newGroup("salesAssistant");
		group1.setName("我方销售助理");
		group1.setType("salesAssistant");
		Group group2 = identityService.newGroup("CustomerBussinessman");
		group2.setName("客户商务人员");
		group2.setType("CustomerBussinessman");
		return "success";
	}

	@RequestMapping(value = "order/claimTask", method = RequestMethod.POST)
	@ResponseBody
	public String claimTask(Model model, @ModelAttribute("userProfile") UserProfile user, String processInstantId) {
		try {
			taskService.claim(processInstantId, user.getCurrentUserId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public int openProcess(Integer id, String userid, String UserRealName, String orderTag){
		Map<String,Object> orderinfo = orderMapper.
				selectByCustomSql("SELECT P . ID AS projectid,P .projectname,P .demandquantity,o .remarks,pc.companyid,co.companyname,o.ordernum "
						+ "FROM project P LEFT JOIN orderproject op ON P . ID = op.projectid "
						+ "LEFT JOIN projectcompany pc ON P . ID = pc.projectid "
						+ "LEFT JOIN company co ON co. ID = pc.companyid "
						+ "LEFT JOIN \"order\" o ON o. ID = op.orderid "
								+ "WHERE op.orderid = '"+ id +"'");
		identityService.setAuthenticatedUserId(userid);
		Map<String, String> variables = new HashMap<String, String>();
		String projectName = orderinfo.get("projectname").toString();
		String demandquantity = orderinfo.get("demandquantity").toString();
		String remark = orderinfo.get("remarks").toString();
		String companyName = orderinfo.get("companyname").toString();
		String orderNum = orderinfo.get("ordernum").toString();
		String projectid = orderinfo.get("projectid").toString();
		String companyid = orderinfo.get("companyid").toString();
		variables.put("projectName", projectName);
		variables.put("authorNums", demandquantity);
		variables.put("remarks", remark);
		variables.put("applyUser", UserRealName);
		variables.put("companyName", companyName);
		variables.put("orderNum", orderNum);
		variables.put("projectId", projectid);
		variables.put("companyId", companyid);
		variables.put("OrderStatus", orderTag);
		ProcessInstance instance = formService.submitStartFormData(ProcessConst.proc_def_id_OpenAndCloseOrder, variables);
		if (instance == null) {
			return -1;
		}
		return 0;
	}
}
