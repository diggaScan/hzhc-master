package com.sunland.hzhc.bean.i_inspect_car;

import com.sunlandgroup.def.bean.result.ResultBase;

public class InspectCarResBean extends ResultBase {
    private String status;
    private CLxxRes clxx;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CLxxRes getClxx() {
        return clxx;
    }

    public void setClxx(CLxxRes clxx) {
        this.clxx = clxx;
    }
}
