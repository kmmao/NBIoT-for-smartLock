package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.CommandLog;

public interface CommandLogMapper extends PageMapper<CommandLog> {
	//@Select("${sql} limit #{offset} , #{rowCount}")
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<CommandLog> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<CommandLog> selectBySql(@Param("sql") String sql);
	
	@Insert("insert into commandlog(deviceid,commandtype,commandstate,commandtime,commandid,commandstr,face_id,report_white,report_sendwhite) "
			+ "values(#{deviceid},#{commandtype},#{commandstate},#{commandtime},#{commandid},#{commandstr},#{face_id},#{report_white},#{report_sendwhite})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public int insertCommand(CommandLog CommandLog);
	
	@Update("UPDATE commandlog SET report_white=#{report_white} WHERE id=#{id}")
	public int updateMark(@Param("id")int id, @Param("report_white") int report_white);

	@Insert("insert into commandlog(deviceid,commandtype,commandtime,face_id,report_manager)"
			+ "values(#{deviceid},#{commandtype},#{commandtime},#{face_id},#{report_manager})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public int insertManagerCommand(CommandLog CommandLog);
}
