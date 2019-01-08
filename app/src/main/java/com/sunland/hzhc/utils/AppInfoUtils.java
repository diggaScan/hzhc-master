package com.sunland.hzhc.utils;

import android.content.Context;

public class AppInfoUtils {

    public static int getAppVersion(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verCode;
    }

    public static String getAppName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verName;
    }
}
