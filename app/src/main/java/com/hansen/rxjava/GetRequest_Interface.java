package com.hansen.rxjava;

import com.hansen.customview.bean.Translation;
import com.hansen.customview.bean.Translation2;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author HanN on 2020/7/7 13:27
 * @email: 1356548475@qq.com
 * @project customview
 * @description: 金山词霸网络请求接口    http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world
 * @updateuser:
 * @updatedata: 2020/7/7 13:27
 * @updateremark:
 * @version: 2.1.67
 */
public interface GetRequest_Interface {
    //模拟注册
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();
    //模拟登陆
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation2> getCall_2();

}
