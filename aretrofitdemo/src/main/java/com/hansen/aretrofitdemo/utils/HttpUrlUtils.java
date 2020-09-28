package com.hansen.aretrofitdemo.utils;

import java.lang.reflect.Field;

import okhttp3.HttpUrl;

/**
 * Description: <HttpUrlUtils><br>
 * Author:      mxdl<br>
 * Date:        2020/8/29<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class HttpUrlUtils {
    private static HttpUrl httpUrl;
    private static Field hostField;
    private static Field portField;

    public HttpUrlUtils(HttpUrl httpUrl) {
        this.httpUrl = httpUrl;
    }
    static {
        try {
            hostField = HttpUrl.class.getDeclaredField("host");
            hostField.setAccessible(true);

            portField = HttpUrl.class.getDeclaredField("port");
            portField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void setHost(String url){
        try {
            hostField.set(httpUrl,url);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void setPort(int port){
        try {
            portField.set(httpUrl,port);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }
}
