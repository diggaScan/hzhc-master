package com.sunland.hzhc.bean.i_inspect_person;

public class Request {
    private String Lrb;//版本信息
    public Dlxx dlxx;
    public RyxxReq ryxx;

    public Dlxx getDlxx() {
        return dlxx;
    }

    public void setDlxx(Dlxx dlxx) {
        this.dlxx = dlxx;
    }

    public RyxxReq getRyxxReq() {
        return ryxx;
    }

    public void setRyxxReq(RyxxReq ryxxReq) {
        this.ryxx = ryxxReq;
    }

    public String getLrb() {
        return Lrb;
    }

    public void setLrb(String lrb) {
        Lrb = lrb;
    }
}
