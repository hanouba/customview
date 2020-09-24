package com.hansen.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Response;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.hansen.customview.Constant;
import com.hansen.customview.R;
import com.hansen.okhttprenewalup.HttpDownListener;
import com.hansen.okhttprenewalup.OkHttpDownUtils;

import java.io.File;
import java.io.IOException;

public class OkHttpActivity extends AppCompatActivity {
    private static final String TAG = OkHttpActivity.class.getSimpleName();
    private String downUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);


    }

    public void renewalDown(View view) {
        File file = new File(Constant.PATH_SDCARD);
        if (!file.exists()) {
            file.mkdirs();
        }
        OkHttpDownUtils.getInstance().getRenewalDownRequest(downUrl,file, new HttpDownListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d(TAG,"下载失败");
            }

            @Override
            public void onResponse(Call call, Response response, long totalLength, long alreadDownLength) {
                LogUtils.d(TAG,"下载成功"+totalLength);
            }
        });
    }
}