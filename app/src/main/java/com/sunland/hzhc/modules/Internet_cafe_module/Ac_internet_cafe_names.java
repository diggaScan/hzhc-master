package com.sunland.hzhc.modules.Internet_cafe_module;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_wb_list.WbBaseInfo;
import com.sunland.hzhc.bean.i_wb_list.WbListReqBean;
import com.sunland.hzhc.bean.i_wb_list.WbListResBean;
import com.sunland.hzhc.customView.CancelableEdit;
import com.sunland.hzhc.customView.DragToRefreshView.DragToRefreshView;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.Frg_search_result;
import com.sunland.hzhc.modules.Hotel_module.Loadable_adapter;
import com.sunland.hzhc.modules.OnItemSelected;
import com.sunland.hzhc.modules.xmzh_module.Rv_Jg_adapter;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;
import com.sunland.hzhc.utils.WindowInfoUtils;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_internet_cafe_names extends Ac_base_info implements OnItemSelected {

    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;
    @BindView(R.id.district_list)
    public RecyclerView rv_district_list;
    @BindView(R.id.wb_list)
    public RecyclerView rv_wb_list;
    @BindView(R.id.refresh)
    public DragToRefreshView d2r_refresh;
    @BindView(R.id.popup_cover)
    public View cover;
    @BindView(R.id.search)
    public CancelableEdit et_search;
    @BindView(R.id.enter_query)
    public TextView tv_query;
    private List<String> districts;
    private List<String> xzqh;
    private List<String> wbmcs;
    private List<String> wbbh;

    private List<String> all_wbmc;
    private List<String> all_wbbh;

    private Loadable_adapter d_adapter;
    private Rv_Jg_adapter wb_adapter;

    private final int items_per_page = 50;
    private int cur_page = 1;
    private int add_pages = 0;

    private boolean hasLoaded;
    private boolean showSearchIcon;
    private boolean shownResultFrg;

    private Frg_search_result frg_search_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_internet_cafe_names);
        showNavIcon(true);
        setToolbarTitle("网吧列表");
        readZdDb();
        showDistrict();
        initView();
        queryYdjwDataNoDialog("GET_INTERNET_CAFE_INFO",V_config.GET_INTERNET_CAFE_INFO);
        queryYdjwDataX();
    }

    @Override
    public void handleIntent() {

    }

    private void initView() {

        frg_search_result = new Frg_search_result();
        d2r_refresh.unableHeaderRefresh(false);
        d2r_refresh.unableFooterRefresh(false);
        d2r_refresh.setUpdateListener(new DragToRefreshView.OnUpdateListener() {
            @Override
            public void onRefreshing(DragToRefreshView view) {
                if (view.isFooterRefreshing()) {
                    add_pages++;
                    cur_page = 1 + add_pages;
                    queryYdjwDataNoDialog("GET_INTERNET_CAFE_INFO",V_config.GET_INTERNET_CAFE_INFO);
                    queryYdjwDataX();
                }
            }

            @Override
            public void onFinished(DragToRefreshView view) {
                if (view.getState() == DragToRefreshView.State.footer_release_to_load) {
                    int scroll_position = wbmcs.size() - items_per_page;
                    if (scroll_position > 0) {
                        rv_wb_list.scrollToPosition(wbmcs.size());
                    }
                }
            }
        });
        d2r_refresh.addMainContent(rv_wb_list);

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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                String input = et_search.getText().toString().trim();
                if (shownResultFrg) {
                    frg_search_result.update(input);
                    return;
                }
                frg_search_result.setSearch_index(input, 2);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.main_content, frg_search_result, "search_result");
                ft.show(frg_search_result);
                ft.commit();
                shownResultFrg = true;
            }
        });
    }

    private void readZdDb() {
        assetDBReader = new AssetDBReader(this);
        database = assetDBReader.readAssetDb(this.getExternalFilesDir("hzydjwzd").getAbsolutePath(), "hzydjwzd.db");
    }

    private void showDistrict() {
        districts = new ArrayList<>();
        xzqh = new ArrayList<>();
        wbmcs = new ArrayList<>();
        wbbh = new ArrayList<>();

        all_wbmc = new ArrayList<>();
        all_wbbh = new ArrayList<>();

        d_adapter = new Loadable_adapter(this, districts);
        wb_adapter = new Rv_Jg_adapter(this, wbmcs);
        d_adapter.setOnItemClickedListener(new Loadable_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (position == 0) {
                    d2r_refresh.unableFooterRefresh(true);
                    wbmcs.clear();
                    wbbh.clear();
                    wbmcs.addAll(all_wbmc);
                    wbbh.addAll(all_wbbh);
                    wb_adapter.notifyDataSetChanged();
                } else {
                    d2r_refresh.unableFooterRefresh(false);
                    showWbmc(xzqh.get(position));
                }

            }
        });

        wb_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                String name = wbmcs.get(position);
                String code = wbbh.get(position);
                onChosenItem(code, name);

            }
        });
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);

        rv_district_list.setAdapter(d_adapter);
        rv_wb_list.setAdapter(wb_adapter);

        rv_district_list.setLayoutManager(manager1);
        rv_wb_list.setLayoutManager(manager2);

        rv_wb_list.addItemDecoration(new Rv_Item_decoration(this));
        rv_district_list.addItemDecoration(new Rv_Item_decoration(this));

        Cursor cursor = database.rawQuery("SELECT WBMC,XZQH FROM wbxzqhlb", null);
        districts.clear();
        xzqh.clear();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                districts.add(cursor.getString(0));
                xzqh.add(cursor.getString(1));
                cursor.moveToNext();
            }
            d_adapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    @Override
    public void onChosenItem(String code, String name) {
        Intent intent = new Intent();
        intent.putExtra("code", code);
        intent.putExtra("name", name);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void showWbmc(String xzqh) {
        Cursor cursor = database.rawQuery("SELECT MC,BH FROM wbmclb WHERE XZQH = ?", new String[]{xzqh});
        wbmcs.clear();
        wbbh.clear();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                wbmcs.add(cursor.getString(0));
                wbbh.add(cursor.getString(1));
                cursor.moveToNext();
            }
            wb_adapter.notifyDataSetChanged();
            cursor.close();
        }
    }


    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.GET_INTERNET_CAFE_INFO:
                WbListReqBean bean = new WbListReqBean();
                assembleBasicRequest(bean);
                bean.setCurrentPage(cur_page);
                bean.setTotalCount(items_per_page);
                return bean;
        }
        return null;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        d2r_refresh.unableFooterRefresh(true);
        d2r_refresh.dismiss();
        WbListResBean wbListResBean = (WbListResBean) resultBase;

        if (!hasLoaded) {
            d_adapter.hasLoaded(true);
            d_adapter.notifyItemChanged(0);
            hasLoaded = true;
        }

        if (wbListResBean == null) {
            Toast.makeText(this, "网吧基本信息接口异常", Toast.LENGTH_SHORT).show();
            return;
        }

        List<WbBaseInfo> wbBaseInfos = wbListResBean.getResps();
        if (wbBaseInfos == null || wbBaseInfos.isEmpty()) {
            Toast.makeText(this, "无法获取网吧信息", Toast.LENGTH_SHORT).show();
            return;
        }

        for (WbBaseInfo info : wbBaseInfos) {
            all_wbmc.add(info.getWbmc());
            all_wbbh.add(info.getWb_code());
        }

        if (wbBaseInfos.size() < items_per_page) {
            d2r_refresh.unableFooterRefresh(false);
        }
        wbmcs.clear();
        wbbh.clear();
        wbmcs.addAll(all_wbmc);
        wbbh.addAll(all_wbbh);
        wb_adapter.notifyDataSetChanged();
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
                    lp.leftMargin = WindowInfoUtils.dp2px(Ac_internet_cafe_names.this, 8);
                    lp.rightMargin = WindowInfoUtils.dp2px(Ac_internet_cafe_names.this, 8);
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
                        lp.leftMargin = WindowInfoUtils.dp2px(Ac_internet_cafe_names.this, 8);
                        lp.rightMargin = WindowInfoUtils.dp2px(Ac_internet_cafe_names.this, 8);
                        et_search.setLayoutParams(lp);
                    }
                });
            }
        }
    }
}
