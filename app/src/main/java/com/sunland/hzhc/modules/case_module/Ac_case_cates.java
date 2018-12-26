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
import android.widget.Toast;

import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_case_cate.CaseCateResBean;
import com.sunland.hzhc.bean.i_charge_case.LbList;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_case_cates extends Ac_base_info {

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

        queryYdjwDataNoDialog(V_config.QUERY_ALL_CASE_CATEGORY);
        queryYdjwDataX("");
        showLoading_layout(true);
    }

    @Override
    public void handleIntent() {

    }

    private void initView() {
        dataSet = new ArrayList<>();
        adapter = new MyCateAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_cate_list.setLayoutManager(manager);
        rv_cate_list.setAdapter(adapter);
        rv_cate_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        BaseRequestBean bean = new BaseRequestBean();
        assembleBasicRequest(bean);
        return bean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.QUERY_ALL_CASE_CATEGORY:
                showLoading_layout(false);
                CaseCateResBean caseCateResBean = (CaseCateResBean) resultBase;
                if (caseCateResBean == null) {
                    Toast.makeText(this, "案件种类接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<LbList> lbList = caseCateResBean.getLbList();
                if (lbList == null || lbList.isEmpty()) {
                    Toast.makeText(this, "案件种类返回未空", Toast.LENGTH_SHORT).show();
                    return;
                }
                dataSet.clear();
                dataSet.addAll(lbList);
                adapter.notifyDataSetChanged();
                break;
        }
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
