package com.hansen.netcory.http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author HanN on 2020/9/14 11:13
 * @email: 1356548475@qq.com
 * @project customview
 * @description: okhttp
 * @updateuser:
 * @updatedata: 2020/9/14 11:13
 * @updateremark:
 * @version: 2.1.67
 */
public  class HttpRequest {

    private Call mCall;
    private Callback mCallBack;
    public HttpRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        mCall = client.newCall(request);

    }

    public void request(Callback callback) {
        if (mCall != null) {
            if (mCall.isExecuted()) {
                mCall.clone().enqueue(callback);
            }else {
                mCall.enqueue(callback);
            }
        }
    }
}
