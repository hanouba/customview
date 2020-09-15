package com.hansen.netcory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hansen.crypto.DH;
import com.hansen.crypto.RSA;
import com.hansen.netcory.http.HttpRequest;

import java.io.IOException;

import javax.crypto.spec.DHGenParameterSpec;

public class MainActivity extends AppCompatActivity {
    private static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChkESFLhsDzQKJPaIQ3WE+9PZt\n" +
            "3o1Bzpkk7uNRLC2rOyeuEAdO+JDDNkxM0MdX/Jfn1Xf9INeDhXikrCmSaaF9/fMf\n" +
            "ihBhfDQ2HKj8T8kVfyV5aNOdXvxwuhKTcyWlfEHl++sDKmCO4H6UhDvawIqxf3GH\n" +
            "wLogOiTIge+CMf1+iwIDAQAB";
    private static final String URL = "http://192.168.3.107/";
    private byte[] mAesKey;
    private static final String TAG = Package.getPackages().getClass().getSimpleName();
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpRequest = new HttpRequest(URL);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void requestUrl(View view) {

        DH dh = new DH();
        //获取自己的公钥
        int publicKey = dh.getPublicKey();
        //如何aes密匙不可用 , 则直接握手
        if (mAesKey == null || mAesKey.length <= 0) {

            Log.i(TAG, "requestUrl: 获取自己的公钥" + dh.getPublicKey() );

            httpRequest.handshake(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            }, RSA.encrypt(publicKey,PUB_KEY));
        }
        httpRequest.request(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: "+response.body().toString());
            }
        });

    }
}
