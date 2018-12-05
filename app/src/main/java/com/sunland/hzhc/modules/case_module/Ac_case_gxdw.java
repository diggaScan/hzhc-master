package com.sunland.hzhc.modules.case_module;

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
import com.sunland.hzhc.modules.case_module.bean.DwBaseInfo;
import com.sunland.hzhc.modules.case_module.bean.DwListResBean;
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

public class Ac_case_gxdw extends Ac_base implements OnRequestCallback {

    private RequestManager mRequestManager;
    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;
    @BindView(R.id.district_list)
    public RecyclerView rv_district_list;
    @BindView(R.id.dw_list)
    public RecyclerView rv_dw_list;

    private Rv_Jg_adapter d_adapter;
    private Rv_Jg_adapter dw_adapter;

    private List<String> districts;
    private List<String> xzqh;
    private List<String> dw;
    private List<String> dwdm;

    private List<String> all_dwmc;
    private List<String> all_dwdm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_case_gxdw);
        showNavIcon(true);
        setToolbarTitle("管辖单位列表");
        mRequestManager = new RequestManager(this, this);
        readDb();
        showDistricts();
        queryCaseGxdw(Dictionary.QUERY_ALL_CASE_INFO);
    }

    private void readDb() {
        assetDBReader = new AssetDBReader(this);
        database = assetDBReader.readAssetDb(this.getExternalFilesDir("hzydjwzd").getAbsolutePath(), "hzydjwzd.db");
    }

    private void showDistricts() {
        districts = new ArrayList<>();
        xzqh = new ArrayList<>();
        dw = new ArrayList<>();
        dwdm = new ArrayList<>();

        all_dwdm = new ArrayList<>();
        all_dwmc = new ArrayList<>();

        d_adapter = new Rv_Jg_adapter(this, districts);
        dw_adapter = new Rv_Jg_adapter(this, dw);
        d_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (position == 0) {
                    dw.clear();
                    dwdm.clear();
                    dw.addAll(all_dwmc);
                    dwdm.addAll(all_dwdm);
                    dw_adapter.notifyDataSetChanged();
                } else {
                    showWbmc(xzqh.get(position) + "%");
                }

            }
        });

        dw_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                String name = dw.get(position);
                String code = dwdm.get(position);
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
        rv_dw_list.setAdapter(dw_adapter);

        rv_district_list.setLayoutManager(manager1);
        rv_dw_list.setLayoutManager(manager2);

        rv_dw_list.addItemDecoration(new Rv_Item_decoration(this));
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

    }

    private void queryCaseGxdw(String reqName) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, reqName
                , assembleRequestObj(), 15000);
        mRequestManager.postRequest();
    }

    private BaseRequestBean assembleRequestObj() {
        BaseRequestBean bean = new BaseRequestBean();
        assembleBasicObj(bean);
        return bean;
    }

    public void assembleBasicObj(BaseRequestBean baseRequestBean) {
        baseRequestBean.setYhdm("test");
        baseRequestBean.setImei(Global.imei);
        baseRequestBean.setImsi(Global.imsi1);
        baseRequestBean.setLbr("02");
        baseRequestBean.setGpsx("");
        baseRequestBean.setGpsY("");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pda_time = simpleDateFormat.format(date);
        baseRequestBean.setPdaTime(pda_time);

    }

    private void showWbmc(String xzqh) {
        Cursor cursor = database.rawQuery("SELECT DWMC,DWDM FROM dwlb WHERE DWDM LIKE ?", new String[]{xzqh});
        dw.clear();
        dwdm.clear();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dw.add(cursor.getString(0));
                dwdm.add(cursor.getString(1));
                cursor.moveToNext();
            }
            dw_adapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {

        DwListResBean dwListResBean = (DwListResBean) bean;
        dw.clear();
        dwdm.clear();
        List<DwBaseInfo> dwBaseInfoList = dwListResBean.getDwlist();
        if (dwBaseInfoList != null) {
            for (DwBaseInfo info : dwBaseInfoList) {
                all_dwmc.add(info.getName());
                all_dwdm.add(info.getCode());
            }
            dw.addAll(all_dwmc);
            dwdm.addAll(all_dwdm);
            dw_adapter.notifyDataSetChanged();
        }


    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return DwListResBean.class;
    }
}
