package com.hansen.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hansen.customview.R;
import com.hansen.recyclerlib.divideintogroups.Bean;
import com.hansen.recyclerlib.divideintogroups.RecyclerViewAdapter;
import com.hansen.recyclerlib.divideintogroups.StickHeaderDecoration;

import java.util.ArrayList;
import java.util.List;

public class Recycle2Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle2);

        mRecyclerView = findViewById(R.id.rv_list);


        List<Bean> beanList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            beanList.add(new Bean(String.format("第一组%d号", i + 1), "第一组"));
        }
        for (int i = 0; i < 6; i++) {
            beanList.add(new Bean(String.format("第二组%d号", i + 1), "第二组"));
        }
        for (int i = 0; i < 6; i++) {
            beanList.add(new Bean(String.format("第三组%d号", i + 1), "第三组"));
        }
        for (int i = 0; i < 10; i++) {
            beanList.add(new Bean(String.format("第四组%d号", i + 1), "第四组"));
        }

        mAdapter = new RecyclerViewAdapter(this, beanList);
        mRecyclerView.addItemDecoration(new StickHeaderDecoration(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
