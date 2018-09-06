package com.campus.campusnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.campus.event_filter.request.IRequest;
import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.logic.RouterProvider;
import com.event_filter.logics.UserLogicMap;

public class LoginActivity extends AppCompatActivity {
    private RouterProvider mRouterProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initRouter();
    }

    private void initRouter(){
        mRouterProvider = RouterFactory.getInstance().obtain(android.R.id.content, getSupportFragmentManager());
        mRouterProvider.setState("LOGIN").navigate();
    }

    private void queryUserInfo(){
        //查询user的信息
    }
}
