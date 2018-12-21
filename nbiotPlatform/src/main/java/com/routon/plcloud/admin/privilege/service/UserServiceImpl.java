package com.routon.plcloud.admin.privilege.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.privilege.service.log.UserServiceLog;
import com.routon.plcloud.common.EncodeUtils;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.PmaxException;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.AuthType;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Group;
import com.routon.plcloud.common.model.HardwareProduct;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.ProjectCompany;
import com.routon.plcloud.common.model.Role;
import com.routon.plcloud.common.model.User;
//import com.routon.plcloud.common.model.User1;
import com.routon.plcloud.common.model.UserGroup;
import com.routon.plcloud.common.model.UserProject;
import com.routon.plcloud.common.model.UserRole;
import com.routon.plcloud.common.persistence.AuthTypeMapper;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.GroupMapper;
import com.routon.plcloud.common.persistence.ProjectCompanyMapper;
import com.routon.plcloud.common.persistence.ProjectMapper;
import com.routon.plcloud.common.persistence.RoleMapper;
import com.routon.plcloud.common.persistence.UserGroupMapper;
import com.routon.plcloud.common.persistence.UserMapper;
import com.routon.plcloud.common.persistence.UserProjectMapper;
import com.routon.plcloud.common.persistence.UserRoleMapper;

