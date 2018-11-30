package com.sunland.hzhc.modules.jdc_module;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunland.hzhc.modules.jdc_module.bean.ClxxzhResponseBean;
import com.sunland.hzhc.modules.jdc_module.bean.InfoJDCXQs;
import com.sunlandgroup.def.bean.result.ResultBase;

import butterknife.BindView;

public class Ac_clcx extends Ac_base_info {

    private String cphm;
    private String hpzl;
    private String fdjh;
    private String clsbh;

    @BindView(R.id.hc_location)
    public TextView tv_location;
    @BindView(R.id.wanted)
    public TextView tv_wanted;
    @BindView(R.id.road_check)
    public TextView tv_road_check;
    @BindView(R.id.id_num)
    public TextView tv_id_num;
    @BindView(R.id.name)
    public TextView tv_name;
    @BindView(R.id.plateform_num)
    public TextView tv_plateform_num;
    @BindView(R.id.car_type)
    public TextView tv_car_type;
    @BindView(R.id.car_brand)
    public TextView tv_car_brand;
    @BindView(R.id.type_num)
    public TextView tv_type_num;
    @BindView(R.id.car_color)
    public TextView tv_car_color;
    @BindView(R.id.sbdm)
    public TextView tv_sbdm;
    @BindView(R.id.fdjh)
    public TextView tv_fdjh;
    @BindView(R.id.fdj_sequence)
    public TextView tv_fdj_sequence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_clcx);
        showNavIcon(true);
        queryYdjwData(Dictionary.CAR_INFO_JOIN);
    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                cphm = bundle.getString("cphm");
                hpzl = bundle.getString("hpzl");
                fdjh = bundle.getString("fdjh");
                clsbh = bundle.getString("clsbh");
            }
        }
    }


    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case Dictionary.CAR_INFO_JOIN:
                ClxxzhReqBean bean = new ClxxzhReqBean();
                assembleBasicObj(bean);
                CarInfoJoinDto carInfoJoinJson = new CarInfoJoinDto();
                carInfoJoinJson.setHphm(cphm);
                carInfoJoinJson.setClsbdh(clsbh);
                carInfoJoinJson.setFdjh(fdjh);
                carInfoJoinJson.setHpzl(hpzl);
                bean.setCarInfoJoinJson(carInfoJoinJson);
                bean.setPageNum(1);
                bean.setCount(10);
                return bean;
            case Dictionary.GET_CAR_INFO_BY_SFZH:
                CzsycReqBean czsycReqBean = new CzsycReqBean();
                assembleBasicObj(czsycReqBean);
                czsycReqBean.setSfzh("330226199312016717");
                return czsycReqBean;
            case Dictionary.CAR_FOCUS:
                VehicleFocusReqBean vehicleFocusReqBean = new VehicleFocusReqBean();
                assembleBasicObj(vehicleFocusReqBean);
                vehicleFocusReqBean.setCphm("浙BEW138");
                vehicleFocusReqBean.setHpzl("02");
                vehicleFocusReqBean.setSyr_sfzmhm("");
                return vehicleFocusReqBean;

        }

        return null;
    }


    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case Dictionary.CAR_INFO_JOIN:
                ClxxzhResponseBean resBean = (ClxxzhResponseBean) resultBase;
                InfoJDCXQs infoJDCXQs = resBean.getInfoJDCXQs().get(0);
                tv_id_num.setText(infoJDCXQs.getZjh());
                tv_name.setText(infoJDCXQs.getClsyr());
                tv_plateform_num.setText(infoJDCXQs.getCphm());
                tv_car_type.setText(infoJDCXQs.getCllx());
                tv_car_brand.setText(infoJDCXQs.getClpp());
                tv_type_num.setText(infoJDCXQs.getClxh());
                tv_car_color.setText(infoJDCXQs.getClys());
                tv_sbdm.setText(infoJDCXQs.getClsbdh());
                tv_fdjh.setText(infoJDCXQs.getFdjh());
                tv_fdj_sequence.setText(infoJDCXQs.getFdjxh());
                break;
        }
    }
}
