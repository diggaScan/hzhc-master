package com.sunland.hzhc.modules.own_car_module;

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
import com.sunland.hzhc.bean.i_owned_car.CarBaseInfo;
import com.sunland.hzhc.bean.i_owned_car.CzsycReqBean;
import com.sunland.hzhc.bean.i_owned_car.OwnedCarResBean;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_car_list extends Ac_base_info {

    @BindView(R.id.car_list)
    public RecyclerView rv_car_list;
    private List<CarBaseInfo> car_list;
    private MyRvAdapter adapter;
    private String sfzh;//身份证号码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_car_list);
        showNavIcon(true);
        setToolbarTitle("名下机动车");
        handleIntent();
        initView();
        queryYdjwDataNoDialog(V_config.GET_CAR_INFO_BY_SFZH);
        queryYdjwDataX("");
        showLoading_layout(true);
    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                sfzh = bundle.getString("sfzh");
            }
        }
    }

    private void initView() {
        car_list = new ArrayList<>();
        adapter = new MyRvAdapter(car_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_car_list.setAdapter(adapter);
        rv_car_list.setLayoutManager(manager);
        rv_car_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    public BaseRequestBean assembleRequestObj(String reqName) {
        CzsycReqBean czsycReqBean = new CzsycReqBean();
        assembleBasicRequest(czsycReqBean);
        czsycReqBean.setSfzh(sfzh);
        return czsycReqBean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        OwnedCarResBean ownedCarResBean = (OwnedCarResBean) resultBase;
        showLoading_layout(false);
        if (ownedCarResBean == null) {
            Toast.makeText(this, "车主名下车辆接口异常", Toast.LENGTH_SHORT).show();
            return;
        }
        List<CarBaseInfo> temp_list = ownedCarResBean.getCarList();
        if (temp_list == null || temp_list.isEmpty()) {
            Toast.makeText(this, "无相关车辆信息", Toast.LENGTH_SHORT).show();
            return;
        }
        car_list.clear();
        car_list.addAll(temp_list);
        adapter.notifyDataSetChanged();
    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
        List<CarBaseInfo> dataSet;
        LayoutInflater inflater;

        public MyRvAdapter(List<CarBaseInfo> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_owner_car_list_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            CarBaseInfo info = dataSet.get(i);
            myViewHolder.tv_cphm.setText(info.getHphm());
            myViewHolder.tv_cllx.setText(info.getCllx_code());
            myViewHolder.ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_cphm;
            TextView tv_cllx;
            LinearLayout ll_container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_cphm = itemView.findViewById(R.id.cphm);
                tv_cllx = itemView.findViewById(R.id.cllx);
                ll_container = itemView.findViewById(R.id.car_container);
            }
        }
    }
}

