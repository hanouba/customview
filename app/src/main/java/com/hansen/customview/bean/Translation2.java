package com.hansen.customview.bean;

import android.util.Log;

/**
 * @author HanN on 2020/7/7 13:24
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 金山词霸实体类型 模拟登陆
 * @updateuser:
 * @updatedata: 2020/7/7 13:24
 * @updateremark:
 * @version: 2.1.67
 */
public class Translation2 {
    private int status;

    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    public void show() {
        Log.d("RxJava", content.out );
    }

}
