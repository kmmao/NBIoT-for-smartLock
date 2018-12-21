package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.OrderSuborder;

public interface OrderSuborderMapper extends PageMapper<OrderSuborder>{

	@Insert("INSERT INTO order_suborder (orderid,suborderid,modifytime) "
			+ " VALUES (#{orderid},#{suborderid},#{modifytime})")
	@SelectKey(statement="SELECT currval('order_suborder_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(OrderSuborder ordersuborder);
	
	@Delete("DELETE FROM order_suborder WHERE suborderid =#{suborderid}")
	void deleteBySuborderId(long suborderid);
	
	@Select("${sql}")
	List<OrderSuborder> selectBySql(@Param("sql") String sql);
	
	@Select("select count(*) from order_suborder where orderid = #{orderid}")
	long selectCountByorderId(long orderid);
}
