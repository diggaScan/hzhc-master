package com.sunland.hzhc.modules.sfz_module.beans;

import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunlandgroup.def.bean.result.ResultBase;

public class CountryPersonReqBean extends BaseRequestBean {
    private String sfzh;

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }
}
