package com.routon.plcloud.admin.privilege.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.plcloud.admin.privilege.service.log.HardwarestationServiceLog;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.HardwareProduct;
import com.routon.plcloud.common.model.HardwareStation;
import com.routon.plcloud.common.model.OperaterHardware;
import com.routon.plcloud.common.model.OperatingSystem;
import com.routon.plcloud.common.model.SoftwareHardwarestation;
import com.routon.plcloud.common.persistence.HardwareProductMapper;
import com.routon.plcloud.common.persistence.HardwareStationMapper;
import com.routon.plcloud.common.persistence.OperatHardwareMapper;
import com.routon.plcloud.common.persistence.OperatingSystemMapper;
import com.routon.plcloud.common.persistence.SoftwareHardwarestationMapper;

/**
 * 
 * @author huanggang
 *
 */
@Service
public class HardwarestationServiceImpl implements HardwarestationService{
	
	private Logger logger = LoggerFactory.getLogger(HardwarestationServiceImpl.class);
	
	@Autowired
	private HardwareStationMapper hardwarestationMapper;
	
	@Autowired
	private HardwareProductMapper hardwareProductMapper;
	
	@Autowired
	private OperatHardwareMapper operathardwareMapper;
	
	@Autowired
	private OperatingSystemMapper operatingsystemMapper;
	
	@Autowired
	private SoftwareHardwarestationMapper softwarehardwarestationMapper;
	
	@Autowired
	private HardwarestationServiceLog hardwarestationServiceLog;
	
	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;	
	
	@Transactional(readOnly = true)
	public PagingBean<HardwareStation> paging(int startIndex, int pageSize,
			String sortCriterion, String sortDirection, HardwareStation queryCondition,
			String in_hardwareIds, String notin_hardwareIds, Long loginUserId, boolean exportflag) {

		String pagingQueryLanguage = " select a.* from hardwarestation a  where 1=1  ";
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);

		if (StringUtils.isNotBlank(queryCondition.getHardwarestationName())) {

			String hardwareName = queryCondition.getHardwarestationName().trim();
			
			sbHQL.append(" and a.hardwarestationname like '%");
			sbHQL.append(hardwareName); 
			sbHQL.append("%'");
			 
		}

		if (in_hardwareIds != null) {

			if (StringUtils.isNotBlank(in_hardwareIds)) {
				  sbHQL.append(" and a.id in ("); 
				  sbHQL.append(in_hardwareIds);
				  sbHQL.append(")");
			} else {
				sbHQL.append(" and a.id in (");
				sbHQL.append("-1");
				sbHQL.append(")");
			}
		}

		if (StringUtils.isNotBlank(notin_hardwareIds)) {
			
			 sbHQL.append(" and a.id not in (");
			 sbHQL.append(notin_hardwareIds); 
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
		
		PagingBean<HardwareStation> pagingSystemhardware = pagingDao.query(hardwarestationMapper,sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);

		return pagingSystemhardware;
	
	}
	
	@Override
	public List<OperatingSystem> queryAllSystemVersion() {
		String sql = "select DISTINCT a.* from operatingsystem a;";
		List<OperatingSystem> list = hardwarestationMapper.selectBySql2(sql);
		return list;
	}
	
