package com.sunland.hzhc.modules.Internet_cafe_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class Frg_internet_cafe extends Frg_base {

    @BindView(R.id.bar_name)
    public TextView tv_bar_name;
    @BindView(R.id.machine_id)
    public EditText et_machine_id;
    @BindView(R.id.time_from)
    public TextView tv_time_from;
    @BindView(R.id.time_to)
    public TextView tv_time_to;

    private final int REQ_WB = 0;
    private String wbbh;

    @Override
    public int setLayoutId() {
        return R.layout.frg_internet_bar;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.query, R.id.time_from, R.id.time_to, R.id.bar_name})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String wbmc = tv_bar_name.getText().toString();
                String zjbh = et_machine_id.getText().toString();
                String swsj_q = tv_time_from.getText().toString();
                String swsj_z = tv_time_to.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("wbmc", wbmc);
                bundle.putString("zjbh", zjbh);
                bundle.putString("wbbh", wbbh);
                bundle.putString("swsj_q", swsj_q);
                bundle.putString("swsj_z", swsj_z);
                ((Ac_main) context).hop2Activity(Ac_internet_cafe.class, bundle);
                break;
            case R.id.time_from:
                openDatePicker(tv_time_from);
                break;
            case R.id.time_to:
                openDatePicker(tv_time_to);
                break;
            case R.id.bar_name:
                hop2ActivityForResult(Ac_internet_cafe_names.class, REQ_WB);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_WB) {
            if (resultCode == RESULT_OK) {
                tv_bar_name.setText(data.getStringExtra("name"));
                wbbh = data.getStringExtra("code");
            }
        }
    }
}
