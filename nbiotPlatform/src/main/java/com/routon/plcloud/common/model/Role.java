package com.routon.plcloud.common.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author huanggang
 *
 */
public class Role implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3653362115874697531L;

//	private Long id;
	private Long id;
	
	@Length(min=1, max=12,message="角色名称长度为1-12")
	private String name;
	
	//查询时用到的角色名称
	private String rolename;

	private String remark;
	
	private String status;

	private Date createTime;
	
	@NotEmpty(message="角色必须有至少一个菜单")
	private String menuIds;
	
	private String menuNames;

	private Date modifyTime;

	private long createUserId;
	
	private boolean checked;

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}

	
	public String getMenuIds() {
		return menuIds;
	}

	
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	
	public String getMenuNames() {
		return menuNames;
	}

	
	public void setMenuNames(String menuNames) {
		this.menuNames = menuNames;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



}
