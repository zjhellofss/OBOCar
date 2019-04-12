package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.DestroyUserHttpUtils;

public class DestroyUserService {
    private final static String Tag = DestroyUserService.class.getSimpleName();
    public static void destoryUser(String sessionId) {
        try {
            DestroyUserHttpUtils.destroyUserFromServer(sessionId);
        } catch (InterruptedException e) {
            Log.e(Tag, "网络访问线程异常中断");
        }
    }
}
