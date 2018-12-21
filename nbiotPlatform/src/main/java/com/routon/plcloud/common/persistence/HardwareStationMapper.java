package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.HardwareStation;
import com.routon.plcloud.common.model.OperatingSystem;

public interface HardwareStationMapper extends PageMapper<HardwareStation>{
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<HardwareStation> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<HardwareStation> selectBySql(@Param("sql") String sql);
	
	@Select("${sql}")
	List<OperatingSystem> selectBySql2(@Param("sql") String sql);
	
	@Insert("INSERT INTO hardwarestation (hardwarestationname,moditytime,createtime) "
			+ "VALUES (#{hardwarestationName},#{moditytime},#{createtime})")
	@SelectKey(statement="SELECT currval('hardware_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(HardwareStation hardwarestation);

	@Update("UPDATE  hardwarestation SET hardwarestationname=#{hardwarestationName},"
			+ "moditytime=#{moditytime},createtime=#{createtime}"
			+ " WHERE id = #{id} ")
	void update(HardwareStation hardwarestation);
	
	@Delete("DELETE FROM hardwarestation WHERE id =#{id}")
	void deleteById(long id);
	
	@Select("select * from hardwarestation")
	List<HardwareStation> findAllHardware();
	
	//按名称查询
	@Select("select * from hardwarestation where hardwarestationname = #{hardwarestationName}")
	List<HardwareStation> selectByHardwarename(String hardwarestationname);
	
	//按id查询
//	@Select("select * from hardwarestation where id=#{id}")
//	List<HardwareStation> selectById(long id);
	
	@Select("select * from hardwarestation where id=#{id}")
	HardwareStation selectById(long id);
}
