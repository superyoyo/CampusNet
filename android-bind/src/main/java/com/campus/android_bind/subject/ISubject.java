package com.campus.android_bind.subject;

/**
 * Created by jacklee on 17/1/12.
 */

public interface ISubject<T> {
    //注册观察者
    public void registe(IObserver<T> observer);

    //移除观察者
    public void remove(IObserver<T> observer);

    //移除所有的观察者
    public void removeAll();

    //进行事件更新
    public void notifyData(String tag, T value);

}
