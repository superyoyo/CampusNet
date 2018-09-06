package com.campus.william.router.logic;

import java.util.HashMap;

/**
 * Created by william.liu on 2018/9/6.
 */

public class RouterParams {
    private String state;//要跳转的目标地址
    private int launchMode = RouterProvider.LAUNCH_MODE.standard;
    private HashMap bundle;//数据
    private int[] animation;

    public RouterParams setLaunchMode(int launchMode){
        if(launchMode > RouterProvider.LAUNCH_MODE.singleInstance || launchMode < RouterProvider.LAUNCH_MODE.standard){
            launchMode = 1;
        }else {
            this.launchMode = launchMode;
        }

        return this;
    }

    public RouterParams addParams(String key, Object value){
        if(bundle == null){
            bundle = new HashMap();
        }
        bundle.put(key, value);
        return this;
    }

    public RouterParams withAnimation(int enter, int exit){
        animation = new int[]{enter, exit};
        return this;
    }

    public RouterParams setState(String state) {
        this.state = state;
        return this;
    }

    public String getState() {
        return state;
    }

    public int getLaunchMode() {
        return launchMode;
    }

    public HashMap getBundle() {
        return bundle;
    }

    public int[] getAnimation() {
        return animation;
    }

    public void clear(){
        state = null;
        launchMode = RouterProvider.LAUNCH_MODE.standard;
        if(bundle != null){
            bundle.clear();
        }
        animation = null;
    }
}
