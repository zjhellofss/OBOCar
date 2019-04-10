package com.wanli.fss.obocar.Service.ServiceUtils;

import android.util.Log;

import com.wanli.fss.obocar.Session.SessionLoger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetDriverLocationHttpUtils {
    private static String res = "";

    public static String GetDriverLocationFromServer(String driverId) throws InterruptedException {

        final FormBody formBody = new FormBody
                .Builder()
                .add("sid", driverId)
                .build();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                //构建FormBody，传入要提交的参数
                final Request request = new Request.Builder()
                        .url("http://192.168.43.109:8080/mywb/getDriver")
                        .post(formBody)
                        .build();
                Call requestCal = client.newCall(request);
                Response response = null;
                try {
                    response = requestCal.execute();
                } catch (IOException e) {
                    throw new RuntimeException(e.toString());
                }
                try {
                    res = response.body().string();
                    Log.e("GetDriverHttpUtils", res);
                } catch (IOException e) {
                    throw new RuntimeException(e.toString());
                }
            }
        });
        t.start();
        t.join(5000);
        return res;
    }
}
