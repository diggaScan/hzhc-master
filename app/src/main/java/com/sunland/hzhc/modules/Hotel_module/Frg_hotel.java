package com.sunland.hzhc.modules.Hotel_module;

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

public class Frg_hotel extends Frg_base {

    @BindView(R.id.hotel_name)
    public TextView tv_hotel_name;
    @BindView(R.id.room_num)
    public EditText et_room_num;
    @BindView(R.id.time_from)
    public TextView tv_time_from;
    @BindView(R.id.time_to)
    public TextView tv_time_to;

    private final int REQ_HOTEL = 0;
    private String lgdm;

    @Override
    public int setLayoutId() {
        return R.layout.frg_hotel;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.query, R.id.time_from, R.id.time_to, R.id.hotel_name})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String lgmc = tv_hotel_name.getText().toString();
                String fjh = et_room_num.getText().toString();
                String rzrq_q = tv_time_from.getText().toString();
                String rzrq_z = tv_time_to.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("lgmc", lgdm);
                bundle.putString("fjh", fjh);
                bundle.putString("rzrq_q", rzrq_q);
                bundle.putString("rzrq_z", rzrq_z);
                ((Ac_main) context).hop2Activity(Ac_hotel.class, bundle);
                break;
            case R.id.time_from:
                openDatePicker(tv_time_from);
                break;
            case R.id.time_to:
                openDatePicker(tv_time_to);
                break;
            case R.id.hotel_name:
                hop2ActivityForResult(Ac_hotel_name.class, REQ_HOTEL);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_HOTEL) {
            if (resultCode == RESULT_OK) {
                tv_hotel_name.setText(data.getStringExtra("name"));
                lgdm = data.getStringExtra("code");
            }
        }
    }
}
