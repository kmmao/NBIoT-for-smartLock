package com.routon.plcloud.admin.nb.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routon.plcloud.admin.nb.service.HeartbeatsService;
import com.routon.plcloud.common.model.Heartbeats;
import com.routon.plcloud.common.persistence.HeatbeatsMapper;

@Service
public class HeartbeatsServiceImpl implements HeartbeatsService {
	
	@Autowired
	private HeatbeatsMapper heatbeatsMapper;

	@Override
	public int insertHeartbeats(Heartbeats heartbeats) {
		int id = heatbeatsMapper.insertHeartbeats(heartbeats);
		return id;
	}

	@Override
	public int update(Heartbeats heartbeats) {
		int id = heatbeatsMapper.update(heartbeats);
		return id;
	}

	@Override
	public Heartbeats selectByDeviceId(String deviceid) {
		Heartbeats h = heatbeatsMapper.selectByDeviceId(deviceid);
		return h;
	}
	
}
