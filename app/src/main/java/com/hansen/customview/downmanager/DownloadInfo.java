package com.hansen.customview.downmanager;

/**
 * @author HanN on 2020/9/25 10:32
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 下载文件信息实体类
 * 这个类就是一些基本信息,total就是需要下载的文件的总大小,而progress就是当前下载的进度了,这样就可以计算出下载的进度信息了
 * 接着看DownloadManager的download方法,首先通过url生成一个Observable对象,然后通过filter操作符过滤一下,如果当前正在下载这个url对应的内容,那么就不下载它,
 * 接下来调用createDownInfo重新生成Observable对象,这里应该用map也是可以的,createDownInfo这个方法里会调用getContentLength来获取服务器上的文件大小,可以看一下这个方法的代码,
 *
 * 作者：陈丰尧
 * 链接：https://www.jianshu.com/p/fe3607dddacc
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * @updateuser:
 * @updatedata: 2020/9/25 10:32
 * @updateremark:
 * @version: 2.1.67
 */
public class DownloadInfo {
    private String url;
    private String fileName;
    private long total;
    private long progress;
    //获取进度失败
    public static final  int TOTAL_ERROR = -1;

    public DownloadInfo(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
