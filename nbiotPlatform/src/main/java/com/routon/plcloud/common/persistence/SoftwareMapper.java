package com.routon.plcloud.common.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.Software;

public interface SoftwareMapper extends PageMapper<Software>{
	
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<Software> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	Integer selectCountSql(@Param("sql") String sql);
	
	@Select("${sql}")
	List<String> selectbysql(@Param("sql") String sql);
	
	@Select("${sql}")
	Map<String,String> selectbysql2(@Param("sql") String sql);
	
	@Insert("INSERT INTO softwareproduct (softwarename, softwareversion, erpcode, softwarecopyrightnumber, uploadtime, size, uploaduser, customername, correspondproject, correspondterminaltype) "
			+ "VALUES (#{softwareName},#{softwareVersion},#{erpCode},#{softwareCopyrightNumber},#{uploadTime},#{size},#{uploadUser},#{customerName},#{correspondProject},#{correspondTerminalType})")
	@SelectKey(statement="SELECT currval('softwareproduct_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(Software software);
}
