package com.routon.plcloud.admin.order.action;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.activiti.common.ActivitiInfoQuery;
import com.routon.plcloud.admin.activiti.model.ProcessConst;
import com.routon.plcloud.admin.order.service.OrderService;
import com.routon.plcloud.admin.order.service.ProjectService;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.ProjectCompany;
import com.routon.plcloud.common.model.User;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.ProjectCompanyMapper;
import com.routon.plcloud.common.persistence.ProjectMapper;
import com.routon.plcloud.common.persistence.UserMapper;
import com.routon.plcloud.common.service.MessageServiceImpl;
import com.routon.plcloud.common.utils.JsonMsgBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author wangzhuo
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class ProjectController {
	
	private Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	private final String RMPATH = "/project/";
	
	@Resource(name = "projectServiceImpl")
	private ProjectService projectService;
	
	 @Autowired
	 private CompanyMapper companyMapper;
	 
	 @Autowired
	 private UserMapper userMapper;
	 
	 @Autowired
	private OrderService orderService;
	 
	 @Autowired
	 private ProjectMapper projectMapper;
	 
	 @Autowired
	 private ProjectCompanyMapper projectCompanyMapper;
	 
	 @Resource(name = "messageServiceImpl")
     protected MessageServiceImpl messageService;
	
	@Resource(name = "ActivitiInfoQueryBean")
	protected ActivitiInfoQuery activitiInfoQuery;
	
	@Autowired
	private IdentityService identityService;

	@Autowired
	private FormService formService;
	
	@RequestMapping(value = RMPATH + "list")
	public String list(HttpServletRequest request, Company queryCondition, Project queryCondition1,Long id,
			@ModelAttribute("userProfile")
			UserProfile user, Model model, HttpSession session , String treeNodeTid, String task_no_page,
			String claimed_page, String allTask_page, String tabStatus, String currentTab, String pageOrder) throws MalformedURLException, IOException {

		Long loginUserId = user.getCurrentUserId();	
		
		String username = user.getCurrentUserRealName();
		
		//List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();
		//公司树状图展示
		List<TreeBean> companyTreeBeans = projectService.getCompanyTreeByUserId(loginUserId, queryCondition, null);	
		
		//软件ERP编码
		List<String> softwareERPcodeList = orderService.querySoftwareErpCodeAll();
		
		int page = 0;
		if(pageOrder == null || "".equals(pageOrder)){
			 page = NumberUtils.toInt(request.getParameter("page"), 1);
		} else{
			 page = Integer.parseInt(pageOrder);
		}
		
		int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 10);
		int startIndex = (page - 1) * pageSize;		
		
		//PagingBean<HardwareProduct> hardwarelist = hardwareService.quryAll(startIndex, pageSize, productName);
		//PagingBean<Company> pagingBean = companyService.paging(
		//startIndex, pageSize, request.getParameter("sort"),
		//request.getParameter("dir"), queryCondition, null, null, loginUserId,request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
		PagingBean<Project> pagingBean = projectService.paging(startIndex, pageSize, request.getParameter("sort"), request.getParameter("dir"), queryCondition1,
				null, null, id, loginUserId, request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
		if (StringUtils.isNotBlank(queryCondition1.getProjectname())) {
			model.addAttribute("projectname", queryCondition1.getProjectname().trim());
		}
		
		int maxpage = (int)Math.ceil(pagingBean.getTotalCount()/(double)pageSize);
		if (pagingBean.getTotalCount() == 0) {
			maxpage = 0;
		}
		if(StringUtils.isNotBlank(queryCondition.getCompanyname())){
			model.addAttribute("companyname", queryCondition.getCompanyname().trim());
		}
		
		//新增项目管理中终端授权数量
//		ArrayList<Project> projects = (ArrayList<Project>) pagingBean.getDatas();
		List<Project> projects = pagingBean.getDatas();
		for(Project systemproject : projects){
			//增加授权数量信息在前台显示
			String command = "LicenceNumQuery";
		    int version = 256;
		    String sql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = " + systemproject.getId();
		    List<Company> temcompany = companyMapper.selectBySql(sql);
		    String company_name = temcompany.get(0).getCompanyregname();
		    String project = systemproject.getProjectregname();
		    String orderid = null;
		    String output = projectService.LicenceNumQuery(command, version, company_name, project,orderid);
		    System.out.println(output);
		    logger.info("已授权数量 LicenceNumQuery ：" + output);
		    JSONObject jsStr = JSONObject.fromObject(output);
	 		String term_num=jsStr.getString("term_num");
	 		String demandquantity = systemproject.getDemandquantity();
	 		String restnum = null;
			try {
				restnum = Integer.toString(Integer.parseInt(demandquantity) - Integer.parseInt(term_num));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		systemproject.setOrdersale(term_num);
	 		systemproject.setRestnum(restnum);
		}
		pagingBean.setDatas(projects);
		
		// added by wangxiwei in 20180621, do not delete.
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
					claimed_pageSize, allTask_page_var, allTask_startIndex, allTask_pageSize, "Project");
	    model.addAttribute("tabStatus", tabStatus);
		model.addAttribute("currentTab", currentTab);
	    //************************************************
	    
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("page", page);
		model.addAttribute("treeNodeTid", treeNodeTid);
		model.addAttribute("treeNodeid", id);
		//model.addAttribute("List", taskQuery);
		model.addAttribute("softwareERPcodeList", softwareERPcodeList);
		model.addAttribute("groupTreeBeans", JSONArray.fromObject(companyTreeBeans).toString());
		model.addAttribute("pageList",pagingBean);	
		return "project/list";
	}
	
	@RequestMapping(value = "project/save", method = RequestMethod.POST)
	public String addProject(Project project, @ModelAttribute("userProfile") UserProfile optUser, Model model,
			String proid, Boolean activitiTag) {
		JsonMsgBean jsonMsg = null;
		do {
			try {
				Long id = null;
				//proid如果为空则为新增，默认为0
				if(proid==""){
					proid = "0";
				}
				Long projectid = Long.parseLong(proid);
				if(projectid!=null&&projectid>0){
					project.setId(projectid);
					String sql = "select p.* from projectcompany p where p.projectid = " +projectid ;
					List<ProjectCompany> tem_projectcompany = 	projectCompanyMapper.selectBySql(sql);		
					id = projectService.edit(project , (int)tem_projectcompany.get(0).getCompanyid() , optUser);
				}else {
					id = projectService.add(project, project.getCompanyid() , optUser);
				}
				
				if(id>0){
					project.setId(id);
					jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功");			
				}
				else if(id==-2){
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "项目名称已经存在");			
				}
				else if(id==-3){
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "项目注册名称已经存在");			
				}
				else {
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存项目失败");
//					jsonMsg = new JsonMsgBean((Integer)0, CVal.Fail);
				}
				
				
				// activiti part, added by wangxiwei in 20180622
				
				if (activitiTag == null) {
					activitiTag = false;
				}
				if (activitiTag) {
					int companyId = project.getCompanyid();
					identityService.setAuthenticatedUserId(optUser.getCurrentUserId().toString());
					String companyName = companyMapper.selectById1(companyId).getCompanyname();
					String startUser = optUser.getCurrentUserRealName();
					Map<String, String> variables = new HashMap<String, String>();
					variables.put("applyUser", startUser);
					variables.put("projectId", id.toString());
					variables.put("projectName", project.getProjectname());
					variables.put("companyId", String.valueOf(companyId));
					variables.put("companyName", companyName);
					ProcessInstance instance = formService.submitStartFormData(ProcessConst.proc_def_id_NewProject, variables);
					if (instance == null) {
						jsonMsg = new JsonMsgBean(0, CVal.Fail, "项目[" + id + "]对应的流程不存在!");
						model.addAttribute("jsonMsg", jsonMsg);
						return "common/jsonTextHtml";
					}
				}
				//**********************************************
				
				
			} 
			catch (Exception e) {
				logger.error("保存失败，请稍候重试", e);
				jsonMsg = new JsonMsgBean(0, CVal.Exception, "保存失败，请稍候重试");
				
			}
		}
		while(false);
		model.addAttribute("jsonMsg", jsonMsg);	
		return "common/jsonTextHtml";	
	}
	
	
	
	@RequestMapping(value = "/project/projectshow")
	@ResponseBody public JsonMsgBean ProjectShow(@ModelAttribute("userProfile")UserProfile userProfile , Integer id){
		JsonMsgBean jsonMsg = null;
		Long  loginUserId = userProfile.getCurrentUserId();
		
		Project project = projectMapper.selectById(id);
		String sql = "select * from company a left join projectcompany b on a.id = b.companyid left join "
				+ "project c on b.projectid = c.id where c.id = "+ id ;
		List<Company> tem_company = companyMapper.selectBySql(sql);
		Company company = tem_company.get(0);
		
		//判断该用户是否有编辑权限
		int editpri = 0;
		String editprisql = "select DISTINCT a.* from users a left join userrole b on a.id = b.userid left join rolemenu c on b.roleid = c.roleid where c.menuid ='90000202' and a.id = " + loginUserId;
		List<User> user2 = userMapper.selectBySql(editprisql);
		if(user2!=null && user2.size()>0){
			editpri =1;
		}
		jsonMsg = new JsonMsgBean(project, company, editpri);
		
//		jsonMsg = new JsonMsgBean(project, company);
		
		return jsonMsg;
	}
	
	@RequestMapping(value = "/project/companyshow")
	@ResponseBody public Company CompanyShow(Integer id){
		Company company = companyMapper.selectById1(id);
		return company;
	}
	
	@RequestMapping(value = "project/disableProject", method = RequestMethod.POST)
	public String disableProject(String id, Model model ,  @ModelAttribute("userProfile") UserProfile user){
		JsonMsgBean jsonMsg = null;
		try {
			String projectID_array[] = id.split(",");
			String disableProjectIds = "";
			for(String projectId : projectID_array){
				if(disableProjectIds.equals(""))
				{
					disableProjectIds += projectId;
				}
				else{
					disableProjectIds += ",";
					disableProjectIds += projectId;
				}
			}
		int result	= projectService.disableProject(disableProjectIds , user);
		if(result == 1){
			this.logger.info("所选项目关闭成功：{}", id);
			jsonMsg = new JsonMsgBean(0, CVal.Success, "");		
		}else if(result == -1){
			this.logger.info("所选项目下有订单关联,不能关闭：{}", id);
			jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选项目因与订单关联,不能关闭");
		}else{
			this.logger.info("所选项目部分关闭成功,还有部分因与订单关联,不能关闭：{}", id);
			jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选项目部分关闭成功,还有部分因与订单关联,不能关闭");
		}
//			this.logger.info("成功禁用了以下项目：{}", disableProjectIds);
//			jsonMsg = new JsonMsgBean(0, CVal.Success, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("关闭以下项目\"{" + id + "}\"时异常", e);
			jsonMsg = new JsonMsgBean(0, CVal.Exception, "关闭项目异常");
		}
		
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";	
	}
	
	
}
