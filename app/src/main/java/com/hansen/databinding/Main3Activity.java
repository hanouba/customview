package com.hansen.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import databinding.Main3ActivityBinding;


import android.os.Bundle;

import com.hansen.customview.BR;
import com.hansen.customview.R;
import com.hansen.databinding.bean.Goods;
import com.hansen.launcher.AHansen;

import java.util.Random;

public class Main3Activity extends AppCompatActivity {
    private Main3ActivityBinding main3ActivityBinding;
    private Goods goods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main3ActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main3);

         goods = new Goods("掌上","很贵",24f);
        main3ActivityBinding.setGoods(goods);
        main3ActivityBinding.setGoodsHandler(new GoodsHandler());

        goods.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.name) {
                    AHansen.logger.info("","name");
                }else if (propertyId == BR.details){
                    AHansen.logger.info("","details");
                }else if (propertyId == BR.price) {
                    AHansen.logger.info("","price");
                }
            }
        });
    }

    public class GoodsHandler {

        public void changeGoodsName() {
            goods.setName("code" + new Random().nextInt(100));
            goods.setPrice(new Random().nextInt(100));
        }

        public void changeGoodsDetails() {
            goods.setDetails("hi" + new Random().nextInt(100));
            goods.setPrice(new Random().nextInt(100));
        }

    }
}