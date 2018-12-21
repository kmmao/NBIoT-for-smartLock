package com.routon.plcloud.admin.nb.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.routon.plcloud.admin.nb.service.PositionService;
import com.routon.plcloud.admin.nb.service.RegisterService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.Position;
import com.routon.plcloud.common.model.Register;
import com.routon.plcloud.common.utils.JsonMsgBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class PositionController {
		@Autowired
		private PositionService positionService;
		
		@Autowired
		private RegisterService registerService;
		
		@RequestMapping(value = "/nbiot/positionInfo")
		public String list(Model model, HttpServletRequest request, @ModelAttribute("userProfile") UserProfile user) {
//			String allDevices = registerService.getDeviceInfo();
//			JSONObject all = JSONObject.fromObject(allDevices);
//			JSONArray ja = all.getJSONArray("devices");
//			for (Iterator tor= ja.iterator(); tor.hasNext();) {
//				 JSONObject job = (JSONObject)tor.next();
//				 String i = job.getString("deviceId");
//				 List<String> did = new ArrayList<>();
//				 did.add(i);
//				 }
			//List<Position> list = positionService.getAllPosition();
			Long loginUserId = user.getCurrentUserId();
			int page = NumberUtils.toInt(request.getParameter("page"), 1);
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), 5);
			int startIndex = (page - 1) * pageSize;
			
			PagingBean<Position> list = positionService.paging(startIndex, pageSize, request.getParameter("sort"),
	    			request.getParameter("dir"), loginUserId, false);
			int maxpage = (int)Math.ceil(list.getTotalCount()/(double)pageSize);
			if (list.getTotalCount() == 0) {
				maxpage = 0;
			}
			model.addAttribute("pageList", list);
			model.addAttribute("maxpage", maxpage);
			model.addAttribute("page", page);
			return "nbiot/positionInfo";
		}
		
		@RequestMapping(value = "/nbiot/deviceIdInfo")
		@ResponseBody public List<String> deviceIdInfo(){
			String allDevices = registerService.getDeviceInfo();
			JSONObject all = JSONObject.fromObject(allDevices);
			JSONArray ja = all.getJSONArray("devices");
			List<String> did = new ArrayList<>();
			for (Iterator tor= ja.iterator(); tor.hasNext();) {
				 JSONObject job = (JSONObject)tor.next();
				 String i = job.getString("deviceId"); 
				 Position position = positionService.selectBydeviceid(i);
					 if(position != null) {
						 continue;
					 } else {
						 did.add(i);
					 }
				 }
			return did;
		}
		
		@RequestMapping(value = "/nbiot/addPositionInfo")
		public String addPosition(Model model,String deviceid,String addr,Integer roomnum,String name,String phone,String jurisdiction,String jurisdictioncon) throws Exception {
			//req.setCharacterEncoding("utf-8");
			Position pos = positionService.selectBydeviceid(deviceid);
			JsonMsgBean jsonMsg = null;
			Position position = new Position();
			position.setDeviceid(deviceid);
			position.setAddr(addr);
			position.setRoomnum(roomnum);
			position.setName(name);
			position.setPhone(phone);
			position.setJurisdiction(jurisdiction);
			position.setJurisdictioncon(jurisdictioncon);
			if(pos != null) {
				int edit = positionService.updatePosition(position);
				if(edit > 0) {
					jsonMsg = new JsonMsgBean(0, CVal.Success, "您选中设备位置成功修改!");
				} else{
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "修改失败!");
				}
				
			} else {
				
				int addid = positionService.insertPosition(position);
				
				if (addid > 0) {
					jsonMsg = new JsonMsgBean(0, CVal.Success, "保存成功!");
				} else{
					jsonMsg = new JsonMsgBean(0, CVal.Fail, "保存失败!");
				}
			}
			model.addAttribute("jsonMsg", jsonMsg);

			return "common/jsonTextHtml";		
		}
		
		@RequestMapping(value = "/nbiot/delPositionInfo")
		public String delPosition(Model model,String id) {
			String[] ids = id.split(",");
			int del = 0;
			for (int i = 0; i < ids.length; i++) {
				del = positionService.delPosition(Integer.parseInt(ids[i]));
			}
			JsonMsgBean jsonMsg = null;
			if (del == 1) {
				jsonMsg = new JsonMsgBean(0, CVal.Success, "所选位置信息删除成功");
			} 
			 else {
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选位置信息删除成功");
			}
			model.addAttribute("jsonMsg", jsonMsg);	
			return "common/jsonTextHtml";	
		}
		
		@RequestMapping(value = "/nbiot/selectById")
		@ResponseBody public Position selectById(String id,Model model) {
			Position pos = positionService.selectBydeviceid(id.trim());
			
			return pos;
			
		}
		
		@RequestMapping(value = "/nbiot/updatePositionInfo")
		public String updatePositionInfo(Model model,String deviceid1,String addr1,Integer roomnum1,String name1,String phone1,String jurisdiction1,String jurisdictioncon1) {
			
			Position position = new Position();
			position.setDeviceid(deviceid1);
			position.setAddr(addr1);
			position.setRoomnum(roomnum1);
			position.setName(name1);
			position.setPhone(phone1);
			position.setJurisdiction(jurisdiction1);
			position.setJurisdictioncon(jurisdictioncon1);
			int edit = positionService.updatePosition(position);
			JsonMsgBean jsonMsg = null;
			if(edit > 0) {
				jsonMsg = new JsonMsgBean(0, CVal.Success, "您选中设备位置成功修改!");
			} else{
				jsonMsg = new JsonMsgBean(0, CVal.Fail, "修改失败!");
			}
			model.addAttribute("jsonMsg", jsonMsg);	
			return "common/jsonTextHtml";
		}
}
