package com.sunland.hzhc.modules.p_archive_module;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_inspect_person.Dlxx;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonReqBean;
import com.sunland.hzhc.bean.i_inspect_person.Request;
import com.sunland.hzhc.bean.i_inspect_person.RyxxReq;
import com.sunland.hzhc.bean.i_inspect_person.RyxxRes;
import com.sunland.hzhc.bean.i_person_focus.FocusReqBean;
import com.sunland.hzhc.bean.i_person_focus.InfoGZXXResp;
import com.sunland.hzhc.bean.i_person_focus.PeopleFocusResBean;
import com.sunland.hzhc.modules.lmhc_module.LmhcResBean;
import com.sunland.hzhc.modules.lmhc_module.MyTaskParams;
import com.sunland.hzhc.modules.lmhc_module.QueryHttp;
import com.sunlandgroup.Global;
import com.sunlandgroup.utils.JsonUtils;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;

public class Frg_focus extends Frg_base {

    @BindView(R.id.wanted)
    public TextView tv_wanted;
    @BindView(R.id.road_check)
    public TextView tv_hcjg;
    @BindView(R.id.focus)
    public Button btn_focus;
    @BindView(R.id.retry)
    public Button btn_retry;
    @BindView(R.id.loading_icon_hc)
    public SpinKitView loading_hc;
    @BindView(R.id.loading_icon_zt)
    public SpinKitView loading_zt;

    private String sfzh;//身份证号码
    private List<InfoGZXXResp> gzxx_list;//关注信息列表
    private Thread thread;
    private boolean load_person_focus_info;
    private boolean load_inspect_person;
    private boolean load_wanted;

    @Override
    public int setLayoutId() {
        return R.layout.frg_focus;
    }

    @Override
    public void initView() {
        btn_focus.setVisibility(View.GONE);
        btn_retry.setVisibility(View.GONE);
        sfzh = ((Ac_archive) context).identity_num;

    }

    private void queryWanted() {
        thread = new Thread(new Runnable() {
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
                    if (context != null) {
                        ((Ac_archive) context).runOnUiThread(new Runnable() {
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
                                if (lmhcResBean.getStatus().equals("-1")) {
                                    tv_wanted.setTextColor(getResources().getColor(R.color.non_warning_color));
                                } else {
                                    ((Ac_base) context).startVibrate();
                                    tv_wanted.setTextColor(getResources().getColor(R.color.warning_color));
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible) {

            queryYdjwDataNoDialog(V_config.INSPECT_PERSON);
            queryYdjwDataX();
            queryWanted();
        }
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.PERSON_FOCUS_INFO:
                FocusReqBean focusReqBean = new FocusReqBean();
                assembleBasicRequest(focusReqBean);
                focusReqBean.setSfzh(sfzh);
                return focusReqBean;
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
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {

        switch (reqName) {
            case V_config.PERSON_FOCUS_INFO:
                PeopleFocusResBean peopleFocusResBean = (PeopleFocusResBean) bean;
                if (peopleFocusResBean == null) {
                    Toast.makeText(context, "人员关注信息接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                gzxx_list = peopleFocusResBean.getGzxxList();
                load_person_focus_info = true;
                break;
            case V_config.INSPECT_PERSON:
                InspectPersonJsonRet inspectPersonJsonRet = (InspectPersonJsonRet) bean;
                if (inspectPersonJsonRet == null) {
                    Toast.makeText(context, "杭州人核查接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                loading_hc.setVisibility(View.GONE);
                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes != null) {
                    String result;
                    if (ryxxRes.getHcjg().equals("000")) {
                        result = "<font color=\"#d13931\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                    } else {
                        result = "<font color=\"#05b163\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                    }
                    tv_hcjg.setText(Html.fromHtml(result));
                    load_inspect_person = true;
                }
                break;
        }
    }

    @Override
    public void onFragmentVisible() {
        super.onFragmentVisible();
        if (!load_inspect_person) {

        }
        if (!load_inspect_person) {
            queryYdjwDataNoDialog(V_config.INSPECT_PERSON);
        }
//        queryYdjwData(V_config.PERSON_FOCUS_INFO);
        queryYdjwDataX();
        if (!load_wanted) {
            queryWanted();
        }
    }
}
