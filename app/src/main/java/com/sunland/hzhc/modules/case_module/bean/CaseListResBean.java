package com.sunland.hzhc.modules.case_module.bean;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class CaseListResBean extends ResultBase {
    private List<InfoAJLB> infoAJLBs;

    public List<InfoAJLB> getInfoAJLBs() {
        return infoAJLBs;
    }

    public void setInfoAJLBs(List<InfoAJLB> infoAJLBs) {
        this.infoAJLBs = infoAJLBs;
    }
}
