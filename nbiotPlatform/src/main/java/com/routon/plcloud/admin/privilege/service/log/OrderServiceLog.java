package com.routon.plcloud.admin.privilege.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.SystemBuzFunctionModule;
import com.routon.plcloud.common.model.OpLog;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.persistence.OpLogMapper;

@Service
public class OrderServiceLog {
	@Autowired
	private OpLogMapper opLogMapper;
	
	public void add(Order order, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(45));
		opLog.setType(45);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" orderNum :"+order.getOrdernum();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void edit(Order order, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(46));
		opLog.setType(46);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+" orderNum :"+order.getOrdernum();
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
	
	public void disable(String orderIds, UserProfile optUser) {
		OpLog opLog = new OpLog();
		opLog.setObject(SystemBuzFunctionModule.SYS_BUZ_FUNCTION_MODULE_MAP.get(48));
		opLog.setType(48);
		opLog.setTime(new Date());
		opLog.setUserId(optUser.getCurrentUserId());
		opLog.setIp(optUser.getCurrentUserLoginIp());
		String log = opLog.getObject()
				+"  orderIds :"+orderIds;
				
		opLog.setLog(log);
		opLogMapper.insert(opLog);
	}
}