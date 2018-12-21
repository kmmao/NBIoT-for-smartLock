package com.routon.plcloud.admin.privilege.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.privilege.service.OperatingsystemService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.OperatingSystem;
import com.routon.plcloud.common.persistence.OperatingSystemMapper;
import com.routon.plcloud.common.service.MessageServiceImpl;
import com.routon.plcloud.common.utils.JsonMsgBean;

/**
 * 弹框显示新增编辑功能
 * @author huanggang
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class OperatingSystemController {
	
	private Logger logger = LoggerFactory.getLogger(OperatingSystemController.class);
	
	private final String RMPATH = "/operatingsystem/";
	
	@Resource(name = "operatingsystemServiceImpl")
	private OperatingsystemService operatingsystemService;
	
	@Resource(name = "messageServiceImpl")
	protected MessageServiceImpl messageService;
	
//	@Autowired
//	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private OperatingSystemMapper operatingsystemMapper;
	
	@RequestMapping(value = RMPATH + "list")
	public String list(HttpServletRequest request, OperatingSystem queryCondition,
			@ModelAttribute("userProfile")
			UserProfile user, Model model, HttpSession session) {
		
		logger.debug("list");
		
		try{
			Long loginUserId = user.getCurrentUserId();
			
			int page = NumberUtils.toInt(request.getParameter("page"), 1);
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),10);
			int startIndex = (page - 1) * pageSize;
			
//			ProcessInstanceQuery querylist = runtimeService.createProcessInstanceQuery().orderByProcessInstanceId().desc().active();
//			List<ProcessInstance> list = querylist.list();
			
			String username = user.getCurrentUserRealName();
			
			List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();
			
			PagingBean<OperatingSystem> pagingBean=operatingsystemService.paging(
					startIndex, pageSize, request.getParameter("sort"), 
					request.getParameter("dir"), queryCondition, null, null, loginUserId, 
					request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
			
			int maxpage = (int)Math.ceil(pagingBean.getTotalCount()/(double)pageSize);
			
			if (pagingBean.getTotalCount() == 0) {
				maxpage = 0;
			}
			
			if(StringUtils.isNotBlank(queryCondition.getOperatingsystemname())){
				model.addAttribute("operatingsystemname", queryCondition.getOperatingsystemname().trim());
			}
			
			model.addAttribute("maxpage", maxpage);
			model.addAttribute("page", page);
			model.addAttribute("pageList", pagingBean);
			model.addAttribute("List", taskQuery);
			
		}catch(Exception e){
			logger.error("错误", e);
		}
		return "operatingsystem/opsystemShow";
		
	}
	
	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/operatingsystem/add")
	public String add(Model model, String opsystemName, Integer updateId, @ModelAttribute("userProfile") UserProfile user){
		JsonMsgBean jsonMsg = null;
		if(updateId == null){
			
			Long id = operatingsystemService.save(opsystemName.trim(), user);
			if (id > 0) {

				jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
			}
			else if(id == -2){
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "操作系统名称已经存在，请重新输入!");
			}
			else {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败!");
			}
		} else{
			
			Long id = operatingsystemService.edit(updateId,opsystemName.trim() ,user);
			if (id > 0) {

				jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
			}
			else if(id == -2){
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "操作系统名称已经存在，请重新输入!");
			}
			else {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败!");
			}
		}

		model.addAttribute("jsonMsg", jsonMsg);

		return "common/jsonTextHtml";
	}
	
	@RequestMapping(value = "/operatingsystem/querybyId")
	@ResponseBody public OperatingSystem queryByID(Integer id){
		OperatingSystem opSystem = operatingsystemMapper.selectById(id);
		return opSystem;
	}
	
	@RequestMapping(value = "/operatingsystem/delete.do")
	public String deleteProduct(Model model, String id , @ModelAttribute("userProfile") UserProfile user){
		JsonMsgBean jsonMsg = null;
		try {
			int result = operatingsystemService.delete(id ,user);
			
			if(result == 1){
				jsonMsg = new JsonMsgBean(0, CVal.Success,"删除成功!");
				
			}else if (result == -1) {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选操作系统与其他模块关联,不能删除!");
				
			}else{
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选操作系统部分与其他模块关联,不能删除!");
				
			} 
			
		} catch (Exception e) {
			jsonMsg = new JsonMsgBean(0, CVal.Exception, "删除操作系统异常!");
		}
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}
}



/**
 * 页面显示新增编辑功能
 * @author huanggang
 *
 */
