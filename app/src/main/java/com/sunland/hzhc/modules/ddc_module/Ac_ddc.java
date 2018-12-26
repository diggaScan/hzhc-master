package com.sunland.hzhc.modules.ddc_module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_country_people.CountryPersonReqBean;
import com.sunland.hzhc.bean.i_country_people.PersonOfCountryJsonRet;
import com.sunland.hzhc.bean.i_e_bike_focus.EVehicleFocusReqBean;
import com.sunland.hzhc.bean.i_e_bike_info.DdcxxplReqBean;
import com.sunland.hzhc.bean.i_inspect_person.Dlxx;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonReqBean;
import com.sunland.hzhc.bean.i_inspect_person.Request;
import com.sunland.hzhc.bean.i_inspect_person.RyxxReq;
import com.sunland.hzhc.bean.i_inspect_person.RyxxRes;
import com.sunland.hzhc.bean.ssjBean.NonVehicleInfo;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunland.hzhc.modules.p_archive_module.Ac_archive;
import com.sunland.hzhc.modules.sfz_module.Ac_rycx;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.utils.JsonUtils;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.xp)
    public ImageView iv_xp;

    private String sfzh;
    private String czxm;
    private String hphm;
    private String clpp;
    private String cjh;
    private String fdjh;

    private boolean isFromSsj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_ddc);
        showNavIcon(true);
        setToolbarTitle("电动车详情");
        showLoading_layout(true);
        queryYdjwDataNoDialog(V_config.COUNTRY_PERSON);
        queryYdjwDataNoDialog(V_config.INSPECT_PERSON);
        queryYdjwDataX("");
        initView();
    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                sfzh = bundle.getString("cz_sfzh");
                czxm = bundle.getString("czxm");
                hphm = bundle.getString("hphm");
                clpp = bundle.getString("clpp");
                cjh = bundle.getString("cjh");
                fdjh = bundle.getString("fdjh");
                isFromSsj = bundle.getBoolean(DataModel.FROM_SSJ_FLAG, false);
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
                    taskParams.putEntity("sfzh", sfzh);
                    final String result = QueryHttp.post(Global.PERSON_CHECK_QGZT_URL, taskParams);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LmhcResBean lmhcResBean = JsonUtils.fromJson(result, LmhcResBean.class);
                            tv_wanted.setText(lmhcResBean.getMessage());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        setText(tv_hc_loaciton, V_config.hc_address);
        setText(tv_id_num, sfzh);
        setText(tv_name, czxm);
        setText(tv_plateform_num, hphm);
        setText(tv_car_brand, clpp);
        setText(tv_cjh, cjh);
        setText(tv_fdjh, fdjh);
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_ELECTRIC_CAR_INFO:
                DdcxxplReqBean bean = new DdcxxplReqBean();
                assembleBasicRequest(bean);
                bean.setHphm(hphm);
                bean.setFdjh(fdjh);
                bean.setCjh(cjh);
                bean.setCurrentPage(1);
                bean.setTotalCount(2);
                return bean;
            case V_config.GET_ELECTRIC_CAR_FOCUS_INFO:
                EVehicleFocusReqBean eVehicleFocusReqBean = new EVehicleFocusReqBean();
                assembleBasicRequest(eVehicleFocusReqBean);
                eVehicleFocusReqBean.setHphm("Y101549");
                eVehicleFocusReqBean.setCjh("");
                eVehicleFocusReqBean.setFdjh("");
                return eVehicleFocusReqBean;
            case V_config.COUNTRY_PERSON:
                CountryPersonReqBean countryPersonReqBean = new CountryPersonReqBean();
                assembleBasicRequest(countryPersonReqBean);
                countryPersonReqBean.setSfzh(sfzh);
                return countryPersonReqBean;
            case V_config.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicRequest(inspectPersonReqBean);
                Request request = new Request();
                inspectPersonReqBean.setYhdm("115576");
                Dlxx dlxx = new Dlxx();
                dlxx.setHCDZ(V_config.hc_address);
                request.setDlxx(dlxx);
                RyxxReq ryxxReq = new RyxxReq();
                ryxxReq.setJNJW("01");
                ryxxReq.setZJLX("10");
                ryxxReq.setZJHM(sfzh);
                ryxxReq.setSJHM("");
                request.setRyxxReq(ryxxReq);
                inspectPersonReqBean.setRequest(request);
                return inspectPersonReqBean;
        }
        return null;
    }


    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.COUNTRY_PERSON:
                PersonOfCountryJsonRet personOfCountry = (PersonOfCountryJsonRet) resultBase;
                if (personOfCountry == null) {
                    Toast.makeText(this, "全国库人员信息查询异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String xp = personOfCountry.getXP();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (xp != null) {
                            byte[] bitmapArray = Base64.decode(xp, Base64.DEFAULT);
                            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iv_xp.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }
                }).start();
                break;
            case V_config.INSPECT_PERSON:
                showLoading_layout(false);
                InspectPersonJsonRet inspectPersonJsonRet = (InspectPersonJsonRet) resultBase;
                if (inspectPersonJsonRet == null) {
                    Toast.makeText(this, "杭州人核查接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes != null) {
                    tv_road_check.setText(ryxxRes.getHcjg() + " " + ryxxRes.getBjxx());
//                    if (ryxxRes.getFhm().equals("000")) {
//                        tv_road_check.setTextColor(Color.GREEN);
//                    } else {
//                        tv_road_check.setTextColor(Color.RED);
//                    }
                }
                break;
        }
    }

    public void setText(TextView textView, String content) {
        if (content == null || content.isEmpty()) {
            textView.setText("无");
        } else {
            textView.setText(content);
        }
    }

    @OnClick({R.id.ddc_check, R.id.focus, R.id.ssj})
    public void onClick(View view) {
        int id = view.getId();
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.ddc_check:
                bundle.putString("id", sfzh);
                hop2Activity(Ac_rycx.class, bundle);
                break;
            case R.id.focus:
                bundle.putString("id", sfzh);
                bundle.putInt("tab_id", 1);
                hop2Activity(Ac_archive.class, bundle);
                break;
            case R.id.ssj:
                Intent intent = new Intent();
                NonVehicleInfo nonVehicleInfo = new NonVehicleInfo();
                initNonVehicleInfo(nonVehicleInfo);
                if (isFromSsj) {
                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 2);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(nonVehicleInfo));
                    intent.putExtra("bundle", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    intent.setAction("com.sunland.action.record");
                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 2);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(nonVehicleInfo));
                    intent.putExtras(bundle);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    private void initNonVehicleInfo(NonVehicleInfo nonVehicleInfo) {
        nonVehicleInfo.setCph(hphm);
        nonVehicleInfo.setSfzh(sfzh);
        nonVehicleInfo.setXm(czxm);
        nonVehicleInfo.setPp(clpp);
        nonVehicleInfo.setCjh(cjh);
        nonVehicleInfo.setFdjh(fdjh);
//        nonVehicleInfo.setFjdcxlh(fjdcxlh);无该参数字段

    }

}
