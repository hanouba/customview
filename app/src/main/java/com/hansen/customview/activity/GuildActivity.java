package com.hansen.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hansen.customview.MainActivity;
import com.hansen.customview.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class GuildActivity extends AppCompatActivity {
    private Banner banner;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild);
        banner = findViewById(R.id.guide_banner);
        textView = findViewById(R.id.tv_start);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.mipmap.banner1);
        imageList.add(R.mipmap.banner2);
        imageList.add(R.mipmap.banner3);

        banner.setAdapter(new BannerImageAdapter<Integer>(imageList) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                //图片加载自己实现
                Glide.with(GuildActivity.this).load(data).into(holder.imageView);

            }
        })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this));


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuildActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}