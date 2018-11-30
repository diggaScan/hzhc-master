package com.sunland.hzhc.modules.case_module.bean;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class DwListResBean extends ResultBase {
    private List<DwBaseInfo> dwlist;

    public List<DwBaseInfo> getDwlist() {
        return dwlist;
    }

    public void setDwlist(List<DwBaseInfo> dwlist) {
        this.dwlist = dwlist;
    }
}
