package com.sunland.hzhc.modules.phone_num_module;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;

import butterknife.BindView;
import butterknife.OnClick;

public class Frg_phone_num extends Frg_base {

    @BindView(R.id.phone_num)
    public EditText et_phone_num;

    @Override
    public int setLayoutId() {
        return R.layout.frg_phone_num;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.query)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String dhhm = et_phone_num.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("dhhm", dhhm);
                if(dhhm.isEmpty()){
                    Toast.makeText(context,"请输入电话号码",Toast.LENGTH_SHORT).show();
                }else {
                    ((Ac_main) context).hop2Activity(Ac_phone_num.class, bundle);
                }

                break;
        }
    }


}
