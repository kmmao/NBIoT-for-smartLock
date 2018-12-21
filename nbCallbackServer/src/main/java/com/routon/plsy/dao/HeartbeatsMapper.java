package com.routon.plsy.dao;

import com.routon.plsy.model.Heartbeats;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface HeartbeatsMapper {

    @Insert("insert into heartbeats(device_id,whitefacelist,isreportwhite,isreportmanager,update_time,manangerfacelist) "
            + "values(#{device_id},#{whitefacelist},#{isreportwhite},#{isreportmanager},#{update_time},#{manangerfacelist})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public int insertHeartbeats(Heartbeats heartbeats);

    @Select("SELECT * FROM heartbeats WHERE device_id = #{device_id}")
    public Heartbeats selectByDeviceId(String device_id);

    @Update("UPDATE heartbeats SET whitefacelist=#{whitefacelist},isreportwhite=#{isreportwhite},isreportmanager=#{isreportmanager}," +
            "update_time=#{update_time},manangerfacelist=#{manangerfacelist} WHERE device_id = #{device_id}")
    int update(Heartbeats heartbeats);
}
