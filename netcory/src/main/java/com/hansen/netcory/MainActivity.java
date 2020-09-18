package com.hansen.netcory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.hansen.crypto.AES;
import com.hansen.crypto.DH;
import com.hansen.crypto.DataUtils;
import com.hansen.crypto.RSA;
import com.hansen.netcory.http.HttpRequest;
import com.hansen.netcory.http.OkHttpDownUtils;
import com.hansen.netcory.http.listener.HttpDownListener;


import java.io.File;
import java.io.IOException;

import javax.crypto.spec.DHGenParameterSpec;

public class MainActivity extends AppCompatActivity {
    private static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChkESFLhsDzQKJPaIQ3WE+9PZt\n" +
            "3o1Bzpkk7uNRLC2rOyeuEAdO+JDDNkxM0MdX/Jfn1Xf9INeDhXikrCmSaaF9/fMf\n" +
            "ihBhfDQ2HKj8T8kVfyV5aNOdXvxwuhKTcyWlfEHl++sDKmCO4H6UhDvawIqxf3GH\n" +
            "wLogOiTIge+CMf1+iwIDAQAB";
    private static final String URL = "http://192.168.3.107/";
    private static final String DOWNURL = "http://192.168.3.107/";
    private static final File PATH_NAME = Environment.getDownloadCacheDirectory();
    private byte[] mAesKey;
    private static final String TAG = "MainActivity_TAG";
    private HttpRequest httpRequest;
    private DH dhC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpRequest = new HttpRequest(URL);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void requestUrl(View view) {

        dhC = new DH();
        //获取自己的公钥
        final int publicKey = dhC.getPublicKey();
        //如何aes密匙不可用 , 则直接握手
        if (mAesKey == null || mAesKey.length <= 0) {

            Log.i(TAG, "requestUrl: 获取自己的公钥" + dhC.getPublicKey() );

            httpRequest.handshake(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //服务端已经将公钥返回
                    byte[] serverPubKey = response.body().bytes();
                    Log.i(TAG, "serverPubKey: "+ DataUtils.byte2int(serverPubKey));
                    //客户端通过服务端的公钥得到私钥 作为aes的密钥  这个和服务端是一致的
                    mAesKey = dhC.getSecretKey(serverPubKey);
                    Log.i(TAG, "mAesKey: "+new String(mAesKey));
                }
                //用公钥(base64)加密公钥(int)得到密文(base64)
            }, RSA.encrypt(publicKey,PUB_KEY));
        }else {
            httpRequest.request(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    byte[] responseContent = response.body().bytes();
                    Log.i(TAG, "onResponse: "+ responseContent);
                    //正常请求中通过aes进行解码
                    AES aes = new AES(mAesKey);
                    String content = new String(aes.decrypt(responseContent));

                    Log.i(TAG, "onResponse: "+content);
                }
            });
        }


    }

    public void downFile(View view) {
        OkHttpDownUtils okHttpDownUtils = new OkHttpDownUtils();
        okHttpDownUtils.getDownRequest(DOWNURL, PATH_NAME, new HttpDownListener() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response, long totalLength, long alreadDownLength) {

            }
        });
    }
}
