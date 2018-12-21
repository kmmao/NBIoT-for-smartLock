package com.routon.plcloud.admin.order.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.ClientList;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.OrderMapper;
import com.routon.plcloud.common.persistence.ProjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author huanggang
 * 
 *
 */
@Service
public class ClientListServiceImpl implements ClientListService {

	private Logger logger = LoggerFactory.getLogger(ClientListServiceImpl.class);

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public PagingBean<ClientList> paging(Long companyid, Long projectid, Long orderid, int page, Long loginUserId)
			throws IOException {
		PagingBean<ClientList> pagingSystemclientlist = new PagingBean<ClientList>();
		int count = 0;
		String output = null;
		// 公司、项目、订单id都为空，显示当前用户权限下的所有导入信息
		if (orderid == null && projectid == null && companyid == null) {
			pagingSystemclientlist.setTotalCount(count);
			return pagingSystemclientlist;
		}
		// 点击订单查看客户信息
		else {
			String ordersql = "select a.* from \"order\" a where a.id = " + orderid;
			List<Order> orders = orderMapper.selectBysql(ordersql);
			String ordernum = orders.get(0).getOrdernum();
			String projectsql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = "
					+ orderid;
			List<Project> projects = projectMapper.selectBysql(projectsql);
			String projectname = projects.get(0).getProjectregname();
			String companysql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = "
					+ projects.get(0).getId();
			List<Company> companies = companyMapper.selectBySql(companysql);
			String companyname = companies.get(0).getCompanyregname();
			String command = "ClientInfoQuery";
			int version = 256;
			int client_num = 10;
			int start = (page - 1) * 10;
			output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
			logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
			JSONObject jsStr = JSONObject.fromObject(output);
			count = count + Integer.parseInt(jsStr.getString("total_num"));

			if (jsStr.getString("client_num").equals("0")) {
				pagingSystemclientlist.setTotalCount(count);
				return pagingSystemclientlist;
			} else {
				JSONArray client_list = jsStr.getJSONArray("client_list");
				ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
				for (int k = 0; k < client_list.size(); k++) {
					ClientList clientList = new ClientList();
					clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
					clientList.setProvince(client_list.getJSONObject(k).getString("province"));
					clientList.setCity(client_list.getJSONObject(k).getString("city"));
					clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
					clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
					clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
					clientList.setContact(client_list.getJSONObject(k).getString("contact"));
					clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
					clientList.setAddress(client_list.getJSONObject(k).getString("address"));
					clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
					clientList.setTime(client_list.getJSONObject(k).getString("time"));
					clientLists.add(clientList);
				}
				pagingSystemclientlist.setDatas(clientLists);
			}
		}
		pagingSystemclientlist.setTotalCount(count);
		return pagingSystemclientlist;
	}
	
