package com.wanli.fss.obocar.Service.ServiceUtils;


public class GetStateServiceHttpUtils {
    public static String res = "";

    public static String getStateServiceFromServer(String sessionId) throws InterruptedException {
        res = OBOHttpUtils.singleParamsHttp(sessionId, "getState");
        return res;
    }
}
