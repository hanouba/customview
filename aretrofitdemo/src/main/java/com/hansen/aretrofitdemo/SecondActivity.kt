package com.hansen.aretrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import com.hansen.aretrofitdemo.bean.BaseResponse
import com.hansen.aretrofitdemo.converter.FileRequestBodyConverterFactory
import com.hansen.aretrofitdemo.download.DownloadCallback
import com.hansen.aretrofitdemo.download.OkHttpManager
import com.hansen.aretrofitdemo.service.CommonService
import com.hansen.aretrofitdemo.service.UserService
import com.hansen.aretrofitdemo.utils.FileUtils
import com.hansen.aretrofitdemo.utils.HttpUrlUtils
import com.hansen.aretrofitdemo.utils.HttpsUtils
import com.hansen.aretrofitdemo.utils.NetUtil
import com.hansen.rxjava3lib.rxjava3.RxJava3CallAdapterFactory
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import okhttp3.internal.http2.Header
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.security.PublicKey
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {
    val DOWN_URL = "http://192.168.3.107:8080/download/okhttp.png"

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
    fun uploadFiles1(view: View) {
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

        var requestBody1 = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                File(getExternalFilesDir(""), "header.jpg")
        )

        var multipartBody = MultipartBody.Builder()
                .addFormDataPart("files", "github.jpg", requestBody)
                .addFormDataPart("files", "header.jpg", requestBody1)
                .build()

        var uploadFileCall = commonService.uploadFiles1(multipartBody)

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


        Header.TARGET_AUTHORITY
    }

    //    4.文件的下载
    fun downloadFile(view: View) {
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


        var uploadFileCall = commonService.downloadFile("http://192.168.3.107:8080/download/okhttp.png")
        //4.执行文件下载命令
        uploadFileCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", t.toString())

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.v("MYTAG", "onResponse start...")
                //Log.v("MYTAG",response.body().toString())
                FileUtils.saveFile(
                        File(getExternalFilesDir(""), "mxdl.jpg"),
                        response.body()?.byteStream()
                )
            }
        })

    }

    //    5.带进度条的文件下载
    fun downloadFileProgress(view: View) {
        OkHttpManager.getInstance().download(DOWN_URL, File(getExternalFilesDir(""), "bbb.jpg")
                , object : DownloadCallback.OnDownloadListener {
            override fun onDownloadStart() {
                Log.v("MYTAG", "onDownloadStart start...")
            }

            override fun onDownloading(progress: Int) {
                Log.v("MYTAG", "onDownloading start:" + progress)
            }

            override fun onDownloadFailed(e: Exception?) {
                Log.v("MYTAG", "onDownloadFailed start...")
            }

            override fun onDownloadSuccess() {
                Log.v("MYTAG", "onDownloadSuccess start...")
            }

        })
    }

    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(
                        LoggingInterceptor.Builder()
                                .setLevel(Level.BASIC)
                                .request("request")
                                .response("respoonse")
                                .loggable(true)
                                .build()
                )
                .build()
    }

    //    6.自定义数据转换器 上传文件
    fun uploadFileConverter(view: View) {
        Log.v("MYTGAG", "uploadFile start...")
        //1创建一个Retrofit
        var retrofit = Retrofit.Builder()
                .client(
                        getOkHttpClient()
                )
                .baseUrl("http://192.168.3.107:8080")
                .addConverterFactory(FileRequestBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        //2.创建一个服务
        var commonService = retrofit.create(CommonService::class.java)

        //3.创建一个文件上传的命令
        val file = File(getExternalFilesDir(""), "github.jpg")
//        var requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
//            file
//        )
//        var part = MultipartBody.Part.createFormData("file","github.jpg", requestBody)
//        var uploadFileCall = commonService.uploadFile(part)
        var uploadFileCall = commonService.uploadFileConver(file)
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

    //   8.动态的切换多个url  实现 commonservice 和 userservice 的切换
    fun changeUrl(view: View) {
        var httpUrlUtils = HttpUrlUtils(HttpUrl.parse("http://192.168.3.107:8080"))

        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl(httpUrlUtils.httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.login("hn", 123456)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })


        view.postDelayed(object : Runnable {
            override fun run() {
                httpUrlUtils.setPort(8088)
                //3.创建用户登录命令
                var loginCall = userService.login("mxdl", 123456)

                //4.执行请求命令
                loginCall.enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(
                            call: Call<BaseResponse>,
                            response: Response<BaseResponse>
                    ) {
                        Log.v("MYTAG", "onResponse start...")
                        Log.v("MYTAG", "body:" + response.body().toString())
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        Log.v("MYTAG", "onFail start...")
                        Log.v("MYTAG", "error:" + t.toString());
                    }
                })
            }
        }, 1000 * 5)
    }

    //    9.网络的缓存设置
    var isfirst = true
    fun loginCache(view: View) {
        //1.创建一个OKHttpClient
        var okHttpClient = OkHttpClient.Builder()
                .cache(Cache(File(cacheDir, "okhttp-cache-v1"), 10 * 1024 * 1024))
                //有网缓存
                .addNetworkInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        var request = chain.request()
                        var response = chain.proceed(request)
                        response = response.newBuilder()
                                .addHeader("Cache-Control", "public,max-age=60")
                                .build()
                        return response
                    }

                })
                //断网缓存
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        var request = chain.request()
                        if (!NetUtil.checkNet(baseContext)) {
                            request = request.newBuilder()
                                    .addHeader(
                                            "Cache-Control",
                                            "public,if-only-cache,max-stale=" + Int.MAX_VALUE
                                    )
                                    .build()
                        }
                        return chain.proceed(request)
                    }

                })
                .build()

        var cacheControl = if (isfirst) CacheControl.FORCE_NETWORK else CacheControl.FORCE_CACHE
        //2.创建一个Request
        var request = Request.Builder()
                .url(
                        HttpUrl.parse("http://192.168.3.107:8080/login/get")?.newBuilder()
                                ?.addQueryParameter("username", "hn")
                                ?.addQueryParameter("password", "123456")
                                ?.build()
                )
                //.cacheControl(cacheControl)
                .build()

        //3.创建一个请求命令
        var loginCall = okHttpClient.newCall(request)

        //4.发起一个登录请求
        loginCall.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.v("MYTAG", "onFail start...")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                Log.v("MYTAG", "onResponse start...")
                if (response.isSuccessful) {
                    isfirst = false;
                    if (response.networkResponse() != null) {
                        Log.v("MYTAG", "net:" + response.body()?.string())

                    } else if (response.cacheResponse() != null) {
                        Log.v("MYTAG", "cache:" + response.body()?.string())
                    } else {
                        Log.v("MYTAG", "body null")
                    }
                } else {
                    Log.v("MYTAG", "body null")
                }
            }
        })
    }

    //    10.添加一个HTTPS的请求
    //    失败  环境文本
    fun loginHttps(view: View) {
        //1.创建一个Retorfit
        var retrofit = Retrofit.Builder()
                .client(
                        //OkHttpClient.Builder()
                        HttpsUtils.getTrustClient(assets.open("mxdl_server.cer")).newBuilder()
                                .addInterceptor(
                                        HttpLoggingInterceptor()
                                                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                                )
                                .build()
                )
                .baseUrl("https://192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2.创建用户服务
        var userService = retrofit.create(UserService::class.java)

        //3.创建用户登录命令
        var loginCall = userService.login("hn", 123456)

        //4.执行请求命令
        loginCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.v("MYTAG", "onResponse start...")
                Log.v("MYTAG", "body:" + response.body().toString())
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.v("MYTAG", "onFail start...")
                Log.v("MYTAG", "error:" + t.toString());
            }
        })
    }

    //  11.获取token
    //  11.使用token
    var token: Token? = null
    fun getToken(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient.Builder()
                                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                                .build()
                )
                .baseUrl("http://192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var commonService = retrofit.create(CommonService::class.java)

        var getTokenCall = commonService.getToken("hn", 123456)

        getTokenCall.enqueue(object : Callback<Token> {
            override fun onFailure(call: Call<Token>, t: Throwable) {
                Log.v("MYTAG", "onFail strat...")
                Log.v("MYTAG", "error:" + t.toString())
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                Log.v("MYTAG", "onResponse strat...")
                Log.v("MYTAG", "body:" + response.body().toString())
                token = response.body()
            }
        })
    }

    fun getUsers(view: View) {


    }

    //    12.RxJava的结合使用
    fun loginRxJava(view: View) {
        var retrofit = Retrofit.Builder()
                .client(
                        OkHttpClient().newBuilder()
                                .addInterceptor(
                                        HttpLoggingInterceptor().setLevel(
                                                HttpLoggingInterceptor.Level.BASIC
                                        )

                                )
                                .build()
                )
                .baseUrl("http://192.168.3.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

        var userService = retrofit.create(UserService::class.java)

        userService.loginRxlief("hn",123456)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose(object :Action {
                    override fun run() {
                        TODO("Not yet implemented")
                    }

                })
                .compose(lifecycleProvider.bindUntilEvent(Lifecycle.Event.ON_DESTROY))
                .subscribe(object : Observer<BaseResponse> {
                    override fun onComplete() {
                        TODO("Not yet implemented")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        TODO("Not yet implemented")
                    }

                    override fun onNext(t: BaseResponse?) {
                        TODO("Not yet implemented")
                    }

                    override fun onError(e: Throwable?) {
                        TODO("Not yet implemented")
                    }

                })
    }

    //    15.与Deferred的结合使用
    fun loginDeferred(view: View) {

    }

    //    16.与Kotlin协程的结合使用
    fun loginCoroutine(view: View) {

    }
}