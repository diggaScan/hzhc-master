package com.sunland.hzhc;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunland.hzhc.modules.batch_check.Ac_batch_check;
import com.sunland.hzhc.utils.WindowInfoUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class Ac_module_list extends Ac_base {

    @BindView(R.id.module_list)
    public RecyclerView rv_module_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_module_list);
        setToolbarTitle("功能列表");
        showNavIcon(true);
        initView();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
            float actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0);
            actionbarSizeTypedArray.recycle();
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    WindowInfoUtils.getStatusBarHeight(this) + (int) actionBarHeight);
            toolbar.setLayoutParams(lp);
            toolbar.setBackgroundColor(getResources().getColor(R.color.app_background));
        }

        MyModuleListAdapter adapter = new MyModuleListAdapter(Arrays.asList(Dictionary.MODULES_ICON)
                , Arrays.asList(Dictionary.MODULE_NAMES));
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        rv_module_list.setLayoutManager(manager);
        rv_module_list.setAdapter(adapter);
    }

    class MyModuleListAdapter extends RecyclerView.Adapter<MyModuleListAdapter.MyViewHolder> {

        private List<Integer> icons;
        private List<String> names;
        private LayoutInflater inflater;

        MyModuleListAdapter(List<Integer> icons, List<String> names) {
            super();
            this.icons = icons;
            this.names = names;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyModuleListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_module_list_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyModuleListAdapter.MyViewHolder myViewHolder, final int i) {
            myViewHolder.rl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (i <= 8) {
                        Intent intent = new Intent();
                        intent.putExtra("position", i);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    if (i == 9) {
                        hop2Activity(Ac_batch_check.class);

                    }
                }
            });
            myViewHolder.tv_name.setText(names.get(i));
            myViewHolder.iv_icon.setImageResource(icons.get(i));
        }

        @Override
        public int getItemCount() {
            return icons.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_icon;
            TextView tv_name;
            RelativeLayout rl_container;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                iv_icon = itemView.findViewById(R.id.icon);
                tv_name = itemView.findViewById(R.id.name);
                rl_container = itemView.findViewById(R.id.module_container);

            }
        }
    }
}
