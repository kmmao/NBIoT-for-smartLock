package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.routon.plcloud.common.model.Area;

public interface AreaMapper extends PageMapper<Area>{

	@Select("${sql}")
	List<Area> selectBySql(@Param("sql") String sql);
	
	@Select("select name from area where code_a = #{address}")
	String selectByaddress(String address);
}
