package com.wanli.fss.obocar.Service;

import com.wanli.fss.obocar.Service.ServiceUtils.FaceScanHttpUtils;

public class FaceScanService {

    public static boolean faceScanForGender(String pictureBase64) {
        try {
            return FaceScanHttpUtils.checkGender(pictureBase64);
        } catch (InterruptedException e) {
            throw new RuntimeException("对端服务器异常");
        }
    }
}
