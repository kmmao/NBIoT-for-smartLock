package com.routon.plsy.service;

import com.routon.plsy.dao.HeartbeatsMapper;
import com.routon.plsy.model.Heartbeats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartbeatsService {

    @Autowired
    private HeartbeatsMapper heartbeatsMapper;

    public int insertHeartbeats(Heartbeats heartbeats){
        int id = heartbeatsMapper.insertHeartbeats(heartbeats);
        return id;
    }

    public Heartbeats selectHeartbeatsByDeviceid(String deviceid){
        Heartbeats heartbeats =  heartbeatsMapper.selectByDeviceId(deviceid);
        return heartbeats;
    }

    public int updateHeartbeatsByDeviceid(Heartbeats heartbeats){
        int id = heartbeatsMapper.update(heartbeats);
        return id;
    }
}
