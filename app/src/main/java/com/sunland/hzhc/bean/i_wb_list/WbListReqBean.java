package com.sunland.hzhc.bean.i_wb_list;

import com.sunland.hzhc.bean.BaseRequestBean;

public class WbListReqBean extends BaseRequestBean {
    private int currentPage;
    private int totalCount;

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
