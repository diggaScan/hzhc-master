package com.sunland.hzhc.modules.sfz_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;

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

    @OnClick({R.id.query, R.id.nfc_scan})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String identity = et_id.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("id", identity);
                ((Ac_main) context).hop2Activity(Ac_rycx.class, bundle);
                break;
            case R.id.nfc_scan:
                Frg_nfc_ocr frg_nfc_ocr = new Frg_nfc_ocr();
                frg_nfc_ocr.show(((Ac_main)context).getSupportFragmentManager(),"dialog");


                break;
        }
    }

    public void updateViews(Intent intent) {
        String num = intent.getStringExtra("identity");
        et_id.setText(num);
    }

    public void updateViews(String num){
        et_id.setText(num);
    }
}
