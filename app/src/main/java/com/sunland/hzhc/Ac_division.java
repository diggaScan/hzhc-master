package com.sunland.hzhc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_an_bao_info.AnBaoReqBean;
import com.sunland.hzhc.bean.i_an_bao_info.AnBaoResBean;
import com.sunland.hzhc.bean.i_an_bao_info.AnbaoInfo;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_division extends Ac_base_info {
    @BindView(R.id.lmhc)
    public TextView tv_lmhc;
    @BindView(R.id.hchhl)
    public TextView tv_hchhl;
    @BindView(R.id.abhl)
    public TextView tv_abhl;
    @BindView(R.id.loading_layout)
    public FrameLayout loading_layout;
    @BindView(R.id.app_version)
    public TextView tv_app_version;
    private List<AnbaoInfo> flavours;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_division);
        initView();
        showToolbar(false);
        queryYdjwDataNoDialog("GET_AN_BAO_INFO", V_config.GET_AN_BAO_INFO);
        queryYdjwDataX();
    }

    public void initView() {

    }

    @Override
    public void handleIntent() {

    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_AN_BAO_INFO:
                AnBaoReqBean anBaoReqBean = new AnBaoReqBean();
                assembleBasicRequest(anBaoReqBean);
                return anBaoReqBean;
        }
        return null;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.GET_AN_BAO_INFO:
                loading_layout.setVisibility(View.GONE);
                AnBaoResBean anBaoResBean = (AnBaoResBean) resultBase;
                if (anBaoResBean == null) {
                    Toast.makeText(this, "版本信息接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<AnbaoInfo> list = anBaoResBean.getAnbaoList();
                if (list == null || list.isEmpty()) {
                    Toast.makeText(this, "无相关版本信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                this.flavours = list;

                break;
        }
    }


    @OnClick({R.id.abhl, R.id.lmhc, R.id.hchhl})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.lmhc:
                for (AnbaoInfo anbaoInfo : flavours) {
                    if (anbaoInfo.getEDITION().equals("02")) {
                        if (anbaoInfo.getISUSE().equals("1")) {
                            V_config.LBR = anbaoInfo.getEDITION();
                            hop2Activity(Ac_location.class);
                        } else {
                            Toast.makeText(this, "未被授权访问该板块", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.hchhl:
                for (AnbaoInfo anbaoInfo : flavours) {
                    if (anbaoInfo.getEDITION().equals("06")) {
                        if (anbaoInfo.getISUSE().equals("1")) {
                            V_config.LBR = anbaoInfo.getEDITION();
                            hop2Activity(Ac_location.class);
                        } else {
                            Toast.makeText(this, "未被授权访问该板块", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.abhl:
                for (AnbaoInfo anbaoInfo : flavours) {
                    if (anbaoInfo.getEDITION().equals("01")) {
                        if (anbaoInfo.getISUSE().equals("1")) {
                            V_config.LBR = anbaoInfo.getEDITION();
                            hop2Activity(Ac_location.class);
                        } else {
                            Toast.makeText(this, "未被授权访问该板块", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}
