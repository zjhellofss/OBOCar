package com.wanli.fss.obocar;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.wanli.fss.obocar.Service.GetDestinationService;
import com.wanli.fss.obocar.Service.GetOrderService;
import com.wanli.fss.obocar.Service.GetStateService;
import com.wanli.fss.obocar.Service.TravelService;
import com.wanli.fss.obocar.Service.UpdateAddressService;
import com.wanli.fss.obocar.Session.SessionLoger;

import java.util.Timer;
import java.util.TimerTask;

public class DriverActivity extends AppCompatActivity {
    private Timer timer = new Timer();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "正在等单...",
                            Toast.LENGTH_SHORT).show();
                    String res = GetStateService.getStateService();
                    Log.e("Amap", "res: " + res);
                    if (res.startsWith("CATCHING")) {
                        Toast.makeText(getApplicationContext(), "已经为您接到新的订单前往接驾",
                                Toast.LENGTH_SHORT).show();
                        //记录乘客的Id
                        String pid = res.substring(9, res.length());
                        SessionLoger.setPeerId(pid);
                        Log.e("Amap", "用户的SessionId为 " + pid);
                        startOrder.setText("已接到乘客上车");
                        timer.cancel();
                        task.cancel();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }

    };

    //创建一个地图容器MapView对象
    private MapView _mapView = null;
    //地图的UISetting对象 给amap设置地图内嵌控件
    private UiSettings _uiSettings = null;
    //地图对象
    private AMap _amap = null;

    //定位服务器客户端句柄
    private AMapLocationClient _amapLocationClient = null;
    //定位服务器客户端句柄属性
    private AMapLocationClientOption _amapLocationOption = null;

    //显示自我位置的图标
    private Marker _selfMarker = null;


    boolean isAddSelfMarker = false;
    int flag = 0;
    int traffic_flag = 0;
    private Button startOrder = null;

    protected void initUI() {
        //将地图容器跟MapView控件相关联
        _mapView = (MapView) findViewById(R.id.DriverMap);
        startOrder = findViewById(R.id.bt_driver);
        startOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始接单的点击事件
                String buttonText = startOrder.getText().toString();
                if (buttonText.equals("开始接单")) {
                    startOrder.setText("接单中...");
                    GetOrderService.GetOrder();
                    timer.schedule(task, 0, 1000);
                } else if (buttonText.equals("已接到乘客上车")) {
                    //修改按钮的文本
                    startOrder.setText("乘客已经到达目的地");
                    //将之前记录的用户的SessionId对应的乘客状态置为traveling
                    //将自身的状态记做driving

                    TravelService.setTravel();
                } else if (buttonText.equals("乘客已经到达目的地")) {
                    //在此处将司机和乘客的状态重新设置为idle
                    GetDestinationService.setStatus();
                    //重新开始接单
                    startOrder.setText("开始接单");
                } else {

                }


            }
        });

    }

    protected void createMap(Bundle savedInstanceState) {
        //展示地图容器
        _mapView.onCreate(savedInstanceState);


        //得到amap对象
        _amap = _mapView.getMap();

        //默认显示实时交通信息
        _amap.setTrafficEnabled(true);


        //得到UISettings
        _uiSettings = _amap.getUiSettings();

        //添加一个指南针控件
        _uiSettings.setCompassEnabled(true);

        //添加一个缩放比例尺
        _uiSettings.setScaleControlsEnabled(true);

        //修改logo位置
        _uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);

    }

    //以某个经纬度为中心来展示地图
    protected void moveMap(double latitude, double longtiude) {
        LatLng lagLng = new LatLng(latitude, longtiude);

        //移动amap地图 以之前的缩放比例展示
        _amap.animateCamera(CameraUpdateFactory.newLatLngZoom(lagLng, _amap.getCameraPosition().zoom));
    }

    //向固定的经纬度添加一个标记
    protected void addMarkerToMap(double latitude, double longitude) {
        _selfMarker = _amap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker))));
    }

    //启动定位服务器
    protected void doLocation() {
        //1 创建一个客户端定位句柄
        _amapLocationClient = new AMapLocationClient(getApplicationContext());

        //1.5 给定位客户端设置一些属性
        _amapLocationOption = new AMapLocationClientOption();
        //每个5s定位一次
        _amapLocationOption.setInterval(3000);
        //_amapLocationOption.setOnceLocation(true);

        //将option设置给client对象
        _amapLocationClient.setLocationOption(_amapLocationOption);

        //2 给客户端句柄设置一个listenner来处理服务器返回的定位数据
        _amapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                //onLocationChanged 就是如果服务器给客户端返回数据，调用的回调函数
                //aMapLocation 就是服务器给客户端返回的定位数据

                if (aMapLocation != null) {
                    //服务器是有响应的

                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功，aMapLocation获取数据
                        Log.e("Amap", "location succ address = " + aMapLocation.getAddress());
                        Log.e("Amap", "city = " + aMapLocation.getCity());
                        Log.e("Amap", "longtitude = " + aMapLocation.getLongitude());
                        Log.e("Amap", "latitude = " + aMapLocation.getLatitude());

                        //更新司机的当前位置
                        UpdateAddressService.updateAddress(String.valueOf(aMapLocation.getLatitude())
                                , String.valueOf(aMapLocation.getLongitude()));

                        if (isAddSelfMarker == false) {
                            //在此位置添加一个标记
                            addMarkerToMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            isAddSelfMarker = true;

                            //以自我为中心展示地图
                            moveMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        }


                    } else {
                        //定位失败，

                        Log.e("Amap", "location error, code = " + aMapLocation.getErrorCode() +
                                ", info = " + aMapLocation.getErrorInfo());
                    }
                }
            }
        });

        //3 开启定位服务
        _amapLocationClient.startLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        initUI();

        createMap(savedInstanceState);

        doLocation();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        _mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        _mapView.onSaveInstanceState(outState);
    }
}