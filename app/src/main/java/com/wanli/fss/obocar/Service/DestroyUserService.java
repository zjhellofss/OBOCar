package com.wanli.fss.obocar.Service;

import com.wanli.fss.obocar.Service.ServiceUtils.DestroyUserHttpUtils;

public class DestroyUserService {
    public static void destoryUser(String sessionId) {
        try {
            DestroyUserHttpUtils.destroyUserFromServer(sessionId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
