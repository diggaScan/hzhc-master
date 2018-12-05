package com.sunland.hzhc.modules.xmzh_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.sfz_module.Ac_rycx;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_xmzh_list extends Ac_base_info {
    private String xm;
    private int xb;
    private String csrq;
    private String hjqh;

    @BindView(R.id.name_list)
    public RecyclerView rv_name_list;

    private MyRvAdapter adapter;
    private List<PersonInfo> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_xmzh_list);
        showNavIcon(true);
        setToolbarTitle("人员列表");
        initView();
        queryYdjwData(Dictionary.GET_PERSON_JOIN_INFO);
    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                xm = bundle.getString("xm");
                xb = bundle.getInt("xb");
                csrq = bundle.getString("csrq");
                hjqh = bundle.getString("hjqh");
            }
        }
    }

    private void initView() {
        dataSet = new ArrayList<>();
        adapter = new MyRvAdapter(dataSet);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        rv_name_list.setAdapter(adapter);
        rv_name_list.setLayoutManager(manager);
        rv_name_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        RycombineReqBean bean = new RycombineReqBean();
        assembleBasicObj(bean);
        PersonJoinDto personJoinDto = new PersonJoinDto();
        personJoinDto.setCsrq(csrq);
        personJoinDto.setHjqh(hjqh);
        personJoinDto.setXb(String.valueOf(xb));
        personJoinDto.setXm(xm);
        bean.setPersonJoinDto(personJoinDto);
        bean.setCurrentPage(1);
        bean.setTotalCount(10);
        return bean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case Dictionary.GET_PERSON_JOIN_INFO:
                XmzhResBean xmzhResBean = (XmzhResBean) resultBase;
                if (xmzhResBean != null) {
                    List<PersonInfo> personInfos = xmzhResBean.getPersonList();
                    if (personInfos != null && !personInfos.isEmpty()) {
                        dataSet.clear();
                        dataSet.addAll(personInfos);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

        private List<PersonInfo> dataSet;
        private LayoutInflater inflater;

        public MyRvAdapter(List<PersonInfo> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_xmzh_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            final PersonInfo info = dataSet.get(i);
            myViewHolder.tv_address.setText(info.getHjxz());
            myViewHolder.tv_age.setText(info.getNl());
            myViewHolder.tv_identity.setText(info.getSfzh());
            myViewHolder.tv_name.setText(info.getXm());
            myViewHolder.tv_gender.setText(info.getXb());
            myViewHolder.tv_src.setText(info.getSjly());
            myViewHolder.tv_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", info.getSfzh());
                    hop2Activity(Ac_rycx.class, bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name;
            TextView tv_identity;
            TextView tv_gender;
            TextView tv_age;
            TextView tv_address;
            TextView tv_src;
            RelativeLayout tv_container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_name = itemView.findViewById(R.id.name);
                tv_identity = itemView.findViewById(R.id.identity);
                tv_gender = itemView.findViewById(R.id.gender);
                tv_age = itemView.findViewById(R.id.age);
                tv_address = itemView.findViewById(R.id.address);
                tv_src = itemView.findViewById(R.id.src);
                tv_container = itemView.findViewById(R.id.container);
            }
        }
    }


}
