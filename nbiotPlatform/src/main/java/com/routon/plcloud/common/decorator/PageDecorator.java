package com.routon.plcloud.common.decorator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.pagination.PaginatedList;

import com.huawei.utils.ConnectFtp;
import com.routon.plcloud.common.model.Register;
import com.routon.plcloud.common.model.User;

/**
 * Does nothing. Simply needed to define a concrete class to test getListIndex() and getViewIndex() from the abstract
 * TableDecorator class
 * @author fgiust
 * @version $Revision: 1081 $ ($Author: fgiust $)
 */
public class PageDecorator extends TableDecorator
{
  /**
   * FastDateFormat used to format the date object.
   */
  //private FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$

  // just subclass TableDecorator
  /**
   * Creates a new Wrapper decorator who's job is to reformat some of the data located in our TestObject's.
   */
  public PageDecorator()
  {
	  super();
  }

  /**
   * 获得导出的文件名，注意：
   * exportFileNamePrefix 给导出的文件名加一个前缀，如"TerminalList"表示终端列表
   * exportFileNamePrefix 只能是英文，不支持中文名导出
   * exportFileNamePrefix 由外部jsp页面传入，如：
   * pageContext.setAttribute("exportFileNamePrefix", "TerminalList");
   */
  public static String getExportFileName(HttpServletRequest request, String exportFileNamePrefix)
  {
	  //取得当前时间。注意，不能是HH:mm:ss，否则文件名非法！
	  String curTime = FastDateFormat.getInstance("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
	  //取得当前页号
	  PaginatedList pageList = (PaginatedList)(request.getAttribute("pageList"));
	  int pageNumber = pageList.getPageNumber();
	  //拼文件名
	  String exportFileName = exportFileNamePrefix+"_"+curTime+"_"+pageNumber+".xls";
	  
	  return exportFileName;
  }
  
  public String getImage_url() throws IOException{
	  Register t = (Register) getCurrentRowObject();
	  String id = t.getId();
	  String head = "data:image/jpg;base64,";
	  //String imgbase64 = ConnectFtp.readfileToBase64("D:\\ftp134\\facephoto\\"+ id + ".jpg", "172.16.42.134", 21, "134", "123456");
	  //String fi = head + imgbase64;
	  //return "<img src=\""+ url +"\""+ id +".jpg /";
	  return "<img src=\"../nbiot/downloadFile.do?id= " + id + "\" height=\"130\" width=\"89\" />";
  }
  
  public String getGender(){
	  Register t = (Register) getCurrentRowObject();
	  int gender = t.getGender();
	  String result;
	  if(gender == 1){
		  result = "男";
	  }else{
		  result = "女";
	  }
	  return result;
  }
  
  public String getRole(){
	  Register t = (Register) getCurrentRowObject();
	  String role = t.getRole();
	  String result = null;
	  if(role.equals("0")){
		  result = "注册用户";
	  }else if(role.equals("1")){
		  result = "白名单用户";
	  } else if(role.equals("2")){
		  result = "管理员";
	  } else if(role.equals("0,2")) {
		  result = "注册用户&管理员";
	  } else if(role.equals("1,2")) {
		  result = "白名单用户&管理员";
	  } 
	  return result;
  }
  
  public String getUpdate_time(){
	  Register t = (Register) getCurrentRowObject();
	  Date date = t.getUpdate_time();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String time = formatter.format(date); 
	  return time;
  }

}
