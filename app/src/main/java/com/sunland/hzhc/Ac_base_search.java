package com.sunland.hzhc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunlandgroup.def.bean.result.ResultBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Ac_base_search extends Ac_base_info {

    @BindView(R.id.search_container)
    public FrameLayout search_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_base_search);
    }

    public void inflateContainer(int id) {
        View view=getLayoutInflater().inflate(id, search_container, true);
        ButterKnife.bind(this,view);
    }

    @Override
    public void handleIntent() {

    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        return null;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {

    }
}
