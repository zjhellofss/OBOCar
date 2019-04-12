package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.TravelServiceHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class TravelService {
    private static final String Tag = TravelService.class.getSimpleName();
    public static void setTravel() {
        String driverId = SessionLoger.getSessionId();
        String passengerId = SessionLoger.getPeerId();
        try {
            TravelServiceHttpUtils.setTravelToServer(driverId, passengerId);
        } catch (InterruptedException e) {
            Log.e(Tag,"访问网络的子线程异常退出");
        }
    }
}
