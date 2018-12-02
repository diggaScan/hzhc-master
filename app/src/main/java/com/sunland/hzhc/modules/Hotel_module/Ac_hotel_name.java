package com.sunland.hzhc.modules.Hotel_module;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.R;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.modules.Hotel_module.bean.LgBaseInfo;
import com.sunland.hzhc.modules.Hotel_module.bean.LgLbResBean;
import com.sunland.hzhc.modules.xmzh_module.Rv_Jg_adapter;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class Ac_hotel_name extends Ac_base implements OnRequestCallback {

    @BindView(R.id.district_list)
    public RecyclerView rv_districts;
    @BindView(R.id.hotel_list)
    public RecyclerView rv_hotel_list;

    private Rv_Jg_adapter d_adapter;
    private Rv_Jg_adapter h_adapter;

    private List<String> dataSet_hotels;
    private List<String> lgdm;

    private List<String> all_lg;
    private List<String> all_lg_code;

    private RequestManager mRequestManager;
    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_hotel_name);
        showNavIcon(true);
        setToolbarTitle("旅馆列表");
        initView();
        readDb();
        mRequestManager = new RequestManager(this, this);
        queryHotelNames(Dictionary.GET_ALL_HOTELS);

    }

    private void initView() {
        dataSet_hotels = new ArrayList<>();
        lgdm = new ArrayList<>();
        all_lg = new ArrayList<>();
        all_lg_code = new ArrayList<>();


        d_adapter = new Rv_Jg_adapter(this, Arrays.asList(Dictionary.HANGZHOU_DISTRICTS));
        h_adapter = new Rv_Jg_adapter(this, dataSet_hotels);

        d_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (position == 0) {
                    dataSet_hotels.clear();
                    lgdm.clear();
                    dataSet_hotels.addAll(all_lg);
                    lgdm.addAll(all_lg_code);
                    h_adapter.notifyDataSetChanged();
                } else {
                    showLglb(Dictionary.HANGZHOU_DISTRICT_CODE[position] + "%");
                }
            }
        });

        h_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                String name = dataSet_hotels.get(position);
                String code = lgdm.get(position);
                Intent intent = new Intent();
                intent.putExtra("code", code);
                intent.putExtra("name", name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        rv_districts.setAdapter(d_adapter);
        rv_hotel_list.setAdapter(h_adapter);

        rv_districts.setLayoutManager(manager1);
        rv_hotel_list.setLayoutManager(manager2);

        rv_districts.addItemDecoration(new Rv_Item_decoration(this));
        rv_hotel_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    private void readDb() {
        assetDBReader = new AssetDBReader(this);
        database = assetDBReader.readAssetDb(this.getExternalFilesDir("hzydjwzd").getAbsolutePath(), "hzydjwzd.db");
    }

    private void showLglb(String code) {
        Cursor cursor = database.rawQuery("SELECT DWMC,DWDM FROM lglb WHERE DWDM LIKE ?", new String[]{code});
        dataSet_hotels.clear();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dataSet_hotels.add(cursor.getString(0));
                lgdm.add(cursor.getString(1));
                cursor.moveToNext();
            }
            h_adapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    private void queryHotelNames(String reqName) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, reqName
                , assembleRequestObj(reqName), 15000);
        mRequestManager.postRequest();
    }

    private BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case Dictionary.GET_ALL_HOTELS:
                BaseRequestBean bean = new BaseRequestBean();
                assembleBasicObj(bean);
                return bean;
        }
        return null;
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

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        LgLbResBean lgLbResBean = (LgLbResBean) bean;
        dataSet_hotels.clear();
        lgdm.clear();
        List<LgBaseInfo> lgBaseInfos = lgLbResBean.getLGList();
        if (lgBaseInfos != null) {
            for (LgBaseInfo info : lgBaseInfos) {
                all_lg.add(info.getLgmc());
                all_lg_code.add(info.getLgmc_code());
            }
        }
        dataSet_hotels.addAll(all_lg);
        lgdm.addAll(all_lg_code);
        h_adapter.notifyDataSetChanged();


    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return LgLbResBean.class;
    }


}
