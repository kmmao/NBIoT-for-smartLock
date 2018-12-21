package com.routon.plcloud.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.routon.plcloud.admin.privilege.service.RoleService;
import com.routon.plcloud.common.model.Role;

/**
 * 
 * @author wangxiwei
 * 注意：如果想使用自己定义的用户组表，请重新定义
 * findGroupById(final String groupCode)和findGroupsByUser(final String userCode)，这两个方法是activiti查询的用户组的方法。
 */

public class CustomGroupEntityManager extends GroupEntityManager{

	@Autowired
	private RoleService roleService;
	
	@Override
	public Group createNewGroup(String groupId) {
		// TODO Auto-generated method stub
		return super.createNewGroup(groupId);
	}

	@Override
	public void insertGroup(Group group) {
		// TODO Auto-generated method stub
		super.insertGroup(group);
	}

	@Override
	public void updateGroup(Group updatedGroup) {
		// TODO Auto-generated method stub
		super.updateGroup(updatedGroup);
	}

	@Override
	public void deleteGroup(String groupId) {
		// TODO Auto-generated method stub
		super.deleteGroup(groupId);
	}

	@Override
	public GroupQuery createNewGroupQuery() {
		// TODO Auto-generated method stub
		return super.createNewGroupQuery();
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		// TODO Auto-generated method stub
		return super.findGroupByQueryCriteria(query, page);
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		// TODO Auto-generated method stub
		return super.findGroupCountByQueryCriteria(query);
	}

	/**
	 * 主要重写这个方法
	 */
	@Override
	public List<Group> findGroupsByUser(String userId) {
		
		List<Role> rolelist = roleService.getRoles(new Long(userId));
		List<Group> groups = new ArrayList<Group>(rolelist.size());
		for(Role role : rolelist){
			GroupEntity group = new GroupEntity();
			group.setName(role.getName());
			group.setId(String.valueOf(role.getId()));
			groups.add(group);
		}
		return groups;
	}

	@Override
	public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return super.findGroupsByNativeQuery(parameterMap, firstResult, maxResults);
	}

	@Override
	public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return super.findGroupCountByNativeQuery(parameterMap);
	}

	@Override
	public boolean isNewGroup(Group group) {
		// TODO Auto-generated method stub
		return super.isNewGroup(group);
	}
	
}
