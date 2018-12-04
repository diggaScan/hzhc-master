package com.sunland.hzhc.modules.sfz_module;

import android.content.Intent;
import android.net.Uri;
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
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;

import butterknife.BindView;

public class Ac_dh extends Ac_base {


    private String dh_str;
    private String[] dh_list;
    private MyRvAdapter adapter;
    @BindView(R.id.dh_list)
    public RecyclerView rv_dh_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_dh);
        showNavIcon(true);
        setToolbarTitle("电话列表");
        handleIntent();
        initView();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                dh_str = bundle.getString("dh_str");
            }
        }
    }

    private void initView() {
        dh_list = dh_str.split(",");
        adapter = new MyRvAdapter();
        rv_dh_list.setAdapter(adapter);
        rv_dh_list.setLayoutManager(new LinearLayoutManager(this));
        rv_dh_list.addItemDecoration(new Rv_Item_decoration(this));
    }

    private void dial(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void send_msg(String number) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + number));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
        LayoutInflater inflater;

        public MyRvAdapter() {
            super();
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_dh_list_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            final String dh = dh_list[i];

            myViewHolder.tv_dh.setText(dh);
            myViewHolder.tv_dial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dial(dh);
                }
            });

            myViewHolder.tv_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send_msg(dh);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dh_list.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_txt;
            TextView tv_dial;
            TextView tv_dh;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_txt = itemView.findViewById(R.id.text);
                tv_dial = itemView.findViewById(R.id.dial);
                tv_dh = itemView.findViewById(R.id.dh);
            }
        }
    }


}
