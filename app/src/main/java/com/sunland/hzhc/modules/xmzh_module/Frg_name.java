package com.sunland.hzhc.modules.xmzh_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.concretejungle.spinbutton.SpinButton;
import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class Frg_name extends Frg_base {

    @BindView(R.id.gender)
    public SpinButton sb_gender;
    @BindView(R.id.name)
    public EditText et_name;
    @BindView(R.id.birth)
    public TextView tv_birth;
    @BindView(R.id.territory)
    public TextView tv_territory;

    private final int REQ_JG = 0;

    private String code;

    // TODO: 2018/12/4/004  NFC与ocr识别后数据也要走到Frg_name中
    @Override
    public int setLayoutId() {
        return R.layout.frg_name;
    }

    @Override
    public void initView() {
        sb_gender.setHeaderTitle("选择性别");
        sb_gender.setDataSet(Arrays.asList(DataModel.GENDERS));
        sb_gender.setSelection(0);
    }

    @OnClick({R.id.query, R.id.birth, R.id.territory})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String xm = et_name.getText().toString();
                int xb = sb_gender.getSelectedItemPosition() + 1;
                String csrq = tv_birth.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("xm", xm);
                bundle.putInt("xb", xb);
                bundle.putString("csrq", csrq);
                bundle.putString("hjqh", code);
                if((xm==null||xm.isEmpty())&&(csrq==null||csrq.isEmpty())&&(code==null||code.isEmpty())){
                    Toast.makeText(context,"姓名，出生日期和地区至少选填一个",Toast.LENGTH_SHORT).show();
                }else {
                    ((Ac_main) context).hop2Activity(Ac_xmzh_list.class, bundle);
                }

                break;
            case R.id.birth:
                openDatePicker(tv_birth);
                break;
            case R.id.territory:
                hop2ActivityForResult(Ac_jg_list.class, REQ_JG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_JG) {
            if (resultCode == RESULT_OK) {
                code = data.getStringExtra("code");
                tv_territory.setText(data.getStringExtra("name"));
            }
        }
    }

    public void unbpdateView(String name, String gender, String birth) {
        et_name.setText(name);
        tv_birth.setText(birth);
    }
}
