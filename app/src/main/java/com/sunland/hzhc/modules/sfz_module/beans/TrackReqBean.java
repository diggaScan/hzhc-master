package com.sunland.hzhc.modules.sfz_module.beans;

import com.sunland.hzhc.bean.BaseRequestBean;

public class TrackReqBean extends BaseRequestBean {
    private String sfzh;
    private int count;
    private String qqsj_q;
    private String qqsj_z;

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
