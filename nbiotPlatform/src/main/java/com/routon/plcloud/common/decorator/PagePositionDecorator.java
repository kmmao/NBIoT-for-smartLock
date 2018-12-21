package com.routon.plcloud.common.decorator;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.pagination.PaginatedList;

import com.routon.plcloud.common.model.Position;
import com.routon.plcloud.common.model.User;

/**
 * Does nothing. Simply needed to define a concrete class to test getListIndex() and getViewIndex() from the abstract
 * TableDecorator class
 * @author fgiust
 * @version $Revision: 1081 $ ($Author: fgiust $)
 */
public class PagePositionDecorator extends TableDecorator
{
  /**
   * FastDateFormat used to format the date object.
   */
  //private FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$

  // just subclass TableDecorator
  /**
   * Creates a new Wrapper decorator who's job is to reformat some of the data located in our TestObject's.
   */
  public PagePositionDecorator()
  {
	  super();
  }
  
	//获取用户管理模块的id
	public String getDeviceid(){
		Position t = (Position) getCurrentRowObject();
		String id = t.getDeviceid();
		return "<a onclick=\"editPosition(this)\" style=\"cursor:pointer;\"> " + id + " </a>";
	}

}
