package com.campus.william.student.internal.ui.widget.tag;

import android.graphics.Color;
import android.view.View;

import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;
import com.campus.william.student.R;

import java.util.ArrayList;
import java.util.List;

public class TagViewObserver extends ViewObserver{
    public TagViewObserver(View view, NgModel ngModel, String property) {
        super(view, ngModel, property);
    }

    @Override
    public void dataChange(ISubject subject, Object object) {
        List<String> list = (ArrayList) object;
        TagView tagView = (TagView) getView();
        for(String item : list){
            Tag tag = new Tag(item);
            tag.tagTextColor = getView().getResources().getColor(R.color.text_gray);
            tag.layoutColorPress = Color.parseColor("#555555");
            tag.background = getView().getResources().getDrawable(R.color.gray_bkg);
            tag.radius = 5f;
            tag.tagTextSize = 14f;
            tag.layoutBorderSize = 1f;
            tag.isDeletable = false;
            tagView.addTag(tag);
        }
    }

    @Override
    public void initViewLogic(View view, NgModel ngModel, String property) {

    }
}
