package com.sunland.hzhc.bean.i_internet_cafe_people;

import com.sunland.hzhc.bean.BaseRequestBean;

public class SwryListReqBean extends BaseRequestBean {
    private String wbmc;
    private String zjbh;
    private String swsj_q;
    private String swsj_z;
    private int currentPage;
    private int totalCount;

    public String getWbmc() {
        return wbmc;
    }

    public void setWbmc(String wbmc) {
        this.wbmc = wbmc;
    }

    public String getZjbh() {
        return zjbh;
    }

    public void setZjbh(String zjbh) {
        this.zjbh = zjbh;
    }

    public String getSwsj_q() {
        return swsj_q;
    }

    public void setSwsj_q(String swsj_q) {
        this.swsj_q = swsj_q;
    }

    public String getSwsj_z() {
        return swsj_z;
    }

    public void setSwsj_z(String swsj_z) {
        this.swsj_z = swsj_z;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