	/*@Override
	public PagingBean<ClientList> paging(Long companyid, Long projectid, Long orderid, int page, Long loginUserId)
			throws IOException {
		PagingBean<ClientList> pagingSystemclientlist = new PagingBean<ClientList>();
		int count = 0;
		String output = null;
		// 公司、项目、订单id都为空，显示当前用户权限下的所有导入信息
		if (orderid == null && projectid == null && companyid == null) {
			// String sql = "select a.* from company a where 1=1";
			// String sql = "select DISTINCT a.* from company a left join
			// projectcompany b on a.id = b.companyid LEFT JOIN userproject c "
			// + "on b.projectid = c.projectid where c.userid ="+ loginUserId;
			String sql = "select DISTINCT a.* from company a left join projectcompany b on a.id = b.companyid LEFT JOIN userproject c on "
					+ "b.projectid = c.projectid left join project d on b.projectid = d.id left join orderproject e on d.id = e.projectid "
					+ "left join \"order\" f on e.orderid = f.id where f.status = 1 and d.status =2 and c.userid ="
					+ loginUserId;
			List<Company> companies = companyMapper.selectBySql(sql);
			for (int i = 0; i < companies.size(); i++) {
				// String projectsql = "select a.* from project a left join
				// projectcompany b on a.id=b.projectid where a.status = '1' and
				// b.companyid = "
				// + companies.get(i).getId();
				// String projectsql = "select DISTINCT a.* from project a left
				// join projectcompany b on a.id = b.projectid LEFT JOIN
				// userproject c "
				// + "on a.id = c.projectid where a.status = '1' and c.userid
				// ="+loginUserId+ " and b.companyid ="+
				// companies.get(i).getId();
				String projectsql = "select DISTINCT a.* from project a left join projectcompany b on a.id=b.projectid left join userproject c "
						+ "on a.id = c.projectid left join orderproject d on a.id = d.projectid left join \"order\" e on d.orderid = e.id "
						+ "where e.status =1 and a.status = 2 and c.userid =" + loginUserId + " and b.companyid="
						+ companies.get(i).getId();
				List<Project> projects = projectMapper.selectBysql(projectsql);
				String companyname = companies.get(i).getCompanyregname();
				int start = (page - 1) * 10;
				for (int j = 0; j < projects.size(); j++) {
					String ordersql = "select DISTINCT a.* from \"order\" a LEFT JOIN orderproject b on a.ID = b.orderid LEFT JOIN"
							+ " userproject c on b.projectid = c.projectid where a.status = 1 and c.userid = '"
							+ loginUserId + "' and b.projectid = '" + projects.get(j).getId() + "'";
					List<Order> orders = orderMapper.selectBysql(ordersql);
					List<String> ordernumLists = new ArrayList<String>();
					//遍历得到当前用户权限下可见的所有订单号
					for(Order order:orders){
						ordernumLists.add(order.getOrdernum());
					}
							
					String projectname = projects.get(j).getProjectregname();
					String command = "ClientInfoQuery";
					String ordernum = null;
					int version = 256;
					int client_num = 10;
					output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
					logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
					JSONObject jsStr = JSONObject.fromObject(output);
//					count = count + Integer.parseInt(jsStr.getString("total_num"));

					if (jsStr.getString("client_num").equals("0")) {
						continue;
					} else {
						JSONArray client_list = jsStr.getJSONArray("client_list");
						ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
						for (int k = 0; k < client_list.size(); k++) {
							String orderNum = client_list.getJSONObject(k).getString("order_id");
							if(ordernumLists.contains(orderNum)){
								
							}
							ClientList clientList = new ClientList();
							clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
							clientList.setProvince(client_list.getJSONObject(k).getString("province"));
							clientList.setCity(client_list.getJSONObject(k).getString("city"));
							clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
							clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
							clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
							clientList.setContact(client_list.getJSONObject(k).getString("contact"));
							clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
							clientList.setAddress(client_list.getJSONObject(k).getString("address"));
							clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
							clientList.setTime(client_list.getJSONObject(k).getString("time"));
							clientLists.add(clientList);
						}
						pagingSystemclientlist.setDatas(clientLists);
					}
				}
			}
		}
		// 点击公司查看客户信息
		else if (companyid != null && projectid == null && orderid == null) {
			// String projectsql = "select a.* from project a left join
			// projectcompany b on a.id=b.projectid where a.status = '1' and
			// b.companyid = " + companyid;
			// String projectsql = "select DISTINCT a.* from project a left join
			// projectcompany b on a.id = b.projectid LEFT JOIN userproject c "
			// + "on a.id = c.projectid where a.status = '1' and c.userid
			// ="+loginUserId+ " and b.companyid ="+companyid;
			String projectsql = "select DISTINCT a.* from project a left join projectcompany b on a.id=b.projectid left join userproject c "
					+ "on a.id = c.projectid left join orderproject d on a.id = d.projectid left join \"order\" e on d.orderid = e.id "
					+ "where e.status =1 and a.status = 2 and c.userid =" + loginUserId + " and b.companyid="
					+ companyid;
			List<Project> projects = projectMapper.selectBysql(projectsql);
			List<Company> companies = companyMapper.selectById(companyid);
			String companyname = companies.get(0).getCompanyregname();
			for (int j = 0; j < projects.size(); j++) {
				String projectname = projects.get(j).getProjectregname();
				String command = "ClientInfoQuery";
				String ordernum = null;
				int version = 256;
				int client_num = 10;
				int start = (page - 1) * 10;

				output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
				logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
				JSONObject jsStr = JSONObject.fromObject(output);
				count = count + Integer.parseInt(jsStr.getString("total_num"));

				if (jsStr.getString("client_num").equals("0")) {
					continue;
				} else {
					JSONArray client_list = jsStr.getJSONArray("client_list");
					ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
					for (int k = 0; k < client_list.size(); k++) {
						ClientList clientList = new ClientList();
						clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
						clientList.setProvince(client_list.getJSONObject(k).getString("province"));
						clientList.setCity(client_list.getJSONObject(k).getString("city"));
						clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
						clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
						clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
						clientList.setContact(client_list.getJSONObject(k).getString("contact"));
						clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
						clientList.setAddress(client_list.getJSONObject(k).getString("address"));
						clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
						clientList.setTime(client_list.getJSONObject(k).getString("time"));
						clientLists.add(clientList);
					}
					pagingSystemclientlist.setDatas(clientLists);
				}
			}
		}
		// 点击项目查看客户信息
		else if (companyid == null && projectid != null && orderid == null) {
			Project projects = projectMapper.selectById(projectid);
			String projectname = projects.getProjectregname();
			String sql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = "
					+ projectid;
			List<Company> companies = companyMapper.selectBySql(sql);
			String companyname = companies.get(0).getCompanyregname();
			String command = "ClientInfoQuery";
			String ordernum = null;
			int version = 256;
			int client_num = 10;
			int start = (page - 1) * 10;
			output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
			logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
			JSONObject jsStr = JSONObject.fromObject(output);
			count = count + Integer.parseInt(jsStr.getString("total_num"));

			if (jsStr.getString("client_num").equals("0")) {
				pagingSystemclientlist.setTotalCount(count);
				return pagingSystemclientlist;
			} else {
				JSONArray client_list = jsStr.getJSONArray("client_list");
				ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
				for (int k = 0; k < client_list.size(); k++) {
					ClientList clientList = new ClientList();
					clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
					clientList.setProvince(client_list.getJSONObject(k).getString("province"));
					clientList.setCity(client_list.getJSONObject(k).getString("city"));
					clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
					clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
					clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
					clientList.setContact(client_list.getJSONObject(k).getString("contact"));
					clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
					clientList.setAddress(client_list.getJSONObject(k).getString("address"));
					clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
					clientList.setTime(client_list.getJSONObject(k).getString("time"));
					clientLists.add(clientList);
				}
				pagingSystemclientlist.setDatas(clientLists);
			}
		}
		// 点击订单查看客户信息
		else {
			String ordersql = "select a.* from \"order\" a where a.id = " + orderid;
			List<Order> orders = orderMapper.selectBysql(ordersql);
			String ordernum = orders.get(0).getOrdernum();
			String projectsql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = "
					+ orderid;
			List<Project> projects = projectMapper.selectBysql(projectsql);
			String projectname = projects.get(0).getProjectregname();
			String companysql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = "
					+ projects.get(0).getId();
			List<Company> companies = companyMapper.selectBySql(companysql);
			String companyname = companies.get(0).getCompanyregname();
			String command = "ClientInfoQuery";
			int version = 256;
			int client_num = 10;
			int start = (page - 1) * 10;
			output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
			logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
			JSONObject jsStr = JSONObject.fromObject(output);
			count = count + Integer.parseInt(jsStr.getString("total_num"));

			if (jsStr.getString("client_num").equals("0")) {
				pagingSystemclientlist.setTotalCount(count);
				return pagingSystemclientlist;
			} else {
				JSONArray client_list = jsStr.getJSONArray("client_list");
				ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
				for (int k = 0; k < client_list.size(); k++) {
					ClientList clientList = new ClientList();
					clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
					clientList.setProvince(client_list.getJSONObject(k).getString("province"));
					clientList.setCity(client_list.getJSONObject(k).getString("city"));
					clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
					clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
					clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
					clientList.setContact(client_list.getJSONObject(k).getString("contact"));
					clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
					clientList.setAddress(client_list.getJSONObject(k).getString("address"));
					clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
					clientList.setTime(client_list.getJSONObject(k).getString("time"));
					clientLists.add(clientList);
				}
				pagingSystemclientlist.setDatas(clientLists);
			}
		}
		pagingSystemclientlist.setTotalCount(count);
		return pagingSystemclientlist;
	}*/
	