	@Override
	public Long add(HardwareStation hardwarestation,String hardwarestationName, String opsystemIds, UserProfile optUser) {
//		HardwareStation harestation=new HardwareStation();
		
		boolean isExist = hardwareNameExist(hardwarestationName, null);

		if (isExist) {
			return -2l;
		}
		
		hardwarestation.setHardwarestationName(hardwarestationName);
		hardwarestation.setCreatetime(new Date());
		
		hardwarestation.setOpsystemIds(opsystemIds);
		
		long id = hardwarestationMapper.insert(hardwarestation);
		
		String opsystemId_array[] = opsystemIds.split(",");
		for (String opsystemId : opsystemId_array) {

			if (StringUtils.isNotBlank(opsystemId)) {

				OperaterHardware ophard=new OperaterHardware();
				
				ophard.setModifyTime(new Date());
				ophard.setOperatingsystemID(Long.parseLong(opsystemId));
				ophard.setHardwarestationID(hardwarestation.getId());

				operathardwareMapper.insert(ophard);
			}
		}
		
		if(id>0){
			hardwarestationServiceLog.add(hardwarestation, opsystemIds, optUser);
		}
		
		return id;
	}
	
	
	/**
	 * 保证新增的硬件平台名称不重复
	 * @param hardwareName
	 * @param hardwareId
	 * @return
	 */
	public boolean hardwareNameExist(String hardwareName, Integer hardwareId) {
		String sql = "select a.* from hardwarestation a where a.hardwarestationname = '"+hardwareName+"'";

		if (hardwareId != null) {
			sql += " and a.id <>" + hardwareId;
		}

		List<HardwareStation> hardwares = hardwarestationMapper.selectBySql(sql);

		if (hardwares != null && hardwares.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	
	@Override
	public Long edit(Integer id, String hardwarestationName, String opsystemIds, UserProfile optUser) {
		
		boolean isExist = hardwareNameExist(hardwarestationName, id);

		if (isExist) {
			return -2l;
		}
		
		HardwareStation hardware=hardwarestationMapper.selectById(id);
		
		hardware.setId(id);
		hardware.setHardwarestationName(hardwarestationName);
		hardware.setModitytime(new Date());
		hardware.setOpsystemIds(opsystemIds);
		
		hardwarestationMapper.update(hardware);
		long hardwarestationId = hardware.getId();
//		if(hardware.getId()>0){
		if(hardwarestationId > 0){
			operathardwareMapper.deleteByHardwareId(hardware.getId());
			
			String opsystemId_array[] = opsystemIds.split(",");
			for (String opsystemId : opsystemId_array) {
				
				if (StringUtils.isNotBlank(opsystemId)) {
					
					OperaterHardware ophardware = new OperaterHardware();
					ophardware.setModifyTime(new Date());
					ophardware.setOperatingsystemID(Long.parseLong(opsystemId));
					ophardware.setHardwarestationID(hardware.getId());

					operathardwareMapper.insert(ophardware);
				}
			}
			
			hardwarestationServiceLog.edit(hardware, opsystemIds, optUser);
//			return hardware.getId();
			return hardwarestationId;
		}
		else{
//			return hardware.getId();
			return hardwarestationId;
		}
	}
	
	private boolean isHardwareProHardwareSta(Long hardwareId){
		String sql = "select a.* from hardwarestation a where a.id =" + hardwareId;
		List<HardwareStation> hardwareStations = hardwarestationMapper.selectBySql(sql);
		String hardwarestationname = hardwareStations.get(0).getHardwarestationName();
		String sql_tem = "select a.* from hardwareproduct a where a.hardware_station = '" + hardwarestationname +"'";
		List<HardwareProduct> hardwareProducts = hardwareProductMapper.selectBySql(sql_tem);
		if(hardwareProducts != null && hardwareProducts.size() > 0){
			return true;
		}
		else{
			return false;
		}
	
	}
	
	private boolean isoftHardware(Long hardwareId) {

		String sql = "select a.* from softwarehardwarestation a where a.hardwarestationid = " + hardwareId;
		List<SoftwareHardwarestation> softwareHardwarestations = softwarehardwarestationMapper.selectBySql(sql);

		if (softwareHardwarestations != null && softwareHardwarestations.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int delete(String hardwareIds, UserProfile optUser) {
		String hardwareId_array[] = hardwareIds.split(",");
		int del_succee_count = 0;
		String del_succee_hardwareIds =  "";
		for (String hardwareId : hardwareId_array) {
			
			if (isoftHardware(Long.parseLong(hardwareId))) {
				logger.info("该硬件平台被软件使用不能删除");
				continue;
			} else if(isHardwareProHardwareSta(Long.parseLong(hardwareId))){
				logger.info("该硬件平台被硬件产品使用不能删除");
				continue;
			}
			else {
				Long hardwareid = Long.parseLong(hardwareId);
				
				operathardwareMapper.deleteByHardwareId(hardwareid);
				hardwarestationMapper.deleteById(hardwareid);	

				if(del_succee_hardwareIds.equals("")){
					del_succee_hardwareIds += hardwareid;
				}else {
					del_succee_hardwareIds += ",";
					del_succee_hardwareIds += hardwareid;
				}
					
				del_succee_count++;
			}
				
		}

		if (del_succee_count == 0) {
			return -1;// 全部删除失败
		} else if (del_succee_count == hardwareId_array.length) {
			hardwarestationServiceLog.delete(del_succee_hardwareIds, optUser);
			return 1;// 全部删除成功
		} else {
			hardwarestationServiceLog.delete(del_succee_hardwareIds, optUser);
			return -2;// 部分删除 还有部分因引用关系不能删除
		}

	}

	@Transactional(readOnly = true)
	public HardwareStation getHardwareByID(Long hardwareId) {
		HardwareStation hardwarestation = hardwarestationMapper.selectById(hardwareId);
		if (hardwarestation != null) {
			fillHardwarestation(hardwarestation);

			return hardwarestation;
		}
		else {
			return null;
		}
	}
	
	private void fillHardwarestation(HardwareStation hardwarestation) {
		String opsystemNames = "";
		String opsystemIds = "";

		List<OperatingSystem> opsystems = operatingsystemMapper
				.selectBySql("SELECT a.* FROM operatingsystem a, operaterhardware b WHERE a.id = b.operatingsystemid AND b.hardwarestationid ="
						+ hardwarestation.getId());
		
		if (opsystems != null) {

			for (OperatingSystem opsystem : opsystems) {
				if (opsystemIds.length() == 0) {
					opsystemIds = opsystem.getId() +"";
					opsystemNames = opsystem.getOperatingsystemname();
				}else {
					opsystemIds +=","+ opsystem.getId();
					opsystemNames +=","+ opsystem.getOperatingsystemname();
				}
				
				hardwarestation.getHardwareIdset().add(opsystem.getId());
			}
		}

		hardwarestation.setOpsystemIds(opsystemIds);
		hardwarestation.setAdaptoperatingSystem(opsystemNames);
	}

}
