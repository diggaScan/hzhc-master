package com.sunland.hzhc.modules.case_module;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.R;

import butterknife.BindView;

public class Ac_case_detail extends Ac_base {

    @BindView(R.id.ajbh)
    public TextView tv_ajbh;
    @BindView(R.id.afsj)
    public TextView tv_afsj;
    @BindView(R.id.ajlb)
    public TextView tv_ajlb;
    @BindView(R.id.ajzt)
    public TextView tv_ajzt;
    @BindView(R.id.gxdw)
    public TextView tv_gxdw;
    @BindView(R.id.jyaq)
    public TextView tv_jyaq;

    private String ajbh;
    private String afsj;
    private String ajlb;
    private String ajzt;
    private String gxdw;
    private String jyaq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_case_detail);
        showNavIcon(true);
        setToolbarTitle("案件详情");
        handleIntent();
        initView();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                ajbh = bundle.getString("ajbh");
                afsj = bundle.getString("afsj");
                ajlb = bundle.getString("ajlb");
                ajzt = bundle.getString("ajzt");
                gxdw = bundle.getString("gxdw");
                jyaq = bundle.getString("jyaq");
            }
        }
    }

    private void initView() {
        tv_afsj.setText(afsj);
        tv_ajbh.setText(ajbh);
        tv_ajlb.setText(ajlb);
        tv_ajzt.setText(ajzt);
        tv_gxdw.setText(gxdw);
        tv_jyaq.setText(jyaq);
    }
}
