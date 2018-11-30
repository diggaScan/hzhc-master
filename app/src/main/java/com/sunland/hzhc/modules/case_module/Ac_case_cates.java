package com.sunland.hzhc.modules.case_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunland.hzhc.modules.case_module.bean.CaseCateResBean;
import com.sunland.hzhc.modules.case_module.bean.LbList;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class Ac_case_cates extends Ac_base implements OnRequestCallback {

    @BindView(R.id.cate_list)
    public RecyclerView rv_cate_list;

    private List<LbList> dataSet;
    private MyCateAdapter adapter;
    private RequestManager mRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_case_cates);
        showNavIcon(true);
        setToolbarTitle("案件类型列表");
        initView();
        mRequestManager = new RequestManager(this, this);
        queryCaseCates(Dictionary.QUERY_ALL_CASE_CATEGORY);
    }

    private void initView() {
        dataSet = new ArrayList<>();
        adapter = new MyCateAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_cate_list.setLayoutManager(manager);
        rv_cate_list.setAdapter(adapter);
        rv_cate_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    private void queryCaseCates(String reqName) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, reqName
                , assembleRequestObj(), 15000);
        mRequestManager.postRequest();
    }

    private BaseRequestBean assembleRequestObj() {
        BaseRequestBean bean = new BaseRequestBean();
        assembleBasicObj(bean);
        return bean;
    }

    public void assembleBasicObj(BaseRequestBean baseRequestBean) {
        baseRequestBean.setYhdm("test");
        baseRequestBean.setImei(Global.imei);
        baseRequestBean.setImsi(Global.imsi1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pda_time = simpleDateFormat.format(date);
        baseRequestBean.setPdaTime(pda_time);

    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        switch (reqName) {
            case Dictionary.QUERY_ALL_CASE_CATEGORY:
                CaseCateResBean caseCateResBean = (CaseCateResBean) bean;
                dataSet.clear();
                dataSet.addAll(caseCateResBean.getLbList());
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return CaseCateResBean.class;
    }

    class MyCateAdapter extends RecyclerView.Adapter<MyCateAdapter.MyViewHolder> {

        List<LbList> dataSet;
        LayoutInflater inflater;

        public MyCateAdapter(List<LbList> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyCateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_case_cate_item, viewGroup, false);
            return new MyCateAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyCateAdapter.MyViewHolder myViewHolder, int i) {
            final LbList info = dataSet.get(i);
            myViewHolder.tv_name.setText(info.getName());
            myViewHolder.tv_code.setText(info.getCode());
            myViewHolder.cate_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("cate_code", info.getCode());
                    intent.putExtra("cate_name", info.getName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout cate_container;
            TextView tv_code;
            TextView tv_name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                cate_container = itemView.findViewById(R.id.cate_container);
                tv_code = itemView.findViewById(R.id.cate_code);
                tv_name = itemView.findViewById(R.id.cate_name);
            }
        }
    }

}
