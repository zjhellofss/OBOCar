package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.wanli.fss.obocar.Service.ServiceUtils.GetDriverLocationHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class GetDriverLocationService {
    private static String Tag = GetDriverLocationService.class.getSimpleName();

    public static LatLonPoint getDriverLocation() {
        String driverId = SessionLoger.getPeerId();
        String location = null;
        try {
            location = GetDriverLocationHttpUtils.GetDriverLocationFromServer(driverId);
        } catch (InterruptedException e) {
            Log.e("GetDriverLocation", "司机定位异常");
        }
        if (location != null) {
            String[] laotitude = location.split("-");
            double latitude = Double.parseDouble(laotitude[0]);
            double longitude = Double.parseDouble(laotitude[1]);
            return new LatLonPoint(latitude, longitude);
        } else {
            Log.e(Tag, "网络访问线程异常中断");
        }
        //默认放回的定位点
        return new LatLonPoint(120.0, 30.0);
    }
}
