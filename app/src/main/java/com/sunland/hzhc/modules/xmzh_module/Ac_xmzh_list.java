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
import android.widget.Toast;

import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_person_join_info.PersonInfo;
import com.sunland.hzhc.bean.i_person_join_info.PersonJoinDto;
import com.sunland.hzhc.bean.i_person_join_info.RycombineReqBean;
import com.sunland.hzhc.bean.i_person_join_info.XmzhResBean;
import com.sunland.hzhc.customView.DragToRefreshView.DragToRefreshView;
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
    @BindView(R.id.drag2Refresh)
    public DragToRefreshView d2r_refresh;
    private MyRvAdapter adapter;
    private List<PersonInfo> dataSet;
    private final int items_per_page = 50;

    private int cur_page = 1;
    private int add_pages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_xmzh_list);
        showNavIcon(true);
        setToolbarTitle("人员列表");
        initView();
        showLoading_layout(true);
        queryYdjwDataNoDialog(V_config.GET_PERSON_JOIN_INFO);
        queryYdjwDataX("");
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
        d2r_refresh.unableHeaderRefresh(false);
        d2r_refresh.setUpdateListener(new DragToRefreshView.OnUpdateListener() {
            @Override
            public void onRefreshing(DragToRefreshView view) {
                if (view.isFooterRefreshing()) {
                    add_pages++;
                    cur_page = 1 + add_pages;
                    queryYdjwDataNoDialog(V_config.GET_PERSON_JOIN_INFO);
                    queryYdjwDataX("");
                }
            }

            @Override
            public void onFinished(DragToRefreshView view) {
                if (view.getState() == DragToRefreshView.State.footer_release_to_load) {
                    int scroll_position = dataSet.size() - items_per_page;
                    if (scroll_position > 0) {
                        rv_name_list.scrollToPosition(dataSet.size());
                    }
                }
            }
        });
        d2r_refresh.addMainContent(rv_name_list);
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        RycombineReqBean bean = new RycombineReqBean();
        assembleBasicRequest(bean);
        PersonJoinDto personJoinDto = new PersonJoinDto();
        personJoinDto.setCsrq(csrq);
        personJoinDto.setHjqh(hjqh);
        personJoinDto.setXb(String.valueOf(xb));
        personJoinDto.setXm(xm);
        bean.setPersonJoinDto(personJoinDto);
        bean.setCurrentPage(cur_page);
        bean.setTotalCount(items_per_page);
        return bean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.GET_PERSON_JOIN_INFO:
                showLoading_layout(false);
                d2r_refresh.dismiss();
                XmzhResBean xmzhResBean = (XmzhResBean) resultBase;
                if (xmzhResBean == null) {
                    Toast.makeText(this, "人员姓名查询接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<PersonInfo> personInfos = xmzhResBean.getPersonList();
                if (personInfos == null || personInfos.isEmpty()) {
                    Toast.makeText(this, "无相关人员数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                dataSet.addAll(personInfos);
                adapter.notifyItemRangeInserted(dataSet.size(), personInfos.size());
                if (dataSet.size() < items_per_page) {
                    d2r_refresh.unableFooterRefresh(false);
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
