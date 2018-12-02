package com.sunland.hzhc.modules.sfz_module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.UserInfo;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_inspect_person.Dlxx;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonReqBean;
import com.sunland.hzhc.bean.i_inspect_person.Request;
import com.sunland.hzhc.bean.i_inspect_person.RyxxReq;
import com.sunland.hzhc.bean.i_inspect_person.RyxxRes;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.jdc_module.Ac_clcx;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunland.hzhc.modules.p_archive_module.Ac_archive;
import com.sunland.hzhc.modules.sfz_module.beans.CountryPersonReqBean;
import com.sunland.hzhc.modules.sfz_module.beans.InfoFCXX;
import com.sunland.hzhc.modules.sfz_module.beans.InfoRYXXForJDC;
import com.sunland.hzhc.modules.sfz_module.beans.PersonOfCountryJsonRet;
import com.sunland.hzhc.modules.sfz_module.beans.RyzhxxReqBean;
import com.sunland.hzhc.modules.sfz_module.beans.RyzhxxResBean;
import com.sunland.hzhc.modules.sfz_module.beans.XQInfoZZ;
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
    public Button btn_jdc_check;
    @BindView(R.id.fjdc_check)
    public Button btn_fjdc_check;
    @BindView(R.id.phone_check)
    public Button btn_phone_check;
    private String sfzh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_info_detail_test);
        showNavIcon(true);
        setToolbarTitle("个人信息");
        initView();
        queryYdjwData(Dictionary.PERSON_COMPLEX);
        queryYdjwDataNoDialog(Dictionary.COUNTRY_PERSON);
        queryYdjwDataNoDialog(Dictionary.INSPECT_PERSON);
    }

    public void initView() {

        tv_hc_location.setText(UserInfo.hc_address);

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
                            tv_wanted.setText(lmhcResBean.getMessage());
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
            }
        }
    }


    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case Dictionary.PERSON_COMPLEX:
                RyzhxxReqBean bean = new RyzhxxReqBean();
                assembleBasicObj(bean);
                bean.setSfzh(sfzh);
                return bean;
            case Dictionary.COUNTRY_PERSON:
                CountryPersonReqBean countryPersonReqBean = new CountryPersonReqBean();
                assembleBasicObj(countryPersonReqBean);
                countryPersonReqBean.setSfzh(sfzh);
                return countryPersonReqBean;
            case Dictionary.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicObj(inspectPersonReqBean);
                Request request = new Request();
                inspectPersonReqBean.setYhdm(UserInfo.jhdm);
                Dlxx dlxx = new Dlxx();
                dlxx.setHCDZ(UserInfo.hc_address);
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

    @OnClick({R.id.focus, R.id.jdc_check})
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
                bundle.putString("sfzh",sfzh );
                hop2Activity(Ac_clcx.class, bundle);
                break;
        }
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case Dictionary.PERSON_COMPLEX:
                RyzhxxResBean ryzhxxResBean = (RyzhxxResBean) resultBase;
                setText(tv_id_num, ryzhxxResBean.getSfzh());
                setText(tv_name, ryzhxxResBean.getXm());
                setText(tv_gender, ryzhxxResBean.getXb());
                setText(tv_birth, ryzhxxResBean.getCsrq());
                setText(tv_ethnic, ryzhxxResBean.getMz());
                setText(tv_origin_place, ryzhxxResBean.getJg());
                setText(tv_driver_license, ryzhxxResBean.getZjcx());
                setText(tv_og_address, ryzhxxResBean.getHjdz());
                List<InfoRYXXForJDC> jdcs = ryzhxxResBean.getJdc_list();
                if (jdcs != null) {
                    StringBuilder hphms = new StringBuilder();
                    for (InfoRYXXForJDC jdc : jdcs) {
                        hphms.append(jdc.getHphm()).append("   ");
                    }
                    setText(tv_vehicle, hphms.toString());
                    showButton(btn_jdc_check, hphms.toString());
                }
                List<String> dh_list = ryzhxxResBean.getDh_list();
                if (dh_list != null) {
                    StringBuilder phone_nums = new StringBuilder();
                    for (String phone_num : dh_list) {
                        phone_nums.append(phone_num).append("   ");
                    }
                    setText(tv_phone_num, phone_nums.toString());
                    showButton(btn_phone_check, phone_nums.toString());
                }

                List<XQInfoZZ> zzxx_list = ryzhxxResBean.getZzxx_list();
                if (zzxx_list != null) {
                    StringBuilder temp_adds = new StringBuilder();
                    for (XQInfoZZ xqInfoZZ : zzxx_list) {
                        temp_adds.append(xqInfoZZ.getZzdz());
                    }
                    setText(tv_temp_address, temp_adds.toString());
                }

                List<String> fjdc_list = ryzhxxResBean.getFjdc_list();
                if (fjdc_list != null) {
                    StringBuilder temp_adds = new StringBuilder();
                    for (String fjdc : fjdc_list) {
                        temp_adds.append(fjdc).append("   ");
                    }
                    setText(tv_fjdc, temp_adds.toString());
                    showButton(btn_fjdc_check, temp_adds.toString());
                }
                List<InfoFCXX> fcxxes = ryzhxxResBean.getFcxx_list();
                if (fcxxes != null) {
                    StringBuilder fcxx = new StringBuilder();
                    for (InfoFCXX infoFcxx : fcxxes) {
                        fcxx.append(infoFcxx.getFcsj()).append(infoFcxx.getFcdz()).append("   ");
                    }
                    setText(tv_fcxx, fcxx.toString());
                }
                break;
            case Dictionary.COUNTRY_PERSON:
                PersonOfCountryJsonRet personOfCountry = (PersonOfCountryJsonRet) resultBase;
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
            case Dictionary.INSPECT_PERSON:
                InspectPersonJsonRet inspectPersonJsonRet = (InspectPersonJsonRet) resultBase;
                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes != null) {
                    tv_road_check.setText(ryxxRes.getHcjg() + "  " + ryxxRes.getBjxx());
                    if (ryxxRes.getFhm().equals("000")) {
                        tv_road_check.setTextColor(Color.GREEN);
                    } else {
                        tv_road_check.setTextColor(Color.RED);
                    }
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

    public void showButton(Button btn, String content) {
        if (content == null || content.isEmpty()) {
            btn.setVisibility(View.GONE);
        } else {
            btn.setVisibility(View.VISIBLE);
        }
    }
}
