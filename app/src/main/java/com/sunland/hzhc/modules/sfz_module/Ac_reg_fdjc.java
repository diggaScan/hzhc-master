package com.sunland.hzhc.modules.sfz_module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunland.hzhc.Ac_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.modules.ddc_module.Ac_ddc_list;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;

import butterknife.BindView;

public class Ac_reg_fdjc extends Ac_base {

    private String fjdc_hp;
    private String[] hp_list;
    private MyRvAdapter adapter;

    @BindView(R.id.fjdc_list)
    public RecyclerView rv_fjdc_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_reg_fdjc);
        handleIntent();
        showNavIcon(true);
        setToolbarTitle("非机动车列表");
        initView();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                fjdc_hp = bundle.getString("fjdc_hp");
            }
        }
    }

    private void initView() {
        hp_list = fjdc_hp.split(",");
        adapter = new MyRvAdapter();
        rv_fjdc_list.setAdapter(adapter);
        rv_fjdc_list.setLayoutManager(new LinearLayoutManager(this));
        rv_fjdc_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

        private LayoutInflater inflater;

        public MyRvAdapter() {
            super();
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_fjdc_hp_list_tem, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            final String hphm = hp_list[i];
            myViewHolder.tv_hp.setText(hphm);
            myViewHolder.tv_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("hphm", hphm);
                    bundle.putString("fdjh", "");
                    bundle.putString("cjh", "");
                    hop2Activity(Ac_ddc_list.class, bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return hp_list.length;
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_hp;
            TextView tv_check;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_hp = itemView.findViewById(R.id.fjdc_hp);
                tv_check = itemView.findViewById(R.id.check);
            }
        }
    }

}
