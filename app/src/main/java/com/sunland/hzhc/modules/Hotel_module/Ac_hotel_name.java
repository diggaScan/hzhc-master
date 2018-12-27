package com.sunland.hzhc.modules.Hotel_module;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_hotel_names.LgBaseInfo;
import com.sunland.hzhc.bean.i_hotel_names.LgLbResBean;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.xmzh_module.Rv_Jg_adapter;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class Ac_hotel_name extends Ac_base_info {

    @BindView(R.id.district_list)
    public RecyclerView rv_districts;
    @BindView(R.id.hotel_list)
    public RecyclerView rv_hotel_list;

    private Loadable_adapter d_adapter;
    private Rv_Jg_adapter h_adapter;

    private List<String> dataSet_hotels;
    private List<String> lgdm;

    private List<String> all_lg;
    private List<String> all_lg_code;

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
        queryYdjwDataNoDialog(V_config.GET_ALL_HOTELS);
        queryYdjwDataX("");
    }

    private void initView() {
        dataSet_hotels = new ArrayList<>();
        lgdm = new ArrayList<>();
        all_lg = new ArrayList<>();
        all_lg_code = new ArrayList<>();
        d_adapter = new Loadable_adapter(this, Arrays.asList(DataModel.HANGZHOU_DISTRICTS));
        h_adapter = new Rv_Jg_adapter(this, dataSet_hotels);
        d_adapter.setOnItemClickedListener(new Loadable_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {

                if (position == 0) {
                    dataSet_hotels.clear();
                    lgdm.clear();
                    dataSet_hotels.addAll(all_lg);
                    lgdm.addAll(all_lg_code);
                    h_adapter.notifyDataSetChanged();
                } else {
                    showLglb(DataModel.HANGZHOU_DISTRICT_CODE[position] + "%");
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

    @Override
    public void handleIntent() {

    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_ALL_HOTELS:
                BaseRequestBean bean = new BaseRequestBean();
                assembleBasicRequest(bean);
                return bean;
        }
        return null;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        LgLbResBean lgLbResBean = (LgLbResBean) resultBase;
        d_adapter.hasLoaded(true);
        d_adapter.notifyItemChanged(0);
        if (lgLbResBean == null) {
            Toast.makeText(this, "获取旅馆名称接口异常", Toast.LENGTH_SHORT).show();
            return;
        }

        List<LgBaseInfo> lgBaseInfos = lgLbResBean.getLGList();
        if (lgBaseInfos == null) {
            Toast.makeText(this, "无法获取杭州全市旅馆信息", Toast.LENGTH_SHORT).show();
            return;
        }

        for (LgBaseInfo info : lgBaseInfos) {
            all_lg.add(info.getLgmc());
            all_lg_code.add(info.getLgmc_code());
        }

        dataSet_hotels.addAll(all_lg);
        lgdm.addAll(all_lg_code);

        h_adapter.notifyDataSetChanged();
    }

}
