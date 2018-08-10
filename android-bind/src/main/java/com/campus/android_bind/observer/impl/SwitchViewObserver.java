package com.campus.android_bind.observer.impl;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;


/**
 * Created by jacklee on 17/1/12.
 */

public class SwitchViewObserver extends ViewObserver {
    private boolean not_change;
    public SwitchViewObserver(View view, final NgModel ngModel, final String perproty) {
        super(view, ngModel, perproty);
    }

    @Override
    public void initViewLogic(View view, final NgModel ngModel, final String property) {
        Switch aSwitch = (Switch)view;
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        Switch aSwitch = (Switch)getView();
        if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            aSwitch.setVisibility(View.VISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.INVISIBLE)){
            aSwitch.setVisibility(View.INVISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.GONE)){
            aSwitch.setVisibility(View.GONE);
        }else{
            aSwitch.setVisibility(View.VISIBLE);
            if(object instanceof Boolean){
                boolean flag = (Boolean)object;
                aSwitch.setChecked(flag);
            }else if(object instanceof Integer){
                int flag = (int)object;
                aSwitch.setChecked(flag == 0);
            }
        }
    }
}
