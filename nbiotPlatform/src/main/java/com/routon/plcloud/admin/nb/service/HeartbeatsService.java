package com.routon.plcloud.admin.nb.service;

import com.routon.plcloud.common.model.Heartbeats;

public interface HeartbeatsService {
	
	public int insertHeartbeats(Heartbeats heartbeats);
	
	public int update(Heartbeats heartbeats);
	
	public Heartbeats selectByDeviceId(String deviceid);
	
}
