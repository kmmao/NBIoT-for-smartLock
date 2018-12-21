package com.routon.plcloud.admin.order.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.admin.order.action.ProjectController;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.privilege.service.log.TerminalServiceLog;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.model.ClientList;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.CompanyAms;
import com.routon.plcloud.common.model.OfflineAuth;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.TermAms;
import com.routon.plcloud.common.model.TerminalAms;
import com.routon.plcloud.common.model.User;
import com.routon.plcloud.common.model.UserRole;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.OfflineauthMapper;
import com.routon.plcloud.common.persistence.OrderMapper;
import com.routon.plcloud.common.persistence.ProjectMapper;
import com.routon.plcloud.common.persistence.UserRoleMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
@SuppressWarnings("restriction")
@Service
public class TerminalServiceImpl implements TerminalService {
	
	private Logger logger = LoggerFactory.getLogger(TerminalServiceImpl.class);
	
	private  static String rootPath = TerminalService.class.getResource("/").getFile().toString().replaceAll("%20", " ").replaceFirst("/", "");
	
	
	
//	private static final String targetURL = "http://172.16.42.38:8910/"; 
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private OfflineauthMapper offlineauthMapper;
	
	@Autowired
	private TerminalServiceLog terminalServiceLog;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private ProjectService projectService;

	public List<TreeBean> getTerminalTreeByUserId(Long loginUserId, TerminalAms queryCondition ,Long requirementtype,String searchname) {
		// TODO Auto-generated method stub
//		String sql = "select * from company ";
		String sql = "select DISTINCT a.* from company a left join projectcompany b on a.id = b.companyid LEFT JOIN userproject c on b.projectid = c.projectid left join project d on b.projectid = d.id left join orderproject e on d.id = e.projectid "
				+ "left join \"order\" f on e.orderid = f.id where f.status = 1 and (f.verify = 1 or f.verify = 2 or f.verify = 3) and d.status =2 and c.userid = " + loginUserId;                           
	
		//搜索框查询公司
		if(requirementtype!=null && requirementtype == 1L){
			sql += " and a.companyname like '%" + searchname + "%'";
		}
		if(requirementtype!=null && requirementtype == 2L){
			sql += " and d.projectname like '%" + searchname + "%'";
		}
		if(requirementtype!=null && requirementtype == 3L){
			sql += " and f.ordernum like '%" + searchname + "%'";
		}
		
		List<Company> companies = companyMapper.selectBySql(sql);
		List<TreeBean> companyTree = new ArrayList<TreeBean>();
		for(Company company : companies){
			TreeBean companyBean = new TreeBean();
			List<TreeBean> projectTree = new ArrayList<TreeBean>();
			String projectsql = "select DISTINCT a.* from project a left join projectcompany b on a.id = b.projectid LEFT JOIN"
					+ " userproject c on a.id = c.projectid left join orderproject d on a.id = d.projectid left join \"order\" e on "
					+ "d.orderid = e.id  where e.status =1 and (e.verify = 1 or e.verify = 2 or e.verify = 3) and a.status = '2' and c.userid =  '" +loginUserId + "' and b.companyid = '" + company.getId() + "'";
			//搜索框查询项目
			if(requirementtype!=null && requirementtype == 2L){
				projectsql += " and a.projectname like '%" + searchname + "%'";
			}
			if(requirementtype!=null && requirementtype == 3L){
				projectsql += " and e.ordernum like '%" + searchname + "%'";
			}
			
			List<Project> projects = projectMapper.selectBysql(projectsql);
			companyBean.setId(company.getId());
			companyBean.setName(company.getCompanyname());
			companyBean.setPid(Long.parseLong("0"));
			companyBean.setParent(true);
			companyBean.setOpen(true);
			for(Project project : projects){
				TreeBean projectBean = new TreeBean();
				List<TreeBean> orderTree = new ArrayList<TreeBean>();
				String ordersql = "select DISTINCT a.* from \"order\" a LEFT JOIN orderproject b on a.ID = b.orderid LEFT JOIN"
						+ " userproject c on b.projectid = c.projectid where a.status = '1' and (a.verify = '1' or a.verify = '2' or a.verify = '3') and c.userid = '" + loginUserId + "' and b.projectid = '" + project.getId() +"'";
				//搜索框查询订单
				if(requirementtype!=null && requirementtype == 3L){
					ordersql += " and a.ordernum like '%" + searchname + "%'";
				}
				
				List<Order> orders = orderMapper.selectBysql(ordersql);
				
				projectBean.setId(project.getId());
				projectBean.setName(project.getProjectname());
				projectBean.setPid(company.getId());
				projectBean.setParent(true);
				projectBean.setOpen(true);
				for(Order order : orders){
					TreeBean orderBean = new TreeBean();
					orderBean.setId(order.getId());
					orderBean.setName(order.getOrdernum());
					orderBean.setPid(project.getId());
					orderTree.add(orderBean);
				}
				projectBean.setChildren(orderTree);
				projectTree.add(projectBean);

				
				//判断用户权限，分别显示不同权限的树结构
//				String sql_tem = "select DISTINCT a.* from rolemenu a left join"
//						+ " userrole b on a.roleid = b.roleid where  a.menuid = '90000401' and b.userid = " + loginUserId;
//				List<UserRole> userRoles = userRoleMapper.selectBySql(sql_tem);
//				if(userRoles != null && userRoles.size() > 0){
//					ordersql = "select DISTINCT a.* from \"order\" a LEFT JOIN orderproject b on a.ID = b.orderid LEFT JOIN"
//							+ " userproject c on b.projectid = c.projectid where c.userid = " + loginUserId;
//				}
			}
			companyBean.setChildren(projectTree);
			companyTree.add(companyBean);			
		}
		return companyTree;
	}

