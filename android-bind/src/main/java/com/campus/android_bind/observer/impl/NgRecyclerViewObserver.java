package com.campus.android_bind.observer.impl;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.campus.android_bind.adapter.CommonAdapter;
import com.campus.android_bind.bean.NgGo;
import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.subject.ISubject;
import com.campus.android_bind.view.NgItemView;
import com.campus.android_bind.view.NgRecyclerView;
import com.campus.android_bind.widget.CommonItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacklee on 17/1/12.
 */

public class NgRecyclerViewObserver extends ViewObserver {
    private CommonAdapter mCommonAdapter;
    private RecyclerView mRecyclerView;

    public NgRecyclerViewObserver(View view, NgModel ngModel, String property) {
        super(view, ngModel, property);
    }

    @Override
    public void initViewLogic(View view, NgModel ngModel, String property) {
        mRecyclerView = new RecyclerView(view.getContext());
        mRecyclerView.setLayoutParams(view.getLayoutParams());
        List<NgItemView> views = new ArrayList<>();
        //3.实例化该viewgroup的所有item
        for(int j = 0, m = ((ViewGroup) view).getChildCount(); j < m; j++){
            View item_view = ((ViewGroup) view).getChildAt(j);
            if(item_view instanceof NgItemView){
                views.add((NgItemView)item_view);
            }

            //将该子view从父view中移除，不然，在CommonAdapter中把该子view当item时会报该子view有多个父view
            ((ViewGroup) view).removeView(item_view);
            //移除
            j --;
            m--;
        }

        mCommonAdapter = new CommonAdapter(views, view.getId(), new ArrayList(), view.getContext());

        if(view.getId() != View.NO_ID){
            mRecyclerView.setId(view.getId());
        }

        //根据LinearLayout的Orientation属性，设置recyclerview的方向
        NgRecyclerView ngRecyclerView = (NgRecyclerView) view;
        if(ngRecyclerView.getType() == 0){
            if(ngRecyclerView.getOrientation() == LinearLayout.VERTICAL){
                mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            }else{
                mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            }
        }else if(ngRecyclerView.getType() == 1){
            if(ngRecyclerView.getOrientation() == LinearLayout.VERTICAL){
                mRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), ngRecyclerView.getSpan(), GridLayoutManager.VERTICAL, false));
            }else{
                mRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), ngRecyclerView.getSpan(), GridLayoutManager.HORIZONTAL, false));
            }
        }


        //为recyclerview设置适配器
        mRecyclerView.setAdapter(mCommonAdapter);
        mRecyclerView.addItemDecoration(new CommonItemDecoration(ngRecyclerView.getDivider_color(), view.getContext(), ngRecyclerView.getDivider()));

        if(ngRecyclerView.getBackground() != null){
            if(Build.VERSION.SDK_INT >= 16){
                mRecyclerView.setBackground(ngRecyclerView.getBackground());
            }else{
                mRecyclerView.setBackgroundDrawable(ngRecyclerView.getBackground());
            }
        }
    }

    @Override
    public void dataChange(ISubject subject, Object object) {
        if(object == null){
            return;
        }
        RecyclerView rv = (RecyclerView)getView();
        if(object.equals(NgGo.NG_VISIBLE.VISIBLE)){
            rv.setVisibility(View.VISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.INVISIBLE)){
            rv.setVisibility(View.INVISIBLE);
        }else if(object.equals(NgGo.NG_VISIBLE.GONE)){
            rv.setVisibility(View.GONE);
        }else {
            rv.setVisibility(View.VISIBLE);
            rv.getAdapter().notifyDataSetChanged();
        }
    }

    public CommonAdapter getCommonAdapter() {
        return mCommonAdapter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
