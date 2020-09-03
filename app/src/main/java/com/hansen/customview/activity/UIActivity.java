package com.hansen.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.internal.schedulers.NewThreadWorker;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hansen.customview.R;
import com.hansen.customview.view.SimleAdapter;

import java.util.ArrayList;
import java.util.List;

public class UIActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i);
        listView = findViewById(R.id.list_view);

        for (int i = 0; i < 10; i++) {
            list.add("我的"+i);
        }

        SimleAdapter simpleAdapter = new SimleAdapter(this,list);
        listView.setAdapter(simpleAdapter);
    }
}