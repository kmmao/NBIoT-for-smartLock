package com.routon.plcloud.common.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author huanggang
 *
 */
public class OperatingSystem implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8950332024716554776L;
	
	private long id;
	
	@Length(min=1, max=12,message="操作系统名称长度为1-12")
	private String operatingsystemname;
	
	private Date createTime;
	
	private Date modityTime;
	
	private boolean checked;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperatingsystemname() {
		return operatingsystemname;
	}

	public void setOperatingsystemname(String operatingsystemname) {
		this.operatingsystemname = operatingsystemname;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModityTime() {
		return modityTime;
	}

	public void setModityTime(Date modityTime) {
		this.modityTime = modityTime;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}


}
