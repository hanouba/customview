package com.hansen.databinding

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hansen.customview.R
import com.hansen.customview.databinding.ActivityMainA4ctivityBinding

class MainA4ctivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainA4ctivityBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main_a4ctivity)


    }
}