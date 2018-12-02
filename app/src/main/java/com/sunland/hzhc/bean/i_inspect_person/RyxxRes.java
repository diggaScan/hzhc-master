package com.sunland.hzhc.bean.i_inspect_person;

public class RyxxRes {
    /**
     * ryxxReq	人员信息
     * fhm	返回码	代码项，000：正常，101：异常
     * fhmbc	返回码备注	返回码备注
     * zj	主键	人员主键
     * jnjw	境内境外	代码项，01：境内，02：境外
     * xm	姓名	姓名
     * xb	性别	代码项，1：男，2：女
     * csrq	出生日期	出生日期，格式为
     * “YYYY-MM-DD”
     * mz	民族	代码项，详见第4章B04
     * hjdqh	户籍地区	代码项，JNJW为01（境内）时，详见第4章B05;JNJW为02（境外）时，详见第4章B06
     * zjlx	证件类型	代码项，详见第4章B07
     * zjhm	证件号码	证件号码
     * zp	人员照片	人员照片
     * sjhm	手机号码	手机号码
     * sfsj	是否司机	代码项,0表示否，1表示是
     * hcjg	核查结果	核查结果
     * rylb	人员类别	人员类别
     * bjxx	背景信息	背景信息
     */
    private String fhmbz;
    private String rylb;
    private String bjxx;
    private String xm;
    private String sjhm;
    private String mz;
    private String hjdqh;
    private String jnjw;
    private String hcjg;
    private String xb;
    private String csrq;
    private String zjlx;
    private String zjhm;
    private String zp;
    private String fhm;
    private String zj;
    private String sfsj;

    public String getFhmbz() {
        return fhmbz;
    }

    public void setFhmbz(String fhmbz) {
        this.fhmbz = fhmbz;
    }

    public String getRylb() {
        return rylb;
    }

    public void setRylb(String rylb) {
        this.rylb = rylb;
    }

    public String getBjxx() {
        return bjxx;
    }

    public void setBjxx(String bjxx) {
        this.bjxx = bjxx;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getHjdqh() {
        return hjdqh;
    }

    public void setHjdqh(String hjdqh) {
        this.hjdqh = hjdqh;
    }

    public String getJnjw() {
        return jnjw;
    }

    public void setJnjw(String jnjw) {
        this.jnjw = jnjw;
    }

    public String getHcjg() {
        return hcjg;
    }

    public void setHcjg(String hcjg) {
        this.hcjg = hcjg;
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

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getZp() {
        return zp;
    }

    public void setZp(String zp) {
        this.zp = zp;
    }

    public String getFhm() {
        return fhm;
    }

    public void setFhm(String fhm) {
        this.fhm = fhm;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public String getSfsj() {
        return sfsj;
    }

    public void setSfsj(String sfsj) {
        this.sfsj = sfsj;
    }
}
