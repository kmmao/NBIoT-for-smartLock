package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.SoftwareHardwarestation;

public interface SoftwareHardwarestationMapper extends PageMapper<SoftwareHardwarestation>{
	
	@Insert("INSERT INTO softwarehardwarestation (softwareid,hardwarestationid,modifytime) "
			+ " VALUES (#{softwareid},#{hardwarestationid},#{modifytime})")
	@SelectKey(statement="SELECT currval('softwarehardwarestation_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(SoftwareHardwarestation softwareHardwarestation);
	
	@Select("${sql}")
	List<SoftwareHardwarestation> selectBySql(@Param("sql") String sql);
}
