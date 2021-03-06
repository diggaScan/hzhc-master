package com.sunland.hzhc;

import java.util.LinkedHashMap;

public class DataModel {

    // TODO: 2018/12/4/004  那些数据访datamodel,那些数据放字典要整理
    public static final String[] MAIN_MODULE_NAMES = {"身份证", "机动车", "电动车", "姓名组合", "旅馆", "网吧", "电话"
            , "案件"};// "境外人员"

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
    public final static String[] VEHICLEMODELS = {
            "小型汽车", "大型汽车", "小型新能源汽车", "大型新能源汽车", "使馆汽车", "领馆汽车", "境外汽车",
            "外籍汽车", "两、三轮摩托车", "轻便摩托车", "使馆摩托车", "领馆摩托车",
            "境外摩托车", "外籍摩托车", "农用运输车类", "拖拉机", "挂车",
            "教练汽车", "教练摩托车", "试验汽车", "试验摩托车", "临时入境汽车",
            "临时入境摩托车", "临时行驶车", "警用汽车号牌", "警用摩托车号牌", "军用车辆号牌",
            "武警车辆号牌", "其它号牌",
    };

    public final static String[] NUMBERPREFIX = {"浙", "京", "津", "沪", "渝", "冀", "晋", "辽",
            "吉", "黑", "苏", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "川",
            "贵", "云", "陕", "甘", "青", "藏", "桂", "蒙", "宁", "琼"};

