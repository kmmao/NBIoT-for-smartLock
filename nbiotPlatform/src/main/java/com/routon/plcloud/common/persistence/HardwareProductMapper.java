package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.HardwareProduct;


/**
 * 
 * @author wangxiwei
 *
 */
public interface HardwareProductMapper extends PageMapper<HardwareProduct>{
	
	@Select("${sql}")
	List<HardwareProduct> selectBySql(@Param("sql") String sql);
	
	@Select("${sql}")
	List<String> selectBySql2(@Param("sql") String sql);
	
	@Select("${sql}")
	Integer selectCountSql(@Param("sql") String sql);
	
	@Select("select a.id, a.hardware_product_name as hardwareProductName, a.hardware_product_version as hardwareProductVersion,"
			+ "a.erpcode, a.createtime, a.modifytime, a.hardware_station as hardwareStation,"
			+ "a.operate_system as operateSystem from hardwareproduct a where id = #{id}")
	HardwareProduct selectById(long id);
	
	@Select("${sql} limit #{rowCount} offset #{offset}")
	List<HardwareProduct> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
	
	@Insert("INSERT INTO hardwareproduct (hardware_product_name, hardware_product_version, erpcode, createtime, modifytime, hardware_station, operate_system) "
			+ "VALUES (#{hardwareProductName},#{hardwareProductVersion},#{erpCode},#{createtime},#{modifytime},#{hardwareStation},#{operateSystem})")
	@SelectKey(statement="SELECT currval('hardwareproduct_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
	long insert(HardwareProduct hardwareProduct);
	
	@Update("UPDATE hardwareproduct SET hardware_product_name=#{hardwareProductName},hardware_product_version=#{hardwareProductVersion},erpcode=#{erpCode},createtime=#{createtime},modifytime=#{modifytime},hardware_station=#{hardwareStation},operate_system=#{operateSystem}"
			+ " WHERE id = #{id} ")
	void update(HardwareProduct hardwareProduct);
	
	@Delete("DELETE FROM hardwareproduct WHERE id = #{id}")
	void deleteById(long id);
	
	@Select("select id from hardwareproduct where hardware_product_name = #{name}")
	Integer queryIdByName(String name);
	
}
