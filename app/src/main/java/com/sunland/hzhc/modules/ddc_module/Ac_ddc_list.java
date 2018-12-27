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
import android.widget.Toast;

import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_e_bike_info.DdcListResBean;
import com.sunland.hzhc.bean.i_e_bike_info.DdcxxplReqBean;
import com.sunland.hzhc.bean.i_e_bike_info.InfoDDCXQs;
import com.sunland.hzhc.customView.DragToRefreshView.DragToRefreshView;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_ddc_list extends Ac_base_info {


    @BindView(R.id.ddc_list)
    public RecyclerView rv_ddc_list;
    @BindView(R.id.drag2Refresh)
    public DragToRefreshView d2r_refresh;
    private String hphm;
    private String fdjh;
    private String cjh;
    private List<InfoDDCXQs> dataSet;
    private MyRvAdapter adapter;
    private final int items_per_page = 50;
    private boolean isFromSsj;

    private int cur_page = 1;
    private int add_pages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_ddc_list);
        showNavIcon(true);
        setToolbarTitle("电动车查询列表");
        handleIntent();
        initView();
        showLoading_layout(true);
        queryYdjwDataNoDialog(V_config.GET_ELECTRIC_CAR_INFO);
        queryYdjwDataX("");
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
        d2r_refresh.unableHeaderRefresh(false);
        d2r_refresh.setUpdateListener(new DragToRefreshView.OnUpdateListener() {
            @Override
            public void onRefreshing(DragToRefreshView view) {
                if (view.isFooterRefreshing()) {
                    add_pages++;
                    cur_page = 1 + add_pages;
                    queryYdjwDataNoDialog(V_config.GET_ELECTRIC_CAR_INFO);
                    queryYdjwDataX("");
                }
            }

            @Override
            public void onFinished(DragToRefreshView view) {
                if (view.getState() == DragToRefreshView.State.footer_release_to_load) {
                    int scroll_position = dataSet.size() - items_per_page;
                    if (scroll_position > 0) {
                        rv_ddc_list.scrollToPosition(dataSet.size());
                    }
                }
            }
        });
        d2r_refresh.addMainContent(rv_ddc_list);
    }

    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_ELECTRIC_CAR_INFO:
                DdcxxplReqBean ddcxxplReqBean = new DdcxxplReqBean();
                ddcxxplReqBean.setCurrentPage(cur_page);
                ddcxxplReqBean.setTotalCount(items_per_page);
                ddcxxplReqBean.setHphm(hphm);
                ddcxxplReqBean.setCjh(cjh);
                ddcxxplReqBean.setFdjh(fdjh);
                return ddcxxplReqBean;
        }
        return null;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase bean) {
        switch (reqName) {
            case V_config.GET_ELECTRIC_CAR_INFO:
                showLoading_layout(false);
                d2r_refresh.dismiss();
                DdcListResBean ddcListResBean = (DdcListResBean) bean;
                if (ddcListResBean == null) {
                    Toast.makeText(this, "电动车批量查询接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<InfoDDCXQs> infoDDCXQs = ddcListResBean.getInfoDDCXQs();
                if (infoDDCXQs == null || infoDDCXQs.isEmpty()) {
                    Toast.makeText(this, "无相关电动车信息返回", Toast.LENGTH_SHORT).show();
                    return;
                }
                dataSet.addAll(infoDDCXQs);
                adapter.notifyItemRangeInserted(dataSet.size(), infoDDCXQs.size());
                if (dataSet.size() < items_per_page) {
                    d2r_refresh.unableFooterRefresh(false);
                }
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
