package com.routon.plcloud.admin.order.action;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.bcel.generic.AALOAD;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.routon.plcloud.admin.activiti.common.ActivitiInfoQuery;
import com.routon.plcloud.admin.order.service.ProjectService;
import com.routon.plcloud.admin.order.service.TerminalService;
import com.routon.plcloud.admin.privilege.model.TreeBean;
import com.routon.plcloud.admin.privilege.service.log.TerminalServiceLog;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.constant.CVal;
import com.routon.plcloud.common.model.ClientInfo;
import com.routon.plcloud.common.model.ClientList;
import com.routon.plcloud.common.model.Company;
import com.routon.plcloud.common.model.CompanyAms;
import com.routon.plcloud.common.model.OfflineAuth;
import com.routon.plcloud.common.model.Order;
import com.routon.plcloud.common.model.Project;
import com.routon.plcloud.common.model.TermAms;
import com.routon.plcloud.common.model.TerminalAms;
import com.routon.plcloud.common.model.User;
import com.routon.plcloud.common.persistence.ClientInfoMapper;
import com.routon.plcloud.common.persistence.CompanyMapper;
import com.routon.plcloud.common.persistence.OfflineauthMapper;
import com.routon.plcloud.common.persistence.OrderMapper;
import com.routon.plcloud.common.persistence.ProjectMapper;
import com.routon.plcloud.common.utils.JsonMsgBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;


/**
 * 
 * @author wangzhuo
 *
 */
@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class TerminalController {
	
	private Logger logger = LoggerFactory.getLogger(TerminalController.class);

	private final String RMPATH = "/terminal/";
	
//	@Resource(name = "TerminalServiceImpl")
//	private TerminalService terminalService;
	
	@Resource(name = "terminalServiceImpl")
	private TerminalService terminalService;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private TerminalServiceLog terminalServiceLog;
	
	@Autowired
	private OfflineauthMapper offlineauthMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private TaskService taskService;
	
	@Resource(name = "ActivitiInfoQueryBean")
	protected ActivitiInfoQuery activitiInfoQuery;
	
	@RequestMapping(value = RMPATH + "list")
	public String list(HttpServletRequest request, TerminalAms queryCondition,Long companyid,Long projectid,Long orderid,
			Long requirementtype,String searchname, 
			@ModelAttribute("userProfile")
			UserProfile user, Model model, HttpSession session ,String treeNodeTid){
		
			Long loginUserId = user.getCurrentUserId();	
		
			String username = user.getCurrentUserRealName();
		
			//List<Task> taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(username).list();
			
			int page = NumberUtils.toInt(request.getParameter("page"), 1);
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),
					10);
			int startIndex = (page - 1) * pageSize;
			
//			System.out.println(companyid);
//			System.out.println(projectid);
//			System.out.println(orderid);
			//获得终端树
//			List<TreeBean> companyTreeBeans = projectService.getCompanyTreeByUserId(loginUserId, queryCondition, null);	
			List<TreeBean> terminalTreeBeans = terminalService.getTerminalTreeByUserId(loginUserId , queryCondition,requirementtype,searchname);
			
			//终端信息显示
