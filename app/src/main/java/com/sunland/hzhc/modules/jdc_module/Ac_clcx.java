package com.sunland.hzhc.modules.jdc_module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.sunland.hzhc.bean.i_car_focus.CarFocus_info;
import com.sunland.hzhc.bean.i_car_focus.VehicleFocusReqBean;
import com.sunland.hzhc.bean.i_car_focus.VehicleFocusResBean;
import com.sunland.hzhc.bean.i_car_info_joint.CarInfoJoinDto;
import com.sunland.hzhc.bean.i_car_info_joint.ClxxzhReqBean;
import com.sunland.hzhc.bean.i_car_info_joint.ClxxzhResponseBean;
import com.sunland.hzhc.bean.i_car_info_joint.InfoJDCXQs;
import com.sunland.hzhc.bean.i_country_people.CountryPersonReqBean;
import com.sunland.hzhc.bean.i_country_people.PersonOfCountryJsonRet;
import com.sunland.hzhc.bean.i_inspect_car.CLxxRes;
import com.sunland.hzhc.bean.i_inspect_car.Clxx;
import com.sunland.hzhc.bean.i_inspect_car.Dlxx;
import com.sunland.hzhc.bean.i_inspect_car.InspectCarReqBean;
import com.sunland.hzhc.bean.i_inspect_car.InspectCarResBean;
import com.sunland.hzhc.bean.i_inspect_car.Request;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonReqBean;
import com.sunland.hzhc.bean.i_inspect_person.RyxxReq;
import com.sunland.hzhc.bean.i_inspect_person.RyxxRes;
import com.sunland.hzhc.bean.ssjB.VehicleInfo;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunland.hzhc.modules.p_archive_module.Ac_archive;
import com.sunland.hzhc.modules.sfz_module.Ac_rycx;
import com.sunland.hzhc.utils.UtilsString;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.utils.JsonUtils;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_clcx extends Ac_base_info {

    //由查询界面传入的字段
    private String cphm;
    private String hpzl;
    private String fdjh;
    private String clsbh;

    @BindView(R.id.hc_location)
    public TextView tv_hc_location;
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
    @BindView(R.id.xp)
    public ImageView iv_xp;
    @BindView(R.id.sfzh_check)
    public Button btn_sfzh;
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
    @BindView(R.id.access_deny_info)
    public TextView access_deny_info;
    @BindView(R.id.info_container)
    public LinearLayout ll_container;

    private String sfzh;
    private boolean isSfzh;
    private boolean isFromssj;

    private boolean isPoliceinfo;
    private String clsyr = "";//车辆所有人
    private String pp = "";//品牌
    private String xh = "";//型号
    private String cllx_str;//车辆类型中文
    private String ys = "";//颜色
    private String fdjxlh = "";//发动机序列号
    private String sffa;//是否涉案 0未涉案 1涉案

    private String hclx = "";//核查类型  01安保 02 路面
    private StringBuilder wfqk = new StringBuilder();//盗抢信息+关注信息+情报接口返回的描述信息

    private boolean load_car_info;
    private boolean load_car_focus;
    private boolean load_inspect_car;
    private boolean load_country_person;
    private boolean load_wanted;
    private boolean load_inspect_person;

    private String result_car_inspect;
    private String result_person_inspect;
    private int inspect_time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_clcx);
        showNavIcon(true);
        setToolbarTitle("机动车信息");
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!load_car_info) {
            showLoading_layout(true);
            queryYdjwDataNoDialog("CAR_INFO_JOIN", V_config.CAR_INFO_JOIN);
        }
        if (!load_car_focus) {
            queryYdjwDataNoDialog("CAR_FOCUS", V_config.CAR_FOCUS);
        }
        queryYdjwDataX();
    }

    public void showWarningIfPolice(String code) {
        isPoliceinfo = true;
        invalidateOptionsMenu();
        ll_container.setVisibility(View.GONE);
        access_deny_info.setVisibility(View.VISIBLE);
        if (code.equals("7")) {
            access_deny_info.setText("警员信息，无权限查询！");
        } else if (code.equals("6")) {
            access_deny_info.setText("无权限查询！");
        }

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
                isFromssj = bundle.getBoolean(DataModel.FROM_SSJ_FLAG, false);
            }
        }
    }

    private void initView() {
        tv_hc_location.setText(V_config.hc_address);
        tv_focus_str.setText("车辆关注信息");
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.CAR_INFO_JOIN:
                ClxxzhReqBean bean = new ClxxzhReqBean();
                assembleBasicRequest(bean);
                if (isFromssj) {
                    bean.setLbr(V_config.ssjBbh);
                }
                CarInfoJoinDto carInfoJoinJson = new CarInfoJoinDto();
                carInfoJoinJson.setHphm(cphm);
                carInfoJoinJson.setClsbdh(clsbh);
                carInfoJoinJson.setFdjh(fdjh);
                carInfoJoinJson.setHpzl(hpzl);
                bean.setCarInfoJoinJson(carInfoJoinJson);
                bean.setPageNum(1);
                bean.setCount(10);
                return bean;
            case V_config.COUNTRY_PERSON:
                CountryPersonReqBean countryPersonReqBean = new CountryPersonReqBean();
                assembleBasicRequest(countryPersonReqBean);
                if (isFromssj) {
                    countryPersonReqBean.setLbr(V_config.ssjBbh);
                }
                countryPersonReqBean.setSfzh(sfzh);
                return countryPersonReqBean;
            case V_config.INSPECT_CAR:
                InspectCarReqBean inspectCarReqBean = new InspectCarReqBean();
                assembleBasicRequest(inspectCarReqBean);
                if (isFromssj) {
                    inspectCarReqBean.setLbr(V_config.ssjBbh);
                }
                Request request = new Request();
                request.setLrb(V_config.LBR);
                Dlxx dlxx = new Dlxx();
                dlxx.setHCDZ(V_config.hc_address);
                Clxx clxx = new Clxx();
                clxx.setCPHM(cphm);
                clxx.setCLLX(hpzl);
                request.setDlxx(dlxx);
                request.setClxx(clxx);
                inspectCarReqBean.setRequest(request);
                return inspectCarReqBean;
            case V_config.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicRequest(inspectPersonReqBean);
                if (isFromssj) {
                    inspectPersonReqBean.setLbr(V_config.ssjBbh);
                }
                com.sunland.hzhc.bean.i_inspect_person.Request request_p = new com.sunland.hzhc.bean.i_inspect_person.Request();
                com.sunland.hzhc.bean.i_inspect_person.Dlxx dlxx_p = new com.sunland.hzhc.bean.i_inspect_person.Dlxx();
                dlxx_p.setHCDZ(V_config.hc_address);
                request_p.setDlxx(dlxx_p);
                request_p.setLrb(V_config.LBR);
                RyxxReq ryxxReq = new RyxxReq();
                ryxxReq.setJNJW("01");
                ryxxReq.setZJLX("10");
                ryxxReq.setZJHM(sfzh);
                ryxxReq.setSJHM("");
                request_p.setRyxxReq(ryxxReq);
                inspectPersonReqBean.setRequest(request_p);
                return inspectPersonReqBean;
            case V_config.CAR_FOCUS:
                VehicleFocusReqBean vehicleFocusReqBean = new VehicleFocusReqBean();
                assembleBasicRequest(vehicleFocusReqBean);
                if (isFromssj) {
                    vehicleFocusReqBean.setLbr(V_config.ssjBbh);
                }
                vehicleFocusReqBean.setCphm(cphm);
                vehicleFocusReqBean.setHpzl(hpzl);
                vehicleFocusReqBean.setSyr_sfzmhm(sfzh);
                return vehicleFocusReqBean;
        }
        return null;
    }


    @OnClick({R.id.sfzh_check, R.id.focus, R.id.location_container, R.id.retry, R.id.focus_retry})
    public void onClick(View view) {
        int id = view.getId();
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.sfzh_check:
                bundle.putString("id", sfzh);
                hop2ActivitySingleTask(Ac_rycx.class, bundle);
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
                inspect_time = 0;
                queryYdjwDataNoDialog("INSPECT_CAR", V_config.INSPECT_CAR);
                queryYdjwDataX();
                break;
            case R.id.focus_retry:
                tv_focus_road_check.setText("");
                sk_loading_icon.setVisibility(View.VISIBLE);
                queryYdjwDataNoDialog("CAR_FOCUS", V_config.CAR_FOCUS);
                queryYdjwDataX();
                break;
        }
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        String code;
        switch (reqName) {
            case V_config.CAR_INFO_JOIN:
                showLoading_layout(false);
                ClxxzhResponseBean resBean = (ClxxzhResponseBean) resultBase;
                if (resBean == null) {
                    Toast.makeText(this, "车辆信息组合查询接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<InfoJDCXQs> infoJDCXQ_list = resBean.getInfoJDCXQs();
                if (infoJDCXQ_list == null || infoJDCXQ_list.isEmpty()) {
                    Toast.makeText(this, "未获得相关车辆信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                load_car_info = true;
                code = resBean.getCode();
                if (code.equals("6") || code.equals("7")) {
                    showWarningIfPolice(code);
                    return;
                }

                InfoJDCXQs infoJDCXQs = infoJDCXQ_list.get(0);
                if (infoJDCXQs == null) {
                    Toast.makeText(this, "未获得相关车辆信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                sfzh = infoJDCXQs.getZjh();
                if (sfzh != null || !sfzh.isEmpty()) {
                    btn_sfzh.setVisibility(View.VISIBLE);
                }
                if (!load_wanted) {
                    queryWanted();
                }
                setText(tv_id_num, sfzh);
                setText(tv_name, infoJDCXQs.getClsyr());
                setText(tv_plateform_num, infoJDCXQs.getCphm());
                setText(tv_car_type, infoJDCXQs.getCllx());
                setText(tv_car_brand, infoJDCXQs.getClpp());
                setText(tv_type_num, infoJDCXQs.getClxh());
                setText(tv_car_color, infoJDCXQs.getClys());
                setText(tv_sbdm, infoJDCXQs.getClsbdh());
                setText(tv_fdjh, infoJDCXQs.getFdjh());
                setText(tv_fdj_sequence, infoJDCXQs.getFdjxh());//发动机型号不对应发动机序列号

                //用于随手记传递
                clsyr = infoJDCXQs.getClsyr();
                pp = infoJDCXQs.getClpp();
                xh = infoJDCXQs.getClxh();
                clsbh = infoJDCXQs.getClsbdh();
                cllx_str = infoJDCXQs.getCllx();
                fdjh = infoJDCXQs.getFdjh();
                fdjxlh = infoJDCXQs.getFdjxh();
                ys = infoJDCXQs.getClys();
                queryWanted();
                isSfzh = !UtilsString.checkId(sfzh).equals("");
                if (isSfzh) {
                    queryYdjwDataNoDialog("COUNTRY_PERSON", V_config.COUNTRY_PERSON);
                } else {
                    btn_sfzh.setVisibility(View.GONE);
                }
                queryYdjwDataNoDialog("INSPECT_CAR", V_config.INSPECT_CAR);
                queryYdjwDataX();
                break;
            case V_config.COUNTRY_PERSON:
                PersonOfCountryJsonRet personOfCountry = (PersonOfCountryJsonRet) resultBase;
                if (personOfCountry == null) {
                    Toast.makeText(this, "全国库人员信息查询异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                load_country_person = true;
                code = personOfCountry.getCode();
                if (code.equals("6") || code.equals("7")) {
                    showWarningIfPolice(code);
                    return;
                }
                final String xp = personOfCountry.getXP();
                if (xp != null) {
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
                }
                break;
            case V_config.INSPECT_CAR:
                if (isSfzh) {
                    queryYdjwDataNoDialog("INSPECT_PERSON", V_config.INSPECT_PERSON);
                    queryYdjwDataX();
                } else {
                    loading_hc.setVisibility(View.GONE);
                }

                InspectCarResBean inspectCarResBean = (InspectCarResBean) resultBase;
                if (inspectCarResBean == null) {
                    Toast.makeText(this, "杭州车核查接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                CLxxRes clxx = inspectCarResBean.getClxx();
                if (clxx == null) {
                    result_car_inspect = "车核查接口" + ":<font color=\"#FF7F50\">" + inspectCarResBean.getMessage() + "</font>";
                    if (!isSfzh) {
                        tv_road_check.setText(Html.fromHtml(result_car_inspect));
                    }
                    return;
                }
                load_inspect_car = true;
                code = inspectCarResBean.getCode();
                if (code.equals("6") || code.equals("7")) {
                    showWarningIfPolice(code);
                    return;
                }
                String result;
                if ("通过".equals(clxx.getHcjg())) {
                    result = "<font color=\"#05b163\">" + clxx.getHcjg() + "</font>" + clxx.getBjxx();
                } else {
                    result = "<font color=\"#d13931\">" + clxx.getHcjg() + "</font>" + clxx.getBjxx();
                    startVibrate();
                    sffa = "1";
                }
                wfqk.append(clxx.getHcjg()).append(clxx.getBjxx()).append("\n");
                result_car_inspect = result;

                if (!isSfzh) {
                    tv_road_check.setText(Html.fromHtml(result_car_inspect));
                }

                break;
            case V_config.INSPECT_PERSON:
                loading_hc.setVisibility(View.GONE);
                InspectPersonJsonRet inspectPersonJsonRet = (InspectPersonJsonRet) resultBase;
                if (inspectPersonJsonRet == null) {
                    Toast.makeText(this, "杭州人核录接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes == null) {
                    tv_road_check.setText(Html.fromHtml("人核查接口:" + "<font color=\"#FF7F50\">" + inspectPersonJsonRet.getMessage() + "</font>" + "<br/><br/>" + result_car_inspect));
                    return;
                }

                load_inspect_person = true;
                code = inspectPersonJsonRet.getCode();
                if (code.equals("6") || code.equals("7")) {
                    showWarningIfPolice(code);
                    return;
                }
                String result_p;
                if ("通过".equals(ryxxRes.getHcjg())) {
                    result_p = "<font color=\"#05b163\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                } else {
                    startVibrate();
                    result_p = "<font color=\"#d13931\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                }
                wfqk.append(ryxxRes.getHcjg()).append(ryxxRes.getBjxx()).append("\n");

                result_person_inspect = result_p;
                tv_road_check.setText(Html.fromHtml(result_car_inspect + "<br/>" + result_person_inspect));
                break;
            case V_config.CAR_FOCUS:
                sk_loading_icon.setVisibility(View.GONE);
                VehicleFocusResBean vehicleFocusResBean = (VehicleFocusResBean) resultBase;
                if (vehicleFocusResBean == null) {
                    Toast.makeText(this, "车辆关注接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<CarFocus_info> list = vehicleFocusResBean.getCarList();
                if (list == null || list.isEmpty()) {
                    tv_focus_road_check.setText(vehicleFocusResBean.getMessage());
                    return;
                }
                load_car_focus = true;
                code = vehicleFocusResBean.getCode();
                if (code.equals("6") || code.equals("7")) {
                    showWarningIfPolice(code);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (CarFocus_info carFocus_info : list) {
                    if (carFocus_info.getStatus().equals("2")) {
                        stringBuilder.append(carFocus_info.getLb()).append("\n");
                        sffa = "1";
                        wfqk.append(carFocus_info.getLb()).append(carFocus_info.getNr()).append("\n");
                        if (carFocus_info.getNr() != null) {
                            stringBuilder.append(": ").append(carFocus_info.getNr()).append("\n\n");
                        }
                    }
                }
                if (stringBuilder.toString().isEmpty()) {
                    tv_focus_road_check.setText(Html.fromHtml("<font color=\"#05b163\"> 无相关记录</font>"));
                } else {
                    tv_focus_road_check.setText(Html.fromHtml(stringBuilder.toString()));
                }
        }
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
                            load_wanted = true;
                            tv_wanted.setText(lmhcResBean.getMessage());
                            if (lmhcResBean.getStatus().equals("2100")) {
                                startVibrate();
                                tv_wanted.setTextColor(getResources().getColor(R.color.warning_color));
                                sffa = "1";
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

    public void setText(TextView textView, String content) {
        if (content == null || content.isEmpty()) {
            textView.setText("无");
        } else {
            textView.setText(content);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == V_config.REQ_LOCATION) {
            if (resultCode == RESULT_OK) {
                tv_hc_location.setText(V_config.hc_address);
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (!isPoliceinfo)
            getMenuInflater().inflate(R.menu.ssj_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.ssj://存储至随手记
                Intent intent = new Intent();
                VehicleInfo vehicleInfo = new VehicleInfo();
                initVehicleInfo(vehicleInfo);
                if (isFromssj) {

                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 1);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(vehicleInfo));
                    bundle.putString("yhdm", V_config.YHDM);
                    bundle.putString("sfz", V_config.JYSFZH);
                    bundle.putString("xm", V_config.JYXM);
                    bundle.putString("bmdm", V_config.JYBMBH);
                    bundle.putString("lx", V_config.gpsX);
                    bundle.putString("ly", V_config.gpsY);
                    bundle.putBoolean("fromHc", true);
                    bundle.putBoolean("isFromSsj", isFromssj);
                    intent.putExtra("bundle", bundle);
                    hop2ActivitySingle(Ac_main.class, bundle);

//                    setResult(RESULT_OK, intent);
//                    finish();
                } else {
                    intent.setAction("com.sunland.action.record");
                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 1);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(vehicleInfo));
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

    @Override
    protected void onStop() {
        super.onStop();
        stopVibrate();
    }

    private void initVehicleInfo(VehicleInfo vehicleInfo) {

        vehicleInfo.setClph(cphm);
        vehicleInfo.setCjh(clsbh);
        vehicleInfo.setFdjh(fdjh);
        vehicleInfo.setCllb(cllx_str);
        vehicleInfo.setCllbdm(hpzl);
//        vehicleInfo.setCsysdm(ys);//无颜色代码信息
        vehicleInfo.setPp(pp);
        vehicleInfo.setXh(xh);
        vehicleInfo.setCsys(ys);
//        vehicleInfo.setTz();
        vehicleInfo.setJsyname(clsyr);
        vehicleInfo.setJsysfzh(sfzh);
        vehicleInfo.setSffa(sffa);
        vehicleInfo.setWfqk(wfqk.toString());
    }
}
