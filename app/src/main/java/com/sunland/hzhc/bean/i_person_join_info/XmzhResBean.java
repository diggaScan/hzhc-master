package com.sunland.hzhc.bean.i_person_join_info;

import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

public class XmzhResBean extends ResultBase {
    private List<PersonInfo> persionList;

    public List<PersonInfo> getPersonList() {
        return persionList;
    }

    public void setPersonList(List<PersonInfo> personList) {
        this.persionList = personList;
    }
}
