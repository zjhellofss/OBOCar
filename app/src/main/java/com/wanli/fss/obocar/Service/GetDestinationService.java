package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.GetDestinationHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class GetDestinationService {
    private final static String Tag = GetDestinationService.class.getSimpleName();
    public static void setStatus() {
        String sessionId = SessionLoger.getSessionId();
        String passengerId = SessionLoger.getPeerId();
        try {
            GetDestinationHttpUtils.setStatusToServer(sessionId, passengerId);
        } catch (InterruptedException e) {
            Log.e(Tag, "网络访问线程异常中断");
        }
    }
}
