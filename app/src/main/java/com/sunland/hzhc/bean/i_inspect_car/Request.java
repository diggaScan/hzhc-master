package com.sunland.hzhc.bean.i_inspect_car;

public class Request {
   private String Lrb;
    private Dlxx dlxx;
    private Clxx clxx;

    public Dlxx getDlxx() {
        return dlxx;
    }

    public void setDlxx(Dlxx dlxx) {
        this.dlxx = dlxx;
    }

    public Clxx getClxx() {
        return clxx;
    }

    public void setClxx(Clxx clxx) {
        this.clxx = clxx;
    }

    public String getLrb() {
        return Lrb;
    }

    public void setLrb(String lrb) {
        Lrb = lrb;
    }
}
