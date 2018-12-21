package com.routon.plcloud.admin.order.service;

import java.io.IOException;
import java.util.List;

import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.ClientList;
//import com.routon.plcloud.common.model.Company;
//import com.routon.plcloud.common.model.Order;
//import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Project;

/**
 * 
 * @author huanggang
 *
 */
public interface ClientListService {

//	public List<Company> getCompanysByUserId(Long loginUserId);
//	
//	public List<Project> getProjectsByUserId(Long loginUserId);
//	
//	public List<Order> getOrdersByUserId(Long loginUserId);
	
	public Company queryCompanyById(String companyId);
	
	public Project queryProjectById(String projectId);
	
//	public List<TreeBean> getUserTreeByUserId(Long loginUserId);
	public List<TreeBean> getUserTreeByUserId(Long loginUserId,Long requirementtype,String searchname);
	
	public PagingBean<ClientList> paging(Long companyid, Long projectid, Long orderid, int page, Long loginUserId) throws IOException;
}
