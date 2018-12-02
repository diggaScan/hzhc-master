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

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.modules.jdc_module.CzsycReqBean;
import com.sunland.hzhc.modules.own_car_module.own_car_bean.CarBaseInfo;
import com.sunland.hzhc.modules.own_car_module.own_car_bean.OwnedCarResBean;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_car_list extends Ac_base implements OnRequestCallback {

    @BindView(R.id.car_list)
    public RecyclerView rv_car_list;

    private List<CarBaseInfo> car_list;
    private MyRvAdapter adapter;
    private String sfzh;//身份证号码
    private RequestManager mRequestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_car_list);
        showNavIcon(true);
        setToolbarTitle("名下机动车");
        handleIntent();
        initView();
        queryYdjwData(Dictionary.GET_CAR_INFO_BY_SFZH);
    }

    private void handleIntent() {
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

    public void queryYdjwData(String method_name) {
        mRequestManager = new RequestManager(this, this);
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequest();
    }

    public BaseRequestBean assembleRequestObj(String reqName) {
        CzsycReqBean czsycReqBean = new CzsycReqBean();
        assembleBasicObj(czsycReqBean);
        czsycReqBean.setSfzh(sfzh);
        return czsycReqBean;
    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        OwnedCarResBean ownedCarResBean = (OwnedCarResBean) bean;
        List<CarBaseInfo> temp_list = ownedCarResBean.getCarList();
        if (temp_list == null) {
            return;
        } else {
            car_list.clear();
            car_list.addAll(temp_list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return OwnedCarResBean.class;
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
