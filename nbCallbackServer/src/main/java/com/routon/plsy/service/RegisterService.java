package com.routon.plsy.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plsy.dao.RegisterMapper;
import com.routon.plsy.model.Register;

@Service
public class RegisterService {
	@Autowired(required = false)
	private RegisterMapper registerMapper;
	
	public int insertRegister(Register register) {
		return registerMapper.insertRegister(register);
	}
	
	public Register selectById(String id) {
		return registerMapper.selectById(id);
	}
	
	public int updateById(Register register) {
		return registerMapper.updateById(register);
	}

	public Register selectByfaceidAndDeviceid(int faceid, String deviceid){
		return registerMapper.selectByfaceIdAndDeviceid(faceid,deviceid);
	}
	
	public int updateRole(String role,int face_id, String deviceid) {
		return registerMapper.updateRole(role, face_id, deviceid);
	}
	
	public String queryRoleByFaceid(int face_id, String deviceid) {
		return registerMapper.queryRoleByFaceid(face_id, deviceid);
	}
}
