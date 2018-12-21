package com.routon.plcloud.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.ProjectCompany;
import com.routon.plcloud.common.model.User;

public interface ProjectCompanyMapper extends PageMapper<ProjectCompany>{
	
	@Insert("INSERT INTO projectcompany (companyid,projectid,modifytime) "
			+ " VALUES (#{companyid},#{projectid},#{modifytime})")
	@SelectKey(statement="SELECT currval('projectcompany_id_seq'::regclass) AS id", keyProperty="id", before=false, resultType=long.class)
	long insert(ProjectCompany projectCompany);
	
	@Delete("DELETE FROM projectcompany WHERE projectid =#{projectid}")
	void deleteByProjectId(long projectid);
	
	@Select("${sql}")
	List<ProjectCompany> selectBySql(@Param("sql") String sql);
	
//	@Select("select count(*) from projectcompany where companyid = #{companyid}")
//	long selectCountBycompanyId(long companyid);
	@Select("${sql}")
	long selectrownumBySql(@Param("sql") String sql);
}
