package com.wanli.fss.obocar.Service;

import com.wanli.fss.obocar.Service.ServiceUtils.GetOrderHttpUtils;

public class GetOrderService {
    public static void GetOrder(String sessionId) {
        GetOrderHttpUtils.GetOrderFromServer(sessionId);
    }
}
