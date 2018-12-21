package com.routon.plsy.model;

import java.util.Date;

public class Heartbeats implements java.io.Serializable{

    private long id;

    private String device_id;

    private String whitefacelist;

    private String manangerfacelist;

    private int isreportwhite;

    private int isreportmanager;

    private String sn;

    private Date update_time;

    public long getId() {
        return id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getWhitefacelist() {
        return whitefacelist;
    }

    public String getManangerfacelist() {
        return manangerfacelist;
    }

    public int getIsreportwhite() {
        return isreportwhite;
    }

    public int getIsreportmanager() {
        return isreportmanager;
    }

    public String getSn() {
        return sn;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setWhitefacelist(String whitefacelist) {
        this.whitefacelist = whitefacelist;
    }

    public void setManangerfacelist(String manangerfacelist) {
        this.manangerfacelist = manangerfacelist;
    }

    public void setIsreportwhite(int isreportwhite) {
        this.isreportwhite = isreportwhite;
    }

    public void setIsreportmanager(int isreportmanager) {
        this.isreportmanager = isreportmanager;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