    public final static String[] NUMBERINITIAL = {"A", "B", "C", "D", "E", "F", "G", "H", "I"
            , "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public final static String[] GENDER = {"男", "女"};
    public static LinkedHashMap<String, String> VEHICLEMODLES;

    static {
        VEHICLEMODLES = new LinkedHashMap<String, String>();

        VEHICLEMODLES.put("大型汽车", "01");
        VEHICLEMODLES.put("小型汽车", "02");
        VEHICLEMODLES.put("小型新能源汽车", "52");
        VEHICLEMODLES.put("大型新能源汽车", "51");
        VEHICLEMODLES.put("使馆汽车", "03");
        VEHICLEMODLES.put("领馆汽车", "04");
        VEHICLEMODLES.put("境外汽车", "05");
        VEHICLEMODLES.put("外籍汽车", "06");
        VEHICLEMODLES.put("普通摩托车", "07");
        VEHICLEMODLES.put("轻便摩托车", "08");
        VEHICLEMODLES.put("使馆摩托车", "09");
        VEHICLEMODLES.put("领馆摩托车", "10");
        VEHICLEMODLES.put("境外摩托车", "11");
        VEHICLEMODLES.put("外籍摩托车", "12");
        VEHICLEMODLES.put("低速车", "13");
        VEHICLEMODLES.put("拖拉机", "14");
        VEHICLEMODLES.put("挂车", "15");
        VEHICLEMODLES.put("教练汽车", "16");
        VEHICLEMODLES.put("教练摩托车", "17");
        VEHICLEMODLES.put("试验汽车", "18");
        VEHICLEMODLES.put("试验摩托车", "19");
        VEHICLEMODLES.put("临时入境汽车", "20");
        VEHICLEMODLES.put("临时入境摩托车", "21");
        VEHICLEMODLES.put("临时行驶车", "22");
        VEHICLEMODLES.put("警用汽车", "23");
        VEHICLEMODLES.put("警用摩托车", "24");
        VEHICLEMODLES.put("原农机号牌", "25");
        VEHICLEMODLES.put("香港入出境车", "26");
        VEHICLEMODLES.put("澳门入出境车", "27");
        VEHICLEMODLES.put("武警号牌", "31");
        VEHICLEMODLES.put("军用号牌", "32");
        VEHICLEMODLES.put("武警车辆", "26");
        VEHICLEMODLES.put("其它号牌", "99");
    }

    public static final String[] PAPER_CATEGORIES = {"05-往来台湾通行证（一次有效",
            " 06-台湾居民来往大陆通行证（一次有效）", "0A\t非税票据",
            "0B\t回乡证白卡", "10\t身份证", "11\t外交护照", "12\t公务护照", "13\t因公普通护照",
            "14\t普通护照", "15\t中华人民共和国旅行证", "16\t台湾居民来往大陆通行证（五年有效）", "17\t海员证", "20\t中华人民共和国出入境通行证"
            , "21\t往来港澳通行证", "23\t前往港澳通行证", "24\t港澳居民来往内地通行证", "25\t往来台湾通行证", "27\t港澳居民定居证",
            "28\t华侨回国定居证", "29\t台湾居民定居证", "30\t外国人出入境证", "31\t外国人旅行证", "32\t外国人居留证、居留许可",
            "33\t外国人临时居留证", "34\t外国人永久居留证", "35\t入籍证书", "36\t出籍证书", "37\t复籍证书", "3E\t特区旅游签证",
            "3P\t普通签证", "3T\t团体签证", "49\t台湾居民登陆证", "51\t签证身份书", "60\t边境通行证", "66\t回美证",
            "70\t香港特别行政区护照", "71\t澳门特别行政区护照", "74\t港澳证贴纸签注", "75\t大陆证贴纸签注", "76\t台湾居民居留贴纸签注",
            "77\t台湾居民来往贴纸签注", "78\t贴纸加注", "98\t转印膜", "99\t其它证件", "9A\t电子护照塑封膜", "9B\t退籍证书",
            "9C\t复籍证书", "9D\t电子港澳证塑封膜", "9E\t2014版塑封膜"};

    public static final Integer[] TAB_ICONS_UNCLICKED = {R.drawable.ic_identity_unclicked, R.drawable.ic_vehicle_unclicked, R.drawable.ic_e_vehicle_unclicked,
            R.drawable.ic_name_unclicked, R.drawable.ic_batch_unclicked, R.drawable.ic_hotel_unclicked, R.drawable.ic_internet_unclicked, R.drawable.ic_phone_unclicked,
            R.drawable.ic_case_unclicked, R.drawable.ic_abroad_unclicked};

    public static final Integer[] TAB_ICONS_CLICKED = {R.drawable.ic_identity_clicked, R.drawable.ic_vehicle_clicked, R.drawable.ic_e_vehicle_clicked,
            R.drawable.ic_name_clicked, R.drawable.ic_batch_clicked, R.drawable.ic_hotel_clicked, R.drawable.ic_internet_clicked, R.drawable.ic_phone_clicked,
            R.drawable.ic_case_clicked, R.drawable.ic_abroad_clicked};

    public static final String[] MODULE_NAMES = {"身份证", "机动车", "电动车", "姓名组合", "旅馆", "网吧", "电话", "案件", "批量核查"};
    //"境外人员", , "人脸识别", "设置"
    public static final Integer[] MODULES_ICON = {R.drawable.ic_identity_unclicked, R.drawable.ic_vehicle_unclicked, R.drawable.ic_e_vehicle_unclicked,
            R.drawable.ic_name_unclicked, R.drawable.ic_hotel_unclicked, R.drawable.ic_internet_unclicked, R.drawable.ic_phone_unclicked,
            R.drawable.ic_case_unclicked, R.drawable.ic_batch_unclicked};
//    R.drawable.ic_abroad_unclicked,  R.drawable.ic_face_rec_unclicked, R.drawable.ic_setting_unclicked

    public static final String[] HANGZHOU_DISTRICTS = {"杭州市", "上城区", "下城区", "江干区", "拱墅区", "西湖区", "滨江区", "萧山区", "余杭区", "桐庐县", "淳安县", "建德市",
            "富阳区", "临安市", "经济技术开发区"};

    public static final String[] HANGZHOU_DISTRICT_CODE = {"330100", "330102", "330103", "330104", "330105", "330106", "330108"
            , "330109", "330110", "330122", "330127", "330182", "330183", "330185", "330198"};

}
