package com.hansen.customview.downmanager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author HanN on 2020/9/25 11:05
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/25 11:05
 * @updateremark:
 * @version: 2.1.67
 */
public abstract class DownLoadObserver implements Observer<DownloadInfo> {
    protected Disposable d;//可以用于取消注册的监听者
    protected DownloadInfo downloadInfo;

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(DownloadInfo value) {
        this.downloadInfo = value;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
