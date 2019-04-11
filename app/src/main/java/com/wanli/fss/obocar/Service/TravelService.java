package com.wanli.fss.obocar.Service;

import com.wanli.fss.obocar.Service.ServiceUtils.TravelServiceHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class TravelService {
    public static void setTravel() {
        String driverId = SessionLoger.getSessionId();
        String passengerId = SessionLoger.getPeerId();
        try {
            TravelServiceHttpUtils.setTravelToServer(driverId, passengerId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
