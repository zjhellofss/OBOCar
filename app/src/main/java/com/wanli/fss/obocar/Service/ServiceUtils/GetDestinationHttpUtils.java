package com.wanli.fss.obocar.Service.ServiceUtils;

public class GetDestinationHttpUtils {
    public static void setStatusToServer(String driverId, String passengerId) throws InterruptedException {
        OBOHttpUtils.doubleParamsHttp(driverId,passengerId,"todest");
    }
}
