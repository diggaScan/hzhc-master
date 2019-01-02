package com.sunland.hzhc.modules.Hotel_module;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_hotel_names.LgBaseInfo;
import com.sunland.hzhc.bean.i_hotel_names.LgLbResBean;
import com.sunland.hzhc.customView.CancelableEdit;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.Frg_search_result;
import com.sunland.hzhc.modules.OnItemSelected;
import com.sunland.hzhc.modules.xmzh_module.Rv_Jg_adapter;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;
import com.sunland.hzhc.utils.WindowInfoUtils;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class Ac_hotel_name extends Ac_base_info implements OnItemSelected {

    @BindView(R.id.district_list)
    public RecyclerView rv_districts;
    @BindView(R.id.hotel_list)
    public RecyclerView rv_hotel_list;
    @BindView(R.id.popup_cover)
    public View cover;
    @BindView(R.id.search)
    public CancelableEdit et_search;
    @BindView(R.id.main_content)
    public FrameLayout main_content;
    @BindView(R.id.enter_query)
    public TextView tv_query;

    private Loadable_adapter d_adapter;
    private Rv_Jg_adapter h_adapter;

    private List<String> dataSet_hotels;
    private List<String> lgdm;

    private List<String> all_lg;
    private List<String> all_lg_code;

    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;
    private boolean showSearchIcon;
    private boolean shownResultFrg;

    private Frg_search_result frg_search_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_hotel_name);
        showNavIcon(true);
        setToolbarTitle("旅馆列表");
        initView();
        readDb();
        queryYdjwDataNoDialog("GET_ALL_HOTELS",V_config.GET_ALL_HOTELS);
        queryYdjwDataX();
    }

    private void initView() {
        frg_search_result = new Frg_search_result();
        dataSet_hotels = new ArrayList<>();
        lgdm = new ArrayList<>();
        all_lg = new ArrayList<>();
        all_lg_code = new ArrayList<>();
        d_adapter = new Loadable_adapter(this, Arrays.asList(DataModel.HANGZHOU_DISTRICTS));
        h_adapter = new Rv_Jg_adapter(this, dataSet_hotels);
        d_adapter.setOnItemClickedListener(new Loadable_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (position == 0) {
                    dataSet_hotels.clear();
                    lgdm.clear();
                    dataSet_hotels.addAll(all_lg);
                    lgdm.addAll(all_lg_code);
                    h_adapter.notifyDataSetChanged();
                } else {
                    showLglb(DataModel.HANGZHOU_DISTRICT_CODE[position] + "%");
                }
            }
        });

        h_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                String name = dataSet_hotels.get(position);
                String code = lgdm.get(position);
                onChosenItem(code, name);
            }
        });

        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        rv_districts.setAdapter(d_adapter);
        rv_hotel_list.setAdapter(h_adapter);
        rv_districts.setLayoutManager(manager1);
        rv_hotel_list.setLayoutManager(manager2);
        rv_districts.addItemDecoration(new Rv_Item_decoration(this));
        rv_hotel_list.addItemDecoration(new Rv_Item_decoration(this));

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

                frg_search_result.setSearch_index(input, 1);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.main_content, frg_search_result, "search_result");
                ft.show(frg_search_result);
                ft.commit();
                shownResultFrg = true;
            }
        });
    }

    private void readDb() {
        assetDBReader = new AssetDBReader(this);
        database = assetDBReader.readAssetDb(this.getExternalFilesDir("hzydjwzd").getAbsolutePath(), "hzydjwzd.db");
    }

    private void showLglb(String code) {
        Cursor cursor = database.rawQuery("SELECT DWMC,DWDM FROM lglb WHERE DWDM LIKE ?", new String[]{code});
        dataSet_hotels.clear();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dataSet_hotels.add(cursor.getString(0));
                lgdm.add(cursor.getString(1));
                cursor.moveToNext();
            }
            h_adapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    @Override
    public void handleIntent() {

    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_ALL_HOTELS:
                BaseRequestBean bean = new BaseRequestBean();
                assembleBasicRequest(bean);
                return bean;
        }
        return null;
    }

    @Override
    public void onChosenItem(String code, String name) {
        Intent intent = new Intent();
        intent.putExtra("code", code);
        intent.putExtra("name", name);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        LgLbResBean lgLbResBean = (LgLbResBean) resultBase;
        d_adapter.hasLoaded(true);
        d_adapter.notifyItemChanged(0);
        if (lgLbResBean == null) {
            Toast.makeText(this, "获取旅馆名称接口异常", Toast.LENGTH_SHORT).show();
            return;
        }

        List<LgBaseInfo> lgBaseInfos = lgLbResBean.getLGList();
        if (lgBaseInfos == null) {
            Toast.makeText(this, "无法获取杭州全市旅馆信息", Toast.LENGTH_SHORT).show();
            return;
        }

        for (LgBaseInfo info : lgBaseInfos) {
            all_lg.add(info.getLgmc());
            all_lg_code.add(info.getLgmc_code());
        }

        dataSet_hotels.addAll(all_lg);
        lgdm.addAll(all_lg_code);

        h_adapter.notifyDataSetChanged();
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
                    lp.leftMargin = WindowInfoUtils.dp2px(Ac_hotel_name.this, 8);
                    lp.rightMargin = WindowInfoUtils.dp2px(Ac_hotel_name.this, 8);
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
                        lp.leftMargin = WindowInfoUtils.dp2px(Ac_hotel_name.this, 8);
                        lp.rightMargin = WindowInfoUtils.dp2px(Ac_hotel_name.this, 8);
                        et_search.setLayoutParams(lp);
                    }
                });
            }
        }
    }
}
