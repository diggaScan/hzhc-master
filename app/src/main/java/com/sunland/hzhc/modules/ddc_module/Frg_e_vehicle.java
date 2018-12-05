package com.sunland.hzhc.modules.ddc_module;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;

import butterknife.BindView;
import butterknife.OnClick;

public class Frg_e_vehicle extends Frg_base {


    @BindView(R.id.number)
    public EditText et_number;
    @BindView(R.id.engine_num)
    public EditText et_engine_num;
    @BindView(R.id.frame_id)
    public EditText et_frame_id;

    @Override

    public int setLayoutId() {
        return R.layout.frg_e_vehicle;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.query)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String hphm = et_number.getText().toString();
                String cjh = et_frame_id.getText().toString();
                String fdjh = et_engine_num.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("hphm", hphm);
                bundle.putString("fdjh", fdjh);
                bundle.putString("cjh", cjh);
                if (((Ac_main) context).isFromSsj) {
                    bundle.putBoolean(DataModel.FROM_SSJ_FLAG, true);
                    ((Ac_main) context).hopWithssj(Ac_ddc_list.class, bundle);
                } else {
                    ((Ac_main) context).hop2Activity(Ac_ddc_list.class, bundle);
                }

                break;
        }
    }
}
