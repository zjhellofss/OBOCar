package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.GetOrderHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class GetOrderService {
    public static void GetOrder() {
        try {
            GetOrderHttpUtils.GetOrderFromServer(SessionLoger.getSessionId());
        } catch (InterruptedException e) {
            Log.e("GerOrderService", "接单失败");
        }
    }
}
