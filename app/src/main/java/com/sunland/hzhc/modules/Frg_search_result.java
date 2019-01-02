package com.sunland.hzhc.modules;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Frg_search_result extends Frg_base {
    @BindView(R.id.search_result)
    public RecyclerView rv_search_result;
    @BindView(R.id.no_result)
    public TextView tv_no_result;

    private MyAdapter myAdapter;
    private String search_index;
    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;
    private List<String> mc;
    private List<String> dm;
    private int type;//1 旅馆 2网吧 3案件管辖单位 4案件类型
    private List<String> ajlbs;

    public void setSearch_index(String search_index, int type) {
        this.search_index = search_index;
        this.type = type;
    }

    public void setAjlbs(List<String> ajlbs) {
        this.ajlbs = ajlbs;
    }

    @Override
    public int setLayoutId() {
        return R.layout.frg_search_result;
    }


    @Override
    public void initView() {
        dm = new ArrayList<>();
        mc = new ArrayList<>();
        readDb();
        myAdapter = new MyAdapter();
        rv_search_result.setAdapter(myAdapter);
        rv_search_result.setLayoutManager(new LinearLayoutManager(context));
        rv_search_result.addItemDecoration(new Rv_Item_decoration(context));
        switch (type) {
            case 1:
                showLglb(search_index);
                break;
            case 2:
                showWblb(search_index);
                break;
            case 3:
                showGxdw(search_index);
                break;
            case 4:
                showAjlb(search_index);
                break;
        }


    }

    private void readDb() {
        assetDBReader = new AssetDBReader(context);
        database = assetDBReader.readAssetDb(context.getExternalFilesDir("hzydjwzd").getAbsolutePath(), "hzydjwzd.db");
    }

    public void update(String dwmc) {
        switch (type) {
            case 1:
                showLglb(dwmc);
                break;
            case 2:
                showWblb(dwmc);
                break;
            case 3:
                showGxdw(dwmc);
                break;
            case 4:
                showAjlb(dwmc);
                break;
        }
    }

    private void showAjlb(String input) {
        dm.clear();
        for (String lbList : ajlbs) {
            if (lbList.contains(input)) {
                dm.add(lbList);
            }
        }
        if (dm.isEmpty()) {
            rv_search_result.setVisibility(View.GONE);
            tv_no_result.setVisibility(View.VISIBLE);
            tv_no_result.setText("无相关案件类型");
        } else {
            rv_search_result.setVisibility(View.VISIBLE);
            tv_no_result.setVisibility(View.GONE);
            myAdapter.notifyDataSetChanged();
        }
    }

    private void showWblb(String input) {
        Cursor cursor1 = database.rawQuery("SELECT MC,BH FROM wbmclb WHERE MC" + " like '%" + input + "%'", null);
        mc.clear();
        dm.clear();
        if (cursor1 != null && cursor1.getCount() > 0) {
            rv_search_result.setVisibility(View.VISIBLE);
            tv_no_result.setVisibility(View.GONE);
            cursor1.moveToFirst();
            while (!cursor1.isAfterLast()) {
                mc.add(cursor1.getString(0));
                dm.add(cursor1.getString(1));
                cursor1.moveToNext();
            }
            myAdapter.notifyDataSetChanged();
            cursor1.close();
        } else {
            rv_search_result.setVisibility(View.GONE);
            tv_no_result.setVisibility(View.VISIBLE);
            tv_no_result.setText("无相关网吧名称");
        }
    }

    private void showGxdw(String input) {
        Cursor cursor = database.rawQuery("SELECT DWMC,DWDM FROM dwlb WHERE DWMC LIKE '%" + input + "%'", null);
        mc.clear();
        dm.clear();
        if (cursor != null && cursor.getCount() > 0) {
            rv_search_result.setVisibility(View.VISIBLE);
            tv_no_result.setVisibility(View.GONE);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mc.add(cursor.getString(0));
                dm.add(cursor.getString(1));
                cursor.moveToNext();
            }
            myAdapter.notifyDataSetChanged();
            cursor.close();
        } else {
            rv_search_result.setVisibility(View.GONE);
            tv_no_result.setVisibility(View.VISIBLE);
            tv_no_result.setText("无相关单位名称");
        }
    }

    private void showLglb(String dwmc) {
        String sql = "SELECT DWMC,DWDM FROM lglb WHERE DWMC" + " like '%" + dwmc + "%'";
        Cursor cursor = database.rawQuery(sql, null);
        mc.clear();
        dm.clear();
        if (cursor != null && cursor.getCount() > 0) {
            rv_search_result.setVisibility(View.VISIBLE);
            tv_no_result.setVisibility(View.GONE);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mc.add(cursor.getString(0));
                dm.add(cursor.getString(1));
                cursor.moveToNext();
            }
            myAdapter.notifyDataSetChanged();
            cursor.close();
        } else {
            rv_search_result.setVisibility(View.GONE);
            tv_no_result.setVisibility(View.VISIBLE);
            tv_no_result.setText("无相关酒店名称");
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder> {

        private LayoutInflater layoutInflater;

        public MyAdapter() {
            super();
            layoutInflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = layoutInflater.inflate(R.layout.rv_search_result_item, viewGroup, false);
            return new MyViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewholder myViewholder, final int i) {
            if (type == 4) {
                myViewholder.tv_mc.setText(dm.get(i));
            } else {
                myViewholder.tv_mc.setText(mc.get(i));
            }

            myViewholder.ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 4) {
                        String cate = dm.get(i);
                        String name = cate.substring(6, cate.length());
                        String code = cate.substring(0, 6);
                        ((OnItemSelected) context).onChosenItem(code, name);
                    } else {
                        String name = mc.get(i);
                        String code = dm.get(i);
                        ((OnItemSelected) context).onChosenItem(code, name);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return dm.size();
        }

        class MyViewholder extends RecyclerView.ViewHolder {
            TextView tv_mc;
            LinearLayout ll_container;

            MyViewholder(@NonNull View itemView) {
                super(itemView);
                tv_mc = itemView.findViewById(R.id.result);
                ll_container = itemView.findViewById(R.id.result_container);
            }
        }

    }
}
