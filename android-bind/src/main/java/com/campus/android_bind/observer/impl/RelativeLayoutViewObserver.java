package com.campus.android_bind.observer.impl;

import android.view.View;
import android.widget.RelativeLayout;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;

/**
 * Created by superyoyo on 17/4/16.
 */

public class RelativeLayoutViewObserver extends ViewObserver {
    public RelativeLayoutViewObserver(View view, NgModel ngModel, String property) {
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
        RelativeLayout rl = (RelativeLayout) getView();
        if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            rl.setVisibility(View.VISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.INVISIBLE)){
            rl.setVisibility(View.INVISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.GONE)){
            rl.setVisibility(View.GONE);
        }else {
            rl.setVisibility(View.VISIBLE);
        }
    }
}
