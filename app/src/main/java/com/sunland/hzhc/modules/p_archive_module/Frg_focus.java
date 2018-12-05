package com.sunland.hzhc.modules.p_archive_module;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.UserInfo;
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
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;
import com.sunlandgroup.utils.JsonUtils;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;

public class Frg_focus extends Frg_base implements OnRequestCallback {

    @BindView(R.id.wanted)
    public TextView tv_wanted;
    @BindView(R.id.road_check)
    public TextView tv_hcjg;

    @BindView(R.id.focus)
    public Button btn_focus;
    @BindView(R.id.retry)
    public Button btn_retry;

    private String sfzh;//身份证号码

    private RequestManager mRequestManager;
    private List<InfoGZXXResp> gzxx_list;//关注信息列表

    @Override
    public int setLayoutId() {
        return R.layout.frg_focus;
    }

    @Override
    public void initView() {
        mRequestManager = new RequestManager(context, this);
        btn_focus.setVisibility(View.GONE);
        btn_retry.setVisibility(View.GONE);
        sfzh = ((Ac_archive) context).identity_num;
//        queryYdjwData(Dictionary.PERSON_FOCUS_INFO);
        queryYdjwData(Dictionary.INSPECT_PERSON);

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
                    ((Ac_archive) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LmhcResBean lmhcResBean = JsonUtils.fromJson(result, LmhcResBean.class);
                            tv_wanted.setText(lmhcResBean.getMessage());
                            if (lmhcResBean.getStatus().equals("-1")) {
                                tv_wanted.setTextColor(getResources().getColor(R.color.non_warning_color));
                            } else {
                                ((Ac_base) context).startVibrate();
                                tv_wanted.setTextColor(getResources().getColor(R.color.warning_color));
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void queryYdjwData(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequest();

    }

    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case Dictionary.PERSON_FOCUS_INFO:
                FocusReqBean focusReqBean = new FocusReqBean();
                assembleBasicObj(focusReqBean);
                focusReqBean.setSfzh(sfzh);
                return focusReqBean;
            case Dictionary.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicObj(inspectPersonReqBean);
                Request request = new Request();
                inspectPersonReqBean.setYhdm("115576");
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

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        switch (reqName) {
            case Dictionary.PERSON_FOCUS_INFO:
                PeopleFocusResBean peopleFocusResBean = (PeopleFocusResBean) bean;
                gzxx_list = peopleFocusResBean.getGzxxList();
                break;
            case Dictionary.INSPECT_PERSON:
                InspectPersonJsonRet inspectPersonJsonRet = (InspectPersonJsonRet) bean;
                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes != null) {
                    String result;
                    if (ryxxRes.getHcjg().equals("000")) {
                        result = "<font color=\"#d13931\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                    } else {
                        result = "<font color=\"#05b163\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                    }
                    tv_hcjg.setText(Html.fromHtml(result));
                }
                break;
        }
    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        switch (reqName) {
            case Dictionary.PERSON_FOCUS_INFO:
                return PeopleFocusResBean.class;
            case Dictionary.INSPECT_PERSON:
                return InspectPersonJsonRet.class;
        }
        return null;
    }
}
