package com.sunland.hzhc.bean.i_track;

import com.sunland.hzhc.bean.BaseRequestBean;

public class TrackReqBean extends BaseRequestBean {

    private String sfzh;//身份证号码（必填）
    private int count;//返回条数（选填，默认100）
    private String qqsj_q;//轨迹开始时间（必填）如：20160101
    private String qqsj_z;//轨迹结束时间（必填）如：20160102

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getQqsj_q() {
        return qqsj_q;
    }

    public void setQqsj_q(String qqsj_q) {
        this.qqsj_q = qqsj_q;
    }

    public String getQqsj_z() {
        return qqsj_z;
    }

    public void setQqsj_z(String qqsj_z) {
        this.qqsj_z = qqsj_z;
    }
}
