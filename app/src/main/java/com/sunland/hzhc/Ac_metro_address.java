package com.sunland.hzhc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfo;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfoRequestBean;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfoResBean;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_metro_address extends Ac_base_info {

    @BindView(R.id.id_input)
    public EditText et_id_input;
    @BindView(R.id.query)
    public Button btn_query;
    @BindView(R.id.metro_address)
    public RecyclerView rv_add;
    @BindView(R.id.address_str)
    public TextView tv_add_str;
    private List<SubwayInfo> sub_info;
    private MyRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_metro);
        showNavIcon(true);
        setToolbarTitle("地址查询");
        initView();
    }

    private void initView() {
        sub_info = new ArrayList<>();
        adapter = new MyRvAdapter();
        rv_add.setLayoutManager(new LinearLayoutManager(this));
        rv_add.setAdapter(adapter);
        rv_add.addItemDecoration(new Rv_Item_decoration(this));
    }

    @Override
    public void handleIntent() {

    }

    @OnClick(R.id.query)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String address_id = et_id_input.getText().toString();
                if (address_id == null || address_id.isEmpty()) {
                    Toast.makeText(this, "地铁区域号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (address_id.length() != 7) {
                    Toast.makeText(this, "请输入7位号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                queryYdjwDataNoDialog("SUBWAY_INFO",V_config.SUBWAY_INFO);
                queryYdjwDataX();
                btn_query.setEnabled(false);
                showLoading_layout(true);
                break;
        }
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.SUBWAY_INFO:
                SubwayInfoRequestBean subwayInfoRequestBean = new SubwayInfoRequestBean();
                assembleBasicRequest(subwayInfoRequestBean);
                subwayInfoRequestBean.setNumber(et_id_input.getText().toString());
                return subwayInfoRequestBean;
        }
        return null;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.SUBWAY_INFO:
                showLoading_layout(false);
                btn_query.setEnabled(true);
                SubwayInfoResBean subwayInfoResBean = (SubwayInfoResBean) resultBase;
                if (subwayInfoResBean == null) {
                    Toast.makeText(this, "地铁地址接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<SubwayInfo> list = subwayInfoResBean.getRows();
                if (list == null || list.isEmpty()) {
                    Toast.makeText(this, "地址信息为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                sub_info.clear();
                sub_info.addAll(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

        LayoutInflater layoutInflater;

        public MyRvAdapter() {
            super();
            layoutInflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = layoutInflater.inflate(R.layout.rv_subway_info, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            SubwayInfo si = sub_info.get(i);
            StringBuilder sb = new StringBuilder();
            final String address = sb.append(si.getLineName()).append(si.getStationName()).append(si.getPositionName()).toString();
            myViewHolder.tv_address.setText(address);
            myViewHolder.ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("metro_address", address);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            });
        }

        @Override
        public int getItemCount() {
            return sub_info.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_address;
            LinearLayout ll_container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_address = itemView.findViewById(R.id.address);
                ll_container = itemView.findViewById(R.id.container);
            }
        }
    }

}
