package com.hansen.netcory.http;

import android.util.Log;


import com.hansen.netcory.http.listener.HttpUpListener;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * @author HanN on 2020/9/17 9:32
 * @email: 1356548475@qq.com
 * @project customview
 * @description:  okhttp上传  断点上传
 * @updateuser:
 * @updatedata: 2020/9/17 9:32
 * @updateremark:
 * @version: 2.1.67
 */
public class OkhttpUpUtils {
    private static final String TAG = "OkHttpUpUtil";
    private String mUpUrl;
    private File mPath;
    private Call mCall;
    private Map<String,String> mParams;
    //已经上传长度
    private long mAlreadyUpLength = 0;
    //整体文件大小
    private long mTotalLength = 0;
    //标记
    private int mSign = 0;
    private HttpUpListener mHttpUpListener;

    /**
     *
     * @param upUrl  上传的地址
     * @param upFilePathAndName 文件路径名称
     * @param params 参数
     * @param listener 上传监听
     */
    public void postUpRequest(final String upUrl, final File upFilePathAndName, final Map<String,String> params, final HttpUpListener listener){
            synchronized (this) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mSign = 1;
                        mUpUrl = upUrl;
                        mPath = upFilePathAndName;
                        mParams = params;
                        mHttpUpListener = listener;
                        mAlreadyUpLength = 0;

                        final RequestBody requestBody = new RequestBody() {
                            @Override
                            public MediaType contentType() {
                                return null;
                            }

                            @Override
                            public void writeTo(BufferedSink sink) throws IOException {
                                RandomAccessFile randomAccessFile = new RandomAccessFile(mPath,"rw");
                                if (mTotalLength == 0) {
                                    mTotalLength = randomAccessFile.length();

                                }
                                byte[] bytes = new byte[2048];
                                int len = 0;

                                try {
                                    while ((len = randomAccessFile.read(bytes)) != -1) {
                                        sink.write(bytes,0,len);
                                        //已经下载的长度
                                        mAlreadyUpLength = mAlreadyUpLength + len;
                                        if (mHttpUpListener != null) {
                                            mHttpUpListener.onUpFile(mTotalLength,mAlreadyUpLength);
                                        }
                                    }
                                }catch (Exception e) {
                                    //流中断
                                    Log.i(TAG,"流异常中断"+e.getMessage());
                                }finally {
                                    //关闭流
                                    randomAccessFile.close();
                                }
                            }
                        };


                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        if (mParams != null) {
                            //该方法返回map中所有key值的列表。
                            Set<String> keys = mParams.keySet();
                            for (String key :
                                    keys) {
                                builder.addFormDataPart(key, mParams.get(key));
                            }
                        }
                        builder.addFormDataPart("file",mPath.getName(),requestBody);
                        MultipartBody multipartBody = builder.build();
                        Request request = new Request.Builder()
                                .url(mUpUrl)
                                .post(multipartBody)
                                .build();
                        OkHttpClient client = new OkHttpClient();

                        mCall = client.newCall(request);
                        mCall.equals(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                if (mHttpUpListener != null) {
                                    mHttpUpListener.onFailure(call,e);
                                }
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (mHttpUpListener != null) {
                                    mHttpUpListener.onResponse(call, response);
                                }
                            }
                        });
                    }
                }).start();
            }
    }


    /**
     * post断点上传
     * @param upUrl
     * @param upFilePathAndName
     * @param params
     * @param listener
     */
    public void postRenewalUpRequest(final String upUrl, final File upFilePathAndName,
                                     final Map<String,String> params, final HttpUpListener listener){
        synchronized (this) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //断点上传
                    mSign = 2;
                    mUpUrl = upUrl;
                    mPath = upFilePathAndName;
                    mParams = params;
                    mHttpUpListener = listener;

                    RequestBody requestBody = new RequestBody() {
                        @Override
                        public MediaType contentType() {
                            return null;
                        }

                        @Override
                        public void writeTo(BufferedSink sink) throws IOException {
                            RandomAccessFile randomAccessFile = new RandomAccessFile(mPath,"rw");
                            if (mTotalLength == 0) {
                                //总文件大小
                                mTotalLength = randomAccessFile.length();
                            }
                            if (mAlreadyUpLength != 0) {
                                //如果已经长传了一部分文件,接着上传
                                randomAccessFile.seek(mAlreadyUpLength);
                            }
                            byte[] bytes = new byte[2048];
                            int len = 0;
                            try {
                                while ((len = randomAccessFile.read(bytes)) != -1) {
                                    //写文件
                                    sink.write(bytes,0,len);
                                    //已经写的文件长度
                                    mAlreadyUpLength = mAlreadyUpLength + len;
                                    if (mHttpUpListener != null) {
                                        //监听文件上传进度
                                        mHttpUpListener.onUpFile(mTotalLength,mAlreadyUpLength);
                                    }
                                }
                            }catch (Exception e) {
                                //长传中断
                            }finally {
                                //关闭流
                                //流关闭的时候已经上传的文件大小
                                mAlreadyUpLength = randomAccessFile.getFilePointer();
                                randomAccessFile.close();
                            }
                        }
                    };

                    //
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    if (mParams != null) {
                        //得到mparams 的key
                        Set<String> keys = mParams.keySet();
                        for (String key :
                                keys) {
                            //遍历key  通过key获取mparsm的value
                            //键值对的形式添加在MultipartBody
                            builder.addFormDataPart(key, mParams.get(key));
                        }
                    }
                    builder.addFormDataPart("file",mPath.getName(),requestBody);
                    MultipartBody multipartBody = builder.build();

                    Request request = new Request.Builder()
                            .url(mUpUrl)
                            .header("RANGE","bytes="+mAlreadyUpLength + "-"+ mTotalLength)
                            .post(multipartBody)
                            .build();
                    OkHttpClient client = new OkHttpClient();

                    mCall = client.newCall(request);
                    mCall.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            if (mHttpUpListener != null) {
                                //监听文件上传进度
                                mHttpUpListener.onFailure(call,e);
                            }
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (mHttpUpListener != null) {
                                //监听文件上传进度
                                mHttpUpListener.onResponse(call,response);
                            }
                            //为啥要赋值为0 呢
                            mAlreadyUpLength = 0;
                            mTotalLength = 0;
                        }
                    });
                }
            }).start();
        }
    }

    public void resume() {
        if (mSign == 0) {
            return;
        }
        switch (mSign) {
            case 1:
                postUpRequest(mUpUrl,mPath,mParams,mHttpUpListener);
                break;
            case 2:
                postRenewalUpRequest(mUpUrl,mPath,mParams,mHttpUpListener);
                break;
            default:
                break;

        }
    }

    /**
     * 暂停
     */
    public void stop() {
        if (mCall != null) {
            mCall.clone();
        }
    }

    public void deleateCurrentFile() {
        if (mPath == null){
            Log.e(TAG, "deleteCurrentFile error : 没有路径");
            return;
        }
        if (!mPath.exists()){
            Log.e(TAG, "deleteCurrentFile error: 文件不存在");
            return;
        }
        mPath.delete();
        mAlreadyUpLength = 0;
        mTotalLength = 0;
        mSign = 0;
    }

    /**
     * 销毁 内存优化
     */
    public void destroy() {
        if (mCall != null) {
            mCall.clone();
            mCall = null;
        }
        mSign = 0;
        mHttpUpListener = null;
        mPath = null;
        mHttpUpListener = null;
        mAlreadyUpLength = 0;
        mTotalLength = 0;
    }

    /**
     * 转换Json参数为RequestBody
     * @param jsonParam json对象
     * @return RequestBody
     */
    private RequestBody changeJSON(JSONObject jsonParam){
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , String.valueOf(jsonParam));
        return requestBody;
    }
}
