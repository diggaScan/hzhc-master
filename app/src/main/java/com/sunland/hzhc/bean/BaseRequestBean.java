package com.sunland.hzhc.bean;

/**
 * Created by PeitaoYe
 * On 2018/8/27
 **/
public class BaseRequestBean {
    public String yhdm;
    public String imei;
    public String imsi;
    public String pdaTime;
    public String Lbr;//版本号
    public String gpsX;//经度
    public String gpsY;//纬度

    public String getYhdm() {
        return yhdm;
    }

    public void setYhdm(String yhdm) {
        this.yhdm = yhdm;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPdaTime() {
        return pdaTime;
    }

    public void setPdaTime(String pdaTime) {
        this.pdaTime = pdaTime;
    }

    public String getLbr() {
        return Lbr;
    }

    public void setLbr(String lbr) {
        Lbr = lbr;
    }

    public String getGpsX() {
        return gpsX;
    }

    public void setGpsX(String gpsX) {
        this.gpsX = gpsX;
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }
}
