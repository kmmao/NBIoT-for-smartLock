package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.SubOrder;

public interface SuborderMapper extends PageMapper<SubOrder>{

	@Select("${sql}")
	List<SubOrder> selectBysql(@Param("sql") String sql);
	
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<SubOrder> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Insert("INSERT INTO suborder (subordernum,contactname,createtime,status,renewnum,remark,modifytime)"
			+ " VALUES (#{subordernum},#{contactname},#{createtime},#{status},#{renewnum},#{remark},#{modifytime})")
	@SelectKey(statement="SELECT currval('suborder_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(SubOrder suborder);
	
	@Update("UPDATE suborder SET subordernum=#{subordernum},contactname=#{contactname},createtime=#{createtime},"
			+ "status=#{status},renewnum=#{renewnum},remark=#{remark},modifytime=#{modifytime} WHERE id = #{id} ")
	void update(SubOrder suborder);
	
	@Update("UPDATE suborder SET subordernum =#{subordernum} WHERE id = #{id} ")
	void updateSubordernum(SubOrder suborder);
	
	@Update("UPDATE suborder SET renewnum =#{renewnum},remark=#{remark},modifytime=#{modifytime} WHERE id = #{id} ")
	void updateSuborderRenewnum(SubOrder suborder);
	
	//修改审核状态
//	@Update("UPDATE \"order\" SET status =#{status} WHERE id = #{id} ")
//	void update2(Order order);
	
	@Select("select * from suborder where id = #{id}")
	SubOrder selectById(long id);
}
