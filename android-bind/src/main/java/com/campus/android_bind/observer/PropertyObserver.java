package com.campus.android_bind.observer;

import com.campus.android_bind.subject.IObserver;
import com.campus.android_bind.subject.ISubject;

/**
 * Created by superyoyo on 17/4/3.
 */

public class PropertyObserver implements IObserver {
    private String tag;

    public PropertyObserver() {
    }

    public PropertyObserver(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public void dataChange(ISubject subject, Object object) {

    }
}
