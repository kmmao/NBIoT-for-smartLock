package com.routon.plcloud.admin.hardware.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.hardware.service.HardwareService;
import com.routon.plcloud.admin.privilege.service.RoleService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.HardwareProduct;
import com.routon.plcloud.common.persistence.HardwareProductMapper;
import com.routon.plcloud.common.utils.JsonMsgBean;

/**
 * 
 * @author wangxiwei
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class HardwareController {
	
	 @Autowired
	 private RuntimeService runtimeService;
	 
	 @Autowired
	 private RoleService roleService;
	 
	 @Autowired
	 private TaskService taskService;
	 
	 @Autowired
	 private HardwareService hardwareService;
	 
	 @Autowired
	 private HardwareProductMapper hardwareProductMapper;
	
	@RequestMapping(value = "/hardware/show")
	public String list(HttpServletRequest request,
			@ModelAttribute("userProfile")
			UserProfile user, Model model, HttpSession session, HardwareProduct queryCondition) {
		
		Long loginUserId = user.getCurrentUserId();	
		int page = NumberUtils.toInt(request.getParameter("page"), 1);
		int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),
				10);
		int startIndex = (page - 1) * pageSize;
		
		ProcessInstanceQuery querylist = runtimeService.createProcessInstanceQuery()
				.orderByProcessInstanceId().desc().active();
		List<ProcessInstance> list = querylist.list();
		
		Map<String,String> map = roleService.queryRoleByUserId(user.getCurrentUserId().toString());
		
		String username = user.getCurrentUserRealName();
		
		List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();
		
		PagingBean<HardwareProduct> hardwarelist = hardwareService.quryAll(startIndex, pageSize,request.getParameter("sort"),
				request.getParameter("dir"), queryCondition , null, null,
				loginUserId,request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
		
		List<String> productAll = null;
		
/*		if(productName != null){
			productAll =  hardwareProductMapper.selectBySql2("select a.hardware_station from hardwareproduct a where a.hardware_product_name like '%" + productName + "%'");
		} else{
			productAll =  hardwareProductMapper.selectBySql2("select a.hardware_station from hardwareproduct a");
		}
		
		HashSet<String> hardwareSet = new HashSet<String>();
		
		for(int i = 0; i < hardwarelist.getTotalCount(); i++){
			hardwareSet.add(productAll.get(i));
		}*/
		
		List<String> hardwareStationlist = hardwareService.queryAllHardwareStation();
		
		List<String> operatingSystemVersion = hardwareService.queryAllSystemVersion();
		
		hardwarelist.setStart(startIndex);
		hardwarelist.setLimit(pageSize);
		int maxpage = (int) Math.ceil(hardwarelist.getTotalCount()
				/ (double) pageSize);
		if (hardwarelist.getTotalCount() == 0) {
			maxpage = 0;
		}
		
		if(StringUtils.isNotBlank(queryCondition.getHardwareProductName())){
			model.addAttribute("productName", queryCondition.getHardwareProductName().trim());
		}

		model.addAttribute("maxpage", maxpage);
		model.addAttribute("page", page);
		model.addAttribute("pageList", hardwarelist);
		model.addAttribute("List", taskQuery);
		model.addAttribute("hardwarelist", hardwareStationlist);
		model.addAttribute("systemVersionlist", operatingSystemVersion);
		return "hardware/productShow";
	}
	
	@RequestMapping(value = "/hardware/add")
	public String add(Model model, String productNameTable, String erpCode, String hardwareVersion, String hardwarePlatform,
			String systemVersion, boolean edit, Integer updateId){
		JsonMsgBean jsonMsg = null;
		if(updateId == null){
			
			Long id = hardwareService.save(productNameTable, erpCode, hardwareVersion, hardwarePlatform, systemVersion);
			if (id > 0) {

				jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功");
			}
			else if (id == -1){
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "硬件产品名称已经存在，请重新输入");
			}
			else if (id == -2){
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "ERP编码已经存在，请重新输入");
			}
			else {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败");
			}
		} else{
			
			Long id = hardwareService.edit(updateId, productNameTable, erpCode, hardwareVersion, hardwarePlatform, systemVersion);
			if (id > 0) {

				jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功");
			}
			else if (id == -1){
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "硬件产品名称已经存在，请重新输入");
			}
			else if (id == -2){
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "ERP编码已经存在，请重新输入");
			}
			else {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败");
			}
		}

		model.addAttribute("jsonMsg", jsonMsg);

		return "common/jsonTextHtml";
	}
	
	@RequestMapping(value = "/hardware/querybyId")
	@ResponseBody public HardwareProduct queryByID(Integer id){
		HardwareProduct hardwareProduct = hardwareProductMapper.selectById(id);
		return hardwareProduct;
	}
	
	@RequestMapping(value = "/hardware/delete.do")
	public String deleteProduct(Model model, @ModelAttribute("userProfile") UserProfile optUser, String id){
		JsonMsgBean jsonMsg = null;
//		try {
//			hardwareProductMapper.deleteById(id);
//			jsonMsg = new JsonMsgBean(0, CVal.Success,
//					"删除成功!");
//		} catch (Exception e) {
//			jsonMsg = new JsonMsgBean(0, CVal.Fail,
//					"删除失败!");
//			e.printStackTrace();
//		}
		try {
			int result = hardwareService.delete( id , optUser);
			if (result == 1) {
				//this.logger.info("所选角色删除成功：{}", id);
				jsonMsg = new JsonMsgBean(0, CVal.Success, "");			
			} 
			else if(result == -1){
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "删除失败");
			}
			else{
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "部分删除成功，部分删除失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}
}
