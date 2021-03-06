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

import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_hotel_people_info.PllgrzReqBean;
import com.sunland.hzhc.customView.DragToRefreshView.DragToRefreshView;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.bean.i_hotel_people_info.InfoLGZSRY;
import com.sunland.hzhc.bean.i_hotel_people_info.LGResBean;
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
    @BindView(R.id.drag2Refresh)
    public DragToRefreshView d2r_refresh;

    private MyRvAdapter adapter;

    private List<InfoLGZSRY> dataSet;

    private final int items_per_page = 50;

    private int cur_page = 1;
    private int add_pages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_hotel);
        showNavIcon(true);
        setToolbarTitle("入住旅客列表");
        queryYdjwDataNoDialog("GET_PERSON_IN_HOTEL_INFO",V_config.GET_PERSON_IN_HOTEL_INFO);
        queryYdjwDataX();
        showLoading_layout(true);
        initView();
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

    private void initView() {
        d2r_refresh.unableHeaderRefresh(false);
        d2r_refresh.setUpdateListener(new DragToRefreshView.OnUpdateListener() {
            @Override
            public void onRefreshing(DragToRefreshView view) {
                if (view.isFooterRefreshing()) {
                    add_pages++;
                    cur_page = 1 + add_pages;
                    queryYdjwDataNoDialog("GET_PERSON_IN_HOTEL_INFO",V_config.GET_PERSON_IN_HOTEL_INFO);
                    queryYdjwDataX();
                }
            }

            @Override
            public void onFinished(DragToRefreshView view) {
                if (view.getState() == DragToRefreshView.State.footer_release_to_load) {
                    int scroll_position = dataSet.size() - items_per_page;
                    if (scroll_position > 0) {
                        rv_ry_info.scrollToPosition(dataSet.size());
                    }
                }
            }
        });
        d2r_refresh.addMainContent(rv_ry_info);
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        PllgrzReqBean bean = new PllgrzReqBean();
        assembleBasicRequest(bean);
        bean.setLgmc(lgmc);
        bean.setFjh(fjh);
        bean.setRzrq_q(rzrq_q);
        bean.setRerq_z(rzrq_z);
        bean.setCurrentPage(cur_page);
        bean.setTotalCount(items_per_page);
        return bean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.GET_PERSON_IN_HOTEL_INFO:
                LGResBean lgResBean = (LGResBean) resultBase;
                d2r_refresh.dismiss();
                showLoading_layout(false);
                if (lgResBean == null) {
                Toast.makeText(this, "入住旅客信息接口异常", Toast.LENGTH_SHORT).show();
                    return;
            }
                dataSet = lgResBean.getInfoLGZSRYs();
                if (dataSet == null || dataSet.isEmpty()) {
                    Toast.makeText(this, "无相关住店旅客信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dataSet.size() < items_per_page) {
                    d2r_refresh.unableFooterRefresh(false);
                }
                initRv();
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
