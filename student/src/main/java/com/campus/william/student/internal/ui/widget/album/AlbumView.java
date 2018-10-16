package com.campus.william.student.internal.ui.widget.album;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.campus.android_bind.util.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlbumView extends ViewGroup{
    private List<ImageView> views = new ArrayList<>();
    private List<AlbumBean> images = new ArrayList<>();
    // 第一个最大的view的宽高
    private int mItmeOne;
    // item 其余宽高
    private int ItemWidth;
    //当前隐藏控件的位置
    private int hidePosition = -1;
    // 根据数据 获取的 最大可拖拽的控件
    // 当前控件 距离屏幕 顶点 的高度
    private int mTopHeight = -1;
    // 每个item之间的间隙
    public int padding = -1;

    public AlbumView(Context context) {
        this(context, null);
    }

    public AlbumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        padding = dp2px(4, context);
        initUI();
    }

    Rect frame = new Rect();

    /**
     * 判断按下的位置是否在Item上 并返回Item的位置 {@link AbsListView #pointToPosition(int, int)}
     */
    public int pointToPosition(int x, int y) {
        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 获取当前控件 距离屏幕 顶点 的高度
     *
     * @param context
     * @return
     */
    private int getTopHeight(Context context) {
        int[] location = new int[2];
        getLocationOnScreen(location);
        int statusHeight = 0;
        statusHeight = location[1];
        if (0 == statusHeight) {
            getLocationInWindow(location);
            statusHeight = location[1];
        }
        return statusHeight;
    }

    public void setImages(List<AlbumBean> images) {
        this.images = images;
        initUI();
    }

    public List<AlbumBean> getImages() {
        List<AlbumBean> temimages = new ArrayList<AlbumBean>();
        for (AlbumBean Item : images) {
            if (!TextUtils.isEmpty(Item.getUrl())) {
                temimages.add(Item);
            }
        }
        return temimages;
    }

    public void initUI() {
        views.clear();
        removeAllViews();
        for (int i = 0; i < images.size(); i++) {
            ImageView view = new ImageView(getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().loadImage(view, images.get(i).getUrl());
            views.add(view);
            addView(view);
        }
    }

    int mItemCount = 1;
    boolean isReverse = false;
    int mViewHeight = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //
    }

    private void setViewsLayout(int resWidth, int l, int t) {
        ItemWidth = resWidth / 3 - padding - (padding / 3);
        for (int i = 0, size = getChildCount(); i < size; i++) {
            View view = getChildAt(i);
            if (i == 0) {
                mItmeOne = ItemWidth * 2 + padding;
                l += padding;
                t += padding;
                view.layout(l, t, l + mItmeOne, t + mItmeOne);
                l += mItmeOne + padding;
            }
            if (i == 1 || i == 2) {
                view.layout(l, t, l + ItemWidth, t + ItemWidth);
                t += ItemWidth + padding;
            }
            if (i >= 3) {
                if(i == 3)isReverse=false;
                view.layout(l, t, l + ItemWidth, t + ItemWidth);
                if (mItemCount % 3 == 0) {
                    isReverse = !isReverse;
                    t += ItemWidth + padding;
                } else {
                    if (isReverse) {
                        l += ItemWidth + padding;
                    } else {
                        l -= ItemWidth + padding;
                    }
                }
                mItemCount++;
            }
            if (i == hidePosition) {
                view.setVisibility(View.GONE);
            }
        }
        if (mViewHeight != t) {
            mViewHeight = t;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int resWidth = 0;
        int resHeight = 0;
        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            resWidth = getSuggestedMinimumWidth();
            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;
            setViewsLayout(resWidth, 0, 0);
            resHeight = getSuggestedMinimumHeight();
            resHeight = resHeight == 0 ? mViewHeight : resHeight;
        } else {
            resWidth = width;
            setViewsLayout(resWidth, 0, 0);
            resHeight = height;
        }
        setMeasuredDimension(resWidth, resHeight);
    }

    /**
     * 获得默认该layout的尺寸
     *
     * @return
     */
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    public int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (mTopHeight <= 0) {
            mTopHeight = getTopHeight(getContext());
        }
    }

}
