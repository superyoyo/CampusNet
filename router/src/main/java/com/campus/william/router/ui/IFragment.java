package com.campus.william.router.ui;

import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.campus.william.router.logic.RouterProvider;

import java.util.HashMap;

/**
 * Created by xm18012414 on 2018/2/5.
 */

public abstract class IFragment extends Fragment {
    protected RouterProvider routerProvider;

    public IFragment() {}

    public void setRouterProvider(RouterProvider provider) {
        this.routerProvider = provider;
    }

    public abstract void reInit(HashMap bundle);
}
