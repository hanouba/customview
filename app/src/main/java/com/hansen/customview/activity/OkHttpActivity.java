package com.hansen.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hansen.customview.Constant;
import com.hansen.customview.R;
import com.hansen.customview.downmanager.DownLoadObserver;
import com.hansen.customview.downmanager.DownloadInfo;
import com.hansen.customview.downmanager.DownloadManager;
import com.hansen.okhttprenewalup.HttpDownListener;
import com.hansen.okhttprenewalup.OkHttpDownUtils;

import java.io.File;
import java.io.IOException;

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = OkHttpActivity.class.getSimpleName();
    private String downUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private String url1 = "http://192.168.3.107:8080/download/okhttp.png";
    private String url2 = "http://192.168.3.107:8080/download/192192.mp4";
    private String url3 = "http://192.168.3.145:8088/portal/r/df?groupValue=0dfac39c-81c2-4638-8e72-c6cc1ba02c1e&fileValue=FILE&sid=cf9585cb-3454-4660-bdaa-96de3472694f&repositoryName=%21form-ui-file-&appId=com.actionsoft.apps.ivsom&attachment=true&fileName=ivsom_zx_v20200825_1736_2.1.98_release_sign.apk&lastModified=1598349244000";

    private Button button1,button2,button3;
    private Button cancel1,cancel2,cancel3;
    private ProgressBar progressBar1,progressBar2,progressBar3;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        file = new File(Constant.PATH_SDCARD);
        LogUtils.i("File文件路径"+ file.getAbsolutePath());
        if (!file.exists()) {
            file.mkdirs();
        }
        initView();
    }

    private void initView() {
        button1 = findViewById(R.id.main_btn_down1);
        button2 = findViewById(R.id.main_btn_down2);
        button3 = findViewById(R.id.main_btn_down3);
        cancel1 = findViewById(R.id.main_btn_cancel1);
        cancel2 = findViewById(R.id.main_btn_cancel2);
        cancel3 = findViewById(R.id.main_btn_cancel3);
        progressBar1 = findViewById(R.id.main_progress1);
        progressBar2 = findViewById(R.id.main_progress2);
        progressBar3 = findViewById(R.id.main_progress3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        cancel1.setOnClickListener(this);
        cancel2.setOnClickListener(this);
        cancel3.setOnClickListener(this);


    }

    public void renewalDown(View view) {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_down1:
                DownloadManager.getInstance().download(file,url1, new DownLoadObserver() {
                    @Override
                    public void onComplete() {
                        ToastUtils.showLong("第一个下载完成");
                    }

                    @Override
                    public void onNext(DownloadInfo value) {
                        super.onNext(value);
                        progressBar1.setMax((int) value.getTotal());
                        progressBar1.setProgress((int) value.getProgress());
                    }
                });
                break;
            case R.id.main_btn_down2:
                DownloadManager.getInstance().download(file,url2, new DownLoadObserver() {
                    @Override
                    public void onComplete() {
                        ToastUtils.showLong("第二个下载完成");
                    }

                    @Override
                    public void onNext(DownloadInfo value) {
                        super.onNext(value);
                        progressBar2.setMax((int) value.getTotal());
                        progressBar2.setProgress((int) value.getProgress());
                    }
                });
                break;
            case R.id.main_btn_down3:
                DownloadManager.getInstance().download(file,url3, new DownLoadObserver() {
                    @Override
                    public void onComplete() {
                        ToastUtils.showLong("第三个下载完成");
                    }

                    @Override
                    public void onNext(DownloadInfo value) {
                        super.onNext(value);
                        progressBar3.setMax((int) value.getTotal());
                        progressBar3.setProgress((int) value.getProgress());
                    }
                });
                break;
            case R.id.main_btn_cancel1:
                DownloadManager.getInstance().cancel(url1);
                break;
            case R.id.main_btn_cancel2:
                DownloadManager.getInstance().cancel(url2);
                break;
            case R.id.main_btn_cancel3:
                DownloadManager.getInstance().cancel(url3);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        DownloadManager.getInstance().cancel(url2);
        super.onDestroy();
    }
}