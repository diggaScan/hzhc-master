package com.sunland.hzhc.modules.abroad_module;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.concretejungle.spinbutton.SpinButton;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class Frg_abroad extends Frg_base {


    @BindView(R.id.paper_cate)
    public SpinButton sb_paper_cate;
    @BindView(R.id.nation)
    public TextView tv_nation;
    private final int GET_NATION=0;

    private String nation_code;
    private String nation_name;
    @Override
    public int setLayoutId() {
        return R.layout.frg_abroad;
    }

    @Override
    public void initView() {
        sb_paper_cate.setHeaderTitle("选择文件类型");
        sb_paper_cate.setDataSet(Arrays.asList(V_config.PAPER_CATEGORIES));
    }

    @OnClick(R.id.nation)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.nation:
                hop2ActivityForResult(Ac_nations_list.class,GET_NATION);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_NATION){
            if(resultCode==Activity.RESULT_OK){
                nation_code=data.getStringExtra("nation_code");
                nation_name=data.getStringExtra("nation_name");
                StringBuilder stringBuilder=new StringBuilder();
                tv_nation.setText(stringBuilder.append(nation_code).append("-").append(nation_name));
            }
        }

    }
}
