package com.sunland.hzhc.bean.i_wb_list;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class WbListResBean extends ResultBase {
    private List<WbBaseInfo> resps;

    public List<WbBaseInfo> getResps() {
        return resps;
    }

    public void setResps(List<WbBaseInfo> resps) {
        this.resps = resps;
    }
}
