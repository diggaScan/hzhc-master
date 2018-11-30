package com.sunland.hzhc.modules.own_car_module.own_car_bean;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class OwnedCarResBean extends ResultBase {

    private List<CarBaseInfo> carList;

    public List<CarBaseInfo> getCarList() {
        return carList;
    }

    public void setCarList(List<CarBaseInfo> carList) {
        this.carList = carList;
    }
}
