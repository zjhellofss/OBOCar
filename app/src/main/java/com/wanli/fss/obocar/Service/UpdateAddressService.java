package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.UpdateServiceHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class UpdateAddressService {
    private static final String Tag = UpdateAddressService.class.getSimpleName();

    public static void updateAddress(String latitude, String longitude) {
        String sessionId = SessionLoger.getSessionId();
        try {
            UpdateServiceHttpUtils.updateAddressToServer(sessionId, latitude, longitude);
        } catch (InterruptedException e) {
            Log.e(Tag, "用户更新位置失败");
        }
    }
}
