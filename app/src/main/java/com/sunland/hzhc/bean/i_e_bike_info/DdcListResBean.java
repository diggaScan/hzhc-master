package com.sunland.hzhc.bean.i_e_bike_info;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class DdcListResBean extends ResultBase {
    private List<InfoDDCXQs> infoDDCXQs;

    public List<InfoDDCXQs> getInfoDDCXQs() {
        return infoDDCXQs;
    }

    public void setInfoDDCXQs(List<InfoDDCXQs> infoDDCXQs) {
        this.infoDDCXQs = infoDDCXQs;
    }
}
