package com.routon.plcloud.admin.privilege.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.plcloud.admin.privilege.service.log.OperatingSystemServiceLog;
import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.HardwareProduct;
import com.routon.plcloud.common.model.OperaterHardware;
import com.routon.plcloud.common.model.OperatingSystem;
import com.routon.plcloud.common.model.SoftwareOS;
import com.routon.plcloud.common.persistence.HardwareProductMapper;
import com.routon.plcloud.common.persistence.OperatHardwareMapper;
import com.routon.plcloud.common.persistence.OperatingSystemMapper;
import com.routon.plcloud.common.persistence.SoftwareOSMapper;


/**
 * 弹框显示实现类
 * @author huanggang
 *
 */
@Service
public class OperatingsystemServiceImpl implements OperatingsystemService{
	
	@Autowired
	private OperatingSystemMapper operatingsystemMapper;
	
	@Autowired
	private SoftwareOSMapper softwareOSMapper;
	
	@Autowired
	private OperatHardwareMapper operathardwareMapper;
	
	@Autowired
	private HardwareProductMapper hardwareProductMapper;
	
	@Autowired
	private OperatingSystemServiceLog operatingsystemServiceLog;
	
	@Resource(name = "pagingDaoMybatis")
    private PagingDaoMybatis pagingDao;
	
