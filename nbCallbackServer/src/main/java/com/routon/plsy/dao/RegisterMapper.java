package com.routon.plsy.dao;

import org.apache.ibatis.annotations.*;

import com.routon.plsy.model.Register;

public interface RegisterMapper {
		@Insert("insert into register(name,id,gender,idcard_no,nation,addr,image_url,birth,agency,valid_date,command,version,update_time,role,device_id,term_id) "
				+ "values(#{name},#{id},#{gender},#{idcard_no},#{nation},#{addr},#{image_url},#{birth},#{agency},#{valid_date},#{command},#{version},#{update_time},#{role},#{device_id},#{term_id})")
		@Options(useGeneratedKeys=true, keyProperty="face_id", keyColumn="face_id")
		public int insertRegister(Register register);

		@Select("SELECT * FROM register WHERE id = #{id}")
		public Register selectById(String id);
		
		@Update("UPDATE register SET id=#{id},idcard_no=#{idcard_no},addr=#{addr},image_url=#{image_url},agency=#{agency},valid_date=#{valid_date},version=#{version},update_time=#{update_time},role=#{role},device_id=#{device_id},term_id=#{term_id} WHERE face_id=#{face_id}")
		public int updateById(Register register);

		@Select("SELECT * FROM register WHERE face_id = #{faceid} AND device_id = #{deviceid}")
		public Register selectByfaceIdAndDeviceid(@Param("faceid") int faceid, @Param("deviceid") String deviceid);

		@Update("update register set role = #{role} where face_id = #{face_id} AND device_id = #{device_id}")
		int updateRole(@Param("role") String role,@Param("face_id") int face_id, @Param("device_id") String device_id);

		@Select("select role from register where face_id = #{face_id} AND device_id = #{device_id}")
		String queryRoleByFaceid(@Param("face_id") int face_id, @Param("device_id") String device_id);
}
