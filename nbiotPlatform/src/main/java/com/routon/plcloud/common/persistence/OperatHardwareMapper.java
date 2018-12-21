package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.OperaterHardware;

public interface OperatHardwareMapper extends PageMapper<OperaterHardware>{
	
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<OperaterHardware> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<OperaterHardware> selectBySql(@Param("sql") String sql);
	
	@Select("${sql}")
	List<String> selectBySql2(@Param("sql") String sql);
	
	@Insert("INSERT INTO operaterhardware (operatingsystemid,hardwarestationid,modifytime) "
			+ " VALUES (#{operatingsystemID},#{hardwarestationID},#{modifyTime})")
	@SelectKey(statement="SELECT currval('operaterhardware_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(OperaterHardware opHardware);
	
	@Delete("DELETE FROM operaterhardware WHERE id =#{id}")
	void deleteById(long id);
	
	@Delete("DELETE FROM operaterhardware WHERE hardwarestationid =#{hardwarestationID}")
	void deleteByHardwareId(long hardwarestationID);
	
	@Delete("DELETE FROM operaterhardware WHERE operatingsystemid =#{opsystemId}")
	void deleteByOpsystemId(long opsystemId);
	
	@Select("SELECT * FROM operaterhardware WHERE hardwarestationID =#{hardwarestationID}")
	List<OperaterHardware> selectByHardwareId(long hardwarestationID);

}
