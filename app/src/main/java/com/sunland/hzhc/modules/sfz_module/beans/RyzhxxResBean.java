package com.sunland.hzhc.modules.sfz_module.beans;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class RyzhxxResBean extends ResultBase {
    private String xm;//姓名
    private String xb;//性别
    private String mz;//民族
    private String jg;//籍贯
    private String csrq;//出生日期
    private String nl;//年龄
    private String hjdz;//户籍地址
    private String hjdz_djrq;//户籍登记日期
    private String hjqh;//户籍区划
    private String hjqh_code;//户籍区划代码
    private String sfzh;//身份证号
    private List<String> dh_list;//电话聚合信息
    private String zjcx;//准驾车型
    private String jsz_yxq;//驾驶证有效期
    private List<InfoRYXXForJDC> jdc_list;//机动车聚合信息
    private List<String> fjdc_list;//	非机动车聚合信息
    private List<XQInfoZZ> zzxx_list;//	暂住聚合信息
    private List<InfoFCXX> fcxx_list;//访查

    public List<InfoFCXX> getFcxx_list() {
        return fcxx_list;
    }

    public void setFcxx_list(List<InfoFCXX> fcxx_list) {
        this.fcxx_list = fcxx_list;
    }

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

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getNl() {
        return nl;
    }

    public void setNl(String nl) {
        this.nl = nl;
    }

    public String getHjdz() {
        return hjdz;
    }

    public void setHjdz(String hjdz) {
        this.hjdz = hjdz;
    }

    public String getHjdz_djrq() {
        return hjdz_djrq;
    }

    public void setHjdz_djrq(String hjdz_djrq) {
        this.hjdz_djrq = hjdz_djrq;
    }

    public String getHjqh() {
        return hjqh;
    }

    public void setHjqh(String hjqh) {
        this.hjqh = hjqh;
    }

    public String getHjqh_code() {
        return hjqh_code;
    }

    public void setHjqh_code(String hjqh_code) {
        this.hjqh_code = hjqh_code;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public List<String> getDh_list() {
        return dh_list;
    }

    public void setDh_list(List<String> dh_list) {
        this.dh_list = dh_list;
    }

    public String getZjcx() {
        return zjcx;
    }

    public void setZjcx(String zjcx) {
        this.zjcx = zjcx;
    }

    public String getJsz_yxq() {
        return jsz_yxq;
    }

    public void setJsz_yxq(String jsz_yxq) {
        this.jsz_yxq = jsz_yxq;
    }

    public List<InfoRYXXForJDC> getJdc_list() {
        return jdc_list;
    }

    public void setJdc_list(List<InfoRYXXForJDC> jdc_list) {
        this.jdc_list = jdc_list;
    }

    public List<String> getFjdc_list() {
        return fjdc_list;
    }

    public void setFjdc_list(List<String> fjdc_list) {
        this.fjdc_list = fjdc_list;
    }

    public List<XQInfoZZ> getZzxx_list() {
        return zzxx_list;
    }

    public void setZzxx_list(List<XQInfoZZ> zzxx_list) {
        this.zzxx_list = zzxx_list;
    }
}
