package com.sunland.hzhc.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_inspect_car.InspectCarResBean;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.modules.Hotel_module.bean.LGResBean;
import com.sunland.hzhc.modules.Internet_cafe_module.bean.RyResBean;
import com.sunland.hzhc.modules.case_module.bean.CaseListResBean;
import com.sunland.hzhc.modules.jdc_module.bean.ClxxzhResponseBean;
import com.sunland.hzhc.modules.own_car_module.own_car_bean.CarBaseInfo;
import com.sunland.hzhc.modules.phone_num_module.bean.RyPhoneResBean;
import com.sunland.hzhc.modules.sfz_module.beans.PersonOfCountryJsonRet;
import com.sunland.hzhc.modules.sfz_module.beans.RyzhxxResBean;
import com.sunland.hzhc.modules.xmzh_module.XmzhResBean;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Ac_base_info extends Ac_base implements OnRequestCallback {

    public RequestManager mRequestManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestManager = new RequestManager(this, this);
        handleIntent();
    }

    public abstract void handleIntent();

    public void queryYdjwData(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequest();
    }

    public void queryYdjwDataNoDialog(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequestWithoutDialog();
    }

    public abstract BaseRequestBean assembleRequestObj(String reqName);

    public void assembleBasicObj(BaseRequestBean baseRequestBean) {
        baseRequestBean.setYhdm("test");
        baseRequestBean.setImei(Global.imei);
        baseRequestBean.setImsi(Global.imsi1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pda_time = simpleDateFormat.format(date);
        baseRequestBean.setPdaTime(pda_time);
    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        onDataResponse(reqId, reqName, (ResultBase) bean);
    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        switch (reqName) {
            case Dictionary.CAR_INFO_JOIN:
                return ClxxzhResponseBean.class;
            case Dictionary.PERSON_COMPLEX:
                return RyzhxxResBean.class;
            case Dictionary.GET_PERSON_JOIN_INFO:
                return XmzhResBean.class;
            case Dictionary.GET_PERSON_IN_HOTEL_INFO:
                return LGResBean.class;
            case Dictionary.GET_INTERNET_CAFE_PERSON_INFO:
                return RyResBean.class;
            case Dictionary.CASE_INFO:
                return CaseListResBean.class;
            case Dictionary.GET_PERSON_MOBILE_JOIN_INFO:
                return RyPhoneResBean.class;
            case Dictionary.COUNTRY_PERSON:
                return PersonOfCountryJsonRet.class;
            case Dictionary.INSPECT_PERSON:
                return InspectPersonJsonRet.class;
            case Dictionary.INSPECT_CAR:
                return InspectCarResBean.class;
        }
        return null;
    }

    public abstract void onDataResponse(String reqId, String reqName, ResultBase resultBase);
}
