package com.campus.android_bind.observer.impl;

import android.view.View;
import android.widget.TextView;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;


/**
 * Created by jacklee on 17/1/12.
 */

public class TextViewObserver extends ViewObserver {


    public TextViewObserver(View view, NgModel ngModel, String property) {
        super(view, ngModel, property);
    }

    @Override
    public void initViewLogic(View view, NgModel ngModel, String property) {

    }

    @Override
    public void dataChange(ISubject subject, Object object) {
        if(object == null){
            return;
        }
        TextView tv = (TextView)getView();
        if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            tv.setVisibility(View.VISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.INVISIBLE)){
            tv.setVisibility(View.INVISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.GONE)){
            tv.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.VISIBLE);
            tv.setText(object + "");
        }
    }
}
