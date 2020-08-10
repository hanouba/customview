package com.hansen.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hansen.kotlindemo.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun start() {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun layoutId(): Int {
       return R.layout.activity_main
    }
}
