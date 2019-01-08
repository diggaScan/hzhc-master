package com.sunland.hzhc.bean.i_car_focus;

public class CarFocus_info  {
    private String sj;//		发生时间
    private String dw;//		录入单位
    private String lb;//		数据类别
    private String bh;//		记录编号
    private String nr;//		记录内容
    private String status;//	0：无记录 1：有记录

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
