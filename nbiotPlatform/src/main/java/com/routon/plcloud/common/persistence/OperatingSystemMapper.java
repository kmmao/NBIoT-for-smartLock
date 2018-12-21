package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.OperatingSystem;

public interface OperatingSystemMapper extends PageMapper<OperatingSystem>{
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<OperatingSystem> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<OperatingSystem> selectBySql(@Param("sql") String sql);
	
	@Insert("INSERT INTO operatingsystem (operatingsystemname,moditytime,createtime) "
			+ "VALUES (#{operatingsystemname},#{modityTime},#{createTime})")
	@SelectKey(statement="SELECT currval('operatingsystem_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(OperatingSystem operatingsystem);

	@Update("UPDATE  operatingsystem SET operatingsystemname=#{operatingsystemname},moditytime=#{modityTime},createtime=#{createTime}"
			+ " WHERE id = #{id} ")
	long update(OperatingSystem operatingsystem);
	
	@Delete("DELETE FROM operatingsystem WHERE id =#{id}")
	void deleteById(long id);
	
	@Select("select * from operatingsystem")
	List<OperatingSystem> findAllOperatingSystem();
	
	//按操作系统名称查询
	@Select("select * from operatingsystem where operatingsystemname = #{operatingsystemname}")
	List<OperatingSystem> selectByOperatingSystemname(String operatingsystemname);
	
	//按操作系统id查询
	@Select("select * from operatingsystem where id=#{id}")
	OperatingSystem selectById(long id);

}
