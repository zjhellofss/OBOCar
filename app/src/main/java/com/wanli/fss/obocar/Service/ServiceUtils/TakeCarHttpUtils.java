package com.wanli.fss.obocar.Service.ServiceUtils;


public class TakeCarHttpUtils {
    public static String res;

    //从服务器中获取合适的司机(距离最近且当前处于接单的状态)
    public static String getDriverFromServer(String sessionId) throws InterruptedException {
        return OBOHttpUtils.singleParamsHttp(sessionId, "takeCar");
    }
}
