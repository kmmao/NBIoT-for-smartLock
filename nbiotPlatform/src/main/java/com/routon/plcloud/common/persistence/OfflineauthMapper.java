package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.OfflineAuth;

public interface OfflineauthMapper extends PageMapper<OfflineAuth>{
	
	@Insert("INSERT INTO offlineauth (termsn,termlicence) "
			+ "VALUES (#{termsn},#{termlicence})")
	@SelectKey(statement="SELECT currval('offlineauth_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(OfflineAuth offlineAuth);
	
	@Select("${sql}")
	List<OfflineAuth> selectBysql(@Param("sql") String sql);
}
