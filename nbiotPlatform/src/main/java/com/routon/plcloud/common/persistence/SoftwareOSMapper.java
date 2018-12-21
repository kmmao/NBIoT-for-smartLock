package com.routon.plcloud.common.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.SoftwareOS;

public interface SoftwareOSMapper extends PageMapper<SoftwareOS> {
	
	@Insert("INSERT INTO softwareoperatingsystem (softwareid,osid,modifytime) "
			+ " VALUES (#{softwareid},#{osid},#{modifytime})")
	@SelectKey(statement="SELECT currval('softwareos_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(SoftwareOS softwareOS);
}
