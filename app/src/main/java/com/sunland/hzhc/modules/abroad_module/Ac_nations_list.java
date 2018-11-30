package com.sunland.hzhc.modules.abroad_module;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_nations_list extends Ac_base {

    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;

    @BindView(R.id.nation_list)
    public RecyclerView nations_list;

    List<NationInfo> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_nations_list);
        setToolbarTitle("国家列表");
        dataSet = new ArrayList<>();
        showNavIcon(true);
        readNationsList();
        showNations();
    }

    private void readNationsList() {
        assetDBReader = new AssetDBReader(this);
        database = assetDBReader.readAssetDb(this.getExternalFilesDir("nations").getAbsolutePath(), "nationsList.db");
    }

    private void showNations() {
        Cursor cursor = database.query("nation_list", null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.d("a", "showNations: " + cursor.getCount());
            while (!cursor.isAfterLast()) {
                NationInfo info = new NationInfo();
                info.setNation_code(cursor.getString(0));
                info.setNation_name((cursor.getString(1)));
                dataSet.add(info);
                cursor.moveToNext();
            }
            cursor.close();
            database.close();
        }
        MyNationsAdapter adapter = new MyNationsAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        nations_list.setLayoutManager(manager);
        nations_list.setAdapter(adapter);
        nations_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    class MyNationsAdapter extends RecyclerView.Adapter<MyNationsAdapter.MyViewHolder> {

        List<NationInfo> dataSet;
        LayoutInflater inflater;

        public MyNationsAdapter(List<NationInfo> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyNationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_nation_list_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyNationsAdapter.MyViewHolder myViewHolder, int i) {
            final NationInfo info = dataSet.get(i);
            myViewHolder.tv_name.setText(info.getNation_name());
            myViewHolder.tv_code.setText(info.getNation_code());
            myViewHolder.nation_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("nation_code", info.getNation_code());
                    intent.putExtra("nation_name", info.getNation_name());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout nation_container;
            TextView tv_code;
            TextView tv_name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nation_container = itemView.findViewById(R.id.nation_container);
                tv_code = itemView.findViewById(R.id.nation_code);
                tv_name = itemView.findViewById(R.id.nation_name);
            }
        }
    }

}
