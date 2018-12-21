package com.routon.plcloud.admin.order.service;

import java.util.List;

import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.SubOrder;

/**
 * 
 * @author wangxiwei
 *
 */
public interface OrderService {

//	public List<TreeBean> getMenuTrees();
	
//	public List<TreeBean> getMenuTrees(Long loginUserId);
	public List<TreeBean> getMenuTrees(Long loginUserId,Long requirementtype,String searchname);
	
//	public PagingBean<Order> queryALL(String orderNum, int startIndex, int pageSize, String projectid);
	
	public Company queryCompanyById(String companyId);
	
	public Project queryProjectById(String projectId);
	
	public List<String> querySoftwareErpCodeAll();
	
	public String querySoftwareNameByERPcode(String erpCode);
	
//	public long save(Order order,String projectid,String companyid);
	public long save(Order order, UserProfile optUser);
	
	//保存子订单信息
	public long saveSuborder(Order order, UserProfile user, String renew, String subordercontact);
	//编辑子订单信息
	public long editSuborder(Order order, UserProfile user);
	
	//查询授权的终端数量
	public String querytermNum(Long id);
	
//	public long edit(Long id, Order order);
	public long edit(Order order, UserProfile optUser, String renew, String subordercontact);
	
//	public void disableOrder(String disableOrderIds);
	public long disableOrder(Integer disableOrderIds, UserProfile optUser);
	
	public long openOrder(Integer disableOrderIds, UserProfile optUser);
	
//	public PagingBean<Order> paging(int startIndex, int pageSize, String sortCriterion,
//			String sortDirection, Order queryCondition,String projectid, String in_orderIds,
//			String notin_orderIds, Long loginUserId, boolean exportflag);
	public PagingBean<Order> paging(int startIndex, int pageSize, String sortCriterion,
			String sortDirection, Order queryCondition,String status,String projectid, String companyid,String in_orderIds,
			String notin_orderIds, Long loginUserId, boolean exportflag);
	
	public PagingBean<SubOrder> pagingchildOrder(int startIndex, int pageSize, Integer orderid);
	
	//查询续订订单的数量
	public String queryrenewCount(Long orderid);
	
	public String LicenceNumQuery(String command,int version,String company_name,
								  String project ,String orderid) throws Exception;
	
	//更改审核状态，配置授权信息即订单信息
	public String changeVerify(String companyId, String projectId, String ordernum, Long loginUserId);
	
	//更改退订审核状态
	public String changeRetretOrderVerify(String ordernum, Long loginUserId);
}
