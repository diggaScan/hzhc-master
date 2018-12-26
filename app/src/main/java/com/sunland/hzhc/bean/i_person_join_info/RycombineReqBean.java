package com.sunland.hzhc.bean.i_person_join_info;

import com.sunland.hzhc.bean.BaseRequestBean;

public class RycombineReqBean extends BaseRequestBean {
    private PersonJoinDto personJoinDto;
    private int currentPage;//第几页  无默认值
    private int totalCount;
    ;//每页显示条数 无默认值


    public PersonJoinDto getPersonJoinDto() {
        return personJoinDto;
    }

    public void setPersonJoinDto(PersonJoinDto personJoinDto) {
        this.personJoinDto = personJoinDto;
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
