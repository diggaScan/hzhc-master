package com.sunland.hzhc.bean.i_track;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class TrackResBean extends ResultBase {

    private int count;
    private String status;
    private List<GjList> gjList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<GjList> getGjList() {
        return gjList;
    }

    public void setGjList(List<GjList> gjList) {
        this.gjList = gjList;
    }
}
