package com.sunland.hzhc.modules.phone_num_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.phone_num_module.bean.RyInfo;
import com.sunland.hzhc.modules.phone_num_module.bean.RyPhoneResBean;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_phone_num extends Ac_base_info {
    private String dhhm;

    @BindView(R.id.ry_list)
    public RecyclerView rv_list;

    private MyRvAdapter adapter;

    private List<RyInfo> dataSet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_phone_num);
        showNavIcon(true);
        setToolbarTitle("人员列表");

        dataSet = new ArrayList<>();
        queryYdjwData(Dictionary.GET_PERSON_MOBILE_JOIN_INFO);
    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                dhhm = bundle.getString("dhhm");
            }
        }
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        RydhjhReqBean bean = new RydhjhReqBean();
        assembleBasicObj(bean);
        bean.setDhhm(dhhm);
        return bean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case Dictionary.GET_PERSON_MOBILE_JOIN_INFO:
                RyPhoneResBean ryPhoneResBean = (RyPhoneResBean) resultBase;
                dataSet.clear();
                dataSet.addAll(ryPhoneResBean.getRyInfos());
                initRv();
                break;
        }
    }

    private void initRv() {
        adapter = new MyRvAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_list.setAdapter(adapter);
        rv_list.setLayoutManager(manager);
        rv_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

        private List<RyInfo> dataSet;
        private LayoutInflater inflater;

        public MyRvAdapter(List<RyInfo> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_phone_ry_item, viewGroup, false);
            return new MyRvAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            RyInfo info = dataSet.get(i);
            myViewHolder.tv_sfzh.setText(info.getSFZH());
            myViewHolder.tv_sjly.setText(info.getSjly());
            myViewHolder.tv_xm.setText(info.getXM());

        }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_sfzh;
            TextView tv_xm;
            TextView tv_sjly;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_sfzh = itemView.findViewById(R.id.sfzh);
                tv_xm = itemView.findViewById(R.id.xm);
                tv_sjly = itemView.findViewById(R.id.sjly);


            }
        }
    }


}
