package com.sunland.hzhc.modules.jdc_module;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.concretejungle.spinbutton.SpinButton;
import com.sunland.hzhc.Ac_main;
import com.sunland.hzhc.DataModel;
import com.sunland.hzhc.Frg_base;
import com.sunland.hzhc.R;
import com.sunland.sunlandkeyboard.SunlandKeyBoardManager;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;


public class Frg_vehicle extends Frg_base {
    @BindView(R.id.vehicle)
    public SpinButton sb_vehicle;
    @BindView(R.id.engine_num)
    public EditText et_engine_num;
    @BindView(R.id.vehicle_id)
    public EditText et_vehivle_id;
    @BindView(R.id.number)
    public EditText et_number;
    @BindView(R.id.relativeLayout)
    public RelativeLayout relativeLayout;
    @BindView(R.id.scrollView)
    public ScrollView scrollView;

    @Override
    public int setLayoutId() {
        return R.layout.frg_vehicle;
    }

    @Override
    public void initView() {
        ((Ac_main) context).sunlandKeyBoardManager.addTarget(((Ac_main) context).myKeyBoardView, et_number,
                SunlandKeyBoardManager.KeyboardMode.VEHICLE_PLATE);
        sb_vehicle.setHeaderTitle("选择车辆类型");
        sb_vehicle.setDataSet(Arrays.asList(DataModel.VEHICLEMODELS));
        sb_vehicle.setSelection(0);
    }

    @OnClick({R.id.query, R.id.scan})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.query:

                String cphm = et_number.getText().toString();
                String hpzl_str = sb_vehicle.getSelectedItem();
                String hpzl_num = DataModel.VEHICLEMODLES.get(hpzl_str);
                String fdjh = et_engine_num.getText().toString();
                if (cphm.isEmpty()) {
                    Toast.makeText(context, "机动车号牌不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String clsbh = et_vehivle_id.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("cphm", cphm);
                bundle.putString("hpzl", hpzl_num);
                bundle.putString("fdjh", fdjh);
                bundle.putString("clsbh", clsbh);
                if (((Ac_main) context).isFromSsj) {
                    bundle.putBoolean(DataModel.FROM_SSJ_FLAG, true);
                    ((Ac_main) context).hopWithssj(Ac_clcx.class, bundle);
                } else {

                    ((Ac_main) context).hop2Activity(Ac_clcx.class, bundle);
                }
                break;
            case R.id.scan:
                Intent mIntent = new Intent();
                mIntent.setClassName("cn.com.cybertech.ocr", "cn.com.cybertech.RecognitionActivity");
                mIntent.putExtra("camera", true);    // true为视频识别，false为拍照识别。如果不加此参数默认为true，使用视频识别的方式。
                startActivityForResult(mIntent, 111);
                break;
        }
    }

//    号牌 和
//    身份证
//    //车牌

//                    Intent mIntent = new Intent();
//                    mIntent.setClassName("cn.com.cybertech.ocr", "cn.com.cybertech.RecognitionActivity");
//                    mIntent.putExtra("camera", false);    // true为视频识别，false为拍照识别。如果不加此参数默认为true，使用视频识别的方式。
//                    startActivityForResult(mIntent, 111);
    //证件识别
//        Intent mIntent = new Intent();
//        mIntent.putExtra("ZJSB", true);
//        mIntent.putExtra("nMainID", 2); // 识别类型，如不加此参数，默认2 识别二代身份证，  具体对应的类型在下面可以按需设置。
//        mIntent.setClassName("cn.com.cybertech.ocr", "cn.com.cybertech.RecognitionActivity");
//        startActivityForResult(mIntent, 222);

//}

    //
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (111 == requestCode) {
            if (resultCode == 2) {
                ArrayList<String> hphms = data.getStringArrayListExtra("HPHMS");
                if (hphms == null) {
                    Toast.makeText(context, "识别异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (hphms.size() > 0)
                    Toast.makeText(context, hphms.get(0), Toast.LENGTH_SHORT).show();

                if (hphms.size() > 1) {
                    String path = hphms.get(0); // 返回的图片地址，有可能没有
                    et_number.setText(path);
                }
            }
        }
    }


}
