package com.hansen.customview.flowlayout;

import android.os.Bundle;
import android.view.View;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hansen.customview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xiangcheng on 17/9/26.
 */

public class ProductActivity extends AppCompatActivity {
    private static final String TAG = ProductActivity.class.getSimpleName();
    //    private TextView suspension;
    protected RecyclerView productView;
    protected List<Product.Classify> classifies = new ArrayList<>();
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        productView = (RecyclerView) findViewById(R.id.product_view);
        productView.setLayoutManager(new LinearLayoutManager(this));

        classifies.add(new Product.Classify("颜色", Arrays.asList(new Product.Classify.Des("红色"),
                new Product.Classify.Des("白色"),
                new Product.Classify.Des("蓝色"),
                new Product.Classify.Des("橘黄色"),
                new Product.Classify.Des("格调灰"),
                new Product.Classify.Des("深色"),
                new Product.Classify.Des("咖啡色"))));
        classifies.add(new Product.Classify("尺寸", Arrays.asList(new Product.Classify.Des("180"),
                new Product.Classify.Des("175"),
                new Product.Classify.Des("170"),
                new Product.Classify.Des("165"),
                new Product.Classify.Des("160"),
                new Product.Classify.Des("155"),
                new Product.Classify.Des("150"))));
        classifies.add(new Product.Classify("款式",
                Arrays.asList(new Product.Classify.Des("男款"), new Product.Classify.Des("女款"),
                        new Product.Classify.Des("中年款"),
                        new Product.Classify.Des("潮流款"),
                        new Product.Classify.Des("儿童款"))));
        classifies.add(new Product.Classify("腰围", Arrays.asList(new Product.Classify.Des("26"),
                new Product.Classify.Des("27"),
                new Product.Classify.Des("28"),
                new Product.Classify.Des("29"),
                new Product.Classify.Des("30"),
                new Product.Classify.Des("31"),
                new Product.Classify.Des("32"),
                new Product.Classify.Des("33"),
                new Product.Classify.Des("34"),
                new Product.Classify.Des("35"))));
        classifies.add(new Product.Classify("肩宽", Arrays.asList(new Product.Classify.Des("26"),
                new Product.Classify.Des("27"),
                new Product.Classify.Des("28"),
                new Product.Classify.Des("29"),
                new Product.Classify.Des("30"),
                new Product.Classify.Des("31"),
                new Product.Classify.Des("32"),
                new Product.Classify.Des("33"),
                new Product.Classify.Des("34"),
                new Product.Classify.Des("35"))));
        classifies.add(new Product.Classify("臂长", Arrays.asList(new Product.Classify.Des("26"),
                new Product.Classify.Des("27"),
                new Product.Classify.Des("28"),
                new Product.Classify.Des("29"),
                new Product.Classify.Des("30"),
                new Product.Classify.Des("31"),
                new Product.Classify.Des("32"),
                new Product.Classify.Des("33"),
                new Product.Classify.Des("34"),
                new Product.Classify.Des("35"))));
        productAdapter = new ProductAdapter(this,classifies);
        productView.setAdapter(productAdapter);
    }


    public void reset(View v) {
        ToastUtils.showShort("重置");


        for (int i = 0; i < classifies.size(); i++) {
            int size = classifies.get(i).des.size();
            for (int j = 0; j < size; j++) {
               classifies.get(i).des.get(j).setSelect(false);

            }
        }

        productAdapter.notifyDataSetChanged();
    }

    public void ensure(View v) {

        for (int i = 0; i < classifies.size(); i++) {
            int size = classifies.get(i).des.size();
            for (int j = 0; j < size; j++) {
                boolean isSelect = classifies.get(i).des.get(j).isSelect;
                if (isSelect) {
                   String selected =  classifies.get(i).des.get(j).des;
                    LogUtils.d(TAG,"选择的设备"+selected);
                }
            }
        }


        ToastUtils.showShort("确定");
    }
}
