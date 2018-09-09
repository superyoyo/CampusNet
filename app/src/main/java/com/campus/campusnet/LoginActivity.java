package com.campus.campusnet;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.campus.campusnet.model.User;
import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.response.IResponse;
import com.campus.william.router.logic.ActivityProvider;
import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.logic.RouterProvider;
import com.event_filter.logics.UserLogicMap;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private RouterProvider mRouterProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                App.getInstance().init();
                initRouter();
                queryUserInfo();
            }
        }, 700);
    }

    private void initRouter(){
        mRouterProvider = RouterFactory.getInstance().obtain(android.R.id.content, getSupportFragmentManager());
    }

    private void queryUserInfo(){
        //查询user的信息
        IRequest.obtain()
                .action("currentUser")
                .submit(MODE.CAMPUTATION, MODE.UI, new ICallback(){
                    @Override
                    public void done(IResponse response) {
                        super.done(response);
                        if(response.getException() == null){
                            String userStr = response.getString("data");
                            try {
                                User user = LoganSquare.parse(userStr, User.class);
                                App.getInstance().setUser(user);
                                finish();

                                ActivityProvider provider = RouterFactory.getInstance().obtainAcitivtyProvider();
                                provider.setState("MAIN")
                                        .setFlag(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .navigate(LoginActivity.this);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "登录过期", Toast.LENGTH_SHORT).show();
                                mRouterProvider.setState("LOGIN").navigate();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "登录过期", Toast.LENGTH_SHORT).show();
                            mRouterProvider.setState("LOGIN").navigate();
                        }
                    }
                });
    }
}
