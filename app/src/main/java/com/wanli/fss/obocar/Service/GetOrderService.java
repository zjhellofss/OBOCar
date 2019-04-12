package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.GetOrderHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class GetOrderService {
    private static  final String Tag = GetOrderService.class.getSimpleName();
    public static void GetOrder() {
        try {
            GetOrderHttpUtils.GetOrderFromServer(SessionLoger.getSessionId());
        } catch (InterruptedException e) {
            Log.e(Tag, "网络线程访问失败");
        }
    }
}
