package com.sunland.hzhc.modules.p_archive_module;

import android.widget.TextView;

import com.sunland.hzhc.Dictionary;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.hzhc.modules.BaseRequestBean;
import com.sunland.hzhc.modules.p_archive_module.bean.InfoRYDADHHM;
import com.sunland.hzhc.modules.p_archive_module.bean.PersonArchiveResBean;
import com.sunland.hzhc.modules.p_archive_module.bean.RYDAInfoGZDW;
import com.sunland.hzhc.modules.sfz_module.beans.ArchiveReqBean;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.util.List;

import butterknife.BindView;

public class Frg_archive extends Frg_base implements OnRequestCallback {

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

    private RequestManager mRequestManager;

    @Override
    public int setLayoutId() {
        return R.layout.frg_archive;
    }

    @Override
    public void initView() {
        mRequestManager = new RequestManager(context, this);
        queryYdjwData(Dictionary.GET_PERSON_INFO_BY_SFZH);
    }

    public void queryYdjwData(String method_name) {
        mRequestManager.addRequest(Global.ip, Global.port, Global.postfix, method_name
                , assembleRequestObj(method_name), 15000);
        mRequestManager.postRequest();

    }


    public BaseRequestBean assembleRequestObj(String reqName) {
        ArchiveReqBean archiveReqBean = new ArchiveReqBean();
        assembleBasicObj(archiveReqBean);
        archiveReqBean.setSfzh(((Ac_archive) context).identity_num);
        return archiveReqBean;
    }


    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        PersonArchiveResBean resBean = (PersonArchiveResBean) bean;
        tv_name.setText(resBean.getXm());
        tv_eng_name.setText(resBean.getXm_e());
        tv_gender.setText(resBean.getXb());
        tv_gender.setText(resBean.getGj());
        tv_birth.setText(resBean.getCsrq());
        tv_birth_place.setText(resBean.getCsd());
        tv_origin_place.setText(resBean.getHjd());
        tv_id_num.setText(resBean.getSfzh());
        tv_passport.setText(resBean.getHz());
        tv_ga_passport.setText(resBean.getGa_txz());
        tv_taiwan_passport.setText(resBean.getTw_txz());
        tv_live_cert.setText(resBean.getJzz());
        tv_ethnic_group.setText(resBean.getMz());
        tv_driver_license.setText(resBean.getJsz());
        tv_citizen_card.setText(resBean.getHz_smk_yxq());

        List<InfoRYDADHHM> phone_nums = resBean.getDhhm_list();
        if (phone_nums != null) {
            StringBuilder phone_number = new StringBuilder();
            for (InfoRYDADHHM infoRYDADHHM : phone_nums) {
                phone_number.append(infoRYDADHHM.getDhhm()).append(",");
            }
            tv_phone_num.setText(phone_number);
        }

//        List<QueryInfoHY> hy = resBean.getHydj();
//        if (hy != null) {
//            StringBuilder hys = new StringBuilder();
//            for (QueryInfoHY queryInfoHY : hy) {
//                hys.append(queryInfoHY.getDjrq()).append(queryInfoHY.getPoxm()).append(queryInfoHY.getPosfzh()).append(";");
//            }
//        }

        List<RYDAInfoGZDW> gzdws = resBean.getGzdw_list();
        if (gzdws != null) {
            StringBuilder dw = new StringBuilder();
            for (RYDAInfoGZDW rydaInfoGZDW : gzdws) {
                dw.append(rydaInfoGZDW.getDwmc()).append(";");
            }
            tv_gzdw.setText(dw);
        }


//        List<QueryInfoDAJDC> ccdjs = resBean.getCcdj();
//        if (ccdjs != null) {
//            StringBuilder cc = new StringBuilder();
//            for (QueryInfoDAJDC queryInfoDAJDC : ccdjs) {
//                cc.append(queryInfoDAJDC.getCphm()).append(";");
//            }
//            tv_car_reg.setText(cc);
//        }


    }

    @Override
    public <T extends ResultBase> Class<?> getBeanClass(String reqId, String reqName) {
        return PersonArchiveResBean.class;
    }
}
