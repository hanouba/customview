package com.hansen.netcory;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hansen.netcory.http.HttpRequest;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://192.168.3.107/";
    private static final String TAG = Package.getPackages().getClass().getSimpleName();
    private HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpRequest = new HttpRequest(URL);
    }

    public void requestUrl(View view) {
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
