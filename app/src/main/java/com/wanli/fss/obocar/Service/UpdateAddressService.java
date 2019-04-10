package com.wanli.fss.obocar.Service;

import com.wanli.fss.obocar.Service.ServiceUtils.UpdateServiceHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class UpdateAddressService {
    public static void updateAddress(String latitude, String longitude) {
        String sessionId = SessionLoger.getSessionId();
        try {
            UpdateServiceHttpUtils.updateAddressToServer(sessionId, latitude, longitude);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
