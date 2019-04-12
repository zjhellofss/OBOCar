package com.wanli.fss.obocar.Service;

import android.util.Log;

import com.wanli.fss.obocar.Service.ServiceUtils.FaceScanHttpUtils;

public class FaceScanService {
    private final static String Tag = FaceScanService.class.getSimpleName();

    public static boolean faceScanForGender(String pictureBase64) {
        try {
            return FaceScanHttpUtils.checkGender(pictureBase64);
        } catch (InterruptedException e) {
            Log.e(Tag, "网络访问线程异常中断");
        }
        return false;
    }
}
