package com.sunland.hzhc.modules.case_module;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_case_cate.CaseCateResBean;
import com.sunland.hzhc.bean.i_charge_case.LbList;
import com.sunland.hzhc.customView.CancelableEdit;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.Frg_search_result;
import com.sunland.hzhc.modules.OnItemSelected;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.WindowInfoUtils;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_case_cates extends Ac_base_info implements OnItemSelected {

    @BindView(R.id.cate_list)
    public RecyclerView rv_cate_list;
    @BindView(R.id.popup_cover)
    public View cover;
    @BindView(R.id.search)
    public CancelableEdit et_search;
    @BindView(R.id.main_content)
    public FrameLayout main_content;
    @BindView(R.id.enter_query)
    public TextView tv_query;

    private List<LbList> dataSet;
    private List<String> combo_data;//结合类别code和名称
    private MyCateAdapter adapter;

    private boolean showSearchIcon;
    private boolean shownResultFrg;

    private Frg_search_result frg_search_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_case_cates);
        showNavIcon(true);
        setToolbarTitle("案件类型列表");
        initView();
        queryYdjwDataNoDialog("QUERY_ALL_CASE_CATEGORY",V_config.QUERY_ALL_CASE_CATEGORY);
        queryYdjwDataX();
        showLoading_layout(true);
    }

    @Override
    public void handleIntent() {

    }

    @Override
    public void onChosenItem(String code, String name) {
        Intent intent = new Intent();
        intent.putExtra("cate_code", code);
        intent.putExtra("cate_name", name);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initView() {
        frg_search_result = new Frg_search_result();
        dataSet = new ArrayList<>();
        combo_data = new ArrayList<>();
        adapter = new MyCateAdapter(dataSet);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_cate_list.setLayoutManager(manager);
        rv_cate_list.setAdapter(adapter);
        rv_cate_list.addItemDecoration(new Rv_Item_decoration(this));

        et_search.setOnEditTextClickedListener(new CancelableEdit.OnEditTextClickedListener() {
            @Override
            public void onEditTextClicked() {
                cover.setVisibility(View.VISIBLE);
            }
        });

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cover.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        et_search.setOnTextChangeListener(new CancelableEdit.OnTextChangeListener() {
            @Override
            public void beforeTextChange() {
            }

            @Override
            public void onTextChange() {
            }

            @Override
            public void afterTextChange() {
                showEnterButton();
                String input = et_search.getText().toString();
                if (input.isEmpty()) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.remove(frg_search_result);
                    ft.commit();
                    shownResultFrg = false;
                }
            }
        });
        tv_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et_search.getText().toString().trim();
                if (shownResultFrg) {
                    frg_search_result.update(input);
                    return;
                }

                frg_search_result.setSearch_index(input, 4);
                frg_search_result.setAjlbs(combo_data);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.main_content, frg_search_result, "search_result");
                ft.show(frg_search_result);
                ft.commit();
                shownResultFrg = true;
            }
        });
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        BaseRequestBean bean = new BaseRequestBean();
        assembleBasicRequest(bean);
        return bean;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.QUERY_ALL_CASE_CATEGORY:
                showLoading_layout(false);
                CaseCateResBean caseCateResBean = (CaseCateResBean) resultBase;
                if (caseCateResBean == null) {
                    Toast.makeText(this, "案件种类接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<LbList> lbList = caseCateResBean.getLbList();
                if (lbList == null || lbList.isEmpty()) {
                    Toast.makeText(this, "案件种类返回未空", Toast.LENGTH_SHORT).show();
                    return;
                }
                dataSet.clear();
                dataSet.addAll(lbList);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    class MyCateAdapter extends RecyclerView.Adapter<MyCateAdapter.MyViewHolder> {

        List<LbList> dataSet;
        LayoutInflater inflater;

        public MyCateAdapter(List<LbList> dataSet) {
            super();
            this.dataSet = dataSet;
            inflater = getLayoutInflater();
        }

        @NonNull
        @Override
        public MyCateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.rv_case_cate_item, viewGroup, false);
            return new MyCateAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyCateAdapter.MyViewHolder myViewHolder, int i) {
            final LbList info = dataSet.get(i);
            myViewHolder.tv_name.setText(info.getName());
            myViewHolder.tv_code.setText(info.getCode());
            combo_data.add(info.getCode() + info.getName());
            myViewHolder.cate_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChosenItem(info.getCode(), info.getName());

                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout cate_container;
            TextView tv_code;
            TextView tv_name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                cate_container = itemView.findViewById(R.id.cate_container);
                tv_code = itemView.findViewById(R.id.cate_code);
                tv_name = itemView.findViewById(R.id.cate_name);
            }
        }
    }

    private void showEnterButton() {
        final int ce_search_width = et_search.getWidth();
        if (!showSearchIcon) {
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
            animator.setDuration(300);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    showSearchIcon = true;
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ce_search_width - (int) (tv_query.getWidth() * value), et_search.getHeight());
                    lp.gravity = Gravity.CENTER_VERTICAL;
                    lp.leftMargin = WindowInfoUtils.dp2px(Ac_case_cates.this, 8);
                    lp.rightMargin = WindowInfoUtils.dp2px(Ac_case_cates.this, 8);
                    et_search.setLayoutParams(lp);

                }
            });
        }
        String q = et_search.getText().toString();
        if (q.equals("")) {
            if (showSearchIcon) {
                ValueAnimator animator2 = ValueAnimator.ofFloat(0f, 1.0f);
                animator2.setDuration(300);
                animator2.start();
                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        showSearchIcon = false;
                        float value = (float) animation.getAnimatedValue();
                        LinearLayout.LayoutParams lp;
                        if (1 - value < 0.0000001) {
                            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, et_search.getHeight());
                        } else {
                            lp = new LinearLayout.LayoutParams(ce_search_width + (int) (tv_query.getWidth() * (value))
                                    , et_search.getHeight());
                        }
                        lp.gravity = Gravity.CENTER_VERTICAL;
                        lp.leftMargin = WindowInfoUtils.dp2px(Ac_case_cates.this, 8);
                        lp.rightMargin = WindowInfoUtils.dp2px(Ac_case_cates.this, 8);
                        et_search.setLayoutParams(lp);
                    }
                });
            }
        }
    }
}
