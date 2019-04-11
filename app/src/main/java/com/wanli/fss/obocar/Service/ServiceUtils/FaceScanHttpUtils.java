package com.wanli.fss.obocar.Service.ServiceUtils;


import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//{"status":200,"age":"84","gender":"female","emotion":"other"}
class UserForJson {
    private String status;
    private int age;
    private String gender;
    private String emotion;

    public UserForJson(String status, int age, String gender, String emotion) {
        this.status = status;
        this.age = age;
        this.gender = gender;
        this.emotion = emotion;
    }

    public UserForJson() {
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

public class FaceScanHttpUtils {
    //验证产生的性别
    private static String gender = "";
    private static String appcode = "aa416896d3f843cead6ed877223e0664";

    /**
     * @param base64Code 待验证照片的base64编码
     * @return 如果是男性返回true 如果是女性返回false
     */
    public static boolean checkGender(String base64Code) throws InterruptedException {
        //初始化Http请求的请求体，采用post方式发出请求
        final FormBody formBody = new FormBody
                .Builder()
                .add("src", base64Code)
                .build();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                OkHttpClient client = new OkHttpClient();
                //构建FormBody，传入要提交的参数
                final Request request = new Request.Builder()
                        .url("https://facescan.xiaohuaerai.com/facescan")
                        .post(formBody)
                        .addHeader("Authorization", "APPCODE " + appcode)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .build();

                Call requestCal = client.newCall(request);
                Response response = null;
                try {
                    response = requestCal.execute();
                } catch (IOException e) {
                    throw new RuntimeException(e.toString());
                }
                try {
                    String jsonRes = response.body().string();
                    Gson gson = new Gson();
                    UserForJson user = gson.fromJson(jsonRes, UserForJson.class);
                    gender = user.getGender();
                } catch (IOException e) {
                    throw new RuntimeException(e.toString());
                }
            }
        });
        t.start();
        t.join(5000);

        if (gender.equals("male")) {
            return true;
        } else {
            return false;
        }
    }
}
