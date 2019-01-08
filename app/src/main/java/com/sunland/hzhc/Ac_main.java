package com.sunland.hzhc;


import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.sunland.hzhc.customView.BannerIndicator;
import com.sunland.hzhc.fragments.Vp_main_adapter;
import com.sunland.hzhc.modules.Hotel_module.Frg_hotel;
import com.sunland.hzhc.modules.Internet_cafe_module.Frg_internet_cafe;
import com.sunland.hzhc.modules.case_module.Frg_case;
import com.sunland.hzhc.modules.ddc_module.Frg_e_vehicle;
import com.sunland.hzhc.modules.jdc_module.Frg_vehicle;
import com.sunland.hzhc.modules.phone_num_module.Frg_phone_num;
import com.sunland.hzhc.modules.sfz_module.Frg_id;
import com.sunland.hzhc.modules.sfz_module.NfcReceiver;
import com.sunland.hzhc.modules.xmzh_module.Frg_name;
import com.sunland.hzhc.utils.WindowInfoUtils;
import com.sunland.sunlandkeyboard.SunlandKeyBoardManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Ac_main extends CheckSelfPermissionActivity implements NfcReceiver.OnGetNfcDataListener {

    private final int REQ_MODULE = 0;
    public final int REQ_SSJ = 1;//请求随手记内容
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
    @BindView(R.id.myKeyb)
    public KeyboardView myKeyBoardView;

    private int backPressed_num = 0;
    private List<Fragment> dataSet;
    private Resources mRes;

    private NfcReceiver rec;

    private Frg_id frg_id;
    private Frg_name frg_name;

    public boolean isFromSsj;//是否由随手记应用跳入

    public SunlandKeyBoardManager sunlandKeyBoardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
        handleIntent();
        mRes = getResources();
        initWindowStyle();
        initView();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle=intent.getBundleExtra("bundle");
        boolean isFromSsj = bundle.getBoolean("isFromSsj", false);
        if (isFromSsj) {
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        rec = new NfcReceiver();
        IntentFilter filter = new IntentFilter();//创建IntentFilter对象
        filter.addAction(DataModel.ACTION_NFC_READ_IDCARD_SUCCESS);
        filter.addAction(DataModel.ACTION_NFC_READ_IDCARD_FAILURE);
        registerReceiver(rec, filter);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            isFromSsj = intent.getBooleanExtra(DataModel.FROM_SSJ_FLAG, false);
            if (isFromSsj) {
                V_config.YHDM = intent.getStringExtra("yhdm");
                V_config.JYSFZH = intent.getStringExtra("sfz");
                V_config.JYXM = intent.getStringExtra("xm");
                V_config.JYBMBH = intent.getStringExtra("bmdm");
                V_config.gpsX = intent.getStringExtra("lx");
                V_config.gpsY = intent.getStringExtra("ly");
                V_config.hc_address = intent.getStringExtra(DataModel.RECORD_BUNDLE_ADDR);
            }
        }
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
        ComponentName componentName = new ComponentName("com.sunland.sunlandkeyboard", "com.sunland.sunlandkeyboard.目标activity"); //用于获取该activity的softInputMode
        sunlandKeyBoardManager = new SunlandKeyBoardManager(this, componentName);

        bannerIndicator.setItem_nums(8);
        bannerIndicator.setRadius(WindowInfoUtils.dp2px(this, 3));
        bannerIndicator.setDotsColor(mRes.getColor(R.color.dot_color));
        bannerIndicator.setMovingDotColor(mRes.getColor(R.color.colorAccent));

        dataSet = new ArrayList<>();
        frg_id = new Frg_id();
        dataSet.add(frg_id);
        dataSet.add(new Frg_vehicle());
        dataSet.add(new Frg_e_vehicle());
        frg_name = new Frg_name();
        dataSet.add(frg_name);
        dataSet.add(new Frg_hotel());
        dataSet.add(new Frg_internet_cafe());
        dataSet.add(new Frg_phone_num());
        dataSet.add(new Frg_case());
//        dataSet.add(new Frg_abroad());
        Vp_main_adapter vp_main_adapter = new Vp_main_adapter(getSupportFragmentManager(), dataSet, Arrays.asList(DataModel.MODULE_NAMES));
        vp_frg_container.setAdapter(vp_main_adapter);
        vp_frg_container.setOffscreenPageLimit(10);
        tl_frg_tabs.setupWithViewPager(vp_frg_container);
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < DataModel.MAIN_MODULE_NAMES.length; i++) {
            View view = inflater.inflate(R.layout.custom_main_bot_tab, null);
            TextView tv_name = view.findViewById(R.id.tab_name);
            ImageView iv_icon = view.findViewById(R.id.tab_icon);
            iv_icon.setImageResource(DataModel.TAB_ICONS_UNCLICKED[i]);
            tv_name.setText(DataModel.MAIN_MODULE_NAMES[i]);
            tl_frg_tabs.getTabAt(i).setTag(i);
            tl_frg_tabs.getTabAt(i).setCustomView(view);
        }


        tl_frg_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = (int) tab.getTag();
                View view = tab.getCustomView();
                ImageView iv_icon = view.findViewById(R.id.tab_icon);
                iv_icon.setImageResource(DataModel.TAB_ICONS_CLICKED[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = (int) tab.getTag();
                View view = tab.getCustomView();
                ImageView iv_icon = view.findViewById(R.id.tab_icon);
                iv_icon.setImageResource(DataModel.TAB_ICONS_UNCLICKED[position]);
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

    @OnClick({R.id.menu_btn, R.id.nav_back})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.menu_btn:
                Intent intent = new Intent(this, Ac_module_list.class);
                startActivityForResult(intent, REQ_MODULE);
                break;
            case R.id.nav_back:
                backPress();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_MODULE) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    vp_frg_container.setCurrentItem(position);
                }
            }
        } else if (222 == requestCode) {
            if (resultCode == 3) {
                ArrayList<String> zjsbname = data.getStringArrayListExtra("ZJSB_NAME");                    //  识别出来的NAME：[保留, 姓名, 性别, 民族, 出生, 住址, 公民身份号码]  顺序不变  从0开始第6位是身份证，如果识别的不是二代身份证，对应的参数及位置，请自行debug查看。
                ArrayList<String> zjsbvalue = data.getStringArrayListExtra("ZJSB_VALUE");                //  识别出来的VALUE：顺序对应于上面的NAME   第6位是身份证，如果识别的不是二代身份证，对应的参数及位置，请自行debug查看。
                Toast.makeText(this, zjsbvalue.get(6), Toast.LENGTH_SHORT).show();
                frg_id.updateViews(zjsbvalue.get(6));
            }
        } else if (requestCode == REQ_SSJ) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getBundleExtra("bundle");
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }


    @Override
    public void onReceiveDate(Intent intent) {
        if (DataModel.ACTION_NFC_READ_IDCARD_SUCCESS.equals(intent.getAction())) {
            Toast.makeText(this, "读取成功! ", Toast.LENGTH_SHORT).show();
            if (!frg_id.isHidden()) {
                frg_id.updateViews(intent);
            }
        } else if (DataModel.ACTION_NFC_READ_IDCARD_FAILURE.equals(intent.getAction())) {
            Toast.makeText(this, "读取失败!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterReceiver(rec);
    }

    //NFC 扫描和身份证识别
    public void onChoose(int which) {
        switch (which) {
            case 0:
                Intent intent = new Intent("cybertech.pstore.intent.action.NFC_READER");
                intent.setPackage("cn.com.cybertech.nfc.reader");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请安装相应NFC模块", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                Intent mIntent = new Intent();
                mIntent.putExtra("ZJSB", true);
                mIntent.putExtra("nMainID", 2); // 识别类型，如不加此参数，默认2 识别二代身份证，  具体对应的类型在下面可以按需设置。
                mIntent.setClassName("cn.com.cybertech.ocr", "cn.com.cybertech.RecognitionActivity");
                startActivityForResult(mIntent, 222);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backPress();
    }

    private void backPress() {
        if (backPressed_num != 1) {
            backPressed_num++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressed_num--;
                }
            }, 2500);
            Toast.makeText(this, "再按一次，退出应用", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
