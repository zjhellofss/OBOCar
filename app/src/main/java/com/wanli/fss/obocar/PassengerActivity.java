package com.wanli.fss.obocar;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

public class PassengerActivity extends AppCompatActivity {


    private MapView _mapView = null;
    private AMap _amap = null;
    private AMapLocationClient _amapLocationClient = null;
    private AMapLocationClientOption _amapLocationOption = null;
    private Button _bt_startOrder = null;
    private TextView _tv_srcAddr = null;
    private TextView _attv_dstAddr = null;

    private Marker _selfMarker = null;
    private boolean isAddSelfMarker = false;



    protected  void initUI() {
        _mapView = (MapView)findViewById(R.id.PassengerMap);
        _bt_startOrder = (Button)findViewById(R.id.bt_startOrder);
        _tv_srcAddr = (TextView)findViewById(R.id.tv_passenger_srcAddr);
        _attv_dstAddr = (TextView)findViewById(R.id.attv_passenger_dstAddr);

    }

    //以某个经纬度为中心来展示地图
    protected void moveMap(double latitude, double longtiude)
    {
        LatLng lagLng = new LatLng(latitude, longtiude);

        //移动amap地图 以之前的缩放比例展示
        _amap.animateCamera(CameraUpdateFactory.newLatLngZoom(lagLng, _amap.getCameraPosition().zoom));
    }

    //向固定的经纬度添加一个标记
    protected void addMarkerToMap(double latitude, double longitude)
    {
        _selfMarker = _amap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker))));
    }


    protected void createMap(Bundle savedInstanceState) {
        //展示地图容器
        _mapView.onCreate(savedInstanceState);


        //得到amap对象
        _amap = _mapView.getMap();

        //默认显示实时交通信息
        _amap.setTrafficEnabled(true);

    }

    //启动定位服务器
    protected  void doLocation()
    {
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

                    if(aMapLocation.getErrorCode() == 0) {
                        //定位成功，aMapLocation获取数据
                        Log.e("Amap", "location succ address = "+ aMapLocation.getAddress());
                        Log.e("Amap", "city = "+ aMapLocation.getCity());
                        Log.e("Amap", "longtitude = " + aMapLocation.getLongitude());
                        Log.e("Amap", "latitude = " + aMapLocation.getLatitude());

                        if (isAddSelfMarker == false) {
                            //在此位置添加一个标记
                            addMarkerToMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            isAddSelfMarker = true;

                            //以自我为中心展示地图
                            moveMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        }

                        //设置乘客源地址信息
                        _tv_srcAddr.setText(aMapLocation.getAddress());


                    }
                    else {
                        //定位失败，

                        Log.e("Amap", "location error, code = "+ aMapLocation.getErrorCode()+
                                ", info = "+ aMapLocation.getErrorInfo());
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
        setContentView(R.layout.activity_passenger);

        initUI();
        createMap(savedInstanceState);
        doLocation();

    }
}

