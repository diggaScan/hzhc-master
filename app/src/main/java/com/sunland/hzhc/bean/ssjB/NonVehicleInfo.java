package com.sunland.hzhc.bean.ssjB;

import java.io.Serializable;
import java.util.ArrayList;

public class NonVehicleInfo implements Serializable {
    //    private String cph = "";//车牌号
//    private String sfzh = "";//车主身份证号
//    private String xm = "";//车主姓名
//    private String pp = "";//品牌
//    private String cjh = "";//车架号
//    private String fdjh = "";//发动机号
//    private String fjdcxlh = "";//非机动车序列号
    private String clph = "";//当事车牌号码
    private String ddjh = "";//	当事电动机号
    private String pp = "";//	当事电车品牌
    private String xh = "";    //当事电车型号
    private String csys = "";//	当事电车身颜色(中文)
    private String tz = "";//	当事电车特征
    private String jsyname = "";//	当事驾驶员姓名
    private String jsysfzh = "";//	当事驾驶员身份证号
    private String hplb = "";//当事号牌类别 1登记号牌  2防盗号牌
    private String cjh = "";//车架号
    private String csysdm = "";//车身颜色代码
    private ArrayList<String> zpList;

    public ArrayList<String> getZpList() {
        if (zpList == null)
            zpList = new ArrayList<>();
        return zpList;
    }

    public void setZpList(ArrayList<String> zpList) {
        this.zpList = zpList;
    }

    public String getClph() {
        return clph;
    }

    public void setClph(String clph) {
        this.clph = clph;
    }

    public String getDdjh() {
        return ddjh;
    }

    public void setDdjh(String ddjh) {
        this.ddjh = ddjh;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getCsys() {
        return csys;
    }

    public void setCsys(String csys) {
        this.csys = csys;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getJsyname() {
        return jsyname;
    }

    public void setJsyname(String jsyname) {
        this.jsyname = jsyname;
    }

    public String getJsysfzh() {
        return jsysfzh;
    }

    public void setJsysfzh(String jsysfzh) {
        this.jsysfzh = jsysfzh;
    }

    public String getHplb() {
        return hplb;
    }

    public void setHplb(String hplb) {
        this.hplb = hplb;
    }

    public String getCjh() {
        return cjh;
    }

    public void setCjh(String cjh) {
        this.cjh = cjh;
    }

    public String getCsysdm() {
        return csysdm;
    }

    public void setCsysdm(String csysdm) {
        this.csysdm = csysdm;
    }

    //    public String getCph() {
//        return cph;
//    }
//
//    public void setCph(String cph) {
//        this.cph = cph;
//    }
//
//    public String getSfzh() {
//        return sfzh;
//    }
//
//    public void setSfzh(String sfzh) {
//        this.sfzh = sfzh;
//    }
//
//    public String getXm() {
//        return xm;
//    }
//
//    public void setXm(String xm) {
//        this.xm = xm;
//    }
//
//    public String getPp() {
//        return pp;
//    }
//
//    public void setPp(String pp) {
//        this.pp = pp;
//    }
//
//    public String getCjh() {
//        return cjh;
//    }
//
//    public void setCjh(String cjh) {
//        this.cjh = cjh;
//    }
//
//    public String getFdjh() {
//        return fdjh;
//    }
//
//    public void setFdjh(String fdjh) {
//        this.fdjh = fdjh;
//    }
//
//    public String getFjdcxlh() {
//        return fjdcxlh;
//    }
//
//    public void setFjdcxlh(String fjdcxlh) {
//        this.fjdcxlh = fjdcxlh;
//    }
}
