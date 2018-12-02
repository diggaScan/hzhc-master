package com.sunland.hzhc.bean.i_person_focus;

public class InfoGZXXResp {
    private String sj;//		发生时间
    private String dw;//	String	录入单位
    private String lb;//	String	数据来源类别
    private String nr;//String	内容
    private String bh;//String	编号
    private String status;//	String	0:服务异常 1：无记录2：有记录
    private String root;//String	身份证号码

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
