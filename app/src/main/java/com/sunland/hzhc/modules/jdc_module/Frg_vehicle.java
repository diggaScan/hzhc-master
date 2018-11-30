package com.sunland.hzhc.modules.jdc_module;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.concretejungle.spinbutton.SpinButton;
import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.Dictionary;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;


public class Frg_vehicle extends Frg_base {
    @BindView(R.id.vehicle)
    public SpinButton sb_vehicle;
    @BindView(R.id.engine_num)
    public EditText et_engine_num;
    @BindView(R.id.vehicle_id)
    public EditText et_vehivle_id;
    @BindView(R.id.number)
    public EditText et_number;

    @Override
    public int setLayoutId() {
        return R.layout.frg_vehicle;
    }

    @Override
    public void initView() {

        sb_vehicle.setHeaderTitle("选择车辆类型");
        sb_vehicle.setDataSet(Arrays.asList(Dictionary.VEHICLEMODELS));
        sb_vehicle.setSelection(0);
    }

    @OnClick(R.id.query)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String cphm = et_number.getText().toString();
                String hpzl_str = sb_vehicle.getSelectedItem();
                String hpzl_num = Dictionary.VEHICLEMODLES.get(hpzl_str);
                String fdjh = et_engine_num.getText().toString();
                String clsbh = et_vehivle_id.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("cphm", cphm);
                bundle.putString("hpzl", hpzl_num);
                bundle.putString("fdjh", fdjh);
                bundle.putString("clsbh", clsbh);
                ((Ac_main) context).hop2Activity(Ac_clcx.class, bundle);
                break;
        }
    }
}
