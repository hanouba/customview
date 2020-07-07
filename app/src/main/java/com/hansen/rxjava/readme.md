https://www.jianshu.com/p/11b3ec672812
rxjava 学习
## 步骤1： 添加依赖
    dependencies {

    // Android 支持 Rxjava
    // 此处一定要注意使用RxJava2的版本
    compile 'io.reactivex.rxjava2:rxjava:2.0.1'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'

    // Android 支持 Retrofit
    compile 'com.squareup.retrofit2:retrofit:2.1.0'

    // 衔接 Retrofit & RxJava
    // 此处一定要注意使用RxJava2的版本
    compile 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'

    // 支持Gson解析
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'

    }
## 步骤2：创建 接收服务器返回数据 的类
    http://fy.iciba.com/ajax.php
    // URL实例
    http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world
## 步骤3：创建 用于描述网络请求 的接口