	/*@Override
	public PagingBean<ClientList> paging(Long companyid, Long projectid, Long orderid, int page, Long loginUserId)
			throws IOException {
		PagingBean<ClientList> pagingSystemclientlist = new PagingBean<ClientList>();
		ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
		int count = 0;
		String output = null;
		// 公司、项目、订单id都为空，显示当前用户权限下的所有导入信息
		if (orderid == null && projectid == null && companyid == null) {
			String sql = "select DISTINCT a.* from company a left join projectcompany b on a.id = b.companyid LEFT JOIN userproject c on "
					+ "b.projectid = c.projectid left join project d on b.projectid = d.id left join orderproject e on d.id = e.projectid "
					+ "left join \"order\" f on e.orderid = f.id where f.status = 1 and d.status =2 and c.userid ="
					+ loginUserId;
			List<Company> companies = companyMapper.selectBySql(sql);
			for (int i = 0; i < companies.size(); i++) {
				String projectsql = "select DISTINCT a.* from project a left join projectcompany b on a.id=b.projectid left join userproject c "
						+ "on a.id = c.projectid left join orderproject d on a.id = d.projectid left join \"order\" e on d.orderid = e.id "
						+ "where e.status =1 and a.status = 2 and c.userid =" + loginUserId + " and b.companyid="
						+ companies.get(i).getId();
				List<Project> projects = projectMapper.selectBysql(projectsql);
				String companyname = companies.get(i).getCompanyregname();
				int start = (page - 1) * 10;
				for (int j = 0; j < projects.size(); j++) {
					String ordersql = "select DISTINCT a.* from \"order\" a LEFT JOIN orderproject b on a.ID = b.orderid LEFT JOIN"
							+ " userproject c on b.projectid = c.projectid where a.status = 1 and c.userid = '"
							+ loginUserId + "' and b.projectid = '" + projects.get(j).getId() + "'";
					List<Order> orders = orderMapper.selectBysql(ordersql);
					List<String> ordernumLists = new ArrayList<String>();
					//遍历得到当前用户权限下可见的所有订单号
					for(Order order:orders){
						ordernumLists.add(order.getOrdernum());
					}
							
					String projectname = projects.get(j).getProjectregname();
					String command = "ClientInfoQuery";
					String ordernum = null;
					int version = 256;
					int client_num = 10;
					//返回全部数据，一次最多返回1000条
//					int client_num = -1;
					output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
					logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
					JSONObject jsStr = JSONObject.fromObject(output);
//					count = count + Integer.parseInt(jsStr.getString("total_num"));
					String client_numResponse = jsStr.getString("client_num");
					if (client_numResponse.equals("0")) {
						continue;
					} else {
						JSONArray client_list = jsStr.getJSONArray("client_list");
//						ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
						for (int k = 0; k < client_list.size(); k++) {
							String orderNum = client_list.getJSONObject(k).getString("order_id");
							if(ordernumLists.contains(orderNum)){
								ClientList clientList = new ClientList();
								clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
								clientList.setProvince(client_list.getJSONObject(k).getString("province"));
								clientList.setCity(client_list.getJSONObject(k).getString("city"));
								clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
								clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
								clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
								clientList.setContact(client_list.getJSONObject(k).getString("contact"));
								clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
								clientList.setAddress(client_list.getJSONObject(k).getString("address"));
								clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
								clientList.setTime(client_list.getJSONObject(k).getString("time"));
//								clientLists.add(clientList);
								
								if(clientLists.size()==10){
				        			 continue;
				        		}
				        		clientLists.add(clientList);
							}
							
						}
						count = count + clientLists.size();
						pagingSystemclientlist.setDatas(clientLists);
					}
				}
			}
		}
		// 点击公司查看客户信息
		else if (companyid != null && projectid == null && orderid == null) {
			// String projectsql = "select a.* from project a left join
			// projectcompany b on a.id=b.projectid where a.status = '1' and
			// b.companyid = " + companyid;
			// String projectsql = "select DISTINCT a.* from project a left join
			// projectcompany b on a.id = b.projectid LEFT JOIN userproject c "
			// + "on a.id = c.projectid where a.status = '1' and c.userid
			// ="+loginUserId+ " and b.companyid ="+companyid;
			String projectsql = "select DISTINCT a.* from project a left join projectcompany b on a.id=b.projectid left join userproject c "
					+ "on a.id = c.projectid left join orderproject d on a.id = d.projectid left join \"order\" e on d.orderid = e.id "
					+ "where e.status =1 and a.status = 2 and c.userid =" + loginUserId + " and b.companyid="
					+ companyid;
			List<Project> projects = projectMapper.selectBysql(projectsql);
			List<Company> companies = companyMapper.selectById(companyid);
			String companyname = companies.get(0).getCompanyregname();
			for (int j = 0; j < projects.size(); j++) {
				String projectname = projects.get(j).getProjectregname();
				String command = "ClientInfoQuery";
				String ordernum = null;
				int version = 256;
				int client_num = 10;
				int start = (page - 1) * 10;

				output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
				logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
				JSONObject jsStr = JSONObject.fromObject(output);
				count = count + Integer.parseInt(jsStr.getString("total_num"));

				if (jsStr.getString("client_num").equals("0")) {
					continue;
				} else {
					JSONArray client_list = jsStr.getJSONArray("client_list");
//					ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
					for (int k = 0; k < client_list.size(); k++) {
						ClientList clientList = new ClientList();
						clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
						clientList.setProvince(client_list.getJSONObject(k).getString("province"));
						clientList.setCity(client_list.getJSONObject(k).getString("city"));
						clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
						clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
						clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
						clientList.setContact(client_list.getJSONObject(k).getString("contact"));
						clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
						clientList.setAddress(client_list.getJSONObject(k).getString("address"));
						clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
						clientList.setTime(client_list.getJSONObject(k).getString("time"));
						clientLists.add(clientList);
					}
					pagingSystemclientlist.setDatas(clientLists);
				}
			}
		}
		// 点击项目查看客户信息
		else if (companyid == null && projectid != null && orderid == null) {
			Project projects = projectMapper.selectById(projectid);
			String projectname = projects.getProjectregname();
			String sql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = "
					+ projectid;
			List<Company> companies = companyMapper.selectBySql(sql);
			String companyname = companies.get(0).getCompanyregname();
			String command = "ClientInfoQuery";
			String ordernum = null;
			int version = 256;
			int client_num = 10;
			int start = (page - 1) * 10;
			output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
			logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
			JSONObject jsStr = JSONObject.fromObject(output);
			count = count + Integer.parseInt(jsStr.getString("total_num"));

			if (jsStr.getString("client_num").equals("0")) {
				pagingSystemclientlist.setTotalCount(count);
				return pagingSystemclientlist;
			} else {
				JSONArray client_list = jsStr.getJSONArray("client_list");
//				ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
				for (int k = 0; k < client_list.size(); k++) {
					ClientList clientList = new ClientList();
					clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
					clientList.setProvince(client_list.getJSONObject(k).getString("province"));
					clientList.setCity(client_list.getJSONObject(k).getString("city"));
					clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
					clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
					clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
					clientList.setContact(client_list.getJSONObject(k).getString("contact"));
					clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
					clientList.setAddress(client_list.getJSONObject(k).getString("address"));
					clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
					clientList.setTime(client_list.getJSONObject(k).getString("time"));
					clientLists.add(clientList);
				}
				pagingSystemclientlist.setDatas(clientLists);
			}
		}
		// 点击订单查看客户信息
		else {
			String ordersql = "select a.* from \"order\" a where a.id = " + orderid;
			List<Order> orders = orderMapper.selectBysql(ordersql);
			String ordernum = orders.get(0).getOrdernum();
			String projectsql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = "
					+ orderid;
			List<Project> projects = projectMapper.selectBysql(projectsql);
			String projectname = projects.get(0).getProjectregname();
			String companysql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = "
					+ projects.get(0).getId();
			List<Company> companies = companyMapper.selectBySql(companysql);
			String companyname = companies.get(0).getCompanyregname();
			String command = "ClientInfoQuery";
			int version = 256;
			int client_num = 10;
			int start = (page - 1) * 10;
			output = ClientInfoQuery(command, version, companyname, projectname, ordernum, client_num, start);
			logger.info("ClientInfoQuery接口查询客户信息返回值:" + output);
			JSONObject jsStr = JSONObject.fromObject(output);
			count = count + Integer.parseInt(jsStr.getString("total_num"));

			if (jsStr.getString("client_num").equals("0")) {
				pagingSystemclientlist.setTotalCount(count);
				return pagingSystemclientlist;
			} else {
				JSONArray client_list = jsStr.getJSONArray("client_list");
//				ArrayList<ClientList> clientLists = new ArrayList<ClientList>();
				for (int k = 0; k < client_list.size(); k++) {
					ClientList clientList = new ClientList();
					clientList.setOrderid(client_list.getJSONObject(k).getString("order_id"));
					clientList.setProvince(client_list.getJSONObject(k).getString("province"));
					clientList.setCity(client_list.getJSONObject(k).getString("city"));
					clientList.setDistrict(client_list.getJSONObject(k).getString("district"));
					clientList.setClient_code(client_list.getJSONObject(k).getString("client_code"));
					clientList.setClient_name(client_list.getJSONObject(k).getString("client_name"));
					clientList.setContact(client_list.getJSONObject(k).getString("contact"));
					clientList.setTelno(client_list.getJSONObject(k).getString("telno"));
					clientList.setAddress(client_list.getJSONObject(k).getString("address"));
					clientList.setRemark(client_list.getJSONObject(k).getString("remark"));
					clientList.setTime(client_list.getJSONObject(k).getString("time"));
					clientLists.add(clientList);
				}
				pagingSystemclientlist.setDatas(clientLists);
			}
		}
		pagingSystemclientlist.setTotalCount(count);
		return pagingSystemclientlist;
	}*/

