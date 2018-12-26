package com.sunland.hzhc.bean.i_car_info_joint;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class ClxxzhResponseBean extends ResultBase {
    private List<InfoJDCXQs> infoJDCXQs;

    public List<InfoJDCXQs> getInfoJDCXQs() {
        return infoJDCXQs;
    }

    public void setInfoJDCXQs(List<InfoJDCXQs> infoJDCXQs) {
        this.infoJDCXQs = infoJDCXQs;
    }
}
