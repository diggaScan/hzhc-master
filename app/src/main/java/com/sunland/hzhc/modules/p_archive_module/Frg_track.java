package com.sunland.hzhc.modules.p_archive_module;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunland.hzhc.modules.p_archive_module.track_bean.GjList;
import com.sunland.hzhc.modules.p_archive_module.track_bean.TrackResBean;
import com.sunland.hzhc.modules.sfz_module.beans.TrackReqBean;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Frg_track extends Frg_base implements OnRequestCallback {

    private RequestManager mRequestManager;

    @BindView(R.id.track_infos)
    public RecyclerView rv_track_infos;

    private List<GjList> dataSet;
    private MyRvAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.frg_track;
    }

    @Override
    public void initView() {
        dataSet = new ArrayList<>();
        mRequestManager = new RequestManager(context, this);
        adapter = new MyRvAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_track_infos.setAdapter(adapter);
        rv_track_infos.setLayoutManager(manager);
        queryYdjwData(Dictionary.PERSON_LOCUS_INFOS);


    }

    public void queryYdjwData(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequest();
    }

    private BaseRequestBean assembleRequestObj(String reqName) {
        TrackReqBean trackReqBean = new TrackReqBean();
        assembleBasicObj(trackReqBean);
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
        TrackResBean resBean = (TrackResBean) bean;
        dataSet.clear();
        dataSet.addAll(resBean.getGjList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return TrackResBean.class;
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
