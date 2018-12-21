package com.routon.plcloud.admin.nb.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.routon.plcloud.admin.nb.service.CommandLogService;
import com.routon.plcloud.admin.nb.service.HeartbeatsService;
import com.routon.plcloud.admin.nb.service.NBCommandService;
import com.routon.plcloud.admin.nb.service.PositionService;
import com.routon.plcloud.admin.nb.service.RegisterService;
import com.routon.plcloud.admin.nb.service.VisitRecordService;
import com.routon.plcloud.common.model.Heartbeats;
import com.routon.plcloud.common.model.Position;
import com.routon.plcloud.common.model.Register;
import com.routon.plcloud.common.model.Visitrecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author wangxiwei
 *
 */
@Controller
public class NBController {
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private VisitRecordService visitRecordService;
	
	@Autowired
	private NBCommandService nBCommandService;

	@Autowired
	private CommandLogService commandLogService;
	
	@Autowired
	HeartbeatsService heartbeatsService;

	@RequestMapping(value ="/nbiot/list")
	public String list(Model model) throws Exception{
		
		JSONArray ob = new JSONArray();
		String allDevices = registerService.getDeviceInfo();
		//JSONObject devicesJSON = JSONObject.fromObject(allDevices);
		JSONArray devicesArray = JSONObject.fromObject(allDevices).getJSONArray("devices");
		Iterator<JSONObject> it = devicesArray.iterator(); 
		while(it.hasNext()){
			JSONObject job = (JSONObject)it.next();
			String deviceid = (String) job.get("deviceId");
			Position ps = positionService.queryPositionbyDeviceid(deviceid);
			if(ps != null){
				Integer roomnum = ps.getRoomnum();
				String address = ps.getAddr();
				job.put("roomnum", roomnum);
				job.put("address", address);
			} else{
				job.put("roomnum", "此设备暂未分配");
				job.put("address", "此设备暂未分配");
			}
			ob.add(job);
		}
		model.addAttribute("devices", ob);
		return "nbiot/command";
		
	}
	
	@RequestMapping(value ="/nbiot/sendWhiteNames")
	@ResponseBody public String sendWhiteNames(String deviceId, int personId, String authority, String card1) throws Exception{
		//指令下发
		String result = nBCommandService.sendWhiteNames(deviceId, personId, authority, card1);
		JSONObject responseJson = JSONObject.fromObject(result);
        String commandId = responseJson.getString("commandId");
        String CommandStr = responseJson.getJSONObject("command").getJSONObject("paras").getString("CommandStr");
		//指令记录存档
		commandLogService.insertCommandRecord(deviceId, 1, commandId, personId, CommandStr, 1, 1);
		return result;
		
	}
	
	@RequestMapping(value ="/nbiot/delWhiteNames")
	@ResponseBody public String delWhiteNames(String delsel, String personId, String deviceId) throws Exception{
		String result = nBCommandService.delWhiteNames(delsel, personId, deviceId);
		//指令记录存档
		JSONObject responseJson = JSONObject.fromObject(result);
        String commandId = responseJson.getString("commandId");
        String CommandStr = responseJson.getJSONObject("command").getJSONObject("paras").getString("CommandStr");
		commandLogService.insertCommandRecord(deviceId, 2, commandId, Integer.parseInt(personId), CommandStr,1,-1);
		return result;
		
	}
	
	@RequestMapping(value ="/nbiot/openAndClose")
	@ResponseBody public String openAndClose(int personId, String operaCode,String phone,String deviceId) throws Exception{
		String result = nBCommandService.openAndClose(personId, operaCode, phone, deviceId);
		//指令记录存档
		JSONObject responseJson = JSONObject.fromObject(result);
        String commandId = responseJson.getString("commandId");
        String CommandStr = responseJson.getJSONObject("command").getJSONObject("paras").getString("CommandStr");
		commandLogService.insertCommandRecord(deviceId, 3, commandId, personId, CommandStr,0, -1);
		return result;
	}
	
	@RequestMapping(value ="/nbiot/lock")
	@ResponseBody public String lock(String deviceId, String lockStatus) throws Exception{
		String result = nBCommandService.lock(deviceId, lockStatus);
		//指令记录存档
		JSONObject responseJson = JSONObject.fromObject(result);
        String commandId = responseJson.getString("commandId");
        String CommandStr = responseJson.getJSONObject("command").getJSONObject("paras").getString("CommandStr");
		commandLogService.insertCommandRecord(deviceId, 4, commandId, -1, CommandStr,0, -1);
		return result;
		
	}
	
	@RequestMapping(value ="/nbiot/clearRecord")
	@ResponseBody public String clearRecord(String deviceId) throws Exception{
		String result = nBCommandService.clearRecord(deviceId);
		return result;
		
	}
	
	@RequestMapping(value ="/nbiot/synTime")
	@ResponseBody public String synTime(String deviceId) throws Exception{
		String result = nBCommandService.synTime(deviceId);
		//指令记录存档
		JSONObject responseJson = JSONObject.fromObject(result);
        String commandId = responseJson.getString("commandId");
        String CommandStr = responseJson.getJSONObject("command").getJSONObject("paras").getString("CommandStr");
		commandLogService.insertCommandRecord(deviceId, 5, commandId, -1, CommandStr,0, -1);
		return result;
	}
	
	@RequestMapping(value ="/nbiot/insertvisitorRecord")
	@ResponseBody public String insertvisitorRecord(Visitrecord visitrecord) throws Exception{
		visitRecordService.insertVistorRecord(visitrecord);
		return null;
	}
	
	@RequestMapping(value ="/nbiot/queryPersonInfobyDevice")
	@ResponseBody public Map<String,String> queryPersonInfobyDevice(String deviceid) throws Exception{
		Heartbeats h =  heartbeatsService.selectByDeviceId(deviceid);
		Map<String,String> resultMap = new HashMap<String,String>();
		StringBuilder whiteStr = new StringBuilder("");
		StringBuilder managerStr = new StringBuilder("");
		Set<String> whiteSet = new HashSet<String>();
		Set<String> managerSet = new HashSet<String>();
		if(h != null){
			String white = h.getWhitefacelist();
			String manager = h.getManangerfacelist();
			if(white != null && !white.equals("")){
				String[] whiteListArr = white.split(",");
				for(String arr : whiteListArr){
					whiteSet.add(arr);
				}
			}
			if(manager != null && !manager.equals("")){
				String[] managerListArr = manager.split(",");
				for(String arr : managerListArr){
					managerSet.add(arr);
				}
			}
			Iterator<String> its = whiteSet.iterator();
			while(its.hasNext()){
				String faceid = its.next();
				Register re = registerService.selectById(Integer.valueOf(faceid));
				if(re != null){
					String personName = re.getName();
					if(whiteStr.toString().equals("")){
						whiteStr.append(personName);
					}else{
						whiteStr.append(",").append(personName);
					}
				}
			}
			Iterator<String> its1 = managerSet.iterator();
			while(its1.hasNext()){
				String faceid = its1.next();
				Register re = registerService.selectById(Integer.valueOf(faceid));
				if(re != null){
					String personName = re.getName();
					if(managerStr.toString().equals("")){
						managerStr.append(personName);
					}else{
						managerStr.append(",").append(personName);
					}
				}
			}
		}
		resultMap.put("whiteStr", whiteStr.toString());
		resultMap.put("managerStr", managerStr.toString());
		return resultMap;
	}
}
