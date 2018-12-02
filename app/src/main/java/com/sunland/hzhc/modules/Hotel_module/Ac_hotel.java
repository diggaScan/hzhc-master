package com.sunland.hzhc.modules.Hotel_module;

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

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.Hotel_module.bean.InfoLGZSRY;
import com.sunland.hzhc.modules.Hotel_module.bean.LGResBean;
import com.sunland.hzhc.modules.sfz_module.Ac_rycx;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.List;

import butterknife.BindView;

public class Ac_hotel extends Ac_base_info {
    private String lgmc;
    private String fjh;
    private String rzrq_q;
    private String rzrq_z;

    @BindView(R.id.ry_info)
    public RecyclerView rv_ry_info;

    private MyRvAdapter adapter;


    private List<InfoLGZSRY> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_hotel);
        showNavIcon(true);
        setToolbarTitle("入住旅客列表");
        queryYdjwData(Dictionary.GET_PERSON_IN_HOTEL_INFO);
    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                lgmc = bundle.getString("lgmc");
                fjh = bundle.getString("fjh");
                rzrq_q = bundle.getString("rzrq_q");
                rzrq_q = bundle.getString("rzrq_z");
            }
        }
    }


    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        PllgrzReqBean bean = new PllgrzReqBean();
        assembleBasicObj(bean);
        bean.setLgmc(lgmc);
        bean.setFjh(fjh);
        bean.setRzrq_q(rzrq_q);
        bean.setRerq_z(rzrq_z);
        bean.setCurrentPage(1);
        bean.setTotalCount(50);
        return bean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case Dictionary.GET_PERSON_IN_HOTEL_INFO:
                LGResBean lgResBean = (LGResBean) resultBase;
                List<InfoLGZSRY> infoLGZSRIES = lgResBean.getInfoLGZSRYs();
                if (infoLGZSRIES != null) {
                    dataSet = lgResBean.getInfoLGZSRYs();
                    initRv();
                } else {
                    Toast.makeText(this, "为获取住店旅客信息", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void initRv() {
        adapter = new MyRvAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_ry_info.setLayoutManager(manager);
        rv_ry_info.setAdapter(adapter);
        rv_ry_info.addItemDecoration(new Rv_Item_decoration(this));
    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

        private List<InfoLGZSRY> dataSet;
        private LayoutInflater inflater;

        public MyRvAdapter(List<InfoLGZSRY> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_rzry_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            InfoLGZSRY info = dataSet.get(i);
            final String sfzh = info.getSfzh();
            myViewHolder.tv_gender.setText(info.getXb());
            myViewHolder.tv_identity.setText(sfzh);
            myViewHolder.tv_name.setText(info.getLkxm());
            myViewHolder.tv_fjh.setText(info.getFjh());
            myViewHolder.tv_identity.setText(info.getSfzh());
            myViewHolder.tv_rzsj.setText(Long.toString(info.getRzsj()));
            myViewHolder.tv_tfsj.setText(Long.toString(info.getTfsj()));
            myViewHolder.rl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sfzh != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", sfzh);
                        hop2Activity(Ac_rycx.class, bundle);
                    } else {
                        Toast.makeText(Ac_hotel.this, "无法获取此人身份证号码", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_fjh;
            TextView tv_name;
            TextView tv_tfsj;
            TextView tv_rzsj;
            TextView tv_gender;
            TextView tv_identity;
            RelativeLayout rl_container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                rl_container = itemView.findViewById(R.id.container);
                tv_fjh = itemView.findViewById(R.id.fjh);
                tv_name = itemView.findViewById(R.id.name);
                tv_tfsj = itemView.findViewById(R.id.tfsj);
                tv_rzsj = itemView.findViewById(R.id.rzsj);
                tv_gender = itemView.findViewById(R.id.gender);
                tv_identity = itemView.findViewById(R.id.identity);


            }
        }
    }
}
