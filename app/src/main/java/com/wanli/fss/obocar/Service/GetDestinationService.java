package com.wanli.fss.obocar.Service;

import com.wanli.fss.obocar.Service.ServiceUtils.GetDestinationHttpUtils;
import com.wanli.fss.obocar.Service.ServiceUtils.GetStateServiceHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class GetDestinationService {
    public static void setStatus() {
        String sessionId = SessionLoger.getSessionId();
        String passengerId = SessionLoger.getPeerId();
        try {
            GetDestinationHttpUtils.setStatusToServer(sessionId, passengerId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
