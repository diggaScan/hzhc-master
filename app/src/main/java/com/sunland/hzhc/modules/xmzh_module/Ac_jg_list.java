package com.sunland.hzhc.modules.xmzh_module;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class Ac_jg_list extends Ac_base {

    @BindView(R.id.provinces)
    public RecyclerView rv_provinces;
    @BindView(R.id.cities)
    public RecyclerView rv_cities;
    @BindView(R.id.district)
    public RecyclerView rv_districts;

    private List<String> dataSet_provinces;
    private Rv_Jg_adapter p_adapter;
    private List<String> dataSet_cities;
    private Rv_Jg_adapter c_adapter;
    private List<String> dataSet_districts;
    private Rv_Jg_adapter d_adapter;

    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;

    private final int CITIES_LEVEL = 1;
    private final int PROVINCES_LEVEL = 0;
    private final int DISTRICTS_LEVEL = 2;
    private final String[] large_cities = {"110000", "120000", "310000", "500000"};
    private boolean hasDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_jg);
        showNavIcon(true);
        setToolbarTitle("地区列表");
        initView();
        readjgData();
        showjgData("%0000", 0);
    }

    private void initView() {
        dataSet_provinces = new ArrayList<>();
        dataSet_cities = new ArrayList<>();
        dataSet_districts = new ArrayList<>();
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        LinearLayoutManager manager3 = new LinearLayoutManager(this);
        p_adapter = new Rv_Jg_adapter(this, dataSet_provinces);
        c_adapter = new Rv_Jg_adapter(this, dataSet_cities);
        d_adapter = new Rv_Jg_adapter(this, dataSet_districts);

        p_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                dataSet_districts.clear();
                d_adapter.notifyDataSetChanged();
                String name = dataSet_provinces.get(position);
                String code = getDqCode(name);
                String arg;
                if (Arrays.asList(large_cities).contains(code)) {
                    arg = code.substring(0, 2) + "%";
                    hasDistrict = false;
                } else {
                    arg = code.substring(0, 2) + "%" + code.substring(4);
                    hasDistrict = true;
                }
                if (code != null) {
                    showjgData(arg, CITIES_LEVEL);
                }

            }
        });

        c_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (hasDistrict) {
                    String name = dataSet_cities.get(position);
                    String code = getDqCode(name);
                    String arg = code.substring(0, 4) + "%";
                    if (code != null) {
                        showjgData(arg, DISTRICTS_LEVEL);
                    }
                } else {
                    String name = dataSet_cities.get(position);
                    String code = getDqCode(name);
                    Intent intent = new Intent();
                    intent.putExtra("code", code);
                    intent.putExtra("name", name);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        d_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                String name = dataSet_districts.get(position);
                String code = getDqCode(name);
                Intent intent = new Intent();
                intent.putExtra("code", code);
                intent.putExtra("name", name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        rv_provinces.setAdapter(p_adapter);
        rv_cities.setAdapter(c_adapter);
        rv_districts.setAdapter(d_adapter);

        rv_provinces.setLayoutManager(manager1);
        rv_cities.setLayoutManager(manager2);
        rv_districts.setLayoutManager(manager3);

        rv_provinces.addItemDecoration(new Rv_Item_decoration(this));
        rv_cities.addItemDecoration(new Rv_Item_decoration(this));
        rv_districts.addItemDecoration(new Rv_Item_decoration(this));
    }

    private void readjgData() {
        assetDBReader = new AssetDBReader(this);
        database = assetDBReader.readAssetDb(this.getExternalFilesDir("jg").getAbsolutePath(), "ydjw.db");
    }

    private void showjgData(String code, int level) {
        switch (level) {
            case PROVINCES_LEVEL:
                Cursor cursor = database.rawQuery("SELECT name FROM huji WHERE code LIKE ?", new String[]{code});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        dataSet_provinces.add(cursor.getString(0));
                        cursor.moveToNext();
                    }
                    p_adapter.notifyDataSetChanged();
                    cursor.close();
                }
                break;
            case CITIES_LEVEL:
                dataSet_cities.clear();
                Cursor cursor2 = database.rawQuery("SELECT name FROM huji WHERE code LIKE ?", new String[]{code});
                if (cursor2 != null && cursor2.getCount() > 0) {
                    cursor2.moveToFirst();
                    cursor2.moveToNext();
                    while (!cursor2.isAfterLast()) {
                        dataSet_cities.add(cursor2.getString(0));
                        cursor2.moveToNext();
                    }
                    c_adapter.notifyDataSetChanged();
                    cursor2.close();
                }
                break;
            case DISTRICTS_LEVEL:
                dataSet_districts.clear();
                Cursor cursor3 = database.rawQuery("SELECT name FROM huji WHERE code LIKE ?", new String[]{code});
                if (cursor3 != null && cursor3.getCount() > 0) {
                    cursor3.moveToFirst();
                    cursor3.moveToNext();
                    while (!cursor3.isAfterLast()) {
                        dataSet_districts.add(cursor3.getString(0));
                        cursor3.moveToNext();
                    }
                    d_adapter.notifyDataSetChanged();
                    cursor3.close();
                }
                break;
        }
    }

    private String getDqCode(String name) {
        String code = "";
        Cursor cursor = database.rawQuery("SELECT code FROM huji WHERE name = ?", new String[]{name});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                code = cursor.getString(0);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return code;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
