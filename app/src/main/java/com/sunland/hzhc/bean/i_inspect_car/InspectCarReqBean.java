package com.sunland.hzhc.bean.i_inspect_car;

import com.sunland.hzhc.bean.BaseRequestBean;


public class InspectCarReqBean extends BaseRequestBean {
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
