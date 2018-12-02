package com.sunland.hzhc;

import android.os.Build;

public class UserInfo {
    //本机信息
    public static String hc_address = "";
    public static String jhdm = "115576";
    public static String imei = "";
    public static String imsi1 = " ";
    public static String imsi2 = "";
    public static String gpsX = "";//经度
    public static String gpsY = "";//纬度
    public static String gpsinfo = gpsX + gpsY;
    public static String brand = Build.BRAND;
    public static String model = Build.MODEL + " " + Build.VERSION.SDK_INT;
}
