package com.hansen.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hansen.customview.activity.Recycle2Activity;
import com.hansen.customview.activity.RecyclerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void impRecyclerView(View view) {
        startActivity(new Intent(this, Recycle2Activity.class));
    }
}
