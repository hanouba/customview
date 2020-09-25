package com.hansen.customview.downmanager;

import android.content.Context;

import com.hansen.customview.BaseApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Currency;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author HanN on 2020/9/25 10:31
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/25 10:31
 * @updateremark:
 * @version: 2.1.67
 */
public class DownloadManager {
    //什么意思
    private static final AtomicReference<DownloadManager> INSTANCE =
            new AtomicReference<>();
    OkHttpClient mClient;
    //存放各个下载请求
    private HashMap<String, Call> downCalls;


    /**
     * 单例模式
     *
     * @return
     */
    public static DownloadManager getInstance() {
        for (; ; ) {
            DownloadManager current = INSTANCE.get();
            if (current != null) {
                return current;
            }

            current = new DownloadManager();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    private DownloadManager() {
        downCalls = new HashMap<>();
        mClient = new OkHttpClient().newBuilder().build();

    }

    public void download(String url, DownLoadObserver downLoadObserver) {
        Observable.just(url)
                .filter(s -> !downCalls.containsKey(s)) //call的map 里面有了就表面正在下载,则这次不下载
                .flatMap(s -> Observable.just(createDownInfo(s)))
                .map(this::getRealFileName)//检测本地文件夹,生成新的文件名
                .flatMap(downloadInfo -> Observable.create(new DownloadSubscribe(downloadInfo)))//下载
                .observeOn(AndroidSchedulers.mainThread())//在主线程回调
                .subscribeOn(Schedulers.io())//在子线程执行
                .subscribe(downLoadObserver); //添加观察者


    }

    /**
     * 创建downinfo
     *
     * @param url 下载地址
     * @return
     */
    private DownloadInfo createDownInfo(String url) {
        //downloadinfo 里面只有下载地址信息
        DownloadInfo downloadInfo = new DownloadInfo(url);
        //获取文件大小 通过url
        getContentLength(url);
        return null;
    }

    /**
     * 获取文件长度
     *
     * @param url
     * @return
     */
    private long getContentLength(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            //获取请求回调
            Response response = mClient.newCall(request).execute();
            //如果请求成功了
            if (request != null && response.isSuccessful()) {
                //通过body获取长度
                long contentLength = response.body().contentLength();
                //关闭
                response.close();
                //如果长度是0 返回-1
                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //默认都是异常的
        return DownloadInfo.TOTAL_ERROR;


    }

    /**
     * @param downloadInfo
     * @return
     */
    private DownloadInfo getRealFileName(DownloadInfo downloadInfo) {
        String fileName = downloadInfo.getFileName();
        //下载进度是 0  文件长度是之前通过url获取得到的
        long downloadLength = 0, contentLength = downloadInfo.getTotal();
        File file = new File(BaseApp.getInstance().getFilesDir(), fileName);
        if (file.exists()) {
            //找到了文件,代办已经下载过,则取其长度
            downloadLength = file.length();
        }
        //之前下载过,需要重新来一个文件
        int i = 1;
        while (downloadLength >= contentLength) {
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex) + "(" + i + ")" + fileName.substring(dotIndex);
            }
            File newFile = new File(BaseApp.getInstance().getFilesDir(), fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
            i++;
        }

        //设置改变过的文件名 大小
        downloadInfo.setProgress(downloadLength);
        downloadInfo.setFileName(file.getName());
        return downloadInfo;
    }

    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {
        private DownloadInfo downloadInfo;

        public DownloadSubscribe(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void subscribe(ObservableEmitter<DownloadInfo> emitter) throws Exception {
            String url = downloadInfo.getUrl();
            long downloadLength = downloadInfo.getProgress();
            long coententLength = downloadInfo.getTotal();
            //初始化进度
            emitter.onNext(downloadInfo);
            Request request = new Request.Builder()
                    //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + coententLength)
                    .url(url)
                    .build();

            Call call = mClient.newCall(request);
            downCalls.put(url, call);
            Response response = call.execute();
            //文件名称
            File file = new File(BaseApp.getInstance().getFilesDir(), downloadInfo.getFileName());
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            try {



                //请求体作为输入流
                inputStream = response.body().byteStream();
                //向文件输出
                fileOutputStream = new FileOutputStream(file, true);
                byte[] buffer = new byte[2048];
                //缓存数组2k
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    //下载的文件长度
                    downloadLength += len;
                    downloadInfo.setProgress(downloadLength);
                    emitter.onNext(downloadInfo);

                }
                //停止
                fileOutputStream.flush();
                //移除
                downCalls.remove(url);
            }finally {
                IOUtils.closeAll(inputStream, fileOutputStream);
            }
            //完成
            emitter.onComplete();
        }
    }





}
