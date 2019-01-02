package com.sunland.hzhc.bean.i_an_bao_info;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class AnBaoResBean extends ResultBase {
    private List<AnbaoInfo> anbaoList;

    public List<AnbaoInfo> getAnbaoList() {
        return anbaoList;
    }

    public void setAnbaoList(List<AnbaoInfo> anbaoList) {
        this.anbaoList = anbaoList;
    }
}
