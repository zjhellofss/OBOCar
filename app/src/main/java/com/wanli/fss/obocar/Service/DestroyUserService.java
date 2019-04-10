package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.DestroyUserHttpUtils;

public class DestroyUserService {
    public static void destoryUser(String sessionId) {
        try {
            DestroyUserHttpUtils.destroyUserFromServer(sessionId);
        } catch (InterruptedException e) {
            Log.e("DestroyService", "用户退出失败");
        }
    }
}
