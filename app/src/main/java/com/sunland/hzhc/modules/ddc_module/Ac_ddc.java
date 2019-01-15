package com.sunland.hzhc.modules.ddc_module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.sunland.hzhc.Ac_location;
import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_country_people.CountryPersonReqBean;
import com.sunland.hzhc.bean.i_country_people.PersonOfCountryJsonRet;
import com.sunland.hzhc.bean.i_e_bike_focus.DdcFocus;
import com.sunland.hzhc.bean.i_e_bike_focus.EVehicleFocusReqBean;
import com.sunland.hzhc.bean.i_e_bike_focus.EVehicleFocusResBean;
import com.sunland.hzhc.bean.i_inspect_person.Dlxx;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonReqBean;
import com.sunland.hzhc.bean.i_inspect_person.Request;
import com.sunland.hzhc.bean.i_inspect_person.RyxxReq;
import com.sunland.hzhc.bean.i_inspect_person.RyxxRes;
import com.sunland.hzhc.bean.ssjB.NonVehicleInfo;
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
import java.util.List;

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
    @BindView(R.id.loading_icon_hc)
    public SpinKitView loading_hc;
    @BindView(R.id.loading_icon_zt)
    public SpinKitView loading_zt;
    @BindView(R.id.focus_str)
    public TextView tv_focus_str;
    @BindView(R.id.focus_road_check)
    public TextView tv_focus_road_check;
    @BindView(R.id.focus_loading_icon_hc)
    public SpinKitView sk_loading_icon;

    private String sfzh;
    private String czxm;
    private String hphm;
    private String clpp;
    private String cjh;
    private String fdjh;
    private String csys;

    private boolean isFromSsj;

    private boolean load_want;
    private boolean load_country_person;
    private boolean load_inspect_person;
    private boolean load_ddc_focus;
    private String check_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_ddc);
        showNavIcon(true);
        setToolbarTitle("电动车详情");
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!load_country_person) {
            queryYdjwDataNoDialog("COUNTRY_PERSON", V_config.COUNTRY_PERSON);
        }
        if (!load_inspect_person) {
            queryYdjwDataNoDialog("COUNTRY_PERSON", V_config.INSPECT_PERSON);
        }
        if (!load_ddc_focus) {
            queryYdjwDataNoDialog("GET_ELECTRIC_CAR_FOCUS_INFO", V_config.GET_ELECTRIC_CAR_FOCUS_INFO);
        }
        queryYdjwDataX();
        if (!load_want) {
            queryWanted();
        }
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
                csys = bundle.getString("csys");
                isFromSsj = bundle.getBoolean(DataModel.FROM_SSJ_FLAG, false);
            }
        }
    }

    private void initView() {
        tv_focus_str.setText("电动车关注信息");
        setText(tv_hc_loaciton, V_config.hc_address);
        setText(tv_id_num, sfzh);
        setText(tv_name, czxm);
        setText(tv_plateform_num, hphm);
        setText(tv_car_brand, clpp);
        setText(tv_cjh, cjh);
        setText(tv_fdjh, fdjh);
    }

    private void queryWanted() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyTaskParams taskParams = new MyTaskParams();
                    taskParams.putHead("jySfzh", V_config.JYSFZH);
                    taskParams.putHead("jyXm", URLEncoder.encode(V_config.JYXM, "UTF-8"));
                    taskParams.putHead("jyBmbh", V_config.JYBMBH);//330100230500;04E5E90AFFD64035B700F6B62D772E2E
                    taskParams.putHead("version", Global.VERSION_URL);
                    taskParams.putEntity("sfzh", sfzh);
                    final String result = QueryHttp.post(Global.PERSON_CHECK_QGZT_URL, taskParams);
                    //-1 查无结果,2100再逃
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading_zt.setVisibility(View.GONE);
                            LmhcResBean lmhcResBean = JsonUtils.fromJson(result, LmhcResBean.class);
                            if (lmhcResBean == null) {
                                tv_wanted.setText(Html.fromHtml("<font color=\"#FF7F50\">全国在逃接口异常</font>"));
                                return;
                            }
                            load_want = true;
                            tv_wanted.setText(lmhcResBean.getMessage());

                            if (lmhcResBean.getStatus().equals("2100")) {
                                tv_wanted.setTextColor(getResources().getColor(R.color.warning_color));
                                startVibrate();
                            } else {
                                tv_wanted.setTextColor(getResources().getColor(R.color.non_warning_color));
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_ELECTRIC_CAR_FOCUS_INFO:
                EVehicleFocusReqBean eVehicleFocusReqBean = new EVehicleFocusReqBean();
                assembleBasicRequest(eVehicleFocusReqBean);
                if (isFromSsj) {
                    eVehicleFocusReqBean.setLbr(V_config.ssjBbh);
                }
                eVehicleFocusReqBean.setHphm(hphm);
                eVehicleFocusReqBean.setCjh(cjh);
                eVehicleFocusReqBean.setFdjh(fdjh);
                return eVehicleFocusReqBean;
            case V_config.COUNTRY_PERSON:
                CountryPersonReqBean countryPersonReqBean = new CountryPersonReqBean();
                assembleBasicRequest(countryPersonReqBean);
                if (isFromSsj) {
                    countryPersonReqBean.setLbr(V_config.ssjBbh);
                }
                countryPersonReqBean.setSfzh(sfzh);
                return countryPersonReqBean;
            case V_config.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicRequest(inspectPersonReqBean);
                if (isFromSsj) {
                    inspectPersonReqBean.setLbr(V_config.ssjBbh);
                }
                Request request = new Request();
                request.setLrb(V_config.LBR);
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
                load_country_person = true;
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

                loading_hc.setVisibility(View.GONE);
                InspectPersonJsonRet inspectPersonJsonRet = (InspectPersonJsonRet) resultBase;
                if (inspectPersonJsonRet == null) {
                    Toast.makeText(this, "杭州人核查接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                load_inspect_person = true;
                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes == null) {
                    tv_road_check.setText(Html.fromHtml("人核查接口:" + "<font color=\"#FF7F50\">" + inspectPersonJsonRet.getMessage() + "</font>"));
                    return;
                }


                if ("通过".equals(ryxxRes.getHcjg())) {
                    check_result = "<font color=\"#05b163\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                } else {
                    check_result = "<font color=\"#d13931\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                    startVibrate();
                }
                tv_road_check.setText(Html.fromHtml(check_result));
                break;

            case V_config.GET_ELECTRIC_CAR_FOCUS_INFO:
                sk_loading_icon.setVisibility(View.GONE);
                EVehicleFocusResBean eVehicleFocusResBean = (EVehicleFocusResBean) resultBase;
                if (eVehicleFocusResBean == null) {
                    Toast.makeText(this, "电动车关注信息接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<DdcFocus> list = eVehicleFocusResBean.getDdcList();
                if (list == null || list.isEmpty()) {
                    tv_focus_road_check.setText(Html.fromHtml("<font color=\"#05b163\">" + eVehicleFocusResBean.getMessage() + "</font>"));
                    return;
                }
                load_ddc_focus = true;
                StringBuilder sb = new StringBuilder();
                for (DdcFocus ddcFocus : list) {
                    if (ddcFocus.getStatus().equals("2")) {
                        sb.append(ddcFocus.getLb());
                        if (ddcFocus.getNr() != null) {
                            sb.append(": ").append(ddcFocus.getNr());
                        } else {
                            sb.append(": ").append("无");
                        }
                    }

                }
                if (sb.toString().isEmpty()) {
                    tv_focus_road_check.setText(Html.fromHtml("<font color=\"#05b163\"> 无相关记录</font>"));
                } else {
                    tv_focus_road_check.setText(Html.fromHtml(sb.toString()));
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

    @OnClick({R.id.ddc_check, R.id.focus, R.id.retry, R.id.location_container, R.id.focus_retry})
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
            case R.id.location_container:
                bundle.putInt("req_location", V_config.REQ_LOCATION);
                hop2ActivityForResult(Ac_location.class, bundle, V_config.REQ_LOCATION);
                break;
            case R.id.retry:
                tv_road_check.setText("");
                loading_hc.setVisibility(View.VISIBLE);
                queryYdjwDataNoDialog("INSPECT_PERSON", V_config.INSPECT_PERSON);
                queryYdjwDataX();
                break;
            case R.id.focus_retry:
                tv_focus_road_check.setText("");
                sk_loading_icon.setVisibility(View.VISIBLE);
                queryYdjwDataNoDialog("GET_ELECTRIC_CAR_FOCUS_INFO", V_config.GET_ELECTRIC_CAR_FOCUS_INFO);
                queryYdjwDataX();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ssj_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.ssj:
                Intent intent = new Intent();
                NonVehicleInfo nonVehicleInfo = new NonVehicleInfo();
                initNonVehicleInfo(nonVehicleInfo);
                if (isFromSsj) {
                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 2);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(nonVehicleInfo));
                    bundle.putString("yhdm", V_config.YHDM);
                    bundle.putString("sfz", V_config.JYSFZH);
                    bundle.putString("xm", V_config.JYXM);
                    bundle.putString("bmdm", V_config.JYBMBH);
                    bundle.putString("lx", V_config.gpsX);
                    bundle.putString("ly", V_config.gpsY);
                    bundle.putBoolean("isFromSsj", isFromSsj);
                    bundle.putBoolean("fromHc", true);
                    intent.putExtra("bundle", bundle);
                    hop2ActivitySingle(Ac_main.class, bundle);

//                    setResult(RESULT_OK, intent);
//                    finish();
                } else {
                    intent.setAction("com.sunland.action.record");
                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 2);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(nonVehicleInfo));
                    bundle.putString("yhdm", V_config.YHDM);
                    bundle.putString("sfz", V_config.JYSFZH);
                    bundle.putString("xm", V_config.JYXM);
                    bundle.putString("bmdm", V_config.JYBMBH);
                    bundle.putString("lx", V_config.gpsX);
                    bundle.putString("ly", V_config.gpsY);
                    bundle.putBoolean("fromHc", true);
                    intent.putExtras(bundle);
                    if (intent.resolveActivity(getPackageManager()) == null) {
                        Toast.makeText(this, "请安装随手记应用", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(intent);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initNonVehicleInfo(NonVehicleInfo nonVehicleInfo) {
        nonVehicleInfo.setClph(hphm);
        nonVehicleInfo.setDdjh(fdjh);
        nonVehicleInfo.setPp(clpp);
//        nonVehicleInfo.setXh();//无电动车信息该字段
        nonVehicleInfo.setCsys(csys);
//        nonVehicleInfo.setTz();
        nonVehicleInfo.setJsyname(czxm);
        nonVehicleInfo.setJsysfzh(sfzh);
//        nonVehicleInfo.setHplb();
        nonVehicleInfo.setCjh(cjh);
//        nonVehicleInfo.setCsysdm();

    }

}
