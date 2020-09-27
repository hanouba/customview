package com.hansen.aretrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.hansen.aretrofitdemo.bean.BaseResponse
import com.hansen.aretrofitdemo.service.CommonService
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.PublicKey
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {
//    解决RxJava使用中的内存泄漏问题
    var lifecycleProvider = AndroidLifecycle.createLifecycleProvider(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)




        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
        val cert: X509Certificate =
                cf.generateCertificate(assets.open("mxdl_server.cer")) as X509Certificate
        val publicKey: PublicKey = cert.getPublicKey()
    }
//   1.单文件上传
    fun uploadFile(view: View) {
    Log.v("MYTGAG", "uploadFile start...")
    var retrofit = Retrofit.Builder()
            .client(
                    OkHttpClient.Builder()
                            .addInterceptor(
                                    HttpLoggingInterceptor()
                                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                            )
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .build()
            )
            .baseUrl("http://192.168.3.107:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    var commonService = retrofit.create(CommonService::class.java)
    //3.创建一个文件上传的命令
    //应用文件目录 getExternalFilesDir  /storage/emulated/0/Android/data/com.learn.test/files
    //竟然可以不要权限
    var requestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            File(getExternalFilesDir(""), "github.jpg")

    )

    var part = MultipartBody.Part.createFormData("file", "github.jpg", requestBody)
    var uploadFileCall = commonService.uploadFile(part)
    uploadFileCall.enqueue(object  : Callback<BaseResponse> {
        override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
            Log.v("MYTAG", "onFail start...")
            Log.v("MYTAG", t.toString())
        }

        override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
            Log.v("MYTAG", "onResponse start...")
            Log.v("MYTAG", response.body().toString())
        }

    })


}
//    2.多文件上传
    fun uploadFiles(view: View) {
    Log.v("MYTGAG", "uploadFiles start...")
    //1创建一个Retrofit
    var retrofit = Retrofit.Builder()
            .client(
                    OkHttpClient.Builder()
                            .addInterceptor(
                                    HttpLoggingInterceptor()
                                            .setLevel(HttpLoggingInterceptor.Level.BASIC)
                            )
                            .build()
            )
            .baseUrl("http://192.168.3.107:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    //2.创建一个服务
    var commonService = retrofit.create(CommonService::class.java)

    //3.创建一个文件上传的命令
    var requestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            File(getExternalFilesDir(""), "github.jpg")
    )
    // 传输文件类型名称   文件名称 请求体
    var part = MultipartBody.Part.createFormData("files", "github.jpg", requestBody)


    var requestBody1 = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            File(getExternalFilesDir(""), "header.jpg")
    )

    var part1 = MultipartBody.Part.createFormData("files", "header.jpg", requestBody1)

    var parts = ArrayList<MultipartBody.Part>()
    parts.add(part)
    parts.add(part1)
    var uploadFileCall = commonService.uploadFiles(parts)

    //4.执行文件上传命令
    uploadFileCall.enqueue(object : Callback<BaseResponse> {
        override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
            Log.v("MYTAG", "onFail start...")
            Log.v("MYTAG", t.toString())
        }

        override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
            Log.v("MYTAG", "onResponse start...")
            Log.v("MYTAG", response.body().toString())
        }
    })
}
//    3.多文件上传1
    fun uploadFiles1(view: View) {}
//    4.文件的下载
    fun downloadFile(view: View) {}
//    5.带进度条的文件下载
    fun downloadFileProgress(view: View) {}
//    6.自定义数据转换器
    fun uploadFileConverter(view: View) {}
//   8.动态的切换多个url
    fun changeUrl(view: View) {}
//    9.网络的缓存设置
    fun loginCache(view: View) {}
//    10.添加一个HTTPS的请求
    fun loginHttps(view: View) {}
//  11.获取token
    fun getToken(view: View) {}
//  11.使用token
    fun getUsers(view: View) {}
//    12.RxJava的结合使用
    fun loginRxJava(view: View) {}
//    15.与Deferred的结合使用
    fun loginDeferred(view: View) {}
//    16.与Kotlin协程的结合使用
    fun loginCoroutine(view: View) {}
}