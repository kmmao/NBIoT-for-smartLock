package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.routon.plcloud.common.model.Visitrecord;

public interface VisitRecordMapper extends PageMapper<Visitrecord> {

	//@Select("${sql} limit #{offset} , #{rowCount}")
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<Visitrecord> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<Visitrecord> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO visitrecord (face_id,name,roomnum,visit_time,operationtype,doorstate,lockstate,validtime) "
			+ " VALUES (#{face_id},#{name},#{roomnum},#{visit_time},#{operationtype},#{doorstate},#{lockstate},#{validtime})")
	long insert(Visitrecord visitrecord);
}
