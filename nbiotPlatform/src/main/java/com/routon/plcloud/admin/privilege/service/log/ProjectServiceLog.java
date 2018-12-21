package com.routon.plcloud.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.SystemBuzFunctionModule;
import com.routon.plcloud.common.model.OpLog;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.persistence.OpLogMapper;

/**
 * 
 * @author wangzhuo
 *
 */
@Service
public class ProjectServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(Project project, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(34));
		opLog.setType(34);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" project Name :"+project.getProjectname();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(Project project, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(35));
		opLog.setType(35);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" project id :"+project.getId()
				+" project Name :"+project.getProjectname();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void disable(String projectIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(36));
		opLog.setType(36);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"  projectids :"+projectIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
}
