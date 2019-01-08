package com.sunland.hzhc.modules.p_archive_module;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.V_config;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_archive.ArchiveReqBean;
import com.sunland.hzhc.bean.i_archive.InfoRYDADHHM;
import com.sunland.hzhc.bean.i_archive.PersonArchiveResBean;
import com.sunland.hzhc.bean.i_archive.QueryInfoDAJDC;
import com.sunland.hzhc.bean.i_archive.QueryInfoHY;
import com.sunland.hzhc.bean.i_archive.RYDAInfoGZDW;

import java.util.List;

import butterknife.BindView;

public class Frg_archive extends Frg_base {

    @BindView(R.id.name)
    public TextView tv_name;
    @BindView(R.id.eng_name)
    public TextView tv_eng_name;
    @BindView(R.id.gender)
    public TextView tv_gender;
    @BindView(R.id.nation)
    public TextView tv_nation;
    @BindView(R.id.birth)
    public TextView tv_birth;
    @BindView(R.id.birth_place)
    public TextView tv_birth_place;
    @BindView(R.id.origin_place)
    public TextView tv_origin_place;
    @BindView(R.id.id_num)
    public TextView tv_id_num;
    @BindView(R.id.passport)
    public TextView tv_passport;
    @BindView(R.id.ga_passport)
    public TextView tv_ga_passport;
    @BindView(R.id.taiwan_passport)
    public TextView tv_taiwan_passport;
    @BindView(R.id.live_cert)
    public TextView tv_live_cert;
    @BindView(R.id.ethnic_group)
    public TextView tv_ethnic_group;
    @BindView(R.id.driver_license)
    public TextView tv_driver_license;
    @BindView(R.id.citizen_card)
    public TextView tv_citizen_card;
    @BindView(R.id.phone_num)
    public TextView tv_phone_num;
    @BindView(R.id.marriage_reg)
    public TextView tv_marriage_num;
    @BindView(R.id.gzdw)
    public TextView tv_gzdw;
    @BindView(R.id.car_reg)
    public TextView tv_car_reg;
    @BindView(R.id.loading_layout)
    public FrameLayout loading_layout;
    @BindView(R.id.loading_icon)
    public SpinKitView loading_icon;

    private boolean load_get_person_info_by_sfzh;


    @Override
    public int setLayoutId() {
        return R.layout.frg_archive;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isVisible) {
            loading_layout.setVisibility(View.VISIBLE);
            queryYdjwDataNoDialog("GET_PERSON_INFO_BY_SFZH", V_config.GET_PERSON_INFO_BY_SFZH);
            queryYdjwDataX();
        }

    }

    @Override
    public void initView() {

    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        ArchiveReqBean archiveReqBean = new ArchiveReqBean();
        assembleBasicRequest(archiveReqBean);
        archiveReqBean.setSfzh(((Ac_archive) context).identity_num);
        return archiveReqBean;
    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        loading_layout.setVisibility(View.GONE);
        PersonArchiveResBean resBean = (PersonArchiveResBean) bean;
        if (resBean == null) {
            Toast.makeText(context, "人员档案信息接口异常", Toast.LENGTH_SHORT).show();
            return;
        }
        load_get_person_info_by_sfzh = true;
        setText(tv_name, resBean.getXm());
        setText(tv_eng_name, resBean.getXm_e());
        setText(tv_gender, resBean.getXb());
        setText(tv_nation, resBean.getGj());
        setText(tv_birth, resBean.getCsrq());
        setText(tv_birth_place, resBean.getCsd());
        setText(tv_origin_place, resBean.getHjd());
        setText(tv_id_num, resBean.getSfzh());
        setText(tv_passport, resBean.getHz());
        setText(tv_ga_passport, resBean.getGa_txz());
        setText(tv_taiwan_passport, resBean.getTw_txz());
        setText(tv_live_cert, resBean.getJzz());
        setText(tv_ethnic_group, resBean.getMz());
        setText(tv_driver_license, resBean.getJsz());
        setText(tv_citizen_card, resBean.getHz_smk_yxq());

        List<InfoRYDADHHM> phone_nums = resBean.getDhhm_list();
        if (phone_nums != null) {
            StringBuilder phone_number = new StringBuilder();
            for (InfoRYDADHHM infoRYDADHHM : phone_nums) {
                phone_number.append(infoRYDADHHM.getDhhm()).append(",");
            }
            setText(tv_phone_num, phone_number.toString());
        }

        List<QueryInfoHY> hy = resBean.getHydj();
        if (hy != null) {
            StringBuilder hys = new StringBuilder();
            for (QueryInfoHY queryInfoHY : hy) {
                hys.append(queryInfoHY.getDjrq()).append(queryInfoHY.getPoxm()).append(queryInfoHY.getPosfzh()).append(";");
            }
            setText(tv_marriage_num, hys.toString());
        }

        List<RYDAInfoGZDW> gzdws = resBean.getGzdw_list();
        if (gzdws != null) {
            StringBuilder dw = new StringBuilder();
            for (RYDAInfoGZDW rydaInfoGZDW : gzdws) {
                dw.append(rydaInfoGZDW.getDwmc()).append(";");
            }
            setText(tv_gzdw, dw.toString());
        }


        List<QueryInfoDAJDC> ccdjs = resBean.getCcdj();
        if (ccdjs != null) {
            StringBuilder cc = new StringBuilder();
            for (QueryInfoDAJDC queryInfoDAJDC : ccdjs) {
                cc.append(queryInfoDAJDC.getCphm()).append(";");
            }
            setText(tv_car_reg, cc.toString());
        }
    }

    @Override
    public void onFragmentVisible() {
        super.onFragmentVisible();
        if (load_get_person_info_by_sfzh) {
            return;
        }
        queryYdjwDataNoDialog("GET_PERSON_INFO_BY_SFZH", V_config.GET_PERSON_INFO_BY_SFZH);
        queryYdjwDataX();
    }
}
