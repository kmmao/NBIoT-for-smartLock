package com.routon.plcloud.admin.hardware.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.routon.plcloud.common.PagingBean;
import com.routon.plcloud.common.PagingSortDirection;
import com.routon.plcloud.common.UserProfile;
import com.routon.plcloud.common.dao.mybatis.PagingDaoMybatis;
import com.routon.plcloud.common.model.HardwareProduct;
import com.routon.plcloud.common.model.Role;
import com.routon.plcloud.common.persistence.HardwareProductMapper;
import com.routon.plcloud.common.persistence.RoleMapper;

/**
 * 
 * @author wangxiwei
 *
 */
@Service
public class HardwareProductImpl implements HardwareService {
	
	@Resource(name = "pagingDaoMybatis")
	private PagingDaoMybatis pagingDao;

	@Autowired
	private HardwareProductMapper hardwareProductMapper;
	
	@Override
	public PagingBean<HardwareProduct> quryAll(int startIndex, int pageSize, String sortCriterion, String sortDirection,
			HardwareProduct queryCondition, String in_hardwareIds, String notin_hardwareIds, Long loginUserId, boolean exportflag) {
		
		String sql = "select a.id, a.hardware_product_name as hardwareProductName, a.hardware_product_version as hardwareProductVersion,"
				+ " a.erpcode, a.createtime, a.modifytime, a.hardware_station as hardwareStation,"
				+ " a.operate_system as operateSystem from hardwareproduct a WHERE 1 = 1 ";
		
		StringBuilder sbHQL = new StringBuilder(sql);
		
		if(StringUtils.isNotBlank(queryCondition.getHardwareProductName())){
			String hardwareproductname = queryCondition.getHardwareProductName().trim();
			sbHQL.append(" and a.hardware_product_name like '%");
			sbHQL.append(hardwareproductname);
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
			if(sortCriterion.equals("hardwareProductName")){
				sortCriterion = "hardware_product_name";
			}
			else if(sortCriterion.equals("hardwareProductVersion")){
				sortCriterion = "hardware_product_version";
			}
			else if(sortCriterion.equals("hardwareStation")){
				sortCriterion = "hardware_station";
			}
			else if(sortCriterion.equals("operateSystem")){
				sortCriterion = "operate_system";
			}
			sortCriterions = new String[] { "a." + sortCriterion };
		}
		PagingSortDirection[] sortDirections =null;
		if(sortDirection != null){
			sortDirections = new PagingSortDirection[] { "desc"
					.equals(sortDirection.toLowerCase()) ? PagingSortDirection.DESC
					: PagingSortDirection.ASC };
		}
		PagingBean<HardwareProduct> pagingSystemhardware = pagingDao.query(hardwareProductMapper,sbHQL.toString(), 
				sortCriterions, sortDirections, startIndex, pageSize,  exportflag);

		return pagingSystemhardware;				
	}
		
		//List<HardwareProduct> list = hardwareProductMapper.selectBySql(sql);
		
//		String countSql = "select count(*) from hardwareproduct a WHERE 1 = 1 ";
//		
//		StringBuilder sbHQL = new StringBuilder(sql);
//		StringBuilder sbHQLcount = new StringBuilder(countSql);
//		if(productName != null){
//			sbHQL.append("and a.hardware_product_name like '%" + productName + "%'");
//			sbHQLcount.append("and a.hardware_product_name like '%" + productName + "%'");
//		}
//		
//		List<HardwareProduct> list = hardwareProductMapper.selectByCondition(sbHQL.toString(), startIndex, pageSize);
//		Integer numbers = hardwareProductMapper.selectCountSql(sbHQLcount.toString());
//		
//		PagingBean<HardwareProduct> pagebean = new PagingBean<HardwareProduct>();
//		pagebean.setDatas(list);
//		pagebean.setTotalCount(numbers);
//		return pagebean;
//	}