//@Controller
//@SessionAttributes(value = { "userPrivilege", "userProfile" })
//public class OperatingSystemController {
//	
//	private Logger logger = LoggerFactory.getLogger(OperatingSystemController.class);
//	
//	private final String RMPATH = "/operatingsystem/";
//	
//	@Resource(name = "operatingsystemServiceImpl")
//	private OperatingsystemService operatingsystemService;
//	
//	@Resource(name = "messageServiceImpl")
//	protected MessageServiceImpl messageService;
//	
//	@RequestMapping(value = RMPATH + "list")
//	public String list(HttpServletRequest request, OperatingSystem queryCondition,
//			@ModelAttribute("userProfile")
//			UserProfile user, Model model, HttpSession session) {
//		
//		logger.debug("list");
//		
//		try{
//			Long loginUserId = user.getCurrentUserId();
//			
//			int page = NumberUtils.toInt(request.getParameter("page"), 1);
//			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),10);
//			int startIndex = (page - 1) * pageSize;
//			
//			PagingBean<OperatingSystem> pagingBean=operatingsystemService.paging(
//					startIndex, pageSize, request.getParameter("sort"), 
//					request.getParameter("dir"), queryCondition, null, null, loginUserId, 
//					request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
//			
//			int maxpage = (int)Math.ceil(pagingBean.getTotalCount()/(double)pageSize);
//			
//			if (pagingBean.getTotalCount() == 0) {
//				maxpage = 0;
//			}
//			
//			if(StringUtils.isNotBlank(queryCondition.getOperatingsystemname())){
//				model.addAttribute("operatingsystemname", queryCondition.getOperatingsystemname());
//			}
//			
//			model.addAttribute("maxpage", maxpage);
//			model.addAttribute("page", page);
//			model.addAttribute("pageList", pagingBean);
//			
//		}catch(Exception e){
//			logger.error("错误", e);
//		}
//		return "operatingsystem/list";
//		
//	}
//	
//	/**
//	 * 新增
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = RMPATH + "add", method = RequestMethod.GET)
//	public String setupAdd(Model model,@ModelAttribute("userProfile") UserProfile userProfile,HttpServletRequest request){
//		
//		OperatingSystem opsystem=new OperatingSystem();
//		
//		int page = NumberUtils.toInt(request.getParameter("page"), 1);
//		
//		model.addAttribute("page", page);
//		model.addAttribute("opsystem", opsystem);
//		
//		return "operatingsystem/edit";
//	}
//	
//	/**
//	 * 编辑
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value=RMPATH +"edit", method = RequestMethod.GET)
//	public String setupEdit(Model model,@ModelAttribute("userProfile") UserProfile user, 
//			Long id,HttpServletRequest request){
//		
//		OperatingSystem opsystem = operatingsystemService.getOpsystemByOpsystemId(id);
//		
//		int page = NumberUtils.toInt(request.getParameter("page"), 1);
//		model.addAttribute("page", page);
//		model.addAttribute("opsystem", opsystem);
//		
//		return "operatingsystem/edit";
//	}
//	
//	/**
//	 * 删除操作系统
//	 * 
//	 * @param roleIds
//	 *            待删除操作系统的ID
//	 * @param optUser
//	 *            当前操作用户
//	 * @return ControllerResult 处理结果
//	 */
//	@RequestMapping(value = "operatingsystem/delete", method = RequestMethod.POST)
//	public String delOpsystem(String id, @ModelAttribute("userProfile") UserProfile optUser, 
//			Model model) {
//		JsonMsgBean jsonMsg = null;
//		try {
//			int result = operatingsystemService.delete(id, optUser);
//
//			if (result == 1) {
//				this.logger.info("所选操作系统删除成功：{}", id);
//				jsonMsg = new JsonMsgBean(0, CVal.Success, "");		
//				
//			} else if (result == -1) {
//				this.logger.info("删除操作系统失败：{}", id);
//				jsonMsg = new JsonMsgBean(0, CVal.Fail, "删除操作系统失败!");
//				
//			} else {
//				this.logger.info("所选操作系统部分删除成功：{}", id);
//
//				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选操作系统部分删除成功!");
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
//	/**
//	 * 保存
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value=RMPATH + "save", method=RequestMethod.POST)
//	public String save(@Valid OperatingSystem opsystem, BindingResult result, MultipartHttpServletRequest multipartRequest, Model model, 
//			@ModelAttribute("userProfile") UserProfile user) {
//		JsonMsgBean jsonMsg = null;
//		
//		do {
//			try {
//				
//				if (result.hasErrors()) {					
//				
//					jsonMsg = new JsonMsgBean(0, CVal.Fail, messageService.getOneMessage(result,"operatingsystemname"));
//					break;
//				}
//				Long id = null;
//				logger.info("提交");
//				if(opsystem.getId() != null && opsystem.getId() > 0){
//					id = operatingsystemService.edit(opsystem, user);
//				}
//				else {
//					id = operatingsystemService.add(opsystem, user);
//				}
//				
//				if(id>0){
//				
//					jsonMsg = new JsonMsgBean(0, CVal.Success, "");			
//				}else if(id==-2){
//					jsonMsg = new JsonMsgBean(0, CVal.Fail, "操作系统名已经存在!");			
//				}else {
//					jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存操作系统名失败!");
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
//
//}

