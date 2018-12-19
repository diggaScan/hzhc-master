package com.sunland.hzhc.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_inspect_car.InspectCarResBean;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.modules.Hotel_module.bean.LGResBean;
import com.sunland.hzhc.modules.Internet_cafe_module.bean.RyResBean;
import com.sunland.hzhc.modules.case_module.bean.CaseListResBean;
import com.sunland.hzhc.modules.jdc_module.bean.ClxxzhResponseBean;
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
//        mRequestManager.postRequestWithoutDialog();
    }

    // TODO: 2018/12/5/005 重载的方法要整合
    public void queryYdjwDataNoDialogTest(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequestWithoutDialog();
    }

    public void queryYdjwDataX(String method_name) {
        mRequestManager.postRequestWithoutDialog();
    }

    public abstract BaseRequestBean assembleRequestObj(String reqName);

    public void assembleBasicObj(BaseRequestBean baseRequestBean) {
        baseRequestBean.setYhdm("test");
        baseRequestBean.setImei(Global.imei);
        baseRequestBean.setImsi(Global.imsi1);
        baseRequestBean.setLbr("02");
        baseRequestBean.setGpsX("");
        baseRequestBean.setGpsY("");
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
            case V_config.CAR_INFO_JOIN:
                return ClxxzhResponseBean.class;
            case V_config.PERSON_COMPLEX:
                return RyzhxxResBean.class;
            case V_config.GET_PERSON_JOIN_INFO:
                return XmzhResBean.class;
            case V_config.GET_PERSON_IN_HOTEL_INFO:
                return LGResBean.class;
            case V_config.GET_INTERNET_CAFE_PERSON_INFO:
                return RyResBean.class;
            case V_config.CASE_INFO:
                return CaseListResBean.class;
            case V_config.GET_PERSON_MOBILE_JOIN_INFO:
                return RyPhoneResBean.class;
            case V_config.COUNTRY_PERSON:
                return PersonOfCountryJsonRet.class;
            case V_config.INSPECT_PERSON:
                return InspectPersonJsonRet.class;
            case V_config.INSPECT_CAR:
                return InspectCarResBean.class;
        }
        return null;
    }

    public abstract void onDataResponse(String reqId, String reqName, ResultBase resultBase);
}
