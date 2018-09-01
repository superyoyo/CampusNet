package com.campus.william.router.logic;

import com.campus.william.router.ui.IFragment;

import java.util.HashMap;
import java.util.Iterator;

public class RouterFactory {
    private HashMap<String, Class<? extends IFragment>> mRouters;
    private RouterProvider mRouterProvider;
    private static class InstanceHolder{
        public static RouterFactory sInstance = new RouterFactory();
    }

    public static RouterFactory getInstance(){
        return InstanceHolder.sInstance;
    }

    private RouterFactory() {
        mRouters = new HashMap();
    }

    public void registe(Class<? extends IFragment> page, String state, String desc){
        mRouters.put(state, page);
    }

    public void setRouterProvicer(RouterProvider routerProvicer){
        if(routerProvicer == null){
            return;
        }
        if(mRouterProvider != null && mRouterProvider == routerProvicer){
            return;
        }
        mRouterProvider = routerProvicer;
        Iterator<String> iterator = mRouters.keySet().iterator();
        while (iterator.hasNext()){
            String state = iterator.next();
            Class<? extends IFragment> page = mRouters.get(state);
            mRouterProvider.registe(page, state, "");
        }
    }
}
