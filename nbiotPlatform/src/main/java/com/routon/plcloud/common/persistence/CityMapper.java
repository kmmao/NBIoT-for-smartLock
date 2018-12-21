package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.routon.plcloud.common.model.City;

public interface CityMapper extends PageMapper<City>{

	@Select("${sql}")
	List<City> selectBySql(@Param("sql") String sql);
	
	@Select("select name from city where code_c = #{city}")
	String selectBycity(String city); 
}
