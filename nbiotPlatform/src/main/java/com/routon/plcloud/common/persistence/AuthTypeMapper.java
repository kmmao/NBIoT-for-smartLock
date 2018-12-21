package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.routon.plcloud.common.model.AuthType;

public interface AuthTypeMapper extends PageMapper<AuthType>{
	
	@Select("${sql}")
	List<AuthType> selectBySql(@Param("sql") String sql);
	
	@Select("select * from tb_auth_type where company_name = #{companyname}")
	List<AuthType> selectAuthTypeByCompany(String companyname);
	
}
