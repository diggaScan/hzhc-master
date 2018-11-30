package com.sunland.hzhc.recycler_config;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunland.hzhc.R;

import java.util.List;

public class Function_rv_adapter extends RecyclerView.Adapter<Function_rv_adapter.MyViewHolder> {

    private List<RvFuntionBean> list;
    private Context context;
    private LayoutInflater inflater;
    private Resources res;
    private OnFunctionClickedListener listener;

    public void setListener(OnFunctionClickedListener listener) {
        this.listener = listener;
    }

    public Function_rv_adapter(Context context, List<RvFuntionBean> list) {
        super();
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
        this.res = context.getResources();
    }

    @NonNull
    @Override
    public Function_rv_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_function_rv_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Function_rv_adapter.MyViewHolder myViewHolder, final int i) {
        RvFuntionBean bean = list.get(i);
        myViewHolder.imageView.setImageDrawable(res.getDrawable(bean.getImage_id()));
        myViewHolder.textView.setText(bean.getFuntion_name());
        myViewHolder.item_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClicked(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;
        public LinearLayout item_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textView = itemView.findViewById(R.id.text);
            item_container = itemView.findViewById(R.id.item_container);

        }
    }

    public interface OnFunctionClickedListener {
        void onClicked(int position);
    }
}
