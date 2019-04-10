package com.wanli.fss.obocar;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.wanli.fss.obocar.Service.DestroyUserService;
import com.wanli.fss.obocar.Service.GetDriverLocationService;
import com.wanli.fss.obocar.Service.GetOrderService;
import com.wanli.fss.obocar.Service.TakeCarService;
import com.wanli.fss.obocar.Service.UpdateAddressService;
import com.wanli.fss.obocar.Session.SessionLoger;

import java.util.ArrayList;
import java.util.List;

public class PassengerActivity extends AppCompatActivity {


    private MapView _mapView = null;
    private AMap _amap = null;
    private AMapLocationClient _amapLocationClient = null;
    private AMapLocationClientOption _amapLocationOption = null;
    private Button _bt_startOrder = null;
    private TextView _tv_srcAddr = null;
    private AutoCompleteTextView _attv_dstAddr = null;

    private Marker _selfMarker = null;
    private boolean isAddSelfMarker = false;
    private String city;
    private LatLonPoint startPoint;
    private LatLonPoint endPoint;
    private DrivingRouteOverlay drivingRouteOverlay = null;

    protected void initUI() {
        _mapView = (MapView) findViewById(R.id.PassengerMap);
        _bt_startOrder = (Button) findViewById(R.id.bt_startOrder);
        _tv_srcAddr = (TextView) findViewById(R.id.tv_passenger_srcAddr);
        _attv_dstAddr = (AutoCompleteTextView) findViewById(R.id.attv_passenger_dstAddr);

    }

    //以某个经纬度为中心来展示地图
    protected void moveMap(double latitude, double longtiude) {
        LatLng lagLng = new LatLng(latitude, longtiude);

        //移动amap地图 以之前的缩放比例展示
        _amap.animateCamera(CameraUpdateFactory.newLatLngZoom(lagLng, _amap.getCameraPosition().zoom));
    }