//			PagingBean<TerminalAms> pagingBean = terminalService.paging(startIndex,
//					pageSize, request.getParameter("sort"),
//					request.getParameter("dir"), queryCondition, null, null,
//					loginUserId,request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);
			
			PagingBean<ClientList> pagingBean = terminalService.paging(companyid , projectid , orderid , page , loginUserId);
		
			pagingBean.setStart(startIndex);
			pagingBean.setLimit(pageSize);
			 pagingBean.setStart(startIndex);
			int maxpage = (int) Math.ceil(pagingBean.getTotalCount()
					/ (double) pageSize);
			if (pagingBean.getTotalCount() == 0) {
				maxpage = 0;
			}
			
			//返回ztree点击节点的id（公司、项目、订单）
			if(companyid !=null){
				model.addAttribute("tmpcompanyid" , companyid);
			}
			if(projectid !=null){
				model.addAttribute("tmpprojectid" , projectid);
			}
			if(orderid !=null){
				model.addAttribute("tmporderid" , orderid);
			}
			
			//activitiInfoQuery.QueryActivitiInfo(model, user.getCurrentUserId().toString(), page, startIndex, pageSize);
			model.addAttribute("maxpage", maxpage);
			model.addAttribute("page", page);
			model.addAttribute("pageList", pagingBean);
			model.addAttribute("terminalTreeBeans", JSONArray.fromObject(terminalTreeBeans).toString());
			//model.addAttribute("List", taskQuery);
			model.addAttribute("treeNodeTid", treeNodeTid);
			return "terminal/list";
		
	}
	
	@RequestMapping(value = RMPATH + "orderask")
	public String OrderAsk(Integer id , Model model){
		 JsonMsgBean jsonMsg = null;
	     Order order = orderMapper.selectById(id);
	     if(order.getLicensetype().equals("在线")){
	    	 jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选订单为在线订单，无法离线申请license");
	     }
	     else{
	    	 jsonMsg = new JsonMsgBean(0, CVal.Success, "");		
	     }
	     model.addAttribute("jsonMsg", jsonMsg);
		 return "common/jsonTextHtml";	
	}
	
	
	@RequestMapping(value = RMPATH + "response")
	public String response(HttpServletRequest request,String termsn, String clientcode,Integer projectid , @ModelAttribute("userProfile") UserProfile optUser, Model model) throws IOException{
		JsonMsgBean jsonMsg = null;
		//终端身份认证
		String command = "AuthInfoUpload";
        int version = 256;
        //获取项目名称
//        String Prosql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = " + orderid;
//        List<Project> projects = projectMapper.selectBysql(Prosql);
        Project projects = projectMapper.selectById(projectid);
        String projectname = projects.getProjectregname();
        //获取公司名称
        String Comsql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = " + projects.getId();
        List<Company> companies = companyMapper.selectBySql(Comsql);
        String companyname = companies.get(0).getCompanyregname();
        //termcode
        String term_code = "";
        String AuthInfoUploadoutput = terminalService.AuthInfoUpload(command, version, companyname, projectname, clientcode, term_code);
        System.out.println(AuthInfoUploadoutput);
        logger.info("身份认证 AuthInfoUpload:" + AuthInfoUploadoutput);
        //解析json数据，获取auth_code
        JSONObject jsStr2 = JSONObject.fromObject(AuthInfoUploadoutput);
        String result = jsStr2.getString("result");
        if(!result.equals("0")){
        	jsonMsg = new JsonMsgBean(0, CVal.Fail, "身份认证失败");
        }
        else{
        	
       
	    String auth_code = jsStr2.getString("auth_code");
	
	//上报授权申请接口
	    String command1 = "LicenceRequestUpload";
	    CompanyAms companyAms = new CompanyAms();
        companyAms.setName(companyname);
        companyAms.setContact(jsStr2.getString("contact"));
        companyAms.setTelno(jsStr2.getString("telno"));
        companyAms.setAddr(jsStr2.getString("address"));
        companyAms.setDesc(jsStr2.getString("district"));  
//        Order order = orderMapper.selectById(orderid);
//        String orderidReal = order.getOrdernum();
        int request_type = 1;
        int term_num = 1;    
        ArrayList<TermAms> termAmss = new ArrayList<TermAms>();     
        TermAms termAms = new TermAms();
        termAms.setTerm_id(1);
        termAms.setTerm_type(0);
        termAms.setTerm_code("");
        termAms.setTerm_sn(termsn);
        termAmss.add(termAms);
        String LicenceRequestUploadoutput = terminalService.LicenceRequestUpload(command1, version, companyAms, projectname,  request_type, clientcode, auth_code, term_num, termAmss);
        System.out.println(LicenceRequestUploadoutput);
        logger.info("上报授权申请 LicenceRequestUpload:" + LicenceRequestUploadoutput);
        JSONObject jsStr3 = JSONObject.fromObject(LicenceRequestUploadoutput);
        
     //查询授权信息接口测试
        String command2 = "LicenceResponseQuery";
        String request_id = jsStr3.getString("request_id");
        String termlicence = null;
        for(int i = 0 ; i < 6 ; i++){
        	try {
				Thread.currentThread();
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	String LicenceResponseQueryoutput = terminalService.LicenceResponseQuery(command2, version, Integer.parseInt(request_id));
        	System.out.println(LicenceResponseQueryoutput);
        	logger.info("查询授权信息 LicenceResponseQuery:" + LicenceResponseQueryoutput);
        	JSONObject jsStr4 = JSONObject.fromObject(LicenceResponseQueryoutput);    
        	if(jsStr4.getString("result").equals("0")){
        		termlicence = jsStr4.getJSONArray("term_list").getJSONObject(0).getString("term_licence");
        		break;
        	} 
        }
        
        if(termlicence == null){
        	jsonMsg = new JsonMsgBean(0, CVal.Fail, "离线授权失败");
        }
        else{
//        	 //存入数据库
//            OfflineAuth offlineAuth = new OfflineAuth();
//            offlineAuth.setTermsn(termsn);
//            offlineAuth.setTermlicence(termlicence);
//                  
//            try {
//                Long id = null;
//    			id = terminalService.add(offlineAuth,request,optUser);
//    			if(id > 0){
//    				jsonMsg = new JsonMsgBean(0, CVal.Success, "离线授权成功!");	
//    			}
//    			else if(id == -2){
//    				jsonMsg = new JsonMsgBean(0, CVal.Fail, "授权文件已经存在!");			
//    			}
//    			else{
//    				jsonMsg = new JsonMsgBean(0, CVal.Fail, "离线授权失败!");
//    			}		
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    		}
        	
        	jsonMsg = new JsonMsgBean(0, CVal.Success, "离线授权成功");
        }
       
        }
	     model.addAttribute("jsonMsg", jsonMsg);
		 return "common/jsonTextHtml";	

	}
	
