package com.routon.plcloud.admin.privilege.service.log;

import java.util.Date;

/**
 * 
 * @author wangzhuo
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.SystemBuzFunctionModule;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.OpLog;
import com.routon.plcloud.common.persistence.OpLogMapper;

@Service
public class CompanyServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(Company company, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(31));
		opLog.setType(31);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" company Name :"+company.getCompanyname();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(Company company, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(32));
		opLog.setType(32);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" company id :"+company.getId()
				+" company Name :"+company.getCompanyname();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void delete(String companyIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(33));
		opLog.setType(33);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"  companyids :"+companyIds
				;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
}
