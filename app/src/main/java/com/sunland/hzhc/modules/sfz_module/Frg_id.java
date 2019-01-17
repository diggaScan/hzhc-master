package com.sunland.hzhc.modules.sfz_module;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.utils.UtilsString;
import com.sunland.sunlandkeyboard.SunlandKeyBoardManager;

import butterknife.BindView;
import butterknife.OnClick;

public class Frg_id extends Frg_base {

    @BindView(R.id.id_input)
    public EditText et_id;
    @BindView(R.id.relativeLayout)
    public RelativeLayout rl_relativeLayout;
    @BindView(R.id.scrollView)
    public ScrollView scrollView;

    @Override
    public int setLayoutId() {
        return R.layout.frg_id;
    }

    @Override
    public void initView() {
        ((Ac_main) context).sunlandKeyBoardManager.addTarget(((Ac_main) context).myKeyBoardView, et_id,
                SunlandKeyBoardManager.KeyboardMode.IDENTITY);
        et_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!UtilsString.checkId(s.toString()).equals("")) {
                    Bundle bundle = new Bundle();
                    if (((Ac_main) context).isFromSsj) {
                        bundle.putString("id", s.toString());
                        bundle.putBoolean(DataModel.FROM_SSJ_FLAG, true);
                        ((Ac_main) context).hopWithssj(Ac_rycx.class, bundle);
                        ((Ac_main) context).sunlandKeyBoardManager.hideKeyboard();
                    } else {
                        bundle.putString("id", s.toString());
                        ((Ac_main) context).hop2Activity(Ac_rycx.class, bundle);
                        ((Ac_main) context).sunlandKeyBoardManager.hideKeyboard();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.query, R.id.nfc_scan, R.id.id_input})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String identity = et_id.getText().toString();
                if (UtilsString.checkId(identity).equals("")) {
                Toast.makeText(context, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                break;
            }
                Bundle bundle = new Bundle();
                if (((Ac_main) context).isFromSsj) {
                    bundle.putString("id", identity);
                    bundle.putBoolean(DataModel.FROM_SSJ_FLAG, true);
                    ((Ac_main) context).hopWithssj(Ac_rycx.class, bundle);
                } else {
                    bundle.putString("id", identity);
                    ((Ac_main) context).hop2Activity(Ac_rycx.class, bundle);
                }
                ((Ac_main) context).sunlandKeyBoardManager.hideKeyboard();
                break;
            case R.id.nfc_scan:
                Frg_nfc_ocr frg_nfc_ocr = new Frg_nfc_ocr();
                frg_nfc_ocr.show(((Ac_main) context).getSupportFragmentManager(), "dialog");
                break;
            case R.id.id_input:
                et_id.setCursorVisible(true);
                break;
        }
    }

    public void updateViews(Intent intent) {
        String num = intent.getStringExtra("identity");
        et_id.setText(num);
    }

    public void updateViews(String num) {
        et_id.setText(num);
    }


}
