package com.routon.plcloud.admin.order.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.TaskService;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.routon.plcloud.admin.activiti.common.ActivitiInfoQuery;
import com.routon.plcloud.admin.order.service.CompanyService;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.privilege.service.RoleService;
import com.routon.plcloud.admin.privilege.service.UserService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.Address;
import com.routon.plcloud.common.model.City;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.Role;
import com.routon.plcloud.common.model.User;
import com.routon.plcloud.common.persistence.AddressMapper;
import com.routon.plcloud.common.persistence.CityMapper;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.UserMapper;
import com.routon.plcloud.common.service.MessageServiceImpl;
import com.routon.plcloud.common.utils.JsonMsgBean;

/**
 * 
 * @author wangzhuo
 *
 */

@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class CompanyController {
	private Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	private final String RMPATH = "/company/";
	
	@Resource(name = "companyServiceImpl")
	private CompanyService companyService;
	
	@Autowired
	private UserMapper userMapper ;
	
	@Autowired
	private CompanyMapper companyMapper ;
	
	@Autowired
	private CityMapper cityMapper ;
	
	@Autowired
	private AddressMapper addressMapper ;
	 
	@Autowired
	private TaskService taskService;
	
	@Resource(name = "messageServiceImpl")
	protected MessageServiceImpl messageService;
	
	@Resource(name = "ActivitiInfoQueryBean")
	protected ActivitiInfoQuery activitiInfoQuery;
	
	@RequestMapping(value=RMPATH + "list")
	public String list(HttpServletRequest request,
			Company queryCondition, @ModelAttribute("userProfile") UserProfile user,Model model,HttpSession session){

		logger.debug("list");
		try {
			
			//UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
			
			Long loginUserId = user.getCurrentUserId();
			
			
			int page = NumberUtils.toInt(request.getParameter("page"), 1);
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 10);
			int startIndex = (page - 1) * pageSize;			 
	        
	    	PagingBean<Company> pagingBean = companyService.paging(
	    			startIndex, pageSize, request.getParameter("sort"),
	    			request.getParameter("dir"), queryCondition, null, null, loginUserId,request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
	        
			String username = user.getCurrentUserRealName();
			
			//List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();
	        
			int maxpage = (int)Math.ceil(pagingBean.getTotalCount()/(double)pageSize);
			if (pagingBean.getTotalCount() == 0) {
				maxpage = 0;
			}
			if(StringUtils.isNotBlank(queryCondition.getCompanyname())){
				model.addAttribute("companyname", queryCondition.getCompanyname().trim());
			}
			
			//activitiInfoQuery.QueryActivitiInfo(model, user.getCurrentUserId().toString(), page, startIndex, pageSize);
			List<Company> companys = pagingBean.getDatas();
			for(Company systemorder : companys){
				long userid = Long.valueOf(systemorder.getSalename());
				String sql = "select a.* from users a where a.id = "+ userid;
				List<User> tem_user = userMapper.selectBySql(sql);
				String realename = tem_user.get(0).getRealName();
		 		systemorder.setRealename(realename);
		 		
//		 		String addressid = systemorder.getAddress();
//		 		String addressname = addressMapper.selectByaddress(addressid);
//		 		systemorder.setAddressname(addressname);
			}
			pagingBean.setDatas(companys);
			
			model.addAttribute("maxpage", maxpage);
			model.addAttribute("page", page);
			//model.addAttribute("List", taskQuery);
			model.addAttribute("pageList", pagingBean);
			
			//addCatalogAttribute(catalogId, model);
		}
		catch (Exception e) {
			logger.error("错误", e);
		}
			
		return "company/list";
	}
	
	
	
	/**
	 * 6.2.12 新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value=RMPATH +"add", method = RequestMethod.GET)
	public String setupAdd(Model model,@ModelAttribute("userProfile") UserProfile user,HttpServletRequest request){
		
		Company company = new Company();
//		HashSet<String> users = new HashSet<String>();
		Map<Long,String> users = new HashMap<Long,String>();
//		String sql = "select a.* from users a where a.status = 1 and a.company = '普利商用'  ";
		String sql = "select a.* from users a left join userrole ur on a.id = ur.userid left join role r on ur.roleid = r.id where a.status = 1 "
				+ "and r.name = '销售助理' ";
		List<User> tem_user = userMapper.selectBySql(sql);
		for(int i=0; i<tem_user.size();i++){
//			users.add(tem_user.get(i).getRealName());
			Long key = tem_user.get(i).getId();
			String value = tem_user.get(i).getRealName();
			users.put(key, value);
		}
		
//		for(int i=0; i<tem_user.size();i++){
//			users.add(tem_user.get(i).getRealName());
//		}
	//	Long loginUserId = user.getCurrentUserId();
		
	//	List<TreeBean> menuTreeBeans = roleService.getMenuTrees(loginUserId, null);
		Map<String, String> provinces = companyService.Threelevellinkage("110000", "province");
		
		int page = NumberUtils.toInt(request.getParameter("page"), 1);
		model.addAttribute("page", page);
		model.addAttribute("users", users);
//		model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeans).toString());
		model.addAttribute("company", company);
		model.addAttribute("provinces", provinces);
		return "company/edit";
	}
	
	@RequestMapping(value=RMPATH +"edit", method = RequestMethod.GET)
	public String setupEdit(Model model,@ModelAttribute("userProfile") UserProfile user, Long id,HttpServletRequest request){
	
		Long loginUserId = user.getCurrentUserId();
		Company company = companyService.getCompanyByCompanyId(id);
//		String sql = "select a.* from users a where a.status = 1 and a.company = '普利商用'  ";
		String sql = "select a.* from users a left join userrole ur on a.id = ur.userid left join role r on ur.roleid = r.id where a.status = 1 "
				+ "and r.name = '销售助理' ";
		List<User> tem_user = userMapper.selectBySql(sql);
		
//		HashSet<String> users = new HashSet<String>();
		Map<Long,String> users = new HashMap<Long,String>();
		for(int i=0; i<tem_user.size();i++){
//			users.add(tem_user.get(i).getRealName());
			Long key = tem_user.get(i).getId();
			String value = tem_user.get(i).getRealName();
			users.put(key, value);
		}
		int editpri = 0;
		String editprisql = "select DISTINCT a.* from users a left join userrole b on a.id = b.userid left join rolemenu c on b.roleid = c.roleid where c.menuid ='90000102' and a.id = " + loginUserId;
		List<User> user2 = userMapper.selectBySql(editprisql);
		if(user2!=null && user2.size()>0){
			editpri =1;
		}
		
//		for(int i=0; i<tem_user.size();i++){
//			users.add(tem_user.get(i).getRealName());
//		}
		
	//	Long loginUserId = user.getCurrentUserId();
		
	//	List<TreeBean> menuTreeBeans = roleService.getMenuTrees(loginUserId, id);
		Map<String, String> provinces = companyService.Threelevellinkage("110000", "province");
		
		int page = NumberUtils.toInt(request.getParameter("page"), 1);
		model.addAttribute("page", page);
		model.addAttribute("editpri", editpri);
	//	model.addAttribute("menuTreeBeans", JSONArray.fromObject(menuTreeBeans).toString());
		model.addAttribute("company", company);
		model.addAttribute("users", users);
		model.addAttribute("provinces", provinces);
		return "company/edit";
	}
	
	@RequestMapping(value = "/company/provinceshow",produces={"text/html;charset=UTF-8;","application/json;"})
//	@ResponseBody public Map<String, String> provinceShow(String code, String grade){
	@ResponseBody public String provinceShow(Model model,String code, String grade){
//		System.out.println("grade="+grade);
//        System.out.println("code="+code);
        
//        ArrayList<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
		if(grade.equals("city")){
			map = new HashMap<String, String>();
			String sqlcity = "select * from city where code_p = '" + code + "' order by code_c";
			List<City> citys = cityMapper.selectBySql(sqlcity);
			for(int i=0; i< citys.size();i++){
				String code_c = citys.get(i).getCode_c();
				String name = citys.get(i).getName();
				map.put(code_c, name);
			}
//			rsList.add(map);
		}else if(grade.equals("area")){ 
			map = new HashMap<String, String>();
			String sqlarea = "select * from address where code_c = '" + code + "' order by code_a";
			List<Address> areas = addressMapper.selectBySql(sqlarea);
			for(int i=0; i< areas.size();i++){
				String code_a = areas.get(i).getCode_a();
				String name = areas.get(i).getName();
				map.put(code_a, name);
			}
//			rsList.add(map);
		}
		
//		String json ="";
//		if(rsList !=null &&rsList.size()>0){
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("map", rsList);
//			json = jsonObject.toString();
//		}
//		
//		JSONObject jsStr = JSONObject.fromObject(json);
////		String value = jsStr.getString("map");
//		System.out.println(jsStr.getString("map"));
//		return jsStr.getString("map");
//		model.addAttribute("map", map);
//        return "company/edit";
		ObjectMapper mapper = new ObjectMapper();  
        String json = null;
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}  
//		System.out.println(json);
        return json;  
//        return map;
	}
	
//	@RequestMapping(value = "/company/companyshow")
//	@ResponseBody public JsonMsgBean companyshow(Integer id){
//		JsonMsgBean jsonMsg = null;
//		
//		Company company = companyMapper.selectById1(id);
//		String cityid = company.getCity();
//		String addressid = company.getAddress();
//		String cityname = null;
//		String addressname = null;
//		
//		if(cityid != null){
//			cityname = cityMapper.selectBycity(cityid);
//		}
//		if(addressid != null){
//			addressname = areaMapper.selectByaddress(addressid);
//		}
//		
//		jsonMsg = new JsonMsgBean(cityname, addressname);
//		return jsonMsg;
//	}
	
	
	/**
	 * 删除公司
	 * 
	 * @param roleIds
	 *            待删除公司的ID
	 * @param optUser
	 *            当前操作用户
	 * @return ControllerResult 处理结果
	 */
	@RequestMapping(value = "company/delete", method = RequestMethod.POST)
	public String delCompany(String id, @ModelAttribute("userProfile") UserProfile optUser, Model model) {
		JsonMsgBean jsonMsg = null;
		try {
			int result = companyService.delete(id, optUser);

			if (result == 1) {
				this.logger.info("所选公司删除成功：{}", id);
				jsonMsg = new JsonMsgBean(0, CVal.Success, "");		
				
			} else if (result == -1) {
				this.logger.info("所选公司下有项目关联,不能删除：{}", id);
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选公司因与项目关联,不能删除");
				
			} else {
				this.logger.info("所选公司部分删除成功,还有部分因与项目关联,不能删除：{}", id);

				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选公司部分删除成功,还有部分因与项目关联,不能删除");
				
			}

		} catch (Exception e) {
			logger.error("删除以下公司\"{" + id + "}\"时异常", e);
			jsonMsg = new JsonMsgBean(0, CVal.Exception, "删除公司异常");
			
		}
		model.addAttribute("jsonMsg", jsonMsg);	
		return "common/jsonTextHtml";	
	}
	
	
	
	@RequestMapping(value=RMPATH + "save", method=RequestMethod.POST)
	public String save(@Valid Company company, BindingResult result, MultipartHttpServletRequest multipartRequest, Model model, 
			@ModelAttribute("userProfile") UserProfile user,HttpServletRequest request) {
		JsonMsgBean jsonMsg = null;
		do {
			try {
				
//				if (result.hasErrors()) {					
//				
//					jsonMsg = new JsonMsgBean(0, CVal.Fail, messageService.getOneMessage(result,"companyname"));
//					break;
//				}
				Long id = null;
				logger.info("提交");
				if(company.getId() != null && company.getId() > 0){
					id = companyService.edit(company,  user);
				}
				else {
					id = companyService.add(company,  user);
				}
				
				if(id>0){
				
					jsonMsg = new JsonMsgBean(0, CVal.Success, "");			
				}else if(id==-2){
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "公司名已经存在");			
				}else if(id==-3){
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "公司注册名称已经存在");			
				}else {
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存公司失败");
				}
				
			} catch (Exception e) {
				logger.error("公司保存异常", e);
				jsonMsg = new JsonMsgBean(0, CVal.Exception, "保存失败，请稍候重试");
			}
		}
		while(false);

		model.addAttribute("jsonMsg", jsonMsg);	
		
		return "common/jsonTextHtml";		
	}
	
	
}
