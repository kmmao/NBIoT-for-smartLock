package com.routon.plcloud.admin.software.service;

import java.util.List;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.Software;

public interface SoftwareService {
	
	public PagingBean<Software> queryALL(int startIndex, int pageSize, String softwareName, String softwareVersion);
	
	public List<String> queryAdoptedProductName(String id);
	
	public List<String> queryAdoptedPlatform(String id);
	
	public List<String> queryAdoptedOS(String id);
	
	public long save(Software software, String hardwareProductSelect, String hardwareStationSelect, String OSSelect);
}
