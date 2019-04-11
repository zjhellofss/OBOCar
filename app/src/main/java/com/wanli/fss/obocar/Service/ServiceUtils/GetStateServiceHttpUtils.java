package com.wanli.fss.obocar.Service.ServiceUtils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetStateServiceHttpUtils {
    public static String res = "";

    public static String getStateServiceFromServer(String sessionId) throws InterruptedException {
        res = OBOHttpUtils.singleParamsHttp(sessionId, "getState");
        return res;
    }
}
