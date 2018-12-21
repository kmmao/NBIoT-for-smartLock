package com.routon.plcloud.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.SystemBuzFunctionModule;
import com.routon.plcloud.common.model.HardwareStation;
import com.routon.plcloud.common.model.OpLog;
import com.routon.plcloud.common.persistence.OpLogMapper;


@Service
public class HardwarestationServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(HardwareStation hardwarestation, String opsystemIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(42));
		opLog.setType(42);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" hardwarestation Name :"+hardwarestation.getHardwarestationName()
				+";opsystemIds :"+opsystemIds;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(HardwareStation hardwarestation, String opsystemIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(43));
		opLog.setType(43);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" hardwarestation id :"+hardwarestation.getId()
				+" hardwarestation Name :"+hardwarestation.getHardwarestationName()
				+";opsystemIds :"+opsystemIds;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void delete(String hardwareIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(44));
		opLog.setType(44);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"  hardwareIds :"+hardwareIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
}
