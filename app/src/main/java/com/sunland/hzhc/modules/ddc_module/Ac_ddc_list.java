package com.sunland.hzhc.modules.ddc_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.R;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.modules.ddc_module.bean.DdcListResBean;
import com.sunland.hzhc.modules.ddc_module.bean.InfoDDCXQs;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_ddc_list extends Ac_base implements OnRequestCallback {


    @BindView(R.id.ddc_list)
    public RecyclerView rv_ddc_list;

    private String hphm;
    private String fdjh;
    private String cjh;
    private RequestManager mRequestManager;
    private List<InfoDDCXQs> dataSet;
    private MyRvAdapter adapter;

    private boolean isFromSsj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_ddc_list);
        showNavIcon(true);
        setToolbarTitle("电动车查询列表");
        handleIntent();
        initView();
    }


    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                hphm = bundle.getString("hphm");
                fdjh = bundle.getString("fdjh");
                cjh = bundle.getString("cjh");
                isFromSsj = bundle.getBoolean(DataModel.FROM_SSJ_FLAG, false);
            }
        }
    }

    private void initView() {
        dataSet = new ArrayList<>();
        adapter = new MyRvAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_ddc_list.setAdapter(adapter);
        rv_ddc_list.setLayoutManager(manager);
        rv_ddc_list.addItemDecoration(new Rv_Item_decoration(this));
        mRequestManager = new RequestManager(this, this);
        queryYdjwData(V_config.GET_ELECTRIC_CAR_INFO);
    }

    public void queryYdjwData(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequest();
    }

    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_ELECTRIC_CAR_INFO:
                DdcxxplReqBean ddcxxplReqBean = new DdcxxplReqBean();
                ddcxxplReqBean.setCurrentPage(1);
                ddcxxplReqBean.setTotalCount(50);
                ddcxxplReqBean.setHphm(hphm);
                ddcxxplReqBean.setCjh(cjh);
                ddcxxplReqBean.setFdjh(fdjh);
                return ddcxxplReqBean;
        }
        return null;
    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        switch (reqName) {
            case V_config.GET_ELECTRIC_CAR_INFO:
                DdcListResBean ddcListResBean = (DdcListResBean) bean;
                if (ddcListResBean == null) {
                    return;
                }
                List<InfoDDCXQs> infoDDCXQs = ddcListResBean.getInfoDDCXQs();
                if (infoDDCXQs == null || infoDDCXQs.isEmpty()) {
                    return;
                }
                dataSet.clear();
                dataSet.addAll(infoDDCXQs);
                adapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SSJ) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getBundleExtra("bundle");
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }

    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return DdcListResBean.class;
    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

        private List<InfoDDCXQs> dataSet;
        private LayoutInflater inflater;

        MyRvAdapter(List<InfoDDCXQs> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_ddc_list_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            final InfoDDCXQs info = dataSet.get(i);
            myViewHolder.tv_name.setText(info.getCzxm());
            myViewHolder.tv_identity.setText(info.getCz_sfzh());
            myViewHolder.tv_brand.setText(info.getClpp());
            myViewHolder.tv_cph.setText(info.getHphm());
            myViewHolder.tv_info_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("cz_sfzh", info.getCz_sfzh());
                    bundle.putString("czxm", info.getCzxm());
                    bundle.putString("hphm", info.getHphm());
                    bundle.putString("clpp", info.getClpp());
                    bundle.putString("cjh", info.getCjh());
                    bundle.putString("fdjh", info.getFdjh());
                    if (isFromSsj) {
                        bundle.putBoolean(DataModel.FROM_SSJ_FLAG, true);
                        hopWithssj(Ac_ddc.class, bundle);
                    } else {

                        hop2Activity(Ac_ddc.class, bundle);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_cph;
            TextView tv_name;
            TextView tv_brand;
            TextView tv_identity;
            RelativeLayout tv_info_container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_info_container = itemView.findViewById(R.id.info_container);
                tv_cph = itemView.findViewById(R.id.cph);
                tv_name = itemView.findViewById(R.id.name);
                tv_brand = itemView.findViewById(R.id.brand);
                tv_identity = itemView.findViewById(R.id.identity);
            }
        }
    }

}
