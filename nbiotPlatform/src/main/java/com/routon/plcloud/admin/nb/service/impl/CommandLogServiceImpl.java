package com.routon.plcloud.admin.nb.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.admin.nb.service.CommandLogService;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.CommandLog;
import com.routon.plcloud.common.persistence.CommandLogMapper;


@Service("commandLogService")
public class CommandLogServiceImpl implements CommandLogService{
	
	@Autowired
	private CommandLogMapper commandLogMapper;
	
	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;	

	@Override
	public PagingBean<CommandLog> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection,
			String in_visitrecordIds, String notin_visitrecordIds, Long loginUserId, boolean exportflag) {
		
		String pagingQueryLanguage = " select DISTINCT a.*,b.name from commandlog a left join register b on a.face_id = b.face_id  where 1=1  ";

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
		PagingBean<CommandLog> pagingSystemcommandlog = pagingDao.query(commandLogMapper,sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);

		return pagingSystemcommandlog;
	}

	@Override
	public int insertCommandRecord(String deviceid, int commandtype, String commandid, int faceid, String commandStr, int report,int reportsend) {
		CommandLog command = new CommandLog();
		command.setDeviceid(deviceid);
		command.setCommandtype(commandtype);
		command.setCommandstate("SENT");
		command.setCommandtime(new Date());
		command.setCommandid(commandid);
		command.setCommandstr(commandStr);
		command.setFace_id(faceid);
		command.setReport_white(report);
		command.setReport_sendwhite(reportsend);
		int result = commandLogMapper.insertCommand(command);
		return result;
	}

	@Override
	public void updateMark(int id, int tag) {
		commandLogMapper.updateMark(id, tag);
	}

	@Override
	public int insertManagerCommand(String deviceid, int commandtype, Date commandtime, int face_id,
			int report_manager) {
		CommandLog command = new CommandLog();
		command.setDeviceid(deviceid);
		command.setCommandtype(commandtype);
		command.setCommandtime(commandtime);
		command.setFace_id(face_id);
		command.setReport_manager(report_manager);
		return commandLogMapper.insertManagerCommand(command);
	}
		
}