	public String ClientInfoQuery(String command, int version, String company_name, String project, String ordernum,
			int client_num, int start) throws IOException {
		if ((command == null || command.equals("")) || (version == 0)) {
			System.err.println("接口名不能为空!");
			return "-1";
		} else if (company_name == null || company_name.equals("")) {
			System.err.println("公司名不能为空!");
			return "-2";
		} else if (project == null || project.equals("")) {
			System.err.println("项目名不能为空!");
			return "-3";
		} else {
			Properties pro = new Properties();
			InputStream in = getClass().getResourceAsStream("/jdbc.properties");
			try {
				pro.load(in);
			} catch (IOException e) {
				System.err.println("读取配置文件失败");
			}
			String ip = pro.getProperty("serverip").trim();
			String targetURL = "http://" + ip + ":8910/";

			URL targetUrl = null;
			HttpURLConnection httpURLConnection = null;
			try {
				targetUrl = new URL(targetURL);
				httpURLConnection = (HttpURLConnection) targetUrl.openConnection();
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setRequestProperty("Content-Type", "application/json");
			} catch (MalformedURLException e) {
				System.err.println("读取URL失败");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("获取httpURLConnection失败");
				e.printStackTrace();
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", command);
			jsonObject.put("version", version);
			jsonObject.put("company_name", company_name);
			jsonObject.put("project", project);
			jsonObject.put("order_id", ordernum);
			jsonObject.put("client_num", client_num);
			jsonObject.put("start", start);
			String input = jsonObject.toString();
			logger.info("查询客户信息传入参数:" + input);
			// System.out.println(input);
			OutputStream outputStream = null;
			String output = null;
			try {
				outputStream = httpURLConnection.getOutputStream();
				outputStream.write(input.getBytes());
				outputStream.flush();
				if (httpURLConnection.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + httpURLConnection.getResponseCode());
				}
				BufferedReader responseBuffer = new BufferedReader(
						new InputStreamReader((httpURLConnection.getInputStream())));
				output = responseBuffer.readLine();
				// System.out.println(output);
			} catch (IOException e) {
				e.printStackTrace();
			}
			httpURLConnection.disconnect();
			return output;
		}
	}

