package com.sunland.hzhc.bean.i_e_bike_focus;

import com.sunland.hzhc.bean.BaseRequestBean;

public class EVehicleFocusReqBean extends BaseRequestBean {
    private String hphm;
    private String cjh;
    private String fdjh;

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getCjh() {
        return cjh;
    }

    public void setCjh(String cjh) {
        this.cjh = cjh;
    }

    public String getFdjh() {
        return fdjh;
    }

    public void setFdjh(String fdjh) {
        this.fdjh = fdjh;
    }
}
