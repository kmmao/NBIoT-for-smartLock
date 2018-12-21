package com.routon.plcloud.admin.privilege.service;

//import java.util.List;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
//import com.routon.plcloud.common.UserProfile;
//import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.model.OperatingSystem;

/**
 * 弹框显示---定义操作系统业务接口
 * @author huanggang
 *
 */
public interface OperatingsystemService {
	/**
	 * 新增
	 * @param opsystemName
	 * @return
	 */
	public Long save(String opsystemName ,UserProfile optUser);
	
	/**
	 * 编辑
	 * @param id
	 * @return
	 */
	public Long edit(Integer id,String opsystemName, UserProfile optUser);	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(String id, UserProfile optUser);
	
	/**
	 * 获取操作系统列表的分页查询
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
	 * @param in_opsystemIds
	 * @param notin_opsystemIds
	 * @param loginUserId
	 *            当前登录用户ID
	 * @return 已分页操作系统集合
	 */
	public PagingBean<OperatingSystem> paging(int startIndex, int pageSize, String sortCriterion,
			String sortDirection, OperatingSystem queryCondition, String in_opsystemIds,
			String notin_opsystemIds, Long loginUserId, boolean exportflag);

}




/**
 * 页面显示---定义操作系统业务接口
 * @author huanggang
 *
 */
//public interface OperatingsystemService {
//	
//	/**
//	 * 判断操作系统名使用存在
//	 * 
//	 * @param operatingsystemName
//	 *            操作系统名
//	 * @param operatingsystemId
//	 *            (可null,当null时 只通过operatingsystemName来检查; 当不为null时,operatingsystemId和operatingsystemName 同时校验)
//	 * @return 存在返回true，反之返回false
//	 */
//	public boolean operatingsystemNameExist(String operatingsystemName, Long operatingsystemId);
//	
//	/**
//	 * 新增操作系统
//	 * 
//	 * @param opSystem
//	 *            操作系统名称
//	 * @param optUser
//	 *            当前操作用户
//	 * @return -2 操作系统名已经存在;-1 失败; 大于0 角色id;
//	 */
//	public Long add(OperatingSystem opSystem,  UserProfile optUser);
//	
//	/**
//	 * 编辑操作系统
//	 * 
//	 * @param opSystem
//	 *            操作系统名称
//	 * @param optUser
//	 *            当前操作用户
//	 * @return 已编辑角色的ID
//	 */
//	public Long edit(OperatingSystem opSystem, UserProfile optUser);
//	
//	/**
//	 * 删除操作系统
//	 * 
//	 * @param opsystemIds
//	 *            操作系统的ID集合
//	 * @param optUser
//	 *            当前操作用户
//	 * @return -1 所有的删除失败; -2 部分删除成功 用户有关联不能删除; 1 全部删除成功;
//	 */
//	public int delete(String opsystemIds, UserProfile optUser);
//	
//	/**
//	 * 通过操作系统ID 获取操作系统
//	 * 
//	 * @param opsystemId
//	 *            操作系统的ID
//	 * @return 操作系统
//	 */
//	public OperatingSystem getOpsystemByOpsystemId(Long opsystemId);
//	
//	/**
//	 * 根据loginUserId 获取其权限所见的所有操作系统名称
//	 * 
//	 * @param loginUserId
//	 *            当前登录用户ID
//	 * 
//	 * @return loginUserId为空返回所有的操作系统集合，反之其权限所见操作系统集合
//	 */
//	public List<OperatingSystem> getOperatingSystems(Long loginUserId);
//	
//	/**
//	 * 获取操作系统列表的分页查询
//	 * 
//	 * @param startIndex
//	 *            起始数据索引，从0开始
//	 * @param pageSize
//	 *            页大小
//	 * @param sortCriterion
//	 *            用于排序的字段
//	 * @param sortDirection
//	 *            排序方向
//	 * @param queryCondition
//	 *            查询条件
//	 * @param in_opsystemIds
//	 * @param notin_opsystemIds
//	 * @param loginUserId
//	 *            当前登录用户ID
//	 * @return 已分页操作系统集合
//	 */
//	public PagingBean<OperatingSystem> paging(int startIndex, int pageSize, String sortCriterion,
//			String sortDirection, OperatingSystem queryCondition, String in_opsystemIds,
//			String notin_opsystemIds, Long loginUserId, boolean exportflag);
//
//}
