package com.routon.plcloud.admin.oplog.service;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.OpLog;


public interface OpLogService {
	public PagingBean<OpLog> paging(int startIndex, int pageSize,
			String sortCriterion, String sortDirection,
			OpLog queryCondition, String in_Ids, String notin_Ids,
			Long loginUserId, boolean exportflag);
}
