package com.sunland.hzhc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.utils.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import cn.com.cybertech.pdk.OperationLog;


public class Ac_base extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView tb_title;
    public ImageView iv_nav;
    public FrameLayout container;
    public Vibrator vibrator;
    public MyApplication mApplication;
    public final int REQ_SSJ = 1;//请求随手记内容
    public DialogUtils dialogUtils;
    public SpinKitView loading_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_base);
        mApplication = (MyApplication) getApplication();
        toolbar = findViewById(R.id.toolbar);
        tb_title = findViewById(R.id.toolbar_title);
        iv_nav = findViewById(R.id.nav_back);
        container = findViewById(R.id.container);
        loading_icon = findViewById(R.id.loading_icon);
        loading_icon.setVisibility(View.GONE);
        iv_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        dialogUtils = DialogUtils.getInstance();
    }

    public void setContentLayout(int layout) {
        getLayoutInflater().inflate(layout, container, true);
        ButterKnife.bind(this);
    }

    public void setToolbarTitle(String title) {
        tb_title.setText(title);
    }

    public void showNavIcon(boolean isShow) {
        if (isShow) {
            iv_nav.setVisibility(View.VISIBLE);
        } else {
            iv_nav.setVisibility(View.GONE);
        }
    }

    public void showTollbar(boolean isShow) {
        if (isShow)
            toolbar.setVisibility(View.VISIBLE);
        else
            toolbar.setVisibility(View.GONE);
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

    public void saveLog(int operateType, int operationResult, String operateCondition) {
        OperationLog.saveLog(this
                , getTitle().toString()
                , "com.sunland.hzhc"
                , "hzhc"
                , operateType
                , OperationLog.OperationResult.CODE_SUCCESS
                , 1
                , operateCondition);
    }

    public String appendString(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i != strings.length - 1) {
                sb.append("@");
            }
        }
        return sb.toString();
    }

    public void hop2Activity(Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void hop2Activity(Class<? extends AppCompatActivity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    public void hop2ActivityForResult(Class<? extends AppCompatActivity> clazz, Bundle bundle, int flag) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, flag);
    }

    public void hop2ActivityForResult(Class<? extends AppCompatActivity> clazz, int flag) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, flag);
    }

    public void hopWithssj(Class<? extends AppCompatActivity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, REQ_SSJ);
    }

    public void startVibrate() {
        vibrator.vibrate(new long[]{500, 1000, 500, 1000}, -1);
    }

    public void stopVibrate() {
        vibrator.cancel();
    }

    public static boolean isChinese(char str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(String.valueOf(str));
        boolean flg = false;
        if (matcher.find())
            flg = true;
        return flg;
    }

    public void showLoading_layout(boolean showloading) {
        if (showloading) {
            loading_icon.setVisibility(View.VISIBLE);
        } else {
            loading_icon.setVisibility(View.GONE);
        }
    }


}
