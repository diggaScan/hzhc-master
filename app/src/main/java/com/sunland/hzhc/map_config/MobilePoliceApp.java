package com.sunland.hzhc.map_config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

public class MobilePoliceApp extends Application {
	public static boolean isCancelable = true;
	public static Map<String, Integer> map;
	public static final int RSP_LOGIN = 1;
	public static final int RSP_COMMON = 0;
	public static String ERRORCODE = null;
	public static int isCommunicatExist = 0;
	public static int currTabPos = 0;
	public static boolean isExist = false;
	public static boolean isLogin = false;
	public static boolean isTelecalling = false;
	public Cursor cursor;
	public static String mImagePath = null;
	private String before;
	private String xz;
	private String screenIndex;
	private int showid;
	private HashMap<String, String> key = new HashMap<String, String>();
	private String sendbuf;
	private String mRecvData = "";
	public static HashMap<String, Drawable>

	sysApp_map = null;
	public static String deviceMD5Id;
	public static String deviceType;
	public static boolean deviceState = false;
	public static boolean IS568PLUS = false;
	public static String READIDSERVICE = "com.softsz.readidcard.service";
//	public TokenBean token;
	public Object params;
	public Object TrafficPoliceListValue;
	public static String dmbValue = "";
	private static Context ctxt;
	public static int textSize = 9;
	public static int isLocal = 0;
	public static boolean isTxry = false;


//	private CRJ_Detailt_Info crj_info;
//
//	public CRJ_Detailt_Info getCrj_info() {
//		return crj_info;
//	}
//
//	public void setCrj_info(CRJ_Detailt_Info crj_info) {
//		this.crj_info = crj_info;
//	}
//
//	public static String rxhlItemID = "";
//
//	private HashMap<String, PersonData> allDate;
//
//	private HashMap<String, PersonData> txryDate;
//
//	public HashMap<String, PersonData> getTxryDate() {
//		return txryDate;
//	}
//
//	public void setTxryDate(HashMap<String, PersonData> txryDate) {
//		this.txryDate = txryDate;
//	}
//
//	public HashMap<String, PersonData> getAllDate() {
//		return allDate;
//	}
//
//	public void setAllDate(HashMap<String, PersonData> allDate) {
//		this.allDate = allDate;
//	}

	/**
	 * 案件详情传值
	 */
//	public Anjinfo caseinfo;
//
//	public Anjinfo getCaseinfo() {
//		return caseinfo;
//	}
//
//	public void setCaseinfo(Anjinfo caseinfo) {
//		this.caseinfo = caseinfo;
//	}
//
//	public static Context getContext() {
//		return ctxt;
//	}
//
//	private ArrayList<RCHL> rchlJsonBeanList;
//
//	public ArrayList<RCHL> getRchlJsonBeanList() {
//		return rchlJsonBeanList;
//	}
//
//	public void setRchlJsonBeanList(ArrayList<RCHL> rchlJsonBeanList) {
//		this.rchlJsonBeanList = rchlJsonBeanList;
//	}

	/**
	 * 是否随手记模式 false正常模式 true随手记模式
	 */
	public static boolean IsNote = false;
	/**
	 * 随手记 数据保存对象
//	 */
//	public RecordNoteTakingJson AllNote;

	/**
	 * 路面盘查开关
	 */
	public static boolean lmpc = true;

	/**
	 * 安保核录开关
	 */
	public static boolean abhl = false;

	/**
	 * 护城河核录开关
	 */
	public static boolean hch = false;

	/**
	 * 随手记 被记录列表 图片对象保存
	 */
//	public ArrayList<Pictures> allPicturs;
//
//	/**
//	 * 随手记 被记录列表 录音对象保存
//	 */
//	public ArrayList<Sounds> allSounds;
//
//	/**
//	 * 随手记 数据保存对象类型
//	 */
//	public NOTE_PHOTO_STYE notePhotoStye;
//
//	/**
//	 * 随手记 数据的index
//	 */
//	public int notePhotoIndex;
//
//	public ArrayList<Pictures> getAllPicturs() {
//		return allPicturs;
//	}
//
//	public void setAllPicturs(ArrayList<Pictures> allPicturs) {
//		this.allPicturs = allPicturs;
//	}
//
//	public int getNotePhotoIndex() {
//		return notePhotoIndex;
//	}
//
//	public void setNotePhotoIndex(int notePhotoIndex) {
//		this.notePhotoIndex = notePhotoIndex;
//	}
//
//	public NOTE_PHOTO_STYE getNotePhotoStye() {
//		return notePhotoStye;
//	}
//
//	public void setNotePhotoStye(NOTE_PHOTO_STYE notePhotoStye) {
//		this.notePhotoStye = notePhotoStye;
//	}

