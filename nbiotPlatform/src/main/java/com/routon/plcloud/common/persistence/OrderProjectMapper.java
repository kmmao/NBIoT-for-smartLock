package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.OrderProject;

public interface OrderProjectMapper extends PageMapper<OrderProject>{
	@Insert("INSERT INTO orderproject (projectid,orderid,modifytime) "
			+ " VALUES (#{projectid},#{orderid},#{modifytime})")
	@SelectKey(statement="SELECT currval('orderproject_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(OrderProject orderproject);
	
	@Delete("DELETE FROM orderproject WHERE orderid =#{orderid}")
	void deleteByOrderId(long orderid);
	
	@Select("${sql}")
	List<OrderProject> selectBySql(@Param("sql") String sql);
	
	@Select("select count(*) from orderproject where projectid = #{projectid}")
	long selectCountByprojectId(long projectid);

}
