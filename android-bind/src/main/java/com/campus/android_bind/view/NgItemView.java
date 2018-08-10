package com.campus.android_bind.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.campus.android_bind.R;

/**
 * Created by jacklee on 17/1/20.
 */

public class NgItemView extends View {
    private int mLaytoutId;
    private String mModel;
    public NgItemView(Context context) {
        super(context);
    }

    public NgItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NgItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs){
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ngRecyclerView);
        mLaytoutId = array.getResourceId(R.styleable.ngRecyclerView_layoutId, -1);
        mModel = array.getString(R.styleable.ngRecyclerView_model);
    }

    public int getLaytoutId() {
        return mLaytoutId;
    }

    public String getModel() {
        return mModel;
    }
}
