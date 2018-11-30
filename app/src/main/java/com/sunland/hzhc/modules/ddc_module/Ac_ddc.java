package com.sunland.hzhc.modules.ddc_module;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.utils.JsonUtils;

import java.net.URLEncoder;

import butterknife.BindView;

public class Ac_ddc extends Ac_base_info {


    @BindView(R.id.hc_location)
    public TextView tv_hc_loaciton;
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
    @BindView(R.id.car_brand)
    public TextView tv_car_brand;
    @BindView(R.id.cjh)
    public TextView tv_cjh;
    @BindView(R.id.fdjh)
    public TextView tv_fdjh;
    @BindView(R.id.fdj_sequence)
    public TextView tv_fdj_sequence;
    private String cz_sfzh;
    private String czxm;
    private String hphm;
    private String clpp;
    private String cjh;
    private String fdjh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_ddc);
        initView();
    }


    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                cz_sfzh = bundle.getString("cz_sfzh");
                czxm = bundle.getString("czxm");
                hphm = bundle.getString("hphm");
                clpp = bundle.getString("clpp");
                cjh = bundle.getString("cjh");
                fdjh = bundle.getString("fdjh");
            }
        }
    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyTaskParams taskParams = new MyTaskParams();
                    taskParams.putHead("jySfzh", "330103199107170010");
                    taskParams.putHead("jyXm", URLEncoder.encode("华晓阳", "UTF-8"));
                    taskParams.putHead("jyBmbh", "330100230500");//330100230500;04E5E90AFFD64035B700F6B62D772E2E
                    taskParams.putHead("version", Global.VERSION_URL);
                    taskParams.putEntity("sfzh", cz_sfzh);
                    final String result = QueryHttp.post(Global.PERSON_CHECK_QGZT_URL, taskParams);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LmhcResBean lmhcResBean = JsonUtils.fromJson(result, LmhcResBean.class);
                            tv_road_check.setText(lmhcResBean.getMessage());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        tv_id_num.setText(cz_sfzh);
        tv_name.setText(czxm);
        tv_plateform_num.setText(hphm);
        tv_car_brand.setText(clpp);
        tv_cjh.setText(cjh);
        tv_fdjh.setText(fdjh);
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case Dictionary.GET_ELECTRIC_CAR_INFO:
                DdcxxplReqBean bean = new DdcxxplReqBean();
                assembleBasicObj(bean);
                bean.setHphm(hphm);
                bean.setFdjh(fdjh);
                bean.setCjh(cjh);
                bean.setCurrentPage(1);
                bean.setTotalCount(2);
                return bean;
            case Dictionary.GET_ELECTRIC_CAR_FOCUS_INFO:
                EVehicleFocusReqBean eVehicleFocusReqBean = new EVehicleFocusReqBean();
                assembleBasicObj(eVehicleFocusReqBean);
                eVehicleFocusReqBean.setHphm("Y101549");
                eVehicleFocusReqBean.setCjh("");
                eVehicleFocusReqBean.setFdjh("");
                return eVehicleFocusReqBean;
        }
        return null;
    }


    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {

    }
}
