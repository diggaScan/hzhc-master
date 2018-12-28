package com.sunland.hzhc.modules.case_module;

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

public class Frg_case extends Frg_base {

    private final int REQ_CASE_CATE = 0;
    private final int REQ_DW = 1;
    @BindView(R.id.case_id)
    public EditText et_case_id;
    @BindView(R.id.case_cate)
    public TextView tv_case_cate;
    @BindView(R.id.case_time)
    public TextView tv_case_time;
    @BindView(R.id.case_src)
    public TextView tv_case_src;
    private String dwbh;
    private String lbbh;


    @Override
    public int setLayoutId() {
        return R.layout.frg_case;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.query, R.id.case_time, R.id.case_cate, R.id.case_src})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:
                String ajbh = et_case_id.getText().toString();
                String afsj = tv_case_time.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("ajbh", ajbh);
                bundle.putString("ajlb", lbbh);
                bundle.putString("afsj", afsj);
                bundle.putString("gxdw", dwbh);
                ((Ac_main) context).hop2Activity(Ac_case.class, bundle);
                break;
            case R.id.case_time:
                openDatePicker(tv_case_time);
                break;
            case R.id.case_cate:
                hop2ActivityForResult(Ac_case_cates.class, REQ_CASE_CATE);
                break;
            case R.id.case_src:
                hop2ActivityForResult(Ac_case_gxdw.class, REQ_DW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CASE_CATE:
                if (resultCode == RESULT_OK) {
                    tv_case_cate.setText(data.getStringExtra("cate_name"));
                    lbbh = data.getStringExtra("cate_code");
                }
                break;
            case REQ_DW:
                if (resultCode == RESULT_OK) {
                    tv_case_src.setText(data.getStringExtra("name"));
                    dwbh = data.getStringExtra("code");
                }
        }
    }
}
