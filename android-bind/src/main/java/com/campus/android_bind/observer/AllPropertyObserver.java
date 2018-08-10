package com.campus.android_bind.observer;

import com.campus.android_bind.subject.IObserver;
import com.campus.android_bind.subject.ISubject;

/**
 * Created by liuji on 17/8/17.
 */

public class AllPropertyObserver implements IObserver {

    @Deprecated
    @Override
    public void dataChange(ISubject subject, Object object) {

    }

    public void dataChange(String tag, Object object){};
}
