package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.Position;

public interface PositionMapper extends PageMapper<Position> {
		@Select("select * from position")
		public List<Position> getAllPosition();
		
		@Delete("delete from position where id = #{id}")
		public int delPosition(Integer id);
		
		@Insert("insert into position(deviceid,addr,roomnum,name,phone,jurisdiction,jurisdictioncon)"
				+ "values(#{deviceid},#{addr},#{roomnum},#{name},#{phone},#{jurisdiction},#{jurisdictioncon})")
		public int insertPosition(Position position);
		
		@Update("update position set addr = #{addr},roomnum = #{roomnum},"
				+ "name = #{name},phone = #{phone},jurisdiction = #{jurisdiction},jurisdictioncon = #{jurisdictioncon} where deviceid = #{deviceid}")
		public int updatePosition(Position position);
		
		@Select("select * from position where deviceid = #{deviceid}")
		public Position selectBydeviceid(String deviceid);
		
		@Select("select * from position where deviceid = #{deviceid}")
		public Position queryPositionbyDeviceid(String deviceid);
		
		@Select("${sql} limit #{rowCount} offset #{offset}")
		List<Position> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
}
