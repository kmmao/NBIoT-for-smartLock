package com.routon.plcloud.admin.order.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.admin.order.utils.userloginUtils;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.privilege.service.log.OrderServiceLog;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.model.OrderProject;
import com.routon.plcloud.common.model.OrderSuborder;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.Role;
import com.routon.plcloud.common.model.SubOrder;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.OrderMapper;
import com.routon.plcloud.common.persistence.OrderProjectMapper;
import com.routon.plcloud.common.persistence.OrderSuborderMapper;
import com.routon.plcloud.common.persistence.ProjectCompanyMapper;
import com.routon.plcloud.common.persistence.ProjectMapper;
import com.routon.plcloud.common.persistence.RoleMapper;
import com.routon.plcloud.common.persistence.SoftwareMapper;
import com.routon.plcloud.common.persistence.SuborderMapper;

import net.sf.json.JSONObject;

@Service
public class OrderServiceImpl implements OrderService {

	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	// private static final String targetURL = "http://172.16.42.38:8910/";

	// @Autowired
	// private UserMapper userMapper;
	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private SuborderMapper suborderMapper;

	@Autowired
	private OrderSuborderMapper ordersuborderMapper;

	@Autowired
	private OrderProjectMapper orderprojectMapper;

	@Autowired
	private ProjectCompanyMapper projectcompanyMapper;

	@Autowired
	private SoftwareMapper softwareMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private OrderServiceLog orderServiceLog;

	@Autowired
	private OrderService orderService;
	
	@Resource(name = "pagingDaoMybatis")
	private PagingDaoMybatis pagingDao;

	// @Override
	//// public List<TreeBean> getMenuTrees(Long loginUserId) {
	// String sql = null;
	// // if(loginUserId != 1){
	// // 销售助理是否有最大的权限能看到所有的公司、项目和订单。
	// // String sqlcompany = "select * from users where id = " + loginUserId;
	// // List<User> listuser = userMapper.selectBySql(sqlcompany);
	// // String company = listuser.get(0).getCompany();
	//
	// // sql = "select * from company where companyname = '" + company + "'";
	// // 查询当前用户可看到的公司
	// sql = "select distinct c.* from userproject a left join projectcompany b
	// on a.projectid = b.projectid left join company c on "
	// + "b.companyid = c.id where c.id is not null and a.userid = " +
	// loginUserId;
	// // }else{
	// // sql = "select * from company";
	// // }
	//
	// List<Company> list = companyMapper.selectBySql(sql);
	// List<TreeBean> companyTree = new ArrayList<TreeBean>();
	// for (Company companyList : list) {
	// TreeBean treeBean = new TreeBean();
	// List<TreeBean> projectTree = new ArrayList<TreeBean>();
	// // 查询当前用户公司下可看到的项目
	// String sqlproject = "select b.* from userproject a left join project b on
	// a.projectid = b.id left join projectcompany c on "
	// + "b.id = c.projectid where a.userid = " + loginUserId + "and b.status =
	// 1 and c.companyid = '"
	// + companyList.getId() + "'";
	// List<Project> projectList = projectMapper.selectBysql(sqlproject);
	//
	// // List<Project> projectList = projectMapper.selectBysql("select a.*
	// // from project a left join projectcompany p on a.id = p.projectid"
	// // + " where p.companyid = '" + companyList.getId() + "'");
	// treeBean.setId(companyList.getId());
	// treeBean.setName(companyList.getCompanyname());
	// treeBean.setPid(Long.parseLong("0"));
	// treeBean.setParent(true);
	// treeBean.setOpen(true);
	// for (Project project : projectList) {
	// TreeBean projectbean = new TreeBean();
	// projectbean.setId(project.getId());
	// projectbean.setName(project.getProjectname());
	// projectbean.setPid(companyList.getId());
	// projectTree.add(projectbean);
	// }
	// treeBean.setChildren(projectTree);
	// companyTree.add(treeBean);
	// }
	// return companyTree;
	// }

	@Override
	public List<TreeBean> getMenuTrees(Long loginUserId, Long requirementtype, String searchname) {
		String sql = null;
		// 查询当前用户可看到的公司
//		sql = "select distinct c.* from userproject a left join projectcompany b on a.projectid = b.projectid left join company c on "
//				+ "b.companyid = c.id where c.id is not null and a.userid = " + loginUserId;
		sql="select distinct c.* from userproject a left join project d on a.projectid =d.id left join projectcompany b on b.projectid=d.id "
				+ "left join company c on b.companyid = c.id where c.id is not null and a.userid ="+ loginUserId;
		// 搜索框查询公司
		if (requirementtype != null && requirementtype == 1L) {
			sql += " and c.companyname like '%" + searchname + "%'";
		}
		if (requirementtype != null && requirementtype == 2L) {
			sql += " and d.projectname like '%" + searchname + "%'";
		}

		List<Company> list = companyMapper.selectBySql(sql);
		List<TreeBean> companyTree = new ArrayList<TreeBean>();
		for (Company companyList : list) {
			TreeBean treeBean = new TreeBean();
			List<TreeBean> projectTree = new ArrayList<TreeBean>();
			// 查询当前用户公司下可看到的项目
			String sqlproject = "select b.* from userproject a left join project b on a.projectid = b.id left join projectcompany c on "
					+ "b.id = c.projectid where a.userid = " + loginUserId + "and b.status = 2 and c.companyid = '"
					+ companyList.getId() + "'";
			
			if (requirementtype != null && requirementtype == 2L) {
				sqlproject += " and b.projectname like '%" + searchname + "%'";
			}
			
			List<Project> projectList = projectMapper.selectBysql(sqlproject);

			treeBean.setId(companyList.getId());
			treeBean.setName(companyList.getCompanyname());
			treeBean.setPid(Long.parseLong("0"));
			treeBean.setParent(true);
			treeBean.setOpen(true);
			for (Project project : projectList) {
				TreeBean projectbean = new TreeBean();
				projectbean.setId(project.getId());
				projectbean.setName(project.getProjectname());
				projectbean.setPid(companyList.getId());
				projectTree.add(projectbean);
			}
			treeBean.setChildren(projectTree);
			companyTree.add(treeBean);
		}
		return companyTree;
	}

	// @Override
	// public List<TreeBean> getMenuTrees() {
	// String sql = "select * from company";
	// List<Company> list = companyMapper.selectBySql(sql);
	// List<TreeBean> companyTree = new ArrayList<TreeBean>();
	// for (Company companyList : list){
	// TreeBean treeBean = new TreeBean();
	// List<TreeBean> projectTree = new ArrayList<TreeBean>();
	// List<Project> projectList = projectMapper.selectBysql("select a.* from
	// project a left join projectcompany p on a.id = p.projectid"
	// + " where p.companyid = '" + companyList.getId() + "'");
	// treeBean.setId(companyList.getId());
	// treeBean.setName(companyList.getCompanyname());
	// treeBean.setPid(Long.parseLong("0"));
	// treeBean.setParent(true);
	// treeBean.setOpen(true);
	// for(Project project : projectList){
	// TreeBean projectbean = new TreeBean();
	// projectbean.setId(project.getId());
	// projectbean.setName(project.getProjectname());
	// projectbean.setPid(companyList.getId());
	// projectTree.add(projectbean);
	// }
	// treeBean.setChildren(projectTree);
	// companyTree.add(treeBean);
	// }
	// return companyTree;
	// }

