package com.hansen.customview;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hansen.customview.greendao.DaoMaster;
import com.hansen.customview.greendao.DaoSession;
import com.hansen.customview.greendao.VideoDateInfoBeanDao;
import com.hansen.customview.hankchart.HankActivity;
import com.hansen.customview.mpandroidchart.GreenHelper;
import com.hansen.customview.mpandroidchart.JNChartActivity;
import com.hansen.customview.mpandroidchart.MpAndroidActivity;
import com.hansen.customview.mpandroidchart.VideoRateActivity;
import com.hansen.customview.test.TestActivity;
import com.hansen.rxjava.RxjavaActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


public class MainActivity extends AppCompatActivity {


    private VideoDateInfoBeanDao videoDateInfoBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = new TextView(this);


        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』

                        }
                    }
                });


    }



    public void impRecyclerView(View view) {
        startActivity(new Intent(this, VideoRateActivity.class));
    }

    public void toHelloChart(View view) {
        startActivity(new Intent(this, JNChartActivity.class));
    }


    public void toHankChart(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void toRxjava(View view) {
        startActivity(new Intent(this, RxjavaActivity.class));
    }
}
