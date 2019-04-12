package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.TakeCarHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class TakeCarService {
    private static final String Tag = TakeCarService.class.getSimpleName();

    /**
     * return 是否有合适的司机
     * 如果有合适的司机返回SessionId，
     * 如果当前没有合适的司机或者出现了其他状况返回FAILED
     */
    public static String getDriverSid() {
        String res = null;
        try {
            res = TakeCarHttpUtils.getDriverFromServer(SessionLoger.getSessionId());
        } catch (InterruptedException e) {
            Log.e(Tag, "访问网络的子线程异常中断");
        }
        return res;
    }
}
