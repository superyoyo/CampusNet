package com.campus.android_bind.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by superyoyo on 17/2/17.
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {
    private int item_offset;
    private View divider;
    private Context context;
    private int color;

    public CommonItemDecoration(int color, Context context, int item_offset) {
        this.color = color;
        this.context = context;
        this.item_offset = item_offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getLayoutManager().canScrollHorizontally()) {
            outRect.set(0, 0, item_offset, 0);
        } else {
            outRect.set(0, 0, 0, item_offset);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getLayoutManager().canScrollHorizontally()) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        c.save();
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + item_offset;
            if (divider == null) {
                divider = new View(context);
                divider.setBackgroundColor(color);
            }
            divider.layout(left, top, right, bottom);
            divider.draw(c);
        }
        c.restore();
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        c.save();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            final int bottom = child.getBottom() + params.bottomMargin;
            final int top = bottom - item_offset;
            if (divider == null) {
                divider = new View(context);
                divider.setBackgroundColor(color);
            }
            divider.layout(left, top, right, bottom);
            divider.draw(c);
        }
        c.restore();
    }
}
