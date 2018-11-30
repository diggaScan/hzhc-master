package com.sunland.hzhc.modules.p_archive_module.bean;

public class QueryInfoDAJDC {
    private String cllx_code;    //车辆类型代码
    private String cllx;//	车辆类型
    private String cphm;//	车牌号码
    private String zt;//	状态
    private String djrq;//	登记日期

    public String getCllx_code() {
        return cllx_code;
    }

    public void setCllx_code(String cllx_code) {
        this.cllx_code = cllx_code;
    }

    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getDjrq() {
        return djrq;
    }

    public void setDjrq(String djrq) {
        this.djrq = djrq;
    }
}
