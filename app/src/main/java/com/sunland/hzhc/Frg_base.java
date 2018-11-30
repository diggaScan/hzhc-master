package com.sunland.hzhc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunlandgroup.Global;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

public abstract class Frg_base extends Fragment {

    public Context context;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public void openDatePicker(final TextView textView) {
        final CustomPicker customPicker = new CustomPicker(getContext(), CustomPicker.DATE_MODE);
        customPicker.show();
        customPicker.setOnConfirmListener(new CustomPicker.OnConfirmListener() {
            @Override
            public void onConfirm() {
                String year = customPicker.getSelected_year() + "";
                String month;
                if (customPicker.getSelected_month() + 1 < 10) {
                    month = "0" + (customPicker.getSelected_month() + 1) + "";
                } else {
                    month = customPicker.getSelected_month() + 1 + "";
                }

                String day;
                if (customPicker.getSelected_day() < 10) {
                    day = "0" + customPicker.getSelected_day() + "";
                } else {
                    day = customPicker.getSelected_day() + "";
                }
                String birth = year + month + day;
                textView.setText(birth);

            }
        });
    }

    public abstract int setLayoutId();

    public abstract void initView();

    public void hop2ActivityForResult(Class<? extends Ac_base> clazz, int requestCode) {
        Intent intent = new Intent(context, clazz);
        startActivityForResult(intent, requestCode);
    }

    public void assembleBasicObj(BaseRequestBean baseRequestBean) {
        baseRequestBean.setYhdm("test");
        baseRequestBean.setImei(Global.imei);
        baseRequestBean.setImsi(Global.imsi1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pda_time = simpleDateFormat.format(date);
        baseRequestBean.setPdaTime(pda_time);

    }
}