	// @Override
	// public PagingBean<Order> queryALL(String orderNum, int startIndex, int
	// pageSize, String projectid) {
	// StringBuffer sbHQL = null;
	// if(projectid == null){
	// String sql = "select * from \"order\" where 1=1";
	// sbHQL = new StringBuffer(sql);
	// } else{
	// String sql = "select a.* from \"order\" a left join orderproject o on
	// a.id = o.orderid where"
	// + " o.projectid = '" + projectid + "'";
	// sbHQL = new StringBuffer(sql);
	// }
	//
	// PagingBean<Order> pagingSystemhardware = pagingDao.query(orderMapper,
	// sbHQL.toString(),
	// null, null, startIndex, pageSize, false);
	// return pagingSystemhardware;
	// }

	// 子订单列表
	@Override
	public PagingBean<SubOrder> pagingchildOrder(int startIndex, int pageSize, Integer orderid) {
		StringBuffer sbHQL = null;
		// String sql = "select * from \"order\" where id = "+ orderid;
		// String sql = "select a.* from order_suborder b left join suborder a
		// on b.suborderid=a.id where b.orderid="+ orderid+" order by
		// a.createtime desc";
		String sql = "select * from (select a.* from order_suborder b left join suborder a on b.suborderid=a.id where "
				+ "b.orderid=" + orderid + " order by a.createtime desc)m";
		sbHQL = new StringBuffer(sql);

		PagingBean<SubOrder> pagingOrder = pagingDao.query(suborderMapper, sbHQL.toString(), null, null, startIndex,
				pageSize, false);
		return pagingOrder;
	}

