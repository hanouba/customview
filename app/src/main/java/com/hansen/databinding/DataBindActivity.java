package com.hansen.databinding;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import databinding.UserBind;

import android.view.View;

import com.hansen.customview.R;
import com.hansen.databinding.bean.User;

public class DataBindActivity extends AppCompatActivity {
    private UserBind userBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_bind);
        userBind = DataBindingUtil.setContentView(this, R.layout.activity_data_bind);
        User user = new User("zhang三","12312131");
        userBind.setUserinfo(user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void toActivity3(View view) {
        Intent intent = new Intent(this,Main3Activity.class);
        startActivity(intent);
    }
}