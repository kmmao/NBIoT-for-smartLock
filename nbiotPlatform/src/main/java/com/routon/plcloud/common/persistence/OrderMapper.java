package com.routon.plcloud.common.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.Order;

/**
 * 
 * @author wangxiwei
 *
 */
public interface OrderMapper extends PageMapper<Order> {
	
	@Select("${sql}")
	List<Order> selectBysql(@Param("sql") String sql);
	
	@Select("${sql}")
	Map<String,Object> selectByCustomSql(@Param("sql") String sql);
	
	@Select("${sql}")
	int selectBysqlActiviti(@Param("sql") String sql);
	
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<Order> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Insert("INSERT INTO \"order\" (softwareerpnumber,softwaretypeversion,demandquantity,reminderdays,bindingmode,month,starttime,endtime"
			+ ",tacitstarttime,remarks,moditytime,createtime,status,verify) "
			+ " VALUES (#{softwareerpnumber},#{softwaretypeversion},#{demandquantity},#{reminderdays},"
			+ "#{bindingmode},#{month},#{starttime},#{endtime},#{tacitstarttime},#{remarks},#{moditytime},#{createtime},#{status},#{verify})")
	@SelectKey(statement="SELECT currval('order_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(Order order);
	
	@Update("UPDATE \"order\" SET ordernum =#{ordernum} WHERE id = #{id} ")
	void update(Order order);
	
	//审核通过修改状态
	@Update("UPDATE \"order\" SET verify = 1 WHERE ordernum = #{ordernum} ")
	void updateVervify(String ordernum);
	
	//续订修改审核状态
	@Update("UPDATE \"order\" SET verify = 2 WHERE ordernum = #{ordernum} ")
	void updateVervify1(String ordernum);
	
	//退订修改审核状态
	@Update("UPDATE \"order\" SET verify = 3 WHERE ordernum = #{ordernum} ")
	void updateVervify2(String ordernum);
	
	//开启修改审核状态
	@Update("UPDATE \"order\" SET verify = 4 WHERE id = #{id} ")
	void updateVervify3(Integer id);
	
	@Update("UPDATE \"order\" SET softwareerpnumber=#{softwareerpnumber},softwaretypeversion=#{softwaretypeversion},demandquantity=#{demandquantity},"
			+ "reminderdays=#{reminderdays},bindingmode=#{bindingmode},month=#{month},starttime=#{starttime},"
			+ "endtime=#{endtime},tacitstarttime=#{tacitstarttime},remarks=#{remarks},moditytime=#{moditytime} WHERE id = #{id} ")
	void update1(Order order);
	
	@Update("UPDATE \"order\" SET status =#{status} WHERE id = #{id} ")
	void update2(Order order);
	
	@Update("UPDATE \"order\" SET demandquantity =#{demandquantity},verify =#{verify} WHERE id = #{id} ")
	void updateDemandquantity(Order order);
	
	@Update("UPDATE \"order\" SET status =#{status},verify =#{verify} WHERE id = #{id} ")
	void updateStatusandVervify(Order order);
	
	@Update("UPDATE \"order\" SET demandquantity =#{demandquantity},remarks=#{remarks},moditytime=#{moditytime} WHERE id = #{id} ")
	void updateRetretOrder(Order order);
	
	@Select("select * from \"order\" where id = #{id}")
	Order selectById(long id);
//	Order selectById(Long id);
	
	@Select("select ordernum from \"order\" where id = #{id}")
	String selectById1(Long id);
	
	@Select("select * from \"order\" where ordernum = #{ordernum}")
	Order selectByOrdernum(String ordernum);
	
	//查询续订订单的数量
	@Select("select count(*) from \"order\" a left join order_suborder b on a.id= b.orderid where b.suborderid is not null and a.id= #{id}")
	int selectRenewCount(Long id);
}
