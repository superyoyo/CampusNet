package com.campus.android_bind.inflater;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class BindLayoutInflater extends LayoutInflater{

    public BindLayoutInflater(Context context) {
        super(context);
    }

    public BindLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    @Override
    public LayoutInflater cloneInContext(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.cloneInContext(context);
    }

    @Override
    public View inflate(int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        View view = super.inflate(resource, root, attachToRoot);

        return view;
    }

    public interface InflaterListener{
        public void inflaterChildAndLayoutParams(View view);
    }
}
