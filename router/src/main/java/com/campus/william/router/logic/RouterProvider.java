package com.campus.william.router.logic;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.campus.william.router.ui.IFragment;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by xm18012414 on 2018/2/5.
 */

public class RouterProvider{
    protected int mContainerId;
    protected FragmentManager mFragmentManager;
    private Stack<IFragment> stack;//目前内存中的fragment
    private HashMap<String, Class<? extends IFragment>> stateMap;//状态映射表
    private RouterParams mRouterParams;
    private int mStatusBarHeight;

    public RouterProvider(@IdRes int containerId, FragmentManager fragmentManager){
        mContainerId = containerId;
        mFragmentManager = fragmentManager;
        stack = new Stack<>();
        stateMap = new HashMap<>();
        mRouterParams = new RouterParams();
    }

    public void setStatusBarHeight(int statusBarHeight){
        mStatusBarHeight = statusBarHeight;
    }

    public int getStatusBarHeight(){
        return mStatusBarHeight;
    }


    public RouterProvider registe(Class<? extends IFragment> fragment, String state, String desc) {
        stateMap.put(state, fragment);
        return this;
    }

    public RouterProvider setState(String state){
        mRouterParams.setState(state);
        return this;
    }

    public RouterProvider setLaunchMode(int launchMode){
        mRouterParams.setLaunchMode(launchMode);
        return this;
    }

    public RouterProvider addParams(String key, Object value){
        mRouterParams.addParams(key, value);
        return this;
    }

    public RouterProvider withAnimation(int enter, int exit){
        mRouterParams.withAnimation(enter, exit);
        return this;
    }

    public void navigate(){
        if(TextUtils.isEmpty(mRouterParams.getState())){
            //"状态码不能为空！"
            return;
        }

        if(!stateMap.containsKey(mRouterParams.getState())){
            //"没有找到此状态，请确认此状态是否在RouterProvider中注册过？"
            return;
        }

        Class<? extends IFragment> clazz = stateMap.get(mRouterParams.getState());
        IFragment fragment = null;
        if(mRouterParams.getLaunchMode() == LAUNCH_MODE.standard){
            try {
                fragment = clazz.newInstance();
                Log.d("liuji", "instance:" + fragment);
                fragment.setRouterProvider(this);
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if(stack.size() > 0){
                    transaction.hide(stack.peek());
                }
                transaction.add(mContainerId, fragment);
                if(mRouterParams.getAnimation() != null){
                    transaction.setCustomAnimations(mRouterParams.getAnimation()[0]
                            , mRouterParams.getAnimation()[1]
                            , mRouterParams.getAnimation()[0]
                            , mRouterParams.getAnimation()[1]);
                }
                transaction.commit();
                stack.push(fragment);
                fragment.reInit(mRouterParams.getBundle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(mRouterParams.getLaunchMode() == LAUNCH_MODE.singleTask){
            //判断栈中是否有此fragment
            boolean find = false;
            for(int i = 0, n = stack.size(); i < n; i++){
                if(stack.get(i).getClass().getName().equals(clazz.getName())){
                    //栈中有此元素，将栈顶元素都移除出去
                    find = true;
                    break;
                }
            }

            if(find){
                IFragment top = stack.peek();
                for(int i = 0, n = stack.size(); i < n; i++){
                    if(!stack.get(i).getClass().getName().equals(clazz.getName())){
                        //栈中有此元素，将栈顶元素都移除出去
                        stack.remove(stack.size() - 1);
                    }else{
                        break;
                    }
                }

                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.hide(top);
                transaction.show(stack.peek());
                if(mRouterParams.getAnimation() != null){
                    transaction.setCustomAnimations(mRouterParams.getAnimation()[0], mRouterParams.getAnimation()[1]);
                }
                transaction.commit();
            }else{
                try {
                    fragment = clazz.newInstance();
                    fragment.setRouterProvider(this);

                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    if(stack.size() > 0){
                        transaction.hide(stack.peek());
                    }
                    transaction.add(mContainerId, fragment);
                    if(mRouterParams.getAnimation() != null){
                        transaction.setCustomAnimations(mRouterParams.getAnimation()[0], mRouterParams.getAnimation()[1]);
                    }
                    transaction.commit();
                    stack.push(fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fragment.reInit(mRouterParams.getBundle());
        }else if(mRouterParams.getLaunchMode() == LAUNCH_MODE.singleTop){
            if(stack.size() > 0 && stack.peek().getClass().getName().equals(clazz.getName())){
                //栈顶是此fragment
                fragment = stack.peek();
            }else{
                //栈顶不是此fragment
                try {
                    fragment = clazz.newInstance();
                    fragment.setRouterProvider(this);

                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    if(stack.size() > 0){
                        transaction.hide(stack.peek());
                    }
                    transaction.add(mContainerId, fragment);
                    if(mRouterParams.getAnimation() != null){
                        transaction.setCustomAnimations(mRouterParams.getAnimation()[0], mRouterParams.getAnimation()[1]);
                    }
                    transaction.commit();
                    stack.push(fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fragment.reInit(mRouterParams.getBundle());
        }else{

        }

        mRouterParams.clear();
    }

    public void sendMessage(String state, HashMap params) {

    }


    public void sendMessage(Class<? extends IFragment> fragment, HashMap params) {

    }


    public boolean back() {
        if(stack.size() <= 1){
            return true;
        }
        IFragment fragment = stack.peek();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(fragment);

        stack.remove(stack.size() - 1);
        if(stack.size() > 0){
            transaction.show(stack.peek());
        }

        transaction.commit();

        return false;
    }

    public void release(){
        if(stack != null){
            stack.clear();
        }
        if(stateMap != null){
            stateMap.clear();
        }
        mRouterParams = null;
        stack = null;
        stateMap = null;
    }

    public static class LAUNCH_MODE{
        public final static int standard = 1;
        public final static int singleTop = 2;
        public final static int singleTask = 3;
        public final static int singleInstance = 4;
    }

}
