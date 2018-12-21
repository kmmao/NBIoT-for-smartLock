package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.routon.plcloud.common.model.Province;

public interface ProvinceMapper extends PageMapper<Province>{

	@Select("${sql}")
	List<Province> selectBySql(@Param("sql") String sql);
}
