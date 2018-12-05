package com.sunland.hzhc.bean.ssjBean;

import com.sunland.hzhc.utils.UtilsString;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonInfo implements Serializable {
    private String gj = "";// 国籍
    private String zjlx = "";//证件类型
    private String xm = "";//姓名
    private String xb = "";//性别
    private String sfzh = "";//身份证号
    private String hjqh = "";//户籍区划
    private String ztry = "";//是否在逃人员
    private String bdgk = "";//先不传 是否本地管控
    private String zdry = "";//是否重点人员
    private String wffz = "";//是否违法犯罪
    private String xd = "";//先不传 是否吸毒
    private String gzdx = "";//先不传 是否工作对象
    private String mz = "";//民族
    private String returncode = "";//情报核录入接口返回代码
    private String hclx = "";//先不传 核查类型hclx 01安保 02 路面
    private String ryxx = "";//在逃接口+关注信息+情报接口 返回的描述信息
    private String sjhm = "";//先不传
    private ArrayList<String> zpList;//先不传

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public ArrayList<String> getZpList() {
        if (zpList == null)
            zpList = new ArrayList<>();
        return zpList;
    }

    public void setZpList(ArrayList<String> zpList) {
        this.zpList = zpList;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getXm() {
        return UtilsString.isEmpty(xm) ? "无" : xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb() {
        return UtilsString.isEmpty(xb) ? "无" : xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getSfzh() {
        return UtilsString.isEmpty(sfzh) ? "无" : sfzh;

    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getHjqh() {
        return hjqh;
    }

    public void setHjqh(String hjqh) {
        this.hjqh = hjqh;
    }

    public String getZtry() {
        return ztry;
    }

    public void setZtry(String ztry) {
        this.ztry = ztry;
    }

    public String getBdgk() {
        return bdgk;
    }

    public void setBdgk(String bdgk) {
        this.bdgk = bdgk;
    }

    public String getZdry() {
        return zdry;
    }

    public void setZdry(String zdry) {
        this.zdry = zdry;
    }

    public String getWffz() {
        return wffz;
    }

    public void setWffz(String wffz) {
        this.wffz = wffz;
    }

    public String getXd() {
        return xd;
    }

    public void setXd(String xd) {
        this.xd = xd;
    }

    public String getGzdx() {
        return gzdx;
    }

    public void setGzdx(String gzdx) {
        this.gzdx = gzdx;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getHclx() {
        return hclx;
    }

    public void setHclx(String hclx) {
        this.hclx = hclx;
    }

    public String getRyxx() {
        return ryxx;
    }

    public void setRyxx(String ryxx) {
        this.ryxx = ryxx;
    }
}
