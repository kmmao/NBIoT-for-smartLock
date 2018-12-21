package com.routon.plcloud.common.model;

import java.io.Serializable;

public class Area implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -192325617218856583L;

	private String id;
	
	private String code_a;
	
	private String name;
	
	private String code_c;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode_a() {
		return code_a;
	}

	public void setCode_a(String code_a) {
		this.code_a = code_a;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode_c() {
		return code_c;
	}

	public void setCode_c(String code_c) {
		this.code_c = code_c;
	}
}
