package com.sunland.hzhc.modules.jdc_module;

import com.sunland.hzhc.modules.BaseRequestBean;

public class ClxxzhReqBean extends BaseRequestBean {
    public CarInfoJoinDto carInfoJoinDto;
    public int pageNum;
    public int count;

    public com.sunland.hzhc.modules.jdc_module.CarInfoJoinDto getCarInfoJoinJson() {
        return carInfoJoinDto;
    }

    public void setCarInfoJoinJson(com.sunland.hzhc.modules.jdc_module.CarInfoJoinDto carInfoJoinJson) {
        this.carInfoJoinDto = carInfoJoinJson;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
