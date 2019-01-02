package com.sunland.hzhc.modules.batch_check;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunland.hzhc.V_config;
import com.sunland.hzhc.R;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_inspect_person.Dlxx;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonReqBean;
import com.sunland.hzhc.bean.i_inspect_person.Request;
import com.sunland.hzhc.bean.i_inspect_person.RyxxReq;
import com.sunland.hzhc.bean.i_inspect_person.RyxxRes;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.sfz_module.Ac_rycx;
import com.sunland.hzhc.bean.i_people_complex.RyzhxxReqBean;
import com.sunland.hzhc.bean.i_people_complex.RyzhxxResBean;
import com.sunland.hzhc.utils.UtilsString;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_batch_check extends Ac_base_info {

    @BindView(R.id.batch_container)
    public LinearLayout ll_container;
    @BindView(R.id.hc_location)
    public TextView tv_hc_loaction;
    @BindView(R.id.id_input)
    public EditText et_id_input;
    @BindView(R.id.phone_input)
    public EditText et_phone_input;
    @BindView(R.id.hc_str)
    public TextView tv_hc_str;
    @BindView(R.id.filter)
    public Button btn_filter;

    public List<String> sfzhs;//所查身份证号码列表

    private String sfzh;

    private boolean i_one_ok = false;
    private boolean i_two_ok = false;
    private RyzhxxResBean ryzhxxResBean;
    private InspectPersonJsonRet inspectPersonJsonRet;

    private boolean isAllPeople = true;

    private int inflated_view_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_batch_check);
        showNavIcon(true);
        setToolbarTitle("人员批量查询");
        sfzhs = new ArrayList<>();
        initView();
    }

    @Override
    public void handleIntent() {
    }

    private void initView() {
        tv_hc_loaction.setText(V_config.hc_address);
    }

    @OnClick({R.id.query, R.id.filter})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                sfzh = et_id_input.getText().toString();
                if (UtilsString.checkId(sfzh).equals("")) {
                    Toast.makeText(this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (sfzhs.contains(sfzh)) {
                    Toast.makeText(this, "已核查过该人员", Toast.LENGTH_SHORT).show();
                    break;
                }
                queryYdjwDataNoDialog("PERSON_COMPLEX",V_config.PERSON_COMPLEX);
                queryYdjwDataNoDialog("INSPECT_PERSON",V_config.INSPECT_PERSON);
                queryYdjwDataX();

                et_id_input.setText("");
                break;
            case R.id.filter:
                if (isAllPeople) {
                    for (int i = 0; i < ll_container.getChildCount(); i++) {
                        View child = ll_container.getChildAt(i);
                        if (!(boolean) child.getTag()) {
                            child.setVisibility(View.GONE);
                        }
                        btn_filter.setText("显示全部人员");
                    }
                    isAllPeople = false;
                } else {
                    for (int i = 0; i < ll_container.getChildCount(); i++) {
                        View child = ll_container.getChildAt(i);
                        if (!(boolean) child.getTag()) {
                            child.setVisibility(View.VISIBLE);
                        }
                        btn_filter.setText("显示关注人员");
                    }
                    isAllPeople = true;
                    break;
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
            case V_config.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicRequest(inspectPersonReqBean);
                Request request = new Request();
                inspectPersonReqBean.setYhdm(V_config.YHDM);
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
            case V_config.PERSON_COMPLEX:
                ryzhxxResBean = (RyzhxxResBean) resultBase;
                i_one_ok = true;
                inflateView();
                break;
            case V_config.INSPECT_PERSON:
                inspectPersonJsonRet = (InspectPersonJsonRet) resultBase;
                i_two_ok = true;
                inflateView();
                break;
        }
    }

    private void inflateView() {
        if (!i_one_ok || !i_two_ok) {
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.rv_batch_check_item, ll_container, false);
        view.setTag(false);//默认都为非关注人员
        TextView xm = view.findViewById(R.id.xm);
        TextView xb = view.findViewById(R.id.xb);
        TextView nl = view.findViewById(R.id.nl);
        TextView hjd = view.findViewById(R.id.hjd);
        TextView mz = view.findViewById(R.id.mz);
        final TextView sfzh = view.findViewById(R.id.sfzh);
        TextView hc_result = view.findViewById(R.id.hc_result);
        final Button btn_check = view.findViewById(R.id.check);
        Button btn_retry = view.findViewById(R.id.retry);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", sfzhs.get((int)btn_check.getTag()));
                hop2Activity(Ac_rycx.class, bundle);
            }
        });
        if (ryzhxxResBean != null) {
            xm.setText(ryzhxxResBean.getXm());
            xb.setText(ryzhxxResBean.getXb());
            nl.setText(ryzhxxResBean.getNl());
            hjd.setText(ryzhxxResBean.getHjdz());
            mz.setText(ryzhxxResBean.getMz());
            sfzh.setText(ryzhxxResBean.getSfzh());
        } else {
            btn_retry.setVisibility(View.VISIBLE);
        }
        if (inspectPersonJsonRet == null) {
            hc_result.setText("接口异常");
            return;
        } else {
            RyxxRes ryxx = inspectPersonJsonRet.getRyxx();

            if (ryxx == null) {
                hc_result.setText("无数据返回");
            } else {
                String result;
                if (ryxx.getHcjg().equals("000")) {
                    view.setTag(true);//关注人员
                    result = "<font color=\"#d13931\">" + ryxx.getHcjg() + "</font>";
                } else {
                    result = "<font color=\"#05b163\">" + ryxx.getHcjg() + "</font>";
                }
                hc_result.setText(Html.fromHtml(result));
            }
        }
        ll_container.addView(view, 0);

        btn_filter.setVisibility(View.VISIBLE);
        tv_hc_str.setVisibility(View.VISIBLE);//显示过滤信息

        i_one_ok = false;
        i_two_ok = false;
        sfzhs.add(this.sfzh);
        btn_check.setTag(sfzhs.size() - 1);


    }
}
