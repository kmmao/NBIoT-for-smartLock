package com.routon.plcloud.admin.nb.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.routon.plcloud.admin.nb.service.VisitRecordService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.UserProfile;

import com.routon.plcloud.common.model.Visitrecord;



@Controller
@SessionAttributes(value = { "userPrivilege", "userProfile" })
public class VisitRecordController {
	
	private final String RMPATH = "/nbiot/";
	
	@Autowired
	private VisitRecordService visitRecordService;
	
	@RequestMapping(value = RMPATH + "visitrecordlist")
	public String list(HttpServletRequest request, @ModelAttribute("userProfile")
			UserProfile user, Model model) {

		try {
			Long loginUserId = user.getCurrentUserId();		
			
			int page = NumberUtils.toInt(request.getParameter("page"), 1);
			int pageSize = NumberUtils.toInt(request.getParameter("pageSize"),
					10);
			int startIndex = (page - 1) * pageSize;


		
			PagingBean<Visitrecord> pagingBean = visitRecordService.paging(startIndex,
					pageSize, request.getParameter("sort"),
					request.getParameter("dir"), null, null,
					loginUserId,request.getParameter("exportflag") != null&&request.getParameter("exportflag").equals("true")?true:false);

			int maxpage = (int) Math.ceil(pagingBean.getTotalCount()
					/ (double) pageSize);
			
			if (pagingBean.getTotalCount() == 0) {
				maxpage = 0;
			}
			

			model.addAttribute("maxpage", maxpage);
		//	model.addAttribute("List", taskQuery);
			model.addAttribute("page", page);
			model.addAttribute("pageList", pagingBean);

		}
		catch (Exception e) {

		}
		
		return "nbiot/visitrecordlist";
	}
	
	
}
