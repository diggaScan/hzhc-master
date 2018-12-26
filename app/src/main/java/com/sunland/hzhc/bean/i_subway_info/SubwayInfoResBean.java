package com.sunland.hzhc.bean.i_subway_info;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class SubwayInfoResBean extends ResultBase {
    private List<SubwayInfo> rows;

    public List<SubwayInfo> getRows() {
        return rows;
    }

    public void setRows(List<SubwayInfo> rows) {
        this.rows = rows;
    }
}
