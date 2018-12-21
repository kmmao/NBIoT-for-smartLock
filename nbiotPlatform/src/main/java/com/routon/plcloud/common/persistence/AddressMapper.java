package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.routon.plcloud.common.model.Address;

public interface AddressMapper extends PageMapper<Address>{

	@Select("${sql}")
	List<Address> selectBySql(@Param("sql") String sql);
	
	@Select("select name from address where code_a = #{address}")
	String selectByaddress(String address);
}