	/**
	 * 安保核录 路面核录
	 */
	public static String stuts = "02";

	public static String stuts_str = "省情报中心\n路面核查";

	// /**
	// * 安保核录 路面核录 档案
	// */
	// public static String stuts_archives = "";

	/**
	 * GPS定位地址 保留用户输入的地址
	 */
	public static String address = "";
	public static int gps_stuts = 0;

	/**
	 * GPS定位地址 保留用户输入的地址
	 */
	public static String gps_jd = "";

	/**
	 * 滑动经度
	 */
	public static String move_jd = "";

	/**
	 * 滑动纬度
	 */
	public static String move_wd = "";

	/**
	 * GPS定位地址 保留用户输入的地址
	 */
	public static String gps_wd = "";

	/**
	 * GPS定位地址 收索到的卫星数量
	 */
	public static String gpsCount = "";

//	private Content content;

	/**
	 * 关注信息列表 获取到的关注信息
	 */

//	public ArrayList<GzxxInfo> gzxxlist;

	/**
	 * 档案 关注信息列表 省厅人车核录信息
	 */

	public ArrayList<HashMap<String, String>> rchl_list;

//	public ArrayList<Sounds> getAllSounds() {
//		return allSounds;
//	}

//	public void setAllSounds(ArrayList<Sounds> allSounds) {
//		this.allSounds = allSounds;
//	}/
//
	public static int noteSinglePhotePosition = 0;

	/**
	 * 记录
	 */
//	private Record informationRecord;
//
	/**
	 * GPS 经纬度
	 */
	public static Intent GpsIntent;
//
//	public ArrayList<Jcz> jczList;
//
//	public ArrayList<Jcz> getJczList() {
//		return jczList;
//	}
//
//	public void setJczList(ArrayList<Jcz> jczList) {
//		this.jczList = jczList;
//	}
//
//	private ArrayList<Record> informationFnodeList;

	public ArrayList<HashMap<String, String>> getRchl_list() {
		return rchl_list;
	}

	public void setRchl_list(ArrayList<HashMap<String, String>> rchl_list) {
		this.rchl_list = rchl_list;
	}

//	public ArrayList<GzxxInfo> getGzxxlist() {
//		return gzxxlist;
//	}

//	public void setGzxxlist(ArrayList<GzxxInfo> gzxxlist) {
//		this.gzxxlist = gzxxlist;
//	}

//	public Content getContent() {
//		return content;
//	}
//
//	public static Content USER;
//
//	public void setContent(Content content) {
//		this.content = content;
//		if (content.getDeptID().contains("330106")) {
//			device();
//		}
//
//	}

	public Object infoValue;

	public Object getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(Object infoValue) {
		this.infoValue = infoValue;
	}

	public Object getTrafficPoliceListValue() {
		return TrafficPoliceListValue;
	}

	public void setTrafficPoliceListValue(Object trafficPoliceListValue) {
		TrafficPoliceListValue = trafficPoliceListValue;
	}

	/**
	 * 匡信接口，交警数据，机动车
	 */
	private Object resp;
	/**
	 * 自适应界面 详细信息展示页
	 */

	private HashMap<String, String> detaileMap;

	/**
	 * 机动车详细对象
	 */
//	private CarInfoBean carInfo;
//
//	/**
//	 * 详细对象
//	 */
//	private EcarInfoBean eCarInfo;
//
//	public EcarInfoBean geteCarInfo() {
//		return eCarInfo;
//	}
//
//	public void seteCarInfo(EcarInfoBean eCarInfo) {
//		this.eCarInfo = eCarInfo;
//	}
//
//	public CarInfoBean getCarInfo() {
//		return carInfo;
//	}
//
//	public void setCarInfo(CarInfoBean carInfo) {
//		this.carInfo = carInfo;
//	}

	public HashMap<String, String> getDetaileMap() {
		return detaileMap;
	}

