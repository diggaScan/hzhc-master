package com.sunland.hzhc.bean.i_inspect_person;

public class RyxxReq {
    private String JNJW;// 01：境内 02：境外
    private String ZJLX;//  当JNJW为01时 填10
    private String ZJHM;//证件号
    private String SJHM;//	手机号码

    public String getJNJW() {
        return JNJW;
    }

    public void setJNJW(String JNJW) {
        this.JNJW = JNJW;
    }

    public String getZJLX() {
        return ZJLX;
    }

    public void setZJLX(String ZJLX) {
        this.ZJLX = ZJLX;
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }

    public String getSJHM() {
        return SJHM;
    }

    public void setSJHM(String SJHM) {
        this.SJHM = SJHM;
    }
}
