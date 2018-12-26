package com.sunland.hzhc.bean.i_case_cate;

import com.sunland.hzhc.bean.i_charge_case.LbList;
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
