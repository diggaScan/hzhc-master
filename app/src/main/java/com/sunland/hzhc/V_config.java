package com.sunland.hzhc;

import android.os.Build;

public class V_config {


    //    1.查询案件管辖单位
    public static final String QUERY_ALL_CASE_INFO = "queryAllCaseInfo";
    //    2.查询案件所有类别信息
    public static final String QUERY_ALL_CASE_CATEGORY = "queryAllCaseCategory";
    //    3.案件信息查询
    public static final String CASE_INFO = "caseInfo";
    //    4.查询车辆关注信息
    public static final String CAR_FOCUS = "carFocus";
    //    5.查询车辆组合信息
    public static final String CAR_INFO_JOIN = "carInfoJoin";
    //    6.车主所属所有车查询
    public static final String GET_CAR_INFO_BY_SFZH = "getCarInfoBySfzh";
    //    7.查询人员档案信息
    public static final String GET_PERSON_INFO_BY_SFZH = "getPersonInfoBysfzh";
    //    8.人员电话聚合信息查询
    public static final String GET_PERSON_MOBILE_JOIN_INFO = "getPersonMobileJoinInfo";
    //    9.人员关注查询
    public static final String PERSON_FOCUS_INFO = "personFocusInfo";
    //    10.人员轨迹查询
    public static final String PERSON_LOCUS_INFOS = "personLocusInfos";
    //    11.人员综合信息查询
    public static final String PERSON_COMPLEX = "personComplex";
    //    12.人员组合信息查询
    public static final String GET_PERSON_JOIN_INFO = "getPersonJoinInfo";
    //    13.电动车关注信息查询
    public static final String GET_ELECTRIC_CAR_FOCUS_INFO = "getElectricCarFocusInfo";
    //    14.批量查询电动车信息
    public static final String GET_ELECTRIC_CAR_INFO = "getElectricCarInfo";
    //    15.批量查询网吧基本信息
    public static final String GET_INTERNET_CAFE_INFO = "getInternetCafeInfo";
    //    16. 获取旅馆行政区划代码及名称所有信息
    public static final String GET_ALL_HOTELS = "getAllAdministrativeHostel";
    //     17.旅馆信息查询
    public static final String GET_HOTEL_INFO = "getHostelInfo";
    //     18.批量查询旅馆入住人员信息
    public static final String GET_PERSON_IN_HOTEL_INFO = "getPersonInHostelInfo";
    //     19.上网人员查询
    public static final String GET_INTERNET_CAFE_PERSON_INFO = "getSuffInternetPersonInfo";
    //     20.网吧信息批量查询
    public static final String INTERNET_CAFE_INFO = "internetCafeInfo";
    //    21.全国库人员信息查询未翻译接口,  请求参数：String sfzh;
    public static final String COUNTRY_PERSON = "getPersonOfCountry";
    //    22.核查人接口
    public static final String INSPECT_PERSON = "queryInspectPerson";
    //   23. 核查车接口
    public static final String INSPECT_CAR = "queryInspectCar";
    //   24.登录接口
    public final static String USER_LOGIN = "userLogin";
    //   25.免密登录接口
    public final static String MM_USER_LOGIN = "userMMLogin";
    //   26. 地铁接口详情
    public final static String SUBWAY_INFO = "querySubwayInfo";
    //   27. 安保接口
    public final static String GET_AN_BAO_INFO = "getAnBaoInfo";

    //本机信息
    public final static String BRAND = Build.BRAND;//手机品牌
    public final static String MODEL = Build.MODEL + " " + Build.VERSION.SDK_INT;//手机型号
    public final static String OS = "android" + Build.VERSION.SDK_INT;//手机操作系统
    public static String imei = "";
    public static String imsi1 = " ";
    public static String imsi2 = "";
    public static String gpsX = "";//经度
    public static String gpsY = "";//纬度
    public static String gpsinfo = gpsX + gpsY;
    //用户代码
    public static String YHDM = "115576";

    public static String JYSFZH = "";
    public static String JYXM = "";
    public static String JYBMBH = "";
    //
    public static String DLMK = "杭州核查";

    public static String hc_address = "";
    public final static int REQ_LOCATION = 1;
}
