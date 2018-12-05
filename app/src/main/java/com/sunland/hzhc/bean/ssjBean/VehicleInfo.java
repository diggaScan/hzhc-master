package com.sunland.hzhc.bean.ssjBean;

import java.io.Serializable;
import java.util.ArrayList;

public class VehicleInfo implements Serializable {
    private String hphm = "";//号牌号码
    private String hpzl = "";//号牌种类
    private String syrzjhm = "";//所有人证件号码
    private String clsyr = "";//车辆所有人
    private String pp = "";//品牌
    private String xh = "";//型号
    private String ys = "";//颜色
    private String clsbdh = "";//车辆识别代号
    private String fdjh = "";//发动机号
    private String fdjxlh = "";//发动机序列号
    private String sfyyc = "";//是否营运车
    private String hclx = "";//核查类型  01安保 02 路面
    private String clxx = "";//盗抢信息+关注信息+情报接口返回的描述信息
    private ArrayList<String> zpList;

    public ArrayList<String> getZpList() {
        return zpList;
    }

    public void setZpList(ArrayList<String> zpList) {
        this.zpList = zpList;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    public String getSyrzjhm() {
        return syrzjhm;
    }

    public void setSyrzjhm(String syrzjhm) {
        this.syrzjhm = syrzjhm;
    }

    public String getClsyr() {
        return clsyr;
    }

    public void setClsyr(String clsyr) {
        this.clsyr = clsyr;
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

    public String getYs() {
        return ys;
    }

    public void setYs(String ys) {
        this.ys = ys;
    }

    public String getClsbdh() {
        return clsbdh;
    }

    public void setClsbdh(String clsbdh) {
        this.clsbdh = clsbdh;
    }

    public String getFdjh() {
        return fdjh;
    }

    public void setFdjh(String fdjh) {
        this.fdjh = fdjh;
    }

    public String getFdjxlh() {
        return fdjxlh;
    }

    public void setFdjxlh(String fdjxlh) {
        this.fdjxlh = fdjxlh;
    }

    public String getSfyyc() {
        return sfyyc;
    }

    public void setSfyyc(String sfyyc) {
        this.sfyyc = sfyyc;
    }

    public String getHclx() {
        return hclx;
    }

    public void setHclx(String hclx) {
        this.hclx = hclx;
    }

    public String getClxx() {
        return clxx;
    }

    public void setClxx(String clxx) {
        this.clxx = clxx;
    }
}
