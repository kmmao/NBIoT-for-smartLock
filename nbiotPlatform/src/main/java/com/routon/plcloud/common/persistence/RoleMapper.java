package com.routon.plcloud.common.persistence;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.Role;



public interface RoleMapper extends PageMapper<Role> {
	
	//@Select("${sql} limit #{offset} , #{rowCount}")
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<Role> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<Role> selectBySql(@Param("sql") String sql);
	
	@Select("${sql}")
	Map<String,String> selectBySql2(@Param("sql") String sql);
	
	
	@Insert("INSERT INTO role (name,remark,status,createTime,createUserId) "
			+ " VALUES (#{name},#{remark},#{status},#{createTime},#{createUserId})")
	@SelectKey(statement="SELECT currval('role_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(Role role);

	@Update("UPDATE  role SET name=#{name},remark=#{remark},status=#{status},createTime=#{createTime},modifyTime=#{modifyTime},createUserId=#{createUserId}"
			+ " WHERE id = #{id} ")
	void update(Role role);
	
	@Delete("DELETE FROM role WHERE id =#{id}")
	void deleteById(long id);
	
	@Select("select * from role where id=#{id}")
	Role selectById(long id);
	
	@Select("select * from role where id=#{id}")
	List<Role> selectById2(long id);
}
