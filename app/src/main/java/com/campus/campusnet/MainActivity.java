package com.campus.campusnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.campus.android_bind.util.ImageLoader;
import com.campus.william.net.model.IFile;
import com.campus.william.net.model.IObject;
import com.campus.william.net.model.IQuery;
import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.logic.RouterProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RouterProvider mRouterProvider;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(false);
            //设置系统栏设置颜色
            //tintManager.setTintColor(R.color.red);
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(R.color.topbar_bkg);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(R.color.topbar_bkg);
        }*/
        ImageLoader.getInstance().setListener(new ImageLoader.Listener() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Glide.with(MainActivity.this).load(url).into(imageView);
            }
        });
        initRouter();
    }

    private void initRouter(){
        mRouterProvider = new RouterProvider(android.R.id.content, getSupportFragmentManager());
        RouterFactory.getInstance().setRouterProvicer(mRouterProvider);
    }


    @Override
    public void onBackPressed() {
        if(mRouterProvider.back()){
            super.onBackPressed();
        }
    }
}
