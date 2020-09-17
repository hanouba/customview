package com.hansen.okhttprenewalup;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author HanN on 2020/9/17 9:41
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 上传监听
 * @updateuser:
 * @updatedata: 2020/9/17 9:41
 * @updateremark:
 * @version: 2.1.67
 */
interface HttpUpListener {
    void onUpFile(long totalLength,long arealdLength);
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response);
}
