package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.GetStateServiceHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

/**
 * @author fss
 * 获取自身状态的一个通用函数
 */
public class GetStateService {
    public static String getStateService() {
        String sid = SessionLoger.getSessionId();
        try {
            return GetStateServiceHttpUtils.getStateServiceFromServer(sid);
        } catch (InterruptedException e) {
            Log.e("GetStateService", "线程异常中断");
        }
        return "";
    }

}
