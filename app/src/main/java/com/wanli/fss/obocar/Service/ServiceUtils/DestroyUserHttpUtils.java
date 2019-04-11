package com.wanli.fss.obocar.Service.ServiceUtils;

import android.util.Log;

import com.wanli.fss.obocar.Session.SessionLoger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DestroyUserHttpUtils {
    public static void destroyUserFromServer(String sessionId) throws InterruptedException {
        OBOHttpUtils.singleParamsHttp(sessionId, "remove");
    }
}
