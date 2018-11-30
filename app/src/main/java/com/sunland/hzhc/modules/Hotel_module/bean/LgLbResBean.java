package com.sunland.hzhc.modules.Hotel_module.bean;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class LgLbResBean extends ResultBase {
    private List<LgBaseInfo> LGList;

    public List<LgBaseInfo> getLGList() {
        return LGList;
    }

    public void setLGList(List<LgBaseInfo> LGList) {
        this.LGList = LGList;
    }
}
