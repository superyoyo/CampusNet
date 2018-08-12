package com.campus.android_bind.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.campus.android_bind.R;

/**
 * Created by jacklee on 17/1/20.
 */

public class NgRecyclerView extends ViewGroup {
    private String type;
    private String orientation;
    private int span;
    private int divider_color;
    private int divider;
    public NgRecyclerView(Context context) {
        super(context);
    }

    public NgRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NgRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs){
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ngRecyclerView);
        type = array.getString(R.styleable.ngRecyclerView_type);
        orientation = array.getString(R.styleable.ngRecyclerView_orientation);
        span = array.getInt(R.styleable.ngRecyclerView_span, 4);
        divider_color = array.getColor(R.styleable.ngRecyclerView_divider_color, Color.WHITE);
        divider = array.getDimensionPixelOffset(R.styleable.ngRecyclerView_divider_height, 0);
    }

    /**
     *
     * @return 0 List 1 Grid 2 瀑布流
     */
    public int getType() {
        if(TextUtils.isEmpty(type)){
            return 0;
        }else{
            if(type.equals("grid")){
                return 1;
            }else if(type.equals("list")){
                return 0;
            }else{
                return 2;
            }
        }
    }

    /**
     * 获取方向
     * @return 0竖直方向 1横向
     */
    public int getOrientation(){
        if(TextUtils.isEmpty(orientation) || orientation.equals("vertical")){
            return LinearLayout.VERTICAL;
        }else{
            return LinearLayout.HORIZONTAL;
        }
    }

    public int getSpan() {
        return span;
    }

    public int getDivider_color() {
        return divider_color;
    }

    public int getDivider() {
        return divider;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {}
}
