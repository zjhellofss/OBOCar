package com.wanli.fss.obocar;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.wanli.fss.obocar.Service.FaceScanService;
import com.wanli.fss.obocar.Service.ServiceUtils.FaceScanHttpUtils;

import java.io.ByteArrayOutputStream;


public class PhotoActivity extends AppCompatActivity {
    private static final int REQ_CODE = 189;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            startActivityForResult(takePhotoIntent, REQ_CODE);//启动相机
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            /*缩略图信息是储存在返回的intent中的Bundle中的，
             * 对应Bundle中的键为data，因此从Intent中取出
             * Bundle再根据data取出来Bitmap即可*/
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            Log.e("bitMap", bitmap.getHeight() + "");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //读取图片到ByteArrayOutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //参数如果为100那么就不压缩
            byte[] bytes = baos.toByteArray();
            //生成拍摄照片的base64编码
            String pictureBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
            Log.e("bitMap", pictureBase64);
            boolean res = FaceScanService.faceScanForGender(pictureBase64);
            Toast.makeText(getApplicationContext(), (res ? "男性" : "女性"), Toast.LENGTH_LONG).show();
        } else {

        }
    }
}
