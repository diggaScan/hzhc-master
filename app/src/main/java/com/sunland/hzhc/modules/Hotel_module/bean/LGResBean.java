package com.sunland.hzhc.modules.Hotel_module.bean;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class LGResBean extends ResultBase {
    private List<InfoLGZSRY> infoLGZSRYs;

    public List<InfoLGZSRY> getInfoLGZSRYs() {
        return infoLGZSRYs;
    }

    public void setInfoLGZSRYs(List<InfoLGZSRY> infoLGZSRYs) {
        this.infoLGZSRYs = infoLGZSRYs;
    }
}
