package com.sunland.hzhc;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunland.hzhc.recycler_config.Function_rv_adapter;
import com.sunland.hzhc.recycler_config.RvFuntionBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_functions extends Ac_base {

    @BindView(R.id.main)
    public RecyclerView rv_main;
    @BindView(R.id.secondary)
    public RecyclerView rv_secondary;
    @BindView(R.id.other)
    public RecyclerView rv_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_functions);
        showTollbar(false);
        initRv();
    }

    private void initRv() {
        List<RvFuntionBean> main = new ArrayList<>();
        Integer[] main_icons = new Integer[]{R.drawable.icon_sfz,
                R.drawable.icon_jdc, R.drawable.icon_fjdc,
                R.drawable.icon_xmzh, R.drawable.icon_plhc};
        String[] main_names = new String[]{
                "身份证", "机动车", "非机动车", "姓名组合", "批量核查"
        };
        for (int i = 0; i < 5; i++) {
            RvFuntionBean bean = new RvFuntionBean();
            bean.setFuntion_name(main_names[i]);
            bean.setImage_id(main_icons[i]);
            main.add(bean);
        }
        List<RvFuntionBean> secondary = new ArrayList<>();
        Integer[] sec_icons = new Integer[]{
                R.drawable.icon_ld, R.drawable.icon_wb, R.drawable.icon_dh,
                R.drawable.icon_aj, R.drawable.icon_jwry, R.drawable.icon_rl
        };
        String[] sec_names = new String[]{
                "旅店", "网吧", "电话", "案件", "境外人员", "人脸"
        };
        for (int i = 0; i < 6; i++) {
            RvFuntionBean bean = new RvFuntionBean();
            bean.setFuntion_name(sec_names[i]);
            bean.setImage_id(sec_icons[i]);
            secondary.add(bean);
        }
        List<RvFuntionBean> others = new ArrayList<>();
        Integer[] other_icons = new Integer[]{
                R.drawable.icon_ssj, R.drawable.icon_sz
        };
        String[] other_names = new String[]{
                "随手记", "设置"
        };
        for (int i = 0; i < 2; i++) {
            RvFuntionBean bean = new RvFuntionBean();
            bean.setFuntion_name(other_names[i]);
            bean.setImage_id(other_icons[i]);
            others.add(bean);
        }

        LinearLayoutManager main_llm = new LinearLayoutManager(this);
        main_llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        Function_rv_adapter main_adapter = new Function_rv_adapter(this, main);
        rv_main.setAdapter(main_adapter);
        rv_main.setLayoutManager(main_llm);

        LinearLayoutManager sec_llm = new LinearLayoutManager(this);
        sec_llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        Function_rv_adapter sec_adapter = new Function_rv_adapter(this, secondary);
        rv_secondary.setAdapter(sec_adapter);
        rv_secondary.setLayoutManager(sec_llm);

        LinearLayoutManager other_llm = new LinearLayoutManager(this);
        other_llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        Function_rv_adapter others_adapter = new Function_rv_adapter(this, others);
        rv_other.setAdapter(others_adapter);
        rv_other.setLayoutManager(other_llm);


    }
}
