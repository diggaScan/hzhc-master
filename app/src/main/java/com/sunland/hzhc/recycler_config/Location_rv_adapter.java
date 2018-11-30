package com.sunland.hzhc.recycler_config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunland.hzhc.R;

import java.util.HashMap;
import java.util.List;

public class Location_rv_adapter extends RecyclerView.Adapter<Location_rv_adapter.MyViewHolder> {

    private Context context;
    private List<HashMap<String, String>> list;
    private LayoutInflater inflater;
    private OnRvItemClickedListener onRvItemClickedListener;

    public Location_rv_adapter(Context context, List<HashMap<String, String>> list) {
        super();
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setOnRvItemClickedListener(OnRvItemClickedListener onRvItemClickedListener) {
        this.onRvItemClickedListener = onRvItemClickedListener;
    }

    @NonNull
    @Override
    public Location_rv_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_location_rv_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Location_rv_adapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.address.setText(list.get(i).get("dz"));
        myViewHolder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRvItemClickedListener!=null){
                    onRvItemClickedListener.onClick(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
        }
    }


}
