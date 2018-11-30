package com.sunland.hzhc.modules.case_module.bean;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class CaseCateResBean extends ResultBase {

    private List<LbList> lbList;

    public List<LbList> getLbList() {
        return lbList;
    }

    public void setLbList(List<LbList> lbList) {
        this.lbList = lbList;
    }
}
