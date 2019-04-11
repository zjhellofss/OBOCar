package com.wanli.fss.obocar.Service.ServiceUtils;

public class GetOrderHttpUtils {
    public static void GetOrderFromServer(String sessionId) throws InterruptedException {
        OBOHttpUtils.singleParamsHttp(sessionId, "getOrder");
    }
}


