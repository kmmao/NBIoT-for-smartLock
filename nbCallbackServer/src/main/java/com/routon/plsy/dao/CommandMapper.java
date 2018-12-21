package com.routon.plsy.dao;

import com.routon.plsy.model.Command;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommandMapper {
    @Insert("insert into commandlog(deviceid,commandtype,commandstate,commandtime,commandid,commandstr,face_id,sn) "
            + "values(#{deviceid},#{commandtype},#{commandstate},#{commandtime},#{commandid},#{commandstr},#{face_id},#{sn})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public int insertCommand(Command command);

    @Update("UPDATE commandlog SET commandstate=#{commandstate} WHERE deviceid = #{deviceid} AND commandid = #{commandid}")
    void update(Command command);

    @Select("SELECT * FROM commandlog WHERE sn = #{sn} AND deviceid = #{deviceid}")
    public List<Command> selectStatusBySn(@Param("sn") String sn, @Param("deviceid") String deviceid);

    @Select("SELECT * FROM commandlog WHERE report_white = #{report_white} AND deviceid = #{deviceid} AND commandtype = #{commandtype}")
    public List<Command> selectInfoByReport(@Param("report_white") int report_white, @Param("deviceid") String deviceid, @Param("commandtype") int commandtype);

    @Select("SELECT * FROM commandlog WHERE report_manager = #{report_manager} AND deviceid = #{deviceid}")
    public List<Command> selectInfoByManagerReport(@Param("report_manager") int report_manager, @Param("deviceid") String deviceid);

    @Update("UPDATE commandlog SET report_white= #{report_white},report_manager = #{report_manager} WHERE deviceid = #{deviceid} AND face_id = #{face_id}")
    void updatereport(Command command);

    @Update("UPDATE commandlog SET report_manager= 0 WHERE deviceid = #{deviceid} AND face_id = #{face_id}")
    void updateMangerreport(@Param("deviceid") String deviceid, @Param("face_id") int reportwhite);
   
    @Select("SELECT * FROM commandlog WHERE report_sendwhite = #{report_sendwhite} AND deviceid = #{deviceid}")
    public List<Command> selectInfoBySendwhiteReport(@Param("report_sendwhite") int report_sendwhite, @Param("deviceid") String deviceid);

    @Update("UPDATE commandlog SET report_sendwhite= 0 WHERE deviceid = #{deviceid} AND face_id = #{face_id}")
    void updateSendwhiteReport(@Param("deviceid") String deviceid, @Param("face_id") int reportwhite);

    @Select("SELECT * FROM commandlog WHERE commandid = #{commandid} AND deviceid = #{deviceid}")
    public Command selectInfoByCommandid(@Param("commandid") String commandid, @Param("deviceid") String deviceid);

}
