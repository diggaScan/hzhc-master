package com.sunland.hzhc.bean.i_car_focus;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class VehicleFocusResBean extends ResultBase {
    private List<CarFocus_info> carList;

    public List<CarFocus_info> getCarList() {
        return carList;
    }

    public void setCarList(List<CarFocus_info> carList) {
        this.carList = carList;
    }
}
