package com.routon.plcloud.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.SystemBuzFunctionModule;
import com.routon.plcloud.common.model.OpLog;
import com.routon.plcloud.common.model.OperatingSystem;
import com.routon.plcloud.common.persistence.OpLogMapper;

@Service
public class OperatingSystemServiceLog {
	
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(OperatingSystem opsystem ,UserProfile optUser){
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(28));
		opLog.setType(28);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"opsystem Name:"+opsystem.getOperatingsystemname();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(OperatingSystem opsystem ,UserProfile optUser){
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(29));
		opLog.setType(29);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"opsystem Id:"+opsystem.getId()
				+",opsystem Name:"+opsystem.getOperatingsystemname();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void delete(String opsystemIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(30));
		opLog.setType(30);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"  opsystemIds :"+opsystemIds;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}

}
