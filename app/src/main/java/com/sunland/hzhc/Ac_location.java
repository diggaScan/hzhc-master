package com.sunland.hzhc;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.concretejungle.spinbutton.SpinButton;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnPanListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Point;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunland.hzhc.bean.BaseRequestBean;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfo;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfoRequestBean;
import com.sunland.hzhc.bean.i_subway_info.SubwayInfoResBean;
import com.sunland.hzhc.map_config.AddressJsonBean;
import com.sunland.hzhc.map_config.Config;
import com.sunland.hzhc.map_config.GenericTask;
import com.sunland.hzhc.map_config.GpsJwdJsonBean;
import com.sunland.hzhc.map_config.MobilePoliceApp;
import com.sunland.hzhc.map_config.QueryCommon;
import com.sunland.hzhc.map_config.TaskAdapter;
import com.sunland.hzhc.map_config.TaskParams;
import com.sunland.hzhc.map_config.TaskResult;
import com.sunland.hzhc.modules.Ac_base_info;
import com.sunland.hzhc.recycler_config.Location_rv_adapter;
import com.sunland.hzhc.recycler_config.OnRvItemClickedListener;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.tgram.gis.MapManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Ac_location extends Ac_base_info {

    public final String TAG = this.getClass().getCanonicalName();
    @BindView(R.id.mapView)
    public MapView mMapView;
    @BindView(R.id.pin)
    public ImageView cen;
    @BindView(R.id.address_list)
    public RecyclerView rv_addr_list;
    @BindView(R.id.enter)
    public TextView tv_enter;
    @BindView(R.id.address)
    public EditText et_address;
    @BindView(R.id.loading_layout)
    public FrameLayout loading_layout;

    public final int QR_REQUEST_CODE = 0;
    public final int REQUEST_METRO_ADDRESS = 1;

    private ArrayList<HashMap<String, String>> allList = null;
    private Location_rv_adapter adapter;
    private MapManager mMapManager;
    private Point center;
    private GatherTask task;
    private boolean isInit;
    private String mapUrl = Config.MAP;
    private Point add_center;
    private String move_jd = "";
    private String move_wd = "";

    private String addressPath;
    private String j;
    private String w;

    //经纬度
    private String jd = "";
    private String wd = "";

    //返回数据
    private String result_j;
    private String result_s;

    private int req_location = -1;
    private String metro_address_code;

    private LocationManager locationManager;
    private LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_location);
        handleIntent();
