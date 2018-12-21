package com.routon.plcloud.admin.nb.service;

import java.util.Date;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.CommandLog;

public interface CommandLogService {
	
	/**
	 * 页面分页
	 * @param startIndex
	 * @param pageSize
	 * @param sortCriterion
	 * @param sortDirection
	 * @param in_visitrecordIds
	 * @param notin_visitrecordIds
	 * @param loginUserId
	 * @param exportflag
	 * @return
	 */
	public PagingBean<CommandLog> paging(int startIndex, int pageSize,
			String sortCriterion, String sortDirection, 
			String in_visitrecordIds, String notin_visitrecordIds, Long loginUserId, boolean exportflag);
	
	/**
	 * 下发指令存档接口
	 * @param deviceid
	 * @param commandtype  commandtype：1--下发白名单；2--删除白名单；3--开关门操作；4--远程开锁；5--同步设备网络时间
	 * @param commandid
	 * @param faceid
	 * @return
	 */
	public int insertCommandRecord(String deviceid, int commandtype, String commandid, int faceid, String commandStr, int report,int reportsend);
	
	/**
	 * 标记需要上报420的白名单
	 * @param id
	 */
	public void updateMark(int id, int tag);
	
	public int insertManagerCommand(String deviceid,int commandtype,Date commandtime,int face_id,int report_manager);
}
