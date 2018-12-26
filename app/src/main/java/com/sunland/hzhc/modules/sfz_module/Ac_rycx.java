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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sunland.hzhc.Ac_location;
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
import com.sunland.hzhc.bean.ssjBean.PersonInfo;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.ddc_module.Ac_ddc_list;
import com.sunland.hzhc.modules.jdc_module.Ac_clcx;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunland.hzhc.modules.own_car_module.Ac_car_list;
import com.sunland.hzhc.modules.p_archive_module.Ac_archive;
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
    private String hjqh = "";//户籍区划
    private String ztry = "";//是否在逃人员
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
        resources = getResources();
        initView();

        queryYdjwDataNoDialog(V_config.INSPECT_PERSON);
        queryYdjwDataNoDialog(V_config.COUNTRY_PERSON);
        queryYdjwDataNoDialog(V_config.PERSON_COMPLEX);
        queryYdjwDataX("");
        showLoading_layout(true);
    }

    public void initView() {
        tv_hc_location.setText(V_config.hc_address);
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
                    //-1 查无结果,2100再逃
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LmhcResBean lmhcResBean = JsonUtils.fromJson(result, LmhcResBean.class);
                            if (lmhcResBean != null) {
                                tv_wanted.setText(lmhcResBean.getMessage());
                                if (lmhcResBean.getStatus().equals("-1")) {
                                    tv_wanted.setTextColor(resources.getColor(R.color.non_warning_color));
                                    ztry = "0";//未在逃
                                } else {
                                    startVibrate();
                                    tv_wanted.setTextColor(resources.getColor(R.color.warning_color));
                                    ztry = "1";//在逃
                                }
                                ryxx.append(lmhcResBean.getMessage());
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
            }
        }
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.PERSON_COMPLEX:
                RyzhxxReqBean bean = new RyzhxxReqBean();
                assembleBasicRequest(bean);
                bean.setSfzh(sfzh);
                return bean;
            case V_config.COUNTRY_PERSON:
                CountryPersonReqBean countryPersonReqBean = new CountryPersonReqBean();
                assembleBasicRequest(countryPersonReqBean);
                countryPersonReqBean.setSfzh(sfzh);
                return countryPersonReqBean;
            case V_config.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicRequest(inspectPersonReqBean);
                Request request = new Request();
                inspectPersonReqBean.setYhdm(V_config.jhdm);
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

    @OnClick({R.id.focus, R.id.jdc_check, R.id.fjdc_check, R.id.phone_check, R.id.location_container})
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
                    hop2Activity(Ac_car_list.class, bundle);
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
        }
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.PERSON_COMPLEX:
                showLoading_layout(false);
                RyzhxxResBean ryzhxxResBean = (RyzhxxResBean) resultBase;
                if (ryzhxxResBean == null) {
                    Toast.makeText(this, "人员综合信息查询接口异常", Toast.LENGTH_SHORT).show();
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
                        fcxx.append(infoFcxx.getFcsj()).append(infoFcxx.getFcdz()).append(",");
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
                InspectPersonJsonRet inspectPersonJsonRet = (InspectPersonJsonRet) resultBase;
                if (inspectPersonJsonRet == null) {
                    Toast.makeText(this, "杭州人核录接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes != null) {
                    tv_road_check.setText(ryxxRes.getHcjg() + "  " + ryxxRes.getBjxx());
                    String result;
                    if (ryxxRes.getFhm().equals("000")) {
                        result = "<font color=\"#05b163\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                        zdry = "0";//非重点
                    } else {
                        result = "<font color=\"#d13931\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                        zdry = "1";//重点人员
                    }
                    tv_road_check.setText(Html.fromHtml(result));
                    returncode = ryxxRes.getFhm();
                    ryxx.append(ryxxRes.getHcjg()).append(ryxxRes.getBjxx());
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                    intent.putExtra("bundle", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    intent.setAction("com.sunland.action.record");
                    bundle.putInt(DataModel.RECORD_BUNDLE_TYPE, 0);
                    bundle.putString(DataModel.RECORD_BUNDLE_ADDR, V_config.hc_address);
                    bundle.putString(DataModel.RECORD_BUNDLE_DATA, new Gson().toJson(personInfo));
                    intent.putExtras(bundle);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
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
        personInfo.setGj(gj);
        personInfo.setZjlx(zjlx);
        personInfo.setXm(xm);
        personInfo.setXb(xb);
        personInfo.setSfzh(sfzh);
        personInfo.setHjqh(hjqh);
        personInfo.setZtry(ztry);
        personInfo.setZdry(zdry);
        personInfo.setWffz(wffz);
        personInfo.setMz(mz);
        personInfo.setReturncode(returncode);
        personInfo.setRyxx(ryxx.toString());
    }
}
