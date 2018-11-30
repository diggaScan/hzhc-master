package com.sunland.hzhc.modules.ddc_module.bean;

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
