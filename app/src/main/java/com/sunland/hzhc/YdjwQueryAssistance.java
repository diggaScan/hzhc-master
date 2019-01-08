package com.sunland.hzhc;

import com.sunland.hzhc.bean.BaseRequestBean;

public interface YdjwQueryAssistance<T> {
    BaseRequestBean assembleRequestObj(String reqName);

//    void onDataResponse(String reqId, String reqName, T bean);

}
