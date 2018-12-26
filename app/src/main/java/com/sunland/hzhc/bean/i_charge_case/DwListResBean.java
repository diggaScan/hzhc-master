package com.sunland.hzhc.bean.i_charge_case;

import com.sunland.hzhc.bean.i_case_cate.DwBaseInfo;
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
