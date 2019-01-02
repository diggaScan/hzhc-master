package com.sunland.hzhc.bean.i_hotel_people_info;

import com.sunland.hzhc.bean.BaseRequestBean;

public class PllgrzReqBean extends BaseRequestBean {

    public String lgmc;
    public String fjh;
    public String rzrq_q;
    public String rerq_z;
    public int currentPage;
    public int totalCount;

    public String getLgmc() {
        return lgmc;
    }

    public void setLgmc(String lgmc) {
        this.lgmc = lgmc;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getRzrq_q() {
        return rzrq_q;
    }

    public void setRzrq_q(String rzrq_q) {
        this.rzrq_q = rzrq_q;
    }

    public String getRerq_z() {
        return rerq_z;
    }

    public void setRerq_z(String rerq_z) {
        this.rerq_z = rerq_z;
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
