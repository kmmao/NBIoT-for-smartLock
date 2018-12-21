package com.routon.plcloud.admin.nb.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huawei.utils.ConnectFtp;
import com.routon.plcloud.admin.nb.service.CommandLogService;
import com.routon.plcloud.admin.nb.service.RegisterService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.Register;
import com.routon.plcloud.common.utils.JsonMsgBean;

@Controller
public class RegisterInfoController {

	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private CommandLogService commandLogService;
	
	@RequestMapping(value ="/nbiot/registerInfo")
	public String list(Model model, HttpServletRequest request, @ModelAttribute("userProfile") UserProfile user) throws Exception{
		//List<Register> list = registerService.getAllRegister();
		Long loginUserId = user.getCurrentUserId();
		int page = NumberUtils.toInt(request.getParameter("page"), 1);
		int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 4);
		int startIndex = (page - 1) * pageSize;
		
		PagingBean<Register> list = registerService.paging(startIndex, pageSize, request.getParameter("sort"),
    			request.getParameter("dir"), loginUserId, false);
		int maxpage = (int)Math.ceil(list.getTotalCount()/(double)pageSize);
		if (list.getTotalCount() == 0) {
			maxpage = 0;
		}
		model.addAttribute("pageList", list);
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("page", page);
		return "nbiot/registerInfo";
		
	}
	
	@RequestMapping(value ="/nbiot/queryRegisterInfo")
	@ResponseBody public List<Register> query() throws Exception{
		List<Register> list = registerService.getAllRegister();
		return list;
		
	}
	
	@RequestMapping(value ="/nbiot/queryRegisterInfobyFaceid")
	@ResponseBody public String queryDataByPersonId(String personid) throws Exception{
		String data = registerService.queryDataByPerson(personid);
		return data;
		
	}
	
	
	@RequestMapping(value ="/nbiot/downloadFile")
	@ResponseBody public String download(HttpServletRequest request,
		HttpServletResponse response, Model model, String id) throws Exception{
		InputStream fis = null;
		OutputStream os = null;
		try {
			fis = ConnectFtp.readfileToBase64("D:\\ftp134\\facephoto\\"+ id + ".jpg", "172.16.42.134", 21, "134", "123456");
			//fis = new FileInputStream("/home/hadoop/2015.jpg");
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			fis.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ok";
		
	}
	
	@RequestMapping(value = "/nbiot/sendmanager")
	public String sendmanager(Model model,String id,String role, String delTag) {
		if(delTag == null){
			delTag = "";
		}
		String[] ids = id.split(",");
		int totalsend = 0;
		
		for (int i = 0; i < ids.length; i++) {
			
			//不能只根据faceid来确定条注册记录，还要带deviceid,如果同一个人在两个终端上都注册了，那么这里就报错了，很多地方都有这个问题。严谨！！！
			Register reg = registerService.selectById(Integer.parseInt(ids[i]));
			String deviceid = reg.getDevice_id();
			role = reg.getRole();
			int pos = role.indexOf("2");
			if(pos == -1) {
				String rolemanager = role.concat(",2");
				registerService.sendmanagerById(rolemanager, Integer.parseInt(ids[i]));
			} else {
				registerService.sendmanagerById(role, Integer.parseInt(ids[i]));
			}
			int commandtype = 6;
			Date date = new Date();
			/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = formatter.format(date);*/
			int report_manager = 1;
			if(delTag.equals("1")){
				commandtype = 7;
			}
			commandLogService.insertManagerCommand(deviceid, commandtype, date, Integer.parseInt(ids[i]), report_manager);
			totalsend++;
		}
		JsonMsgBean jsonMsg = null;
		if(totalsend != 0) {
			if(delTag.equals("1")){
				jsonMsg = new JsonMsgBean(0, CVal.Success, "成功删除"+totalsend+"位管理员！");
			} else{
				jsonMsg = new JsonMsgBean(0, CVal.Success, "成功下发"+totalsend+"位管理员！");
			}
		} else {
			if(delTag.equals("1")){
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "删除管理员失败！");
			} else{
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "下发管理员失败！");
			}
		}
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}
	
}
