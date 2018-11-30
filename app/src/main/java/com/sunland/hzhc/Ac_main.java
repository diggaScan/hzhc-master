package com.sunland.hzhc;


import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunland.hzhc.fragments.Frg_batch_check;
import com.sunland.hzhc.fragments.Vp_main_adapter;
import com.sunland.hzhc.modules.Hotel_module.Frg_hotel;
import com.sunland.hzhc.modules.Internet_cafe_module.Frg_internet_cafe;
import com.sunland.hzhc.modules.abroad_module.Frg_abroad;
import com.sunland.hzhc.modules.case_module.Frg_case;
import com.sunland.hzhc.modules.ddc_module.Frg_e_vehicle;
import com.sunland.hzhc.modules.jdc_module.Frg_vehicle;
import com.sunland.hzhc.modules.phone_num_module.Frg_phone_num;
import com.sunland.hzhc.modules.sfz_module.Frg_id;
import com.sunland.hzhc.modules.xmzh_module.Frg_name;
import com.sunland.hzhc.utils.WindowInfoUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Ac_main extends CheckSelfPermissionActivity {

    @BindView(R.id.frg_container)
    public ViewPager vp_frg_container;
    @BindView(R.id.frg_tabs)
    public TabLayout tl_frg_tabs;
    @BindView(R.id.menu_btn)
    public ImageView iv_menu_btn;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.position_indicator)
    public BannerIndicator bannerIndicator;


    private final int REQ_MODULE = 0;
    private List<Fragment> dataSet;
    private Resources mRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
        mRes = getResources();
        initWindowStyle();
        initView();
    }

    private void initWindowStyle() {
        if (toolbar != null)
            setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
            float actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0);
            actionbarSizeTypedArray.recycle();
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    WindowInfoUtils.getStatusBarHeight(this) + (int) actionBarHeight);
            toolbar.setLayoutParams(lp);
        }
    }

    private void initView() {

        bannerIndicator.setItem_nums(10);
        bannerIndicator.setRadius(WindowInfoUtils.dp2px(this, 3));
        bannerIndicator.setDotsColor(mRes.getColor(R.color.dot_color));
        bannerIndicator.setMovingDotColor(mRes.getColor(R.color.colorAccent));

        dataSet = new ArrayList<>();
        dataSet.add(new Frg_id());
        dataSet.add(new Frg_vehicle());
        dataSet.add(new Frg_e_vehicle());
        dataSet.add(new Frg_name());
        dataSet.add(new Frg_batch_check());
        dataSet.add(new Frg_hotel());
        dataSet.add(new Frg_internet_cafe());
        dataSet.add(new Frg_phone_num());
        dataSet.add(new Frg_case());
        dataSet.add(new Frg_abroad());
        Vp_main_adapter vp_main_adapter = new Vp_main_adapter(getSupportFragmentManager(), dataSet, Arrays.asList(DataModel.MODULE_NAMES));
        vp_frg_container.setAdapter(vp_main_adapter);
        vp_frg_container.setOffscreenPageLimit(10);
        tl_frg_tabs.setupWithViewPager(vp_frg_container);
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < DataModel.MODULE_NAMES.length; i++) {
            View view = inflater.inflate(R.layout.custom_main_bot_tab, null);
            TextView tv_name = view.findViewById(R.id.tab_name);
            ImageView iv_icon = view.findViewById(R.id.tab_icon);
            iv_icon.setImageResource(Dictionary.TAB_ICONS_UNCLICKED[i]);
            tv_name.setText(DataModel.MODULE_NAMES[i]);
            tl_frg_tabs.getTabAt(i).setTag(i);
            tl_frg_tabs.getTabAt(i).setCustomView(view);
        }


        tl_frg_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = (int) tab.getTag();
                View view = tab.getCustomView();
                ImageView iv_icon = view.findViewById(R.id.tab_icon);
                iv_icon.setImageResource(Dictionary.TAB_ICONS_CLICKED[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = (int) tab.getTag();
                View view = tab.getCustomView();
                ImageView iv_icon = view.findViewById(R.id.tab_icon);
                iv_icon.setImageResource(Dictionary.TAB_ICONS_UNCLICKED[position]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        vp_frg_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                bannerIndicator.setCurrentPosition(i, v);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick(R.id.menu_btn)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.menu_btn:
                Intent intent = new Intent(this, Ac_module_list.class);
                startActivityForResult(intent, REQ_MODULE);
                break;
        }
    }

    public void hop2Activity(Class<? extends Ac_base> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    public void hop2Activity(Class<? extends Ac_base> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_MODULE) {
            if (resultCode == RESULT_OK) {
                int position=data.getIntExtra("position",-1);
                if(position!=-1){
                    vp_frg_container.setCurrentItem(position);
                }
            }
        }
    }
}
