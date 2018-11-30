package com.sunland.hzhc.modules.Internet_cafe_module.bean;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class RyResBean  extends ResultBase {
    private List<InfoWBSWRY> infoWBSWRYs;

    public List<InfoWBSWRY> getInfoWBSWRYs() {
        return infoWBSWRYs;
    }

    public void setInfoWBSWRYs(List<InfoWBSWRY> infoWBSWRYs) {
        this.infoWBSWRYs = infoWBSWRYs;
    }
}
