package com.sunland.hzhc.modules.Internet_cafe_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunland.hzhc.modules.Internet_cafe_module.bean.InfoWBSWRY;
import com.sunland.hzhc.modules.Internet_cafe_module.bean.RyResBean;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

import butterknife.BindView;

public class Ac_internet_cafe extends Ac_base_info {
    private String wbmc;
    private String zjbh;
    private String wbbh;
    private String swsj_q;
    private String swsj_z;

    @BindView(R.id.ry_list)
    public RecyclerView rv_list;

    private MyRvAdapter adapter;
    private List<InfoWBSWRY> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_internet_cafe);
        queryYdjwData(Dictionary.GET_INTERNET_CAFE_PERSON_INFO);

    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                wbmc = bundle.getString("wbmc");
                zjbh = bundle.getString("zjbh");
                swsj_q = bundle.getString("swsj_q");
                swsj_z = bundle.getString("swsj_z");
                wbbh = bundle.getString("wbbh");
            }
        }
    }


    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        SwryListReqBean bean = new SwryListReqBean();
        assembleBasicObj(bean);
        bean.setWbmc(wbbh);
        bean.setZjbh(zjbh);
        bean.setSwsj_q(swsj_q);
        bean.setSwsj_z(swsj_z);
        bean.setCurrentPage(1);
        bean.setTotalCount(50);
        return bean;
    }


    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case Dictionary.GET_INTERNET_CAFE_PERSON_INFO:
                RyResBean ryResBean = (RyResBean) resultBase;
                dataSet = ryResBean.getInfoWBSWRYs();
                initRv();

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

        private List<InfoWBSWRY> dataSet;
        private LayoutInflater inflater;

        public MyRvAdapter(List<InfoWBSWRY> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_ry_internet_item, viewGroup, false);
            return new MyRvAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            InfoWBSWRY info = dataSet.get(i);
//            myViewHolder.tv_gender.setText(info.getXb());
            myViewHolder.tv_identity.setText(info.getSfzh());
            myViewHolder.tv_xm.setText(info.getXm());
            myViewHolder.tv_zjbh.setText(info.getZjbh());
            myViewHolder.tv_sxsj.setText(info.getSxsj() + "");
            myViewHolder.tv_xxsj.setText(info.getXxsj() + "");
        }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_zjbh;
            TextView tv_xm;
            TextView tv_xxsj;
            TextView tv_sxsj;
            TextView tv_identity;
            TextView tv_xx;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_zjbh = itemView.findViewById(R.id.zjbh);
                tv_xm = itemView.findViewById(R.id.xm);
                tv_xxsj = itemView.findViewById(R.id.xxsj);
                tv_sxsj = itemView.findViewById(R.id.sxsj);
                tv_identity = itemView.findViewById(R.id.sfzh);
                tv_xx = itemView.findViewById(R.id.xx);


            }
        }
    }
}
