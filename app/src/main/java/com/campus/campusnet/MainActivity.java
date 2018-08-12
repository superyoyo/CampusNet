package com.campus.campusnet;

import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.campus.android_bind.util.ImageLoader;
import com.campus.campusnet.ui.page.LoginFragment;
import com.campus.campusnet.ui.page.RegisteFragment;
import com.campus.campusnet.ui.state.NavState;
import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;
import com.campus.william.router.logic.RouterParams;
import com.campus.william.router.logic.RouterProvider;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends AppCompatActivity {

    @Override
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
        RouterProvider routerProvider = new RouterProvider(android.R.id.content, getSupportFragmentManager());
        routerProvider.registe(LoginFragment.class, NavState.LOGIN, "登录");
        routerProvider.registe(RegisteFragment.class, NavState.REGISTE, "注册");

        routerProvider.setState(new RouterParams().setState(NavState.LOGIN).setLaunchMode(RouterParams.LAUNCH_MODE.standard));
    }

    public void registe(View view) {
        IRequest.obtain()
                .action(1)
                .add("name", "william")
                .add("sex", "male")
                .next(MODE.CAMPUTATION, MODE.CAMPUTATION, new ICallback() {
                    @Override
                    public IRequest next(IResponse response) {
                        if(response.getException() != null){

                            return null;
                        }

                        IRequest request = IRequest.obtain()
                                .action(3)
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

}