//	@RequestMapping("/terminal/downloadExcel")
	@RequestMapping(value = RMPATH + "downExcel")
	public void download(HttpServletRequest request,	
			HttpServletResponse response,String browerType, String fileName , @ModelAttribute("userProfile") UserProfile user) throws MalformedURLException {

		response.setContentType("text/html;charset=utf-8");
		try {
			// 解决中文文件名乱码问题
			String eFileName = URLEncoder.encode(fileName, "UTF-8");//IE浏览器
			//firefox、webkit、opera浏览器
			if (!browerType.equals("msie")) {
				eFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1"); 
			} 
			request.setCharacterEncoding("UTF-8");
			FileInputStream bis = null;
			ServletOutputStream bos = null;
			
			String ctxPath = request.getSession().getServletContext().getResource("/").getPath() + "templeExcel/"; 
			File dirPath = new File(ctxPath);
			if (!dirPath.exists()) {
				//如果不存在则创建文件路径
				dirPath.mkdir();
			}
			String downLoadPath = ctxPath + fileName;
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload");
			response.setHeader("content-Disposition", "attachment; filename="+ eFileName);
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new FileInputStream(downLoadPath);
			bos = response.getOutputStream();
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
				terminalServiceLog.downexcel(fileName, user);
			}
		} catch (Exception e) {
			String msg = "下载上传文件模板异常：" + e.getMessage();
			logger.error(msg,e);
			e.printStackTrace();
		}

	}
	
	
	
	@RequestMapping(value = RMPATH + "licencejudge")
	public String licencejudge(String termsn , Model model){
		 JsonMsgBean jsonMsg = null;
		 String sql = "select a.* from offlineauth a where a.termsn = '"+ termsn + "'";
//	     Order order = orderMapper.selectById(id);
		 List<OfflineAuth> offlineAuths = offlineauthMapper.selectBysql(sql);
		 if (offlineAuths != null && offlineAuths.size() > 0) {
			 jsonMsg = new JsonMsgBean(0, CVal.Success, "");
		 }
		 else{
			 jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选终端为在线终端，无法下载license");
		 }
	     model.addAttribute("jsonMsg", jsonMsg);
		 return "common/jsonTextHtml";	
	}
	
	@RequestMapping(value = RMPATH + "downloadlicence")
	public void downloadlicence(HttpServletRequest request,	
			HttpServletResponse response,String browerType, String termlicence , Model model , @ModelAttribute("userProfile") UserProfile user) throws MalformedURLException {
//		JsonMsgBean jsonMsg = null;
		response.setContentType("text/html;charset=utf-8");
		
		try {
			//生成licence文件，并存放在指定目录					
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes = null;
			try {
				bytes = decoder.decodeBuffer(termlicence);
			} catch (IOException e) {
			    
			}
//			String FileName = "licence.lic";
			
			String eFileName = URLEncoder.encode("licence.lic", "UTF-8");//IE浏览器
			//firefox、webkit、opera浏览器
			if (!browerType.equals("msie")) {
				eFileName = new String("licence.lic".getBytes("UTF-8"), "ISO8859-1"); 
			} 
			request.setCharacterEncoding("UTF-8");
			//response.setContentType("application/x-msdownload");
			response.setContentType("application/octet-stream; charset=UTF-8");
//			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("content-Disposition", "attachment; filename="+ eFileName);
			response.setHeader("Content-Length", String.valueOf(bytes.length));
			
			
			InputStream is = new ByteArrayInputStream(bytes);
			OutputStream out = response.getOutputStream();
			byte[] content = new byte[1024];
			int length = 0;
			while ((length = is.read(content)) != -1) {
			out.write(content, 0, length);
			}
			out.write(content);
			out.flush();
			out.close();
			
//			FileInputStream bis = null;
//			ServletOutputStream bos = null;
			
//				 bis  = new FileInputStream(FileName);
//				 bos = response.getOutputStream();
//					byte[] buff = new byte[2048];
//					int bytesRead;
					
//					bos.write(bytes);
//					bos.close();
//					while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//						try {
//							bos.write(buff, 0, bytesRead);
//							
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
//					}
//					if (bis != null) {
//						bis.close();
//					}
//					if (bos != null) {
//						bos.close();
//						jsonMsg = new JsonMsgBean(0, CVal.Success, "授权文件下载成功!");
//					terminalServiceLog.licencedownload(eFileName,termsn, user);
//					jsonMsg = new JsonMsgBean(0, CVal.Success, "");
//					}
//			FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
//			 fileOutputStream.write(bytes, 0, bytes.length);
//			 fileOutputStream.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		    
		System.out.println(termlicence);
//		model.addAttribute("jsonMsg", jsonMsg);
//		return "common/jsonTextHtml";	
	}
		
	
	@RequestMapping(value = RMPATH + "licencedownload")
	public void licencedownload(HttpServletRequest request,	
			HttpServletResponse response,String browerType, String termsn , Model model , @ModelAttribute("userProfile") UserProfile user) throws MalformedURLException {

		
//		JsonMsgBean jsonMsg = null;
		response.setContentType("text/html;charset=utf-8");
		try {
			// 解决中文文件名乱码问题
			String eFileName = URLEncoder.encode("licence.lic", "UTF-8");//IE浏览器
			//firefox、webkit、opera浏览器
			if (!browerType.equals("msie")) {
				eFileName = new String("licence.lic".getBytes("UTF-8"), "ISO8859-1"); 
			} 
			request.setCharacterEncoding("UTF-8");
			FileInputStream bis = null;
			ServletOutputStream bos = null;
			
			String ctxPath = request.getSession().getServletContext().getResource("/").getPath() + "licence/" + termsn + "/"; 
//			File dirPath = new File(ctxPath);
//			if (!dirPath.exists()) {
//				String header = "所选订单为在线订单，无法离线下载license!";
//				response.setContentType("application/x-msdownload");
//				response.setHeader("content-Disposition" , header);
	
				//如果不存在则创建文件路径
//				dirPath.mkdir();
//				jsonMsg = new JsonMsgBean(0, CVal.Fail, "所选订单为在线订单，无法离线下载license!");	
//			}
//			else{
				String downLoadPath = ctxPath + "licence.lic";
				long fileLength = new File(downLoadPath).length();
				response.setContentType("application/x-msdownload");
//				response.setContentType("text/html;charset=UTF-8");
				response.setHeader("content-Disposition", "attachment; filename="+ eFileName);
				response.setHeader("Content-Length", String.valueOf(fileLength));
				bis = new FileInputStream(downLoadPath);
				bos = response.getOutputStream();
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					try {
						bos.write(buff, 0, bytesRead);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
					terminalServiceLog.licencedownload(eFileName,termsn, user);
//					jsonMsg = new JsonMsgBean(0, CVal.Success, "");
				}
//			}
			
		} catch (Exception e) {
			String msg = "下载授权文件异常：" + e.getMessage();
//			jsonMsg = new JsonMsgBean(0, CVal.Fail, "下载授权文件异常");
			logger.error(msg,e);
			e.printStackTrace();
		}
				 
//		 model.addAttribute("jsonMsg", jsonMsg);
//		 return "common/jsonTextHtml";
  }
	
	@RequestMapping(value = RMPATH + "reportinfo")
	public String reportinfo(HttpServletRequest request, @ModelAttribute("userProfile")UserProfile user,ClientList clientList , Long orderid , Model model) throws IOException{
		JsonMsgBean jsonMsg = null;
		//获取订单号
		Order order = orderMapper.selectById(orderid);
		String orderNum = order.getOrdernum();
		//获取项目信息
		 //获取项目名称
        String Prosql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = " + orderid;
        List<Project> projects = projectMapper.selectBysql(Prosql);
        String projectname = projects.get(0).getProjectregname();
		
//		Project temproject = projectMapper.selectById(projectid);
//		String projectname = temproject.getProjectregname();
		String sql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = " + projects.get(0).getId();
		List<Company> temcompany = companyMapper.selectBySql(sql);
		String companyname = temcompany.get(0).getCompanyregname();
		ArrayList<ClientList> clientLists = new ArrayList<ClientList>(); 
		clientLists.add(clientList);
		String command = "ClientInfoUpload";
        int version = 256;
        
        String output = terminalService.ClientInfoUpload(command, version, companyname, projectname,orderNum, 1, clientLists);
        logger.info("上报客户信息  ClientInfoUpload:" + output );
        JSONObject jsStr = JSONObject.fromObject(output);
	    String result = jsStr.getString("result");
        if(result.equals("0")){
        	 jsonMsg = new JsonMsgBean(0, CVal.Success, "上报授权信息成功");
		 }
		 else{
			 jsonMsg = new JsonMsgBean(0, CVal.Fail, "上报授权信息失败");
		 }
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";	
	}
	

	
	
//@RequestMapping(value ="/terminal/import")
	@RequestMapping(value = RMPATH + "importExcel")
	public ModelAndView importExcel(HttpServletRequest request, @ModelAttribute("userProfile")
			UserProfile user, @RequestParam(value = "images", required = false) MultipartFile file , Long orderid) throws FileNotFoundException{
		String up_path = "toupload";
//	    String companyName = user.getCurrentUserCompany();
//	    String project = user.getCurrentUserProject();
		//获取订单号
		Order order = orderMapper.selectById(orderid);
		String orderNum = order.getOrdernum();
        //获取项目名称
        String Prosql = "select a.* from project a left join orderproject b on a.id = b.projectid where b.orderid = " + orderid;
        List<Project> projects = projectMapper.selectBysql(Prosql);
//        String projectname = projects.get(0).getProjectregname();
		Long projectid = projects.get(0).getId();
		
		
		Project temproject = projectMapper.selectById(projectid);
		String project = temproject.getProjectregname();
		String sql = "select a.* from company a left join projectcompany b on a.id = b.companyid where b.projectid = " + projectid;
		List<Company> temcompany = companyMapper.selectBySql(sql);
		String companyName = temcompany.get(0).getCompanyregname();
		
		//存储上传结果信息
		Map<String, String> map = new HashMap<String, String>();
		String realFileName = file.getOriginalFilename();
		//获取文件名后缀名
		String suffixfilename = realFileName.substring(realFileName.lastIndexOf('.') + 1, realFileName.length());
		Boolean iread = false;
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + up_path + "/";	
		logger.info("upload path:" + ctxPath + realFileName);
		//创建文件目录
		File dirPath = new File(ctxPath);  
		if (!dirPath.exists()) {
			//如果不存则创建文件路径
			dirPath.mkdir();
		}
		//上传文件到服务器
		File uploadFile = new File(ctxPath + realFileName);
		InputStream is = null;
		if (file != null && !file.isEmpty()) {
			try {
				is = file.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			DataOutputStream os = null;
			try {
				//创建一个输出流，将数据写入到指定的文件
				os = new DataOutputStream(new FileOutputStream(uploadFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			byte[] buffer = null;
			try {
				//创建一个字节流缓冲区，大小为预计的可读取字节流的大小
				buffer = new byte[is.available()];
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				//将数据写入到输入缓冲区
				is.read(buffer);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				//将缓冲区中的数据读取到输出流中
				os.write(buffer);
				map.put("result", "uploadsucess");
			} catch (IOException e1) {
				e1.printStackTrace();
				map.put("result", "uploadfail");
			}
			try {
				//将数据强制写入到输出流
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				map.put("result", "uploadfail");
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				map.put("result", "uploadfail");
			}
		}
		try {
			StringBuffer result = this.uploadValidat(ctxPath, realFileName, suffixfilename);
			if (result.toString() != null && !result.toString().isEmpty()) {
				map.put("geshi", result.toString());
			} else {
				iread = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(iread){
			if (suffixfilename.equals("xls")) {
				
				StringBuffer bbbbs = this.readXls(ctxPath, realFileName, companyName,  project ,orderNum,user);
			    map.put("readdata", bbbbs.toString());
		
			}
		}
//		return new ModelAndView("terminal/list", map);
		return new ModelAndView("clientInfo/clientlist", map);
	}
	
	
	public StringBuffer uploadValidat(String ctxPath, String fileName, String suffixfilename) throws FileNotFoundException{
		//用于传递错误信息
		StringBuffer abs = new StringBuffer();
		StringBuffer error = new StringBuffer();
		if (suffixfilename.equals("xls")) {
			try {
				InputStream is = new FileInputStream(new File(ctxPath + fileName));
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
				int isheet = 0;
				for(int numSheet = 0; numSheet < 1/*hssfWorkbook.getNumberOfSheets()*/; numSheet++){
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
					if (hssfSheet == null) {
						isheet++;
						continue;
					}
					HSSFRow hssfRow = hssfSheet.getRow(0);   //获取第一行的格式
					for(short cellNum = 0; cellNum < 10 /*hssfRow.getLastCellNum()*/; cellNum++){
						HSSFCell hssfCell = hssfRow.getCell(cellNum);
						if (hssfCell == null) {
							abs.append("第"+ (cellNum + 1)+"列列名为空");
							break;
						}
						if (cellNum == 0) {
							String orgCode = getValueofXls(hssfCell);
							if (!orgCode.equals("序号")) {
								abs.append("第1列列名不对");
								break;
							}
						}
						if (cellNum == 1) {
							String orgName = getValueofXls(hssfCell);
							if (!orgName.equals("省份")) {
								abs.append("第2列列名不对");
								break;
							}
						}
						if (cellNum == 2) {
							String contactPhone = getValueofXls(hssfCell);
							if (!contactPhone.equals("城市")) {
								abs.append("第3列列名不对");
								break;
							}
						}
						if (cellNum == 3) {
							String contactMobile = getValueofXls(hssfCell);
							if (!contactMobile.equals("区")) {
								abs.append("第4列列名不对");
								break;
							}
						}
						if (cellNum == 4) {
							String contactPerson = getValueofXls(hssfCell);
							if (!contactPerson.equals("业务代码")) {
								abs.append("第5列列名不对");
								break;
							}
						}
						if (cellNum == 5) {
							String contactPerson = getValueofXls(hssfCell);
							if (!contactPerson.equals("业务名称")) {
								abs.append("第6列列名不对");
								break;
							}
						}
						if (cellNum == 6) {
							String contactPerson = getValueofXls(hssfCell);
							if (!contactPerson.equals("联系人")) {
								abs.append("第7列列名不对");
								break;
							}
						}
						if (cellNum == 7) {
							String contactPerson = getValueofXls(hssfCell);
							if (!contactPerson.equals("联系电话")) {
								abs.append("第8列列名不对");
								break;
							}
						}
						if (cellNum == 8) {
							String contactPerson = getValueofXls(hssfCell);
							if (!contactPerson.equals("联系地址")) {
								abs.append("第9列列名不对");
								break;
							}
						}
						if (cellNum == 9) {
							String contactPerson = getValueofXls(hssfCell);
							if (!contactPerson.equals("备注")) {
								abs.append("第10列列名不对");
								break;
							}
						}
						if (cellNum >= 10) {// 由于hssfCell为null就会立马停止循环，只有不为空才能走下去
							abs.append("列数过多，检测到每行有{"+hssfRow.getLastCellNum()+"}列，格式不对，请删除多余无内容但是存在的单元格");
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				error.append("导入失败，服务器内部错误");
				return error;
			}
		}else{
			abs.append("导入文件格式不正确，请重新导入");
			
		}
		
		return abs;
	}
	
	//导入Excel
	
	
	public StringBuffer readXls(String ctxPath, String fileName, String companyName, String project , String orderNum ,UserProfile optUser) throws FileNotFoundException{
		//用于传递错误信息
		StringBuffer bs = new StringBuffer();
		StringBuffer error = new StringBuffer();
		try {
		InputStream is = new FileInputStream(ctxPath + fileName);
		/*Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream(rootPath + "setting.properties");
		
			prop.load(new InputStreamReader(inStream, "UTF-8"));
			String companyName = prop.getProperty("company_info");
			String project = prop.getProperty("project");*/
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			for (int numSheet =0; numSheet < 1/*hssfWorkbook.getNumberOfSheets()*/; numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				System.out.println("表单数"+hssfWorkbook.getNumberOfSheets());
				if (hssfSheet == null) {
					continue;
				}
				int totalImportRowNum = hssfSheet.getLastRowNum();
				int successImportRowNum = 0;
				int failImportRowNum = 0;
				int rownum = hssfSheet.getPhysicalNumberOfRows();
				
//				for(int circleNum = 0 ; circleNum < totalImportRowNum ; circleNum+=1000 ){	
				
				if(totalImportRowNum-1 < 1000){
				
				ArrayList<ClientList> clientLists = new ArrayList<ClientList>(); 
				// 循环行Row
				for (int rowNum = 1  ; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
//					ClientInfo clientInfo = new ClientInfo();
					ClientList clientList = new ClientList();
					Boolean iTrue = false;
//					Boolean update = false;
					String order = null;
					String province = null;
					String city = null;
					String district = null;
					String clientCode = null;
					String clientName = null;
					String contact = null;
					String telno = null;
					String address = null;
					String remark = null;
					Date updateTime = new Date();
					for (short cellNum = 0; cellNum < 10/*hssfRow.getLastCellNum()*/; cellNum++) {
						HSSFCell hssfCell = hssfRow.getCell(cellNum);
/*						if (hssfCell == null) {
							continue;
						}*/
						if(cellNum == 0){
							order = getValueofXls(hssfCell);
							if (order == null|| order.equals("")){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其序号为空。");
								failImportRowNum++;
								break;
							} 
						}
						if (cellNum == 1) { 
							province = getValueofXls(hssfCell);
/*							if(province.length() > 10 || isNumeric(province)){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其省份字符限制或格式不正确。");
								failImportRowNum++;
								break;
							}*/
						}
						if (cellNum == 2) { 
							city = getValueofXls(hssfCell);
						}
						if (cellNum == 3) { 
							district = getValueofXls(hssfCell);
						}
						if (cellNum == 4) { 
							clientCode = getValueofXls(hssfCell);
							if (clientCode == null|| clientCode.equals("")){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店代码为空。");
								failImportRowNum++;
								break;
							}
							if(clientCode.length() != 10 || !isNumeric(clientCode)){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店代码必须为十位数字。");
								failImportRowNum++;
								break;
							}
//							List<ClientInfo> list = clientInfoMapper.queryDataByClientCode(clientCode);
//							if(list.size() > 1){
//								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，现有记录中存在酒店代码为{"+ clientCode +"}的多条记录。");
//								failImportRowNum++;
//								break;
//							} else if(list.size() == 1){
//								clientInfo = list.get(0);
//								update = true;
//							}
							
						}
						if (cellNum == 5) { 
							clientName = getValueofXls(hssfCell);
							if(clientName.length() > 32){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店名称超出字符限制。");
								failImportRowNum++;
								break;
							}
						}
						if (cellNum == 6) { 
							contact = getValueofXls(hssfCell);
							if(contact.length() > 10){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其联系人超出字符限制。");
								failImportRowNum++;
								break;
							}
						}
						if (cellNum == 7) { 
							telno = getValueofXls(hssfCell);
/*							if(!isPhone(telno) && !isFixedLine(telno)){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，联系方式格式不正确。");
								failImportRowNum++;
								break;
							}*/
						}
						if (cellNum == 8) { 
							address = getValueofXls(hssfCell);
							if(address.length() > 32){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其联系地址超出字符限制。");
								failImportRowNum++;
								break;
							}
						}
						if (cellNum == 9) { 
							remark = getValueofXls(hssfCell);
							if(remark.length() > 30){
								bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其备注超出字符限制。");
								failImportRowNum++;
								break;
							}
							iTrue = true;
						}
						if(iTrue){
							clientList.setProvince(province);
							clientList.setCity(city);
							clientList.setDistrict(district);
							clientList.setClient_code(clientCode);
							clientList.setClient_name(clientName);
							clientList.setContact(contact);
							clientList.setTelno(telno);
							clientList.setAddress(address);
							clientList.setRemark(remark);
//							clientInfo.setCompanyName(companyName);
//							clientInfo.setProject(project);
//							clientInfo.setProvince(province);
//							clientInfo.setCity(city);
//							clientInfo.setDistrict(district);
//							clientInfo.setClientCode(clientCode);
//							clientInfo.setClientName(clientName);
//							clientInfo.setContact(contact);
//							clientInfo.setTelno(telno);
//							clientInfo.setAddress(address);
//							clientInfo.setRemark(remark);
//							clientInfo.setTime(updateTime);
//							if(update){
//								clientInfoMapper.updateClientInfo(clientInfo);
//							} else{
//								clientInfoMapper.insert(clientInfo);
//							}
							successImportRowNum++;
							clientLists.add(clientList);
						}
					}

				}
				String command = "ClientInfoUpload";
		        int version = 256;
		        
//		        String input = interfaceCall.ClientInfoUpload(command, version, company_name, project, client_num, terminalAmss);
//		        String output = projectService.LicenceNumQuery(command, version, company_name, project);
		        
		        ThreadLocal<Long> startTime = new ThreadLocal<Long>();
				ThreadLocal<Long> endTime = new ThreadLocal<Long>();
				startTime.set(System.currentTimeMillis());
		        String output = terminalService.ClientInfoUpload(command, version, companyName, project, orderNum , successImportRowNum, clientLists);
		       logger.info("上报客户信息 ClientInfoUpload: " + output);
		        endTime.set(System.currentTimeMillis());
		    	long time = endTime.get() - startTime.get();
		    	System.out.println("查询所有终端耗时:"+ time + "ms");
		        
		        
		        
		        System.out.println(output);
		        JSONObject jsStr = JSONObject.fromObject(output);
		        String result = jsStr.getString("result");
		        if(result.equals("0")){
		        	terminalServiceLog.importexcel(String.valueOf(totalImportRowNum), String.valueOf(successImportRowNum), String.valueOf(failImportRowNum), optUser);
		        }
				
				bs.append("第" + numSheet+1 + "个sheet共导入："+ totalImportRowNum +"条记录，导入成功：" +successImportRowNum+ "条记录，导入失败：" + failImportRowNum +"条记录。");
				}
				else{
					int i =0 ;
					for( i = 0 ; i < (totalImportRowNum-1)/1000 ; i++ ){
						ArrayList<ClientList> clientLists = new ArrayList<ClientList>(); 
						// 循环行Row
						for (int rowNum = 1 + i*1000  ; rowNum <= 1000; rowNum++) {
							HSSFRow hssfRow = hssfSheet.getRow(rowNum);
							if (hssfRow == null) {
								continue;
							}
//							ClientInfo clientInfo = new ClientInfo();
							ClientList clientList = new ClientList();
							Boolean iTrue = false;
//							Boolean update = false;
							String order = null;
							String province = null;
							String city = null;
							String district = null;
							String clientCode = null;
							String clientName = null;
							String contact = null;
							String telno = null;
							String address = null;
							String remark = null;
							Date updateTime = new Date();
							for (short cellNum = 0; cellNum < 10/*hssfRow.getLastCellNum()*/; cellNum++) {
								HSSFCell hssfCell = hssfRow.getCell(cellNum);
		/*						if (hssfCell == null) {
									continue;
								}*/
								if(cellNum == 0){
									order = getValueofXls(hssfCell);
									if (order == null|| order.equals("")){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其序号为空。");
										failImportRowNum++;
										break;
									} 
								}
								if (cellNum == 1) { 
									province = getValueofXls(hssfCell);
		/*							if(province.length() > 10 || isNumeric(province)){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其省份字符限制或格式不正确。");
										failImportRowNum++;
										break;
									}*/
								}
								if (cellNum == 2) { 
									city = getValueofXls(hssfCell);
								}
								if (cellNum == 3) { 
									district = getValueofXls(hssfCell);
								}
								if (cellNum == 4) { 
									clientCode = getValueofXls(hssfCell);
									if (clientCode == null|| clientCode.equals("")){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店代码为空。");
										failImportRowNum++;
										break;
									}
									if(clientCode.length() != 10 || !isNumeric(clientCode)){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店代码必须为十位数字。");
										failImportRowNum++;
										break;
									}
//									List<ClientInfo> list = clientInfoMapper.queryDataByClientCode(clientCode);
//									if(list.size() > 1){
//										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，现有记录中存在酒店代码为{"+ clientCode +"}的多条记录。");
//										failImportRowNum++;
//										break;
//									} else if(list.size() == 1){
//										clientInfo = list.get(0);
//										update = true;
//									}
									
								}
								if (cellNum == 5) { 
									clientName = getValueofXls(hssfCell);
									if(clientName.length() > 32){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店名称超出字符限制。");
										failImportRowNum++;
										break;
									}
								}
								if (cellNum == 6) { 
									contact = getValueofXls(hssfCell);
									if(contact.length() > 10){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其联系人超出字符限制。");
										failImportRowNum++;
										break;
									}
								}
								if (cellNum == 7) { 
									telno = getValueofXls(hssfCell);
		/*							if(!isPhone(telno) && !isFixedLine(telno)){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，联系方式格式不正确。");
										failImportRowNum++;
										break;
									}*/
								}
								if (cellNum == 8) { 
									address = getValueofXls(hssfCell);
									if(address.length() > 32){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其联系地址超出字符限制。");
										failImportRowNum++;
										break;
									}
								}
								if (cellNum == 9) { 
									remark = getValueofXls(hssfCell);
									if(remark.length() > 30){
										bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其备注超出字符限制。");
										failImportRowNum++;
										break;
									}
									iTrue = true;
								}
								if(iTrue){
									clientList.setProvince(province);
									clientList.setCity(city);
									clientList.setDistrict(district);
									clientList.setClient_code(clientCode);
									clientList.setClient_name(clientName);
									clientList.setContact(contact);
									clientList.setTelno(telno);
									clientList.setAddress(address);
									clientList.setRemark(remark);
//									clientInfo.setCompanyName(companyName);
//									clientInfo.setProject(project);
//									clientInfo.setProvince(province);
//									clientInfo.setCity(city);
//									clientInfo.setDistrict(district);
//									clientInfo.setClientCode(clientCode);
//									clientInfo.setClientName(clientName);
//									clientInfo.setContact(contact);
//									clientInfo.setTelno(telno);
//									clientInfo.setAddress(address);
//									clientInfo.setRemark(remark);
//									clientInfo.setTime(updateTime);
//									if(update){
//										clientInfoMapper.updateClientInfo(clientInfo);
//									} else{
//										clientInfoMapper.insert(clientInfo);
//									}
									successImportRowNum++;
									clientLists.add(clientList);
								}
							}

						}
						String command = "ClientInfoUpload";
				        int version = 256;
				        
//				        String input = interfaceCall.ClientInfoUpload(command, version, company_name, project, client_num, terminalAmss);
//				        String output = projectService.LicenceNumQuery(command, version, company_name, project);
				        
				        ThreadLocal<Long> startTime = new ThreadLocal<Long>();
						ThreadLocal<Long> endTime = new ThreadLocal<Long>();
						startTime.set(System.currentTimeMillis());
//				        String output = terminalService.ClientInfoUpload(command, version, companyName, project, rownum-1, clientLists);
				        String output = terminalService.ClientInfoUpload(command, version, companyName, project, orderNum, successImportRowNum, clientLists);
				        endTime.set(System.currentTimeMillis());
				    	long time = endTime.get() - startTime.get();
				    	System.out.println("查询所有终端耗时:"+ time + "ms");
				        
				        
				        
				        System.out.println(output);
				        logger.info("上报客户信息 ClientInfoUpload: " + output);
//						bs.append("第" + numSheet+1 + "个sheet共导入："+ totalImportRowNum +"条记录，导入成功：" +successImportRowNum+ "条记录，导入失败：" + failImportRowNum +"条记录。");
						
					}
					//1000的倍数循环完成
					ArrayList<ClientList> clientLists = new ArrayList<ClientList>(); 
					// 循环行Row
					for (int rowNum = 1 + i*1000; rowNum < totalImportRowNum; rowNum++) {
						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
						if (hssfRow == null) {
							continue;
						}
//						ClientInfo clientInfo = new ClientInfo();
						ClientList clientList = new ClientList();
						Boolean iTrue = false;
//						Boolean update = false;
						String order = null;
						String province = null;
						String city = null;
						String district = null;
						String clientCode = null;
						String clientName = null;
						String contact = null;
						String telno = null;
						String address = null;
						String remark = null;
						Date updateTime = new Date();
						for (short cellNum = 0; cellNum < 10/*hssfRow.getLastCellNum()*/; cellNum++) {
							HSSFCell hssfCell = hssfRow.getCell(cellNum);
	/*						if (hssfCell == null) {
								continue;
							}*/
							if(cellNum == 0){
								order = getValueofXls(hssfCell);
								if (order == null|| order.equals("")){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其序号为空。");
									failImportRowNum++;
									break;
								} 
							}
							if (cellNum == 1) { 
								province = getValueofXls(hssfCell);
	/*							if(province.length() > 10 || isNumeric(province)){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其省份字符限制或格式不正确。");
									failImportRowNum++;
									break;
								}*/
							}
							if (cellNum == 2) { 
								city = getValueofXls(hssfCell);
							}
							if (cellNum == 3) { 
								district = getValueofXls(hssfCell);
							}
							if (cellNum == 4) { 
								clientCode = getValueofXls(hssfCell);
								if (clientCode == null|| clientCode.equals("")){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店代码为空。");
									failImportRowNum++;
									break;
								}
								if(clientCode.length() != 10 || !isNumeric(clientCode)){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店代码必须为十位数字。");
									failImportRowNum++;
									break;
								}
//								List<ClientInfo> list = clientInfoMapper.queryDataByClientCode(clientCode);
//								if(list.size() > 1){
//									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，现有记录中存在酒店代码为{"+ clientCode +"}的多条记录。");
//									failImportRowNum++;
//									break;
//								} else if(list.size() == 1){
//									clientInfo = list.get(0);
//									update = true;
//								}
								
							}
							if (cellNum == 5) { 
								clientName = getValueofXls(hssfCell);
								if(clientName.length() > 32){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其酒店名称超出字符限制。");
									failImportRowNum++;
									break;
								}
							}
							if (cellNum == 6) { 
								contact = getValueofXls(hssfCell);
								if(contact.length() > 10){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其联系人超出字符限制。");
									failImportRowNum++;
									break;
								}
							}
							if (cellNum == 7) { 
								telno = getValueofXls(hssfCell);
	/*							if(!isPhone(telno) && !isFixedLine(telno)){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，联系方式格式不正确。");
									failImportRowNum++;
									break;
								}*/
							}
							if (cellNum == 8) { 
								address = getValueofXls(hssfCell);
								if(address.length() > 32){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其联系地址超出字符限制。");
									failImportRowNum++;
									break;
								}
							}
							if (cellNum == 9) { 
								remark = getValueofXls(hssfCell);
								if(remark.length() > 30){
									bs.append("第" + numSheet+1 + "个sheet第" + (rowNum + 1) + "行未导入，其备注超出字符限制。");
									failImportRowNum++;
									break;
								}
								iTrue = true;
							}
							if(iTrue){
								clientList.setProvince(province);
								clientList.setCity(city);
								clientList.setDistrict(district);
								clientList.setClient_code(clientCode);
								clientList.setClient_name(clientName);
								clientList.setContact(contact);
								clientList.setTelno(telno);
								clientList.setAddress(address);
								clientList.setRemark(remark);
//								clientInfo.setCompanyName(companyName);
//								clientInfo.setProject(project);
//								clientInfo.setProvince(province);
//								clientInfo.setCity(city);
//								clientInfo.setDistrict(district);
//								clientInfo.setClientCode(clientCode);
//								clientInfo.setClientName(clientName);
//								clientInfo.setContact(contact);
//								clientInfo.setTelno(telno);
//								clientInfo.setAddress(address);
//								clientInfo.setRemark(remark);
//								clientInfo.setTime(updateTime);
//								if(update){
//									clientInfoMapper.updateClientInfo(clientInfo);
//								} else{
//									clientInfoMapper.insert(clientInfo);
//								}
								successImportRowNum++;
								clientLists.add(clientList);
							}
						}

					}
					String command = "ClientInfoUpload";
			        int version = 256;
			        
//			        String input = interfaceCall.ClientInfoUpload(command, version, company_name, project, client_num, terminalAmss);
//			        String output = projectService.LicenceNumQuery(command, version, company_name, project);
			        
			        ThreadLocal<Long> startTime = new ThreadLocal<Long>();
					ThreadLocal<Long> endTime = new ThreadLocal<Long>();
					startTime.set(System.currentTimeMillis());
//			        String output = terminalService.ClientInfoUpload(command, version, companyName, project, rownum-1, clientLists);
			        String output = terminalService.ClientInfoUpload(command, version, companyName, project, orderNum, successImportRowNum-i*1000, clientLists);
			        endTime.set(System.currentTimeMillis());
			    	long time = endTime.get() - startTime.get();
			    	System.out.println("查询所有终端耗时:"+ time + "ms");
			        
			        
			        
			        System.out.println(output);
			        logger.info("上报客户信息 ClientInfoUpload: " + output);
			        bs.append("第" + numSheet+1 + "个sheet共导入："+ totalImportRowNum +"条记录，导入成功：" +successImportRowNum+ "条记录，导入失败：" + failImportRowNum +"条记录。");
					
					
				}
			}
//			}
			//inStream.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			error.append("导入失败，内部错误！！！");
			return error;
		}
		return bs;
	}
	
	
	/**
	 * @Description:读取后缀为xls的excel单元格的内容
	 * @param :hssfCell excel单元格
	 * @return String 返回单元格数据
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private String getValueofXls(HSSFCell hssfCell) {
		DecimalFormat df = new DecimalFormat("0");
		if(hssfCell == null){
			return "";
		}
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return df.format(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getRichStringCellValue());
		}
	}
	
	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	}
}
