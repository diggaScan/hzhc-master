package com.sunlandgroup;

import android.os.Build;
import android.os.Environment;

/**
 * Created by LSJ on 2017/10/17.
 */

public class Global {
    public final static String appName = "ydjw";
    public final static String sdPath = Environment.getExternalStorageDirectory().getPath();
    public final static String ip = "20.65.2.12";
    public static String port = "3565";//测试端口6022
    public final static String postfix = "/hzydjw/services/IQueryOfJwtService";
    //    public final static String portName="newsService";
//20.65.2.12:3565/hzydjw/services/IQueryOfJwtService?wsdl

    public final static String PERSON_CHECK_QGZT_URL = "http://20.65.2.12:3501/node/ztry/check/";
    public final static String VERSION_URL = "1.0";
    //本机信息
    public static String imei = "";
    public static String imsi1 = " ";
    public static String imsi2 = "";
    public static String gpsX = "";//经度
    public static String gpsY = "";//纬度
    public static String gpsinfo = gpsX + gpsY;

    public static String brand = Build.BRAND;
    public static String model = Build.MODEL + " " + Build.VERSION.SDK_INT;

}
