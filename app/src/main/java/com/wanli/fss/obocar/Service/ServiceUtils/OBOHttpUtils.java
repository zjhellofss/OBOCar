package com.wanli.fss.obocar.Service.ServiceUtils;

import android.util.Log;

import com.wanli.fss.obocar.Session.SessionLoger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class PersonForJson {
    private String status;
    private int age;
    private String gender;
    private String emotion;

    public PersonForJson(String status, int age, String gender, String emotion) {
        this.status = status;
        this.age = age;
        this.gender = gender;
        this.emotion = emotion;

    }

    public PersonForJson() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}

/**
 * 作为所有访问网络方法的父类
 *
 * @author fss
 */
public abstract class OBOHttpUtils {
    public static String res = "";

    public static String singleParamsHttp(String sessionId, final String method) throws InterruptedException {
        res = "";
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
                        .url("http://192.168.43.109:8080/mywb/" + method)
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
                    if (res.startsWith("success")) {
                        String sessionId = res.substring(8, res.length());
                        //记录用户当前的sessionId
                        SessionLoger.setSessionId(sessionId);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e.toString());
                }
            }
        });
        t.start();
        t.join(5000);
        return res;
    }

    public static String doubleParamsHttp(String driverId, String passengerId, final String method) throws InterruptedException {
        res = "";
        final FormBody formBody = new FormBody
                .Builder()
                .add("sid", driverId)
                .add("pid", passengerId)
                .build();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                OkHttpClient client = new OkHttpClient();
                //构建FormBody，传入要提交的参数

                final Request request = new Request.Builder()
                        .url("http://192.168.43.109:8080/mywb/" + method)
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
                    if (res.startsWith("success")) {
                        String sessionId = res.substring(8, res.length());
                        //记录用户当前的sessionId
                        SessionLoger.setSessionId(sessionId);
                    }
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