	@Override
	public PagingBean<Order> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection,
			Order queryCondition, String status, String projectid, String companyid, String in_orderIds,
			String notin_orderIds, Long loginUserId, boolean exportflag) {
		// public PagingBean<Order> paging(int startIndex, int pageSize, String
		// sortCriterion, String sortDirection,
		// Order queryCondition,String projectid, String in_orderIds, String
		// notin_orderIds, Long loginUserId,
		// boolean exportflag) {

		StringBuffer sbHQL = null;
		// if(loginUserId == 1){
		// if(projectid == null){
		// if(companyid != null){
		// String sql = "SELECT a.* FROM \"order\" a left join orderproject b on
		// a.id = b.orderid left join project c on "
		// + "c.id = b.projectid left join projectcompany d on d.projectid =
		// c.id left JOIN company e on "
		// + "e.id = d.companyid where a.status = 1 and e.id = '" + companyid +
		// "'";
		// sbHQL = new StringBuffer(sql);
		// }else{
		// String sql = "select a.* from \"order\" a where a.status=1";
		// sbHQL = new StringBuffer(sql);
		// }
		//// String sql = "select a.* from \"order\" a where a.status=1";
		//// sbHQL = new StringBuffer(sql);
		// } else{

		// String sql = "select a.* from \"order\" a left join orderproject o on
		// a.id = o.orderid where a.status=1 and"
		// + " o.projectid = '" + projectid + "'";
		// sbHQL = new StringBuffer(sql);
		// }
		// }
		// else{
		// //只显示status=1的启用订单
		// if (projectid == null) {
		// if (companyid != null) {
		// // 查询当前公司下可见项目的所有订单
		// String sql = "select * from (select a.* from userproject e left join
		// project b on e.projectid = b.id left join projectcompany c on "
		// + "b.id = c.projectid left join orderproject d on b.id = d.projectid
		// left join \"order\" a on d.orderid = a.id "
		// + "where e.userid =" + loginUserId
		// + " and a.status = 1 and a.id is not NULL and b.status = 1 and
		// c.companyid = '" + companyid
		// + "'" + " order by a.createtime DESC)m";
		// sbHQL = new StringBuffer(sql);
		// } else {
		// // 查询当前用户下可见的所有订单
		// String sql = "select * from (select a.* from userproject d left join
		// project b on d.projectid = b.id left join orderproject c on "
		// + "b.id = c.projectid left join \"order\" a on c.orderid = a.id where
		// a.status = 1 and b.status = 1 and d.userid ="
		// + loginUserId + " order by a.createtime DESC)m";
		// sbHQL = new StringBuffer(sql);
		// }
		// } else {
		// String sql = "select * from (select a.* from \"order\" a left join
		// orderproject o on a.id = o.orderid where a.status=1 and"
		// + " o.projectid = '" + projectid + "'"+ " order by a.createtime
		// DESC)m";
		// sbHQL = new StringBuffer(sql);
		// }
		// if(status !=null){
		// if (Integer.valueOf(status) == 1) {
		
		//按生成时间倒序排列
		/*if (queryCondition.getStatus() == 1) {
			// 显示启用状态下订单 status == 1，项目status=2表示已审核项目
			if (projectid == null) {
				if (companyid != null) {
					// 查询当前公司下可见项目的所有订单
					String sql = "select * from (select a.* from userproject e left join project b on e.projectid = b.id left join projectcompany c on "
							+ "b.id = c.projectid left join orderproject d on b.id = d.projectid  left join \"order\" a on d.orderid = a.id "
							+ "where e.userid =" + loginUserId
							+ " and a.id is not NULL and a.status = 1 and b.status = 2 and c.companyid = '" + companyid
							+ "'" + " order by a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				} else {
					// 查询当前用户下可见的所有订单
					String sql = "select * from (select a.* from userproject d left join project b on d.projectid = b.id left join orderproject c on "
							+ "b.id = c.projectid left join \"order\" a on c.orderid = a.id where b.status = 2 and a.status = 1 and a.id is not null and d.userid ="
							+ loginUserId + " order by a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				}
			} else {
				String sql = "select * from (select a.* from \"order\" a left join orderproject o on a.id = o.orderid where a.status = 1 and"
						+ " o.projectid = '" + projectid + "'" + " order by a.createtime DESC)m";
				sbHQL = new StringBuffer(sql);
			}
		}
		// else if (Integer.valueOf(status) == 2) {
		else if (queryCondition.getStatus() == 2) {
			// 显示结束状态下订单 status == 2
			if (projectid == null) {
				if (companyid != null) {
					// 查询当前公司下可见项目的所有订单
					String sql = "select * from (select a.* from userproject e left join project b on e.projectid = b.id left join projectcompany c on "
							+ "b.id = c.projectid left join orderproject d on b.id = d.projectid  left join \"order\" a on d.orderid = a.id "
							+ "where e.userid =" + loginUserId
							+ " and a.id is not NULL and a.status = 2 and b.status = 2 and c.companyid = '" + companyid
							+ "'" + " order by a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				} else {
					// 查询当前用户下可见的所有订单
					String sql = "select * from (select a.* from userproject d left join project b on d.projectid = b.id left join orderproject c on "
							+ "b.id = c.projectid left join \"order\" a on c.orderid = a.id where b.status = 2 and a.status = 2 and a.id is not null and d.userid ="
							+ loginUserId + " order by a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				}
			} else {
				String sql = "select * from (select a.* from \"order\" a left join orderproject o on a.id = o.orderid where a.status = 2 and"
						+ " o.projectid = '" + projectid + "'" + " order by a.createtime DESC)m";
				sbHQL = new StringBuffer(sql);
			}
		} 
		else if (queryCondition.getStatus() == 3) {
			// 显示强制停止状态下订单 status == 0
			if (projectid == null) {
				if (companyid != null) {
					// 查询当前公司下可见项目的所有订单
					String sql = "select * from (select a.* from userproject e left join project b on e.projectid = b.id left join projectcompany c on "
							+ "b.id = c.projectid left join orderproject d on b.id = d.projectid  left join \"order\" a on d.orderid = a.id "
							+ "where e.userid =" + loginUserId
							+ " and a.id is not NULL and a.status = 0 and b.status = 2 and c.companyid = '" + companyid
							+ "'" + " order by a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				} else {
					// 查询当前用户下可见的所有订单
					String sql = "select * from (select a.* from userproject d left join project b on d.projectid = b.id left join orderproject c on "
							+ "b.id = c.projectid left join \"order\" a on c.orderid = a.id where b.status = 2 and a.status = 0 and a.id is not null and d.userid ="
							+ loginUserId + " order by a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				}
			} else {
				String sql = "select * from (select a.* from \"order\" a left join orderproject o on a.id = o.orderid where a.status = 0 and"
						+ " o.projectid = '" + projectid + "'" + " order by a.createtime DESC)m";
				sbHQL = new StringBuffer(sql);
			}
		}
		else {
			// 显示所有订单
			if (projectid == null) {
				if (companyid != null) {
					// 查询当前公司下可见项目的所有订单
					String sql = "select * from (select a.* from userproject e left join project b on e.projectid = b.id left join projectcompany c on "
							+ "b.id = c.projectid left join orderproject d on b.id = d.projectid  left join \"order\" a on d.orderid = a.id "
							+ "where e.userid =" + loginUserId
							+ " and a.id is not NULL and b.status = 2 and c.companyid = '" + companyid + "'"
							+ " order by a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				} else {
					// 查询当前用户下可见的所有订单
					String sql = "select * from (select a.* from userproject d left join project b on d.projectid = b.id left join orderproject c on "
							+ "b.id = c.projectid left join \"order\" a on c.orderid = a.id where b.status = 2 and a.id is not null and d.userid ="
							+ loginUserId + " order by a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				}
			} else {
				String sql = "select * from (select a.* from \"order\" a left join orderproject o on a.id = o.orderid where "
						+ " o.projectid = '" + projectid + "'" + " order by a.createtime DESC)m";
				sbHQL = new StringBuffer(sql);
			}
		}*/
		
		//先按修改时间倒序排序，再按生成时间倒序排序
		if (queryCondition.getStatus() == 1) {
			// 显示启用状态下订单 status == 1，项目status=2表示已审核项目
			if (projectid == null) {
				if (companyid != null) {
					// 查询当前公司下可见项目的所有订单
					String sql = "select * from (select a.* from userproject e left join project b on e.projectid = b.id left join projectcompany c on "
							+ "b.id = c.projectid left join orderproject d on b.id = d.projectid  left join \"order\" a on d.orderid = a.id "
							+ "where e.userid =" + loginUserId
							+ " and a.id is not NULL and a.status = 1 and b.status = 2 and c.companyid = '" + companyid
							+ "'" + " order by a.moditytime DESC,a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				} else {
					// 查询当前用户下可见的所有订单
					String sql = "select * from (select a.* from userproject d left join project b on d.projectid = b.id left join orderproject c on "
							+ "b.id = c.projectid left join \"order\" a on c.orderid = a.id where b.status = 2 and a.status = 1 and a.id is not null and d.userid ="
							+ loginUserId + " order by a.moditytime DESC,a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				}
			} else {
				String sql = "select * from (select a.* from \"order\" a left join orderproject o on a.id = o.orderid where a.status = 1 and"
						+ " o.projectid = '" + projectid + "'" + " order by a.moditytime DESC,a.createtime DESC)m";
				sbHQL = new StringBuffer(sql);
			}
		}
		// else if (Integer.valueOf(status) == 2) {
		else if (queryCondition.getStatus() == 2) {
			// 显示结束状态下订单 status == 2
			if (projectid == null) {
				if (companyid != null) {
					// 查询当前公司下可见项目的所有订单
					String sql = "select * from (select a.* from userproject e left join project b on e.projectid = b.id left join projectcompany c on "
							+ "b.id = c.projectid left join orderproject d on b.id = d.projectid  left join \"order\" a on d.orderid = a.id "
							+ "where e.userid =" + loginUserId
							+ " and a.id is not NULL and a.status = 2 and b.status = 2 and c.companyid = '" + companyid
							+ "'" + " order by a.moditytime DESC,a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				} else {
					// 查询当前用户下可见的所有订单
					String sql = "select * from (select a.* from userproject d left join project b on d.projectid = b.id left join orderproject c on "
							+ "b.id = c.projectid left join \"order\" a on c.orderid = a.id where b.status = 2 and a.status = 2 and a.id is not null and d.userid ="
							+ loginUserId + " order by a.moditytime DESC,a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				}
			} else {
				String sql = "select * from (select a.* from \"order\" a left join orderproject o on a.id = o.orderid where a.status = 2 and"
						+ " o.projectid = '" + projectid + "'" + " order by a.moditytime DESC,a.createtime DESC)m";
				sbHQL = new StringBuffer(sql);
			}
		} 
		else if (queryCondition.getStatus() == 3) {
			// 显示强制停止状态下订单 status == 0
			if (projectid == null) {
				if (companyid != null) {
					// 查询当前公司下可见项目的所有订单
					String sql = "select * from (select a.* from userproject e left join project b on e.projectid = b.id left join projectcompany c on "
							+ "b.id = c.projectid left join orderproject d on b.id = d.projectid  left join \"order\" a on d.orderid = a.id "
							+ "where e.userid =" + loginUserId
							+ " and a.id is not NULL and a.status = 0 and b.status = 2 and c.companyid = '" + companyid
							+ "'" + " order by a.moditytime DESC,a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				} else {
					// 查询当前用户下可见的所有订单
					String sql = "select * from (select a.* from userproject d left join project b on d.projectid = b.id left join orderproject c on "
							+ "b.id = c.projectid left join \"order\" a on c.orderid = a.id where b.status = 2 and a.status = 0 and a.id is not null and d.userid ="
							+ loginUserId + " order by a.moditytime DESC,a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				}
			} else {
				String sql = "select * from (select a.* from \"order\" a left join orderproject o on a.id = o.orderid where a.status = 0 and"
						+ " o.projectid = '" + projectid + "'" + " order by a.moditytime DESC,a.createtime DESC)m";
				sbHQL = new StringBuffer(sql);
			}
		}
		else {
			// 显示所有订单
			if (projectid == null) {
				if (companyid != null) {
					// 查询当前公司下可见项目的所有订单
					String sql = "select * from (select a.* from userproject e left join project b on e.projectid = b.id left join projectcompany c on "
							+ "b.id = c.projectid left join orderproject d on b.id = d.projectid  left join \"order\" a on d.orderid = a.id "
							+ "where e.userid =" + loginUserId
							+ " and a.id is not NULL and b.status = 2 and c.companyid = '" + companyid + "'"
							+ " order by a.moditytime DESC,a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				} else {
					// 查询当前用户下可见的所有订单
					String sql = "select * from (select a.* from userproject d left join project b on d.projectid = b.id left join orderproject c on "
							+ "b.id = c.projectid left join \"order\" a on c.orderid = a.id where b.status = 2 and a.id is not null and d.userid ="
							+ loginUserId + " order by a.moditytime DESC,a.createtime DESC)m";
					sbHQL = new StringBuffer(sql);
				}
			} else {
				String sql = "select * from (select a.* from \"order\" a left join orderproject o on a.id = o.orderid where "
						+ " o.projectid = '" + projectid + "'" + " order by a.moditytime DESC,a.createtime DESC)m";
				sbHQL = new StringBuffer(sql);
			}
		}
		
		// }else{
		// // 显示所有订单
		// if (projectid == null) {
		// if (companyid != null) {
		// // 查询当前公司下可见项目的所有订单
		// String sql = "select * from (select a.* from userproject e left join
		// project b on e.projectid = b.id left join projectcompany c on "
		// + "b.id = c.projectid left join orderproject d on b.id = d.projectid
		// left join \"order\" a on d.orderid = a.id "
		// + "where e.userid =" + loginUserId
		// + " and a.id is not NULL and b.status = 1 and c.companyid = '" +
		// companyid + "'"
		// + " order by a.createtime DESC)m";
		// sbHQL = new StringBuffer(sql);
		// } else {
		// // 查询当前用户下可见的所有订单
		// String sql = "select * from (select a.* from userproject d left join
		// project b on d.projectid = b.id left join orderproject c on "
		// + "b.id = c.projectid left join \"order\" a on c.orderid = a.id where
		// b.status = 1 and a.id is not null and d.userid ="
		// + loginUserId + " order by a.createtime DESC)m";
		// sbHQL = new StringBuffer(sql);
		// }
		// } else {
		// String sql = "select * from (select a.* from \"order\" a left join
		// orderproject o on a.id = o.orderid where "
		// + " o.projectid = '" + projectid + "'" + " order by a.createtime
		// DESC)m";
		// sbHQL = new StringBuffer(sql);
		// }
		// }
		// // 只显示status=1/0的启用/关闭订单
		// if (projectid == null) {
		// if (companyid != null) {
		// // 查询当前公司下可见项目的所有订单
		// String sql = "select * from (select a.* from userproject e left join
		// project b on e.projectid = b.id left join projectcompany c on "
		// + "b.id = c.projectid left join orderproject d on b.id = d.projectid
		// left join \"order\" a on d.orderid = a.id "
		// + "where e.userid =" + loginUserId
		// + " and a.id is not NULL and b.status = 1 and c.companyid = '" +
		// companyid
		// + "'" + " order by a.createtime DESC)m";
		// sbHQL = new StringBuffer(sql);
		// } else {
		// // 查询当前用户下可见的所有订单
		// String sql = "select * from (select a.* from userproject d left join
		// project b on d.projectid = b.id left join orderproject c on "
		// + "b.id = c.projectid left join \"order\" a on c.orderid = a.id where
		// b.status = 1 and a.id is not null and d.userid ="
		// + loginUserId + " order by a.createtime DESC)m";
		// sbHQL = new StringBuffer(sql);
		// }
		// } else {
		// String sql = "select * from (select a.* from \"order\" a left join
		// orderproject o on a.id = o.orderid where "
		// + " o.projectid = '" + projectid + "'" + " order by a.createtime
		// DESC)m";
		// sbHQL = new StringBuffer(sql);
		// }

		// }

		// if(projectid == null){
		// if(companyid != null){
		// String sql = "SELECT a.* FROM \"order\" a left join orderproject b on
		// a.id = b.orderid left join project c on "
		// + "c.id = b.projectid left join projectcompany d on d.projectid =
		// c.id left JOIN company e on "
		// + "e.id = d.companyid where a.status = 1 and e.id = '" + companyid +
		// "'";
		// sbHQL = new StringBuffer(sql);
		// }else{
		// String sql = "select a.* from \"order\" a where a.status=1";
		// sbHQL = new StringBuffer(sql);
		// }
		//// String sql = "select a.* from \"order\" a where a.status=1";
		//// sbHQL = new StringBuffer(sql);
		// } else{
		// String sql = "select a.* from \"order\" a left join orderproject o on
		// a.id = o.orderid where a.status=1 and"
		// + " o.projectid = '" + projectid + "'";
		// sbHQL = new StringBuffer(sql);
		// }

		// String pagingQueryLanguage = " select a.* from \"order\" a where 1=1
		// ";

		// StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);

		if (StringUtils.isNotBlank(queryCondition.getOrdernum())) {

			String orderNum = queryCondition.getOrdernum().trim();

			sbHQL.append(" where m.ordernum like '%");
			sbHQL.append(orderNum);
			sbHQL.append("%'");
		}

		String[] sortCriterions = null;
		if (sortCriterion != null) {
			sortCriterions = new String[] { "m." + sortCriterion };
		}

		PagingSortDirection[] sortDirections = null;
		if (sortDirection != null) {
			sortDirections = new PagingSortDirection[] {
					"desc".equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC : PagingSortDirection.ASC };
		}

		PagingBean<Order> pagingOrder = pagingDao.query(orderMapper, sbHQL.toString(), sortCriterions, sortDirections,
				startIndex, pageSize, exportflag);

		return pagingOrder;
	}

	@Override
	public Company queryCompanyById(String projectid) {
		Company company = companyMapper.selectById1(Long.parseLong(projectid));
		return company;
	}

	@Override
	public Project queryProjectById(String projectId) {
		return projectMapper.selectById(Long.parseLong(projectId));
	}

	@Override
	public List<String> querySoftwareErpCodeAll() {
		List<String> list = softwareMapper.selectbysql("select a.erpcode from softwareproduct a");
		return list;
	}

	@Override
	public String querySoftwareNameByERPcode(String erpCode) {

		Map<String, String> map = softwareMapper.selectbysql2(
				"select a.softwarename, a.softwareversion from softwareproduct a where a.erpcode = '" + erpCode + "'");
		return map.get("softwarename") + " " + map.get("softwareversion");
	}

	public String licenceInfoConfig(String companyname, Project project, Order order) {
		String str = getProperties();
		String[] use_pass = str.split(":");
		String username = use_pass[0];
		String password = use_pass[use_pass.length - 1];
		// System.out.println("username=" + username);
		// System.out.println("password=" + password);
		// userloginUtils ul = new userloginUtils();
		byte[] user_key = userloginUtils.getUserkey(username, password);

		if (user_key != null) {
			// 利用user_key加密密码
			String mimaStr = null;
			try {
				byte[] newKey = userloginUtils.get24Byte(user_key);
				byte[] mi = new byte[16];
				System.arraycopy(password.getBytes(), 0, mi, 0, password.getBytes().length);
				byte[] mimaByte = userloginUtils.des3EncodeECB(newKey, mi);
				mimaStr = userloginUtils.byteArr2str(mimaByte);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String company_name = companyname;
			// System.out.println("company_name:" + company_name);
//			String projectname = project.getProjectname();
			String projectname = project.getProjectregname();
			// System.out.println("project_name:" + project);
			String order_id = order.getOrdernum();
			// System.out.println("order_num:" + order_id);
			String sdk_name = project.getSdkname();
			// System.out.println("sdk_name:" + sdk_name);

			String month = order.getMonth();
			String endtime = order.getEndtime();
			String tacitstarttime = order.getTacitstarttime();
			String expire_date = null;
			int lic_time = 0;
			if (month != null) {
				if (endtime != null) {
					// 合同第一种
					expire_date = convert(endtime);
					// expire_date = endtime;
					// expire_date = sdf.format(endtime);
				} else {
					// 合同第二种
					// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd
					// HH:mm:ss");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = sdf.parse(tacitstarttime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(date);
					gc.add(2, Integer.parseInt(month));
					// expire_date = sdf.format(gc.getTime());
					// System.out.println(expire_date);
					expire_date = convert(sdf.format(gc.getTime()));
					lic_time = Integer.parseInt(month);
				}
			} else {
				// 永久有效
				expire_date = "20991231";
			}
			// System.out.println("expire_date:" + expire_date);
			//
			// System.out.println("lic_time:" + lic_time);

			String license_type = order.getBindingmode();
			// System.out.println("license_type:" + license_type);
			int lic_type = 0;
			if ("机器码".equals(license_type)) {
				// lic_type = 1;
				lic_type = 5;
			} else if ("设备码".equals(license_type)) {
				// lic_type = 2;
				lic_type = 6;
			} else if ("有效期".equals(license_type)) {
				lic_type = 4;
			}
			// else if ("机器码+有效期".equals(license_type)) {
			// lic_type = 5;
			// } else if ("设备码+有效期".equals(license_type)) {
			// lic_type = 6;
			// } else {
			// lic_type = 0;
			// }
			// System.out.println("lic_type:" + lic_type);

			int term_num = Integer.parseInt(order.getDemandquantity());
			// System.out.println("term_num:" + term_num);

			byte[] time = userloginUtils.GetLocalTime();
			// 拼凑计算mac值的msg_data
			// byte[] msg_data5 = new byte[224];
			// System.arraycopy(username.getBytes(), 0, msg_data5, 0,
			// username.getBytes().length);
			// System.arraycopy(password.getBytes(), 0, msg_data5, 32,
			// password.getBytes().length);
			// System.arraycopy(company_name.getBytes(), 0, msg_data5, 64,
			// company_name.getBytes().length);
			// System.arraycopy(projectname.getBytes(), 0, msg_data5, 96,
			// projectname.getBytes().length);
			// System.arraycopy(order_id.getBytes(), 0, msg_data5, 128,
			// order_id.getBytes().length);
			// System.arraycopy(sdk_name.getBytes(), 0, msg_data5, 144,
			// sdk_name.getBytes().length);
			// System.arraycopy(expire_date.getBytes(), 0, msg_data5, 176,
			// expire_date.getBytes().length);
			//
			// byte[] lictimeByte = userloginUtils.intToByteArray(lic_time);
			// byte[] lictimeZero = userloginUtils.deltZero(lictimeByte);
			// System.arraycopy(lictimeZero, 0, msg_data5, 192,
			// lictimeZero.length);
			//
			// byte[] lictypeByte = userloginUtils.intToByteArray(lic_type);
			// byte[] lictypeZero = userloginUtils.deltZero(lictypeByte);
			// System.arraycopy(lictypeZero, 0, msg_data5, 196,
			// lictypeZero.length);
			//
			// byte[] lictermByte = userloginUtils.intToByteArray(term_num);
			// byte[] lictermZero = userloginUtils.deltZero(lictermByte);
			// System.arraycopy(lictermZero, 0, msg_data5, 200,
			// lictermZero.length);
			// System.arraycopy(time, 0, msg_data5, 208, time.length);

			byte[] msg_data5 = new byte[256];
			System.arraycopy(username.getBytes(), 0, msg_data5, 0, username.getBytes().length);
			System.arraycopy(password.getBytes(), 0, msg_data5, 32, password.getBytes().length);
			System.arraycopy(company_name.getBytes(), 0, msg_data5, 64, company_name.getBytes().length);
			System.arraycopy(projectname.getBytes(), 0, msg_data5, 128, projectname.getBytes().length);
			System.arraycopy(order_id.getBytes(), 0, msg_data5, 160, order_id.getBytes().length);
			System.arraycopy(sdk_name.getBytes(), 0, msg_data5, 176, sdk_name.getBytes().length);
			System.arraycopy(expire_date.getBytes(), 0, msg_data5, 208, expire_date.getBytes().length);

			byte[] lictimeByte = userloginUtils.intToByteArray(lic_time);
			byte[] lictimeZero = userloginUtils.deltZero(lictimeByte);
			System.arraycopy(lictimeZero, 0, msg_data5, 224, lictimeZero.length);

			byte[] lictypeByte = userloginUtils.intToByteArray(lic_type);
			byte[] lictypeZero = userloginUtils.deltZero(lictypeByte);
			System.arraycopy(lictypeZero, 0, msg_data5, 228, lictypeZero.length);

			byte[] lictermByte = userloginUtils.intToByteArray(term_num);
			byte[] lictermZero = userloginUtils.deltZero(lictermByte);
			System.arraycopy(lictermZero, 0, msg_data5, 232, lictermZero.length);
			System.arraycopy(time, 0, msg_data5, 240, time.length);

			String msgdata = userloginUtils.byteArr2str(msg_data5).toLowerCase();
			byte[] msgdataByte = userloginUtils.str2byteArr(msgdata);
			int len = msgdataByte.length;

			// 计算mca
			byte[] mac = userloginUtils.CalcMac(msgdataByte, len, time);
			String calmac = userloginUtils.byteArr2str(mac);

			// 加密mac
			byte[] Key = userloginUtils.str2byteArr(userloginUtils.mac_key);
			byte[] key = userloginUtils.get24Byte(Key);
			String macStr = null;
			try {
				byte[] desmac = userloginUtils.des3EncodeECB(key, userloginUtils.str2byteArr(calmac));
				macStr = userloginUtils.byteArr2str(desmac);
			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONObject jsonObject1 = new JSONObject();
			jsonObject1.put("command", "LicenceInfoConfig");
			jsonObject1.put("version", 256);
			jsonObject1.put("user_name", username);
			jsonObject1.put("user_password", mimaStr);
			jsonObject1.put("company_name", company_name);
			jsonObject1.put("project", projectname);
			jsonObject1.put("order_id", order_id);
			jsonObject1.put("sdk_name", sdk_name);
			jsonObject1.put("expire_date", expire_date);
			jsonObject1.put("lic_time", lic_time);
			jsonObject1.put("lic_type", lic_type);
			jsonObject1.put("term_num", term_num);
			jsonObject1.put("mac", macStr);

			String input = jsonObject1.toString();
			logger.info("上报订单信息给授权服务:" + input);
			// 调用接口
			String output = userloginUtils.initPrivateInterface(input);
			// 检查返回值
			String response = userloginUtils.booResult(output);
			// System.out.println("调用LicenceInfoConfig接口的返回值:" + response);
			return response;
		} else {
			System.err.println("会话密钥为空");
			return null;
		}
	}

	private String convert(String source) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf2.parse(source);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getProperties() {
		Properties pro = new Properties();
		InputStream in = getClass().getResourceAsStream("/handshake.properties");
		try {
			pro.load(in);
		} catch (IOException e) {
			System.err.println("读取配置握手文件失败");
		}
		String username = pro.getProperty("username");
		String password = pro.getProperty("password");
		return username + ":" + password;
	}

	@Override
	// 提供更改审核标志位的接口，最后一步审核完成后更改verify
	public String changeVerify(String companyId, String projectId, String ordernum, Long loginUserId) {
		JSONObject jsonObject = new JSONObject();
		String sql = null;
		String rolename = null;
		if (loginUserId != null && !loginUserId.equals("")) {
			sql = "select r.* from users u left join userrole ur on u.id = ur.userid left join role r on ur.roleid = r.id where u.id ="
					+ loginUserId;
			List<Role> rolelist = roleMapper.selectBySql(sql);
			Role role = rolelist.get(0);
			rolename = role.getName();
		}

		if (companyId != null && companyId != "" && projectId != null && projectId != "" && ordernum != null && ordernum != "") {
			Company company = queryCompanyById(companyId);
			// 获取公司注册名
			String companyname = company.getCompanyregname();
			Project project = queryProjectById(projectId);
			
			Order order = orderMapper.selectByOrdernum(ordernum);
			if(order.getVerify()==2){
				String subordersql="select a.* from \"order\" b left join order_suborder c on b.id=c.orderid left join suborder a on "
						+ "c.suborderid=a.id where b.id="+ order.getId() +" order by a.createtime desc";
				List<SubOrder> suborders = suborderMapper.selectBysql(subordersql);
				String renewnum ="";
				if(suborders.get(0)!=null){
					renewnum = suborders.get(0).getRenewnum();
					String OldorderNum = order.getDemandquantity();
					String NeworderNum = renewnum;
					Long Demandquantity = Long.parseLong(OldorderNum) + Long.parseLong(NeworderNum);
					order.setDemandquantity(String.valueOf(Demandquantity));
				}
			}
			
			if (rolename != null && rolename.equals("财务人员")) {
				String response = licenceInfoConfig(companyname, project, order);
				if (response != null) {
					JSONObject jsStr = JSONObject.fromObject(response);
					int result = jsStr.getInt("result");
					if (result == 0) {
						jsonObject.put("result", result);
						
						//判断是否为续订审核订单,更新订单数量和审核状态
						if(order.getVerify()==2){
							order.setVerify(1);
							orderMapper.updateDemandquantity(order);
						}else if(order.getVerify()==4){//判断是否为开启审核订单，更新订单状态和审核状态
							order.setStatus(1);
							order.setVerify(1);
							orderMapper.updateStatusandVervify(order);
						}else{//新增订单更新审核状态
							orderMapper.updateVervify(ordernum);
						}
						
						return jsonObject.toString();
					} else {
						System.err.println("传递订单信息失败!");
					}
				}
			} else {
				logger.error(rolename + "：非财务人员，没有提交订单信息的权限");
			}
		}

		jsonObject.put("result", -1);
		return jsonObject.toString();
	}

	@Override
	public String changeRetretOrderVerify(String ordernum, Long loginUserId) {
		JSONObject jsonObject = new JSONObject();
		
		String sql = null;
		String rolename = null;
		if (loginUserId != null && !loginUserId.equals("")) {
			sql = "select r.* from users u left join userrole ur on u.id = ur.userid left join role r on ur.roleid = r.id where u.id ="
					+ loginUserId;
			List<Role> rolelist = roleMapper.selectBySql(sql);
			Role role = rolelist.get(0);
			rolename = role.getName();
		}

		if (ordernum != null && ordernum != "") {
			if (rolename != null && rolename.equals("财务人员")) {
				orderMapper.updateVervify(ordernum);
				
				jsonObject.put("result", 0);
				return jsonObject.toString();
			} else {
				logger.error(rolename + "：非财务人员，没有访问权限");
			}
		}

		jsonObject.put("result", -1);
		return jsonObject.toString();
	}
	
	@Override
	// public long save(Order order,String projectid,String companyid){
	public long save(Order order, UserProfile optUser) {
		String projectid = order.getProjectid();
		String companyid = order.getCompanyid();

		Long orderid = saveOrder(order, projectid, companyid);

		// Project project = queryProjectById(projectid);

		if (orderid > 0) {
			OrderProject projectorder = new OrderProject();
			projectorder.setProjectid(Integer.parseInt(projectid));
			projectorder.setOrderid(orderid);
			projectorder.setModifytime(new Date());

			orderprojectMapper.insert(projectorder);

			orderServiceLog.add(order, optUser);

			// 先跳过审核，不分角色，订单保存成功后与授权服务握手传递订单信息
			// String response =
			// licenceInfoConfig(company.getCompanyname(),project,order);
			// if(response != null){
			// JSONObject jsStr = JSONObject.fromObject(response);
			// int result = jsStr.getInt("result");
			// if(result ==0){
			// System.out.println("已成功传递订单信息给授权服务!");
			// }else{
			// System.err.println("传递订单信息失败!");
			// }
			// }

			// 根据setting文件的flag来配置是否需要审核
//			Properties pro = new Properties();
//			InputStream in = getClass().getResourceAsStream("/setting.properties");
//			try {
//				pro.load(in);
//			} catch (IOException e) {
//				System.err.println("读取配置握手文件失败");
//			}
//			String flag = pro.getProperty("flag");
//			if (flag != null && flag.equals("true")) {
//				String res = changeVerify(companyid, projectid, order.getOrdernum(), optUser.getCurrentUserId());
//				System.out.println(res);
//			}

			return orderid;
		} else {
			return orderid;
		}

	}
	
	private Long saveOrder(Order order, String projectid, String companyid) {
		order.setStatus(1);
		order.setVerify(0);
		long orderid = orderMapper.insert(order);
		// long projectcount =
		// projectcompanyMapper.selectCountBycompanyId(Long.parseLong(companyid));selectrownumBySql
		// String rownumSql="select row_number() over (order by projectid)rownum
		// from (select * from projectcompany "
		// + "where companyid = '" + companyid+ "'" +")pj where pj.projectid ='"
		// + projectid+ "'";
		String rownumSql = "select count(*) from projectcompany where companyid = '" + companyid + "'"
				+ " and projectid<='" + projectid + "'";
		long projectcount = projectcompanyMapper.selectrownumBySql(rownumSql);
		long ordercount = orderprojectMapper.selectCountByprojectId(Long.parseLong(projectid));

		orderid = order.getId();
		if (orderid > 0) {

			String ordernum = "";

			/**
			 * 生成订单号存入,规则以公司id、项目id、订单条数的顺序各取五位数拼成最终订单号。 id不足5位数的，在前面补0。
			 * 订单号按公司流水 每新建1个公司，当前公司下的项目号从1开始往后流水。 每新建1个项目，当前项目下的订单号从1开始往后流水。
			 */
			String str1 = String.format("%05d", Integer.parseInt(companyid));
			// String str2 = String.format("%05d", Integer.parseInt(projectid));
			String str2 = String.format("%05d", projectcount);
			// String str3 = String.format("%05d", orderid);
			String str3 = String.format("%05d", ordercount + 1);

			ordernum = str1 + str2 + str3;

			order.setOrdernum(ordernum);

			orderMapper.update(order);
			// orderMapper.update(ordernum, orderid);

			return orderid;
		}
		return -1l;

	}

	@Override
	// public long edit(Integer id, Order order) {
	public long edit(Order order, UserProfile optUser, String renew, String subordercontact) {
		// Order order1=orderMapper.selectById(id);
		Order order1 = orderMapper.selectById(order.getId());

		// int vervify = order1.getVerify();
		// //订单已审核，不能修改
		// if(vervify > 0){
		// return -1;
		// }

		order1.setSoftwareerpnumber(order.getSoftwareerpnumber());
		order1.setSoftwaretypeversion(order.getSoftwaretypeversion());
		
//		// 续订数量后，总数量=原数量+续订数量
//		if (renew != null && "xuding".equals(renew)) {
//			String OldorderNum = order1.getDemandquantity();
//			String NeworderNum = order.getDemandquantity();
//			Long Demandquantity = Long.parseLong(OldorderNum) + Long.parseLong(NeworderNum);
//			order1.setDemandquantity(String.valueOf(Demandquantity));
//		} else {
//			order1.setDemandquantity(order.getDemandquantity());
//		}
		
		order1.setDemandquantity(order.getDemandquantity());
		order1.setLicensetype(order.getLicensetype());
		order1.setReminderdays(order.getReminderdays());
		order1.setBindingmode(order.getBindingmode());
		order1.setMonth(order.getMonth());
		order1.setStarttime(order.getStarttime());
		order1.setEndtime(order.getEndtime());
		order1.setTacitstarttime(order.getTacitstarttime());
		order1.setRemarks(order.getRemarks());
		order1.setModitytime(new Date());

		orderMapper.update1(order1);
		
//		// 续订订单改变审核状态
//		if (renew != null && "xuding".equals(renew)) {
//			orderMapper.updateVervify1(order1.getOrdernum());
//
//			// 存入子订单信息
//			SubOrder suborder = new SubOrder();
//			// input框为disabled的值传不到后台，如何解决？
//			suborder.setContactname(subordercontact);
//			suborder.setCreatetime(new Date());
//			suborder.setRenewnum(order.getDemandquantity());
//			suborder.setRemark(order.getRemarks());
//			long suborderid = suborderMapper.insert(suborder);
//
//			suborderid = suborder.getId();
//			if (suborderid > 0) {
//				OrderSuborder ordersuborder = new OrderSuborder();
//				ordersuborder.setOrderid(order.getId());
//				ordersuborder.setSuborderid(suborderid);
//				ordersuborder.setModifytime(new Date());
//
//				long ordersuborderid = ordersuborderMapper.insert(ordersuborder);
//				ordersuborderid = ordersuborder.getId();
//				if (ordersuborderid > 0) {
//					String ordernum = order1.getOrdernum();
//					long ordertcount = ordersuborderMapper.selectCountByorderId(order.getId());
//					String str = String.format("%03d", ordertcount);
//					String subordernum = ordernum + str;
//					suborder.setSubordernum(subordernum);
//
//					suborderMapper.updateSubordernum(suborder);
//				}
//			}
//		}

		long id = order1.getId();
		if (id > 0) {
			orderServiceLog.edit(order1, optUser);
		}
		// return order1.getId();
		return id;
	}

	//保存子订单
	@Override
	public long saveSuborder(Order order, UserProfile user, String renew, String subordercontact) {
		Order order1 = orderMapper.selectById(order.getId());
		// 存入子订单信息
		SubOrder suborder = new SubOrder();
		// 续订订单保存
		if (renew != null && "xuding".equals(renew)) {
			// input框为disabled的值传不到后台，如何解决？
			suborder.setContactname(subordercontact);
			suborder.setCreatetime(new Date());
			//子订单状态0表示续订
			suborder.setStatus(0);
			suborder.setRenewnum(order.getDemandquantity());
			suborder.setRemark(order.getRemarks());
			long suborderid = suborderMapper.insert(suborder);

			suborderid = suborder.getId();
			if (suborderid > 0) {
				OrderSuborder ordersuborder = new OrderSuborder();
				ordersuborder.setOrderid(order.getId());
				ordersuborder.setSuborderid(suborderid);
				ordersuborder.setModifytime(new Date());

				long ordersuborderid = ordersuborderMapper.insert(ordersuborder);
				ordersuborderid = ordersuborder.getId();
				
				if (ordersuborderid > 0) {
					orderMapper.updateVervify1(order1.getOrdernum());
					
					String ordernum = order1.getOrdernum();
					long ordertcount = ordersuborderMapper.selectCountByorderId(order.getId());
					String str = String.format("%03d", ordertcount);
					String subordernum = ordernum + str;
					suborder.setSubordernum(subordernum);

					suborderMapper.updateSubordernum(suborder);
				}
				return suborderid;
			}else{
				//续订子订单保存出错
				return -2;
			}
		}else if(renew != null && "tuiding".equals(renew)){//退订订单保存
			//1.先update总订单信息，减去退订数量,并上报授权服务
			long RetretorderInfo = retretOrder(order,order1,user);
			
			if(RetretorderInfo == 0){
				suborder.setContactname(subordercontact);
				suborder.setCreatetime(new Date());
				//子订单状态1表示退订
				suborder.setStatus(1);
				suborder.setRenewnum(order.getDemandquantity());
				suborder.setRemark(order.getRemarks());
				long suborderid = suborderMapper.insert(suborder);
				suborderid = suborder.getId();
				
				if (suborderid > 0) {
					OrderSuborder ordersuborder = new OrderSuborder();
					ordersuborder.setOrderid(order.getId());
					ordersuborder.setSuborderid(suborderid);
					ordersuborder.setModifytime(new Date());

					long ordersuborderid = ordersuborderMapper.insert(ordersuborder);
					ordersuborderid = ordersuborder.getId();
					
					if (ordersuborderid > 0) {
//						orderMapper.updateVervify2(order1.getOrdernum());
						String ordernum = order1.getOrdernum();
						long ordertcount = ordersuborderMapper.selectCountByorderId(order.getId());
						String str = String.format("%03d", ordertcount);
						String subordernum = ordernum + str;
						suborder.setSubordernum(subordernum);

						suborderMapper.updateSubordernum(suborder);
					}
					return suborderid;
				}else{
//					退订子订单保存出错
					return -3;
				}
			}
			
		}
		
		//子订单保存出错
		return -1;
	}
	
	/**
	 * 退订时更改订单需求数量，重新上报授权服务
	 * @param order退订订单信息
	 * @param order1总订单信息
	 */
	public long retretOrder(Order order,Order order1,UserProfile user){
		Long loginUserId = user.getCurrentUserId();
		String sql = null;
		String rolename = null;
		if (loginUserId != null && !loginUserId.equals("")) {
			sql = "select r.* from users u left join userrole ur on u.id = ur.userid left join role r on ur.roleid = r.id where u.id ="
					+ loginUserId;
			List<Role> rolelist = roleMapper.selectBySql(sql);
			Role role = rolelist.get(0);
			rolename = role.getName();
		}
		
		if (rolename != null && rolename.equals("销售助理")) {
			//查询已下发授权数量
			String term_num = orderService.querytermNum(order.getId());
			//老订单需求数量
			String OldorderNum = order1.getDemandquantity();
			//剩余授权数量
			Long restCount = Long.parseLong(OldorderNum) - Long.parseLong(term_num);
			//退订订单数量
			String NeworderNum = order.getDemandquantity();
			//退订数量要小于等于剩余授权数量才可退订
			if(restCount >= Long.parseLong(NeworderNum)){
				// 退订后，总数量=原数量-退订数量
				Long Demandquantity = Long.parseLong(OldorderNum) - Long.parseLong(NeworderNum);
				order1.setDemandquantity(String.valueOf(Demandquantity));
				order1.setRemarks(order.getRemarks());
				order1.setModitytime(new Date());
				//1.上报新的订单信息到授权服务
				long retretOrderlicenceInfo = retretOrderlicenceInfoConfig(order1);
				//2.update退订后的订单数量
				if(retretOrderlicenceInfo == 0){
					orderMapper.updateRetretOrder(order1);
					return 0;
				}else{
					return -4;
				}
			}else{
				return -5;
			}
			
		} else {
			logger.error(rolename + "：非销售助理，没有退订订单的权限");
			return -6;
		}
	}
	
	//退订订单先把数量减下来上报授权服务，然后继续余下审核流程。
	public long retretOrderlicenceInfoConfig(Order order1){
		String projectsql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = "+ order1.getId();
		List<Project> projects = projectMapper.selectBysql(projectsql);
		Project project = projects.get(0);
		String companysql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = "+ project.getId();
		List<Company> companies = companyMapper.selectBySql(companysql);
		String companyname = companies.get(0).getCompanyregname();
		
		if (companyname != null && companyname != "" && project != null && order1 != null) {
			String response = licenceInfoConfig(companyname, project, order1);
			if (response != null) {
				JSONObject jsStr = JSONObject.fromObject(response);
				long result = jsStr.getLong("result");
				if (result == 0) {
					orderMapper.updateVervify2(order1.getOrdernum());
					return 0;
				} else {
					System.err.println("传递订单信息失败!");
					return -7;
				}
			}
		}
		
		return -8;
	}
	
	@Override
	public long editSuborder(Order order, UserProfile user) {
		String subordersql="select a.* from \"order\" b left join order_suborder c on b.id=c.orderid left join suborder a on "
				+ "c.suborderid=a.id where b.id="+ order.getId() +" order by a.createtime desc";
		List<SubOrder> suborders =suborderMapper.selectBysql(subordersql);
		SubOrder suborder = null;
		if(suborders.get(0)!=null){
			suborder = suborders.get(0);
		}
		suborder.setRenewnum(order.getDemandquantity());
		suborder.setRemark(order.getRemarks());
		suborder.setModifytime(new Date());
		suborderMapper.updateSuborderRenewnum(suborder);
	
		long id = suborder.getId();
		return id;
	}
	
	// @Override
	// public void disableOrder(String disableOrderIds) {
	// String orderId_array[] = disableOrderIds.split(",");
	// for(String orderId : orderId_array){
	// Order order = orderMapper.selectById(Long.parseLong(orderId));
	// order.setStatus(0);
	// orderMapper.update2(order);
	// }
	// }
//	@Override
//	public int disableOrder(String disableOrderIds, UserProfile optUser) {
//		String orderId_array[] = disableOrderIds.split(",");
//		int del_succee_count = 0;
//		String del_succee_orderId = "";
//		for (String orderId : orderId_array) {
//			// 判断是否有终端与订单关联
//			String termNum = querytermNum(Long.parseLong(orderId));
//			int num = Integer.parseInt(termNum);
//			if (num > 0) {
//				continue;
//			} else {
//				Long orderid = Long.parseLong(orderId);
//				Order order = orderMapper.selectById(orderid);
//				//0表示强制停止状态
//				order.setStatus(0);
//				orderMapper.update2(order);
//
//				if (del_succee_orderId.equals("")) {
//					del_succee_orderId += orderid;
//				} else {
//					del_succee_orderId += ",";
//					del_succee_orderId += orderid;
//				}
//
//				del_succee_count++;
//			}
//		}
//
//		if (del_succee_count == 0) {
//			return -1;// 全部关闭失败
//		} else if (del_succee_count == orderId_array.length) {
//			orderServiceLog.disable(del_succee_orderId, optUser);
//			return 1;// 全部关闭成功
//		} else {
//			orderServiceLog.disable(del_succee_orderId, optUser);
//			return -2;// 部分关闭 还有部分因终端关联不能关闭
//		}
//
//	}

	/**
	 * 强制停止订单
	 */
	@Override
	public long disableOrder(Integer disableOrderIds, UserProfile optUser) {
		Long loginUserId = optUser.getCurrentUserId();
		String sql = null;
		String rolename = null;
		if (loginUserId != null && !loginUserId.equals("")) {
			sql = "select r.* from users u left join userrole ur on u.id = ur.userid left join role r on ur.roleid = r.id where u.id ="
					+ loginUserId;
			List<Role> rolelist = roleMapper.selectBySql(sql);
			Role role = rolelist.get(0);
			rolename = role.getName();
		}
		
		if (rolename != null && rolename.equals("销售助理")) {
			long disableOrderlicenceInfo = disableOrderlicenceInfoConfig(disableOrderIds);
			
			return disableOrderlicenceInfo;
		} else {
			logger.error(rolename + "：非销售助理，没有退订订单的权限");
			return -3;
		}

	}
	
	//强制停止-》先关闭授权（把上报授权服务的订单数量改为0），然后再进行审核流程。
	public long disableOrderlicenceInfoConfig(Integer disableOrderIds){
		Order order = orderMapper.selectById(disableOrderIds);
		order.setDemandquantity("0");
		
		String projectsql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = "+ disableOrderIds;
		List<Project> projects = projectMapper.selectBysql(projectsql);
		Project project = projects.get(0);
		String companysql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = "+ project.getId();
		List<Company> companies = companyMapper.selectBySql(companysql);
		String companyname = companies.get(0).getCompanyregname();
			
		String response = licenceInfoConfig(companyname, project, order);
		if (response != null) {
			JSONObject jsStr = JSONObject.fromObject(response);
			long result = jsStr.getLong("result");
			if (result == 0) {
				order.setStatus(0);//0代表强制停止状态
				orderMapper.update2(order);
				return 0;
			} else {
				System.err.println("传递订单信息失败!");
				return -1;
			}
		}
			
		return -2;
	}
	
	/**
	 * 开启订单
	 */
	@Override
	public long openOrder(Integer disableOrderIds, UserProfile optUser) {
		
		try {
			orderMapper.updateVervify3(disableOrderIds);
		} catch (Exception e) {
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * 调用外部接口LicenceNumQuery查询已授权licence的总数
	 */
	@Override
	public String LicenceNumQuery(String command, int version, String company_name, String project, String orderid)
			throws Exception {
		if ((command == null || command.equals("")) || (version == 0)) {
			System.err.println("接口名不能为空!");
			return "-1";
		} else if (company_name == null || company_name.equals("")) {
			System.err.println("公司名不能为空!");
			return "-2";
		} else if (project == null || project.equals("")) {
			System.err.println("项目名不能为空!");
			return "-3";
		} else if (orderid == null || orderid.equals("")) {
			System.err.println("订单号不能为空!");
			return "-4";
		} else {

			Properties pro = new Properties();
			InputStream in = getClass().getResourceAsStream("/jdbc.properties");
			try {
				pro.load(in);
			} catch (IOException e) {
				System.err.println("读取配置文件失败");
			}
			String ip = pro.getProperty("serverip").trim();
			// System.out.println("ip地址为："+ip);
			String targetURL = "http://" + ip + ":8910/";

			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpURLConnection = (HttpURLConnection) targetUrl.openConnection();
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", command);
			jsonObject.put("version", version);
			jsonObject.put("company_name", company_name);
			jsonObject.put("project", project);
			jsonObject.put("order_id", orderid);
			String input = jsonObject.toString();
			logger.info("查询订单已发数量：" + input);

			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();
			if (httpURLConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + httpURLConnection.getResponseCode());
			}
			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpURLConnection.getInputStream())));

			String output = null;
			while ((output = responseBuffer.readLine()) != null) {
				httpURLConnection.disconnect();
				return output;
			}
			httpURLConnection.disconnect();
			return output;
		}
	}

	/**
	 * 查询已授权的订单数量，若数量不为0，则与终端关联，订单不可关闭
	 */
	@Override
	public String querytermNum(Long id) {
		String command = "LicenceNumQuery";
		int version = 256;

		String sql = "select * from company a LEFT JOIN projectcompany b on a.id = b.companyid LEFT JOIN project c on b.projectid = c.id "
				+ "LEFT JOIN orderproject d on c.id = d.projectid LEFT JOIN \"order\" e on d.orderid = e.id where e.id = "
				+ id;
		List<Company> temcompany = companyMapper.selectBySql(sql);
//		String company_name = temcompany.get(0).getCompanyname();
		String company_name = temcompany.get(0).getCompanyregname();

		String sql1 = "select * from project a left join orderproject b on a.id = b.projectid left join \"order\" c on b.orderid = c.id "
				+ "where c.id = " + id;
		List<Project> projects = projectMapper.selectBysql(sql1);
//		String project_name = projects.get(0).getProjectname();
		String project_name = projects.get(0).getProjectregname();

		// String sql2 = "select ordernum from \"order\" where id = " + id;
//		String orderid = orderMapper.selectById1(id);
		Order order = orderMapper.selectById(id);
		String ordernum = order.getOrdernum();
		String demandquantity = order.getDemandquantity();
//		System.out.println("ordernum="+ordernum+"==="+"demandquantity="+demandquantity);
		String output = null;
		try {
//			output = LicenceNumQuery(command, version, company_name, project_name, orderid);
			output = LicenceNumQuery(command, version, company_name, project_name, ordernum);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject jsStr = JSONObject.fromObject(output);
		String term_num = jsStr.getString("term_num");
		
		//判断订单数量是否下发完毕，完毕则订单状态变更为结束
		if(term_num.equals(demandquantity)){
			order.setStatus(2);
			orderMapper.update2(order);
		}
		
		return term_num;
	}

	@Override
	public String queryrenewCount(Long orderid) {
		// String sql="select count(*) from \"order\" a left join order_suborder
		// b on a.id= b.orderid where a.id="+orderid;
		int count = orderMapper.selectRenewCount(orderid);
		return String.valueOf(count+1);
	}

}
