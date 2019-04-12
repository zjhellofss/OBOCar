package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.GetStateServiceHttpUtils;
import com.wanli.fss.obocar.Session.SessionLoger;

/**
 * @author fss
 * 获取自身状态的一个通用函数
 */
public class GetStateService {
    private static final String Tag = GetStateService.class.getSimpleName();

    public static String getStateService() {
        String sid = SessionLoger.getSessionId();
        try {
            return GetStateServiceHttpUtils.getStateServiceFromServer(sid);
        } catch (InterruptedException e) {
            Log.e(Tag, "线程异常中断");
        }
        return "";
    }
}
