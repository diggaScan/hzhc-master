package com.sunland.hzhc.modules.p_archive_module;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_track.GjList;
import com.sunland.hzhc.bean.i_track.TrackReqBean;
import com.sunland.hzhc.bean.i_track.TrackResBean;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Frg_track extends Frg_base {

    @BindView(R.id.track_infos)
    public RecyclerView rv_track_infos;
    @BindView(R.id.loading_layout)
    public FrameLayout loading_layout;
    @BindView(R.id.loading_icon)
    public SpinKitView loading_icon;

    private List<GjList> dataSet;
    private MyRvAdapter adapter;
    private boolean load_person_locus_info;

    @Override
    public int setLayoutId() {
        return R.layout.frg_track;
    }

    @Override
    public void initView() {
        loading_icon.setVisibility(View.GONE);
        dataSet = new ArrayList<>();
        adapter = new MyRvAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_track_infos.setAdapter(adapter);
        rv_track_infos.setLayoutManager(manager);
        rv_track_infos.addItemDecoration(new Rv_Item_decoration(context));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible) {
            queryYdjwDataNoDialog("PERSON_LOCUS_INFOS", V_config.PERSON_LOCUS_INFOS);
        }
        loading_layout.setVisibility(View.VISIBLE);
        queryYdjwDataX();

    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        TrackReqBean trackReqBean = new TrackReqBean();
        assembleBasicRequest(trackReqBean);
        trackReqBean.setSfzh(((Ac_archive) context).identity_num);
        trackReqBean.setCount(100);
        trackReqBean.setQqsj_q("19930101");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String pda_time = simpleDateFormat.format(System.currentTimeMillis());
        trackReqBean.setQqsj_z(pda_time);
        return trackReqBean;
    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        loading_layout.setVisibility(View.GONE);

        TrackResBean resBean = (TrackResBean) bean;
        if (resBean == null) {
            Toast.makeText(context, "人员轨迹接口异常", Toast.LENGTH_SHORT).show();
            return;
        }
        List<GjList> list = resBean.getGjList();
        if (list == null || list.isEmpty()) {
            Toast.makeText(context, "无轨迹信息", Toast.LENGTH_SHORT).show();
            return;
        }
        dataSet.clear();
        dataSet.addAll(list);
        adapter.notifyDataSetChanged();
        load_person_locus_info = true;
    }

    @Override
    public void onFragmentVisible() {
        super.onFragmentVisible();
        if (load_person_locus_info) {
            return;
        }
        loading_icon.setVisibility(View.VISIBLE);
        queryYdjwData("PERSON_LOCUS_INFOS", V_config.PERSON_LOCUS_INFOS);

    }

    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {

        private List<GjList> dataSet;
        private LayoutInflater inflater;

        MyRvAdapter(List<GjList> dataSet) {
            super();
            this.dataSet = dataSet;
            this.inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_track_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder myViewHolder, int i) {
            GjList info = dataSet.get(i);
            myViewHolder.tv_sj.setText(info.getGjsj());
            myViewHolder.tv_nr.setText(info.getGjnr());
            myViewHolder.tv_lb.setText(info.getGjlb());
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_lb;
            TextView tv_sj;
            TextView tv_nr;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_lb = itemView.findViewById(R.id.gjlb);
                tv_nr = itemView.findViewById(R.id.gjnr);
                tv_sj = itemView.findViewById(R.id.gjsj);
            }
        }
    }
}
