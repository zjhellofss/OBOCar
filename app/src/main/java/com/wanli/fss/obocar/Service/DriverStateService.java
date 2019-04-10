package com.wanli.fss.obocar.Service;

//等待司机的状态发生改变

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.DriverStateHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

/**
 * @author fss
 * 从ready状态到catching 状态表示接到一个单子需要改变状态
 */
public class DriverStateService {
    public static void waitForState() {
        //得到司机自身的sessionId
        String sessionId = SessionLoger.getSessionId();
        try {
            DriverStateHttpUtils.waitStateFromServer(sessionId);
        } catch (InterruptedException e) {
            Log.e("司机等单", "线程中断异常");
        }
    }
}