/**
 * 
 * <p>
 * Title: UserServiceImpl
 * </p>
 * <p>
 * Description: 定义系统用户业务的实现类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company:
 * </p>
 * <p>
 * Date: 2013-5-13
 * </p>
 * 
 * @author
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private EncodeUtils encodeUtil;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private GroupMapper groupMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private UserProjectMapper userProjectMapper;

	@Autowired
	private UserGroupMapper userGroupMapper;
	
	@Autowired
	private ProjectCompanyMapper projectCompanyMapper;

	@Autowired
	private EncodeUtils encodeUtils;
	
	@Autowired
	private AuthTypeMapper authTypeMapper;

	@Resource(name = "pagingDaoMybatis")
	private PagingDaoMybatis pagingDao;

	@Autowired
	private UserServiceLog userServiceLog;
	
	private String newPwd = "111111";

	
	/**
	 * 
	 * @param systemuser
	 * @return
	 */
	private Long saveSystemUser(User systemuser) {
//		String username = systemuser.getUserName();
//		boolean isExist = userNameExist(username);

		String phone = systemuser.getPhone();
		boolean isExist = phoneExist(phone);
		if (isExist) {
			logger.info("新增用户时,用户名已经存在");
			return -2l;
		}
		systemuser.setPwd(newPwd);
		String beforeMD5 = systemuser.getPwd();
		// systemuser.setPswSalt(encodeUtils.getEncodedSalt());
		systemuser.setPwd(encodeUtils.getPasswordMD5(beforeMD5));

		if (systemuser.getPwd().equals(beforeMD5)) {
			logger.info("新增用户时,密码加密时异常");
			return -1l;
		}

		systemuser.setCreateTime(new Date());
		systemuser.setModifyTime(new Date());
		systemuser.setStatus(CVal.UserStatus.valid);
		try {
			long userId = userMapper.insert(systemuser);
			userId = systemuser.getId();
			if (userId > 0) {
				return userId;
			}

			return -1l;
		}
		catch (Exception e) {
			logger.error("saveSystemUser异常", e);

			return -1l;
		}

	}

	/**
	 * 
	 * @param systemuser
	 * @return
	 */
	private Long updateSystemUser(User systemuser) {
		
//		String phone = systemuser.getPhone();
//		boolean isExist = phoneExist(phone);
//		if (isExist) {
//			logger.info("新增用户时,用户名已经存在");
//			return -2l;
//		}

		List<User> tem_systemusers = userMapper.selectById(systemuser.getId());
		User tem_systemuser = tem_systemusers.get(0);
/*		boolean isExist = userNameExist(systemuser.getUserName());

		if (isExist) {
			logger.info("用户名已经存在");
			return -2l;
		}*/
	//	System.out.println(systemuser.getPhone());
		 List<User> users = userMapper.selectById(systemuser.getId());
		 User user = users.get(0);
	//	 System.out.println(user.getPhone());
		if(!systemuser.getPhone().equals(user.getPhone()))
		{
			String phone = systemuser.getPhone();
			boolean isExist = phoneExist(phone);
			if (isExist) {
				logger.info("用户名已经存在");
				return -2l;
			}
		}
		
		tem_systemuser.setUserName(systemuser.getUserName());
		tem_systemuser.setRealName(systemuser.getRealName());
		tem_systemuser.setCompany(systemuser.getCompany());
		tem_systemuser.setPhone(systemuser.getPhone());
		tem_systemuser.setModifyTime(new Date());
		tem_systemuser.setProject(systemuser.getProject());
		tem_systemuser.setIdcard(systemuser.getIdcard());
		tem_systemuser.setDepartment(systemuser.getDepartment());
	//	tem_systemuser.setStatus(systemuser.getStatus());

		userMapper.update(tem_systemuser);

		return systemuser.getId();
	}

	private void fillSystemuser(User systemuser) {
		String role_texts = "";
		String roleIds = "";
		String project_texts = "";
		String projectIds = "";

		List<Role> roles = roleMapper
				.selectBySql("SELECT a.* FROM role a, userrole b WHERE a.id = b.roleId AND b.userId ="
						+ systemuser.getId());
		if (roles != null) {

			for (Role systemuserrole : roles) {
				String roleName = systemuserrole.getName();
				Long roleId = systemuserrole.getId();

				if (StringUtils.isNotBlank(roleName)) {

					if (StringUtils.isNotBlank(role_texts)) {
						role_texts += ",";
						roleIds += ",";
						role_texts += roleName;
						roleIds += roleId;
					}
					else {
						role_texts += roleName;
						roleIds += roleId;
					}

					systemuser.getRoleIdset().add(roleId);
				}

			}
		}

		systemuser.setRoleIds(roleIds);
		systemuser.setRole_texts(role_texts);

		
		List<Group> groups = groupMapper
				.selectBySql("SELECT a.* FROM groups a, usergroup b WHERE a.id = b.groupId AND b.userId ="
						+ systemuser.getId());

		if (groups != null) {

			for (Group systemuserproject : groups) {
				String projectName = systemuserproject.getName();
				Long projectId = systemuserproject.getId();

				if (StringUtils.isNotBlank(projectName)) {

					if (StringUtils.isNotBlank(project_texts)) {
						project_texts += ",";
						project_texts += projectName;

						projectIds += ",";
						projectIds += projectId;
					}
					else {
						projectIds += projectId;
						project_texts += projectName;
					}
				}

			}
		}
		


		systemuser.setGroupIds(projectIds);
		systemuser.setGroup_texts(project_texts);

	}

	@Transactional(rollbackFor = Exception.class)
	public Long add(User systemuser, String groupIds, String roleIds,
			UserProfile optUser) {
		Long userId = saveSystemUser(systemuser);

		if (userId > 0) {
			String groupId_array[] = groupIds.split(",");
			for (String groupId : groupId_array) {
				UserGroup userGroup = new UserGroup();
				userGroup.setGroupId(Long.parseLong(groupId));
				userGroup.setUserId(userId);

				userGroupMapper.insert(userGroup);
			}

			String roleId_array[] = roleIds.split(",");
			for (String roleId : roleId_array) {

				if (StringUtils.isNotBlank(roleId)) {

					UserRole userRole = new UserRole();
					userRole.setModifyTime(new Date());
					userRole.setRoleId(Long.parseLong(roleId));
					userRole.setUserId(userId);

					userRoleMapper.insert(userRole);
				}
			}
			
			userServiceLog.add(systemuser, groupIds, roleIds, optUser);

			return userId;

		}
		else {
			return userId;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Long edit(User systemuser, String groupIds, String roleIds,
			UserProfile optUser) {
		Long userId = updateSystemUser(systemuser);
		
		if (userId > 0) {
			userRoleMapper.deleteByUserId(userId);
			userGroupMapper.deleteByUserId(userId);

			String groupId_array[] = groupIds.split(",");
			for (String groupId : groupId_array) {
				UserGroup userGroup = new UserGroup();
				userGroup.setGroupId(Long.parseLong(groupId));
				userGroup.setUserId(userId);

				userGroupMapper.insert(userGroup);
			}

			String roleId_array[] = roleIds.split(",");
			for (String roleId : roleId_array) {
				if (StringUtils.isNotBlank(roleId)) {

					UserRole userRole = new UserRole();
					userRole.setModifyTime(new Date());
					userRole.setRoleId(Long.parseLong(roleId));
					userRole.setUserId(userId);

					userRoleMapper.insert(userRole);
				}
			}

			userServiceLog.edit(systemuser, groupIds, roleIds, optUser);
			return userId;
		}
		else {
			return userId;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void assignSystemUserRole(String userIds, String roleIds) {
		String userId_array[] = userIds.split(",");

		for (String userId : userId_array) {

			String roleId_array[] = roleIds.split(",");
			userRoleMapper.deleteByUserId(Long.parseLong(userId));

			for (String roleId : roleId_array) {
				UserRole userRole = new UserRole();
				userRole.setModifyTime(new Date());
				userRole.setRoleId(Long.parseLong(roleId));
				userRole.setUserId(Long.parseLong(userId));
				userRoleMapper.insert(userRole);
			}
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void assignSystemUserGroup(String userIds, String groupIds) {
		String userId_array[] = userIds.split(",");

		for (String userId : userId_array) {

			String groupId_array[] = groupIds.split(",");
			userGroupMapper.deleteByUserId(Long.parseLong(userId));

			for (String groupId : groupId_array) {

				UserGroup userGroup = new UserGroup();
				userGroup.setGroupId(Long.parseLong(groupId));
				userGroup.setUserId(Long.parseLong(userId));

				userGroupMapper.insert(userGroup);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(String userIds, UserProfile optUser) {
		String userId_array[] = userIds.split(",");

		for (String userId : userId_array) {
			userGroupMapper.deleteByUserId(Long.parseLong(userId));
			userRoleMapper.deleteByUserId(Long.parseLong(userId));
			userMapper.deleteById(Integer.parseInt(userId));

		}
		userServiceLog.delete(userIds, optUser);
	}

	@Transactional(readOnly = true)
	public boolean userNameExist(String username) {
		String sql = "select a.* from users a where a.username = '" + username
				+ "'";
		List<User> systemusers = userMapper.selectBySql(sql);

		if (systemusers != null && systemusers.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional(readOnly = true)
	public boolean phoneExist(String phone) {
		String sql = "select a.* from users a where a.phone = '" + phone
				+ "'";
		List<User> systemusers = userMapper.selectBySql(sql);

		if (systemusers != null && systemusers.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Transactional(readOnly = true)
	public User getUserByUserId(Long userId) {
	//	String sql = "select a.* , b.projectid from users a left join userproject b on a.id = b.userid where a.id =" + userId;
		List<User> systemusers = userMapper.selectById(userId);
		User systemuser = systemusers.get(0);
		if (systemuser != null) {
			tem_fillSystemuser(systemuser);

			return systemuser;
		}
		else {
			return null;
		}
	}
	


	private void tem_fillSystemuser(User systemuser) {
		// TODO Auto-generated method stub
		String role_texts = "";
		String roleIds = "";
		String project_texts = "";
		String projectIds = "";

		List<Role> roles = roleMapper
				.selectBySql("SELECT a.* FROM role a, userrole b WHERE a.id = b.roleId AND b.userId ="
						+ systemuser.getId());
		if (roles != null) {

			for (Role systemuserrole : roles) {
				String roleName = systemuserrole.getName();
				Long roleId = systemuserrole.getId();

				if (StringUtils.isNotBlank(roleName)) {

					if (StringUtils.isNotBlank(role_texts)) {
						role_texts += ",";
						roleIds += ",";
						role_texts += roleName;
						roleIds += roleId;
					}
					else {
						role_texts += roleName;
						roleIds += roleId;
					}

					systemuser.getRoleIdset().add(roleId);
				}

			}
		}

		systemuser.setRoleIds(roleIds);
		systemuser.setRole_texts(role_texts);
		
		String sql = " SELECT a.* FROM project a left join userproject b on  a.id = b.projectId where b.userId  =" +systemuser.getId();
		
		//List<Project> projects = projectMapper.selectBySql(sql);

		List<Project> projects = projectMapper.selectBysql(sql);
		
			if (projects != null) {
			
				for (Project systemuserproject : projects) {
					String projectName = systemuserproject.getProjectname();
					Long projectId = systemuserproject.getId();
			
					if (StringUtils.isNotBlank(projectName)) {
			
						if (StringUtils.isNotBlank(project_texts)) {
							project_texts += ",";
							project_texts += projectName;
			
							projectIds += ",";
							projectIds += projectId;
						}
						else {
							projectIds += projectId;
							project_texts += projectName;
						}
					}
			
				}
			}
		systemuser.setProjectIds(projectIds);
		systemuser.setProject_texts(project_texts);
	}

	@Override
	public PagingBean<User> paging(int pageNumber, int pageSize,
			String sortCriterion, String sortDirection, User queryCondition,
			String in_userIds, String notin_userIds, Long loginUserId, boolean exportflag) {
		User user = userMapper.selectById2(loginUserId);
		String sql = "select a.* from role a left join userrole b on b.roleid = a.id where b.userid =" + loginUserId;
		List<Role> roles = roleMapper.selectBySql(sql);
		String pagingQueryLanguage = null;
		for(Role roletemp : roles){
			if(roletemp.getName().equals("超级管理员")||roletemp.getName().equals("销售助理")){
				 pagingQueryLanguage = "select * from (select DISTINCT a.*,r.name from users a left join userrole b on a.id = b.userId left join role r on b.roleid=r.id order by a.modifytime DESC) a where 1=1";                    
			}
			else{
				 pagingQueryLanguage = "select * from (select DISTINCT a.*,r.name from users a left join userrole b on a.id = b.userId left join role r on b.roleid=r.id order by a.modifytime DESC) a where 1=1 and a.company = '" + user.getCompany() + "'";                            
				
			}
		}
		
		
		Map<String, Object> parameters = new HashMap<String, Object>(0);
		
			// String countpagingQueryLanguage =
		// "select count(DISTINCT a) from users a left join a.systemuserroles b left join a.systemuserprojects c where 1=1  ";
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
		// StringBuilder countsbHQL = new
		// StringBuilder(countpagingQueryLanguage);

//		if (StringUtils.isNotBlank(queryCondition.getUserName())) {
//
//			String loginName = queryCondition.getUserName();
//
//			sbHQL.append(" and a.username like '%");
//			sbHQL.append(loginName);
//			sbHQL.append("%'");
//
//		}
		
		
		if (StringUtils.isNotBlank(queryCondition.getPhone())) {	

			String phone = queryCondition.getPhone().trim();

			sbHQL.append(" and a.phone like '%");
			sbHQL.append(phone);
			sbHQL.append("%'");

		}

		if (StringUtils.isNotBlank(queryCondition.getRealName())) {

			String realName = queryCondition.getRealName().trim();

			sbHQL.append(" and a.realName like '%");
			sbHQL.append(realName);
			sbHQL.append("%'");

		}
		
		if (StringUtils.isNotBlank(queryCondition.getCompany())) {	

//			String company = queryCondition.getCompany();
			
			String companysql = "select a.* from company a where a.companyname = '" + queryCondition.getCompany() + "'";
			List<Company> company = companyMapper.selectBySql(companysql);
			String companyid = company.get(0).getId().toString();
			sbHQL.append(" and a.company = '");
			sbHQL.append(companyid);
			sbHQL.append("'");

		}
		
//		if (StringUtils.isNotBlank(queryCondition.getProject())) {	
//
//			String project = queryCondition.getProject();
//
//			sbHQL.append(" and e.projectname like '%");
//			sbHQL.append(project);
//			sbHQL.append("%'");
//
//		}
		
		
		if (StringUtils.isNotBlank(queryCondition.getRoleIds())) {
			sbHQL.append(" and b.roleId in (");
			sbHQL.append(queryCondition.getRoleIds());
			sbHQL.append(")");

		}

		if (StringUtils.isNotBlank(queryCondition.getStartDate_modifyTime())) {
			sbHQL.append(" and a.modifyTime >= '");
			if (queryCondition.getStartDate_modifyTime().length() > 11) {
				sbHQL.append(queryCondition.getStartDate_modifyTime());
			}
			else {
				sbHQL.append(queryCondition.getStartDate_modifyTime()
						+ " 00:00:00");
			}
			sbHQL.append("'");

		}

		if (StringUtils.isNotBlank(queryCondition.getEndDate_modifyTime())) {
			sbHQL.append(" and a.modifyTime <= '");
			if (queryCondition.getEndDate_modifyTime().length() > 11) {
				sbHQL.append(queryCondition.getEndDate_modifyTime());
			}
			else {
				sbHQL.append(queryCondition.getEndDate_modifyTime()
						+ " 23:59:59");
			}
			sbHQL.append("'");

		}

		if (StringUtils.isNotBlank(queryCondition.getStartDate_createTime())) {
			sbHQL.append(" and a.createTime >= '");
			if (queryCondition.getEndDate_createTime().length() > 11) {
				sbHQL.append(queryCondition.getStartDate_createTime());
			}
			else {
				sbHQL.append(queryCondition.getStartDate_createTime()
						+ " 23:59:59");
			}
			sbHQL.append("'");

		}

		if (StringUtils.isNotBlank(queryCondition.getEndDate_createTime())) {
			sbHQL.append(" and a.createTime <= '");
			if (queryCondition.getEndDate_createTime().length() > 11) {
				sbHQL.append(queryCondition.getEndDate_createTime());
			}
			else {
				sbHQL.append(queryCondition.getEndDate_createTime()
						+ " 23:59:59");
			}
			sbHQL.append("'");

		}

		if (in_userIds != null) {
			if (StringUtils.isNotBlank(in_userIds)) {
				sbHQL.append(" and a.id in (");
				sbHQL.append(in_userIds);
				sbHQL.append(")");

			}
			else {
				sbHQL.append(" and a.id in (");
				sbHQL.append("-1");
				sbHQL.append(")");
			}
		}

		if (StringUtils.isNotBlank(notin_userIds)) {
			sbHQL.append(" and a.id not in (");
			sbHQL.append(notin_userIds);
			sbHQL.append(")");

		}

		if (loginUserId != null) {
			parameters.put("loginUserId", loginUserId);
			sbHQL.append(" and (select count(DISTINCT bb.menuId) from rolemenu bb where bb.roleId in (select cc.roleId from userrole cc where cc.userId = a.id)) ");
			sbHQL.append("-    (select count(DISTINCT bb.menuId) from rolemenu bb where bb.roleId in (select cc.roleId from userrole cc where cc.userId = a.id) ");
			sbHQL.append("    and bb.menuId in (select cc.menuId FROM rolemenu cc where cc.roleId in (select dd.roleId from userrole dd where dd.userId ="
					+ loginUserId + " ))");
			sbHQL.append(") <= 0");

//			sbHQL.append(" and a.id NOT IN (");
//			sbHQL.append("	SELECT DISTINCT userid FROM usergroup WHERE groupid NOT IN ( ");
//			sbHQL.append("		SELECT g.id FROM groups g JOIN usergroup b ON g.link LIKE CONCAT(CONCAT('%,', b.groupid), ',%') AND b.userid = "
//					+ loginUserId);
//			sbHQL.append(")");
//			sbHQL.append(")  ");

			// if (StringUtils.isNotBlank(queryCondition.getGroupIds())) {
			// sbHQL.append(" and (select count(DISTINCT cc.groupId) from usergroup cc where cc.userId = a.id) ");
			// sbHQL.append("-    (select count(DISTINCT cc.groupId) from usergroup cc where cc.userId = a.id ");
			// sbHQL.append("    and cc.groupId in (" +
			// queryCondition.getGroupIds()
			// + " ))");
			// sbHQL.append(" <= 0");
			//
			//
			// }

		}

		String[] sortCriterions = null;
		if (sortCriterion == null) {
			sortCriterion = "modifyTime";
		}
		if (sortCriterion != null) {
			if(sortCriterion.equals("name")){
				sortCriterions = new String[] { "a." + sortCriterion };
			}
			else{
				sortCriterions = new String[] { "a." + sortCriterion };
			}
			
		}
		PagingSortDirection[] sortDirections = null;
		if (sortDirection != null) {
			sortDirections = new PagingSortDirection[] { "asc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}

		PagingBean<User> pagingSystemusers = pagingDao.query(userMapper,
				sbHQL.toString(), sortCriterions, sortDirections, pageNumber,
				pageSize,  exportflag);

		List<User> systemusers = pagingSystemusers.getDatas();
		for (User systemuser : systemusers) {

			fillSystemuser(systemuser);
		}

		return pagingSystemusers;
	}

	@Override
	public String getGroupIdsByUserId(Long userId) {
		String sql = "select a.* from usergroup a where a.userId = " + userId;
		StringBuffer sb = new StringBuffer();
		Set<Long> groupIds = new HashSet<Long>();
		List<UserGroup> systemuserprojects = userGroupMapper.selectBySql(sql);
		for (UserGroup systemuserproject : systemuserprojects) {
			// Project project = systemuserproject.getProject();
			groupIds.add(systemuserproject.getGroupId());
			// getChildProjectIds(systemuserproject.getGroupId(), groupIds);
		}

		for (Long groupId : groupIds) {
			if (sb.length() == 0) {
				sb.append(groupId);
			}
			else {
				sb.append(",");
				sb.append(groupId);
			}
		}

		return sb.toString();
	}

	@Transactional(rollbackFor = Exception.class)
	public void resetPwd(String userIds, UserProfile optUser) throws Exception {
		String userId_array[] = userIds.split(",");

		for (String userId : userId_array) {
			List<User> users = userMapper.selectById(Long.parseLong(userId));
			if (users == null) {
				throw new PmaxException("当前用户" + userId + "不存在，无法完成密码修改！");
			}
			User user = users.get(0);

			user.setPwd(encodeUtil.getPasswordMD5(newPwd));
			userMapper.update(user);

		}
		
		userServiceLog.resetPwd(userIds, optUser);

	}
	
	@Transactional(rollbackFor = Exception.class)
	public Long disableUser(String userIds, UserProfile optUser) throws Exception {
		String userId_array[] = userIds.split(",");
		User user = null;
		int count = 0; 
		for (String userId : userId_array) {
			List<User> users = userMapper.selectById(Long.parseLong(userId));
			if (users == null) {
				throw new PmaxException("当前用户" + userId + "不存在，无法完成用户禁用！");
			}
			 user = users.get(0);
			int status = user.getStatus();
			if(status == 1)
			{
				status = 0;
				user.setStatus(status);
				user.setModifyTime(new Date());
				userMapper.update(user);
				userServiceLog.disable(userIds, optUser);
				count++;
			}
			else{
//				throw new PmaxException("当前用户无法完成禁用修改！");
				status = 1;
				user.setStatus(status);
				user.setModifyTime(new Date());
				userMapper.update(user);
				userServiceLog.disable(userIds, optUser);
				count++;
			}
	//		user.setPwd(encodeUtil.getPasswordMD5(newPwd));
			
		}
		if(count == 0 ){
			return -1L;
		}
		else if( count < userId_array.length){
			return -2L;
		}
		else{
			return user.getId();
		}
		
//		userServiceLog.resetPwd(userIds, optUser);

	}

	@Override
	public ArrayList<AuthType> getAuthType() {
		String sql = "select * from tb_auth_type";
		ArrayList<AuthType> list = (ArrayList<AuthType>) authTypeMapper.selectBySql(sql);
		return list;
	}

	@Override
	public List<User> queryUserbyName(String username) {
		List<User> list = userMapper.selectByUsername(username);
		return list;
	}

	@Override
	public List<AuthType> queryProjectByCompany(String company) {
		List<AuthType> list = authTypeMapper.selectAuthTypeByCompany(company);
		return list;
	}
	
//	@Override
//	public Long save(String realname) {
//		User user = new User();
//		user.setCreateTime(new Date());
//		user.setRealName(realname);
//		long id = userMapper.insert(user);
//		return id;
//	}

	private boolean userPhoneExist(String phone) {
	// TODO Auto-generated method stub	
		List<User> systemUser = userMapper.selectByPhone(phone);
		if(systemUser != null && systemUser.size() > 0){
			return true;
		}
		else {
			return false;
		}	
}
	
	@Override
	public Long save(User user,String realName,String idcard,  String phone, String companynames, String department, String roleIds ,String projectIds,UserProfile optUser) {
		// TODO Auto-generated method stub
		//User user = new User();
		String roleIdcheck_array[] = roleIds.split(",");
		for (String roleId : roleIdcheck_array) {
			String sql = "select a.* from role a where a.id =" + roleId;
			List<Role> roles = roleMapper.selectBySql(sql);
			if(roles.get(0).getName().equals("商务人员")){
				String checksql = "select DISTINCT a.* from users a left join userrole b on a.ID = b.userid LEFT JOIN role c on b.roleid = c.id where c.name = '商务人员' and a.company = '" + companynames + "' and a.status = 1";
				List<User> users = userMapper.selectBySql(checksql);
				if(users != null && users.size() >= 1 ){
					return -3L;
				}
				else{
					continue;
				}
			}
			else{
				continue;
			}
		}
		
		
		boolean isExist = userPhoneExist(phone);
		if(isExist){
			return -2l;
		}
	
//		String sql = "select DISTINCT a.* from users a left join userrole b on a.ID = b.userid LEFT JOIN role c on b.roleid = c.id where c.name = '商务人员' and a.company = '广州神盾' and a.status = 1" ;                                                      
		
		user.setCreateTime(new Date());
		user.setModifyTime(new Date());
		user.setRealName(realName.trim());
		user.setIdcard(idcard);
		user.setPhone(phone);
		user.setCompany(companynames);
		user.setDepartment(department.trim());		
		user.setPwd(newPwd);
		String beforeMD5 = user.getPwd();
		// systemuser.setPswSalt(encodeUtils.getEncodedSalt());
		
		user.setPwd(encodeUtils.getPasswordMD5(beforeMD5));
		if (user.getPwd().equals(beforeMD5)) {
			logger.info("新增用户时,密码加密时异常");
			return -1l;
		}
		
		user.setRoleIds(roleIds);
		user.setStatus(CVal.UserStatus.valid);
		//modified by wangxiwei in 20180322
		//long id = userMapper.insert(user);
		userMapper.insert(user);
		long id = user.getId();
		if(id > 0){
			//角色
			String roleId_array[] = roleIds.split(",");
			
			for (String roleId : roleId_array) {
				if (StringUtils.isNotBlank(roleId)) {
					UserRole userRole = new UserRole();
					userRole.setModifyTime(new Date());
					userRole.setRoleId(Long.parseLong(roleId));
					userRole.setUserId(user.getId());
					userRoleMapper.insert(userRole);
				}
			}
			//项目
			String projectId_array[] = projectIds.split(",");
			
			for(String projectId :projectId_array){
				if(StringUtils.isNotBlank(projectId)){
					UserProject userProject = new UserProject();
					userProject.setProjectId(Long.parseLong(projectId));
					userProject.setUserId(user.getId());
					userProjectMapper.insert(userProject);
				}
			}
			
			
			userServiceLog.add(user, projectIds, roleIds, optUser);
			return id;
		}
		
		else {
			return id;
		}
	
	}



	@Override
	public Long edit( Integer id, String realName, String idcard, String phone, String companynames,
			String department, String roleIds, String projectIds, UserProfile optUser) {
		// TODO Auto-generated method stub
		User user = userMapper.selectById2(id); 
		user.setRealName(realName.trim());
		user.setIdcard(idcard);
		user.setPhone(phone);
		user.setCompany(companynames);
		user.setDepartment(department.trim());
		user.setRoleIds(roleIds);	
		user.setModifyTime(new Date());
		userMapper.update(user);
		if(user.getId()>0)
		{
			userRoleMapper.deleteByUserId(user.getId());
			String roleId_array[] = roleIds.split(",");
			for (String roleId : roleId_array) {
				if (StringUtils.isNotBlank(roleId)) {

					UserRole userRole = new UserRole();
					userRole.setModifyTime(new Date());
					userRole.setRoleId(Long.parseLong(roleId));
					userRole.setUserId(user.getId());

					userRoleMapper.insert(userRole);
				}
			}
			
			userProjectMapper.deleteByUserId(user.getId());
			String project_array[] = projectIds.split(",");
			for (String projectId :project_array ){
				if(StringUtils.isNotBlank(projectId)){
					
				UserProject userProject = new UserProject();
				userProject.setUserId(user.getId());
				userProject.setProjectId(Long.parseLong(projectId));
					
				userProjectMapper.insert(userProject);
				}
			}
			
			userServiceLog.edit(user, projectIds, roleIds, optUser);
			return user.getId();
		}
		else{
			return user.getId();
		}
		
	}
//	public Long edit(Integer id,User user,String realName,String idcard,  String phone, 
//	String companynames, String department, String roleIds) {
//// TODO Auto-generated method stub
//List<User> list = userMapper.selectById(id);
//
//User user = new User();
//user.setId(list.get(0).getId());
//user.setRealName(list.get(0).getRealName());
////user.setId(id);
////hardwareProduct.setCreatetime(list.get(0).getCreatetime());
////hardwareProduct.setHardwareProductName(list.get(0).getHardwareProductName());
////hardwareProduct.setErpCode(list.get(0).getErpCode());
////hardwareProduct.setHardwareProductVersion(list.get(0).getHardwareProductVersion());
////hardwareProduct.setOperateSystem(list.get(0).getOperateSystem());
////hardwareProduct.setHardwareStation(list.get(0).getHardwareStation());
////hardwareProduct.setModifytime(new Date());
////hardwareProductMapper.update(hardwareProduct);
//
//return user.getId();
//}

	@Override
	public List<TreeBean> getProjectTrees(Long loginUserId, Long userId) {
		// TODO Auto-generated method stub
	//	String sql = "select a.* from projectcompany a where a.projectid in(select b.projectid from userproject b where b.userid = )";
		//判断登录用户的角色
		List<Company> systemcompanys = new ArrayList<Company>();
		String sql = null;
		boolean flag = true;
		String RoleSql = "select DISTINCT a.* from role a left join userrole b on a.id = b.roleid where b.userid = " + loginUserId;
		List<Role> rolescheck =  roleMapper.selectBySql(RoleSql);
		for(Role temprole : rolescheck){
			if(temprole.getName() != null && (temprole.getName().equals("超级管理员")||temprole.getName().equals("销售助理") )){
				 flag = false;
				 break;
			}
			else{
				continue;
			}
		}
		if(flag){
			sql = "select DISTINCT a.* from company a left join projectcompany b on a.id = b.companyid  left join userproject c on b.projectid= c.projectid where c.userid =" + loginUserId;                           
			 systemcompanys = companyMapper.selectBySql(sql);
		}
		else{
			 sql = "select DISTINCT a.* from company a left join projectcompany b on a.id = b.companyid  where b.projectid is NOT NULL";                           
			 systemcompanys = companyMapper.selectBySql(sql);
		}
		
		//找出所有项目的公司父菜单
//		String companyregname = company.getCompanyregname();
//		if(companyregname.equals("普利商用")){
//			sql = "select DISTINCT a.* from company a left join projectcompany b on a.id = b.companyid  where b.projectid is NOT NULL";
//			systemcompanys = companyMapper.selectBySql(sql);
//		}
//		else{
//			systemcompanys.add(company);
//		}
		

		//保存当前用户下所有的项目id
		
	
		List<TreeBean> projectTreebeans = new ArrayList<TreeBean>();
		for(Company systemcompany : systemcompanys)
		{
			TreeBean projectTreebean = new TreeBean();
			//父节点
			projectTreebean.setId(systemcompany.getId());
			projectTreebean.setName(systemcompany.getCompanyname());
			//子节点
			String projectcompanysql = "select a.* from projectcompany a where a.companyid =" + systemcompany.getId();
			List<ProjectCompany> projectCompanies = projectCompanyMapper.selectBySql(projectcompanysql);
			for(ProjectCompany projectCompany : projectCompanies){
				
				TreeBean tem_projectTreebean = new TreeBean();
				tem_projectTreebean.setLeaf(true);
				tem_projectTreebean.setId(projectCompany.getProjectid());
				//查找对应项目节点
				String projectsql = "select a.* from project a where a.id =" + projectCompany.getProjectid();
				List<Project> temproject = projectMapper.selectBysql(projectsql);
				tem_projectTreebean.setName(temproject.get(0).getProjectname());
				
				tem_projectTreebean.setPid(projectCompany.getCompanyid());
				
				Collection<TreeBean> projectTreebeanChild = projectTreebean.getChildren();
				if(projectTreebeanChild == null){
					projectTreebeanChild = new ArrayList<TreeBean>();
				}
				projectTreebeanChild.add(tem_projectTreebean);
				projectTreebean.setChildren(projectTreebeanChild);
			}
			
			
			
			projectTreebeans.add(projectTreebean);
		}
		
		return projectTreebeans;
	}
	

	
	
	

	// /**
	// *
	// * @param project
	// * @param projectIds
	// */
	// private void getChildProjectIds(long pid, Set<Long> projectIds) {
	//
	// List<Group> childGroups =
	// groupMapper.selectBySql("SELECT * FROM groups WHERE pid = " + pid);
	// if (childGroups != null && childGroups.size() > 0) {
	// for (Group childProject : childGroups) {
	// projectIds.add(childProject.getId());
	// getChildProjectIds(childProject.getId(), projectIds);
	// }
	//
	// } else {
	// projectIds.add(pid);
	// }
	//
	// }

	// @Override
	// public List<TreeBean> getGroupTrees(Long opUserId, Long userId) {
	//
	//
	//
	//
	// return groupTreeBeans;
	//
	//
	// }
	//
	// private List<TreeBean> getGroupTree(Collection<Group> groups,
	// Set<Long> userGroupId, Set<Long> checkedGroupId) {
	//
	// List<TreeBean> groupTreeBeans = new ArrayList<TreeBean>();
	// for (Group group : groups) {
	// TreeBean treeBean = new TreeBean();
	// treeBean.setId(group.getId());
	// treeBean.setText(group.getName());
	//
	// if (checkedGroupId.contains(group.getId())) {
	// treeBean.setChecked(true);
	// } else {
	// treeBean.setChecked(false);
	// }
	//
	// treeBean.setParent(group.getPid());
	//
	// List<Group> childMenus =
	// groupMapper.selectBySql("SELECT * FROM groups WHERE pid = " +
	// group.getId());
	// if (childMenus != null && childMenus.size() > 0) {
	//
	//
	// treeBean.setChildren(getGroupTree(childMenus, userGroupId,
	// checkedGroupId));
	// if (treeBean.getChildren() == null || treeBean.getChildren().size() == 0)
	// {
	// continue;
	// }
	//
	// } else {
	// if (userGroupId != null && userGroupId.size() > 0) {
	//
	// if (!userGroupId.contains(group.getId())) {
	// continue;
	// }
	//
	// }
	// else {
	// continue;
	// }
	// }
	//
	// if (treeBean.getChildren() == null || treeBean.getChildren().size() == 0)
	// {
	// treeBean.setLeaf(true);
	// } else {
	// treeBean.setLeaf(false);
	// }
	//
	// groupTreeBeans.add(treeBean);
	//
	// }
	//
	// return groupTreeBeans;
	// }
	
}
