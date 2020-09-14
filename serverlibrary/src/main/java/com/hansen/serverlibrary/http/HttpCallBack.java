package com.hansen.serverlibrary.http;

/**
 * @author HanN on 2020/9/14 11:50
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 网络监听
 * @updateuser:
 * @updatedata: 2020/9/14 11:50
 * @updateremark:
 * @version: 2.1.67
 */
public interface HttpCallBack {
    /**
     * 请求到的数据
     * @param request
     * @return
     */
    byte[] onResponse(String request);
}
