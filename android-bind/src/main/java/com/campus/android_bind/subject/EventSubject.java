package com.campus.android_bind.subject;

import android.util.Log;
import android.view.View;

import com.campus.android_bind.bean.NgBindTag;
import com.campus.android_bind.observer.AllPropertyObserver;
import com.campus.android_bind.observer.PropertyObserver;
import com.campus.android_bind.observer.ViewObserver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jacklee on 17/1/12.
 */

public class EventSubject implements ISubject{
    private ArrayList<IObserver> observers;

    public EventSubject() {
        observers = new ArrayList<>();
    }

    @Override
    public void registe(IObserver observer) {
        for(IObserver iObserver : observers){
            if(observer.equals(iObserver)){
                //已经注册过，不再注册
                return;
            }
        }
        //还没有注册过，现在进行注册
        observers.add(observer);
    }

    @Override
    public void remove(IObserver observer) {
        for(IObserver iObserver : observers){
            if(observer.equals(iObserver)){
                //找到了已经注册过的观察者，将其移除
                observers.remove(iObserver);
                return;
            }
        }
    }

    @Override
    public void removeAll() {
        observers.clear();
    }

    @Override
    public void notifyData(String tag, Object value) {
        for(IObserver iObserver : observers){
            if(iObserver instanceof ViewObserver){
                //获取对应的view
                String property = ((ViewObserver)iObserver).getProperty();
                if(!property.contains(tag)){
                    continue;
                }
                //通知这个viewObserver去做出相应的改变
                iObserver.dataChange(this, value);
            }else if(iObserver instanceof PropertyObserver){
                if(tag.equals(((PropertyObserver) iObserver).getTag())){
                    HashMap param = new HashMap();
                    param.put("tag", tag);
                    param.put("value", value);
                    iObserver.dataChange(this, param);
                }
            }else if(iObserver instanceof AllPropertyObserver){
                ((AllPropertyObserver)iObserver).dataChange(tag, value);
            }

        }
    }

}
