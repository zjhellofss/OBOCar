package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.TakeCarHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

public class TakeCarService {
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
            Log.e("TakeCarService","线程异常中断");
        }
        return res;
    }
}
