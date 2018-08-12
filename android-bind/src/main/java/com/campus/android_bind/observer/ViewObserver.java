package com.campus.android_bind.observer;

import android.view.View;

import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.subject.IObserver;
import com.campus.android_bind.subject.ISubject;

/**
 * Created by jacklee on 17/1/12.
 *
 * 该类主要是对ngModle的改变，做出对应的UI操作，
 * 写为抽象类，是为了不同的UI组件再相应同一个属性值时，表现不同。
 */

public abstract class ViewObserver implements IObserver {
    private View mView;
    private NgModel mNgModel;
    private String mProperty;

    public View getView() {
        return mView;
    }

    public ViewObserver(View view, NgModel ngModel, String property) {
        mView = view;
        mNgModel = ngModel;
        mProperty = property;
        initViewLogic(mView, mNgModel, mProperty);
        if(mNgModel != null && mNgModel.getValue(mProperty) != null){
            dataChange(mNgModel, mNgModel.getValue(property));
        }
    }

    public void reInit(View view, NgModel ngModel, String property) {
        mView = view;
        mNgModel = ngModel;
        mProperty = property;
        initViewLogic(mView, mNgModel, mProperty);
    }

    public NgModel getNgModel() {
        return mNgModel;
    }

    public String getProperty() {
        return mProperty;
    }

    protected void setView(View view) {
        mView = view;
    }

    @Override
    public abstract void dataChange(ISubject subject, Object object);

    public abstract void initViewLogic(View view, NgModel ngModel, String property);
}
