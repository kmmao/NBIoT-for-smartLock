package com.routon.plcloud.admin.order.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.plcloud.admin.privilege.service.log.CompanyServiceLog;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.ProjectCompany;
import com.routon.plcloud.common.model.Province;
import com.routon.plcloud.common.model.Role;
import com.routon.plcloud.common.model.RoleMenu;
import com.routon.plcloud.common.model.UserRole;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.ProjectCompanyMapper;
import com.routon.plcloud.common.persistence.ProvinceMapper;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyMapper CompanyMapper;
	
	@Autowired
	private ProvinceMapper provinceMapper ;
	
	@Autowired
	private CompanyServiceLog companyServiceLog;
	
	@Autowired
	private ProjectCompanyMapper projectCompanyMapper;
	
	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;	
	
	@Transactional(readOnly = true)
	public boolean companyNameExist(String companyName, Long companyId) {
		String sql = "select a.* from company a where a.companyname = '"+companyName+"'";

		if (companyId != null) {
			sql += " and a.id <>" + companyId;
		}

		List<Company> roles = CompanyMapper.selectBySql(sql);

		if (roles != null && roles.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional(readOnly = true)
	public boolean companyregNameExist(String companyregName, Long companyId) {
		String sql = "select a.* from company a where a.companyregname = '"+companyregName+"'";

		if (companyId != null) {
			sql += " and a.id <>" + companyId;
		}

		List<Company> roles = CompanyMapper.selectBySql(sql);

		if (roles != null && roles.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	

	
	
	private Long saveCompany(Company company , UserProfile optUser) {
//		String companytmp = company.getCompanyregname();
		//循环加入测试数据
//		for(int i=0; i<1000;i++){
//			company.setCompanyregname(companytmp+i);
		
//		String companyName = company.getCompanyname();
		String companyregname = company.getCompanyregname();
	//	String roleName = role.getName();
		boolean isExist = companyNameExist(companyregname, null);
		if (isExist) {
//			logger.info("新增角色时,角色名已经存在");
			return -2l;
		}
		boolean RegisExist = companyregNameExist(companyregname, null);
		if (RegisExist) {
//			logger.info("新增角色时,角色名已经存在");
			return -3l;
		}

		company.setCreatetime(new Date());
		company.setModitytime(new Date());
	//	role.setCreateTime(new Date());
	//	role.setModifyTime(new Date());
		company.setCompanyname(company.getCompanyregname().trim());
		company.setCompanyregname(company.getCompanyregname().trim());
		company.setCompanyrep(company.getCompanyrep().trim());
		company.setEnglishname(company.getEnglishname().trim());
		company.setNamespell(company.getNamespell().trim());
//		company.setIdentifynum(company.getIdentifynum().trim());
//		company.setRegnum(company.getRegnum().trim());
		company.setOrganizationcode(company.getOrganizationcode().trim());
		company.setTrade(company.getTrade().trim());
		company.setContactname(company.getContactname().trim());
		company.setContactphone(company.getContactphone().trim());
		company.setContactfixedphone(company.getContactfixedphone().trim());
		company.setEmail(company.getEmail().trim());
		company.setCountry(company.getCountry().trim());
		company.setProvince(company.getProvince().trim());
		company.setCity(company.getCity().trim());
		company.setAddress(company.getAddress().trim());
		company.setZipcode(company.getZipcode().trim()); 
		

		long companyid = CompanyMapper.insert(company);
//		companyid = company.getId();
		if (companyid > 0) {
			companyServiceLog.add(company, optUser);
			return companyid;
		}
//	}
		return -1l;

	}
	
	@Transactional(readOnly = true)
	public Company getCompanyByCompanyId(Long roleId) {
		List<Company> companys = CompanyMapper.selectById(roleId);
		Company company = companys.get(0);
		return company;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Long add(Company company, UserProfile optUser) {
	//	role.setCreateUserId(optUser.getCurrentUserId()); 
		Long companyId = saveCompany(company , optUser);

//		if (roleId > 0) {
//			String menuId_array[] = menuIds.split(",");
//
//			for (String menuId : menuId_array) {
//
//				RoleMenu roleMenu = new RoleMenu();
//				roleMenu.setMenuID(Long
//						.parseLong(menuId));
//				roleMenu.setRoleID(Long.parseLong(roleId + ""));
//				roleMenu.setModifyTime(new Date());
//
//				saveRoleMenu(roleMenu);
//			}
//
//			roleServiceLog.add(role, menuIds, optUser);
//			
//			return roleId;
//
//		} else {
//			return roleId;
//		}
		return companyId;
	}
	
	
	
	
	

	@Transactional(readOnly = true)
	public PagingBean<Company> paging(int startIndex, int pageSize,
			String sortCriterion, String sortDirection, Company queryCondition,
			String in_companyIds, String notin_companyIds, Long loginUserId, boolean exportflag) {

		String pagingQueryLanguage = " select * from (select a.* from company  a  order by a.moditytime DESC) b";
		// String countpagingQueryLanguage =
		// "select count(DISTINCT a) from Systemrole a  where 1=1  ";
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
		// StringBuilder countsbHQL = new
		// StringBuilder(countpagingQueryLanguage);

		if (StringUtils.isNotBlank(queryCondition.getCompanyname())) {

			String companyName = queryCondition.getCompanyname().trim();
			
			sbHQL.append(" where b.companyname like '%");
			sbHQL.append(companyName); 
			sbHQL.append("%'");
			 
		}

		

		if (in_companyIds != null) {

			if (StringUtils.isNotBlank(in_companyIds)) {
				  sbHQL.append(" and b.id in ("); 
				  sbHQL.append(in_companyIds);
				  sbHQL.append(")");
			} else {
				sbHQL.append(" and b.id in (");
				sbHQL.append("-1");
				sbHQL.append(")");
			}
		}

		if (StringUtils.isNotBlank(notin_companyIds)) {
			
			 sbHQL.append(" and b.id not in (");
			 sbHQL.append(notin_companyIds); 
			 sbHQL.append(")");
			
		}

//		if (loginUserId != null) {
//			
//			sbHQL.append(" and (select count(*) from rolemenu b where b.roleId = a.id) ");
//			sbHQL.append("- (select count(*) from rolemenu b where b.roleId = a.id ");
//			sbHQL.append("    and b.menuId IN (select c.menuId FROM rolemenu c where c.roleId in (select d.roleId from userrole d WHERE d.userId = ");
//			sbHQL.append(loginUserId);
//			sbHQL.append("))");
//			sbHQL.append(") <= 0");
//
//	
//
//		}
		
		String[] sortCriterions = null;
		if(sortCriterion != null){
			sortCriterions = new String[] { "b." + sortCriterion };
		}
		PagingSortDirection[] sortDirections =null;
		if(sortDirection != null){
			sortDirections = new PagingSortDirection[] { "desc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}
		PagingBean<Company> pagingSystemcompany = pagingDao.query(CompanyMapper,sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);

		return pagingSystemcompany;
	
	}




//	@Override
//	public Long edit(Company company, UserProfile optUser) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Transactional(rollbackFor = Exception.class)
	public Long edit(Company company, UserProfile optUser) {
		
		Long companyId = updateCompany(company , optUser);

//		if (roleId > 0) {
//			roleMenuMapper.deleteByRoleId(roleId);
//
//			String menuId_array[] = menuIds.split(",");
//			for (String menuId : menuId_array) {
//				RoleMenu roleMenu = new RoleMenu();
//				roleMenu.setMenuID(Long
//						.parseLong(menuId));
//				roleMenu.setRoleID(Long.parseLong(roleId + ""));
//				roleMenu.setModifyTime(new Date());
//
//				saveRoleMenu(roleMenu);
//			}
//			roleServiceLog.edit(role, menuIds, optUser);
//			return roleId;
//		} else {
//			return roleId;
//		}
		return companyId;
	}


//	/**
//	 * 
//	 * @param systemrole
//	 * @return
//	 */
//	private Long updateRole(Role role) {
//
//		boolean isExist = roleNameExist(role.getName(), role.getId());
//
//		if (isExist) {
//			logger.info("更新角色时,角色名已经存在");
//			return -2L;
//		}
//
//		List<Role> tem_roles = roleMapper.selectById(role.getId());
//		Role tem_role = tem_roles.get(0);
//		tem_role.setName(role.getName());
//		tem_role.setRemark(role.getRemark());
//		tem_role.setModifyTime(new Date());
//		roleMapper.update(tem_role);
//
//		return role.getId();
//	}
	
//	@Transactional(readOnly = true)
//	public boolean roleNameExist(String roleName, Long roleId) {
//		String sql = "select a.* from role a where a.name = '"+roleName+"'";
//
//		if (roleId != null) {
//			sql += " and a.id <>" + roleId;
//		}
//
//		List<Role> roles = roleMapper.selectBySql(sql);
//
//		if (roles != null && roles.size() > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	

private boolean roleNameExist(String companyname, Long companyid) {
	// TODO Auto-generated method stub
	String sql = "select a.* from company a where a.companyname = '"+companyname+"'";
	if (companyid != null) {
		sql += " and a.id <>" + companyid;
	}

	List<Company> roles = CompanyMapper.selectBySql(sql);

	if (roles != null && roles.size() > 0) {
		return true;
	} else {
		return false;
	}
	
}

private Long updateCompany(Company company , UserProfile optUser) {
	// TODO Auto-generated method stub
	
	boolean isExist = roleNameExist(company.getCompanyname(), company.getId());

	if (isExist) {
	//	logger.info("更新角色时,角色名已经存在");
		return -2L;
	}

	List<Company> tem_companys = CompanyMapper.selectById(company.getId());
	Company tem_company = tem_companys.get(0);
	tem_company.setCompanyname(company.getCompanyname().trim());
	tem_company.setCompanyrep(company.getCompanyrep().trim());
	tem_company.setEnglishname(company.getEnglishname().trim());
	tem_company.setNamespell(company.getNamespell().trim());
//	tem_company.setIdentifynum(company.getIdentifynum().trim());
//	tem_company.setRegnum(company.getRegnum().trim());
	tem_company.setOrganizationcode(company.getOrganizationcode().trim());
	tem_company.setTrade(company.getTrade().trim());
	tem_company.setStatus(company.getStatus());
	tem_company.setContactname(company.getContactname().trim());
	tem_company.setContactphone(company.getContactphone());
	tem_company.setContactfixedphone(company.getContactfixedphone().trim());
	tem_company.setEmail(company.getEmail().trim());
	tem_company.setSalename(company.getSalename().trim());
	tem_company.setCountry(company.getCountry().trim());
	tem_company.setProvince(company.getProvince().trim());
	tem_company.setCity(company.getCity().trim());
	tem_company.setArea(company.getArea().trim());
	tem_company.setAddress(company.getAddress().trim());
	tem_company.setZipcode(company.getZipcode().trim());
	tem_company.setModitytime(new Date());
//	tem_company.setCreatetime(new Date());

	CompanyMapper.update(tem_company);
	if(tem_company.getId() > 0){
		companyServiceLog.edit(tem_company, optUser);
		return company.getId();
	}
//	tem_role.setName(role.getName());
//	tem_role.setRemark(role.getRemark());
//	tem_role.setModifyTime(new Date());
//	roleMapper.update(tem_role);

	return company.getId();
}


/*公司与项目相关查询关系*/

//private boolean isProCompany(Long companyId) {
//
//	String sql = "select a.* from userrole a where a.roleId = " + roleId;
//	List<UserRole> roleUsers = userRoleMapper.selectBySql(sql);
//
//	if (roleUsers != null && roleUsers.size() > 0) {
//		return true;
//	} else {
//		return false;
//	}
//}

private boolean isCompanyProject(Long companyId) {

	String sql = "select a.* from projectcompany a where a.companyid = " + companyId;
	List<ProjectCompany> projectCompanies = projectCompanyMapper.selectBySql(sql);

	if (projectCompanies != null && projectCompanies.size() > 0) {
		return true;
	} else {
		return false;
	}
}


@Transactional(rollbackFor = Exception.class)
public int delete(String companyIds, UserProfile optUser) {
	String companyId_array[] = companyIds.split(",");
	int del_succee_count = 0;
	String del_succee_companyIds =  "";
	for (String companyId : companyId_array) {

		if (isCompanyProject(Long.parseLong(companyId))) {
	//		logger.info("该角色被用户使用不能删除");
			continue;
		} else {

		
		  Long companyid = Long.parseLong(companyId);
			CompanyMapper.deleteById(companyid);

			if(del_succee_companyIds.equals("")){
				del_succee_companyIds += companyid;
			}else {
				del_succee_companyIds += ",";
				del_succee_companyIds += companyid;
			}
			
			del_succee_count++;
		}
	}

	if (del_succee_count == 0) {
		return -1;// 全部删除失败
	} else if (del_succee_count == companyId_array.length) {
		companyServiceLog.delete(del_succee_companyIds, optUser);
//		roleServiceLog.delete(del_succee_roleIds, optUser);
		return 1;// 全部删除成功
	} else {
//		roleServiceLog.delete(del_succee_roleIds, optUser);
		companyServiceLog.delete(del_succee_companyIds, optUser);
		return -2;// 部分删除 还有部分因用户引用关系不能删除
	}

}




	@Override
	public Map<String, String> Threelevellinkage(String code,String grade) {
		
		  Map<String, String> map = new LinkedHashMap<String, String>();
		
//	      if(grade.equals("province")){
				String sqlprovince = "select * from province order by code_p";
				List<Province> provinces = provinceMapper.selectBySql(sqlprovince);
				for(int i=0; i< provinces.size();i++){
					String code_p = provinces.get(i).getCode_p();
					String name = provinces.get(i).getName();
					map.put(code_p, name);
				}
				
//				List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
//		        Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
//		            //升序排序
//		            public int compare(Entry<String, String> o1,
//		                    Entry<String, String> o2) {
//		                return o1.getValue().compareTo(o2.getValue());
//		            }
//
//		        });

//			}
	      /*else if(grade.equals("city")){
				String sqlcity = "select * from city where code_p = '" + code + "'";
				List<City> citys = cityMapper.selectBySql(sqlcity);
				for(int i=0; i< citys.size();i++){
					String code_c = citys.get(i).getCode_c();
					String name = citys.get(i).getName();
					map.put(code_c, name);
				}
			}else if(grade.equals("address")){    
				String sqlarea = "select * from area where code_c = '" + code + "'";
				List<Area> areas = areaMapper.selectBySql(sqlarea);
				for(int i=0; i< areas.size();i++){
					String code_a = areas.get(i).getCode_a();
					String name = areas.get(i).getName();
					map.put(code_a, name);
				}
			}*/
	      
		 return map;
	}

	
}
