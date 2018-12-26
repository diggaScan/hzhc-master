package com.sunland.hzhc;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfoRequestBean;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfoResBean;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_metro_address extends Ac_base implements OnRequestCallback {
    @BindView(R.id.id_input)
    public EditText et_id_input;
    @BindView(R.id.query)
    public Button btn_query;
    @BindView(R.id.metro_address)
    public RecyclerView rv_add;
    @BindView(R.id.address_str)
    public TextView tv_add_str;

    private RequestManager mRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_metro);
        showNavIcon(true);
        setToolbarTitle("地址查询");
        mRequestManager = new RequestManager(this, this);
    }

    @OnClick(R.id.query)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                queryYdjwData(V_config.SUBWAY_INFO);
                break;
        }
    }

    public void queryYdjwData(String reqName) {

        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, reqName, assembleRequestObj(reqName), 15000);
        mRequestManager.postRequestWithoutDialog();
    }

    private BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.SUBWAY_INFO:
                SubwayInfoRequestBean subwayInfoRequestBean = new SubwayInfoRequestBean();
                assembleBasicRequest(subwayInfoRequestBean);
                subwayInfoRequestBean.setYhdm("012146");
                subwayInfoRequestBean.setNumber(et_id_input.getText().toString());
                return subwayInfoRequestBean;
        }
        return null;
    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {

    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return SubwayInfoResBean.class;
    }
}
