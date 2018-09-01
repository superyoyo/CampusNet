package com.campus.campusnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.logic.RouterProvider;

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


    }
}
