package com.sunland.hzhc.bean.i_car_focus;

import com.sunland.hzhc.bean.BaseRequestBean;

public class VehicleFocusReqBean extends BaseRequestBean {
    private String cphm;
    private String hpzl;
    private String syr_sfzmhm;

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    public String getSyr_sfzmhm() {
        return syr_sfzmhm;
    }

    public void setSyr_sfzmhm(String syr_sfzmhm) {
        this.syr_sfzmhm = syr_sfzmhm;
    }
}
