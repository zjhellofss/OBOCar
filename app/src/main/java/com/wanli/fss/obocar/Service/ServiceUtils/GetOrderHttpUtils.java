package com.wanli.fss.obocar.Service.ServiceUtils;

import android.util.Log;

import com.wanli.fss.obocar.Session.SessionLoger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetOrderHttpUtils {
    //
    public static void GetOrderFromServer(String sessionId) throws InterruptedException {
        final FormBody formBody = new FormBody
                .Builder()
                .add("sid", sessionId)
                .build();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                OkHttpClient client = new OkHttpClient();
                //构建FormBody，传入要提交的参数

                final Request request = new Request.Builder()
                        .url("http://192.168.43.109:8080/mywb/getOrder")
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
                    String res = response.body().string();
                    if (res.startsWith("success")) {
                        String sessionId = res.substring(8, res.length());
                        //记录用户当前的sessionId
                        SessionLoger.setSessionId(sessionId);
                    }
                    Log.e("GetOrderHttpUtils", res);
                } catch (IOException e) {
                    throw new RuntimeException(e.toString());
                }
            }
        });
        t.start();
        t.join(5000);
    }
}


