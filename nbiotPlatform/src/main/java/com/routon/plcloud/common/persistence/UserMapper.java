package com.routon.plcloud.common.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.User;
//import com.routon.plcloud.common.model.User1;


public interface UserMapper extends PageMapper<User> {
	//@Select("${sql} limit #{offset} , #{rowCount}")
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<User> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Select("${sql}")
	List<User> selectBySql(@Param("sql") String sql);
	
	@Select("${sql}")
	List<String> querybySQL(@Param("sql") String sql);

	@Insert("INSERT INTO users (username,realName,pwd,phone,company,status,modifytime,createtime,createUserId,lastLoginIP,lastLoginTime,project,idcard,department) "
			+ "VALUES (#{userName},#{realName},#{pwd},#{phone},#{company},#{status},#{modifyTime},#{createTime},#{createUserId},#{lastLoginIP},#{lastLoginTime},#{project},#{idcard},#{department})")
	@SelectKey(statement="SELECT currval('users_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(User user);

	@Update("UPDATE  users SET username=#{userName},realName=#{realName},pwd=#{pwd},phone=#{phone},company=#{company},status=#{status},modifytime=#{modifyTime},createtime=#{createTime},createUserId=#{createUserId},lastLoginIP=#{lastLoginIP},lastLoginTime=#{lastLoginTime},project=#{project},idcard=#{idcard},department=#{department}"
			+ " WHERE id = #{id} ")
	void update(User user);
	
	@Delete("DELETE FROM users WHERE id =#{id}")
	void deleteById(long id);
	
	
	@Select("select * from users where id = #{id}")
	List<User> selectById(long id);
	
	@Select("select * from users where id = #{id}")
	User selectById2(long id);

//	@Select("SELECT a.id,a.realname,a.idcard,a.phone,a.company,a.department,a.createtime,a.modifytime,a.createuserid,c.id as roleid ,c.name as rolename FROM users a LEFT JOIN userrole b on a.id = b.userid LEFT JOIN role c on b.roleid = c.id WHERE a.id = #{id}")
//	User1 selectById1(long id);
	
	@Select("select * from users where username = #{username}")
	List<User> selectByUsername(String username);
	//通过电话号码查询（电话作为登录名）
	@Select("select * from users where phone = #{phone}")
	List<User> selectByPhone(String phone);
}
