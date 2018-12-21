package com.routon.plcloud.admin.hardware.service;

import java.util.List;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.model.HardwareProduct;

/**
 * 
 * @author wangxiwei
 *
 */
public interface HardwareService {
	
//	public PagingBean<HardwareProduct> quryAll(int startIndex, int pageSize, String productName);
	
	public Long save(String productName, String erpCode, String hardwareVersion, String hardwarePlatform, String systemVersion);
	
	public Long edit(Integer id, String productName, String erpCode, String hardwareVersion, String hardwarePlatform,
			String systemVersion);
	
	public List<String> queryAllHardwareProduct();
	
	public List<String> queryAllHardwareStation();
	
	public List<String> queryAllSystemVersion();

	public PagingBean<HardwareProduct> quryAll(int startIndex, int pageSize, String sortCriterion, String sortDirection,
			HardwareProduct queryCondition, String in_hardwareIds, String notin_hardwareIds, Long loginUserId, boolean exportflag);

	public int delete(String ids, UserProfile optUser);
}