	@Override
	// public List<TreeBean> getUserTreeByUserId(Long loginUserId) {
	public List<TreeBean> getUserTreeByUserId(Long loginUserId, Long requirementtype, String searchname) {
		// String sql = "select DISTINCT a.* from company a left join
		// projectcompany b on a.id = b.companyid LEFT JOIN userproject c on
		// b.projectid = c.projectid where c.userid = " + loginUserId;
		String sql = "select DISTINCT a.* from company a left join projectcompany b on a.id = b.companyid LEFT JOIN userproject c on b.projectid ="
				+ " c.projectid left join project d on b.projectid = d.id left join orderproject e on d.id = e.projectid left join \"order\" f "
				+ "on e.orderid = f.id where f.status = 1 and d.status =2 and c.userid =" + loginUserId;

		// 搜索框查询公司
		if (requirementtype != null && requirementtype == 1L) {
			sql += " and a.companyname like '%" + searchname + "%'";
		}
		if (requirementtype != null && requirementtype == 2L) {
			sql += " and d.projectname like '%" + searchname + "%'";
		}
		if (requirementtype != null && requirementtype == 3L) {
			sql += " and f.ordernum like '%" + searchname + "%'";
		}

		List<Company> companies = companyMapper.selectBySql(sql);
		List<TreeBean> companyTree = new ArrayList<TreeBean>();
		for (Company company : companies) {
			TreeBean companyBean = new TreeBean();
			List<TreeBean> projectTree = new ArrayList<TreeBean>();
			// String projectsql = "select DISTINCT a.* from project a left join
			// projectcompany b on a.id = b.projectid LEFT JOIN"
			// + " userproject c on a.id = c.projectid where a.status = '1' and
			// c.userid = '" +loginUserId + "' and b.companyid = '" +
			// company.getId() + "'";

			String projectsql = "select DISTINCT a.* from project a left join projectcompany b on a.id=b.projectid left join userproject c "
					+ "on a.id = c.projectid left join orderproject d on a.id = d.projectid left join \"order\" e on d.orderid = e.id "
					+ "where e.status =1 and a.status = 2 and c.userid =" + loginUserId + " and b.companyid= '"
					+ company.getId() + "'";

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
			for (Project project : projects) {
				TreeBean projectBean = new TreeBean();
				List<TreeBean> orderTree = new ArrayList<TreeBean>();
				// String ordersql = "select DISTINCT a.* from \"order\" a LEFT
				// JOIN orderproject b on a.ID = b.orderid LEFT JOIN"
				// + " userproject c on b.projectid = c.projectid where a.status
				// = '1' and c.userid = '" + loginUserId + "' and b.projectid =
				// '" + project.getId() +"'";

				String ordersql = "select DISTINCT a.* from \"order\" a LEFT JOIN orderproject b on a.ID = b.orderid LEFT JOIN"
						+ " userproject c on b.projectid = c.projectid where a.status = 1 and c.userid = '"
						+ loginUserId + "' and b.projectid = '" + project.getId() + "'";

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
				for (Order order : orders) {
					TreeBean orderBean = new TreeBean();
					orderBean.setId(order.getId());
					orderBean.setName(order.getOrdernum());
					orderBean.setPid(project.getId());
					orderTree.add(orderBean);
				}
				projectBean.setChildren(orderTree);
				projectTree.add(projectBean);

			}
			companyBean.setChildren(projectTree);
			companyTree.add(companyBean);
		}
		return companyTree;
	}

	@Override
	public Company queryCompanyById(String companyId) {
		Company company = companyMapper.selectById1(Long.parseLong(companyId));
		return company;
	}

	@Override
	public Project queryProjectById(String projectId) {
		return projectMapper.selectById(Long.parseLong(projectId));
	}

}
