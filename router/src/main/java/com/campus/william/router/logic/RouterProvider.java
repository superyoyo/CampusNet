package com.campus.william.router.logic;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import com.campus.william.router.ui.IFragment;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by xm18012414 on 2018/2/5.
 */

public class RouterProvider{
    private int containerId;
    private FragmentManager fragmentManager;
    private Stack<IFragment> stack;//目前内存中的fragment
    private HashMap<String, Class<? extends IFragment>> stateMap;//状态映射表
    public RouterProvider(@IdRes int containerId, FragmentManager fragmentManager){
        this.containerId = containerId;
        this.fragmentManager = fragmentManager;
        stack = new Stack<>();
        stateMap = new HashMap<>();
    }


    public RouterProvider registe(Class<? extends IFragment> fragment, String state, String desc) {
        stateMap.put(state, fragment);
        return this;
    }


    public void setState(RouterParams routerParams){
        if(TextUtils.isEmpty(routerParams.getState())){
            //"状态码不能为空！"
            return;
        }

        if(!stateMap.containsKey(routerParams.getState())){
            //"没有找到此状态，请确认此状态是否在RouterProvider中注册过？"
            return;
        }

        Class<? extends IFragment> clazz = stateMap.get(routerParams.getState());
        IFragment fragment = null;
        if(routerParams.getLaunchMode() == RouterParams.LAUNCH_MODE.standard){
            try {
                fragment = clazz.newInstance();
                Log.d("liuji", "instance:" + fragment);
                fragment.setRouterProvider(this);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if(stack.size() > 0){
                    transaction.hide(stack.peek());
                }
                transaction.add(containerId, fragment);
                if(routerParams.getAnimation() != null){
                    transaction.setCustomAnimations(routerParams.getAnimation()[0], routerParams.getAnimation()[1]);
                }
                transaction.commit();
                stack.push(fragment);
                fragment.reInit(routerParams.getBundle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(routerParams.getLaunchMode() == RouterParams.LAUNCH_MODE.singleTask){
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

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.hide(top);
                transaction.show(stack.peek());
                if(routerParams.getAnimation() != null){
                    transaction.setCustomAnimations(routerParams.getAnimation()[0], routerParams.getAnimation()[1]);
                }
                transaction.commit();
            }else{
                try {
                    fragment = clazz.newInstance();
                    fragment.setRouterProvider(this);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if(stack.size() > 0){
                        transaction.hide(stack.peek());
                    }
                    transaction.add(containerId, fragment);
                    if(routerParams.getAnimation() != null){
                        transaction.setCustomAnimations(routerParams.getAnimation()[0], routerParams.getAnimation()[1]);
                    }
                    transaction.commit();
                    stack.push(fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fragment.reInit(routerParams.getBundle());
        }else if(routerParams.getLaunchMode() == RouterParams.LAUNCH_MODE.singleTop){
            if(stack.size() > 0 && stack.peek().getClass().getName().equals(clazz.getName())){
                //栈顶是此fragment
                fragment = stack.peek();
            }else{
                //栈顶不是此fragment
                try {
                    fragment = clazz.newInstance();
                    fragment.setRouterProvider(this);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if(stack.size() > 0){
                        transaction.hide(stack.peek());
                    }
                    transaction.add(containerId, fragment);
                    if(routerParams.getAnimation() != null){
                        transaction.setCustomAnimations(routerParams.getAnimation()[0], routerParams.getAnimation()[1]);
                    }
                    transaction.commit();
                    stack.push(fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fragment.reInit(routerParams.getBundle());
        }else{

        }


    }

    public void sendMessage(String state, HashMap params) {

    }


    public void sendMessage(Class<? extends IFragment> fragment, HashMap params) {

    }


    public boolean back() {
        if(stack.size() == 0){
            return true;
        }
        IFragment fragment = stack.peek();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);

        stack.remove(stack.size() - 1);
        if(stack.size() > 0){
            transaction.show(stack.peek());
        }

        transaction.commit();

        return false;
    }
}