	@Override
	public Long save(String productName, String erpCode, String hardwareVersion, String hardwarePlatform,
			String systemVersion) {
		HardwareProduct hardwareProduct = new HardwareProduct();
		//硬件产品名称重复查询
		String sql = "select a.* from hardwareproduct a where a.hardware_product_name = '" + productName.trim() + "'";
		List<HardwareProduct> hardwareProducts = hardwareProductMapper.selectBySql(sql);
		if(hardwareProducts != null && hardwareProducts.size() > 0){
			return -1L;
		}
		//ERP编码重复查询
		String ERPsql = "select a.* from hardwareproduct a where a.erpcode = '" + erpCode.trim() + "'";
		List<HardwareProduct> hardwareProducts_Erp =  hardwareProductMapper.selectBySql(ERPsql);
		if(hardwareProducts_Erp != null && hardwareProducts_Erp.size() > 0){
			return -2L;
		}
		hardwareProduct.setCreatetime(new Date());
		hardwareProduct.setHardwareProductName(productName.trim());
		hardwareProduct.setErpCode(erpCode.trim());
		hardwareProduct.setHardwareProductVersion(hardwareVersion.trim());
		hardwareProduct.setOperateSystem(systemVersion);
		hardwareProduct.setHardwareStation(hardwarePlatform);
		long id = hardwareProductMapper.insert(hardwareProduct);
		if(id > 0){
		   return id;
		}
		else{
			return -3L;
		}
	}

	
	
	@Override
	public Long edit(Integer id, String productName, String erpCode, String hardwareVersion, String hardwarePlatform,
			String systemVersion) {
		//硬件产品名称重复查询
			String sql = "select a.* from hardwareproduct a where a.hardware_product_name = '" + productName.trim() + "'";
			if(id != null){
				sql +="and a.id <>"+id;
			}
			List<HardwareProduct> hardwareProducts = hardwareProductMapper.selectBySql(sql);
			if(hardwareProducts != null && hardwareProducts.size() > 0){
				return -1L;
			}
		//ERP编码重复查询
		    String ERPsql = "select a.* from hardwareproduct a where a.erpcode = '" + erpCode.trim() + "'";
		    if(id != null){
		    	ERPsql +="and a.id <>"+id;
			}
			List<HardwareProduct> hardwareProducts_Erp =  hardwareProductMapper.selectBySql(ERPsql);
			if(hardwareProducts_Erp != null && hardwareProducts_Erp.size() > 0){
				return -2L;
			}
		HardwareProduct hardwareProduct = hardwareProductMapper.selectById(id);
		hardwareProduct.setHardwareProductName(productName.trim());
		hardwareProduct.setErpCode(erpCode.trim());
		
		hardwareProduct.setHardwareProductVersion(hardwareVersion.trim());
		hardwareProduct.setHardwareStation(hardwarePlatform);
		hardwareProduct.setOperateSystem(systemVersion);
		hardwareProduct.setModifytime(new Date());
		
		hardwareProductMapper.update(hardwareProduct);
		return hardwareProduct.getId();
	}

	@Override
	public List<String> queryAllHardwareStation() {
		String sql = "select distinct hardwarestationname from hardwarestation;";
		List<String> list = hardwareProductMapper.selectBySql2(sql);
		return list;
	}

	@Override
	public List<String> queryAllSystemVersion() {
		String sql = "select distinct operatingsystemname from operatingsystem;";
		List<String> list = hardwareProductMapper.selectBySql2(sql);
		return list;
	}

	@Override
	public List<String> queryAllHardwareProduct() {
		String sql = "select distinct hardware_product_name from hardwareproduct;";
		List<String> list = hardwareProductMapper.selectBySql2(sql);
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	public int delete(String ids, UserProfile optUser) {
		String HardwareId_array[] = ids.split(",");
		int del_succee_count = 0;
		String del_succee_HardwareIds =  "";
		for(String HardwareId : HardwareId_array){
			Long Hardwareid = Long.parseLong(HardwareId);
			hardwareProductMapper.deleteById(Hardwareid);
			if(del_succee_HardwareIds == ""){
				del_succee_HardwareIds += HardwareId;
			}
			else{
				del_succee_HardwareIds += ",";
				del_succee_HardwareIds += HardwareId;
			}
			del_succee_count++;
		}
		if(del_succee_count == HardwareId_array.length){
			//roleServiceLog.delete(del_succee_roleIds, optUser);
			return 1;// 全部删除成功
		}
		else if(del_succee_count == 0){
			return -1;//全部删除失败
		}
		else{
			return -2;//部分删除成功
		}
	}
	
}
