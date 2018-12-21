package com.routon.plcloud.admin.order.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.poi.hssf.record.formula.functions.Int;

import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.model.Company;

import com.routon.plcloud.common.model.Project;

public interface ProjectService {

//	PUBLIC LONG ADD(GROUP GROUP, USERPROFILE OPTUSER) THROWS EXCEPTION;
//
//	PUBLIC LONG EDIT(GROUP GROUP, USERPROFILE OPTUSER);
//
//	PUBLIC INT DELETE(STRING IDS, USERPROFILE OPTUSER) THROWS EXCEPTION;

	public Long move(Company group, UserProfile optUser);
	
	//终端授权数量接口调用
	public  String LicenceNumQuery(String command,int version,String company_name,String project ,String orderid) throws MalformedURLException, IOException;
	
	//配置认证方式接口调用
	public  String AuthTypeConfig(String command,int version,String company_name,String project , int auth_client_code , int auth_term_code) throws IOException;

	public List<TreeBean> getCompanyTreeByUserId(Long opuserId,
			Company queryCondition, Long userId  );

//	public List<TreeBean> getCompanyTreeByUserId(Long opuserId,
//			Company queryCondition, Long userId, boolean onlyleafcheck);

	public List<TreeBean> getCompanyTreeByUserId(Long opuserId,
			Company queryCondition, Long userId, boolean onlyleafcheck,
			boolean showRelevanceCount);
	
	public PagingBean<Project> paging(int startIndex, int pageSize, String sortCriterion,
			String sortDirection, Project queryCondition1, String in_projectIds,
			String notin_projectIds, Long id , Long loginUserId, boolean exportflag);



	public Long add(Project project, int companyid, UserProfile optUser) throws Exception;



	public Long edit(Project project, int companyid, UserProfile optUser);



	public int disableProject(String disableProjectIds , UserProfile optUser);
	
	//审核通过之后上报授权服务
	public String ProjectVerify(Project project, int companyid, String username, String password)
			throws Exception, IOException;



	
}