	public void setDetaileMap(HashMap<String, String> detaileMap) {
		this.detaileMap = detaileMap;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	/**
	 * private Record informationRecord; private ArrayList <Record>
	 * informationFnodeList; browseImage 选择的�?�?
	 */

	private int chiceCount;
	/**
	 * browseImahge 每个文件夹�?择的图片
	 */
	private boolean[] isChice;

	/**
	 * browseImage 选中的图�?
	 */
	private Hashtable<Integer, Bitmap> mPhotoFiles;

	/**
	 * broseImage当前文件夹的图片
	 */
	private ArrayList<Bitmap> bitmapList;
	/**
	 * broseImage已经选择的图
	 */
	private ArrayList<Bitmap> SelectbitmapList;
	private String weibostate;

	public String getWeibostate() {
		return weibostate;
	}

	public void setWeibostate(String weibostate) {
		this.weibostate = weibostate;
	}

	public Object getResp() {
		return resp;
	}

	public void setResp(Object resp) {
		this.resp = resp;
	}

	/**
	 * 是否电动车采�?true false
	 */
	/**
	 * 线索 传�?
	 */
	private String isEcarGather;

	private HashMap<String, Object> saveAllValue;

	private String sendBuff;

	public String getSendBuff() {
		return sendBuff;
	}

	public void setSendBuff(String sendBuff) {
		this.sendBuff = sendBuff;
	}
//
//	public TokenBean getToken() {
//		return token;
//	}
//
//	public void setToken(TokenBean token) {
//		this.token = token;
//	}

	public HashMap<String, Object> getSaveAllValue() {
		return saveAllValue;
	}

	public void setSaveAllValue(HashMap<String, Object> saveAllValue) {
		this.saveAllValue = saveAllValue;
	}

	public String getIsEcarGather() {
		return isEcarGather;
	}

	public void setIsEcarGather(String isEcarGather) {
		this.isEcarGather = isEcarGather;
	}

	public ArrayList<Bitmap> getSelectbitmapList() {
		return SelectbitmapList;
	}

	public void setSelectbitmapList(ArrayList<Bitmap> selectbitmapList) {
		SelectbitmapList = selectbitmapList;
	}

	public ArrayList<Bitmap> getBitmapList() {
		return bitmapList;
	}

	public void setBitmapList(ArrayList<Bitmap> bitmapList) {
		this.bitmapList = bitmapList;
	}

	public Hashtable<Integer, Bitmap> getmPhotoFiles() {
		return mPhotoFiles;
	}

	public void setmPhotoFiles(Hashtable<Integer, Bitmap> mPhotoFiles) {
		this.mPhotoFiles = mPhotoFiles;
	}

	public boolean[] getIsChice() {
		return isChice;
	}

	public void setIsChice(boolean[] isChice) {
		this.isChice = isChice;
	}

	public int getChiceCount() {
		return chiceCount;
	}

	public void setChiceCount(int chiceCount) {
		this.chiceCount = chiceCount;
	}
//
//	public Record getInformationRecord() {
//		return informationRecord;
//	}
//
//	public void setInformationRecord(Record informationRecord) {
//		this.informationRecord = informationRecord;
//	}
//
//	public ArrayList<Record> getInformationFnodeList() {
//		return informationFnodeList;
//	}
//
//	public void setInformationFnodeList(ArrayList<Record> informationFnodeList) {
//		this.informationFnodeList = informationFnodeList;
//	}

	public String getSendbuf() {
		String tmp = sendbuf;
		sendbuf = null;
		return tmp;
	}

	public void setSendbuf(String sendbuf) {
		this.sendbuf = sendbuf;
	}

	public int getShowid() {
		return showid;
	}

	public void setShowid(int showid) {
		this.showid = showid;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public String getScreenIndex() {
		return screenIndex;
	}

	public void setScreenIndex(String screenIndex) {
		this.screenIndex = screenIndex;
	}

	public String getXz() {
		return xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	public HashMap<String, String> getKey() {
		return key;
	}

	public void setKey(HashMap<String, String> key) {
		this.key = key;
	}
//
//	GpsBrocastReciver myRecive;
//	private SQLiteUtil sql;

	@Override
	public void onCreate() {
		super.onCreate();
		ctxt = this;
		sysApp_map = new HashMap<String, Drawable>();

		// CrashManager.registerHandler();
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// crashHandler.init(getApplicationContext());
//		Intent bindIntent = new Intent(ctxt, GPSService.class);
//		bindService(bindIntent, connection, BIND_AUTO_CREATE);
//		device();

	}

	TelephonyManager tm;

	

	class deviceResult {
		private String message;
		private String status;
		private boolean success;

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}

//	private GPSService.ServiceBinder mBinderService;
//	private ServiceConnection connection = new ServiceConnection() {
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//		}
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
////			mBinderService = (GPSService.ServiceBinder) service;
//			try {
////				mBinderService.startDownload();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	};

//	@Override
//	public void onTerminate() {
//		super.onTerminate();
//		if (null != connection) {
//			unbindService(connection);
//		}
//	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public void clearWallpaper() throws IOException {
		super.clearWallpaper();
	}

	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

	public WindowManager.LayoutParams getMywmParams() {
		return wmParams;
	}

	class BinderRun implements Runnable {

		@Override
		public void run() {

		}

	}

	public void setRecvData(String text) {
		mRecvData = text;
	}

	public String getRecvData() {
		String tmp = mRecvData;
		mRecvData = null;
		return tmp;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

//	public RecordNoteTakingJson getAllNote() {
//		return AllNote;
//	}

//	public void setAllNote(RecordNoteTakingJson allNote) {
//		AllNote = allNote;
//	}


}
