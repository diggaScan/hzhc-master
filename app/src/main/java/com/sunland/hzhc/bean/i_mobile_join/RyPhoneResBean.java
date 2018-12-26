package com.sunland.hzhc.bean.i_mobile_join;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class RyPhoneResBean extends ResultBase {
    private List<RyInfo> result_list;

    public List<RyInfo> getRyInfos() {
        return result_list;
    }

    public void setRyInfos(List<RyInfo> result_list) {
        this.result_list = result_list;
    }
}
