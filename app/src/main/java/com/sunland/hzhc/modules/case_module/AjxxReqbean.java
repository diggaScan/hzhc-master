package com.sunland.hzhc.modules.case_module;

import com.sunland.hzhc.bean.BaseRequestBean;

public class AjxxReqbean extends BaseRequestBean {
    private CaseInfoDto caseInfoDto;
    private int currentPage;
    private int totalCount;

    public CaseInfoDto getCaseInfoDto() {
        return caseInfoDto;
    }

    public void setCaseInfoDto(CaseInfoDto caseInfoDto) {
        this.caseInfoDto = caseInfoDto;
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
