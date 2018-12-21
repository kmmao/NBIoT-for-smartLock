package com.routon.plcloud.common.model;

import java.io.Serializable;

public class City implements Serializable{
	
	private static final long serialVersionUID = -5233474235397214685L;

	private String id;
	
	private String code_c;
	
	private String name;
	
	private String code_p;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode_c() {
		return code_c;
	}

	public void setCode_c(String code_c) {
		this.code_c = code_c;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode_p() {
		return code_p;
	}

	public void setCode_p(String code_p) {
		this.code_p = code_p;
	}
	
	
}
