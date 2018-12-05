package com.sunland.hzhc.bean.ssjBean;

import java.io.Serializable;
import java.util.ArrayList;

public class NonVehicleInfo implements Serializable {
    private String cph = "";//车牌号
    private String sfzh = "";//车主身份证号
    private String xm = "";//车主姓名
    private String pp ="";//品牌
    private String cjh = "";//车架号
    private String fdjh = "";//发动机号
    private String fjdcxlh = "";//非机动车序列号
    private ArrayList<String> zpList;

    public ArrayList<String> getZpList() {
        return zpList;
    }

    public void setZpList(ArrayList<String> zpList) {
        this.zpList = zpList;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getCjh() {
        return cjh;
    }

    public void setCjh(String cjh) {
        this.cjh = cjh;
    }

    public String getFdjh() {
        return fdjh;
    }

    public void setFdjh(String fdjh) {
        this.fdjh = fdjh;
    }

    public String getFjdcxlh() {
        return fjdcxlh;
    }

    public void setFjdcxlh(String fjdcxlh) {
        this.fjdcxlh = fjdcxlh;
    }
}
