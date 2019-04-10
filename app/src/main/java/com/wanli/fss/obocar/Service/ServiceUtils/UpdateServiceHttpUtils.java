package com.wanli.fss.obocar.Service.ServiceUtils;

import android.util.Log;

import com.wanli.fss.obocar.Session.SessionLoger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateServiceHttpUtils {
    public static void updateAddressToServer(String sessionId, String latitude, String longitude) throws InterruptedException {
        //调用实例 xxx/xxx.com/?sid=xxx&la=xxx&lo=xxx

        final FormBody formBody = new FormBody
                .Builder()
                .add("sid", sessionId)
                .add("la", latitude)
                .add("lo", longitude)
                .build();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                //构建FormBody，传入要提交的参数

                final Request request = new Request.Builder()
                        .url("http://192.168.43.109:8080/mywb/update")
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
                        Log.e("UpdateHttpUtils", "坐标更新成功");
                    }
                    Log.e("UpdateHttpUtils", res);
                } catch (IOException e) {
                    throw new RuntimeException(e.toString());
                }
            }
        });
        t.start();
        t.join(2000);
    }
}
