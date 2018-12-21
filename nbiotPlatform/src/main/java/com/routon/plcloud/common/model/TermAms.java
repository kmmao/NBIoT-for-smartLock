package com.routon.plcloud.common.model;

public class TermAms implements java.io.Serializable{

	private static final long serialVersionUID = 4087360367894590382L;
	
	private int term_id;
	
	private int term_type;
	
	private String term_code;
	
	private String term_sn;

	public int getTerm_id() {
		return term_id;
	}

	public void setTerm_id(int term_id) {
		this.term_id = term_id;
	}

	public int getTerm_type() {
		return term_type;
	}

	public void setTerm_type(int term_type) {
		this.term_type = term_type;
	}

	public String getTerm_code() {
		return term_code;
	}

	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}

	public String getTerm_sn() {
		return term_sn;
	}

	public void setTerm_sn(String term_sn) {
		this.term_sn = term_sn;
	}
	

}
