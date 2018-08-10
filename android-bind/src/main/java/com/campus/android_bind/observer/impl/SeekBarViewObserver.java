package com.campus.android_bind.observer.impl;

import android.view.View;
import android.widget.SeekBar;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;


/**
 * Created by jacklee on 17/1/22.
 */

public class SeekBarViewObserver extends ViewObserver {
    private boolean not_change;
    public SeekBarViewObserver(View view, final NgModel ngModel, String property) {
        super(view, ngModel, property);
    }

    @Override
    public void initViewLogic(View view, NgModel ngModel, String property) {
        SeekBar sb = (SeekBar)view;
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                not_change = true;
                if(ngModel.getValue(property) instanceof Integer){
                    ngModel.addParams(property, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void dataChange(ISubject subject, Object object) {
        if(not_change){
            not_change = false;
            return;
        }
        if(object == null){
            return;
        }
        SeekBar sb = (SeekBar)getView();
        if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            sb.setVisibility(View.VISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            sb.setVisibility(View.INVISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.GONE)){
            sb.setVisibility(View.GONE);
        }else{
            sb.setVisibility(View.VISIBLE);
            if(object instanceof Integer){
                sb.setProgress((Integer)object);
            }
        }
    }
}
