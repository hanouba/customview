package com.hansen.okhttprenewalup;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author HanN on 2020/9/17 11:45
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/17 11:45
 * @updateremark:
 * @version: 2.1.67
 */
public interface HttpDownListener {
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response,long totalLength,long alreadDownLength);

}
