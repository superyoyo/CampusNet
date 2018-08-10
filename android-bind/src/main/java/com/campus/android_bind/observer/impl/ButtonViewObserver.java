package com.campus.android_bind.observer.impl;

import android.view.View;
import android.widget.Button;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;


/**
 * Created by jacklee on 17/1/12.
 */

public class ButtonViewObserver extends ViewObserver {


    public ButtonViewObserver(View view, NgModel ngModel, String property) {
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
        Button btn = (Button)getView();
        if(object.toString().equals(NgGo.NG_VISIBLE.VISIBLE)){
            btn.setVisibility(View.VISIBLE);
        }else if(object.toString().equals(NgGo.NG_VISIBLE.INVISIBLE)){
            btn.setVisibility(View.INVISIBLE);
        }else if(object.toString().equals(NgGo.NG_VISIBLE.GONE)){
            btn.setVisibility(View.GONE);
        }else{
            btn.setVisibility(View.VISIBLE);
            btn.setText(object + "");
        }
    }
}
