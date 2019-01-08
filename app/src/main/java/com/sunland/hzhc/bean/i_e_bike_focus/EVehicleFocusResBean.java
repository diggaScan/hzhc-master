package com.sunland.hzhc.bean.i_e_bike_focus;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class EVehicleFocusResBean extends ResultBase {
    private List<DdcFocus> ddcList;

    public List<DdcFocus> getDdcList() {
        return ddcList;
    }

    public void setDdcList(List<DdcFocus> ddcList) {
        this.ddcList = ddcList;
    }
}
