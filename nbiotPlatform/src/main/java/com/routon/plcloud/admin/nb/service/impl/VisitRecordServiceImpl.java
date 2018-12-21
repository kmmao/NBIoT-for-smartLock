package com.routon.plcloud.admin.nb.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.admin.nb.service.VisitRecordService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.Visitrecord;
import com.routon.plcloud.common.persistence.VisitRecordMapper;

@Service("visitRecordService")
public class VisitRecordServiceImpl implements VisitRecordService{
	
	@Autowired
	private VisitRecordMapper visitRecordMapper;
	
	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;	

	@Override
	public PagingBean<Visitrecord> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection,
			String in_visitrecordIds, String notin_visitrecordIds, Long loginUserId, boolean exportflag) {
		// TODO Auto-generated method stub
		String pagingQueryLanguage = " select DISTINCT a.* from visitrecord a  where 1=1  ";

		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
		
		if (in_visitrecordIds != null) {

			if (StringUtils.isNotBlank(in_visitrecordIds)) {
				  sbHQL.append(" and a.id in ("); 
				  sbHQL.append(in_visitrecordIds);
				  sbHQL.append(")");
			} else {
				sbHQL.append(" and a.id in (");
				sbHQL.append("-1");
				sbHQL.append(")");
			}
		}

		if (StringUtils.isNotBlank(notin_visitrecordIds)) {
			
			 sbHQL.append(" and a.id not in (");
			 sbHQL.append(notin_visitrecordIds); 
			 sbHQL.append(")");
			
		}

//		if (loginUserId != null) {
//			
//			if(loginUserId != 1){
//				sbHQL.append(" and (select count(*) from rolemenu b where b.roleId = a.id) ");
//				sbHQL.append("- (select count(*) from rolemenu b where b.roleId = a.id ");
//				sbHQL.append("    and b.menuId IN (select c.menuId FROM rolemenu c where c.roleId in (select d.roleId from userrole d WHERE d.userId = ");
//				sbHQL.append(loginUserId);
//				sbHQL.append("))");
//				sbHQL.append(") <= 0");
//			}
//
//		}
		
		String[] sortCriterions = null;
		if(sortCriterion != null){
			sortCriterions = new String[] { "a." + sortCriterion };
		}
		PagingSortDirection[] sortDirections =null;
		if(sortDirection != null){
			sortDirections = new PagingSortDirection[] { "desc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}
		PagingBean<Visitrecord> pagingSystemvisitrecord = pagingDao.query(visitRecordMapper,sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);

		return pagingSystemvisitrecord;
		
	}

	@Override
	public long insertVistorRecord(Visitrecord visitrecord) {
		return visitRecordMapper.insert(visitrecord);
	}
	
	

}
