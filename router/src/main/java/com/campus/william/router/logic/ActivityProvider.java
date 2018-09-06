package com.campus.william.router.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.campus.william.router.ui.IFragment;

import java.util.HashMap;

/**
 * Created by william.liu on 2018/9/6.
 */

public class ActivityProvider{
    private HashMap<String, Class<? extends Activity>> mActivities;
    private String mState;
    private Bundle mBundle;
    private int mFlags;

    protected ActivityProvider() {
        mActivities = new HashMap<>();
    }

    
    protected ActivityProvider registe(Class<? extends Activity> activity, String state) {
        mActivities.put(state, activity);
        return this;
    }

    
    public ActivityProvider setState(String state) {
        mState = state;
        return this;
    }

    
    public ActivityProvider setFlag(int flag) {
        mFlags = flag;
        return this;
    }

    
    public ActivityProvider setData(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    
    public void navigate(Context context) {
        if(TextUtils.isEmpty(mState)){
            //"状态码不能为空！"
            return;
        }

        if(!mActivities.containsKey(mState)){
            //"没有找到此状态，请确认此状态是否在RouterProvider中注册过？"
            return;
        }

        Class<? extends Activity> clazz = mActivities.get(mState);
        Intent intent = new Intent(context, clazz);
        intent.putExtra("data", mBundle);
        intent.addFlags(mFlags);
        context.startActivity(intent);
    }
}
