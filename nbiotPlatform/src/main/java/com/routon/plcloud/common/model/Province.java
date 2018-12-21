package com.routon.plcloud.common.model;

import java.io.Serializable;

public class Province implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5006106657219312809L;

	private String id ; 
	
	private String code_p ; 
	
	private String name ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode_p() {
		return code_p;
	}

	public void setCode_p(String code_p) {
		this.code_p = code_p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

}
