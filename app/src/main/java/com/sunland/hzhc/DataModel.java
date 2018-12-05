package com.sunland.hzhc;

public class DataModel {
    // TODO: 2018/12/4/004  那些数据访datamodel,那些数据放字典要整理
    public static final String[] MODULE_NAMES = {"身份证", "机动车", "电动车", "姓名组合", "旅馆", "网吧", "电话"
            , "案件", "境外人员"};

    public static final String[] VEHICLE_CATEGORIES = {"无数据"};

    public static final String[] NUMBER_PREFIX = {"浙", "津", "沪", "渝", "冀", "晋", "辽",
            "吉", "黑", "苏", "京", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "川",
            "贵", "云", "陕", "甘", "青", "藏", "桂", "蒙", "宁", "琼"};
    public static final String[] ALPHABETS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"
            , "U", "V", "W", "X", "Y", "Z"};

    public static final String[] GENDERS = {"男", "女"};

    public static final String[] NATIONS = {"无数据"};

    public static final String[] PAPER_CATES = {"无数据"};


    public static final String ACTION_NFC_READ_IDCARD_SUCCESS =
            "cybertech.pstore.intent.action.NFC_READ_IDCARD_SUCCESS";

    public static final String ACTION_NFC_READ_IDCARD_FAILURE =
            "cybertech.pstore.intent.action.NFC_READ_IDCARD_FAILURE";


    public final static String RECORD_BUNDLE_TYPE = "RECORD_BUNDLE_TYPE"; //0人员  1 车辆 2非机动车
    public final static String RECORD_BUNDLE_DATA = "RECORD_BUNDLE_DATA";
    public final static String RECORD_BUNDLE_ADDR = "RECORD_BUNDLE_ADDR";//核查地点

    public final static String FROM_SSJ_FLAG = "fromRandomRecord";

}
