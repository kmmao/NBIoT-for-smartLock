package com.routon.plcloud.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author wangxiwei
 *
 */
public class HardwareProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8415268019410462776L;

	private long id;
	
	private String hardwareProductName;
	
	private String hardwareProductVersion;
	
	private String erpCode;
	
	private Date createtime;
	
	private Date modifytime;
	
	private String hardwareStation;
	
	private String operateSystem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHardwareProductName() {
		return hardwareProductName;
	}

	public void setHardwareProductName(String hardwareProductName) {
		this.hardwareProductName = hardwareProductName;
	}

	public String getHardwareProductVersion() {
		return hardwareProductVersion;
	}

	public void setHardwareProductVersion(String hardwareProductVersion) {
		this.hardwareProductVersion = hardwareProductVersion;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getHardwareStation() {
		return hardwareStation;
	}

	public void setHardwareStation(String hardwareStation) {
		this.hardwareStation = hardwareStation;
	}

	public String getOperateSystem() {
		return operateSystem;
	}

	public void setOperateSystem(String operateSystem) {
		this.operateSystem = operateSystem;
	}
	
	
}
