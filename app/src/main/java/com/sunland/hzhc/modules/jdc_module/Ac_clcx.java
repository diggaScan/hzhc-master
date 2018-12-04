package com.sunland.hzhc.modules.jdc_module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunland.hzhc.Ac_location;
import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.UserInfo;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_inspect_car.Clxx;
import com.sunland.hzhc.bean.i_inspect_car.Dlxx;
import com.sunland.hzhc.bean.i_inspect_car.InspectCarReqBean;
import com.sunland.hzhc.bean.i_inspect_car.InspectCarResBean;
import com.sunland.hzhc.bean.i_inspect_car.Request;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.jdc_module.bean.ClxxzhResponseBean;
import com.sunland.hzhc.modules.jdc_module.bean.InfoJDCXQs;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunland.hzhc.modules.p_archive_module.Ac_archive;
import com.sunland.hzhc.modules.sfz_module.Ac_rycx;
import com.sunland.hzhc.modules.sfz_module.beans.CountryPersonReqBean;
import com.sunland.hzhc.modules.sfz_module.beans.PersonOfCountryJsonRet;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.utils.JsonUtils;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_clcx extends Ac_base_info {

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

    private String sfzh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_clcx);
        showNavIcon(true);
        setToolbarTitle("机动车信息");
        queryYdjwData(Dictionary.CAR_INFO_JOIN);
        queryYdjwData(Dictionary.INSPECT_CAR);
        initView();
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

    private void initView() {
        tv_hc_location.setText(UserInfo.hc_address);
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
            case Dictionary.COUNTRY_PERSON:
                CountryPersonReqBean countryPersonReqBean = new CountryPersonReqBean();
                assembleBasicObj(countryPersonReqBean);
                countryPersonReqBean.setSfzh(sfzh);
                return countryPersonReqBean;
            case Dictionary.INSPECT_CAR:
                InspectCarReqBean inspectCarReqBean = new InspectCarReqBean();
                assembleBasicObj(inspectCarReqBean);
                inspectCarReqBean.setYhdm("115576");
                Request request = new Request();
                Dlxx dlxx = new Dlxx();
                dlxx.setHCDZ(UserInfo.hc_address);
                Clxx clxx = new Clxx();
                clxx.setCPHM(cphm);
                clxx.setCLLX(hpzl);
                request.setDlxx(dlxx);
                request.setClxx(clxx);
                inspectCarReqBean.setRequest(request);
                return inspectCarReqBean;
        }
        return null;
    }


    @OnClick({R.id.sfzh_check, R.id.focus, R.id.location_container})
    public void onClick(View view) {
        int id = view.getId();
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.sfzh_check:
                bundle.putString("id", sfzh);
                hop2Activity(Ac_rycx.class, bundle);
                break;
            case R.id.focus:
                bundle.putString("id", sfzh);
                bundle.putInt("tab_id", 1);
                hop2Activity(Ac_archive.class, bundle);
                break;
            case R.id.location_container:
                bundle.putInt("req_location", UserInfo.REQ_LOCATION);
                hop2ActivityForResult(Ac_location.class, bundle, UserInfo.REQ_LOCATION);
                break;
        }
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case Dictionary.CAR_INFO_JOIN:
                ClxxzhResponseBean resBean = (ClxxzhResponseBean) resultBase;
                if (resBean != null) {
                    List<InfoJDCXQs> infoJDCXQ_list = resBean.getInfoJDCXQs();
                    if (infoJDCXQ_list != null && !infoJDCXQ_list.isEmpty()) {
                        InfoJDCXQs infoJDCXQs = infoJDCXQ_list.get(0);
                        sfzh = infoJDCXQs.getZjh();
                        if (sfzh != null || !sfzh.isEmpty()) {
                            btn_sfzh.setVisibility(View.VISIBLE);
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
                        setText(tv_fdj_sequence, infoJDCXQs.getFdjxh());
                        queryWanted();
                        queryYdjwDataNoDialog(Dictionary.COUNTRY_PERSON);
                    } else {
                        Toast.makeText(this, "异常", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case Dictionary.COUNTRY_PERSON:
                PersonOfCountryJsonRet personOfCountry = (PersonOfCountryJsonRet) resultBase;
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
            case Dictionary.INSPECT_CAR:
                InspectCarResBean inspectCarResBean = (InspectCarResBean) resultBase;
                tv_road_check.setText(inspectCarResBean.getMessage());
                break;
        }

    }

    private void queryWanted() {
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
        if (requestCode == UserInfo.REQ_LOCATION) {
            if (resultCode == RESULT_OK) {
                tv_hc_location.setText(UserInfo.hc_address);
            }
        }
    }
}
