package com.routon.plcloud.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.SystemBuzFunctionModule;
import com.routon.plcloud.common.model.OpLog;
import com.routon.plcloud.common.model.User;
import com.routon.plcloud.common.persistence.OpLogMapper;

@Service
public class UserServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(User systemuser, String projectIds, String roleIds,
			UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(24));
		opLog.setType(24);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" User Name :"+systemuser.getUserName()
				+";User RealName:"+systemuser.getRealName()
				+";User Company:"+systemuser.getCompany()
				+";User Phone:"+systemuser.getPhone()
				+";User projectIds:"+projectIds
				+";User roleIds:"+roleIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(User systemuser, String projectIds, String roleIds,
			UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(25));
		opLog.setType(25);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" User id :"+systemuser.getId()
				+";User Name :"+systemuser.getUserName()
				+";User RealName:"+systemuser.getRealName()
				+";User Company:"+systemuser.getCompany()
				+";User Phone:"+systemuser.getPhone()
				+";User projectIds:"+projectIds
				+";User roleIds:"+roleIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
		
	}
	
	public void disable(String userIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(15));
		opLog.setType(15);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" userIds :"+userIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void delete(String userIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(26));
		opLog.setType(26);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" userIds :"+userIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	
	public void resetPwd(String userIds, UserProfile optUser) throws Exception {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(27));
		opLog.setType(27);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" userIds :"+userIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
}
