package com.sunland.hzhc.map_config;

public class Config {

    /**
     * 市局链路(测试用)
     */
//    public static final String IP = "http://10.10.100.18";
//    public static final String PortAr = "9000";
//    public static final String PortQqb = "11101";
//    public static final String PortQqb2 = "20006";
//    public static final String PortQqb3 = "8079";
//    public static final String PortKx = "60606";
//    public static final String PortQqb4 = "17080";
    /**
     * 省厅的链路
     */
    public static final String IP="http://20.65.2.12";
    public static final String PortQqb2="3508";
    public static final String PortQqb3="8079";
    public static final String PortAr="3501";
    public static final String PortQqb="3506";
    public static final String PortKx="3505";
    public static final String PortQqb4="3507";

    public static final String GPS_JWD = IP + ":" + PortQqb3 + "/addressservice/addrToCoords/address=";
    public static final String EWM_ADDRESS = IP + ":" + PortQqb + "/basepolice/record/getAddressByBarcodeNoSession.do?";
    public static final String MAP = IP + ":" + PortQqb2 + "/arcgis/rest/services/hangzhouquanshisl/MapServer";
    public static final String ADDRESS = IP + ":" + PortQqb + "/basepolice/record/getAddressByZuobiaoNoSession.do?";



}
