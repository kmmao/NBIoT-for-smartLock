package com.routon.plcloud.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.SystemBuzFunctionModule;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.OpLog;
import com.routon.plcloud.common.persistence.OpLogMapper;

/**
 * 
 * @author wangzhuo
 *
 */

@Service
public class TerminalServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void licencedownload(String eFileName, String termsn , UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(37));
		opLog.setType(37);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+ " termsn :" + termsn
				+" eFileName :"+eFileName+" downsucced ";
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void licencerequst(String termsn , UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(38));
		opLog.setType(38);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+ " termsn :" + termsn;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void downexcel(String fileName , UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(39));
		opLog.setType(39);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+ " fileName :" + fileName;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void importexcel(String totalImportRowNum ,String successImportRowNum,String failImportRowNum , UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(40));
		opLog.setType(40);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+ " totalImportRowNum :" + totalImportRowNum
				+ " successImportRowNum : " + successImportRowNum + 
				" failImportRowNum :" + failImportRowNum;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
}
