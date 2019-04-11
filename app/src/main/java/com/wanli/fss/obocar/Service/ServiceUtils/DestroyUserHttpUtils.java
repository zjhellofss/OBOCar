package com.wanli.fss.obocar.Service.ServiceUtils;
public class DestroyUserHttpUtils {
    public static void destroyUserFromServer(String sessionId) throws InterruptedException {
        OBOHttpUtils.singleParamsHttp(sessionId, "remove");
    }
}
