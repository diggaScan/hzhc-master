package com.sunland.hzhc.modules.case_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_case_info.AjxxReqbean;
import com.sunland.hzhc.bean.i_case_info.CaseInfoDto;
import com.sunland.hzhc.bean.i_case_info.CaseListResBean;
import com.sunland.hzhc.bean.i_case_info.InfoAJLB;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.xmzh_module.Rv_Jg_adapter;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_case extends Ac_base_info {

    @BindView(R.id.case_list)
    public RecyclerView rv_case_list;
    private String ajbh;
    private String ajlb;
    private String afsj;
    private String gxdw;
    private MyRvAdapter adapter;
    private List<InfoAJLB> dataSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_case);
        showNavIcon(true);
        setToolbarTitle("案件列表");
        dataSet = new ArrayList<>();
        queryYdjwDataNoDialog("CASE_INFO",V_config.CASE_INFO);
        queryYdjwDataX();
        showLoading_layout(true);
    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                ajbh = bundle.getString("ajbh");
                ajlb = bundle.getString("ajlb");
                afsj = bundle.getString("afsj");
                gxdw = bundle.getString("gxdw");
            }
        }
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        AjxxReqbean ajxxReqbean = new AjxxReqbean();
        assembleBasicRequest(ajxxReqbean);
        CaseInfoDto caseInfoDto = new CaseInfoDto();
        caseInfoDto.setAjbh(ajbh);
        caseInfoDto.setAjlb(ajlb);
        caseInfoDto.setAfsj(afsj);
        caseInfoDto.setGxdw(gxdw);
        ajxxReqbean.setCaseInfoDto(caseInfoDto);
        ajxxReqbean.setCurrentPage(1);
        ajxxReqbean.setTotalCount(50);
        return ajxxReqbean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.CASE_INFO:
                showLoading_layout(false);
                CaseListResBean caseListResBean = (CaseListResBean) resultBase;
                if (caseListResBean == null) {
                    Toast.makeText(this, "案件信息接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                dataSet.clear();
                List<InfoAJLB> infoAJLBS = caseListResBean.getInfoAJLBs();
                if (infoAJLBS != null) {
                    dataSet.addAll(caseListResBean.getInfoAJLBs());
                    initRv();
                } else {
                    Toast.makeText(this, "未找到相应案件", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initRv() {
        adapter = new MyRvAdapter(dataSet);
        adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                InfoAJLB info = dataSet.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("ajbh", info.getAjbh());
                bundle.putString("afsj", info.getAfsj());
                bundle.putString("ajlb", info.getAjlb());
                bundle.putString("ajzt", info.getAjzt());
                bundle.putString("gxdw", info.getGxdw());
                bundle.putString("jyaq", info.getJyaq());
                hop2Activity(Ac_case_detail.class, bundle);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_case_list.setLayoutManager(manager);
        rv_case_list.setAdapter(adapter);
        rv_case_list.addItemDecoration(new Rv_Item_decoration(this));

    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

        private List<InfoAJLB> dataSet;
        private LayoutInflater inflater;
        private Rv_Jg_adapter.OnItemClickedListener onItemClickedListener;

        public MyRvAdapter(List<InfoAJLB> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        public void setOnItemClickedListener(Rv_Jg_adapter.OnItemClickedListener onItemClickedListener) {
            this.onItemClickedListener = onItemClickedListener;
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_case_list_item, viewGroup, false);
            return new MyRvAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, final int i) {
            InfoAJLB info = dataSet.get(i);
            myViewHolder.tv_afsj.setText(info.getAfsj());
            myViewHolder.tv_ajbh.setText(info.getAjbh());
            myViewHolder.tv_ajlb.setText(info.getAjlb());
            myViewHolder.tv_gxdw.setText(info.getGxdw());
            myViewHolder.tv_jyaq.setText(info.getJyaq());

            myViewHolder.rl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(i);
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_ajlb;
            TextView tv_afsj;
            TextView tv_gxdw;
            TextView tv_jyaq;
            TextView tv_ajbh;

            RelativeLayout rl_container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                rl_container = itemView.findViewById(R.id.case_container);
                tv_ajlb = itemView.findViewById(R.id.ajlb);
                tv_afsj = itemView.findViewById(R.id.afsj);
                tv_gxdw = itemView.findViewById(R.id.gxdw);
                tv_jyaq = itemView.findViewById(R.id.jyaq);
                tv_ajbh = itemView.findViewById(R.id.ajbh);

            }
        }
    }

}
