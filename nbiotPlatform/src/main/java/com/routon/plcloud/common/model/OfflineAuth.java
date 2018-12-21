package com.routon.plcloud.common.model;


/**
 * 
 * @author wangzhuo
 *
 */

public class OfflineAuth implements java.io.Serializable {

	private static final long serialVersionUID = -6202865316503830854L;
	
	private Long id;
	
	private String termsn;
	
	private String termlicence;

	public Long getId() {
		return id;
	}

	public String getTermsn() {
		return termsn;
	}

	public String getTermlicence() {
		return termlicence;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTermsn(String termsn) {
		this.termsn = termsn;
	}

	public void setTermlicence(String termlicence) {
		this.termlicence = termlicence;
	}

}
