package com.hansen.customview.mpandroidchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hansen.customview.R;
import com.hansen.customview.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class SmallChartActivity extends AppCompatActivity {
    private RecyclerView mRecycler;
    private List<String> mDatas = new ArrayList<>();
    private HistoryDeviceAdapter deviceAdapter;
    private RelativeLayout rl_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_chart);


        mRecycler = findViewById(R.id.rl_view);
        rl_title = findViewById(R.id.rl_title);

        StatusBarUtil.setTranslucentForImageView(this,0,rl_title);
        mRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecycler.addItemDecoration(new SpaceItem(0,5,0,5));
        for (int i = 0; i < 4; i++) {
            mDatas.add("设备名称"+i);
        }

        deviceAdapter = new HistoryDeviceAdapter(mDatas,this);
        mRecycler.setAdapter(deviceAdapter);

    }

    public class  SpaceItem  extends RecyclerView.ItemDecoration {
        private int left,top,right,bottom;


        public SpaceItem(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top = top;
                outRect.bottom = bottom;
                outRect.left = left;
                outRect.right = right;
            } else {
                outRect.top = top;
                outRect.bottom = bottom;
                outRect.left = left;
                outRect.right = right;
            }
        }
        }


}