package com.sunland.hzhc.modules.case_module;

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

import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_case_cate.DwBaseInfo;
import com.sunland.hzhc.bean.i_charge_case.DwListResBean;
import com.sunland.hzhc.customView.CancelableEdit;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.modules.Frg_search_result;
import com.sunland.hzhc.modules.Hotel_module.Loadable_adapter;
import com.sunland.hzhc.modules.OnItemSelected;
import com.sunland.hzhc.modules.xmzh_module.Rv_Jg_adapter;
import com.sunland.hzhc.recycler_config.Rv_Item_decoration;
import com.sunland.hzhc.utils.AssetDBReader;
import com.sunland.hzhc.utils.WindowInfoUtils;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Ac_case_gxdw extends Ac_base_info implements OnItemSelected {

    @BindView(R.id.district_list)
    public RecyclerView rv_district_list;
    @BindView(R.id.dw_list)
    public RecyclerView rv_dw_list;
    @BindView(R.id.popup_cover)
    public View cover;
    @BindView(R.id.search)
    public CancelableEdit et_search;
    @BindView(R.id.main_content)
    public FrameLayout main_content;
    @BindView(R.id.enter_query)
    public TextView tv_query;

    private RequestManager mRequestManager;
    private AssetDBReader assetDBReader;
    private SQLiteDatabase database;
    private Loadable_adapter d_adapter;
    private Rv_Jg_adapter dw_adapter;

    private List<String> districts;
    private List<String> xzqh;
    private List<String> dw;
    private List<String> dwdm;

    private List<String> all_dwmc;
    private List<String> all_dwdm;
    private boolean showSearchIcon;
    private boolean shownResultFrg;

    private Frg_search_result frg_search_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_case_gxdw);
        showNavIcon(true);
        setToolbarTitle("管辖单位列表");
        mRequestManager = new RequestManager(this, this);
        readDb();
        showDistricts();
        initView();
        queryYdjwDataNoDialog("QUERY_ALL_CASE_INFO",V_config.QUERY_ALL_CASE_INFO);
        queryYdjwDataX();
    }

    @Override
    public void handleIntent() {

    }

    @Override
    public void onChosenItem(String code, String name) {
        Intent intent = new Intent();
        intent.putExtra("code", code);
        intent.putExtra("name", name);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void initView() {

        frg_search_result = new Frg_search_result();
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

                frg_search_result.setSearch_index(input, 3);
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

    private void showDistricts() {
        districts = new ArrayList<>();
        xzqh = new ArrayList<>();
        dw = new ArrayList<>();
        dwdm = new ArrayList<>();

        all_dwdm = new ArrayList<>();
        all_dwmc = new ArrayList<>();

        d_adapter = new Loadable_adapter(this, districts);
        dw_adapter = new Rv_Jg_adapter(this, dw);
        d_adapter.setOnItemClickedListener(new Loadable_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (position == 0) {
                    dw.clear();
                    dwdm.clear();
                    dw.addAll(all_dwmc);
                    dwdm.addAll(all_dwdm);
                    dw_adapter.notifyDataSetChanged();
                } else {
                    showWbmc(xzqh.get(position) + "%");
                }

            }
        });

        dw_adapter.setOnItemClickedListener(new Rv_Jg_adapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                String name = dw.get(position);
                String code = dwdm.get(position);
                onChosenItem(code, name);

            }
        });

        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);

        rv_district_list.setAdapter(d_adapter);
        rv_dw_list.setAdapter(dw_adapter);

        rv_district_list.setLayoutManager(manager1);
        rv_dw_list.setLayoutManager(manager2);

        rv_dw_list.addItemDecoration(new Rv_Item_decoration(this));
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
    public BaseRequestBean assembleRequestObj(String reqName) {
        BaseRequestBean bean = new BaseRequestBean();
        assembleBasicRequest(bean);
        return bean;
    }


    private void showWbmc(String xzqh) {
        Cursor cursor = database.rawQuery("SELECT DWMC,DWDM FROM dwlb WHERE DWDM LIKE ?", new String[]{xzqh});
        dw.clear();
        dwdm.clear();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dw.add(cursor.getString(0));
                dwdm.add(cursor.getString(1));
                cursor.moveToNext();
            }
            dw_adapter.notifyDataSetChanged();
            cursor.close();
        }
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        d_adapter.hasLoaded(true);
        d_adapter.notifyItemChanged(0);
        DwListResBean dwListResBean = (DwListResBean) resultBase;
        if (dwListResBean == null) {
            Toast.makeText(this, "案件管辖单位接口异常", Toast.LENGTH_SHORT).show();
            return;
        }

        List<DwBaseInfo> dwBaseInfoList = dwListResBean.getDwlist();
        if (dwBaseInfoList == null || dwBaseInfoList.isEmpty()) {
            Toast.makeText(this, "无相关管辖单位", Toast.LENGTH_SHORT).show();
            return;
        }
        dw.clear();
        dwdm.clear();
        for (DwBaseInfo info : dwBaseInfoList) {
            all_dwmc.add(info.getName());
            all_dwdm.add(info.getCode());
        }
        dw.addAll(all_dwmc);
        dwdm.addAll(all_dwdm);
        dw_adapter.notifyDataSetChanged();

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
                    lp.leftMargin = WindowInfoUtils.dp2px(Ac_case_gxdw.this, 8);
                    lp.rightMargin = WindowInfoUtils.dp2px(Ac_case_gxdw.this, 8);
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
                        lp.leftMargin = WindowInfoUtils.dp2px(Ac_case_gxdw.this, 8);
                        lp.rightMargin = WindowInfoUtils.dp2px(Ac_case_gxdw.this, 8);
                        et_search.setLayoutParams(lp);
                    }
                });
            }
        }
    }

}
