package com.sunland.hzhc.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_an_bao_info.AnBaoResBean;
import com.sunland.hzhc.bean.i_car_focus.VehicleFocusResBean;
import com.sunland.hzhc.bean.i_car_info_joint.ClxxzhResponseBean;
import com.sunland.hzhc.bean.i_case_cate.CaseCateResBean;
import com.sunland.hzhc.bean.i_case_info.CaseListResBean;
import com.sunland.hzhc.bean.i_charge_case.DwListResBean;
import com.sunland.hzhc.bean.i_country_people.PersonOfCountryJsonRet;
import com.sunland.hzhc.bean.i_e_bike_focus.EVehicleFocusResBean;
import com.sunland.hzhc.bean.i_e_bike_info.DdcListResBean;
import com.sunland.hzhc.bean.i_hotel_names.LgLbResBean;
import com.sunland.hzhc.bean.i_hotel_people_info.LGResBean;
import com.sunland.hzhc.bean.i_inspect_car.InspectCarResBean;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_internet_cafe_people.RyResBean;
import com.sunland.hzhc.bean.i_mobile_join.RyPhoneResBean;
import com.sunland.hzhc.bean.i_owned_car.OwnedCarResBean;
import com.sunland.hzhc.bean.i_people_complex.RyzhxxResBean;
import com.sunland.hzhc.bean.i_person_focus.PeopleFocusResBean;
import com.sunland.hzhc.bean.i_person_join_info.XmzhResBean;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfoResBean;
import com.sunland.hzhc.bean.i_wb_list.WbListResBean;
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

    public void queryYdjwData(String id, String method_name) {
        mRequestManager.addRequest(id, Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequest();
    }

    public void queryYdjwDataNoDialog(String id, String method_name) {
        mRequestManager.addRequest(id, Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
//        mRequestManager.postRequestWithoutDialog();
    }

    public void queryYdjwDataSingle(String id, String method_name) {
        mRequestManager.newRequest(id, Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000).postRequest();
    }

    // TODO: 2018/12/5/005 重载的方法要整合
    public void queryYdjwDataNoDialogTest(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequestWithoutDialog();
    }

    public void queryYdjwDataX() {
        mRequestManager.postRequestWithoutDialog();
    }

    public abstract BaseRequestBean assembleRequestObj(String reqName);

    public void assembleBasicRequest(BaseRequestBean requestBean) {
        requestBean.setYhdm(V_config.YHDM);
        requestBean.setImei(V_config.imei);
        requestBean.setImsi(V_config.imsi1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pda_time = simpleDateFormat.format(date);
        requestBean.setPdaTime(pda_time);
        requestBean.setGpsX(V_config.gpsX);
        requestBean.setGpsY(V_config.gpsY);
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
            case V_config.GET_ELECTRIC_CAR_INFO:
                return DdcListResBean.class;
            case V_config.QUERY_ALL_CASE_CATEGORY:
                return CaseCateResBean.class;
            case V_config.GET_CAR_INFO_BY_SFZH:
                return OwnedCarResBean.class;
            case V_config.SUBWAY_INFO:
                return SubwayInfoResBean.class;
            case V_config.GET_ALL_HOTELS:
                return LgLbResBean.class;
            case V_config.GET_INTERNET_CAFE_INFO:
                return WbListResBean.class;
            case V_config.QUERY_ALL_CASE_INFO:
                return DwListResBean.class;
            case V_config.GET_AN_BAO_INFO:
                return AnBaoResBean.class;
//            case V_config.GET_ELECTRIC_CAR_FOCUS_INFO:
//                return EVehicleFocusResBean.class;
//            case V_config.CAR_FOCUS:
//                return VehicleFocusResBean.class;
//            case V_config.PERSON_FOCUS_INFO:
//                return PeopleFocusResBean.class;
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestManager != null) {
            mRequestManager.cancelAll();
        }
    }

    public abstract void onDataResponse(String reqId, String reqName, ResultBase resultBase);
}
