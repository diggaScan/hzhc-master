package com.sunland.hzhc.bean.i_owned_car;

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
