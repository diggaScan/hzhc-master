package com.sunland.hzhc.modules.p_archive_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_archive extends Ac_base {

    @BindView(R.id.tab_layout)
    public TabLayout tl_items;
    @BindView(R.id.viewPager)
    public ViewPager vp_frgs;

    public String identity_num;
    public int tab_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_archive);
        showNavIcon(true);
        setToolbarTitle("个人综合信息");
        handleIntent();
        initView();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                identity_num = bundle.getString("id");
                tab_id = bundle.getInt("tab_id");
            }
        }
    }


    public void initView() {
        List<Fragment> frgs = new ArrayList<>();
        frgs.add(new Frg_archive());
        frgs.add(new Frg_focus());
        frgs.add(new Frg_track());
        String[] names = {"档案信息", "关注信息", "轨迹信息"};
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), frgs, names);
        vp_frgs.setAdapter(myViewPagerAdapter);
        vp_frgs.setOffscreenPageLimit(3);
        tl_items.setupWithViewPager(vp_frgs);
        vp_frgs.setCurrentItem(tab_id);
        vp_frgs.setOffscreenPageLimit(3);
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> dataSet;
        private String[] names;

        MyViewPagerAdapter(FragmentManager fm, List<Fragment> dataSet, String[] names) {
            super(fm);
            this.dataSet = dataSet;
            this.names = names;
        }

        @Override
        public Fragment getItem(int arg0) {
            return dataSet.get(arg0);
        }

        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return names[position];
        }
    }

    public String getIdentity_num() {
        return identity_num;
    }
}
