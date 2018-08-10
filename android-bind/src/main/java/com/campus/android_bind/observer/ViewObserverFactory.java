package com.campus.android_bind.observer;

import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.campus.android_bind.bean.NgModel;
import com.campus.android_bind.observer.impl.ButtonViewObserver;
import com.campus.android_bind.observer.impl.CheckBoxViewObserver;
import com.campus.android_bind.observer.impl.EditViewObserver;
import com.campus.android_bind.observer.impl.FrameLayoutViewObserver;
import com.campus.android_bind.observer.impl.ImageViewObserver;
import com.campus.android_bind.observer.impl.LinearLayoutViewObserver;
import com.campus.android_bind.observer.impl.ProgressBarViewObserver;
import com.campus.android_bind.observer.impl.NgRecyclerViewObserver;
import com.campus.android_bind.observer.impl.RelativeLayoutViewObserver;
import com.campus.android_bind.observer.impl.SeekBarViewObserver;
import com.campus.android_bind.observer.impl.SwitchViewObserver;
import com.campus.android_bind.observer.impl.TextViewObserver;
import com.campus.android_bind.view.NgRecyclerView;

import java.util.HashMap;


/**
 * Created by jacklee on 17/1/12.
 */

public class ViewObserverFactory {
    private LruCache<String, ViewObserver> mCachedViewObservers;
    private final int mMaxCached = 20;
    private ViewObserverFactory2 mViewObserverFactory2;

    private static class InstanceHolder{
        public static ViewObserverFactory sInstance = new ViewObserverFactory();
    }

    public static ViewObserverFactory getInstance(){
        return InstanceHolder.sInstance;
    }

    private ViewObserverFactory() {
        mCachedViewObservers = new LruCache<>(mMaxCached);
    }

    public void setViewObserverFactory2(ViewObserverFactory2 viewObserverFactory2) {
        mViewObserverFactory2 = viewObserverFactory2;
    }

    public ViewObserver createViewObserver(View view, NgModel ngModel, String perproty){
        ViewObserver viewObserver = null;
        /*if(mCachedViewObservers.get(view.getClass().getName()) != null){
            viewObserver = mCachedViewObservers.get(view.getClass().getName());
            viewObserver.reInit(view, ngModel, perproty);
            return viewObserver;
        }*/
        if(mViewObserverFactory2 != null){
            viewObserver = mViewObserverFactory2.createViewObserver(view, ngModel, perproty);
        }
        if(viewObserver == null){
            if(view instanceof TextView){
                if(view instanceof EditText){
                    //如果是输入框
                    viewObserver = new EditViewObserver(view, ngModel, perproty);
                }else if(view instanceof Button){
                    if(view instanceof CompoundButton) {
                        if (view instanceof CheckBox) {
                            //如果是CheckBox
                            viewObserver = new CheckBoxViewObserver(view, ngModel, perproty);
                        } else if(view instanceof Switch){
                            //如果是Switch
                            viewObserver = new SwitchViewObserver(view, ngModel, perproty);
                        }
                    }else{
                        //如果是Button
                        viewObserver = new ButtonViewObserver(view, ngModel, perproty);
                    }
                }else{
                    //如果是TexView
                    viewObserver = new TextViewObserver(view, ngModel, perproty);
                }
            }else if(view instanceof ImageView){
                //如果是图片
                viewObserver = new ImageViewObserver(view, ngModel, perproty);
            }else if(view instanceof ProgressBar){
                if(view instanceof SeekBar){
                    viewObserver = new SeekBarViewObserver(view, ngModel, perproty);
                }else{
                    //如果是ProgressBar
                    viewObserver = new ProgressBarViewObserver(view, ngModel, perproty);
                }
            }else if(view instanceof RelativeLayout){
                viewObserver = new RelativeLayoutViewObserver(view, ngModel, perproty);
            }else if(view instanceof LinearLayout){
                viewObserver = new LinearLayoutViewObserver(view, ngModel, perproty);
            }else if(view instanceof FrameLayout){
                viewObserver = new FrameLayoutViewObserver(view, ngModel, perproty);
            }else if(view instanceof NgRecyclerView){
                viewObserver = new NgRecyclerViewObserver(view, ngModel, perproty);
            }
        }
        mCachedViewObservers.put(view.getClass().getName(), viewObserver);
        return viewObserver;
    }

    public interface ViewObserverFactory2{
        public ViewObserver createViewObserver(View view, NgModel ngModel, String perproty);
    }
}
