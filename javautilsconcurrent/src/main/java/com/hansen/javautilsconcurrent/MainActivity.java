package com.hansen.javautilsconcurrent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.hansen.utils.LogUtil;

import java.util.concurrent.BlockingQueue;

public class MainActivity extends AppCompatActivity {
    private String TAG = getPackageName().getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    class Producer implements Runnable {
        private final BlockingQueue queue;
        Producer(BlockingQueue q) { queue = q; }
        @Override
        public void run() {
            try {
                while (true) { queue.put(produce()); }
            } catch (InterruptedException ex) { }
        }
        Object produce() {
            LogUtils.iTag(TAG,"produce");
            return "";
        };
    }

}
