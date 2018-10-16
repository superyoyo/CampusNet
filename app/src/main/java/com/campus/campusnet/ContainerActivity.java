package com.campus.campusnet;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.campus.campusnet.model.User;
import com.campus.william.annotationprocessor.annotation.ActivityUrl;
import com.campus.william.router.logic.RouterFactory;
import com.campus.william.router.logic.RouterProvider;
import com.readystatesoftware.systembartint.SystemBarTintManager;

@ActivityUrl(url = "CONTAINER")
public class ContainerActivity extends AppCompatActivity {
    private RouterProvider mRouterProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mRouterProvider = RouterFactory.getInstance()
                .obtain(android.R.id.content, getSupportFragmentManager());

        dealAction();
    }

    private void dealAction() {
        Bundle bundle = getIntent().getBundleExtra("data");
        if (TextUtils.isEmpty(bundle.getString("action"))) {
            finish();
            return;
        }
        String action = bundle.getString("action");
        switch (action) {
            case "studentInfoPage":
                mRouterProvider.setState("studentInfoPage")
                        .addParams("userId", bundle.getString("userId"))
                        .addParams("studentId", bundle.getString("studentId"))
                        .navigate();
                break;
            case "userInfoPage":
                User user = App.getInstance().currentUser();
                mRouterProvider.setState("EditUserInfo")
                        .addParams("userId", user.getUserId())
                        .navigate();
                break;
            case "studentAuthPage":
                mRouterProvider.setState("studentAuthPage")
                        .navigate();
                break;
        }
    }
}
