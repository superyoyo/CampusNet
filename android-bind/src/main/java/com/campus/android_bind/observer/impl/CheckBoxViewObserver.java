package com.campus.android_bind.observer.impl;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;

/**
 * Created by jacklee on 17/1/12.
 */

public class CheckBoxViewObserver extends ViewObserver {
    private boolean not_change;
    public CheckBoxViewObserver(View view, final NgModel ngModel, final String property) {
        super(view, ngModel, property);
    }

    @Override
    public void initViewLogic(View view, NgModel ngModel, String property) {
        CheckBox checkBox = (CheckBox)view;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                not_change = true;
                if(ngModel.getValue(property) instanceof Boolean){
                    ngModel.addParams(property, isChecked);
                }else{
                    ngModel.addParams(property, isChecked ? 0 : 1);
                }
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
        CheckBox cb = (CheckBox)getView();
        if(object.toString().equals(NgGo.NG_VISIBLE.VISIBLE)){
            cb.setVisibility(View.VISIBLE);
        }else if(object.toString().equals(NgGo.NG_VISIBLE.INVISIBLE)){
            cb.setVisibility(View.INVISIBLE);
        }else if(object.toString().equals(NgGo.NG_VISIBLE.GONE)){
            cb.setVisibility(View.GONE);
        }else{
            cb.setVisibility(View.VISIBLE);
            if(object instanceof Boolean){
                boolean flag = (Boolean)object;
                cb.setChecked(flag);
            }else if(object instanceof Integer){
                int flag = (int)object;
                cb.setChecked(flag == 0);
            }
        }
    }
}
