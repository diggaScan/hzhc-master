package com.sunland.hzhc.modules.sfz_module;

import android.content.Intent;
import android.content.res.Resources;
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
import com.sunland.hzhc.bean.i_country_people.CountryPersonReqBean;
import com.sunland.hzhc.bean.i_country_people.PersonOfCountryJsonRet;
import com.sunland.hzhc.bean.i_inspect_person.Dlxx;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonReqBean;
import com.sunland.hzhc.bean.i_inspect_person.Request;
import com.sunland.hzhc.bean.i_inspect_person.RyxxReq;
import com.sunland.hzhc.bean.i_inspect_person.RyxxRes;
import com.sunland.hzhc.bean.i_people_complex.InfoFCXX;
import com.sunland.hzhc.bean.i_people_complex.InfoRYXXForJDC;
import com.sunland.hzhc.bean.i_people_complex.RyzhxxReqBean;
import com.sunland.hzhc.bean.i_people_complex.RyzhxxResBean;
import com.sunland.hzhc.bean.i_people_complex.XQInfoZZ;
import com.sunland.hzhc.bean.i_person_focus.FocusReqBean;
import com.sunland.hzhc.bean.i_person_focus.InfoGZXXResp;
import com.sunland.hzhc.bean.i_person_focus.PeopleFocusResBean;
import com.sunland.hzhc.bean.ssjB.PersonInfo;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.ddc_module.Ac_ddc_list;
import com.sunland.hzhc.modules.jdc_module.Ac_clcx;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunland.hzhc.modules.own_car_module.Ac_car_list;
import com.sunland.hzhc.modules.p_archive_module.Ac_archive;
import com.sunland.hzhc.utils.UtilsString;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.utils.JsonUtils;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_rycx extends Ac_base_info {

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
    @BindView(R.id.gender)
    public TextView tv_gender;
    @BindView(R.id.birth)
    public TextView tv_birth;
    @BindView(R.id.ethnic_group)
    public TextView tv_ethnic;
    @BindView(R.id.origin_place)
    public TextView tv_origin_place;
    @BindView(R.id.driver_license)
    public TextView tv_driver_license;
    @BindView(R.id.vehicles)
    public TextView tv_vehicle;
    @BindView(R.id.phone_num)
    public TextView tv_phone_num;
    @BindView(R.id.origin_address)
    public TextView tv_og_address;
    @BindView(R.id.temp_address)
    public TextView tv_temp_address;
    @BindView(R.id.e_vehicles)
    public TextView tv_fjdc;
    @BindView(R.id.xp)
    public ImageView iv_xp;
    @BindView(R.id.fcxx)
    public TextView tv_fcxx;
    @BindView(R.id.jdc_check)
    public TextView btn_jdc_check;
    @BindView(R.id.fjdc_check)
    public TextView btn_fjdc_check;
    @BindView(R.id.phone_check)
    public TextView btn_phone_check;
    @BindView(R.id.loading_icon_hc)
    public SpinKitView loading_hc;
    @BindView(R.id.loading_icon_zt)
    public SpinKitView loading_zt;
    @BindView(R.id.retry)
    public Button btn_retry;
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

    private boolean load_inspect_person;
    private boolean load_country_person;
    private boolean load_person_complex;
    private boolean load_person_focus;
    private boolean load_zt_info;

    private boolean isPoliceInfo;
    private String sfzh = "";//身份证号

    private int jdc_num;
    private int fjdc_num;
    private String jdc_hp;
    private String fjdc_hp;
    private String dh_str;

    private String tp_jdc_hp;
    private String tp_fjdc_hp;
    private String tp_dh_str;

    private Resources resources;
    private String gj = "";// 国籍
    private String zjlx = "";//证件类型
    private String xm = "";//姓名
    private String xb = "";//性别
    private String csrq;//出生日期
    private String jg;//籍贯
    private String hjdz;//户籍详址
    private String zzdz;//暂住地址
    private String dhhm;//电话号码
    private String hjqh = "";//户籍区划
    private String ztry = "";//是否在逃人员
    private String bjxx;//背景信息
    private String zdry = "";//是否重点人员
    private String wffz = "";//是否违法犯罪
    private String mz = "";//民族
    private String returncode = "";//情报核录入接口返回代码
    private StringBuilder ryxx = new StringBuilder();//在逃接口+关注信息+情报接口 返回的描述信息
    public boolean isFromssj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_info_detail_test);
        showNavIcon(true);
        setToolbarTitle("个人信息");
        tv_focus_str.setText("人员关注信息");
        resources = getResources();
        initView();
    }


    public void showWarningIfPolice(String code) {
        isPoliceInfo = true;
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
    protected void onStart() {
        super.onStart();
        if (!load_country_person) {
            queryYdjwDataNoDialog("COUNTRY_PERSON", V_config.COUNTRY_PERSON);
        }
        if (!load_person_complex) {
            showLoading_layout(true);
            queryYdjwDataNoDialog("PERSON_COMPLEX", V_config.PERSON_COMPLEX);
        }

        if (!load_inspect_person) {
            queryYdjwDataNoDialog("INSPECT_PERSON", V_config.INSPECT_PERSON);
        }

        if (!load_person_focus) {
            queryYdjwDataNoDialog("PERSON_FOCUS_INFO", V_config.PERSON_FOCUS_INFO);
        }

        queryYdjwDataX();

        if (!load_zt_info) {
            queryWanted();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopVibrate();
    }

    public void initView() {
        tv_hc_location.setText(V_config.hc_address);
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
                            load_zt_info = true;
                            tv_wanted.setText(lmhcResBean.getMessage());
                            if (lmhcResBean.getStatus().equals("2100")) {
                                ztry = "1";
                                startVibrate();
                                tv_wanted.setTextColor(getResources().getColor(R.color.warning_color));

                            } else {
                                ztry = "0";
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
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                sfzh = bundle.getString("id");
                isFromssj = bundle.getBoolean("fromRandomRecord");

                String yhdm = bundle.getString("yhdm");
                if (!UtilsString.isNullOrEmpty(yhdm)) {
                    V_config.YHDM = yhdm;
                }

                String jysfzh = bundle.getString("jysfzh");
                if (!UtilsString.isNullOrEmpty(jysfzh)) {
                    V_config.JYSFZH = jysfzh;
                }

                String jyxm = bundle.getString("jyxm");
                if (!UtilsString.isNullOrEmpty(jyxm)) {
                    V_config.JYXM = jyxm;
                }

                String jybmbh = bundle.getString("jybmbh");
                if (!UtilsString.isNullOrEmpty(jybmbh)) {
                    V_config.JYBMBH = jybmbh;
                }
                String lbr=bundle.getString("lbr");
                if(!UtilsString.isNullOrEmpty(jybmbh)){
                    V_config.LBR=lbr;
                }

            }
        }
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.PERSON_COMPLEX:
                RyzhxxReqBean bean = new RyzhxxReqBean();
                assembleBasicRequest(bean);
                if (isFromssj) {
                    bean.setLbr(V_config.ssjBbh);
                }
                bean.setSfzh(sfzh);
                return bean;
            case V_config.COUNTRY_PERSON:
                CountryPersonReqBean countryPersonReqBean = new CountryPersonReqBean();
                assembleBasicRequest(countryPersonReqBean);

                if (isFromssj) {
                    countryPersonReqBean.setLbr(V_config.ssjBbh);
                }

                countryPersonReqBean.setSfzh(sfzh);
                return countryPersonReqBean;
            case V_config.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicRequest(inspectPersonReqBean);

                if (isFromssj) {
                    inspectPersonReqBean.setLbr(V_config.ssjBbh);
                }

                Request request = new Request();
                inspectPersonReqBean.setYhdm(V_config.YHDM);
                Dlxx dlxx = new Dlxx();
                dlxx.setHCDZ(V_config.hc_address);
                request.setDlxx(dlxx);
                request.setLrb(V_config.LBR);
                RyxxReq ryxxReq = new RyxxReq();
                ryxxReq.setJNJW("01");
                ryxxReq.setZJLX("10");
                ryxxReq.setZJHM(sfzh);
                ryxxReq.setSJHM("");
                request.setRyxxReq(ryxxReq);
                inspectPersonReqBean.setRequest(request);
                return inspectPersonReqBean;
            case V_config.PERSON_FOCUS_INFO:
                FocusReqBean focusReqBean = new FocusReqBean();
                assembleBasicRequest(focusReqBean);
                focusReqBean.setSfzh(sfzh);
                return focusReqBean;
        }
        return null;
    }

    @OnClick({R.id.focus, R.id.jdc_check, R.id.fjdc_check, R.id.phone_check, R.id.location_container, R.id.retry, R.id.focus_retry})
    public void onClick(View view) {
        int id = view.getId();
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.focus:
                bundle.putString("id", sfzh);
                bundle.putInt("tab_id", 1);
                hop2Activity(Ac_archive.class, bundle);
                break;
            case R.id.jdc_check:
                if (jdc_num == 1) {
                    String cphm = tp_jdc_hp.split(",")[0];
                    bundle.putString("cphm", cphm);
                    bundle.putString("hpzl", "");
                    bundle.putString("fdjh", "");
                    bundle.putString("clsbh", "");
                    hop2Activity(Ac_clcx.class, bundle);
                } else {
                    bundle.putString("sfzh", sfzh);
                    hop2ActivitySingleTask(Ac_car_list.class, bundle);
                }
                break;
            case R.id.fjdc_check:
                if (fjdc_num == 1) {
                    String cphm = tp_fjdc_hp.split(",")[0];
                    bundle.putString("hphm", cphm);
                    bundle.putString("fdjh", "");
                    bundle.putString("cjh", "");
                    hop2Activity(Ac_ddc_list.class, bundle);
                } else {
                    bundle.putString("fjdc_hp", tp_fjdc_hp);
                    hop2Activity(Ac_reg_fdjc.class, bundle);
                }
                break;
            case R.id.phone_check:
                bundle.putString("dh_str", tp_dh_str);
                hop2Activity(Ac_dh.class, bundle);
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
                queryYdjwDataNoDialog("PERSON_FOCUS_INFO", V_config.PERSON_FOCUS_INFO);
                queryYdjwDataX();
        }
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        String code;
        switch (reqName) {
            case V_config.PERSON_COMPLEX:
                showLoading_layout(false);
                RyzhxxResBean ryzhxxResBean = (RyzhxxResBean) resultBase;
                if (ryzhxxResBean == null) {
                    Toast.makeText(this, "人员综合信息查询接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                load_person_complex = true;

                code = ryzhxxResBean.getCode();
                if (code.equals("6") || code.equals("7")) {
                    showWarningIfPolice(code);
                    return;
                }
                setText(tv_id_num, ryzhxxResBean.getSfzh());
                setText(tv_name, ryzhxxResBean.getXm());
                setText(tv_gender, ryzhxxResBean.getXb());
                setText(tv_birth, ryzhxxResBean.getCsrq());
                setText(tv_ethnic, ryzhxxResBean.getMz());
                setText(tv_origin_place, ryzhxxResBean.getJg());
                setText(tv_driver_license, ryzhxxResBean.getZjcx());
                setText(tv_og_address, ryzhxxResBean.getHjdz());
                List<InfoRYXXForJDC> jdcs = ryzhxxResBean.getJdc_list();
                xm = ryzhxxResBean.getXm();
                xb = ryzhxxResBean.getXb();
                hjqh = ryzhxxResBean.getHjqh();
                mz = ryzhxxResBean.getMz();
                csrq = ryzhxxResBean.getCsrq();
                jg = ryzhxxResBean.getJg();
                hjdz = ryzhxxResBean.getHjdz();
                if (jdcs == null || jdcs.isEmpty()) {
                    setText(tv_vehicle, "");
                    showButton(btn_jdc_check, "");
                } else {
                    jdc_num = jdcs.size();
                    StringBuilder hphms = new StringBuilder();
                    StringBuilder sb_jdc = new StringBuilder();
                    int y = 1;
                    for (int i = 0; i < jdc_num; i++) {
                        String jdc_hp = jdcs.get(i).getHphm();
                        sb_jdc.append(jdc_hp).append(",");
                        if (y != 3) {
                            hphms.append(jdc_hp).append(",");
                            y++;
                        } else {
                            if (i == jdc_num - 1) {
                                hphms.append(jdc_hp).append(",");
                            } else {
                                hphms.append(jdc_hp).append("," + "\n");
                            }
                            y = 1;
                        }
                    }
                    tp_jdc_hp = sb_jdc.toString();
                    jdc_hp = hphms.toString();
                    setText(tv_vehicle, jdc_hp.substring(0, jdc_hp.length() - 1));
                    showButton(btn_jdc_check, jdc_hp);
                }

                List<String> dh_list = ryzhxxResBean.getDh_list();
                if (dh_list == null || dh_list.isEmpty()) {
                    setText(tv_phone_num, "");
                    showButton(btn_phone_check, "");
                } else {
                    dhhm = dh_list.get(0);
                    StringBuilder phone_nums = new StringBuilder();
                    int k = 1;
                    StringBuilder tp_phone = new StringBuilder();
                    for (int i = 0; i < dh_list.size(); i++) {
                        String dh = dh_list.get(i);
                        tp_phone.append(dh).append(",").toString();
                        if (k != 2) {
                            phone_nums.append(dh).append(",");
                            k++;
                        } else {
                            if (i == dh_list.size() - 1) {
                                phone_nums.append(dh).append(",");
                            } else {
                                phone_nums.append(dh).append("," + "\n");
                            }
                            k = 1;
                        }
                    }
                    tp_dh_str = tp_phone.toString();
                    dh_str = phone_nums.toString();
                    setText(tv_phone_num, dh_str.substring(0, dh_str.length() - 1));
                    showButton(btn_phone_check, dh_str);
                }

                List<XQInfoZZ> zzxx_list = ryzhxxResBean.getZzxx_list();
                if (zzxx_list == null || zzxx_list.isEmpty()) {
                    setText(tv_temp_address, "");
                } else {
                    zzdz = zzxx_list.get(0).getZzdz();
                    StringBuilder temp_adds = new StringBuilder();
                    for (XQInfoZZ xqInfoZZ : zzxx_list) {
                        temp_adds.append(xqInfoZZ.getZzdz());
                    }
                    setText(tv_temp_address, temp_adds.toString());
                }

                List<String> fjdc_list = ryzhxxResBean.getFjdc_list();
                if (fjdc_list == null || fjdc_list.isEmpty()) {
                    setText(tv_fjdc, "");
                    showButton(btn_fjdc_check, "");
                } else {
                    fjdc_num = fjdc_list.size();
                    StringBuilder temp_adds = new StringBuilder();
                    StringBuilder sb_fjdc = new StringBuilder();
                    int y = 1;//分为3列
                    for (int i = 0; i < fjdc_list.size(); i++) {
                        String hp = fjdc_list.get(i);
                        sb_fjdc.append(hp).append(",").toString();
                        if (y != 3) {
                            temp_adds.append(hp).append(",");
                            y++;
                        } else {
                            if (i == fjdc_list.size() - 1) {
                                temp_adds.append(hp).append(",");
                            } else {
                                temp_adds.append(hp).append("," + "\n");
                            }
                            y = 1;
                        }
                    }
                    fjdc_hp = temp_adds.toString();
                    tp_fjdc_hp = sb_fjdc.toString();
                    setText(tv_fjdc, fjdc_hp.substring(0, fjdc_hp.length() - 1));
                    showButton(btn_fjdc_check, fjdc_hp);
                }

                List<InfoFCXX> fcxxes = ryzhxxResBean.getFcxx_list();
                if (fcxxes == null || fcxxes.isEmpty()) {
                    setText(tv_fcxx, "");
                } else {
                    StringBuilder fcxx = new StringBuilder();
                    for (InfoFCXX infoFcxx : fcxxes) {
                        fcxx.append("访查时间:").append(infoFcxx.getFcsj()).append("\n").append("访查地址:")
                                .append(infoFcxx.getFcdz()).append("\n\n");
                    }
                    setText(tv_fcxx, fcxx.substring(0, fcxx.length() - 1));
                }
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
                gj = personOfCountry.getJGGJ();
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
                    Toast.makeText(this, "杭州人核录接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes == null) {
                    if (V_config.hc_address == null || V_config.hc_address.isEmpty()) {
                        tv_road_check.setText(Html.fromHtml("<font color=\"#FF7F50\"> 请填写核查地址后重查</font>"));
                    } else {
                        tv_road_check.setText(Html.fromHtml("人核查接口:" + "<font color=\"#FF7F50\">" + inspectPersonJsonRet.getMessage() + "</font>"));
                    }
                    return;
                }

                load_inspect_person = true;
                code = inspectPersonJsonRet.getCode();
                if (code.equals("6") || code.equals("7")) {
                    showWarningIfPolice(code);
                    return;
                }
                String result;
                // TODO: 2019/1/8/008
                if ("通过".equals(ryxxRes.getHcjg())) {
                    result = "<font color=\"#05b163\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                    zdry = "0";//非重点，绿色
                } else {
                    result = "<font color=\"#d13931\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                    zdry = "1";//重点人员,红色
                    startVibrate();
                }

                tv_road_check.setText(Html.fromHtml(result));
                returncode = ryxxRes.getFhm();
                bjxx = ryxxRes.getBjxx();
                ryxx.append(ryxxRes.getHcjg()).append(ryxxRes.getBjxx());
                break;
            case V_config.PERSON_FOCUS_INFO:
                sk_loading_icon.setVisibility(View.GONE);
                PeopleFocusResBean peopleFocusResBean = (PeopleFocusResBean) resultBase;
                if (peopleFocusResBean == null) {
                    Toast.makeText(this, "人员关注信息接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<InfoGZXXResp> list = peopleFocusResBean.getGzxxList();
                if (list == null || list.isEmpty()) {
                    tv_focus_road_check.setText(Html.fromHtml("人核查接口:" + "<font color=\"#FF7F50\">" + peopleFocusResBean.getMessage() + "</font>"));
                    return;
                }

                load_person_focus = true;
                code = peopleFocusResBean.getCode();
                if (code.equals("6") || code.equals("7")) {
                    showWarningIfPolice(code);
                    return;
                }
                StringBuilder sb = new StringBuilder();

                for (InfoGZXXResp infoGZXXResp : list) {
                    if (infoGZXXResp.getStatus().equals("2"))
                        sb.append("<font color=\"#d13931\">").append(infoGZXXResp.getNr()).append("<br/>")
                                .append("录入时间:").append(infoGZXXResp.getSj()).append("</font>").append("<br/><br/>");
                }
                if (sb.toString().isEmpty()) {
                    tv_focus_road_check.setText(Html.fromHtml("<font color=\"#05b163\"> 无相关记录</font>"));
                } else {
                    tv_focus_road_check.setText(Html.fromHtml(sb.toString()));
                }

                break;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (!isPoliceInfo)
            getMenuInflater().inflate(R.menu.ac_sfz_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.archive:
                bundle.putString("id", sfzh);
                bundle.putInt("tab_id", 0);
                hop2Activity(Ac_archive.class, bundle);
                break;
            case R.id.track:
                bundle.putString("id", sfzh);
                bundle.putInt("tab_id", 2);
                hop2Activity(Ac_archive.class, bundle);
                break;
            case R.id.ssj:
                Intent intent = new Intent();
                PersonInfo personInfo = new PersonInfo();
                initPersonInfo(personInfo);
                if (isFromssj) {
                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 0);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(personInfo));
                    bundle.putString("yhdm", V_config.YHDM);
                    bundle.putString("sfz", V_config.JYSFZH);
                    bundle.putString("xm", V_config.JYXM);
                    bundle.putString("bmdm", V_config.JYBMBH);
                    bundle.putString("lx", V_config.gpsX);
                    bundle.putString("ly", V_config.gpsY);
                    bundle.putBoolean("isFromSsj", isFromssj);
                    bundle.putBoolean("fromHc", true);
                    intent.putExtra("bundle", bundle);
                    hop2ActivitySingle(Ac_main.class, bundle);
//                    hop2Activity(Ac_main.class, bundle);
//                    setResult(RESULT_OK, intent);
////                    finish();
                } else {
                    intent.setAction("com.sunland.action.record");
                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 0);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(personInfo));
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

    public void setText(TextView textView, String content) {
        if (content == null || content.isEmpty()) {
            textView.setText("无");
        } else {
            textView.setText(content);
        }
    }

    public void showButton(TextView btn, String content) {
        if (content == null || content.isEmpty()) {
            btn.setVisibility(View.GONE);
        } else {
            btn.setVisibility(View.VISIBLE);
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

    private void initPersonInfo(PersonInfo personInfo) {
        personInfo.setSfzh(sfzh);
        personInfo.setName(xm);
        personInfo.setXb(xb);
        personInfo.setCsrq(csrq);
        personInfo.setHksx(jg);
        personInfo.setHkxz(hjdz);
        personInfo.setZzdz(zzdz);
        personInfo.setDhhm(dhhm);
        personInfo.setZtry(ztry);
        personInfo.setQk(bjxx);

//        personInfo.setZzsx("");
//        personInfo.setCljg("");

    }
}
