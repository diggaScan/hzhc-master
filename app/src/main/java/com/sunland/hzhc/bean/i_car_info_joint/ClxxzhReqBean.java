package com.sunland.hzhc.bean.i_car_info_joint;

import com.sunland.hzhc.bean.BaseRequestBean;

public class ClxxzhReqBean extends BaseRequestBean {
    public CarInfoJoinDto carInfoJoinDto;
    public int pageNum;
    public int count;

    public CarInfoJoinDto getCarInfoJoinJson() {
        return carInfoJoinDto;
    }

    public void setCarInfoJoinJson(CarInfoJoinDto carInfoJoinJson) {
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
