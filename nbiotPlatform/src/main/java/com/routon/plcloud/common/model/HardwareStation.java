package com.routon.plcloud.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author huanggang
 *
 */
public class HardwareStation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 887599399100048340L;

	/**
	 * 硬件平台id
	 */
	private long id; 
	
	
	/**
	 * 硬件平台名称
	 */
	@Length(min=1, max=12,message="硬件平台名称长度为1-12")
	private String hardwarestationName;
	
	
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 修改时间
	 */
	private Date moditytime;
	
	@NotEmpty(message="硬件平台必须有至少一个操作系统")
	private String opsystemIds;
	
	/**
	 * 适配操作系统
	 */
	private String adaptoperatingSystems;
	
	private HashSet<Long> hardwareIdset = new HashSet<Long>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHardwarestationName() {
		return hardwarestationName;
	}

	public void setHardwarestationName(String hardwarestationName) {
		this.hardwarestationName = hardwarestationName;
	}

	public String getAdaptoperatingSystem() {
		return adaptoperatingSystems;
	}

	public void setAdaptoperatingSystem(String adaptoperatingSystems) {
		this.adaptoperatingSystems = adaptoperatingSystems;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModitytime() {
		return moditytime;
	}

	public void setModitytime(Date moditytime) {
		this.moditytime = moditytime;
	}
	
	public String getOpsystemIds() {
		return opsystemIds;
	}

	public void setOpsystemIds(String opsystemIds) {
		this.opsystemIds = opsystemIds;
	}

	public HashSet<Long> getHardwareIdset() {
		return hardwareIdset;
	}

	public void setHardwareIdset(HashSet<Long> hardwareIdset) {
		this.hardwareIdset = hardwareIdset;
	}

}
