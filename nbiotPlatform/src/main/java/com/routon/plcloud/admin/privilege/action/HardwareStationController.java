package com.routon.plcloud.admin.privilege.action;

import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.privilege.service.HardwarestationService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.HardwareStation;
import com.routon.plcloud.common.model.OperatingSystem;
import com.routon.plcloud.common.persistence.HardwareStationMapper;
import com.routon.plcloud.common.persistence.OperatHardwareMapper;
import com.routon.plcloud.common.service.MessageServiceImpl;
import com.routon.plcloud.common.utils.JsonMsgBean;

/**
 * 
 * @author huanggang
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class HardwareStationController {

	private Logger logger = LoggerFactory.getLogger(RoleController.class);

	private final String RMPATH = "/hardwarestation/";
	
	@Autowired
	private HardwareStationMapper hardwarestationMapper;
	
	@Autowired
	private OperatHardwareMapper operathardwareMapper;

	@Resource(name = "hardwarestationServiceImpl")
	private HardwarestationService hardwarestationService;

	@Resource(name = "messageServiceImpl")
	protected MessageServiceImpl messageService;

	@Autowired
	private TaskService taskService;

	@RequestMapping(value = RMPATH + "list")
	public String list(HttpServletRequest request, HardwareStation queryCondition,
			@ModelAttribute("userProfile") UserProfile user, Model model, HttpSession session) {

		logger.debug("list");
		try {

			Long loginUserId = user.getCurrentUserId();

			List<OperatingSystem> opsystems = hardwarestationService.queryAllSystemVersion();

			int page = NumberUtils.toInt(request.getParameter("page"), 1);
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 10);
			int startIndex = (page - 1) * pageSize;

			String username = user.getCurrentUserRealName();

			List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();

			PagingBean<HardwareStation> pagingBean = hardwarestationService.paging(startIndex, pageSize,
					request.getParameter("sort"), request.getParameter("dir"), queryCondition, null, null, loginUserId,
					request.getParameter("exportflag") != null && request.getParameter("exportflag").equals("true")
							? true : false);

			int maxpage = (int) Math.ceil(pagingBean.getTotalCount() / (double) pageSize);
			if (pagingBean.getTotalCount() == 0) {
				maxpage = 0;
			}

			if (StringUtils.isNotBlank(queryCondition.getHardwarestationName())) {
				model.addAttribute("HardwarestationName", queryCondition.getHardwarestationName().trim());
			}

			model.addAttribute("opsystems", opsystems);
			model.addAttribute("maxpage", maxpage);
			model.addAttribute("page", page);
			model.addAttribute("pageList", pagingBean);
			model.addAttribute("List", taskQuery);
		} catch (Exception e) {
			logger.error("错误", e);
		}

		return "hardwarestation/hardwareShow";
	}
	
	@RequestMapping(value = RMPATH + "queryAllOpsystem")
	@ResponseBody public List<String> queryOpsystem(String hardwarestationId){
		
		List<String> opsystemNames=new ArrayList<String>();
		
		if (hardwarestationId != null) {
			
			opsystemNames = operathardwareMapper.selectBySql2("select b.operatingsystemname FROM operaterhardware a LEFT JOIN "
					+ "operatingsystem b on a.operatingsystemid=b.id where a.hardwarestationid =" + hardwarestationId);
			
		}
		
		return opsystemNames;
	}

	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hardwarestation/add")
	public String add(HardwareStation hardwarestation, Model model, Integer updateId, String hardwarestationName, String opsystemIds, @ModelAttribute("userProfile") UserProfile user) {

		JsonMsgBean jsonMsg = null;
		if (updateId == null) {
			Long id = hardwarestationService.add(hardwarestation,hardwarestationName.trim(), opsystemIds, user);
			if (id > 0) {

				jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
			} else {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败!");
			}
		} else {

			Long id = hardwarestationService.edit(updateId, hardwarestationName.trim(), opsystemIds ,user);
			if (id > 0) {

				jsonMsg = new JsonMsgBean(0, CVal.Success, "修改成功!");
			} else {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "修改失败!");
			}
		}

		model.addAttribute("jsonMsg", jsonMsg);

		return "common/jsonTextHtml";
	}
	
	/**
	 * 回填input框值及复选框里的值
	 */
	@RequestMapping(value = RMPATH + "querybyId")
	@ResponseBody public JsonMsgBean querybyId(Long id,Model model){
		
		HardwareStation hardware=hardwarestationService.getHardwareByID(id);
		
		HashSet<Long> hardwareOpsystemIdSet = hardware.getHardwareIdset();
		
		List<OperatingSystem> opsystems = hardwarestationService.queryAllSystemVersion();
		for (OperatingSystem opsystem : opsystems) {
			if (hardwareOpsystemIdSet.contains(opsystem.getId())) {
				opsystem.setChecked(true);
			}
		
		}
		
		JsonMsgBean jsonMsg = null;
		hardware = hardwarestationMapper.selectById(id);
		jsonMsg = new JsonMsgBean(hardware, opsystems);
		return jsonMsg;
	}
	
	/**
	 * 删除硬件平台
	 * 
	 * @param hardwareIds
	 *            待删除硬件平台的ID
	 * @return ControllerResult 
	 * 			      处理结果
	 */
	@RequestMapping(value = "hardwarestation/delete", method = RequestMethod.POST)
	public String delHardware(String id, Model model, @ModelAttribute("userProfile") UserProfile optUser) {
		JsonMsgBean jsonMsg = null;
		try {
			int result = hardwarestationService.delete(id, optUser);

			if (result == 1) {
				this.logger.info("所选硬件平台删除成功：{}", id);
				jsonMsg = new JsonMsgBean(0, CVal.Success, "");		
				
			} else if (result == -1) {
				this.logger.info("所选硬件平台因关联不能删除：{}", id);
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选硬件平台因关联不能删除!");
				
			} else {
				this.logger.info("所选硬件平台部分删除成功,还有部分因关联不能删除：{}", id);

				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选硬件平台部分删除成功,还有部分因关联不能删除!");
				
			}

		} catch (Exception e) {
			logger.error("删除以下硬件平台\"{" + id + "}\"时异常", e);
			jsonMsg = new JsonMsgBean(0, CVal.Exception, "删除角色异常!");
			
		}
		model.addAttribute("jsonMsg", jsonMsg);	
		return "common/jsonTextHtml";	
	}

}
