package com.campus.android_bind.observer.impl;

import android.view.View;
import android.widget.ProgressBar;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;


/**
 * Created by jacklee on 17/1/22.
 */

public class ProgressBarViewObserver extends ViewObserver {
    public ProgressBarViewObserver(View view, NgModel ngModel, String property) {
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
        ProgressBar pb = (ProgressBar)getView();
        if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            pb.setVisibility(View.VISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.INVISIBLE)){
            pb.setVisibility(View.INVISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.GONE)){
            pb.setVisibility(View.GONE);
        }else {
            pb.setVisibility(View.VISIBLE);
            if(object instanceof Integer){
                pb.setProgress((Integer)object);
            }
        }
    }
}
