package com.campus.campusnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.campus.event_filter.request.IRequest;
import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.logic.RouterProvider;
import com.event_filter.logics.UserLogicMap;
import com.router.urls.UserRouterMap;

public class LoginActivity extends AppCompatActivity {
    private RouterProvider mRouterProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initRouter();
    }

    private void initRouter(){
        mRouterProvider = new RouterProvider(android.R.id.content, getSupportFragmentManager());
        RouterFactory.getInstance().setRouterProvicer(mRouterProvider);

        mRouterProvider.setState(UserRouterMap.States.LOGIN).navigate();
    }
}
