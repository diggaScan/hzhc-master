package com.sunland.hzhc.modules.sfz_module;

import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.sunlandkeyboard.SunlandKeyBoard;

import butterknife.BindView;
import butterknife.OnClick;

public class Frg_id extends Frg_base {

    @BindView(R.id.id_input)
    public EditText et_id;

    @Override
    public int setLayoutId() {
        return R.layout.frg_id;
    }

    @Override
    public void initView() {


    }

    @OnClick(R.id.query)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String identity = et_id.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("id", identity);
                ((Ac_main) context).hop2Activity(Ac_rycx.class, bundle);
                break;
        }
    }
}
