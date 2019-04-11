package com.wanli.fss.obocar.Service.ServiceUtils;

public class GetDriverLocationHttpUtils {
    public static String GetDriverLocationFromServer(String driverId) throws InterruptedException {
        return OBOHttpUtils.singleParamsHttp(driverId, "getDriver");
    }
}
