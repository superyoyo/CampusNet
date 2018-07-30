package com.campus.william.router.logic;

import android.os.Bundle;

import java.util.HashMap;

/**
 * Created by xm18012414 on 2018/2/9.
 */

public class RouterParams {
    private String state;//要跳转的目标地址
    private int launchMode = LAUNCH_MODE.standard;
    private HashMap bundle;//数据
    private int[] animation;

    public RouterParams setLaunchMode(int launchMode){
        if(launchMode > LAUNCH_MODE.singleInstance || launchMode < LAUNCH_MODE.standard){
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

    public static class LAUNCH_MODE{
        public final static int standard = 1;
        public final static int singleTop = 2;
        public final static int singleTask = 3;
        public final static int singleInstance = 4;
    }
}
