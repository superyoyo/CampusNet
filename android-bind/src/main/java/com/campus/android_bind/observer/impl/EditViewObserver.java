package com.campus.android_bind.observer.impl;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;


/**
 * Created by jacklee on 17/1/12.
 */

public class EditViewObserver extends ViewObserver {
    private boolean not_change;

    public EditViewObserver(View view, NgModel ngModel, String property) {
        super(view, ngModel, property);
    }

    @Override
    public void initViewLogic(View view, final NgModel ngModel, final String property) {
        EditText editText = (EditText)view;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                not_change = true;
                ngModel.addParams(property, s.toString());
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
        EditText et = (EditText)getView();
        if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            et.setVisibility(View.VISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.INVISIBLE)){
            et.setVisibility(View.INVISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.GONE)){
            et.setVisibility(View.GONE);
        }else{
            et.setVisibility(View.VISIBLE);
            et.setText(object.toString());
        }
    }
}
