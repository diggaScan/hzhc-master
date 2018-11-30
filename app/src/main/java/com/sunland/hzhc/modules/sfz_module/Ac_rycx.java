package com.sunland.hzhc.modules.sfz_module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.UserInfo;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunland.hzhc.modules.jdc_module.CzsycReqBean;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunland.hzhc.modules.own_car_module.Ac_car_list;
import com.sunland.hzhc.modules.p_archive_module.Ac_archive;
import com.sunland.hzhc.modules.sfz_module.beans.CountryPersonReqBean;
import com.sunland.hzhc.modules.sfz_module.beans.InfoRYXXForJDC;
import com.sunland.hzhc.modules.sfz_module.beans.InspectPersonReqBean;
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
    @BindView(R.id.xp)
    public ImageView iv_xp;

    private String identity_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_info_detail_test);
        showNavIcon(true);
        initView();
        queryYdjwData(Dictionary.PERSON_COMPLEX);
        queryYdjwDataNoDialog(Dictionary.COUNTRY_PERSON);
//        queryYdjwDataNoDialog(Dictionary.INSPECT_PERSON);
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
                    taskParams.putEntity("sfzh", identity_num);
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
                identity_num = bundle.getString("id");
            }
        }
    }


    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case Dictionary.PERSON_COMPLEX:
                RyzhxxReqBean bean = new RyzhxxReqBean();
                assembleBasicObj(bean);
                bean.setSfzh(identity_num);
                return bean;
            case Dictionary.COUNTRY_PERSON:
                CountryPersonReqBean countryPersonReqBean = new CountryPersonReqBean();
                assembleBasicObj(countryPersonReqBean);
                countryPersonReqBean.setSfzh(identity_num);
                return countryPersonReqBean;
            case Dictionary.INSPECT_PERSON:
                InspectPersonReqBean inspectPerson = new InspectPersonReqBean();
                assembleBasicObj(inspectPerson);
                return inspectPerson;
        }
        return null;
    }

    @OnClick({R.id.archive, R.id.track, R.id.focus, R.id.owned_cars})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.archive:
                Bundle bundle = new Bundle();
                bundle.putString("id", identity_num);
                hop2Activity(Ac_archive.class, bundle);
                break;
            case R.id.track:
                queryYdjwData(Dictionary.PERSON_LOCUS_INFOS);
                break;
            case R.id.focus:
                queryYdjwData(Dictionary.PERSON_FOCUS_INFO);
                break;
            case R.id.owned_cars:
                Bundle bundle1 = new Bundle();
                bundle1.putString("sfzh", identity_num);
                hop2Activity(Ac_car_list.class, bundle1);
                break;

        }
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case Dictionary.PERSON_COMPLEX:
                RyzhxxResBean ryzhxxResBean = (RyzhxxResBean) resultBase;
                tv_id_num.setText(ryzhxxResBean.getSfzh());
                tv_name.setText(ryzhxxResBean.getXm());
                tv_gender.setText(ryzhxxResBean.getXb());
                tv_birth.setText(ryzhxxResBean.getCsrq());
                tv_ethnic.setText(ryzhxxResBean.getMz());
                tv_origin_place.setText(ryzhxxResBean.getJg());
                tv_driver_license.setText(ryzhxxResBean.getZjcx());
                List<InfoRYXXForJDC> jdcs = ryzhxxResBean.getJdc_list();
                if (jdcs != null) {
                    StringBuilder hphms = new StringBuilder();

                    for (InfoRYXXForJDC jdc : jdcs) {
                        hphms.append(jdc.getHphm()).append(",");
                    }
                    tv_vehicle.setText(hphms);
                }
                List<String> dh_list = ryzhxxResBean.getDh_list();
                if (dh_list != null) {
                    StringBuilder phone_nums = new StringBuilder();
                    for (String phone_num : dh_list) {
                        phone_nums.append(phone_num).append(",");
                    }
                    tv_phone_num.setText(phone_nums);
                }

                tv_og_address.setText(ryzhxxResBean.getHjdz());
                List<XQInfoZZ> zzxx_list = ryzhxxResBean.getZzxx_list();
                if (zzxx_list != null) {
                    StringBuilder temp_adds = new StringBuilder();
                    for (XQInfoZZ xqInfoZZ : zzxx_list) {
                        temp_adds.append(xqInfoZZ.getZzdz());
                    }
                    tv_temp_address.setText(temp_adds);
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


        }
    }
}
