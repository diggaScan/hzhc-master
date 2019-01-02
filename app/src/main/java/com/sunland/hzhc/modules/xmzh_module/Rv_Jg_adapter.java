package com.sunland.hzhc.modules.xmzh_module;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunland.hzhc.R;

import java.util.List;

public class Rv_Jg_adapter extends RecyclerView.Adapter<Rv_Jg_adapter.MyViewHolder> {


    private List<String> dataSet;
    private LayoutInflater inflater;
    private OnItemClickedListener onItemClickedListener;

    private TextView pre_clicked_tv;
    private Resources res;
    private int init_pos = 0;

    public Rv_Jg_adapter(Context context, List<String> dataSet) {
        super();
        this.dataSet = dataSet;
        inflater = LayoutInflater.from(context);
        res = context.getResources();
    }

    public void setInit_pos(int position) {
        this.init_pos = position;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public Rv_Jg_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.rv_jg_item, viewGroup, false);
        return new Rv_Jg_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Rv_Jg_adapter.MyViewHolder myViewHolder, final int i) {
        if (init_pos == i) {
            myViewHolder.tv_name.setTextColor(res.getColor(R.color.list_clicked_color));
            pre_clicked_tv = myViewHolder.tv_name;
        } else {
            myViewHolder.tv_name.setTextColor(res.getColor(R.color.list_unclicked_color));
        }
        myViewHolder.tv_name.setText(dataSet.get(i));
        myViewHolder.name_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pre_clicked_tv.setTextColor(res.getColor(R.color.list_unclicked_color));
                myViewHolder.tv_name.setTextColor(res.getColor(R.color.list_clicked_color));
                pre_clicked_tv = myViewHolder.tv_name;
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClicked(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout name_container;
        TextView tv_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_container = itemView.findViewById(R.id.name_container);
            tv_name = itemView.findViewById(R.id.name);
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(int position);
    }
}
