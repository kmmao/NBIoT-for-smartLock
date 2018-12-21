package com.routon.plcloud.admin.nb.service;

import java.util.List;

/**
 * 
 * @author wangxiwei
 *
 */
public interface NBCommandService {
	
	/**
	 * 下发白名单
	 * @param deviceId
	 * @param personId
	 * @param authority
	 * @param card1
	 * @return
	 * @throws Exception
	 */
	public String sendWhiteNames(String deviceId, int personId, String authority, String card1) throws Exception;
	
	/**
	 * 删除白名单
	 * @param delsel
	 * @param personId
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	public String delWhiteNames(String delsel, String personId, String deviceId) throws Exception;
	
	/**
	 * 开关门操作
	 * @param personId
	 * @param operaCode
	 * @param phone
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	public String openAndClose(int personId, String operaCode,String phone,String deviceId) throws Exception;
	
	/**
	 * 远程开门
	 * @param deviceId
	 * @param lockStatus
	 * @return
	 * @throws Exception
	 */
	public String lock(String deviceId, String lockStatus) throws Exception;
	
	/**
	 * 清除设备记录
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	public String clearRecord(String deviceId) throws Exception;
	
	/**
	 * 同步设备网络时间
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	public String synTime(String deviceId) throws Exception;
	
	
}
