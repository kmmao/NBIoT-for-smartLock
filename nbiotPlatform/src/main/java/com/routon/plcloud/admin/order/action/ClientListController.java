package com.routon.plcloud.admin.order.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.order.service.ClientListService;
import com.routon.plcloud.admin.order.service.TerminalService;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.ClientList;
import com.routon.plcloud.common.model.OrderProject;
import com.routon.plcloud.common.persistence.OrderProjectMapper;
import com.routon.plcloud.common.utils.JsonMsgBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author huanggang
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class ClientListController {
	
	@Autowired
	private ClientListService clientlistService;
	
	@Resource(name = "terminalServiceImpl")
	private TerminalService terminalService;
	
	@Autowired
	private OrderProjectMapper orderprojectMapper;
	
	@RequestMapping(value = "/clientinfo/list")
	public String list(HttpServletRequest request, Long companyid,Long projectid,Long orderid,Long requirementtype,String searchname,
					   @ModelAttribute("userProfile")UserProfile user, Model model, String treeNodeTid){
//	public String list(HttpServletRequest request,ClientList queryCondition, Long companyid,Long projectid,Long orderid,
//			   @ModelAttribute("userProfile")UserProfile user, Model model, String treeNodeTid){
				 
			Long loginUserId = user.getCurrentUserId();	
			int page = NumberUtils.toInt(request.getParameter("page"), 1);
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),10);
			int startIndex = (page - 1) * pageSize;
		
			//获取用户信息管理--公司项目订单树，获取的是别名来显示
//			List<TreeBean> userTreeBeans = clientlistService.getUserTreeByUserId(loginUserId);
			List<TreeBean> userTreeBeans = clientlistService.getUserTreeByUserId(loginUserId,requirementtype,searchname);
			
			//下拉框 选择公司
//			HashSet<String> companynames = new HashSet<String>();
			
			//查询客户信息
			PagingBean<ClientList> pagingBean = null;
			try {
				pagingBean = clientlistService.paging(companyid, projectid, orderid, page, loginUserId);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			pagingBean.setStart(startIndex);
			pagingBean.setLimit(pageSize);
			 pagingBean.setStart(startIndex);
			int maxpage = (int) Math.ceil(pagingBean.getTotalCount() / (double) pageSize);
			if (pagingBean.getTotalCount() == 0) {
				maxpage = 0;
			}
			
			//返回ztree点击节点的id（公司、项目、订单）
			if(companyid !=null){
				model.addAttribute("tmpcompanyid" , companyid);
			}
			if(projectid !=null){
				model.addAttribute("tmpprojectid" , projectid);
			}
			if(orderid !=null){
				model.addAttribute("tmporderid" , orderid);
			}
			
			model.addAttribute("maxpage", maxpage);
			model.addAttribute("page", page);
			model.addAttribute("pageList", pagingBean);
			model.addAttribute("userTreeBeans", JSONArray.fromObject(userTreeBeans).toString());
			model.addAttribute("treeNodeTid", treeNodeTid);
			return "clientInfo/clientlist";
	}
	
	@RequestMapping(value = "/clientinfo/clientinfoShow")
	@ResponseBody public ClientList clientinfoShow(HttpServletRequest request,ClientList client,
			@ModelAttribute("userProfile") UserProfile optUser, Model model){
		
		String ordernum = client.getOrderid();
		if(ordernum != null && ordernum !=""){
			//根据订单号截取出公司id
			String company_id=  ordernum.substring(0, 5);
			//去掉字符串开头的0字符
			int companyidint = Integer.parseInt(company_id);
			//int类型转为String类型
			String companyid = String.valueOf(companyidint);
			//Companyregname是注册名，原始companyname为别名
			String companyname = clientlistService.queryCompanyById(companyid).getCompanyregname();
			client.setCompanyname(companyname);
			
			String sql="select b.* from \"order\" a left join orderproject b on a.id=b.orderid where a.ordernum='" + ordernum + "'";
			List<OrderProject> orderprojects = orderprojectMapper.selectBySql(sql);
			long projectidint = orderprojects.get(0).getProjectid();
			String projectid = String.valueOf(projectidint);
			String projectname = clientlistService.queryProjectById(projectid).getProjectregname();
			client.setProjectname(projectname);
		}
		return client;	
	}
	
	@RequestMapping(value = "/clientinfo/clientinfo")
	public String save(ClientList client, @ModelAttribute("userProfile") UserProfile user, Model model){
		JsonMsgBean jsonMsg = null;
		
		String companyname = client.getCompanyname();
		String projectname = client.getProjectname();
		String ordernum = client.getOrderid();
		
		ArrayList<ClientList> clientlist = new ArrayList<ClientList>(); 
		clientlist.add(client);
		String command = "ClientInfoUpload";
        int version = 256;
		
        String output = null;
		try {
			output = terminalService.ClientInfoUpload(command, version, companyname, projectname,ordernum, 1, clientlist);
		} catch (IOException e) {
			System.err.println("调用ClientInfoUpload接口出错");
			e.printStackTrace();
		}
        
        JSONObject jsStr = JSONObject.fromObject(output);
	    String result = jsStr.getString("result");
        if(result.equals("0")){
        	 jsonMsg = new JsonMsgBean(0, CVal.Success, "修改信息成功");
		 }
		 else{
			 jsonMsg = new JsonMsgBean(0, CVal.Fail, "修改信息失败");
		 }
        
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}

}
