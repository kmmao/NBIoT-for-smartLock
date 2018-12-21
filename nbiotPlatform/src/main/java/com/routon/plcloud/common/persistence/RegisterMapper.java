package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.Register;

public interface RegisterMapper extends PageMapper<Register>{
		@Insert("insert into register(name,id,gender,idcard_no,nation,addr,image_url,birth,agency,valid_date,command,version,role,device_id,term_id) "
				+ "values(#{name},#{id},#{gender},#{idcard_no},#{nation},#{addr},#{image_url},#{birth},#{agency},#{valid_date},#{command},#{version},#{role},#{device_id},#{term_id})")
		@Options(useGeneratedKeys=true, keyProperty="face_id", keyColumn="face_id")
		public int insertRegister(Register register);

		@Select("SELECT * FROM register WHERE face_id = #{face_id}")
		public Register selectById(int face_id);
		
		@Select("SELECT idcard_no FROM register WHERE face_id = #{face_id}")
		public String selectByFaceId(int faceid);
		
		@Update("UPDATE register SET idcard_no=#{idcard_no},addr=#{addr},image_url=#{image_url},agency=#{agency},valid_date=#{valid_date},version=#{version},role=#{role},device_id=#{device_id},term_id=#{term_id} WHERE id=#{id}")
		public int updateById(Register register);
		
		@Select("SELECT * FROM register")
		public List<Register> getAllregister();
		
		@Select("${sql} limit #{rowCount} offset #{offset}")
		List<Register> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);

		@Update("update register set role = #{role} where face_id = #{face_id}")
		int sendmanagerByid(@Param("role")String role,@Param("face_id")Integer face_id);

}
