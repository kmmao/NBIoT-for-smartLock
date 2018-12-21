package com.routon.plcloud.admin.software.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.hardware.service.HardwareService;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.software.service.SoftwareService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.Software;
import com.routon.plcloud.common.utils.JsonMsgBean;

import net.sf.json.JSONArray;

@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class SoftwareController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SoftwareService softwareService;
	
	@Autowired
	private HardwareService hardwareService;
	
	@RequestMapping(value="/software/show")
	public String list(HttpServletRequest request, @ModelAttribute("userProfile") UserProfile user, Model model,
			String softwareName, String softwareVersion){
		
		
		int page = NumberUtils.toInt(request.getParameter("page"), 1);
		int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),
				10);
		int startIndex = (page - 1) * pageSize;
		String username = user.getCurrentUserRealName();
		List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();
		
		PagingBean<Software> pageBean = softwareService.queryALL(startIndex, pageSize, softwareName, softwareVersion);
		List<String> hardwareStationList = hardwareService.queryAllHardwareStation();
		List<String> operatingSystemList = hardwareService.queryAllSystemVersion();
		List<String> hardwareProductList = hardwareService.queryAllHardwareProduct();
		pageBean.setStart(startIndex);
		pageBean.setLimit(pageSize);
		int maxpage = (int) Math.ceil(pageBean.getTotalCount()
				/ (double) pageSize);
		if (pageBean.getTotalCount() == 0) {
			maxpage = 0;
		}
		
		//test mode in 2017/10/26
		List<TreeBean> menuTreeBeansResult = new ArrayList<TreeBean>();
		List<TreeBean> menuTreeBeans = new ArrayList<TreeBean>();
		List<TreeBean> menutreeOrder1List = new ArrayList<TreeBean>();
		List<TreeBean> menutreeOrder2List = new ArrayList<TreeBean>();
		TreeBean treeALL = new TreeBean();
		treeALL.setName("浙江广安");
		treeALL.setId(Long.parseLong("10"));
		treeALL.setPid(Long.parseLong("0"));
		TreeBean tree1 = new TreeBean();
		tree1.setName("项目1");
		tree1.setPid(Long.parseLong("10"));
		tree1.setId(Long.parseLong("1001"));
		TreeBean tree2 = new TreeBean();
		tree2.setName("项目2");
		tree2.setPid(Long.parseLong("10"));
		tree2.setId(Long.parseLong("1002"));
		TreeBean tree1Order1 = new TreeBean();
		tree1Order1.setName("订单1");
		tree1Order1.setPid(Long.parseLong("1001"));
		tree1Order1.setId(Long.parseLong("100101"));
		//tree1Order1.setChecked(true);
		TreeBean tree1Order2 = new TreeBean();
		tree1Order2.setName("订单2");
		tree1Order2.setPid(Long.parseLong("1001"));
		tree1Order2.setId(Long.parseLong("100102"));
		
		TreeBean tree2Order1 = new TreeBean();
		tree2Order1.setName("订单1");
		tree2Order1.setPid(Long.parseLong("1002"));
		tree2Order1.setId(Long.parseLong("100201"));
		TreeBean tree2Order2 = new TreeBean();
		tree2Order2.setName("订单2");
		tree2Order2.setPid(Long.parseLong("1002"));
		tree2Order2.setId(Long.parseLong("100202"));
		//tree2Order2.setChecked(true);
		menutreeOrder1List.add(tree1Order1);
		menutreeOrder1List.add(tree1Order2);
		
		menutreeOrder2List.add(tree2Order1);
		menutreeOrder2List.add(tree2Order2);
		tree1.setChildren(menutreeOrder1List);
		tree2.setChildren(menutreeOrder2List);
		
		menuTreeBeans.add(tree1);
		menuTreeBeans.add(tree2);
		treeALL.setChildren(menuTreeBeans);
		
		menuTreeBeansResult.add(treeALL);
		model.addAttribute("List", taskQuery);
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("page", page);
		model.addAttribute("pageList", pageBean);
		model.addAttribute("hardwareProductList", hardwareProductList);
		model.addAttribute("hardwareStationList", hardwareStationList);
		model.addAttribute("operatingSystemList", operatingSystemList);
		model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeansResult).toString());
		return "software/softwareShow";
		
	}
	
	@RequestMapping(value="/software/queryAdoptedHardwareProduct")
	@ResponseBody public List<String> queryAdoptedHardwareProduct(String softwareId){
		List<String> list = softwareService.queryAdoptedProductName(softwareId);
		return list;
	}
	
	@RequestMapping(value="/software/queryAdoptedHardwarePlatform")
	@ResponseBody public List<String> queryAdoptedHardwarePlatform(String softwareId){
		List<String> list = softwareService.queryAdoptedPlatform(softwareId);
		return list;
	}
	
	@RequestMapping(value="/software/queryAdoptedOS")
	@ResponseBody public List<String> queryAdoptedOS(String softwareId){
		List<String> list = softwareService.queryAdoptedOS(softwareId);
		return list;
	}
	
	@RequestMapping(value = "/software/add")
	public String uploadSoftware(@ModelAttribute("userProfile") UserProfile user, Software software,
			String hardwareProductSelect, String hardwareStationSelect, String OSSelect, Model model){
		JsonMsgBean jsonMsg = null;
		software.setUploadTime(new Date());
		software.setUploadUser(user.getCurrentUserRealName());
		long id = softwareService.save(software, hardwareProductSelect, hardwareStationSelect, OSSelect);
		if (id > 0) {

			jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
		}
		else {
			jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败!");
		}
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}
	
	@RequestMapping(value = "/software/ztree")
	@ResponseBody public List<String> ztreeDemo(){
		List<String> list = new ArrayList<String>();
		list.add("100101");
		list.add("100202");
		return list;
	}
}