	@Override
	public PagingBean<ClientList> paging(Long companyid , Long projectid , Long orderid , int page , Long loginUserId) {
		// TODO Auto-generated method stub
		PagingBean<ClientList> pagingSystemclientlist = new PagingBean<ClientList>();
		ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
		int count = 0;
		String output = null;
		//判断传入的参数
		if(orderid == null && projectid == null && companyid == null){			
			String sql = "select DISTINCT a.* from company a left join projectcompany b on a.id = b.companyid LEFT JOIN userproject c on b.projectid = c.projectid "
					+ "left join project d on b.projectid = d.id left join orderproject e on d.id = e.projectid left join \"order\" f on e.orderid = f.id where f.status = 1 and (f.verify = 1 or f.verify = 2 or f.verify = 3) and d.status =2 and c.userid = " + loginUserId; 
					List<Company> companies = companyMapper.selectBySql(sql);
					for( int i = 0 ; i < companies.size() ; i++){
						String projectsql = "select DISTINCT a.* from project a left join projectcompany b on a.id=b.projectid left join userproject c on a.id = c.projectid "
								+ "left join orderproject d on a.id = d.projectid left join \"order\" e on d.orderid = e.id where e.status =1 and (e.verify = 1 or e.verify = 2 or e.verify = 3) and a.status = '2' and c.userid = " + loginUserId + "and b.companyid = " + companies.get(i).getId() ;
						List<Project> projects = projectMapper.selectBysql(projectsql);
						String companyname = companies.get(i).getCompanyregname();
//						int termtotalNum = 0;
						int start = (page-1)*10;
						for(int j =0 ; j < projects.size() ; j++){
							String projectname = projects.get(j).getProjectregname();
							String orderidtmp = String.valueOf(orderid);
							String command = "LicenceTermQuery";
							String command1 = "LicenceNumQuery";
						    int version = 256;
						    int term_num = 10;	
//						    int all_tem = -1;
						    try {
						    	//获得所有的授权终端总数
//						    	ThreadLocal<Long> startTime = new ThreadLocal<Long>();
//								ThreadLocal<Long> endTime = new ThreadLocal<Long>();
//								startTime.set(System.currentTimeMillis());
						    	
//						    String	allcount = LicenceTermQuery(command, version, companyname, projectname, orderidtmp, all_tem, start);
//						    System.out.println(allcount);
						    
//						        endTime.set(System.currentTimeMillis());
//						    	long time = endTime.get() - startTime.get();
//						    	System.out.println("查询所有终端耗时："+companyname+ projectname +":"+ time + "ms");
						    	
						    	
//						    JSONObject jsStr1 = JSONObject.fromObject(allcount);
//						    count = count + Integer.parseInt(jsStr1.getString("term_num"));
						    					
						   String Findcount = projectService.LicenceNumQuery(command1, version, companyname, projectname,orderidtmp);
						   logger.info( "查询授权终端总数 LicenceNumQuery:" + Findcount);
						   JSONObject jsStr1 = JSONObject.fromObject(Findcount);
						   count = count + Integer.parseInt(jsStr1.getString("term_num"));
						   
								 output = LicenceTermQuery(command, version, companyname, projectname, orderidtmp, term_num, start);
								 logger.info("查询授权终端信息 LicenceTermQuery:" + output);
								 JSONObject jsStr2 = JSONObject.fromObject(output);
//								 termtotalNum = Integer.parseInt(jsStr2.getString("total_num"));
								
								 if(jsStr2.getString("term_num").equals("0")){
									 continue;
								 }
//								 else if(termtotalNum > 1000 && Integer.parseInt(jsStr2.getString("term_num")) == 1000){
//									 ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
//									 for(int m = 0 ; m < termtotalNum/1000+1 ; m++){
//										 start = m*1000;
//										 output = LicenceTermQuery(command, version, companyname, projectname, orderidtmp, term_num, start);
//										 JSONObject jsStr = JSONObject.fromObject(output);
//										 JSONArray client_list = jsStr.getJSONArray("term_list");
////								        	ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
//								        	 for( int k = 0 ; k < client_list.size() ; k++){
//										    	 	
//								        		 ClientList clientList  = new ClientList();
//								        		 clientList.setProvince(client_list.getJSONObject(k).getString("province"));
//								        		 clientList.setCity(client_list.getJSONObject(k).getString("city"));
//								        		 clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
//								        		 clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
//								        		 clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
//								        		 clientList.setContact(client_list.getJSONObject(k).getString("contact"));
//								        		 clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
//								        		 clientList.setAddress(client_list.getJSONObject(k).getString("address"));
//								        		 clientList.setTerm_code(client_list.getJSONObject(k).getString("term_code"));
//								        		 clientList.setExpire(client_list.getJSONObject(k).getString("expire"));
//								        		 clientList.setTime(client_list.getJSONObject(k).getString("time"));
////								        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
////								        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
////											    	   System.out.println(terminalAms.getProvince());
//								        		 clientLists.add(clientList);
//										      }
////								        	 count = count + client_list.size();
//									 }
									 
									 
//									 JSONArray client_list = jsStr2.getJSONArray("term_list");
//							        	ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
//							        	 for( int k = 0 ; k < client_list.size() ; k++){
//									    	 	
//							        		 ClientList clientList  = new ClientList();
//							        		 clientList.setProvince(client_list.getJSONObject(k).getString("province"));
//							        		 clientList.setCity(client_list.getJSONObject(k).getString("city"));
//							        		 clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
//							        		 clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
//							        		 clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
//							        		 clientList.setContact(client_list.getJSONObject(k).getString("contact"));
//							        		 clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
//							        		 clientList.setAddress(client_list.getJSONObject(k).getString("address"));
//							        		 clientList.setTerm_code(client_list.getJSONObject(k).getString("term_code"));
//							        		 clientList.setExpire(client_list.getJSONObject(k).getString("expire"));
//							        		 clientList.setTime(client_list.getJSONObject(k).getString("time"));
////							        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
////							        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
////										    	   System.out.println(terminalAms.getProvince());
//							        		 clientLists.add(clientList);
//									      }
//							        	 pagingSystemclientlist.setDatas(clientLists);
////							        	 count = count + client_list.size();
//								 }
								 else {
									 JSONArray client_list = jsStr2.getJSONArray("term_list");
//							        	ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
							        	 for( int k = 0 ; k < client_list.size() ; k++){
							        		 String type = client_list.getJSONObject(k).getString("request_type");
							        		 if(type.equals("1")){
							        			 type = "离线";
							        		 }
							        		 else{
							        			 type = "在线";
							        		 }
									    	 	
							        		 ClientList clientList  = new ClientList();
							        		 clientList.setProvince(client_list.getJSONObject(k).getString("province"));
							        		 clientList.setCity(client_list.getJSONObject(k).getString("city"));
							        		 clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
							        		 clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
							        		 clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
							        		 clientList.setContact(client_list.getJSONObject(k).getString("contact"));
							        		 clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
							        		 clientList.setAddress(client_list.getJSONObject(k).getString("address"));
							        		 clientList.setTerm_code(client_list.getJSONObject(k).getString("term_code"));
							        		 clientList.setTerm_sn(client_list.getJSONObject(k).getString("term_sn"));
							        		 clientList.setTerm_licence(client_list.getJSONObject(k).getString("term_licence"));
							        		 clientList.setRequest_type(type);
							        		 clientList.setExpire(client_list.getJSONObject(k).getString("expire"));
							        		 clientList.setTime(client_list.getJSONObject(k).getString("time"));
//							        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
//							        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
//										    	   System.out.println(terminalAms.getProvince());
							        		 if(clientLists.size()==10){
							        			 continue;
							        		 }
							        		 clientLists.add(clientList);
							        		 
									      }
							        	 
//							        	 count = count + client_list.size();
								}
								 pagingSystemclientlist.setDatas(clientLists);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}		
					}
				}
		else if(companyid != null && projectid == null && orderid == null){
			
			String projectsql = "select DISTINCT a.* from project a left join projectcompany b on a.id=b.projectid left join userproject c on a.id = c.projectid "
					+ "left join orderproject d on a.id = d.projectid left join \"order\" e on d.orderid = e.id where e.status =1 and (e.verify = 1 or e.verify = 2 or e.verify = 3) and a.status = '2' and c.userid = " + loginUserId + "and b.companyid = " + companyid ;
	
			
//					String projectsql = "select a.* from project a left join projectcompany b on a.id=b.projectid where a.status = '1' and b.companyid = " + companyid;
					List<Project> projects = projectMapper.selectBysql(projectsql);
					List<Company> companies = companyMapper.selectById(companyid);
					String companyname = companies.get(0).getCompanyregname();
					for(int j =0 ; j < projects.size() ; j++){
					String projectname = projects.get(j).getProjectregname();
					String orderidtmp = String.valueOf(orderid);
					String command = "LicenceTermQuery";
					String command1 = "LicenceNumQuery";
				    int version = 256;
				    int term_num = 10;
				    int start = (page-1)*10;
				    try {
				    	
				    	 String Findcount = projectService.LicenceNumQuery(command1, version, companyname, projectname,orderidtmp);
				    	 logger.info( "查询授权终端总数 LicenceNumQuery:" + Findcount);
				    	 JSONObject jsStr1 = JSONObject.fromObject(Findcount);
						 count = count + Integer.parseInt(jsStr1.getString("term_num"));
						   
						 output = LicenceTermQuery(command, version, companyname, projectname, orderidtmp, term_num, start);
						 logger.info("查询授权终端信息 LicenceTermQuery:" + output);
						 JSONObject jsStr2 = JSONObject.fromObject(output);
						 if(jsStr2.getString("term_num").equals("0")){
							 continue;
						 }
						 else{
							 JSONArray client_list = jsStr2.getJSONArray("term_list");
//					        	ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
					        	 for( int k = 0 ; k < client_list.size() ; k++){
					        		 String type = client_list.getJSONObject(k).getString("request_type");
					        		 if(type.equals("1")){
					        			 type = "离线";
					        		 }
					        		 else{
					        			 type = "在线";
					        		 }
					        		 ClientList clientList  = new ClientList();
					        		 clientList.setProvince(client_list.getJSONObject(k).getString("province"));
					        		 clientList.setCity(client_list.getJSONObject(k).getString("city"));
					        		 clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
					        		 clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
					        		 clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
					        		 clientList.setContact(client_list.getJSONObject(k).getString("contact"));
					        		 clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
					        		 clientList.setAddress(client_list.getJSONObject(k).getString("address"));
					        		 clientList.setTerm_code(client_list.getJSONObject(k).getString("term_code"));
					        		 clientList.setTerm_sn(client_list.getJSONObject(k).getString("term_sn"));
					        		 clientList.setTerm_licence(client_list.getJSONObject(k).getString("term_licence"));
					        		 clientList.setRequest_type(type);
					        		 clientList.setExpire(client_list.getJSONObject(k).getString("expire"));
					        		 clientList.setTime(client_list.getJSONObject(k).getString("time"));
//					        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
//					        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
//								    	   System.out.println(terminalAms.getProvince());
					        		 if(clientLists.size()==10){
					        			 continue;
					        		 }
					        		 clientLists.add(clientList);
							      }
					        	
//					        	 count = count + client_list.size();
						 }
						 pagingSystemclientlist.setDatas(clientLists);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		else if(companyid == null && projectid != null && orderid == null){
			Project projects = projectMapper.selectById(projectid);
			String projectname = projects.getProjectregname();
			String sql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = " + projectid;
			List<Company> companies = companyMapper.selectBySql(sql);
			String companyname = companies.get(0).getCompanyregname();
			String orderidtmp = String.valueOf(orderid);
			String command = "LicenceTermQuery";
			String command1 = "LicenceNumQuery";
		    int version = 256;
		    int term_num = 10;
		    int start = (page-1)*10;
		    try {
		    	
		    	 String Findcount = projectService.LicenceNumQuery(command1, version, companyname, projectname,orderidtmp);
		    	 logger.info( "查询授权终端总数 LicenceNumQuery:" + Findcount);
		    	 JSONObject jsStr1 = JSONObject.fromObject(Findcount);
				   count = count + Integer.parseInt(jsStr1.getString("term_num"));
		    	
				 output = LicenceTermQuery(command, version, companyname, projectname, orderidtmp, term_num, start);
				 logger.info("查询授权终端信息 LicenceTermQuery:" + output);
				 JSONObject jsStr2 = JSONObject.fromObject(output);
				 if(jsStr2.getString("term_num").equals("0")){
					 pagingSystemclientlist.setTotalCount(count);
					 return pagingSystemclientlist;
				 }
				 else{
					 JSONArray client_list = jsStr2.getJSONArray("term_list");
//			        	ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
			        	 for( int k = 0 ; k < client_list.size() ; k++){
			        		 String type = client_list.getJSONObject(k).getString("request_type");
			        		 if(type.equals("1")){
			        			 type = "离线";
			        		 }
			        		 else{
			        			 type = "在线";
			        		 }
			        		 ClientList clientList  = new ClientList();
			        		 clientList.setProvince(client_list.getJSONObject(k).getString("province"));
			        		 clientList.setCity(client_list.getJSONObject(k).getString("city"));
			        		 clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
			        		 clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
			        		 clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
			        		 clientList.setContact(client_list.getJSONObject(k).getString("contact"));
			        		 clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
			        		 clientList.setAddress(client_list.getJSONObject(k).getString("address"));
			        		 clientList.setTerm_code(client_list.getJSONObject(k).getString("term_code"));
			        		 clientList.setTerm_sn(client_list.getJSONObject(k).getString("term_sn"));
			        		 clientList.setTerm_licence(client_list.getJSONObject(k).getString("term_licence"));
			        		 clientList.setRequest_type(type);
			        		 clientList.setExpire(client_list.getJSONObject(k).getString("expire"));
			        		 clientList.setTime(client_list.getJSONObject(k).getString("time"));
//			        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
//			        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
//						    	   System.out.println(terminalAms.getProvince());
			        		 if(clientLists.size()==10){
			        			 continue;
			        		 }
			        		 clientLists.add(clientList);
					      }
			        	
//			        	 count = count + client_list.size();
				 }
				 pagingSystemclientlist.setDatas(clientLists);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		else{

			String ordersql = "select a.* from \"order\" a where a.id = " + orderid;
			List<Order> orders = orderMapper.selectBysql(ordersql);
			String orderidtmp = orders.get(0).getOrdernum();
			String projectsql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = " + orderid;
			List<Project> projects = projectMapper.selectBysql(projectsql);
			String projectname = projects.get(0).getProjectregname();
			String companysql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = " + projects.get(0).getId();
			List<Company> companies = companyMapper.selectBySql(companysql);
			String companyname = companies.get(0).getCompanyregname();
			String command = "LicenceTermQuery";
			String command1 = "LicenceNumQuery";
		    int version = 256;
		    int term_num = 10;
		    int start = (page-1)*10;
		    try {
		    	
		    	 String Findcount = projectService.LicenceNumQuery(command1, version, companyname, projectname,orderidtmp);
		    	 logger.info( "查询授权终端总数 LicenceNumQuery:" + Findcount);
		    	 JSONObject jsStr1 = JSONObject.fromObject(Findcount);
				   count = count + Integer.parseInt(jsStr1.getString("term_num"));
		    	
				 output = LicenceTermQuery(command, version, companyname, projectname, orderidtmp, term_num, start);
				 logger.info("查询授权终端信息 LicenceTermQuery:" + output);
				 JSONObject jsStr2 = JSONObject.fromObject(output);
				 if(jsStr2.getString("term_num").equals("0")){					   	 
					 pagingSystemclientlist.setTotalCount(count);
					 return pagingSystemclientlist;
				 }
				 else{
					 JSONArray client_list = jsStr2.getJSONArray("term_list");
//			        	ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
			        	 for( int k = 0 ; k < client_list.size() ; k++){
			        		 String type = client_list.getJSONObject(k).getString("request_type");
			        		 if(type.equals("1")){
			        			 type = "离线";
			        		 }
			        		 else{
			        			 type = "在线";
			        		 }
			        		 ClientList clientList  = new ClientList();
			        		 clientList.setProvince(client_list.getJSONObject(k).getString("province"));
			        		 clientList.setCity(client_list.getJSONObject(k).getString("city"));
			        		 clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
			        		 clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
			        		 clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
			        		 clientList.setContact(client_list.getJSONObject(k).getString("contact"));
			        		 clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
			        		 clientList.setAddress(client_list.getJSONObject(k).getString("address"));
			        		 clientList.setTerm_code(client_list.getJSONObject(k).getString("term_code"));
			        		 clientList.setTerm_sn(client_list.getJSONObject(k).getString("term_sn"));
			        		 clientList.setTerm_licence(client_list.getJSONObject(k).getString("term_licence"));
			        		 clientList.setRequest_type(type);
			        		 clientList.setExpire(client_list.getJSONObject(k).getString("expire"));
			        		 clientList.setTime(client_list.getJSONObject(k).getString("time"));
//			        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
//			        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
//						    	   System.out.println(terminalAms.getProvince());
			        		 if(clientLists.size()==10){
			        			 continue;
			        		 }
			        		 clientLists.add(clientList);
					      }
			        	
//			        	 count = count + client_list.size();
				 }
				 pagingSystemclientlist.setDatas(clientLists);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			
			
//				List<Company> companies = companyMapper.selectById(companyid);
//				String companyname = companies.get(0).getCompanyname();
//				Project projects = projectMapper.selectById(projectid);
//				String projectname = projects.getProjectname();
//				String command = "LicenceTermQuery";
//			    int version = 256;
//			    int term_num = -1;
//			    int start = 0;
//			    try {
//					 output = LicenceTermQuery(command, version, companyname, projectname, orderidtmp, term_num, start);
//					 JSONObject jsStr2 = JSONObject.fromObject(output);
//					 if(jsStr2.getString("term_num").equals("0")){					   	 
//							return pagingSystemclientlist;
//					 }
//					 else{
//						 JSONArray client_list = jsStr2.getJSONArray("term_list");
//				        	ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
//				        	 for( int k = 0 ; k < client_list.size() ; k++){
//						    	 	
//				        		 ClientList clientList  = new ClientList();
//				        		 clientList.setProvince(client_list.getJSONObject(k).getString("province"));
//				        		 clientList.setCity(client_list.getJSONObject(k).getString("city"));
//				        		 clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
//				        		 clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
//				        		 clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
//				        		 clientList.setContact(client_list.getJSONObject(k).getString("contact"));
//				        		 clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
//				        		 clientList.setAddress(client_list.getJSONObject(k).getString("address"));
//				        		 clientList.setTerm_code(client_list.getJSONObject(k).getString("term_code"));
//				        		 clientList.setExpire(client_list.getJSONObject(k).getString("expire"));
//				        		 clientList.setTime(client_list.getJSONObject(k).getString("time"));
////				        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
////				        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
////							    	   System.out.println(terminalAms.getProvince());
//				        		 clientLists.add(clientList);
//						      }
//				        	 pagingSystemclientlist.setDatas(clientLists);
//				        	 count = count + client_list.size();
//					 }
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		

//        String output2 = "{\"result\":0,\"client_list\":[{\"province\":\"浙江\",\"city\":\"杭州\",\"district\":\"广安\","
//				+ "\"client_code\":\"123456\",\"client_name\":\"酒店A\",\"contact\":\"张三\",\"telno\":\"11111111111\",\"address\":\"浙江省杭州市上城区1号\",\"remark\":\"备注\","
//				+ "\"time\":\"20171226\"},{\"province\":\"酒店1\",\"city\":\"杭州2\",\"district\":\"广安2\",\"client_code\":\"1234567\",\"client_name\":\"酒店A\",\"contact\":\"张三\",\"telno\":\"22222222222\",\"address\":\"浙江省杭州市上城区1号\",\"remark\":\"备注\","
//				+ "\"time\":\"20171226\"},{\"province\":\"酒店2\",\"city\":\"杭州2\",\"district\":\"广安2\",\"client_code\":\"1234567\",\"client_name\":\"酒店A\",\"contact\":\"张三\",\"telno\":\"22222222222\",\"address\":\"浙江省杭州市上城区1号\",\"remark\":\"备注\","
//				+ "\"time\":\"20171226\"},{\"province\":\"酒店3\",\"city\":\"杭州2\",\"district\":\"广安2\",\"client_code\":\"1234567\",\"client_name\":\"酒店A\",\"contact\":\"张三\",\"telno\":\"22222222222\",\"address\":\"浙江省杭州市上城区1号\",\"remark\":\"备注\","
//				+ "\"time\":\"20171226\"}]}";
//        	JSONObject jsStr2 = JSONObject.fromObject(output);
//        	JSONArray client_list = jsStr2.getJSONArray("term_list");
//        	ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
//        	 for( int i = 0 ; i < client_list.size() ; i++){
//		    	 	
//        		 ClientList clientList  = new ClientList();
//        		 clientList.setProvince(client_list.getJSONObject(i).getString("province"));
//        		 clientList.setCity(client_list.getJSONObject(i).getString("city"));
//        		 clientList.setDistrict(client_list.getJSONObject(i).getString("district"));
//        		 clientList.setClient_code(client_list.getJSONObject(i).getString("client_code"));
//        		 clientList.setClient_name(client_list.getJSONObject(i).getString("client_name"));
//        		 clientList.setContact(client_list.getJSONObject(i).getString("contact"));
//        		 clientList.setTelno(client_list.getJSONObject(i).getString("telno"));
//        		 clientList.setAddress(client_list.getJSONObject(i).getString("address"));
//        		 clientList.setTerm_code(client_list.getJSONObject(i).getString("term_code"));
//        		 clientList.setExpire(client_list.getJSONObject(i).getString("expire"));
//        		 clientList.setTime(client_list.getJSONObject(i).getString("time"));
////        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
////        		 clientList.setRemark(client_list.getJSONObject(i).getString("term_code"));
////			    	   System.out.println(terminalAms.getProvince());
//        		 clientLists.add(clientList);
//		      }
//        	 pagingSystemclientlist.setDatas(clientLists);
        	 pagingSystemclientlist.setTotalCount(count);
   	 
		return pagingSystemclientlist;
	}
	
	
	@SuppressWarnings("unused")
	public String ClientInfoUpload(String command,int version,String company_name,String project ,String orderNum , int client_num , ArrayList<ClientList> ClientList) throws IOException{
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream(rootPath + "jdbc.properties");
		prop.load(inStream);
		String ip = prop.getProperty("serverip").trim();
		String targetURL = "http://" + ip + ":8910/" ;
//		private static final String targetURL = "http://172.16.42.38:8910/"; 
		if((command == null||command.equals(""))||(version == 0)){
			return "-1";
		}
		else if(company_name == null||company_name.equals("")){
			return "-2";
		}
		else if(project == null||project.equals("")){
			return "-3";
		}
		else{
		    URL targetUrl = new URL(targetURL);
		    HttpURLConnection httpURLConnection = (HttpURLConnection)targetUrl.openConnection();
		    httpURLConnection.setDoOutput(true);
		    httpURLConnection.setRequestMethod("POST");
		    httpURLConnection.setRequestProperty("Content-Type","application/json");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", command);
		    jsonObject.put("version", version);
		    jsonObject.put("company_name", company_name);
		    jsonObject.put("project", project);
		    jsonObject.put("order_id", orderNum);
		    jsonObject.put("client_num", client_num);
		    jsonObject.put("client_list", ClientList);
		    String input = jsonObject.toString();

		    OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpURLConnection.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP error code : "
                          + httpURLConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpURLConnection.getInputStream())));

		      String output = null;
		      while ((output = responseBuffer.readLine()) != null) {
		    	httpURLConnection.disconnect();
		        return output;
		      }
		      httpURLConnection.disconnect();
		      return output;
		}

	}

	@Override
	public String LicenceTermQuery(String command, int version, String company_name, String project, String orderid,
			int term_num, int start) throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream(rootPath + "jdbc.properties");
		prop.load(inStream);
		String ip = prop.getProperty("serverip").trim();
		String targetURL = "http://" + ip + ":8910/" ;
		if((command == null||command.equals(""))||(version == 0)){
			return "-1";
		}
		else if(company_name == null||company_name.equals("")){
			return "-2";
		}
		else if(project == null||project.equals("")){
			return "-3";
		}
		else{
		    URL targetUrl = new URL(targetURL);
		    HttpURLConnection httpURLConnection = (HttpURLConnection)targetUrl.openConnection();
		    httpURLConnection.setDoOutput(true);
		    httpURLConnection.setRequestMethod("POST");
		    httpURLConnection.setRequestProperty("Content-Type","application/json");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", command);
		    jsonObject.put("version", version);
		    jsonObject.put("company_name", company_name);
		    jsonObject.put("project", project);
		    jsonObject.put("order_id", orderid);
		    jsonObject.put("term_num", term_num);
		    jsonObject.put("start", start);
		    String input = jsonObject.toString();
//		    System.out.println("传入的信息+"+input);
		    OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpURLConnection.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP error code : "
                          + httpURLConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpURLConnection.getInputStream())));

		      String output = null;
		      while ((output = responseBuffer.readLine()) != null) {
		    	httpURLConnection.disconnect();
		        return output;
		      }
		      httpURLConnection.disconnect();
		      return output;
		}


	}

	@Override
	public String AuthInfoUpload(String command, int version, String company_name, String project, String client_code,
			String term_code) throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream(rootPath + "jdbc.properties");
		prop.load(inStream);
		String ip = prop.getProperty("serverip").trim();
		String targetURL = "http://" + ip + ":8910/" ;
		if((command == null||command.equals(""))||(version == 0)){
			return "-1";
		}
		else if(company_name == null||company_name.equals("")){
			return "-2";
		}
		else if(project == null||project.equals("")){
			return "-3";
		}
		else{
		    URL targetUrl = new URL(targetURL);
		    HttpURLConnection httpURLConnection = (HttpURLConnection)targetUrl.openConnection();
		    httpURLConnection.setDoOutput(true);
		    httpURLConnection.setRequestMethod("POST");
		    httpURLConnection.setRequestProperty("Content-Type","application/json");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", command);
		    jsonObject.put("version", version);
		    jsonObject.put("company_name", company_name);
		    jsonObject.put("project", project);
		    jsonObject.put("client_code", client_code);
		    jsonObject.put("term_code", term_code);
		    String input = jsonObject.toString();
		    OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpURLConnection.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP error code : "
                          + httpURLConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpURLConnection.getInputStream())));

		      String output = null;
		      while ((output = responseBuffer.readLine()) != null) {
		    	httpURLConnection.disconnect();
		        return output;
		      }
		      httpURLConnection.disconnect();
		      return output;
		}
	}

	@Override
	public String LicenceRequestUpload(String command, int version, CompanyAms companyAms, String project,
			int request_type, String client_code, String auth_code, int term_num,
			ArrayList<TermAms> termAms) throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream(rootPath + "jdbc.properties");
		prop.load(inStream);
		String ip = prop.getProperty("serverip").trim();
		String targetURL = "http://" + ip + ":8910/" ;
		if((command == null||command.equals(""))||(version == 0)){
			return "-1";
		}
		else{
		    URL targetUrl = new URL(targetURL);
		    HttpURLConnection httpURLConnection = (HttpURLConnection)targetUrl.openConnection();
		    httpURLConnection.setDoOutput(true);
		    httpURLConnection.setRequestMethod("POST");
		    httpURLConnection.setRequestProperty("Content-Type","application/json");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", command);
		    jsonObject.put("version", version);
		    jsonObject.put("company", companyAms);
		    jsonObject.put("project", project);
		    jsonObject.put("request_type", request_type);
		    jsonObject.put("client_code", client_code);
		    jsonObject.put("auth_code", auth_code);
		    jsonObject.put("term_num", term_num);
		    jsonObject.put("term_list", termAms);
		    
		    String input = jsonObject.toString();
		    OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpURLConnection.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP error code : "
                          + httpURLConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpURLConnection.getInputStream())));

		      String output = null;
		      while ((output = responseBuffer.readLine()) != null) {
		    	httpURLConnection.disconnect();
		        return output;
		      }
		      httpURLConnection.disconnect();
		      return output;
		}
	}

	@Override
	public String LicenceResponseQuery(String command, int version, int request_id) throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream(rootPath + "jdbc.properties");
		prop.load(inStream);
		String ip = prop.getProperty("serverip").trim();
		String targetURL = "http://" + ip + ":8910/" ;
		if((command == null||command.equals(""))||(version == 0)){
			return "-1";
		}
		else{
		    URL targetUrl = new URL(targetURL);
		    HttpURLConnection httpURLConnection = (HttpURLConnection)targetUrl.openConnection();
		    httpURLConnection.setDoOutput(true);
		    httpURLConnection.setRequestMethod("POST");
		    httpURLConnection.setRequestProperty("Content-Type","application/json");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", command);
		    jsonObject.put("version", version);
		    jsonObject.put("request_id", request_id);
		    
		    String input = jsonObject.toString();
		    OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpURLConnection.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP error code : "
                          + httpURLConnection.getResponseCode());
            }
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpURLConnection.getInputStream())));

		      String output = null;
		      while ((output = responseBuffer.readLine()) != null) {
		    	httpURLConnection.disconnect();
		        return output;
		      }
		      httpURLConnection.disconnect();
		      return output;
		}
	}
	
	

	@Override
	public Long add(OfflineAuth offlineAuth , HttpServletRequest request , UserProfile optUser) throws Exception {
		String termsn = offlineAuth.getTermsn();
//		boolean isexit = termsnExist(termsn);
//		if(isexit){
//			return -2l;
//		}
//		else{
			offlineAuth.setTermsn(termsn.trim());
			offlineAuth.setTermlicence(offlineAuth.getTermlicence().trim());
			try {
				Long offlineauthid = offlineauthMapper.insert(offlineAuth);
				if(offlineauthid > 0){
					//生成licence文件，并存放在指定目录					
					BASE64Decoder decoder = new BASE64Decoder();
					byte[] bytes = null;
					try {
						bytes = decoder.decodeBuffer(offlineAuth.getTermlicence());
					} catch (IOException e) {
					    
					}
					//创建路径
					String up_path = "licence";
					String ctxPath = request.getSession().getServletContext().getRealPath("/") + up_path + "/" + termsn + "/";
					//创建文件目录
					File dirPath = new File(ctxPath);  
					if (!dirPath.exists()) {
						//如果不存则创建文件路径
						try {
							dirPath.mkdir();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					String realFileName = "licence.lic";
				    File uploadFile = new File(ctxPath + realFileName);
				    FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
				    
				    
				    fileOutputStream.write(bytes, 0, bytes.length);
				    fileOutputStream.close();
				    
				    terminalServiceLog.licencerequst(termsn, optUser);
					return offlineauthid;
				}else {
					return -1l;
				}
			} catch (Exception e) {
				return -1l;
			}
//		}

	}

	private boolean termsnExist(String termsn) {
		String sql = "select a.* from offlineauth a where a.termsn = " + "'" + termsn + "'";
		List<OfflineAuth> offlineAuths = offlineauthMapper.selectBysql(sql);
		if (offlineAuths != null && offlineAuths.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
		
}
