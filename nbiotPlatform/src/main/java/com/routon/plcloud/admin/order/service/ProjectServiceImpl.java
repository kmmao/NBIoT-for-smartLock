package com.routon.plcloud.admin.order.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.plcloud.admin.order.utils.userloginUtils;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.privilege.service.log.ProjectServiceLog;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.OrderProject;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.ProjectCompany;
import com.routon.plcloud.common.model.RoleMenu;
import com.routon.plcloud.common.model.UserProject;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.OrderProjectMapper;
import com.routon.plcloud.common.persistence.ProjectCompanyMapper;
import com.routon.plcloud.common.persistence.ProjectMapper;
import com.routon.plcloud.common.persistence.RoleMenuMapper;
import com.routon.plcloud.common.persistence.UserProjectMapper;

import net.sf.json.JSONObject;


@Service
public class ProjectServiceImpl implements ProjectService{

	private Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	private  static String rootPath = TerminalService.class.getResource("/").getFile().toString().replaceAll("%20", " ").replaceFirst("/", "");
	
	//	private static final String targetURL = "http://172.16.42.38:8910/"; 
	
	// mac_key用于计算mac
	private static final String mac_key = "fcd3631882054598bf0fe9bcfb124eb7";	
	
	@Autowired
	private ProjectMapper ProjectMapper;
	
	@Autowired
	private ProjectServiceLog projectServiceLog;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private UserProjectMapper userProjectMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Autowired
	private OrderProjectMapper orderProjectMapper;
	
	@Autowired
	private ProjectCompanyMapper projectCompanyMapper;
	
	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;	

	@Override
	public Long move(Company group, UserProfile optUser) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public List<TreeBean> getCompanyTreeByUserId(Long opuserId, Company queryCondition, Long userId) {
		// TODO Auto-generated method stub
		//没有划分权限，等用户管理权限划分完成后，完善权限的添加（通过连表查询projectcompany以及userproject与opuserId匹配）
		
		String sql = null;
		//如果当前用户有新增项目的权限，则能看到全部公司，否则只能看到与自己相关联的公司
		String sql_tem = "select DISTINCT a.* from rolemenu a left join userrole b on a.roleid = b.roleid where  a.menuid = '90000201' and b.userid = " + opuserId;
		List<RoleMenu> roleMenus = roleMenuMapper.selectBySql(sql_tem);
		if(roleMenus != null && roleMenus.size() > 0){
			 sql = "select DISTINCT a.* from company a where 1=1";
		}
		else{
			sql = "select DISTINCT a.* from company a LEFT JOIN projectcompany b on a.id = b.companyid left join"
					+ " userproject c on b.projectid = c.projectid where 1=1 and c.userid = " + opuserId ;
		}
		
		//查询公司名称
		if(queryCondition != null && StringUtils.isNotBlank(queryCondition.getCompanyname()))
		{
			sql += " and a.companyname like '%" + queryCondition.getCompanyname() + "%'";
		}
		List<Company> companys = companyMapper.selectBySql(sql);
		HashMap<Long, TreeBean> companyHashMaps = new HashMap<Long, TreeBean>();
	//树状显示	
		TreeBean treeBean1 = new TreeBean();
		Long a = -1L;
		treeBean1.setId(a);
		treeBean1.setName("全部公司");	
		treeBean1.setParent(true);
		treeBean1.setOpen(true);
		
		companyHashMaps.put(a, treeBean1);
		
		for(Company company : companys){
			TreeBean treeBean = new TreeBean();
			treeBean.setId(company.getId());
			treeBean.setName(company.getCompanyname());
			treeBean.setPid(a);
			companyHashMaps.put(company.getId(), treeBean);
		}

	//分级显示		
		Iterator<Long> iterator = companyHashMaps.keySet().iterator();
		Set<Long> removeIds = new HashSet<Long>();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			TreeBean treeBean = companyHashMaps.get(id);
			
			
			TreeBean parentTreeBean = new TreeBean();
			if(treeBean.getId() == -1L){
			//	 parentTreeBean = companyHashMaps.get(treeBean.getId());
				continue;
			}
			else {				
				if(parentTreeBean !=null){
					parentTreeBean = companyHashMaps.get(-1L);
					Collection<TreeBean> parentschild = parentTreeBean
							.getChildren();
					if (parentschild == null) {
						parentschild = new ArrayList<TreeBean>();
					}
					parentschild.add(treeBean);
					parentTreeBean.setChildren(parentschild);
					removeIds.add(id);
				}	
			}
		}	