	@Override
	public PagingBean<OperatingSystem> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection,
			OperatingSystem queryCondition, String in_opsystemIds, String notin_opsystemIds, Long loginUserId,
			boolean exportflag) {

		String pagingQueryLanguage = " select a.* from operatingsystem a  where 1=1  ";
		
		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
		
		if (StringUtils.isNotBlank(queryCondition.getOperatingsystemname())){
			
			String opsystemName= queryCondition.getOperatingsystemname().trim();
			
			sbHQL.append(" and a.operatingsystemname like '%");
			sbHQL.append(opsystemName); 
			sbHQL.append("%'");
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
		PagingBean<OperatingSystem> pagingSystemrole = pagingDao.query(operatingsystemMapper,sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);

		return pagingSystemrole;
	}


	@Override
//	public Long save(String opsystemName) {
	public Long save(String opsystemName ,UserProfile optUser) {
		
		boolean isExist = opsystemNameExist(opsystemName, null);

		if (isExist) {
			return -2l;
		}
		
		OperatingSystem opSystem=new OperatingSystem();
		opSystem.setCreateTime(new Date());
		opSystem.setOperatingsystemname(opsystemName);
		long id = operatingsystemMapper.insert(opSystem);
		
		if(id>0){
			operatingsystemServiceLog.add(opSystem, optUser);
		}
		
		return id;
	}
	
	/**
	 * 保证添加的操作系统的唯一性
	 * @param opsystemName
	 * @param opsystemId
	 * @return
	 */
	@Transactional(readOnly = true)
	public boolean opsystemNameExist(String opsystemName, Integer opsystemId) {
		String sql = "select a.* from operatingsystem a where a.operatingsystemname = '"+opsystemName+"'";

		if (opsystemId != null) {
			sql += " and a.id <>" + opsystemId;
		}

		List<OperatingSystem> opsystems = operatingsystemMapper.selectBySql(sql);

		if (opsystems != null && opsystems.size() > 0) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public Long edit(Integer id,String opsystemName ,UserProfile optUser) {
		
		boolean isExist = opsystemNameExist(opsystemName, id);

		if (isExist) {
			return -2l;
		}
		
		OperatingSystem opSystem = operatingsystemMapper.selectById(id);
		
		opSystem.setId(id);
		opSystem.setOperatingsystemname(opsystemName);
		opSystem.setModityTime(new Date());
		long id1 = operatingsystemMapper.update(opSystem);
		
		if(id1>0){
			operatingsystemServiceLog.edit(opSystem, optUser);
		}
//		return opSystem.getId();
		return id1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int delete(String id,UserProfile optUser) {
		String opSystemId_array[] = id.split(",");
		int del_succee_count = 0;
		String del_succee_opSystemIds =  "";
		for(String opSystemid : opSystemId_array){
			if (isHardwareOpsystem(Long.parseLong(opSystemid))) {
		//		logger.info("该操作系统被硬件平台使用不能删除");
				continue;
			}else if(isHardwareProOpsystem(Long.parseLong(opSystemid))){
				continue;
			}else if(isSoftwareProOpsystem(Long.parseLong(opSystemid))){
				continue;
			}else {
				Long opSystemId = Long.parseLong(opSystemid);
				operatingsystemMapper.deleteById(opSystemId);
		//		return 1;
				if(del_succee_opSystemIds.equals("")){
					del_succee_opSystemIds += opSystemId;
				}
				else{
					del_succee_opSystemIds += ",";
					del_succee_opSystemIds += opSystemId;
				}
				del_succee_count++;
			}
		}
		if(del_succee_count == 0){
			return -1;//全部删除失败
		}else if(del_succee_count == opSystemId_array.length){
			operatingsystemServiceLog.delete(del_succee_opSystemIds, optUser);
			return 1;//全部删除成功
		}else{
			operatingsystemServiceLog.delete(del_succee_opSystemIds, optUser);
			return -2;//部分删除
		}
	}
	
	private boolean isSoftwareProOpsystem(long opsystemid){
		String sql = "select a.* from softwareoperatingsystem a where a.osid =" + opsystemid ;
		List<SoftwareOS> softwareOSs = softwareOSMapper.selectBySql(sql);
		if(softwareOSs != null && softwareOSs.size() > 0){
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isHardwareProOpsystem(long opsystemid){
		//String sql = "select a.* from operatingsystem a"
		String sql = "select a.* from operatingsystem a where a.id = "+opsystemid;
		List<OperatingSystem> operatingSystems = operatingsystemMapper.selectBySql(sql);
		String operatingsystemname = operatingSystems.get(0).getOperatingsystemname();
		String sql_tem = "select a.* from hardwareproduct a where a.operate_system = '"+operatingsystemname+"'";
		List<HardwareProduct> hardwareProducts = hardwareProductMapper.selectBySql(sql_tem);
		if(hardwareProducts != null && hardwareProducts.size() > 0){
			return true;
		}
		else{
			return false;
		}
		
		
	}
	
	private boolean isHardwareOpsystem(long opsystemid) {

		String sql = "select a.* from operaterhardware a where a.operatingsystemid = " + opsystemid;
		
//		List<HardwareStation> hardwareStations = hardwarestationMapper.selectBySql(sql);

		List<OperaterHardware> operaterhardwares=operathardwareMapper.selectBySql(sql);
		
		if (operaterhardwares != null && operaterhardwares.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}

/**
 * 页面显示实现类
 * @author huanggang
 *
 */
//@Service
//public class OperatingsystemServiceImpl implements OperatingsystemService{
//	private Logger logger = LoggerFactory.getLogger(OperatingsystemServiceImpl.class);
//	
//	@Autowired
//	private OperatingSystemMapper operatingsystemMapper;
//	
//	@Resource(name = "pagingDaoMybatis")
//    private PagingDaoMybatis pagingDao;
//	
//	@Autowired
//	private OperatingSystemServiceLog operatingsystemServiceLog;
//	
//	/**
//	 * 
//	 * @param OperatingSystem
//	 * @return -2 分组名已经存在;-1 失败; 大于0 分组id;
//	 */
//	private Long saveOpsystem(OperatingSystem opsystem){
//		String opsystemName = opsystem.getOperatingsystemname();
//		boolean isExist = operatingsystemNameExist(opsystemName, null);
//
//		if (isExist) {
//			logger.info("新增操作系统时,操作系统名已经存在");
//			return -2l;
//		}
//
//		opsystem.setCreateTime(new Date());
//		opsystem.setModityTime(new Date());
//
//		operatingsystemMapper.insert(opsystem);
//		long opsystemid = opsystem.getId();
//		if (opsystemid > 0) {
//			return opsystemid;
//		}
//
//		return -1l;
//	}
//	
//	/**
//	 * 
//	 * @param systemoperating
//	 * @return
//	 */
//	private Long updateOpsystem(OperatingSystem opsystem){
//		boolean isExist =operatingsystemNameExist(opsystem.getOperatingsystemname(),opsystem.getId());
//		
//		if (isExist) {
//			logger.info("更新操作系统时,操作系统名已经存在");
//			return -2L;
//		}
//		
//		List<OperatingSystem> tem_opsystems=operatingsystemMapper.selectById(opsystem.getId());
//		OperatingSystem tem_opsystem=tem_opsystems.get(0);
//		tem_opsystem.setOperatingsystemname(opsystem.getOperatingsystemname());
//		tem_opsystem.setModityTime(new Date());
//		
//		operatingsystemMapper.update(tem_opsystem);
//		
//		return opsystem.getId();
//	}
//
//	@Transactional(readOnly = true)
//	public boolean operatingsystemNameExist(String operatingsystemName, Long operatingsystemId) {
//		String sql = "select a.* from operatingsystem a where a.operatingsystemname = '"+operatingsystemName+"'";
//		
//		if (operatingsystemId != null) {
//			sql += " and a.id <>" + operatingsystemId;
//		}
//
//		List<OperatingSystem> opsystems = operatingsystemMapper.selectBySql(sql);
//
//		if (opsystems != null && opsystems.size() > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	public Long add(OperatingSystem opSystem, UserProfile optUser) {
//		Long opsystemId=saveOpsystem(opSystem);
//		
//		if (opsystemId > 0) {
//			operatingsystemServiceLog.add(opSystem, optUser);
//			return opsystemId;
//		}else{
//			return opsystemId;
//		}
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	public Long edit(OperatingSystem opSystem, UserProfile optUser) {
//		Long opsystemId=updateOpsystem(opSystem);
//		
//		if(opsystemId > 0){
//			operatingsystemServiceLog.edit(opSystem, optUser);
//			return opsystemId;
//		}else{
//			return opsystemId;
//		}
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	public int delete(String opsystemIds, UserProfile optUser) {
//		String opsystemId_array[] = opsystemIds.split(",");
//		
//		int del_succee_count = 0;
//		String del_succee_roleIds =  "";
//		
//		for (String opsystemId : opsystemId_array) {
//
//				Long opsystemid = Long.parseLong(opsystemId);
//				operatingsystemMapper.deleteById(opsystemid);
//
//				if(del_succee_roleIds.equals("")){
//					del_succee_roleIds += opsystemid;
//				}else {
//					del_succee_roleIds += ",";
//					del_succee_roleIds += opsystemid;
//				}
//				
//				del_succee_count++;
//		}
//
//		if (del_succee_count == 0) {
//			return -1;// 全部删除失败
//		} else if (del_succee_count == opsystemId_array.length) {
//			operatingsystemServiceLog.delete(del_succee_roleIds, optUser);
//			return 1;// 全部删除成功
//		} else {
//			operatingsystemServiceLog.delete(del_succee_roleIds, optUser);
//			return -2;// 部分删除 还有部分未能删除
//		}
//	}
//
//	@Transactional(readOnly = true)
//	public OperatingSystem getOpsystemByOpsystemId(Long opsystemId) {
//		List<OperatingSystem> opsystems=operatingsystemMapper.selectById(opsystemId);
//		
//		return opsystems.get(0);
//	}
//
//	@Override
//	public List<OperatingSystem> getOperatingSystems(Long loginUserId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public PagingBean<OperatingSystem> paging(int startIndex, int pageSize, String sortCriterion, String sortDirection,
//			OperatingSystem queryCondition, String in_opsystemIds, String notin_opsystemIds, Long loginUserId,
//			boolean exportflag) {
//
//		String pagingQueryLanguage = " select a.* from operatingsystem a  where 1=1  ";
//		
//		StringBuilder sbHQL = new StringBuilder(pagingQueryLanguage);
//		
//		if (StringUtils.isNotBlank(queryCondition.getOperatingsystemname())){
//			
//			String opsystemName= queryCondition.getOperatingsystemname();
//			
//			sbHQL.append(" and a.operatingsystemname like '%");
//			sbHQL.append(opsystemName); 
//			sbHQL.append("%'");
//		}
//		
//		String[] sortCriterions = null;
//		if(sortCriterion != null){
//			sortCriterions = new String[] { "a." + sortCriterion };
//		}
//		
//		PagingSortDirection[] sortDirections =null;
//		if(sortDirection != null){
//			sortDirections = new PagingSortDirection[] { "desc"
//					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
//					: PagingSortDirection.ASC };
//		}
//		PagingBean<OperatingSystem> pagingSystemrole = pagingDao.query(operatingsystemMapper,sbHQL.toString(), 
//				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);
//
//		return pagingSystemrole;
//	}
//
//}