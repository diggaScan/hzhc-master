package com.sunland.hzhc.bean.i_hotel_names;

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
