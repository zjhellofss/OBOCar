package com.wanli.fss.obocar.Service.ServiceUtils;


public class TravelServiceHttpUtils {
    public static void setTravelToServer(String driverId, String passengerId) throws InterruptedException {
        OBOHttpUtils.doubleParamsHttp(driverId, passengerId, "travel");

    }
}
