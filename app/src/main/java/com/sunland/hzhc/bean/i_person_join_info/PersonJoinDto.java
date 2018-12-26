package com.sunland.hzhc.bean.i_person_join_info;

public class PersonJoinDto {
    private String xm;//姓名
    private String xb;//性别 1:男，2:女
    private String csrq;//出生日期
    private String hjqh;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getHjqh() {
        return hjqh;
    }

    public void setHjqh(String hjqh) {
        this.hjqh = hjqh;
    }
}
