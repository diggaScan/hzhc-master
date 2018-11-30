package com.sunland.hzhc.modules.xmzh_module;

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
