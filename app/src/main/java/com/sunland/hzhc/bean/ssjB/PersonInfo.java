package com.sunland.hzhc.bean.ssjB;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonInfo implements Serializable {
    private String sfzh = "";//	当事人身份证号
    private String name = "";//当事人姓名
    private String xb = "";// 当事人性别
    private String csrq = "";//当事人出生日期
    private String hksx = "";// 当事人户籍省县
    private String hkxz = "";// 当事人户口详址
    private String zzsx = "";// 当事人暂住省县
    private String zzdz = "";// 当事人暂住地址
    private String dhhm = "";// 当事人电话号码
    private String ztry = "";//是否在逃人员  0否 ，1是
    private String qk = "";// 当事人前科情况（背景信息）
    private String cljg = "";//当事人处理结果  带回 放行
    private ArrayList<String> zpList;//照片 先不传

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getHksx() {
        return hksx;
    }

    public void setHksx(String hksx) {
        this.hksx = hksx;
    }

    public String getHkxz() {
        return hkxz;
    }

    public void setHkxz(String hkxz) {
        this.hkxz = hkxz;
    }

    public String getZzsx() {
        return zzsx;
    }

    public void setZzsx(String zzsx) {
        this.zzsx = zzsx;
    }

    public String getZzdz() {
        return zzdz;
    }

    public void setZzdz(String zzdz) {
        this.zzdz = zzdz;
    }

    public String getDhhm() {
        return dhhm;
    }

    public void setDhhm(String dhhm) {
        this.dhhm = dhhm;
    }

    public String getZtry() {
        return ztry;
    }

    public void setZtry(String ztry) {
        this.ztry = ztry;
    }

    public String getQk() {
        return qk;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }

    public String getCljg() {
        return cljg;
    }

    public void setCljg(String cljg) {
        this.cljg = cljg;
    }

    public ArrayList<String> getZpList() {
        if (zpList == null)
            zpList = new ArrayList<>();
        return zpList;
    }

    public void setZpList(ArrayList<String> zpList) {
        this.zpList = zpList;
    }

//    private String gj = "";// 国籍
//    private String zjlx = "";//证件类型
//    private String xm = "";//姓名
//    private String xb = "";//性别
//    private String sfzh = "";//身份证号
//    private String hjqh = "";//户籍区划
//    private String ztry = "";//是否在逃人员
//    private String bdgk = "";//是否本地管控
//    private String zdry = "";//是否重点人员
//    private String wffz = "";//是否违法犯罪
//    private String xd = "";//是否吸毒
//    private String gzdx = "";//是否工作对象
//    private String mz = "";//民族
//    private String returncode = "";//情报核录入接口返回代码
//    private String hclx = "";//核查类型hclx 01安保 02 路面
//    private String ryxx = "";//在逃接口+关注信息+情报接口 返回的描述信息
//    private String sjhm = "";
//    private ArrayList<String> zpList;
//
//    public String getSjhm() {
//        return sjhm;
//    }
//
//    public void setSjhm(String sjhm) {
//        this.sjhm = sjhm;
//    }
//
//    public ArrayList<String> getZpList() {
//        if (zpList == null)
//            zpList = new ArrayList<>();
//        return zpList;
//    }
//
//    public void setZpList(ArrayList<String> zpList) {
//        this.zpList = zpList;
//    }
//
//    public String getGj() {
//        return gj;
//    }
//
//    public void setGj(String gj) {
//        this.gj = gj;
//    }
//
//    public String getZjlx() {
//        return zjlx;
//    }
//
//    public void setZjlx(String zjlx) {
//        this.zjlx = zjlx;
//    }
//
//    public String getXm() {
//        return StringUtils.isEmpty(xm) ? "无" : xm;
//    }
//
//    public void setXm(String xm) {
//        this.xm = xm;
//    }
//
//    public String getXb() {
//        return StringUtils.isEmpty(xb) ? "无" : xb;
//    }
//
//    public void setXb(String xb) {
//        this.xb = xb;
//    }
//
//    public String getSfzh() {
//        return StringUtils.isEmpty(sfzh) ? "无" : sfzh;
//
//    }
//
//    public void setSfzh(String sfzh) {
//        this.sfzh = sfzh;
//    }
//
//    public String getHjqh() {
//        return hjqh;
//    }
//
//    public void setHjqh(String hjqh) {
//        this.hjqh = hjqh;
//    }
//
//    public String getZtry() {
//        return ztry;
//    }
//
//    public void setZtry(String ztry) {
//        this.ztry = ztry;
//    }
//
//    public String getBdgk() {
//        return bdgk;
//    }
//
//    public void setBdgk(String bdgk) {
//        this.bdgk = bdgk;
//    }
//
//    public String getZdry() {
//        return zdry;
//    }
//
//    public void setZdry(String zdry) {
//        this.zdry = zdry;
//    }
//
//    public String getWffz() {
//        return wffz;
//    }
//
//    public void setWffz(String wffz) {
//        this.wffz = wffz;
//    }
//
//    public String getXd() {
//        return xd;
//    }
//
//    public void setXd(String xd) {
//        this.xd = xd;
//    }
//
//    public String getGzdx() {
//        return gzdx;
//    }
//
//    public void setGzdx(String gzdx) {
//        this.gzdx = gzdx;
//    }
//
//    public String getMz() {
//        return mz;
//    }
//
//    public void setMz(String mz) {
//        this.mz = mz;
//    }
//
//    public String getReturncode() {
//        return returncode;
//    }
//
//    public void setReturncode(String returncode) {
//        this.returncode = returncode;
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
//    public String getRyxx() {
//        return ryxx;
//    }
//
//    public void setRyxx(String ryxx) {
//        this.ryxx = ryxx;
//    }
}