    //向固定的经纬度添加一个标记
    protected void addMarkerToMap(double latitude, double longitude, int resourceId) {
        _selfMarker = _amap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), resourceId))));
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
    protected void doLocation() {
        //1 创建一个客户端定位句柄
        _amapLocationClient = new AMapLocationClient(getApplicationContext());

        //1.5 给定位客户端设置一些属性
        _amapLocationOption = new AMapLocationClientOption();
        //每5s定位一次
        _amapLocationOption.setInterval(5000);
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

                        UpdateAddressService.updateAddress(String.valueOf(aMapLocation.getLatitude())
                                , String.valueOf(aMapLocation.getLongitude()));
                        if (!isAddSelfMarker) {
                            //在此位置添加一个标记
                            addMarkerToMap(aMapLocation.getLatitude(), aMapLocation.getLongitude(), R.drawable.location_marker);
                            isAddSelfMarker = true;

                            //以自我为中心展示地图
                            moveMap(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            city = aMapLocation.getCity();
                        }

                        //设置乘客源地址信息
                        _tv_srcAddr.setText(aMapLocation.getAddress());
                        if (startPoint == null) {
                            //得到乘客的其实坐标
                            startPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
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

    //绘制驾驶交通路径

    protected void drawRouteLine(LatLonPoint startPoint, LatLonPoint endPoint) {
        final LatLonPoint startPointFinal = startPoint;
        final LatLonPoint endPointFinal = endPoint;
        //创建路径的绘制句柄
        RouteSearch routeSearch = new RouteSearch(getApplicationContext());
        //获取起点和终点
        RouteSearch.FromAndTo ft = new RouteSearch.FromAndTo(startPointFinal, endPointFinal);
        //设置一个路径搜索的query
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(ft,
                RouteSearch.DrivingDefault, null, null, "");
        //给绘制路径的句柄设置一个callback函数
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                //仅处理驾驶的路线绘制
                //画驾驶的路线图
                if (i != 1000) {
                    Log.e("Amap", "驾驶路径绘制失败");
                    Toast.makeText(getApplicationContext(), "服务器正忙", Toast.LENGTH_LONG).show();
                } else {
                    //得到路径
                    //todo 以第一种方案为准
                    DrivePath path = driveRouteResult.getPaths().get(0);
                    drivingRouteOverlay = new DrivingRouteOverlay(
                            getApplicationContext(), _amap, path, startPointFinal, endPointFinal);
                    //去掉中间的小车
                    drivingRouteOverlay.setNodeIconVisibility(false);
                    drivingRouteOverlay.zoomToSpan();
                    drivingRouteOverlay.setThroughPointIconVisibility(false);
                    //将路径添加到地图
                    drivingRouteOverlay.addToMap();
                }
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        routeSearch.calculateDriveRouteAsyn(query);

    }

    //开启POI兴趣点
    protected void doSearchPOI() {
        _bt_startOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_bt_startOrder.getText().equals("开始约车")) {
                    Log.e("Amap", "开始搜寻目的地POI");
                    String dstAddr = _attv_dstAddr.getText().toString();
                    //创建一个搜索的条件对象
                    PoiSearch.Query query = new PoiSearch.Query(dstAddr, "", city);
                    //创建一个POISearch句柄和query关联
                    PoiSearch poiSearch = new PoiSearch(getApplicationContext(), query);
                    //给search绑定一个回调函数
                    poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                        @Override
                        public void onPoiSearched(PoiResult poiResult, int i) {
                            //得到POI兴趣点集合,使用i判断查询是否成功
                            if (i != 1000) {
                                Toast.makeText(getApplicationContext(), "服务器正忙", Toast.LENGTH_LONG).show();
                                Log.e("Amap", "POI搜索失败");
                            } else {
                                List<PoiItem> poiItemList = poiResult.getPois();
                                for (int j = 0; j < poiItemList.size(); ++j) {
                                    PoiItem item = poiItemList.get(j);
                                    Log.e("Amap", "搜索到的兴趣点: " + item.getTitle());
                                    addMarkerToMap(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude(), R.drawable.lc);
                                }
                                //todo 以第一个兴趣点作为兴趣点
                                if (poiItemList.size() > 0) {
                                    endPoint = poiItemList.get(0).getLatLonPoint();
                                }
                                //TODO 约车的逻辑
                                _bt_startOrder.setText("确认打车");
                                //将之前的路径清空
                                _amap.clear();
                                //画路线地址
                                drawRouteLine(startPoint, endPoint);
                            }
                        }

                        @Override
                        public void onPoiItemSearched(PoiItem poiItem, int i) {

                        }
                    });
                    poiSearch.searchPOIAsyn();
                    _bt_startOrder.setText("确定打车");
                } else if (_bt_startOrder.getText().equals("确认打车") || _bt_startOrder.getText().equals("约车中...")) {
                    //更新服务器中User对象的状态
                    _bt_startOrder.setText("约车中...");
                    String driverSid = TakeCarService.getDriverSid();
                    if (driverSid.equals("FAILED")) {
                        Toast.makeText(getApplicationContext(), "服务器正忙或附近没有司机，请稍后再试", Toast.LENGTH_LONG).show();
                        //todo 应该设置回退的环节，但是我时间来不及了
                    } else {
                        Log.e("Amap", "司机的sessionId为 " + driverSid);
                        SessionLoger.setPeerId(driverSid);
                        //todo 必须完成的功能,绘制司机的位置和路径图
                        Toast.makeText(getApplicationContext(), "司机正在赶来，请在原地等待", Toast.LENGTH_LONG).show();
                        _bt_startOrder.setText("等待接驾...");
                        _amap.clear();//清除地图的覆盖物
                        drivingRouteOverlay.removeFromMap();//清除路线规划图
                        //将自身位置添加到地图中
                        addMarkerToMap(startPoint.getLatitude(), startPoint.getLongitude(), R.drawable.location_marker);
                        //司机所在的位置
                        LatLonPoint driverPoint = GetDriverLocationService.getDriverLocation();

                    }
                }
            }
        });
    }


    protected void initAutoCompleteTextView() {
        //给attv控件设置一个阈值
        _attv_dstAddr.setThreshold(1);

        //给autocompleteTextView绑定自动补齐功能
        _attv_dstAddr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当文本内容发生改变的时候 调用此回调函数


                //从高德的服务器获取有关跟关键字匹配的poi名称的数据

                //(1) 得到要搜索的关键字
                final String keyword = _attv_dstAddr.getText().toString();
                if (keyword == null || keyword.length() == 0) {
                    Log.e("Amap", "search keyword == null");
                    return;
                }

                //(2) 创建一个query 查询所有tips的条件
                InputtipsQuery query = new InputtipsQuery(keyword, "");

                //(3) 创建一个InputTips 查询句柄
                Inputtips search = new Inputtips(getApplicationContext(), query);

                //(4) 给InputTips 设定回调函数
                search.setInputtipsListener(new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> list, int i) {

                        if (i != 1000) {
                            Log.e("Amap", "search input tips error i = " + i);
                            return;
                        }


                        // 1 应该从服务器获取能够匹配的 单词集合
                        ArrayList<String> poiList = new ArrayList<String>();
                        for (int index = 0; index < list.size(); index++) {
                            Log.e("Amap", "通过 " + keyword + "匹配到的tips 有 " + list.get(index).getName());
                            poiList.add(list.get(index).getName());

                        }


                        // 2 给autoCompleteTextView 设置一个适配器Adapter
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, poiList);

                        // 3 将Adapter  和 给autoCompleteTextView 相关联
                        _attv_dstAddr.setAdapter(adapter);

                        // 4 触发adapter 触发控件显示  单词集合
                        adapter.notifyDataSetChanged();

                    }
                });


                //(5) 开启InputTips 向服务器发送查询请求
                search.requestInputtipsAsyn();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        initUI();
        createMap(savedInstanceState);
        doLocation();
        doSearchPOI();
        initAutoCompleteTextView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //todo 摧毁服务器中User对象
        Log.e("Amap", "摧毁地图对象");
        //DestroyUserService.destoryUser(SessionLoger.getSessionId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Amap", "暂停地图显示");
        //为了方便开发故不从服务器中移除对应的对象
        // DestroyUserService.destoryUser(SessionLoger.getSessionId());
    }
}

