package com.routon.plcloud.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author huanggang
 * 子订单model
 */
public class SubOrder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9216489190256054775L;

	private Long id;
	
	//子订单号
	private String subordernum;
	
	//客户商务人员
	private String contactname;
	
	//子订单创建时间
	private Date createtime;
	
	private String createtimeStr;
	
	//状态
	private int status;
	
	//续订数量
	private String renewnum;
	
	private String remark;
	
	//子订单修改时间
	private Date modifytime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubordernum() {
		return subordernum;
	}

	public void setSubordernum(String subordernum) {
		this.subordernum = subordernum;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRenewnum() {
		return renewnum;
	}

	public void setRenewnum(String renewnum) {
		this.renewnum = renewnum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}

}
