package com.sunland.hzhc.bean.ssjB;

import java.io.Serializable;
import java.util.ArrayList;

public class VehicleInfo implements Serializable {
    private String clph;//当事车辆牌号
    private String cjh;//	当事车架号
    private String fdjh;//当事发动机号
    private String cllb;//当事车辆类别（中文）
    private String cllbdm = "";//车辆类别代码
    private String csysdm = "";//车身颜色代码
    private String pp;//	当事车辆品牌
    private String xh;//	当事车辆型号
    private String csys;//	当事车身颜色(中文)
    private String tz;//当事车辆特征
    private String jsyname;//	驾驶员姓名
    private String jsysfzh;//驾驶员身份证号
    private String sffa = ""; //是否涉案 0未涉案 1 涉案
    private String wfqk = "";//违法情况

    public String getCllbdm() {
        return cllbdm;
    }

    public void setCllbdm(String cllbdm) {
        this.cllbdm = cllbdm;
    }

    public String getCsysdm() {
        return csysdm;
    }

    public void setCsysdm(String csysdm) {
        this.csysdm = csysdm;
    }

    public String getClph() {
        return clph;
    }

    public void setClph(String clph) {
        this.clph = clph;
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

    public String getCllb() {
        return cllb;
    }

    public void setCllb(String cllb) {
        this.cllb = cllb;
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

    public String getSffa() {
        return sffa;
    }

    public void setSffa(String sffa) {
        this.sffa = sffa;
    }

    public String getWfqk() {
        return wfqk;
    }

    public void setWfqk(String wfqk) {
        this.wfqk = wfqk;
    }

    //    private String hphm = "";//号牌号码
//    private String hpzl = "";//号牌种类
//    private String syrzjhm = "";//所有人证件号码
//    private String clsyr = "";//车辆所有人
//    private String pp = "";//品牌
//    private String xh = "";//型号
//    private String ys = "";//颜色
//    private String clsbdh = "";//车辆识别代号
//    private String fdjh = "";//发动机号
//    private String fdjxlh = "";//发动机序列号
//    private String sfyyc = "";//是否营运车
//    private String hclx = "";//核查类型  01安保 02 路面
//    private String clxx = "";//盗抢信息+关注信息+情报接口返回的描述信息
    private ArrayList<String> zpList;

    public ArrayList<String> getZpList() {
        if (zpList == null)
            zpList = new ArrayList<>();
        return zpList;
    }

    public void setZpList(ArrayList<String> zpList) {
        this.zpList = zpList;
    }

//    public String getHphm() {
//        return hphm;
//    }
//
//    public void setHphm(String hphm) {
//        this.hphm = hphm;
//    }
//
//    public String getHpzl() {
//        return hpzl;
//    }
//
//    public void setHpzl(String hpzl) {
//        this.hpzl = hpzl;
//    }
//
//    public String getSyrzjhm() {
//        return syrzjhm;
//    }
//
//    public void setSyrzjhm(String syrzjhm) {
//        this.syrzjhm = syrzjhm;
//    }
//
//    public String getClsyr() {
//        return clsyr;
//    }
//
//    public void setClsyr(String clsyr) {
//        this.clsyr = clsyr;
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
//    public String getXh() {
//        return xh;
//    }
//
//    public void setXh(String xh) {
//        this.xh = xh;
//    }
//
//    public String getYs() {
//        return ys;
//    }
//
//    public void setYs(String ys) {
//        this.ys = ys;
//    }
//
//    public String getClsbdh() {
//        return clsbdh;
//    }
//
//    public void setClsbdh(String clsbdh) {
//        this.clsbdh = clsbdh;
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
//    public String getFdjxlh() {
//        return fdjxlh;
//    }
//
//    public void setFdjxlh(String fdjxlh) {
//        this.fdjxlh = fdjxlh;
//    }
//
//    public String getSfyyc() {
//        return sfyyc;
//    }
//
//    public void setSfyyc(String sfyyc) {
//        this.sfyyc = sfyyc;
//    }
//
//    public String getHclx() {
//        return hclx;
//    }
//
//    public void setHclx(String hclx) {
//        this.hclx = hclx;
//    }
//
//    public String getClxx() {
//        return clxx;
//    }
//
//    public void setClxx(String clxx) {
//        this.clxx = clxx;
//    }
}
