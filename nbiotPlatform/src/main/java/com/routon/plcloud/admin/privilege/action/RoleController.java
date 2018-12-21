package com.routon.plcloud.admin.privilege.action;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.privilege.service.RoleService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.Role;
import com.routon.plcloud.common.model.RoleMenu;
import com.routon.plcloud.common.persistence.RoleMenuMapper;
import com.routon.plcloud.common.service.MessageServiceImpl;
import com.routon.plcloud.common.utils.JsonMsgBean;

 
/**
 * 
 * @author huanggang
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class RoleController {
	private Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	private final String RMPATH = "/role/";
	
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;
	
	@Resource(name = "messageServiceImpl")
	protected MessageServiceImpl messageService;	
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Autowired
	private IdentityService identityService;
	
	/**
	 * 弹框显示实现方法
	 * @param request
	 * @param queryCondition
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value=RMPATH + "list")
	public String list(HttpServletRequest request,
			Role queryCondition, @ModelAttribute("userProfile") UserProfile user,Model model,HttpSession session){

		logger.debug("list");
		try {
			
			Long loginUserId = user.getCurrentUserId();
			
			int page = NumberUtils.toInt(request.getParameter("page"), 1);
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 10);
			int startIndex = (page - 1) * pageSize;	
			
			String username = user.getCurrentUserRealName();
			
/*	        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().active().orderByDeploymentId().desc();
	        List<ProcessDefinition> list = query.list();
			
			List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();*/
	        
	    	PagingBean<Role> pagingBean = roleService.paging(
	    			startIndex, pageSize, request.getParameter("sort"),
	    			request.getParameter("dir"), queryCondition, null, null, loginUserId,request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
	        
	 
	        
			int maxpage = (int)Math.ceil(pagingBean.getTotalCount()/(double)pageSize);
			if (pagingBean.getTotalCount() == 0) {
				maxpage = 0;
			}
			if(StringUtils.isNotBlank(queryCondition.getRolename())){
				model.addAttribute("rolename1", queryCondition.getRolename().trim());
			}
			
			List<TreeBean> menuTreeBeans =roleService.getMenuTrees(loginUserId, null);
			
			//model.addAttribute("pageList_process", list);
			model.addAttribute("maxpage", maxpage);
			model.addAttribute("page", page);
			model.addAttribute("pageList", pagingBean);
			//model.addAttribute("List", taskQuery);
			model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeans).toString());
		}
		catch (Exception e) {
			logger.error("错误", e);
		}
			
		return "role/roleShow";
	}
	
	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/role/add")
	public String add(@Valid Role role,BindingResult result,Model model,@ModelAttribute("userProfile") UserProfile user,Integer updateId){
		
		JsonMsgBean jsonMsg = null;
		do{
//			if (result.hasErrors()) {					
//			
//				jsonMsg = new JsonMsgBean(0, CVal.Fail, messageService.getOneMessage(result,"name","menuIds"));
//				break;
//			}
		
			if(updateId == null){
				Long id = roleService.add(role, role.getMenuIds(), user);
				if (id > 0) {

					jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
				}
				else if(id == -2){
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "角色名称已经存在!");
				}
				else{
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败!");
				}
			} else{
			
				Long id = roleService.edit(updateId,role, role.getMenuIds(), user);
				if (id > 0) {

					jsonMsg = new JsonMsgBean(0, CVal.Success, "");
				}
				else if(id == -2){
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "角色名称已经存在!");
				}
				else {
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "修改失败!");
				}
			}
		}
		while(false);

		model.addAttribute("jsonMsg", jsonMsg);

		return "common/jsonTextHtml";
	}
	
	/**
	 * 回填框里的值
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/role/querybyId")
	@ResponseBody public Role queryByID(Long id){
		
//		Long loginUserId = user.getCurrentUserId();
		Role role1 = roleService.getRoleByRoleId(id);
//		List<TreeBean> menuTreeBeans = roleService.getMenuTrees(loginUserId, id);
//		int page = NumberUtils.toInt(request.getParameter("page"), 1);
//		model.addAttribute("page", page);
//		model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeans).toString());
//		model.addAttribute("role", role);
		
//		Role role1 = roleMapper.selectById(id);
		return role1;
	}
	
	/**
	 * 回填ztree树里的值
	 * @param model
	 * @param user
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/role/queryChecked")
	@ResponseBody public List<Long> queryChecked(Model model,@ModelAttribute("userProfile") UserProfile user,Long id,HttpServletRequest request){
		
		List<Long> treeids=new ArrayList<Long>();
		
		if (id != null) {
//			List<RoleMenu> checked_systemmenuroles = roleMenuMapper.selectBySql("select b.* from rolemenu b where b.roleId =" + id);
			
			List<RoleMenu> checked_systemmenuroles = roleMenuMapper.selectBySql("select b.* from rolemenu b LEFT JOIN menu m on b.menuid=m.id "
					+ "where m.id not in(400001,400002,400004,400005,900001,900002,900003,900004,900005) and b.menuid>10000 and b.roleId =" + id);
			
			for(int i=0;i<checked_systemmenuroles.size();i++){
				RoleMenu rm=checked_systemmenuroles.get(i);
				treeids.add(rm.getMenuID());
//				if(rm.getMenuID() > 10000){
//					treeids.add(rm.getMenuID());
//				}
			}
			
//			if (checked_systemmenuroles != null) {
//	
//				for (RoleMenu systemmenurole : checked_systemmenuroles) {
//					tree1.add(systemmenurole.getMenuID());
//				}
//			}
		}
		
		return treeids;
		
//		Long pid=tree1.get(0);
//		
//		int count=menuMapper.selectCount(pid);
		
//		tree1.remove(0);
//		for(int i=0;i<tree1.size();i++){
//			treeids.add(tree1.get(i));
//		}
		
//		if(tree1.size()==count){
//			tree1.remove(0);
//			for(int i=0;i<tree1.size();i++){
//				treeids.add(tree1.get(i));
//			}
//		}else{
//			treeids.add(pid);
//		}
		
//		Long loginUserId = user.getCurrentUserId();
//		List<TreeBean> menuTreeBeans = roleService.getMenuTrees(loginUserId, id);
//		
//		for(TreeBean tree:menuTreeBeans){
//			if(tree.isChecked()){
//				listtreeid.add(tree.getId());
//			}
//		}
		
//		int page = NumberUtils.toInt(request.getParameter("page"), 1);
//		model.addAttribute("page", page);
//		model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeans).toString());
//		model.addAttribute("role", role);
//		Role role1 = roleMapper.selectById(id);
	}
	
	
	/**
	 * 删除角色
	 * 
	 * @param roleIds
	 *            待删除角色的ID
	 * @param optUser
	 *            当前操作用户
	 * @return ControllerResult 处理结果
	 */
	@RequestMapping(value = "role/delete", method = RequestMethod.POST)
	public String delRole(String id, @ModelAttribute("userProfile") UserProfile optUser, Model model) {
		JsonMsgBean jsonMsg = null;
		try {
			int result = roleService.delete(id, optUser);

			if (result == 1) {
				this.logger.info("所选角色删除成功：{}", id);
				jsonMsg = new JsonMsgBean(0, CVal.Success, "");		
				
			} else if (result == -1) {
				this.logger.info("所选角色因与用户关联,不能删除：{}", id);
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选角色因与用户关联,不能删除!");
				
			} else {
				this.logger.info("所选角色部分删除成功,还有部分因与用户关联,不能删除：{}", id);

				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选角色部分删除成功,还有部分因与用户关联,不能删除!");
				
			}

		} catch (Exception e) {
			logger.error("删除以下角色\"{" + id + "}\"时异常", e);
			jsonMsg = new JsonMsgBean(0, CVal.Exception, "删除角色异常!");
			
		}
		model.addAttribute("jsonMsg", jsonMsg);	
		return "common/jsonTextHtml";	
	}
	
	
	