//        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        showToolbar(false);

        initView();
        initMap();
    }

    private void getGpsInfo() {
// TODO: 2019/1/4/004 要注册一个广播监听gps设置是否开启
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            //杭州经纬度范围东经118.35°-120.5°，北纬29.183°-30.55°(来自互联网)。
            String currentLongitude = String.valueOf(location.getLongitude());
            boolean isInHzLongitude = (currentLongitude.compareTo("118.35") > 0 && currentLongitude.compareTo("120.5") < 0);
            String currentLatitude = String.valueOf(location.getLatitude());
            boolean isInHzLatitude = (currentLatitude.compareTo("29.183") > 0 && currentLatitude.compareTo("30.55") < 0);
            if (isInHzLongitude && isInHzLatitude) {
                V_config.gpsX = String.format("%.5f", location.getLongitude());
                V_config.gpsY = String.format("%.5f", location.getLatitude());
            }


//            V_config.gpsX = "" + Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
//            V_config.gpsY = "" + Location.convert(location.getLatitude(), Location.FORMAT_DEGREES);
//            V_config.gpsX = Double.valueOf(location.getLongitude()).toString();
//            V_config.gpsY = Double.valueOf(location.getAltitude()).toString();
        }
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(Ac_location.this, "请在设置中开启GPS定位", Toast.LENGTH_LONG).show();
        } else {
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    V_config.gpsX = String.format("%.5f", location.getLongitude());
                    V_config.gpsY = String.format("%.5f", location.getLatitude());

//                    V_config.gpsX = "" + Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
//                    V_config.gpsY = "" + Location.convert(location.getLatitude(), Location.FORMAT_DEGREES);

//                    V_config.gpsX = Double.valueOf(location.getLongitude()).toString();
//                    V_config.gpsY = Double.valueOf(location.getLongitude()).toString();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        }
    }

    @OnClick({R.id.qrscan, R.id.metro, R.id.enter})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.qrscan:
                Intent intent = new Intent();
                intent.setAction("com.sunland.QR_SCAN");
                startActivityForResult(intent, QR_REQUEST_CODE);
                break;
            case R.id.metro:
                hop2ActivityForResult(Ac_metro_address.class, REQUEST_METRO_ADDRESS);
                break;
            case R.id.enter:
                String loc = et_address.getText().toString();
                int chinese_num = 0;
                for (char a : loc.toCharArray()) {
                    if (isChinese(a)) {
                        chinese_num++;
                    }
                }
                if (chinese_num < 5) {
                    Toast.makeText(Ac_location.this, "核查地址至少要有5个汉字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (req_location == V_config.REQ_LOCATION) {
                    V_config.hc_address = et_address.getText().toString();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    V_config.hc_address = et_address.getText().toString();
                    Intent intent2 = new Intent(Ac_location.this, Ac_main.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGpsInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (requestCode) {
            case QR_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    metro_address_code = data.getStringExtra("result");
                    loading_layout.setVisibility(View.VISIBLE);
                    queryYdjwDataNoDialog("SUBWAY_INFO", V_config.SUBWAY_INFO);
                    queryYdjwDataX();
                } else {
                    Toast.makeText(Ac_location.this, "二维码解析异常", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_METRO_ADDRESS:
                if (resultCode == RESULT_OK) {
                    String address = data.getStringExtra("metro_address");
                    et_address.setText(address
                    );
                } else {
                    Toast.makeText(Ac_location.this, "地址传递错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                req_location = bundle.getInt("req_location");
            }
        }
    }

    @Override
    public BaseRequestBean assembleRequestObj(String reqName) {
        switch (reqName) {
            case V_config.SUBWAY_INFO:
                SubwayInfoRequestBean subwayInfoRequestBean = new SubwayInfoRequestBean();
                assembleBasicRequest(subwayInfoRequestBean);
                subwayInfoRequestBean.setNumber(metro_address_code);
                return subwayInfoRequestBean;
        }
        return null;
    }

    @Override
    public void onDataResponse(String reqId, String reqName, ResultBase resultBase) {
        switch (reqName) {
            case V_config.SUBWAY_INFO:
                loading_layout.setVisibility(View.GONE);
                SubwayInfoResBean subwayInfoResBean = (SubwayInfoResBean) resultBase;
                if (subwayInfoResBean == null) {
                    Toast.makeText(this, "地铁地址接口异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<SubwayInfo> list = subwayInfoResBean.getRows();
                if (list == null || list.isEmpty()) {
                    Toast.makeText(this, "地址信息为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (list.size() == 1) {
                    SubwayInfo si = list.get(0);
                    StringBuilder sb = new StringBuilder();
                    String address = sb.append(si.getLineName()).append(si.getStationName()).append(si.getPositionName()).toString();
                    et_address.setText(address);
                } else {
                    //当返回地址多余一项时才会显示spinButton
                    final List<String> addresses = new ArrayList<>();
                    for (SubwayInfo si : list) {
                        StringBuilder sb = new StringBuilder();
                        String address = sb.append(si.getLineName()).append(si.getStationName()).append(si.getPositionName()).toString();
                        addresses.add(address);
                    }
                    SpinButton spinButton = new SpinButton(this);
                    spinButton.setHeaderTitle("选择地址");
                    spinButton.setDataSet(addresses);
                    spinButton.setSelection(0);
                    spinButton.setOnItemSelectedListener(new SpinButton.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int position) {
                            et_address.setText(addresses.get(position));
                        }
                    });
                    spinButton.showSpin();
                }
                break;
        }
    }

    private void initView() {
        allList = new ArrayList<>();
        adapter = new Location_rv_adapter(this, allList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv_addr_list.setLayoutManager(llm);
        rv_addr_list.setAdapter(adapter);
        adapter.setOnRvItemClickedListener(new OnRvItemClickedListener() {
            @Override
            public void onClick(int i) {
                et_address.setText(allList.get(i).get("dz"));
            }
        });
    }

    private void initMap() {
        double res = 0.352130262388273E-4;
        mMapView.setResolution(res);
        mMapManager = new MapManager(this, mMapView);
        mMapManager.initBaseLayer(mapUrl).startMap();
        mMapManager.setOnMapStatusChangedListener(new MapManager.OnMapStatusChanged() {

            @Override
            public void onStatusChanged(Object arg0, OnStatusChangedListener.STATUS arg1) {
            }

            @Override
            public void onMapInitialized(MapView mapview) {
                Log.d(TAG, "onMapInitialized: " + "asd");
                isInit = true;
                if (!MobilePoliceApp.address.equals("")) {
                    addressPath = Config.GPS_JWD + MobilePoliceApp.address + "&qxdm=&user=";
                    GTask task1 = new GTask(addressPath);
                    task1.setTaskListener(new GAdapter());
                    task1.execute();
                } else {
//                    if (!MobilePoliceApp.gps_jd.equals("")) {
//                        jd = MobilePoliceApp.gps_jd;
//                        wd = MobilePoliceApp.gps_wd;
                    if (!V_config.gpsX.equals("")) {
                        jd = V_config.gpsX;
                        wd = V_config.gpsY;
                    } else {
                        jd = "120.19404248126632";
                        V_config.gpsX = String.format("%.5f", Double.valueOf(jd));
                        wd = "30.232071189613492";
                        V_config.gpsY = String.format("%.5f", Double.valueOf(wd));
                    }
                    addressPath = Config.ADDRESS + "lx=" + jd + "&ly=" + wd + "&pagesize=10";
                    Log.d(TAG, "onMapInitialized: " + addressPath);
                    Point point = new Point(Double.valueOf(jd), Double.valueOf(wd));
                    mapview.centerAt(point, true);
                    task = new GatherTask(addressPath);
                    task.setTaskListener(new GatherAdapter());
                    task.execute();
                }
            }
        });
        mMapManager.setZoomLevel(10);
        mMapView.setMinResolution(0.000004);
        mMapView.setMaxResolution(0.00017);
        mMapView.setOnPanListener(new OnPanListener() {

            @Override
            public void prePointerUp(float arg0, float arg1, float arg2, float arg3) {
                if (task != null) {
                    task.cancel(true);
                }
            }

            @Override
            public void prePointerMove(float arg0, float arg1, float arg2, float arg3) {
            }

            @Override
            public void postPointerUp(float arg0, float arg1, float arg2, float arg3) {
                TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                        -0.5f);
                // TranslateAnimation(fromXType, fromXValue, toXType, toXValue,
                // fromYType, fromYValue, toYType, toYValue);
                ta.setDuration(300);
                ta.setRepeatCount(1);
                ta.setRepeatMode(Animation.REVERSE);
                cen.startAnimation(ta);

                // 滑动手指离开时
                center = mMapView.getCenter();
                try {
                    move_jd = center.getX() + "";
                    move_wd = center.getY() + "";
//                    V_config.gpsX = move_jd;
//                    V_config.gpsY = move_wd;
                    V_config.gpsX = String.format("%.5f", move_jd);
                    V_config.gpsY = String.format("%.5f", move_wd);
                    MobilePoliceApp.move_jd = move_jd;
                    MobilePoliceApp.move_wd = move_wd;
                    // lx=120&ly=32&pagesize=10
                    addressPath = Config.ADDRESS + "lx=" + center.getX() + "&ly=" + center.getY() + "&pagesize=10";
                    task = new GatherTask(addressPath);
                    task.setTaskListener(new GatherAdapter());
                    task.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!isInit) {
                        Toast.makeText(getApplicationContext(), "地图未初始化完成请稍后重试", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void postPointerMove(float arg0, float arg1, float arg2, float arg3) {
            }
        });
        mMapView.setOnZoomListener(new OnZoomListener() {

            @Override
            public void preAction(float arg0, float arg1, double arg2) {
                // TODO Auto-generated method stub
                if (!move_jd.equals("")) {
                    add_center = new Point(Double.valueOf(move_jd), Double.valueOf(move_wd));
                    mMapView.centerAt(add_center, false);
                } else if (!jd.equals("")) {
                    add_center = new Point(Double.valueOf(jd), Double.valueOf(wd));
                    mMapView.centerAt(add_center, false);
                }

            }

            @Override
            public void postAction(float arg0, float arg1, double arg2) {
                // TODO Auto-generated method stub
                if (!move_jd.equals("")) {
                    add_center = new Point(Double.valueOf(move_jd), Double.valueOf(move_wd));
                    mMapView.centerAt(add_center, false);
                } else if (!jd.equals("")) {
                    add_center = new Point(Double.valueOf(jd), Double.valueOf(wd));
                    mMapView.centerAt(add_center, false);
                }
            }
        });
    }

    class GTask extends GenericTask {
        private String path;

        public GTask(String path) {
            this.path = path;
        }

        @Override
        protected TaskResult _doInBackground(TaskParams... params) {
            try {
                result_s = QueryCommon.sendGET(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null == result_s) {
                return TaskResult.FAILED;
            }
            if (!result_s.equals("")) {
                return TaskResult.OK;
            } else {
                return TaskResult.FAILED;
            }
        }

    }

    class GAdapter extends TaskAdapter {
        @Override
        public void onPostExecute(GenericTask task, TaskResult result) {
            super.onPostExecute(task, result);
            if (result == TaskResult.OK) {
                try {
                    Gson goGson = new Gson();
                    GpsJwdJsonBean Json = goGson.fromJson(result_s, GpsJwdJsonBean.class);
                    j = Json.getLx();
                    w = Json.getLy();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (MobilePoliceApp.gps_stuts == 1) {

                            jd = String.format("%.5f", Double.valueOf(MobilePoliceApp.gps_jd));
                            wd = String.format("%.5f", Double.valueOf(MobilePoliceApp.gps_wd));
//                            wx.setVisibility(View.VISIBLE);
//                            wx.setText("卫星已定位，点此返回当前位置" + "\n" + jd + "," + wd);
                            System.out.println("MobilePoliceApp.gps_stuts == 1  11");

                        }
                        if (j != null && !j.equals("")) {
                            Point p = new Point(Double.valueOf(j), Double.valueOf(w));
                            mMapView.centerAt(p, true);
                        } else {
                            /*
                             * jd = "120.17248"; wd = "30.247541";
                             */
                            Point p = new Point(120.19404248126632, 30.232071189613492);
                            mMapView.centerAt(p, true);
                        }

                    }
                });
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("dz", MobilePoliceApp.address);
                allList.add(map);
//                adapter.notifyDataSetChanged();
            } else if (result == TaskResult.FAILED) {
                Point p = new Point(120.19404248126632, 30.232071189613492);
                mMapView.centerAt(p, true);
            }
        }
    }

    class GatherTask extends GenericTask {
        private String path;

        public GatherTask(String path) {
            this.path = path;
        }

        @Override
        protected TaskResult _doInBackground(TaskParams... params) {
            try {
                result_j = QueryCommon.doGET(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!result_j.equals("")) {
                return TaskResult.OK;
            } else {
                return TaskResult.FAILED;
            }

        }
    }

    class GatherAdapter extends TaskAdapter {
        @Override
        public void onPostExecute(GenericTask task, TaskResult result) {
            super.onPostExecute(task, result);
            if (result == TaskResult.OK) {
                Gson goGson = new Gson();
                Type listType = new TypeToken<LinkedList<AddressJsonBean>>() {
                }.getType();
                LinkedList<AddressJsonBean> resources = goGson.fromJson(result_j, listType);
                if (allList != null) {
                    allList.clear();
                }
                for (Iterator iterator = resources.iterator(); iterator.hasNext(); ) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    AddressJsonBean resource = (AddressJsonBean) iterator.next();
                    map.put("dz", resource.getDz());
                    allList.add(map);
                }
                if (MobilePoliceApp.gps_stuts == 1) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (MobilePoliceApp.gps_jd.length() >= 9 && MobilePoliceApp.gps_wd.length() >= 8) {
                                jd = String.format("%.5f", Double.valueOf(MobilePoliceApp.gps_jd));
                                wd = String.format("%.5f", Double.valueOf(MobilePoliceApp.gps_wd));
                            } else {
                                jd = MobilePoliceApp.gps_jd;
                                wd = MobilePoliceApp.gps_wd;
                            }

//                            wx.setVisibility(View.VISIBLE);
//                            wx.setText("卫星已定位，点此返回当前位置" + "\n" + jd + "," + wd);
                            System.out.println("MobilePoliceApp.gps_stuts == 1");
                        }
                    });

                }
                adapter.notifyDataSetChanged();
            } else if (result == TaskResult.FAILED) {
                // 失败
                // showDetaile("查询失败", "接口返回失败");
                Toast.makeText(getApplicationContext(), "接口返回失败", Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null && mLocationListener != null) {
            locationManager.removeUpdates(mLocationListener);
        }
        mLocationListener = null;
        locationManager = null;

    }
}
