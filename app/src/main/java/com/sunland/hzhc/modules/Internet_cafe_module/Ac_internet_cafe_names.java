package com.sunland.hzhc.modules.Internet_cafe_module;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.R;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.modules.Internet_cafe_module.bean.WbBaseInfo;
import com.sunland.hzhc.modules.Internet_cafe_module.bean.WbListResBean;
import com.sunland.hzhc.modules.xmzh_module.Rv_Jg_adapter;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class Ac_internet_cafe_names extends Ac_base implements OnRequestCallback {

    private RequestManager mRequestManager;
    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;
    @BindView(R.id.district_list)
    public RecyclerView rv_district_list;
    @BindView(R.id.wb_list)
    public RecyclerView rv_wb_list;

    private List<String> districts;
    private List<String> xzqh;
    private List<String> wbmcs;
    private List<String> wbbh;

    private List<String> all_wbmc;
    private List<String> all_wbbh;

    private Rv_Jg_adapter d_adapter;
    private Rv_Jg_adapter wb_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_internet_cafe_names);
        showNavIcon(true);
        setToolbarTitle("网吧列表");
        readZdDb();
        showDistrict();
        mRequestManager = new RequestManager(this, this);
        queryNames(V_config.GET_INTERNET_CAFE_INFO);
    }

    private void readZdDb() {
        assetDBReader = new AssetDBReader(this);
        database = assetDBReader.readAssetDb(this.getExternalFilesDir("hzydjwzd").getAbsolutePath(), "hzydjwzd.db");
    }

    private void showDistrict() {
        districts = new ArrayList<>();
        xzqh = new ArrayList<>();
        wbmcs = new ArrayList<>();
        wbbh = new ArrayList<>();

        all_wbmc = new ArrayList<>();
        all_wbbh = new ArrayList<>();

        d_adapter = new Rv_Jg_adapter(this, districts);
        wb_adapter = new Rv_Jg_adapter(this, wbmcs);
        d_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (position == 0) {
                    wbmcs.clear();
                    wbbh.clear();
                    wbmcs.addAll(all_wbmc);
                    wbbh.addAll(all_wbbh);
                    wb_adapter.notifyDataSetChanged();
                } else {
                    showWbmc(xzqh.get(position));
                }

            }
        });

        wb_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                String name = wbmcs.get(position);
                String code = wbbh.get(position);
                Intent intent = new Intent();
                intent.putExtra("code", code);
                intent.putExtra("name", name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);

        rv_district_list.setAdapter(d_adapter);
        rv_wb_list.setAdapter(wb_adapter);

        rv_district_list.setLayoutManager(manager1);
        rv_wb_list.setLayoutManager(manager2);

        rv_wb_list.addItemDecoration(new Rv_Item_decoration(this));
        rv_district_list.addItemDecoration(new Rv_Item_decoration(this));

        Cursor cursor = database.rawQuery("SELECT WBMC,XZQH FROM wbxzqhlb", null);
        districts.clear();
        xzqh.clear();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                districts.add(cursor.getString(0));
                xzqh.add(cursor.getString(1));
                cursor.moveToNext();
            }
            d_adapter.notifyDataSetChanged();
            cursor.close();
        }

        Cursor cursor1 = database.rawQuery("SELECT MC,BH FROM wbmclb ", null);
        if (cursor1 != null && cursor.getCount() > 0) {
            cursor1.moveToFirst();
            while (!cursor1.isAfterLast()) {
                wbmcs.add(cursor1.getString(0));
                wbbh.add(cursor1.getString(1));
                cursor1.moveToNext();
            }
            wb_adapter.notifyDataSetChanged();
            cursor1.close();
        }
    }

    private void showWbmc(String xzqh) {
        Cursor cursor = database.rawQuery("SELECT MC,BH FROM wbmclb WHERE XZQH = ?", new String[]{xzqh});
        wbmcs.clear();
        wbbh.clear();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                wbmcs.add(cursor.getString(0));
                wbbh.add(cursor.getString(1));
                cursor.moveToNext();
            }
            wb_adapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    private void queryNames(String reqName) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, reqName
                , assembleRequestObj(reqName), 15000);
        mRequestManager.postRequest();
    }

    private BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_INTERNET_CAFE_INFO:
                WbListReqBean bean = new WbListReqBean();
                assembleBasicObj(bean);
                bean.setCurrentPage(1);
                bean.setTotalCount(100);
                return bean;
        }
        return null;
    }

    public void assembleBasicObj(BaseRequestBean baseRequestBean) {
        baseRequestBean.setYhdm("test");
        baseRequestBean.setImei(Global.imei);
        baseRequestBean.setImsi(Global.imsi1);
        baseRequestBean.setLbr("02");
        baseRequestBean.setGpsX("");
        baseRequestBean.setGpsY("");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pda_time = simpleDateFormat.format(date);
        baseRequestBean.setPdaTime(pda_time);

    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        WbListResBean wbListResBean = (WbListResBean) bean;
        if (wbListResBean == null) {
            return;
        }
        List<WbBaseInfo> wbBaseInfos = wbListResBean.getResps();
        if (wbBaseInfos == null || wbBaseInfos.isEmpty()) {
            return;
        }
        wbmcs.clear();
        wbbh.clear();
        for (WbBaseInfo info : wbListResBean.getResps()) {
            all_wbmc.add(info.getWbmc());
            all_wbbh.add(info.getWb_code());
        }
        wbmcs.addAll(all_wbmc);
        wbbh.addAll(all_wbbh);
        wb_adapter.notifyDataSetChanged();
    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return WbListResBean.class;
    }
}
