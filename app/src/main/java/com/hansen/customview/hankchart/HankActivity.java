package com.hansen.customview.hankchart;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.hansen.customview.R;

public class HankActivity extends AppCompatActivity   {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;
    private MenuItem menuItem4;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_hank);
        initView();

    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        initNavigationView();

        fragmentManager = getSupportFragmentManager();
    }

    private void initNavigationView() {
        //登录
        menuItem1 = navigationView.getMenu().findItem(R.id.nav_item1);
        menuItem2 = navigationView.getMenu().findItem(R.id.nav_item2);
        menuItem3 = navigationView.getMenu().findItem(R.id.nav_item3);
        menuItem4 = navigationView.getMenu().findItem(R.id.nav_item4);
        //通过actionbardrawertoggle将toolbar与drawablelayout关联起来
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.home_drawer_open, R.string.home_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //可以重新侧滑方法,该方法实现侧滑动画,整个布局移动效果
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = drawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };

        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        //设置图片为本身的颜色
        navigationView.setItemIconTintList(null);
        //设置item的点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                if (item == menuItem1) {
                    if (fragmentManager == null) {
                        fragmentManager = getSupportFragmentManager();
                    }
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, new LineFragment())
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item == menuItem2) {
                    if (fragmentManager == null) {
                        fragmentManager = getSupportFragmentManager();
                    }
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, new BarFragment())
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item == menuItem3) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item == menuItem4) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });

    }


}
