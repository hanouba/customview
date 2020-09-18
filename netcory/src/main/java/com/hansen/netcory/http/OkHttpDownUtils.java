package com.hansen.netcory.http;

import android.util.Log;

import com.hansen.netcory.http.listener.HttpDownListener;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author HanN on 2020/9/17 11:41
 * @email: 1356548475@qq.com
 * @project customview
 * @description: okhttp实现下载
 * @updateuser:
 * @updatedata: 2020/9/17 11:41
 * @updateremark:
 * @version: 2.1.67
 */
public class OkHttpDownUtils {

    private static final String TAG = "OkHttpDownUtil";
    private Call mCall;
    private long mAlreadyDownLength = 0;//已经下载长度
    private long mTotalLength = 0;//整体文件大小
    private int mSign = 0; //标记当前运行的是那个方法
    private String mDownUrl;//下载网络地址
    private File mPath;//文件保存路径
    private JSONObject mJson;
    private HttpDownListener mHttpDownListener;//下载进度接口回调

    /**
     * 没有断点续传的下载
     * @param downUrl
     * @param saveFilePathAndName
     * @param listener
     */
    public  void getDownRequest(final String downUrl, final File saveFilePathAndName, final HttpDownListener listener) {
        mSign = 1;
        mDownUrl = downUrl;
        mPath = saveFilePathAndName;
        mHttpDownListener = listener;
        mAlreadyDownLength = 0;

        final Request request = new Request.Builder()
                .url(mDownUrl)
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();

        mCall = client.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHttpDownListener != null) {
                    mHttpDownListener.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                mTotalLength = responseBody.contentLength();
                InputStream inputStream = responseBody.byteStream();
                FileOutputStream fileOutputStream = new FileOutputStream(mPath);

                try {
                    byte[] bytes = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(bytes)) != -1) {
                        mAlreadyDownLength = mAlreadyDownLength + len;
                        fileOutputStream.write(bytes,0,len);
                        if (mHttpDownListener != null) {
                            mHttpDownListener.onResponse(call,response,mTotalLength,mAlreadyDownLength);
                        }
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    fileOutputStream.close();
                    inputStream.close();
                }
            }
        });


    }

    /**
     * 有断点续传的get下载
     * @param downUrl
     * @param saveFilePathAndName
     * @param listener
     */
    public void getRenewalDownRequest(final String downUrl, final File saveFilePathAndName, final HttpDownListener listener) {
        mSign = 2;
        mDownUrl = downUrl;
        mPath = saveFilePathAndName;
        mHttpDownListener = listener;

        final Request request = new Request.Builder()
                .url(mDownUrl)
                .header("RANGE","bytes=" + mAlreadyDownLength + "-")
                .build();
        OkHttpClient client = new OkHttpClient();
        mCall = client.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHttpDownListener != null) {
                    mHttpDownListener.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                //输入流
                InputStream inputStream = responseBody.byteStream();
                RandomAccessFile randomAccessFile = new RandomAccessFile(mPath,"rw");

                if (mTotalLength == 0) {
                mTotalLength = responseBody.contentLength();
                //预设创建一个总字节的的占位文件
                randomAccessFile.setLength(mTotalLength);

                }
                if (mAlreadyDownLength != 0) {
                    randomAccessFile.seek(mAlreadyDownLength);
                }

                try {
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(bytes)) != -1) {
                        randomAccessFile.write(bytes,0,len);
                        mAlreadyDownLength = mAlreadyDownLength + len;
                        if (mHttpDownListener != null) {
                            mHttpDownListener.onResponse(call,response,mTotalLength,mAlreadyDownLength);
                        }
                    }

                }catch (Exception e) {
                    //上传异常
                }finally {
                    //记录当前保存文件的位置
                    mAlreadyDownLength = randomAccessFile.getFilePointer();
                    randomAccessFile.close();
                    inputStream.close();

                }


            }
        });
    }

    /**
     * 没有断点续传的post下载
     * @param downUrl
     * @param saveFilePathAndName
     * @param json
     * @param listener
     */
    public void postDownRequest(final String downUrl,final File saveFilePathAndName,final JSONObject
            json,final HttpDownListener listener) {
        mSign = 3;
        mDownUrl = downUrl;
        mPath = saveFilePathAndName;
        mJson = json;
        mHttpDownListener = listener;
        mAlreadyDownLength = 0;

        final Request request = new Request.Builder()
                .url(mDownUrl)
                .post(changeJSON(mJson))
                .build();

        OkHttpClient client = new OkHttpClient();
        mCall = client.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHttpDownListener != null) {
                    mHttpDownListener.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                mTotalLength = responseBody.contentLength();
                InputStream inputStream = responseBody.byteStream();
                FileOutputStream fileOutputStream = new FileOutputStream(mPath);

                try {
                    byte[] bytes = new byte[2048];
                    int len  =  0;
                    while ((len = inputStream.read(bytes)) != 0) {
                        fileOutputStream.write(bytes,0,len);
                        mAlreadyDownLength = mAlreadyDownLength  +  len;
                        if (mHttpDownListener != null) {
                            mHttpDownListener.onResponse(call,response,mTotalLength,mAlreadyDownLength);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    fileOutputStream.close();
                    inputStream.close();
                }


            }
        });

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
    /**
     * 支持断点续传的post下载
     * @param downUrl 下载网址
     * @param saveFilePathAndName 文件保存路径
     * @param json 参数
     * @param listener 接口回调
     */
    public void postRenewalDownRequest(final String downUrl, final File saveFilePathAndName, final JSONObject json, final HttpDownListener listener){
        mSign = 4;
        mDownUrl = downUrl;
        mPath = saveFilePathAndName;
        mJson = json;
        mHttpDownListener = listener;
        Request request = new Request.Builder()
                .url(mDownUrl)
                .header("RANGE","bytes="+mAlreadyDownLength+"-")
                .post(changeJSON(json))
                .build();
        OkHttpClient client = new OkHttpClient();
        mCall = client.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHttpDownListener != null){
                    mHttpDownListener.onFailure(call,e);
                }
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                InputStream inputStream = responseBody.byteStream();
                RandomAccessFile randomAccessFile = new RandomAccessFile(mPath,"rw");
                if (mTotalLength == 0){
                    mTotalLength = responseBody.contentLength();
                    randomAccessFile.setLength(mTotalLength);
                }
                if (mAlreadyDownLength!=0){
                    randomAccessFile.seek(mAlreadyDownLength);
                }
                byte[] bytes = new byte[2048];
                int len = 0;
                try {
                    while ((len = inputStream.read(bytes)) != -1) {
                        randomAccessFile.write(bytes, 0, len);
                        mAlreadyDownLength = mAlreadyDownLength + len;
                        if (mHttpDownListener != null) {
                            mHttpDownListener.onResponse(call, response, mTotalLength, mAlreadyDownLength);
                        }


                    }
                }catch (Exception e){
                    Log.e(TAG, "Post下载异常");


                }finally {
                    mAlreadyDownLength = randomAccessFile.getFilePointer();
                    randomAccessFile.close();
                    inputStream.close();
                    Log.e(TAG, "流关闭 下载的位置="+mAlreadyDownLength);
                }


            }
        });
    }
    /**
     * 恢复下载
     */
    public void resume(){
        if (mSign==0){
            return;
        }
        switch (mSign){
            case 1:
                getDownRequest(mDownUrl,mPath,mHttpDownListener);
                break;
            case 2:
                getRenewalDownRequest(mDownUrl,mPath,mHttpDownListener);
                break;
            case 3:
                postDownRequest(mDownUrl,mPath,mJson,mHttpDownListener);
                break;
            case 4:
                postRenewalDownRequest(mDownUrl,mPath,mJson,mHttpDownListener);
                break;
            default:
                break;
        }


    }

    /**
     * 暂停下载
     */
    public void stop(){
        if (mCall!=null){
            mCall.cancel();
        }


    }

    /**
     * 删除下载文件
     */
    public void deleteCurrentFile(){
        if (mPath == null){
            Log.e(TAG, "deleteCurrentFile error : 没有路径");
            return;
        }
        if (!mPath.exists()){
            Log.e(TAG, "deleteCurrentFile error: 文件不存在");
            return;
        }
        mPath.delete();
        mAlreadyDownLength = 0;
        mTotalLength = 0;
        mSign = 0;
    }

    /**
     * 销毁
     */
    public void destroy(){
        if (mCall!=null){
            mCall.cancel();
            mCall = null;
        }
        mSign = 0;
        mDownUrl = null;
        mPath = null;
        mHttpDownListener = null;
        mAlreadyDownLength = 0;
        mTotalLength = 0;
    }
}
