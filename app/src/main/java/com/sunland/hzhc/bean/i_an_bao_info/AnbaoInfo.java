package com.sunland.hzhc.bean.i_an_bao_info;

public class AnbaoInfo {
    private String EDITION;//"安保版本,值为 01 ,02  03，"

    private String ISUSE;// "是否启用。1：启用；2：未启用"

    public String getEDITION() {
        return EDITION;
    }

    public void setEDITION(String EDITION) {
        this.EDITION = EDITION;
    }

    public String getISUSE() {
        return ISUSE;
    }

    public void setISUSE(String ISUSE) {
        this.ISUSE = ISUSE;
    }
}
