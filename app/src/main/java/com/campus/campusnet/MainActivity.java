package com.campus.campusnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.campus.android_bind.util.ImageLoader;
import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;
import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.logic.RouterProvider;
import com.event_filter.logics.UserLogicMap;
import com.router.urls.AppRouterMap;

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

        mRouterProvider.setState(AppRouterMap.States.LOGIN)
                .setLaunchMode(RouterProvider.LAUNCH_MODE.standard)
                .withAnimation(R.anim.anim_entry_from_bottom, R.anim.anim_leave_from_bottom)
                .navigate();
    }

    public void registe(View view) {
        IRequest.obtain()
                .action(UserLogicMap.Actions.user_LoginLogic)
                .add("name", "william")
                .add("sex", "male")
                .next(MODE.CAMPUTATION, MODE.CAMPUTATION, new ICallback() {
                    @Override
                    public IRequest next(IResponse response) {
                        if(response.getException() != null){

                            return null;
                        }

                        IRequest request = IRequest.obtain()
                                .action("")
                                .initParams(response.getData())
                                .add("account", response.getString("account"))
                                .add("password", response.getString("password"));
                        return request;
                    }
                }).next(MODE.IO, MODE.UI, new ICallback() {
                    @Override
                    public IRequest next(IResponse response) {
                        if(response.getException() == null){
                            Toast.makeText(getApplicationContext()
                                    ,"name:" + response.getString("name") + " account:" + response.getString("account")
                                    , Toast.LENGTH_SHORT)
                                    .show();
                        }
                        return null;
                    }
                }).submit();
    }

    @Override
    public void onBackPressed() {
        if(mRouterProvider.back()){
            super.onBackPressed();
        }
    }
}
