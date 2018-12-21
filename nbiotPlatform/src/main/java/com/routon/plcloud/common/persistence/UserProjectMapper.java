package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.UserProject;

public interface UserProjectMapper extends PageMapper<UserProject>{

	
	@Select("${sql} limit #{offset} , #{rowCount}")
	List<UserProject> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<UserProject> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO userproject (userId,projectId) "
			+ " VALUES (#{userId},#{projectId})")
	@SelectKey(statement="SELECT currval('userproject_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(UserProject userProject);
	
//	@Delete("DELETE FROM rolemenu WHERE id =#{id}")
//	void deleteById(long id);
	
	@Delete("DELETE FROM userproject WHERE userId =#{userId}")
	int deleteByUserId(long userId);
}
