package com.sunland.hzhc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_division extends Ac_base {
    @BindView(R.id.lmhc)
    public TextView tv_lmhc;
    @BindView(R.id.hchhl)
    public TextView tv_hchhl;
    @BindView(R.id.abhl)
    public TextView tv_abhl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_division);
        showTollbar(false);

    }

    @OnClick({R.id.lmhc,R.id.hchhl,R.id.abhl})
    public void onTabChose(View view){
        int id=view.getId();
        switch (id){
            case R.id.lmhc:
//                hop2Activity(Ac_location.class);
                break;
            case R.id.hchhl:
                break;
            case R.id.abhl:
                break;

        }
    }
}
