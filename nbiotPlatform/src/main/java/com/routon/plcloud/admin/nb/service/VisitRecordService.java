package com.routon.plcloud.admin.nb.service;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.Visitrecord;

public interface VisitRecordService {

	public PagingBean<Visitrecord> paging(int startIndex, int pageSize,
			String sortCriterion, String sortDirection, 
			String in_visitrecordIds, String notin_visitrecordIds, Long loginUserId, boolean exportflag);
	
	public long insertVistorRecord(Visitrecord visitrecord);
}
