package com.routon.plcloud.common.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.routon.plcloud.common.model.Company;


public interface CompanyMapper extends  PageMapper<Company>{
	//@Select("${sql} limit #{offset} , #{rowCount}")
		@Select("${sql} limit #{rowCount} offset #{offset}")
		List<Company> selectByCondition(@Param("sql") String sql,  @Param("offset")int offset, @Param("rowCount")int rowCount);
		
		@Select("${sql}")
		List<Company> selectBySql(@Param("sql") String sql);
		
		@Select("${sql}")
		Map<String,String> selectBySql2(@Param("sql") String sql);
		
		
		@Insert("INSERT INTO company (companyname,companyregname,companyrep,englishname,namespell,organizationcode,trade,status,contactname,contactphone,contactfixedphone,email,salename,country,province,city,area,address,zipcode,moditytime,createtime) "
				+ " VALUES (#{companyname},#{companyregname},#{companyrep},#{englishname},#{namespell},#{organizationcode},#{trade},#{status},#{contactname},#{contactphone},#{contactfixedphone},#{email},#{salename},#{country},#{province},#{city},#{area},#{address},#{zipcode},#{moditytime},#{createtime})")
		@SelectKey(statement="SELECT currval('company_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class) 
		long insert(Company company);

		@Update("UPDATE  company SET companyname=#{companyname},companyregname=#{companyregname},companyrep=#{companyrep},englishname=#{englishname},namespell=#{namespell},organizationcode=#{organizationcode},trade=#{trade},status=#{status},contactname=#{contactname},contactphone=#{contactphone},contactfixedphone=#{contactfixedphone},email=#{email},salename=#{salename},country=#{country},province=#{province},city=#{city},area=#{area},address=#{address},zipcode=#{zipcode},moditytime=#{moditytime},createtime=#{createtime}"
				+ " WHERE id = #{id} ")
		void update(Company company);
		
		@Delete("DELETE FROM company WHERE id =#{id}")
		void deleteById(long id);
		
		@Select("select * from company where id=#{id}")
		List<Company> selectById(long id);
		
		@Select("select * from company where id=#{id}")
		Company selectById1(long id);
}
