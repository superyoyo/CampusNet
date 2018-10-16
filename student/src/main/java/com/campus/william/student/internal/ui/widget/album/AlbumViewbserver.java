package com.campus.william.student.internal.ui.widget.album;

import android.view.View;

import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;

import java.util.ArrayList;
import java.util.List;

public class AlbumViewbserver extends ViewObserver{

    public AlbumViewbserver(View view, NgModel ngModel, String property) {
        super(view, ngModel, property);
    }

    @Override
    public void dataChange(ISubject subject, Object object) {
        List<String> list = (ArrayList) object;
        List<AlbumBean> beans = new ArrayList<>();
        AlbumView albumView = (AlbumView) getView();
        for(String item : list){
            AlbumBean albumBean = new AlbumBean();
            albumBean.setUrl(item);
            beans.add(albumBean);
        }
        albumView.setImages(beans);
    }

    @Override
    public void initViewLogic(View view, NgModel ngModel, String property) {
        AlbumView albumView = (AlbumView) view;

    }
}
