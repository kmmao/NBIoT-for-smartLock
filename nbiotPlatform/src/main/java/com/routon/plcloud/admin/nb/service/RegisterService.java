package com.routon.plcloud.admin.nb.service;

import java.util.List;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.Register;


public interface RegisterService {
	
	
	public PagingBean<Register> paging(int startIndex, int pageSize, String sortCriterion,
			String sortDirection, Long loginUserId, boolean exportflag);
	
	public List<Register> getAllRegister();
	
	public String queryDataByPerson(String personid);
	
	public String getDeviceInfo();
	
	public int sendmanagerById(String role,Integer face_id);
	
	public Register selectById(int face_id);
}
