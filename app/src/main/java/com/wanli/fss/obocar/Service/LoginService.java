package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.LoginHttpUtils;

public class LoginService {
    private static final String Tag = LoginService.class.getSimpleName();
    public static boolean UserIsValid(String username, String pswd, String isDriver) {
        boolean res = false;
        try {
            res = LoginHttpUtils.CheckUserFromServer(username, pswd, isDriver);
        } catch (Exception e) {
            Log.e(Tag, "子线程异常");
        }
        return res;
    }
}
