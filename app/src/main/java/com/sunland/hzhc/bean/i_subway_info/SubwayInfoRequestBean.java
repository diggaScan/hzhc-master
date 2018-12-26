package com.sunland.hzhc.bean.i_subway_info;

import com.sunland.hzhc.bean.BaseRequestBean;

public class SubwayInfoRequestBean extends BaseRequestBean {
    private String number;//传入地铁number

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
