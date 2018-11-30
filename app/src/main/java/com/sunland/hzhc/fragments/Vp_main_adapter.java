package com.sunland.hzhc.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.List;

public class Vp_main_adapter extends FragmentPagerAdapter {

    private List<Fragment> dataSet;
    private List<String> names;

    public Vp_main_adapter(FragmentManager fm, List<Fragment> dataSet, List<String> names) {
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
        return names.get(position);
    }
}