//	/**
//	 * 页面显示实现方法
//	 * @param request
//	 * @param queryCondition
//	 * @param user
//	 * @param model
//	 * @param session
//	 * @return
//	 */
//	@RequestMapping(value=RMPATH + "list")
//	public String list(HttpServletRequest request,
//			Role queryCondition, @ModelAttribute("userProfile") UserProfile user,Model model,HttpSession session){
//
//		logger.debug("list");
//		try {
//			
//			//UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
//			
//			Long loginUserId = user.getCurrentUserId();
//			
//			
//			
//			int page = NumberUtils.toInt(request.getParameter("page"), 1);
//			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 10);
//			int startIndex = (page - 1) * pageSize;			 
//	        
//	    	PagingBean<Role> pagingBean = roleService.paging(
//	    			startIndex, pageSize, request.getParameter("sort"),
//	    			request.getParameter("dir"), queryCondition, null, null, loginUserId,request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
//	        
//	 
//	        
//			int maxpage = (int)Math.ceil(pagingBean.getTotalCount()/(double)pageSize);
//			if (pagingBean.getTotalCount() == 0) {
//				maxpage = 0;
//			}
//			if(StringUtils.isNotBlank(queryCondition.getName())){
//				model.addAttribute("name", queryCondition.getName());
//			}
//			
//			model.addAttribute("maxpage", maxpage);
//			model.addAttribute("page", page);
//			model.addAttribute("pageList", pagingBean);
//			
//			//addCatalogAttribute(catalogId, model);
//		}
//		catch (Exception e) {
//			logger.error("错误", e);
//		}
//			
//		return "role/list";
//	}
//	
//	/**
//	 * 6.2.12 新增
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value=RMPATH +"add", method = RequestMethod.GET)
//	public String setupAdd(Model model,@ModelAttribute("userProfile") UserProfile user,HttpServletRequest request){
//		
//		Role role = new Role();
//		Long loginUserId = user.getCurrentUserId();
//		
//		List<TreeBean> menuTreeBeans = roleService.getMenuTrees(loginUserId, null);
//		
//		int page = NumberUtils.toInt(request.getParameter("page"), 1);
//		model.addAttribute("page", page);
//		model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeans).toString());
//		model.addAttribute("role", role);
//		
//		return "role/edit";
//	}
//	
//	@RequestMapping(value=RMPATH +"edit", method = RequestMethod.GET)
//	public String setupEdit(Model model,@ModelAttribute("userProfile") UserProfile user, Long id,HttpServletRequest request){
//		
//		Role role = roleService.getRoleByRoleId(id);
//		
//		Long loginUserId = user.getCurrentUserId();
//		
//		List<TreeBean> menuTreeBeans = roleService.getMenuTrees(loginUserId, id);
//		
//		int page = NumberUtils.toInt(request.getParameter("page"), 1);
//		model.addAttribute("page", page);
//		model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeans).toString());
//		model.addAttribute("role", role);
//		
//		return "role/edit";
//	}
//	
//	/**
//	 * 删除角色
//	 * 
//	 * @param roleIds
//	 *            待删除角色的ID
//	 * @param optUser
//	 *            当前操作用户
//	 * @return ControllerResult 处理结果
//	 */
//	@RequestMapping(value = "role/delete", method = RequestMethod.POST)
//	public String delRole(String id, @ModelAttribute("userProfile") UserProfile optUser, Model model) {
//		JsonMsgBean jsonMsg = null;
//		try {
//			int result = roleService.delete(id, optUser);
//
//			if (result == 1) {
//				this.logger.info("所选角色删除成功：{}", id);
//				jsonMsg = new JsonMsgBean(0, CVal.Success, "");		
//				
//			} else if (result == -1) {
//				this.logger.info("所选角色因与用户关联,不能删除：{}", id);
//				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选角色因与用户关联,不能删除!");
//				
//			} else {
//				this.logger.info("所选角色部分删除成功,还有部分因与用户关联,不能删除：{}", id);
//
//				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选角色部分删除成功,还有部分因与用户关联,不能删除!");
//				
//			}
//
//		} catch (Exception e) {
//			logger.error("删除以下角色\"{" + id + "}\"时异常", e);
//			jsonMsg = new JsonMsgBean(0, CVal.Exception, "删除角色异常!");
//			
//		}
//		model.addAttribute("jsonMsg", jsonMsg);	
//		return "common/jsonTextHtml";	
//	}
//	
//	
//	
//	@RequestMapping(value=RMPATH + "save", method=RequestMethod.POST)
//	public String save(@Valid Role role, BindingResult result, MultipartHttpServletRequest multipartRequest, Model model, @ModelAttribute("userProfile") UserProfile user) {
//		JsonMsgBean jsonMsg = null;
//		
//		do {
//			try {
//				
//				if (result.hasErrors()) {					
//				
//					jsonMsg = new JsonMsgBean(0, CVal.Fail, messageService.getOneMessage(result,"name","menuIds"));
//					break;
//				}
//				Long id = null;
//				logger.info("提交");
//				if(role.getId() != null && role.getId() > 0){
//					id = roleService.edit(role, role.getMenuIds(), user);
//				}
//				else {
//					id = roleService.add(role, role.getMenuIds(), user);
//				}
//				
//				if(id>0){
//				
//					jsonMsg = new JsonMsgBean(0, CVal.Success, "");			
//				}else if(id==-2){
//					jsonMsg = new JsonMsgBean(0, CVal.Fail, "角色名已经存在!");			
//				}else {
//					jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存角色失败!");
//				}
//				
//			} catch (Exception e) {
//				logger.error("角色保存异常", e);
//				jsonMsg = new JsonMsgBean(0, CVal.Exception, "保存失败，请稍候重试!");
//			}
//		}
//		while(false);
//
//		model.addAttribute("jsonMsg", jsonMsg);	
//		
//		return "common/jsonTextHtml";		
//	}
}
