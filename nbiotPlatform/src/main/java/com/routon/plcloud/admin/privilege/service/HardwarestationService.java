package com.routon.plcloud.admin.privilege.service;

import java.util.List;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.model.HardwareStation;
import com.routon.plcloud.common.model.OperatingSystem;

/**
 * 
 * @author huanggang
 *
 */
public interface HardwarestationService {
	
	/**
	 * 查询出所有的操作系统名称
	 * @return
	 */
	public List<OperatingSystem> queryAllSystemVersion();
	
	/**
	 * 新增硬件平台
	 * @param hardwarestation
	 * @param hardwarestationName
	 * @param opsystemIds
	 * @return
	 */
	public Long add(HardwareStation hardwarestation,String hardwarestationName, String opsystemIds, UserProfile optUser);
	
	/**
	 * 编辑硬件平台
	 * @param id
	 * @param hardwarestationName
	 * @param opsystemIds
	 * @return
	 */
	public Long edit(Integer id, String hardwarestationName, String opsystemIds, UserProfile optUser);
	
	/**
	 * @param hardwareId
	 * @return
	 */
	public HardwareStation getHardwareByID(Long hardwareId);
	
	/**
	 * 删除硬件平台
	 * 
	 * @param hardwareIds
	 *            硬件平台的ID集合
	 * @return -1 所有的删除失败; -2 部分删除成功 有关联不能删除; 1 全部删除成功;
	 */
	public int delete(String hardwareIds, UserProfile optUser);
	
	/**
	 * 获取硬件平台列表的分页查询
	 * 
	 * @param startIndex
	 *            起始数据索引，从0开始
	 * @param pageSize
	 *            页大小
	 * @param sortCriterion
	 *            用于排序的字段
	 * @param sortDirection
	 *            排序方向
	 * @param queryCondition
	 *            查询条件
	 * @param in_hardwareIds
	 * @param notin_hardwareIds
	 * @param loginUserId
	 *            当前登录用户ID
	 * @return 已分页硬件平台集合
	 */
	public PagingBean<HardwareStation> paging(int startIndex, int pageSize, String sortCriterion,
			String sortDirection, HardwareStation queryCondition, String in_hardwareIds,
			String notin_hardwareIds, Long loginUserId, boolean exportflag);
}
