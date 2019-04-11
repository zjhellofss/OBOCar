package com.wanli.fss.obocar.Service.ServiceUtils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TravelServiceHttpUtils {
    public static void setTravelToServer(String driverId, String passengerId) throws InterruptedException {
        OBOHttpUtils.doubleParamsHttp(driverId, passengerId, "travel");

    }
}
