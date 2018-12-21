package com.routon.plcloud.admin.nb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.service.dataCollection.QueryDevices;
import com.routon.plcloud.admin.nb.service.RegisterService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.Register;
import com.routon.plcloud.common.persistence.RegisterMapper;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;
	
	@Autowired
	private RegisterMapper registerMapper;
	
	public List<Register> getAllRegister() {
		// TODO Auto-generated method stub
		return registerMapper.getAllregister();
	}

	@Override
	public String queryDataByPerson(String personid) {
		return registerMapper.selectByFaceId(Integer.parseInt(personid));
	}

	@Override
	public String getDeviceInfo() {
		String devices = null;
		try {
			devices = QueryDevices.quryAllDevices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devices;
	}

	@Override
	public PagingBean<Register> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection, 
			Long loginUserId, boolean exportflag) {
		String pagingQueryLanguage = "select a.* from register a";
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
		String[] sortCriterions = null;
		if(sortCriterion != null){
			sortCriterions = new String[] { "a." + sortCriterion };
		}
		PagingSortDirection[] sortDirections =null;
		if(sortDirection != null){
			sortDirections = new PagingSortDirection[] { "desc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}
		PagingBean<Register> pagingRegister = pagingDao.query(registerMapper, sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);
		return pagingRegister;
	}

	@Override
	public int sendmanagerById(String role, Integer face_id) {
		
		return registerMapper.sendmanagerByid(role, face_id);
	}

	@Override
	public Register selectById(int face_id) {
		// TODO Auto-generated method stub
		return registerMapper.selectById(face_id);
	}
	

}
