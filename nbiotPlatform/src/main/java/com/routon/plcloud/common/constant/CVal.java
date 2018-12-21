package com.routon.plcloud.common.constant;


public interface CVal {
	public int Success 		= 1;
	public int Fail			= 0;
	public int Exception	= -1;
	
	public interface UserStatus {
		int valid = 1;
		int Invalid = 0;
	}
	public interface ProjectStatus {
		int valid = 1;
		int Invalid = 0;
	}
	

}
