package com.routon.plcloud.admin.software.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.model.Software;
import com.routon.plcloud.common.model.SoftwareHardware;
import com.routon.plcloud.common.model.SoftwareHardwarestation;
import com.routon.plcloud.common.model.SoftwareOS;
import com.routon.plcloud.common.persistence.HardwareProductMapper;
import com.routon.plcloud.common.persistence.SoftwareHardwareMapper;
import com.routon.plcloud.common.persistence.SoftwareHardwarestationMapper;
import com.routon.plcloud.common.persistence.SoftwareMapper;
import com.routon.plcloud.common.persistence.SoftwareOSMapper;

@Service
public class SoftwareImpl implements SoftwareService {

	@Autowired
	private SoftwareMapper softwareMapper;
	
	@Autowired
	private HardwareProductMapper hardwareProductMapper;
	
	@Autowired
	private SoftwareHardwareMapper softwareHardwareMapper;
	
	@Autowired
	private SoftwareHardwarestationMapper softwareHardwarestationMapper;
	
	@Autowired
	private SoftwareOSMapper softwareOSMapper;
	
	@Override
	public PagingBean<Software> queryALL(int startIndex, int pageSize, String softwareName, String softwareVersion) {
		
		PagingBean<Software> pageBean = new PagingBean<Software>();
		String sql = "select * from softwareproduct a WHERE 1 = 1 ";
		String countSql = "select count(*) from softwareproduct WHERE 1 = 1 ";
		
		StringBuilder sbHQL = new StringBuilder(sql);
		StringBuilder sbHQLcount = new StringBuilder(countSql);
		
		if(softwareName != null){
			sbHQL.append("and a.softwarename like '%" + softwareName + "%'");
			sbHQLcount.append("and a.softwarename like '%" + softwareName + "%'");
		}
		
		if(softwareVersion != null){
			sbHQL.append("and a.softwareversion like '%" + softwareVersion + "%'");
			sbHQLcount.append("and a.softwareversion like '%" + softwareVersion + "%'");
		}
		
		List<Software> list = softwareMapper.selectByCondition(sbHQL.toString(), startIndex, pageSize);
		int totalnumber = softwareMapper.selectCountByCondition(sbHQLcount.toString());
		
		pageBean.setDatas(list);
		pageBean.setTotalCount(totalnumber);
		return pageBean;
	}

	@Override
	public List<String> queryAdoptedProductName(String id) {
		String sql = "select h.hardware_product_name from softwarehardware al"
				+ " LEFT JOIN hardwareproduct h ON al.hardwareid = h.id where al.softwareid = " + id + ";";
		List<String> list = softwareMapper.selectbysql(sql);
		return list;
	}

	@Override
	public List<String> queryAdoptedPlatform(String id) {
		String sql = "select h.hardwarestationname from softwareHardwareStation al"
				+ " LEFT JOIN hardwarestation h ON al.hardwarestationid = h.id where al.softwareid = " + id + ";";
		List<String> list = softwareMapper.selectbysql(sql);
		return list;
	}

	@Override
	public List<String> queryAdoptedOS(String id) {
		String sql = "select h.operatingsystemname from softwareoperatingsystem al LEFT JOIN operatingsystem h ON al.osid = h.id where al.softwareid = " + id + ";";
		List<String> list = softwareMapper.selectbysql(sql);
		return list;
	}

	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long save(Software software, String hardwareProductSelect, String hardwareStationSelect, String OSSelect) {
		long id = softwareMapper.insert(software);
		long newSoftwareId = software.getId();
		if(hardwareProductSelect != null){
			String[] hardwareProductArray = hardwareProductSelect.split(",");
			for(int i = 0; i < hardwareProductArray.length; i++){
				Integer hardwareProductId = hardwareProductMapper.queryIdByName(hardwareProductArray[i]);
				SoftwareHardware softwareHardware = new SoftwareHardware();
				softwareHardware.setSoftwareid(newSoftwareId);
				softwareHardware.setHardwareid(hardwareProductId);
				softwareHardware.setModifytime(new Date());
				softwareHardwareMapper.insert(softwareHardware);
			}
			
		}
		
		if(hardwareStationSelect != null){
			String[] hardwareStationArray = hardwareStationSelect.split(",");
			for(int i = 0; i < hardwareStationArray.length; i++){
				String sql = "select id from hardwarestation where hardwarestationname = '" + hardwareStationArray[i] + "'";
				List<String> hardwarestationIdList = softwareMapper.selectbysql(sql);
				SoftwareHardwarestation softwareHardwarestation = new SoftwareHardwarestation();
				softwareHardwarestation.setSoftwareid(newSoftwareId);
				softwareHardwarestation.setHardwarestationid(Long.parseLong(hardwarestationIdList.get(0)));
				softwareHardwarestation.setModifytime(new Date());
				softwareHardwarestationMapper.insert(softwareHardwarestation);
			}
			
		}
		
		if(OSSelect != null){
			String[] OSArray = OSSelect.split(",");
			for(int i = 0; i < OSArray.length; i++){
				String sql = "select id from operatingsystem where operatingsystemname = '" + OSArray[i] + "'";
				List<String> OSIdList = softwareMapper.selectbysql(sql);
				SoftwareOS softwareOS = new SoftwareOS();
				softwareOS.setSoftwareid(newSoftwareId);
				softwareOS.setOsid(Long.parseLong(OSIdList.get(i)));
				softwareOS.setModifytime(new Date());
				softwareOSMapper.insert(softwareOS);
			}
			
		}
		return id;
	}
	
}
