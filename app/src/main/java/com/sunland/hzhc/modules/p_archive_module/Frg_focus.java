package com.sunland.hzhc.modules.p_archive_module;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.Global;
import com.sunlandgroup.utils.JsonUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
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
    @BindView(R.id.focus_str)
    public TextView tv_focus_str;
    @BindView(R.id.people_focus)
    public RecyclerView rv_people_focus;
//    @BindView(R.id.focus_container)
//    public RelativeLayout rl_focus_container;

    private String sfzh;//身份证号码
    private List<InfoGZXXResp> gzxx_list;//关注信息列表
    private Thread thread;
    private MyAdapter myAdapter;

//    private boolean load_person_focus_info;
    private boolean load_inspect_person;
    private boolean load_wanted;

    @Override
    public int setLayoutId() {
        return R.layout.frg_focus;
    }

    @Override
    public void initView() {
        gzxx_list = new ArrayList<>();
        btn_focus.setVisibility(View.GONE);
        btn_retry.setVisibility(View.GONE);
        sfzh = ((Ac_archive) context).identity_num;
//        rl_focus_container.setVisibility(View.GONE);
        myAdapter = new MyAdapter();
        rv_people_focus.setAdapter(myAdapter);
        rv_people_focus.setLayoutManager(new LinearLayoutManager(context));
        rv_people_focus.addItemDecoration(new Rv_Item_decoration(context));
    }

    private void queryWanted() {
        thread = new Thread(new Runnable() {
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
            queryYdjwDataNoDialog("INSPECT_PERSON", V_config.INSPECT_PERSON);
//            queryYdjwDataNoDialog("PERSON_FOCUS_INFO", V_config.PERSON_FOCUS_INFO);
            queryYdjwDataX();
            queryWanted();
        }
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
//            case V_config.PERSON_FOCUS_INFO:
//                FocusReqBean focusReqBean = new FocusReqBean();
//                assembleBasicRequest(focusReqBean);
//                focusReqBean.setSfzh(sfzh);
//                return focusReqBean;
            case V_config.INSPECT_PERSON:
                InspectPersonReqBean inspectPersonReqBean = new InspectPersonReqBean();
                assembleBasicRequest(inspectPersonReqBean);
                Request request = new Request();
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
        if (isDestroyView) {
            return;
        }
        switch (reqName) {
//            case V_config.PERSON_FOCUS_INFO:
//                PeopleFocusResBean peopleFocusResBean = (PeopleFocusResBean) bean;
//                if (peopleFocusResBean == null) {
//                    Toast.makeText(context, "人员关注信息接口异常", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                List<InfoGZXXResp> list = peopleFocusResBean.getGzxxList();
//                if (list == null || list.isEmpty()) {
//                    Toast.makeText(context, "人员关注信息无内容返回", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                load_person_focus_info = true;
//                for (InfoGZXXResp infoGZXXResp : list) {
//                    if (infoGZXXResp.getStatus().equals("2"))
//                        gzxx_list.add(infoGZXXResp);
//                }
//                if (gzxx_list != null && gzxx_list.size() > 0) {
//                    tv_focus_str.setVisibility(View.VISIBLE);
//                    tv_focus_str.setText("人员关注信息");
//                    tv_focus_str.setTextColor(Color.RED);
//                    rv_people_focus.setVisibility(View.VISIBLE);
//                } else {
//                    tv_focus_str.setVisibility(View.GONE);
//                }
//                myAdapter.notifyDataSetChanged();
//                load_person_focus_info = true;
//                break;
            case V_config.INSPECT_PERSON:
                InspectPersonJsonRet inspectPersonJsonRet = (InspectPersonJsonRet) bean;
                loading_hc.setVisibility(View.GONE);
                if (inspectPersonJsonRet == null) {
                    Toast.makeText(context, "杭州人核查接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                RyxxRes ryxxRes = inspectPersonJsonRet.getRyxx();
                if (ryxxRes == null) {
                    tv_hcjg.setText(Html.fromHtml("人核查接口:" + "<font color=\"#FF7F50\">" + inspectPersonJsonRet.getMessage() + "</font>"));
                    return;
                }


                String result;
                // TODO: 2019/1/8/008
                if ("通过".equals(ryxxRes.getHcjg())) {
                    result = "<font color=\"#05b163\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                } else {
                    result = "<font color=\"#d13931\">" + ryxxRes.getHcjg() + "</font>" + ryxxRes.getBjxx();
                }

                tv_hcjg.setText(Html.fromHtml(result));
                load_inspect_person = true;
                break;
        }
    }

    @Override
    public void onFragmentVisible() {
        super.onFragmentVisible();

        if (!load_inspect_person) {
            queryYdjwDataNoDialog("INSPECT_PERSON", V_config.INSPECT_PERSON);
        }
//        if (!load_person_focus_info) {
//            queryYdjwDataNoDialog("PERSON_FOCUS_INFO", V_config.PERSON_FOCUS_INFO);
//        }
        queryYdjwDataX();
        if (!load_wanted) {
            queryWanted();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        public MyAdapter() {
            super();
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.rv_people_focus_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder myViewHolder, int i) {
            InfoGZXXResp infoGZXXResp = gzxx_list.get(i);
            myViewHolder.tv_focus.setText(infoGZXXResp.getLb());
            if (!infoGZXXResp.getNr().isEmpty())
                myViewHolder.tv_nr.setText(infoGZXXResp.getNr());
        }

        @Override
        public int getItemCount() {
            return gzxx_list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_focus;
            TextView tv_nr;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_focus = itemView.findViewById(R.id.focus);
                tv_nr = itemView.findViewById(R.id.nr);
            }
        }
    }


}