		for (Long id : removeIds) {
			companyHashMaps.remove(id);
		}
		return new ArrayList<TreeBean>(companyHashMaps.values());
	}

//	@Override
//	public List<TreeBean> getCompanyTreeByUserId(Long opuserId, Company queryCondition, Long userId,
//			boolean onlyleafcheck) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<TreeBean> getCompanyTreeByUserId(Long opuserId, Company queryCondition, Long userId,
			boolean onlyleafcheck, boolean showRelevanceCount) {
		// TODO Auto-generated method stub
		return null;
	}



	@Transactional(readOnly = true)
	public PagingBean<Project> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection,
			Project queryCondition1, String in_projectIds, String notin_projectIds, Long id, Long loginUserId,
			boolean exportflag) {
		// TODO Auto-generated method stub
		String pagingQueryLanguage = null;
		if(id != null){
			 pagingQueryLanguage = "select * from (select DISTINCT a.*,c.address from project a LEFT JOIN projectcompany b on a.id=b.projectid LEFT JOIN"
					+ " company c on b.companyid=c.ID left join userproject d on a.id = d.projectid where d.userid = " + loginUserId +"and c.id ="+ id +" order by a.modifytime DESC) p";
		
		}
		else{
			 pagingQueryLanguage = "select * from (select DISTINCT a.*,c.address from project a LEFT JOIN projectcompany b on a.id=b.projectid LEFT JOIN"
					+ " company c on b.companyid=c.ID left join userproject d on a.id = d.projectid where d.userid = " + loginUserId +" order by a.modifytime DESC) p";

		}
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
	
		
		if (StringUtils.isNotBlank(queryCondition1.getProjectname())){
			
				String projectname = queryCondition1.getProjectname().trim();		
				sbHQL.append(" where p.projectname like '%");
				sbHQL.append(projectname); 
				sbHQL.append("%'");
						 
			}

		if (in_projectIds != null) {

			if (StringUtils.isNotBlank(in_projectIds)) {
				  sbHQL.append(" and p.id in ("); 
				  sbHQL.append(in_projectIds);
				  sbHQL.append(")");
			} else {
				sbHQL.append(" and p.id in (");
				sbHQL.append("-1");
				sbHQL.append(")");
			}
		}

		if (StringUtils.isNotBlank(notin_projectIds)) {
			
			 sbHQL.append(" and p.id not in (");
			 sbHQL.append(notin_projectIds); 
			 sbHQL.append(")");
			
		}
		
//		if(id != null )
//		{
//			sbHQL.append("and c.id = ");
//			sbHQL.append(id);
//		}
//		if(loginUserId != null )
//		{
//			sbHQL.append("and d.userid = ");
//			sbHQL.append(loginUserId);
//		}

		String[] sortCriterions = null;
		if(sortCriterion != null){
//			if(sortCriterion.equals("address"))
//			{
//				sortCriterions = new String[] { "c." + sortCriterion };
//			}
//			else{
				sortCriterions = new String[] { "p." + sortCriterion };
//			}	
		}
		PagingSortDirection[] sortDirections =null;
		if(sortDirection != null){
			sortDirections = new PagingSortDirection[] { "desc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}

		PagingBean<Project> pagingSystemproject = pagingDao.query(ProjectMapper, sbHQL.toString(),
				sortCriterions, sortDirections, startIndex, pageSize, exportflag);
		
		return pagingSystemproject;
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



	@Transactional(rollbackFor = Exception.class)
	public Long add(Project project, int companyid, UserProfile optUser) throws Exception {
		// TODO Auto-generated method stub
		Long projectid = saveproject(project , companyid, optUser);
		//获取张工授权服务的用户名和密码
		String str = getProperties();
		String[] use_pass = str.split(":");
		String username = use_pass[0];
		String password = use_pass[use_pass.length - 1];
		
		if(projectid > 0 && companyid != 0){
			//插入公司项目表信息
			ProjectCompany projectCompany = new ProjectCompany();
			projectCompany.setCompanyid(companyid);
			projectCompany.setProjectid(projectid);
			projectCompany.setModifytime(new Date());
			
			projectCompanyMapper.insert(projectCompany);
			
			//插入项目用户表信息
			UserProject userProject = new UserProject();
			userProject.setProjectId(projectid);
			userProject.setUserId(optUser.getCurrentUserId());
			//项目绑定创建用户
			userProjectMapper.insert(userProject);
			
			
/*			String result = ProjectVerify(project, companyid, username, password);
			System.out.println(result);*/
			return projectid;
		}
		else{
			return projectid;
		}
	}



	/**
	 * @param project
	 * @param companyid
	 * @param username
	 * @param password
	 * @throws Exception
	 * @throws IOException
	 */
	public String ProjectVerify(Project project, int companyid, String username, String password)
			throws Exception, IOException {
		//调用4.13配置人脸SDK接口
//			String username = "plsy";
//			String password = "1234";
		byte[] PassWord = new byte[16];
		 System.arraycopy(password.getBytes(), 0, PassWord, 0, password.getBytes().length);
		//调用4.10 用户登录接口
		byte[] user_key = userloginUtils.getUserkey(username, password);
		String sdk_name = project.getSdkname();
		String sdk_key = project.getLicensekey();
//			byte[] Sdk_Key = new byte[8];
		byte[] Sdk_Key = userloginUtils.str2byteArr(sdk_key);
//			System.arraycopy(Sdk_Key, 0, Sdk_Key, 0, Sdk_Key.length);
//			System.arraycopy(sdk_key.getBytes(), 0, Sdk_Key, 0, sdk_key.getBytes().length);
		byte[] passworddata = null; 
		byte[] sdkkeydata = null;
		String passwordmi = null;
		String sdkkeymi = null;
		
		
		
//			byte[] newKey = userloginUtils.get24Byte(user_key);
////			String newkeyStr = userloginUtils.byteArr2str(newKey);
////			System.out.println(newKey.length+":"+newkeyStr);
//			
//			byte[] mi = new byte[16];
//	        System.arraycopy(password.getBytes(), 0, mi, 0, password.getBytes().length);
//			
////	        System.out.println("加密前密码："+userloginUtils.byteArr2str(mi));
//			byte[] mimaByte = userloginUtils.des3EncodeECB(newKey, mi);
//			
////			byte[] jie = userloginUtils.des3DecodeECB(newKey, mimaByte);
////			System.out.println("解密后密码："+userloginUtils.byteArr2str(jie));
//			
//			mimaStr = userloginUtils.byteArr2str(mimaByte);
		
		

		// 增长mackey
		byte[] keyadd = userloginUtils.get24Byte(user_key);
		
//			for(int i = 0 ; i < PassWord.length ; i +=8){
			passworddata = userloginUtils.des3EncodeECB(keyadd, PassWord);	    
//			}
		if(passworddata != null){
			 passwordmi = userloginUtils.byteArr2str(passworddata);
		}
		
		
//			for(int i = 0 ; i < Sdk_Key.length ; i +=8){
			sdkkeydata = userloginUtils.des3EncodeECB(keyadd, Sdk_Key);	    
//			}	
		if(sdkkeydata != null){
			 sdkkeymi = userloginUtils.byteArr2str(sdkkeydata);
		}
		
		//获取时间戳
		byte[] time = userloginUtils.GetLocalTime();
		//组装macdata
		byte[] macdata = new byte[128];
		System.arraycopy(username.getBytes(), 0, macdata, 0, username.getBytes().length);
		System.arraycopy(password.getBytes(), 0, macdata, 32, password.getBytes().length);
		System.arraycopy(sdk_name.getBytes(), 0, macdata, 64, sdk_name.getBytes().length);
		System.arraycopy(Sdk_Key, 0, macdata, 96, Sdk_Key.length);
		System.arraycopy(time, 0, macdata, 112, time.length);
		System.out.println("***macdata***" + userloginUtils.byteArr2str(macdata));
		// 计算mac
		byte[] mac = userloginUtils.CalcMac(macdata, macdata.length, time);
		// 增长mackey
		byte[] key = userloginUtils.get24Byte(userloginUtils.str2byteArr(mac_key));
		// 加密mac
		String hexmacMi = null;
		try {
			hexmacMi = userloginUtils.byteArr2str(userloginUtils.des3EncodeECB(key, mac));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 封装访问UserLogin接口所需的参数
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "SdkInfoConfig");
		jsonObject.put("version", 256);
		jsonObject.put("user_name", username);
//			jsonObject.put("user_signal", signReverse);
		jsonObject.put("user_password", passwordmi);
		jsonObject.put("sdk_name", sdk_name);
		jsonObject.put("sdk_key", sdkkeymi);
		jsonObject.put("mac", hexmacMi);
		String input = jsonObject.toString();
		// 获取返回值
		String output = userloginUtils.initPrivateInterface(input);
//			System.out.println(username);
//			System.out.println(passwordmi);
//			System.out.println(sdk_name);
//			System.out.println(sdkkeymi);
//			System.out.println(hexmacMi);
		System.out.println(output);
//			JSONObject jsStr = JSONObject.fromObject(output);
//		    String result = jsStr.getString("result");
//		    if(result.equals("0")){
//		    	
//		    }
		
		//调用配置认证方式接口
		 String command = "AuthTypeConfig";
		 int version = 256;    
		 List<Company> companies = companyMapper.selectById(companyid);
		 String company_name = companies.get(0).getCompanyregname();
		 String projecttmp = project.getProjectregname();
		 int auth_client_code = 0;
		 int auth_term_code = 0;
		 String authentication = project.getAuthentication();
		 if(authentication.equals("客户代码")){	 
			 auth_client_code = 1;	 
		 }else if(authentication.equals("终端代码")){
			 auth_term_code = 1;
		 }
		 else{
			 auth_client_code = 1;	
			 auth_term_code = 1;
		 }
		 String AuthTypeConfigoutput = AuthTypeConfig(command, version, company_name, projecttmp, auth_client_code, auth_term_code);
		//判断接口调用结果
		   JSONObject jsStr = JSONObject.fromObject(AuthTypeConfigoutput);
		   String result=jsStr.getString("result");
		   if(result.equals("0")){
			   	 project.setStatus(2);
			   	 ProjectMapper.update(project);
			   	 System.out.println(AuthTypeConfigoutput);
				 logger.info("配置认证方式 AuthTypeConfig："+AuthTypeConfigoutput);
				 return AuthTypeConfigoutput;
		   }
		 
		 
//		 
//		 System.out.println(AuthTypeConfigoutput);
//		 logger.info("配置认证方式 AuthTypeConfig："+AuthTypeConfigoutput);
		 return AuthTypeConfigoutput;
	}

	@Override
	public Long edit(Project project, int companyid, UserProfile optUser) {
		// TODO Auto-generated method stub
		Long projectid = updateproject(project ,companyid, optUser);
		if(projectid > 0)
		{
			projectCompanyMapper.deleteByProjectId(projectid);
			if(companyid != 0 )
			{
				ProjectCompany projectCompany = new ProjectCompany();
				projectCompany.setCompanyid(companyid);
				projectCompany.setProjectid(projectid);
				projectCompany.setModifytime(new Date());
				
				projectCompanyMapper.insert(projectCompany);
			}
			return projectid;
		}
		else{
			return projectid;
		}
	}

	private Long updateproject(Project project ,int companyid, UserProfile optUser) {
		// TODO Auto-generated method stub
		Project tem_project = ProjectMapper.selectById(project.getId());
		tem_project.setProjectname(project.getProjectname().trim());
		tem_project.setProjectadd(project.getProjectadd().trim());
		tem_project.setCusprojectname(project.getCusprojectname().trim());
		tem_project.setCusprojectphone(project.getCusprojectphone().trim());
		tem_project.setDemandquantity(project.getDemandquantity().trim());
		tem_project.setRequirementtype(project.getRequirementtype());
		tem_project.setIndustry(project.getIndustry());
		tem_project.setAuthentication(project.getAuthentication());
		tem_project.setSdkname(project.getSdkname().trim());
		tem_project.setSoftwareerpnumber(project.getSoftwareerpnumber().trim());
		tem_project.setSoftwaretypeversion(project.getSoftwaretypeversion().trim());
		tem_project.setLicensekey(project.getLicensekey().trim());
//		tem_project.setMonth(project.getMonth().trim());
//		tem_project.setStarttime(project.getStarttime());
//		tem_project.setTacitstarttime(project.getTacitstarttime());
//		tem_project.setEndtime(project.getEndtime());
		tem_project.setModifytime(new Date());
		boolean isExist = projectnameExist(project.getProjectname() , companyid, project.getId());
		if(isExist){
			return -2l;
		}
		else{
			ProjectMapper.update(tem_project);
			if(tem_project.getId()>0){
				projectServiceLog.edit(tem_project, optUser);
				return tem_project.getId();
			}
			return tem_project.getId();
		}
	}



	private Long saveproject(Project project ,int companyid, UserProfile optUser) throws Exception  {
//		String projectname = project.getProjectname();
		
		//循环加入项目测试数据
//		String projecttmp = project.getProjectregname();
//		for(int i=0; i<1000;i++){
//			project.setProjectregname(projecttmp+i);

		
		String projectregname = project.getProjectregname();
		boolean isExist = projectnameExist(projectregname.trim() ,companyid, project.getId());
		if(isExist)
		{
	//		Logger.info("新增项目时，项目名称已经存在");
			return -2l;
		}
		
		boolean RegisExist = projectregnameExist(projectregname, companyid , project.getId());
		if (RegisExist) {
//			logger.info("新增角色时,角色名已经存在");
			return -3l;
		}
		
		project.setProjectname(projectregname.trim());
		project.setProjectregname(projectregname.trim());
		project.setProjectregname(project.getProjectregname().trim());
		project.setProjectadd(project.getProjectadd());
//		project.setAddress(project.getAddress().trim());
		project.setCusprojectname(project.getCusprojectname().trim());
		project.setCusprojectphone(project.getCusprojectphone().trim());
		project.setDemandquantity(project.getDemandquantity().trim());
		project.setSoftwareerpnumber(project.getSoftwareerpnumber().trim());
		project.setSoftwaretypeversion(project.getSoftwaretypeversion().trim());
		project.setSdkname(project.getSdkname().trim());
		project.setLicensekey(project.getLicensekey().trim());
		project.setCreatetime(new Date());
		project.setModifytime(new Date());
		project.setStatus(CVal.ProjectStatus.valid);
		
		try {
			long projectId = ProjectMapper.insert(project);
			projectId = project.getId();
			if(projectId > 0)
			{
				projectServiceLog.add(project, optUser);
//				//插入公司项目表信息
//				ProjectCompany projectCompany = new ProjectCompany();
//				projectCompany.setCompanyid(companyid);
//				projectCompany.setProjectid(projectId);
//				projectCompany.setModifytime(new Date());
//				
//				projectCompanyMapper.insert(projectCompany);
//				
//				//插入项目用户表信息
//				UserProject userProject = new UserProject();
//				userProject.setProjectId(projectId);
//				userProject.setUserId(optUser.getCurrentUserId());
//				
//				userProjectMapper.insert(userProject);
				return projectId;
			}
			return -1l;
		} catch (Exception e) {
			
			return -1l;
		}
		
//	  }
//		//加入测试数据
//		return -1l;
	}

	private boolean projectregnameExist(String projectregname ,int companyid , Long projectid) {
		// TODO Auto-generated method stub
		if(companyid == 0){
			String companysql = "select a.* from company a left join projectcompany b on a.id = b.companyid left join project c on b.projectid = c.id where c.projectregname ='" + projectregname + "'";
			List<Company> companies = companyMapper.selectBySql(companysql);
			companyid =Integer.parseInt(String.valueOf(companies.get(0).getId()));
		}
		String sql = "select a.* from project a left join projectcompany b on a.id = b.projectid where a.projectname = '" + projectregname + "' and b.companyid = " + companyid;                            
		if(projectid != null){
			sql += " and a.id <>" + projectid;
		}
		List<Project> projects = ProjectMapper.selectBysql(sql);
		if (projects != null && projects.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean projectnameExist(String projectname ,int companyid , Long projectid) {
		// TODO Auto-generated method stub
		if(companyid == 0){
			String companysql = "select a.* from company a left join projectcompany b on a.id = b.companyid left join project c on b.projectid = c.id where c.projectname ='" + projectname + "'";
			List<Company> companies = companyMapper.selectBySql(companysql);
			companyid =Integer.parseInt(String.valueOf(companies.get(0).getId()));
		}
		String sql = "select a.* from project a left join projectcompany b on a.id = b.projectid where a.projectname = '" + projectname + "' and b.companyid = " + companyid;                            
		if(projectid != null){
			sql += " and a.id <>" + projectid;
		}
		List<Project> projects = ProjectMapper.selectBysql(sql);
		if (projects != null && projects.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean isProjectOrder(Long projectId) {

		String sql = "select a.* from orderproject a where a.projectid = " + projectId;
		List<OrderProject> orderProjects = orderProjectMapper.selectBySql(sql);

		if (orderProjects != null && orderProjects.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int disableProject(String disableProjectIds , UserProfile optUser) {
		// TODO Auto-generated method stub
		String projectId_array[] = disableProjectIds.split(",");
		int disable_succee_count = 0;
		String disable_succee_projectIds = "";
		for(String projectId : projectId_array)
		{
			if(isProjectOrder(Long.parseLong(projectId))){
				continue;
			}
			else{
				  Long projectid = Long.parseLong(projectId);
				Project project = ProjectMapper.selectById(Long.parseLong(projectId));
				int status = project.getStatus();
				if(status == 1)
				{
					status = 0;
					project.setStatus(status);
				}
				ProjectMapper.update(project);
				if(disable_succee_projectIds.equals("")){
					disable_succee_projectIds += projectid;
				}else {
					disable_succee_projectIds += ",";
					disable_succee_projectIds += projectid;
				}
				
				disable_succee_count++;
				
			}
			
		}
		
		if(disable_succee_count == 0){
			return -1; //全部删除失败
		}else if(disable_succee_count == projectId_array.length){
			projectServiceLog.disable(disable_succee_projectIds, optUser);
			return 1;//全部删除成功
		}else{
			projectServiceLog.disable(disable_succee_projectIds, optUser);
			return -2;//部分删除成功
		}
		
		
	}



	@Override
	public String LicenceNumQuery(String command, int version, String company_name, String project ,String orderid) throws IOException {
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
			Properties prop = new Properties();
			FileInputStream inStream = new FileInputStream(rootPath + "jdbc.properties");
			prop.load(inStream);
			String ip = prop.getProperty("serverip").trim();
			String targetURL = "http://" + ip + ":8910/" ;
			
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
	public String AuthTypeConfig(String command, int version, String company_name, String project, int auth_client_code,
			int auth_term_code) throws IOException {
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
			Properties prop = new Properties();
			FileInputStream inStream = new FileInputStream(rootPath + "jdbc.properties");
			prop.load(inStream);
			String ip = prop.getProperty("serverip").trim();
			String targetURL = "http://" + ip + ":8910/" ;
			
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
		    jsonObject.put("auth_client_code", auth_client_code);
		    jsonObject.put("auth_term_code", auth_term_code);
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












//
//		
//
//		if (in_companyIds != null) {
//
//			if (StringUtils.isNotBlank(in_companyIds)) {
//				  sbHQL.append(" and a.id in ("); 
//				  sbHQL.append(in_companyIds);
//				  sbHQL.append(")");
//			} else {
//				sbHQL.append(" and a.id in (");
//				sbHQL.append("-1");
//				sbHQL.append(")");
//			}
//		}
//
//		if (StringUtils.isNotBlank(notin_companyIds)) {
//			
//			 sbHQL.append(" and a.id not in (");
//			 sbHQL.append(notin_companyIds); 
//			 sbHQL.append(")");
//			
//		}
//
//		String[] sortCriterions = null;
//		if(sortCriterion != null){
//			sortCriterions = new String[] { "a." + sortCriterion };
//		}
//		PagingSortDirection[] sortDirections =null;
//		if(sortDirection != null){
//			sortDirections = new PagingSortDirection[] { "desc"
//					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
//					: PagingSortDirection.ASC };
//		}
//		PagingBean<Company> pagingSystemcompany = pagingDao.query(CompanyMapper,sbHQL.toString(), 
//				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);
//
//		return pagingSystemcompany;
//		
//		
//	}

}
