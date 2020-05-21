package com.hansen.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.view.View;

import com.hansen.customview.R;
import com.hansen.customview.expeandview.adapter.ImageAdapter;
import com.hansen.customview.expeandview.bean.ImageBean;
import com.hansen.customview.expeandview.itemdecoration.SimplePaddingDecoration;
import com.hansen.expend.bean.RecyclerViewData;
import com.hansen.expend.listener.OnRecyclerViewListener;
import com.hansen.recyclerlib.divideintogroups.Bean;
import com.hansen.recyclerlib.divideintogroups.RecyclerViewAdapter;
import com.hansen.recyclerlib.divideintogroups.StickHeaderDecoration;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity implements OnRecyclerViewListener.OnItemClickListener {
    private List<RecyclerViewData> mDatas;
    private RecyclerView mRecyclerView;
    private ImageAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);


        mRecyclerView = findViewById(R.id.rv_view);

        mDatas = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SimplePaddingDecoration(this));
        initImages();
        adapter = new ImageAdapter(this, mDatas);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void initImages() {
        mDatas = new ArrayList<>();
        List<ImageBean> bean1 = new ArrayList<>();
        List<ImageBean> bean2 = new ArrayList<>();
        List<ImageBean> bean3 = new ArrayList<>();
        // 每个子列表长度可以不相同
        bean1.add(new ImageBean("Dog", R.mipmap.dog));
        bean1.add(new ImageBean("Dog", R.mipmap.dog));
        bean2.add(new ImageBean("Cat", R.mipmap.cat));
        bean3.add(new ImageBean("Bird", R.mipmap.bird));

        mDatas.add(new RecyclerViewData("Dog", bean1, true));
        mDatas.add(new RecyclerViewData("Cat", bean2, true));
        mDatas.add(new RecyclerViewData("Bird", bean3, true));
    }

    @Override
    public void onGroupItemClick(int position, int groupPosition, View view) {

    }

    @Override
    public void onChildItemClick(int position, int groupPosition, int childPosition, View view) {

    }
}
