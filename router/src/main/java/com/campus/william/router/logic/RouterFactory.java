package com.campus.william.router.logic;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.campus.william.router.ui.IFragment;

import java.util.HashMap;
import java.util.Iterator;

public class RouterFactory {
    private HashMap<String, Class<? extends IFragment>> mRouters;
    private HashMap<String, Class<? extends Activity>> mActivitys;
    private RouterProvider mRouterProvider;
    private ActivityProvider mActivityProvider;
    private static class InstanceHolder{
        public static RouterFactory sInstance = new RouterFactory();
    }

    public static RouterFactory getInstance(){
        return InstanceHolder.sInstance;
    }

    private RouterFactory() {
        mRouters = new HashMap();
        mActivityProvider = new ActivityProvider();
    }

    public void registeFragment(Class<? extends IFragment> page, String state, String desc){
        mRouters.put(state, page);
    }

    public void registeActivity(Class<? extends Activity> activity, String state){
        mActivityProvider.registe(activity, state);
    }

    public RouterProvider obtain(int containerId, FragmentManager fragmentManager){
        if(mRouterProvider != null){
            if(mRouterProvider.mContainerId == containerId
                    && mRouterProvider.mFragmentManager == fragmentManager){
                return mRouterProvider;
            }
            mRouterProvider.release();
        }

        mRouterProvider = new RouterProvider(containerId, fragmentManager);
        Iterator<String> iterator = mRouters.keySet().iterator();
        while (iterator.hasNext()){
            String state = iterator.next();
            Class<? extends IFragment> page = mRouters.get(state);
            mRouterProvider.registe(page, state, "");
        }
        return mRouterProvider;
    }

    public ActivityProvider obtainAcitivtyProvider(){
        return mActivityProvider;
    }
}
