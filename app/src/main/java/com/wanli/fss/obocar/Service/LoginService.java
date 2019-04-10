package com.wanli.fss.obocar.Service;

import android.util.Log;

public class LoginService {
    public static boolean UserIsValid(String username, String pswd, String isDriver) {
        boolean res = false;
        try {
            res = LoginHttpUtils.CheckUserFromServer(username, pswd, isDriver);
        } catch (Exception e) {
            Log.e("LoginService", "子线程异常");
        }
        return res;
    }
}
