package com.routon.plcloud.admin.order.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;

import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.model.ClientList;
import com.routon.plcloud.common.model.CompanyAms;
import com.routon.plcloud.common.model.OfflineAuth;
import com.routon.plcloud.common.model.TermAms;
import com.routon.plcloud.common.model.TerminalAms;

public interface TerminalService {

//	     public List<TreeBean> getTerminalTreeByUserId(Long loginUserId, TerminalAms queryCondition);

		public List<TreeBean> getTerminalTreeByUserId(Long loginUserId, TerminalAms queryCondition ,Long requirementtype,String searchname);

//		public PagingBean<TerminalAms> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection,
//				TerminalAms queryCondition, String in_userIds, String notin_userIds, Long loginUserId, boolean exportflag);

		public String ClientInfoUpload(String command, int version, String companyName, String project, String orderNum , int rownum,
				ArrayList<ClientList> clientLists) throws IOException;

		public PagingBean<ClientList> paging(Long companyid, Long projectid, Long orderid ,int page,Long loginUserId);  
		
		public  String LicenceTermQuery(String command,int version,String company_name,String project ,String orderid , int term_num , int start) throws IOException;
			
		public  String AuthInfoUpload(String command,int version,String company_name,String project ,String client_code , String term_code) throws IOException;
		
		public  String LicenceRequestUpload(String command,int version,CompanyAms companyAms,String project , int request_type , String client_code ,String auth_code , int term_num,ArrayList<TermAms> termAms) throws IOException;
			
		public  String LicenceResponseQuery(String command,int version,int request_id) throws IOException;
		
		public Long add(OfflineAuth offlineAuth,HttpServletRequest request , UserProfile optUser) throws Exception ;
		
}
