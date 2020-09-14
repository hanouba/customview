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
    private Request.Builder mBuilder;

    //请求头里面的标记
    private static final String HANDSHAKE = "handshake";

    public HttpRequest(String url) {
        mBuilder = new Request.Builder()
                .get()
                .url(url);
    }

    /**
     * 与对方交换公钥
     * @param callback
     * @param pubKey
     */
    public void handshake(Callback callback,String pubKey) {
        //当前是一个握手请求
        mBuilder.addHeader(HANDSHAKE,pubKey);
        request(callback);
        //防止其他请求也带handshake
        mBuilder.removeHeader(HANDSHAKE);
    }

    public void request(Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(mBuilder.build());


        if (call != null) {
            if (call.isExecuted()) {
                call.clone().enqueue(callback);
            }else {
                call.enqueue(callback);
            }
        }
    }
}
