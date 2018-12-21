package com.routon.plcloud.custom;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * 
 * @author wangxiwei
 *
 */
public class CustomGroupEntityManagerFactory implements SessionFactory{


	private GroupEntityManager groupEntityManager;
	
	@Autowired
	public void setGroupEntityManager(GroupEntityManager groupEntityManager) {
        this.groupEntityManager = groupEntityManager;
    }
	
	@Override
	public Class<?> getSessionType() {

		return GroupEntityManager.class;
	}

	@Override
	public Session openSession() {

		return groupEntityManager;
	}

}
