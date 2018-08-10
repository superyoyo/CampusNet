package com.campus.android_bind.observer.impl;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;
import com.campus.android_bind.util.ImageLoader;


/**
 * Created by jacklee on 17/1/12.
 */

public class ImageViewObserver extends ViewObserver {
    public ImageViewObserver(View view, NgModel ngModel, String perproty) {
        super(view, ngModel, perproty);
    }

    @Override
    public void initViewLogic(View view, NgModel ngModel, String property) {

    }

    @Override
    public void dataChange(ISubject subject, Object object) {
        if(object == null){
            return;
        }
        ImageView iv = (ImageView)getView();
        if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            iv.setVisibility(View.VISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.INVISIBLE)){
            iv.setVisibility(View.INVISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.GONE)){
            iv.setVisibility(View.GONE);
        }else{
            iv.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().loadImage(iv, object.toString());
        }
    }
}
