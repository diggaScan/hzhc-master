package com.sunland.hzhc;

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

import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_inspect_person.InspectPersonJsonRet;
import com.sunland.hzhc.bean.i_person_focus.PeopleFocusResBean;
import com.sunland.hzhc.customView.CustomPicker;
import com.sunland.hzhc.bean.i_archive.PersonArchiveResBean;
import com.sunland.hzhc.bean.i_track.TrackResBean;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

public abstract class Frg_base extends Fragment implements OnRequestCallback, OnFragmentVisibleListener, YdjwQueryAssistance<BaseRequestBean> {

    public Context context;
    public boolean isVisible;
    public RequestManager mRequestManager;
    private boolean onResumed;


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
        mRequestManager = new RequestManager(context, this);
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

    public void queryYdjwData(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequestWithoutDialog();
    }

    public void queryYdjwDataNoDialog(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequestWithoutDialog();
    }

    public void hop2ActivityForResult(Class<? extends Ac_base> clazz, int requestCode) {
        Intent intent = new Intent(context, clazz);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        return null;
    }

    public void assembleBasicRequest(BaseRequestBean requestBean) {
        requestBean.setYhdm(V_config.YHDM);
        requestBean.setImei(V_config.imei);
        requestBean.setImsi(V_config.imsi1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pda_time = simpleDateFormat.format(date);
        requestBean.setPdaTime(pda_time);
        requestBean.setGpsX(V_config.gpsX);
        requestBean.setGpsY(V_config.gpsY);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (!isVisibleToUser && mRequestManager != null) {
            mRequestManager.cancelAll();
        }
        if (isVisibleToUser && onResumed) {
            onFragmentVisible();
        }
    }

    @Override
    public void onFragmentVisible() {

    }

    @Override
    public void onResume() {
        super.onResume();
        onResumed = true;
    }

    public void setText(TextView textView, String content) {
        if (content == null || content.isEmpty()) {
            textView.setText("无");
        } else {
            textView.setText(content);
        }
    }


    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        switch (reqName) {
            case V_config.GET_PERSON_INFO_BY_SFZH:
                return PersonArchiveResBean.class;
            case V_config.PERSON_LOCUS_INFOS:
                return TrackResBean.class;
            case V_config.PERSON_FOCUS_INFO:
                return PeopleFocusResBean.class;
            case V_config.INSPECT_PERSON:
                return InspectPersonJsonRet.class;
        }
        return null;
    }
}
