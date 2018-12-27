package com.sunland.hzhc.modules.Hotel_module;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.sunland.hzhc.R;

import java.util.List;

public class Loadable_adapter extends RecyclerView.Adapter<Loadable_adapter.MyViewHolder> {


    private List<String> dataSet;
    private LayoutInflater inflater;
    private OnItemClickedListener onItemClickedListener;
    private boolean hasLoaded;

    private Resources res;
    public boolean has_clicked;
    public TextView pre_clicked_tv;

    public Loadable_adapter(Context context, List<String> dataSet) {
        super();
        this.dataSet = dataSet;
        inflater = LayoutInflater.from(context);
        res = context.getResources();
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public Loadable_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.rv_loadable_item, viewGroup, false);
        return new Loadable_adapter.MyViewHolder(view);
    }

    public void hasLoaded(boolean hasLoaded) {
        this.hasLoaded = hasLoaded;
    }


    @Override
    public void onBindViewHolder(@NonNull final Loadable_adapter.MyViewHolder myViewHolder, final int i) {
        if (0 == i&&has_clicked!=true) {
            myViewHolder.tv_name.setTextColor(res.getColor(R.color.list_clicked_color));
            pre_clicked_tv = myViewHolder.tv_name;
        } else {
            myViewHolder.tv_name.setTextColor(res.getColor(R.color.list_unclicked_color));
        }
        if (i == 0 && !hasLoaded) {
            myViewHolder.skv_load.setVisibility(View.VISIBLE);
            myViewHolder.tv_name.setVisibility(View.GONE);
        } else {
            myViewHolder.tv_name.setVisibility(View.VISIBLE);
            myViewHolder.skv_load.setVisibility(View.GONE);
            myViewHolder.tv_name.setText(dataSet.get(i));
            myViewHolder.name_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    has_clicked=true;
                    pre_clicked_tv.setTextColor(res.getColor(R.color.list_unclicked_color));
                    myViewHolder.tv_name.setTextColor(res.getColor(R.color.list_clicked_color));
                    pre_clicked_tv = myViewHolder.tv_name;
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout name_container;
        TextView tv_name;
        SpinKitView skv_load;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_container = itemView.findViewById(R.id.name_container);
            tv_name = itemView.findViewById(R.id.name);
            skv_load = itemView.findViewById(R.id.loading_icon);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(int position);
    }
}
