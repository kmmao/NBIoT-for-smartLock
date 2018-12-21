package com.routon.plcloud.common.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.SoftwareHardware;

public interface SoftwareHardwareMapper extends PageMapper<SoftwareHardware>{
	
	@Insert("INSERT INTO softwarehardware (softwareid,hardwareid,modifytime) "
			+ " VALUES (#{softwareid},#{hardwareid},#{modifytime})")
	@SelectKey(statement="SELECT currval('softwarehardware_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(SoftwareHardware softwareHardware);
}
