package com.routon.plcloud.admin.order.service;

import java.util.Map;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Role;



public interface CompanyService {

	
	/**
	 * 获取公司列表的分页查询
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
	 * @param in_roleIds
	 * @param notin_roleIds
	 * @param loginUserId
	 *            当前登录用户ID
	 * @return 已分页公司集合
	 */
	public PagingBean<Company> paging(int startIndex, int pageSize, String sortCriterion,
			String sortDirection, Company queryCondition, String in_companyIds,
			String notin_companyIds, Long loginUserId, boolean exportflag);

	/**
	 * 新增公司
	 * 
	 * @param company
	 *            公司名
	 * @param optUser
	 *            当前操作用户
	 * @return -2 角色名已经存在;-1 失败; 大于0 公司id;
	 */
	public Long add(Company company, UserProfile optUser);
	
	/**
	 * 编辑公司
	 * 
	 * @param company
	 *            公司名

	 * @param optUser
	 *            当前操作用户
	 * @return 已编辑公司的ID
	 */
	public Long edit(Company company, UserProfile optUser);
	
	/**
	 * 通过公司ID 获取公司
	 * 
	 * @param companyid
	 *            公司的ID
	 * @return 公司
	 */
	public Company getCompanyByCompanyId(Long companyid);
	
	
	
	/**
	 * 删除公司
	 * 
	 * @param roleIds
	 *            公司的ID集合
	 * @param optUser
	 *            当前操作用户
	 * @return -1 所有的删除失败; -2 部分删除成功 用户有关联不能删除; 1 全部删除成功;
	 */
	public int delete(String companyIds, UserProfile optUser);


	public Map<String, String> Threelevellinkage(String code,String grade);
	
}
