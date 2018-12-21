package com.routon.plcloud.custom;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author wangxiwei
 *
 */
public class CustomUserEntityManagerFactory implements SessionFactory {

	private UserEntityManager userEntityManager; 
	
	@Autowired
	public void setUserEntityManager(UserEntityManager userEntityManager) {
        this.userEntityManager = userEntityManager;
    }
	
	@Override
	public Class<?> getSessionType() {

		return CustomUserEntityManager.class;
	}

	@Override
	public Session openSession() {

		return userEntityManager;
	}

}
