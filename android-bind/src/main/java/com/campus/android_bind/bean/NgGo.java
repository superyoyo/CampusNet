package com.campus.android_bind.bean;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.campus.android_bind.BuildConfig;
import com.campus.android_bind.adapter.CommonAdapter;
import com.campus.android_bind.observer.AllPropertyObserver;
import com.campus.android_bind.observer.PropertyObserver;
import com.campus.android_bind.observer.ViewObserver;
import com.campus.android_bind.observer.ViewObserverFactory;
import com.campus.android_bind.observer.impl.NgRecyclerViewObserver;
import com.campus.android_bind.subject.EventSubject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by jacklee on 17/1/12.
 */

public class NgGo {
    private final String TAG = "NgGo";
    //每个主题都有其订阅者，即ngModel
    private HashMap<String, EventSubject> subjects;
    private SparseArray<CommonAdapter> adapters;
    //当前nggo作用的域
    private View parent;
    private LayoutInflater inflater;
    private int mLayoutId;
    private Object mActionContainer;

    public interface NG_VISIBLE {
        public final static String VISIBLE = "VISIBLE";
        public final static String INVISIBLE = "INVISIBLE";
        public final static String GONE = "INVISIBLE";
    }

    public NgGo(Context context, int layouId) {
        mLayoutId = layouId;
        adapters = new SparseArray<>();
        subjects = new HashMap<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = inflater.cloneInContext(context);
        inflater.setFactory2(new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attributeSet) {
                View view = null;
                try {
                    if (-1 == name.indexOf('.')) {
                        view = inflater.createView(name, "android.widget.", attributeSet);
                    } else {
                        view = inflater.createView(name, null, attributeSet);
                    }
                } catch (ClassNotFoundException e) {
                    Log.d(TAG, "name:" + name + " e:" + e.getMessage());
                    return null;
                }

                paraseViewTag(view);
                return view;

            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
    }

    public NgGo(View view) {
        parent = view;
    }

    //添加ngModel
    public NgGo addNgModel(NgModel ngModel) {
        subjects.put(ngModel.getTag(), ngModel);
        return this;
    }

    public NgGo addActionContainer(Object object){
        mActionContainer = object;
        return this;
    }

    public View inflateAndBind() {
        if (mLayoutId <= 0) {
            Toast.makeText(inflater.getContext(), "LayoutId不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }
        parent = inflater.inflate(mLayoutId, null);
        return parent;
    }

    public void bind() {
        if (parent != null) {
            Toast.makeText(inflater.getContext(), "view不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        paraseViewGroup((ViewGroup) parent);
    }

    public CommonAdapter getRecyclerAdapter(int id) {
        return adapters.get(id);
    }

    public NgGo addPropertyObserver(NgModel ngModel, PropertyObserver iObserver) {
        String tag = ngModel.getTag();

        EventSubject subject = subjects.get(tag);
        if (subject != null) {
            subject.registe(iObserver);
        }
        return this;
    }

    public NgGo addAllPropertyObserver(AllPropertyObserver propertyObserver) {
        Iterator<String> keys = subjects.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            EventSubject subject = subjects.get(key);
            subject.registe(propertyObserver);
        }
        return this;
    }

    public void release(){
        Iterator<String> it = subjects.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            EventSubject subject = subjects.get(key);
            subject.removeAll();
            subject = null;
        }

        adapters.clear();
        adapters = null;
        mActionContainer = null;
    }

    private void paraseViewGroup(ViewGroup viewGroup) {
        for (int i = 0, n = ((ViewGroup) parent).getChildCount(); i < n; i++) {
            View itemView = ((ViewGroup) parent).getChildAt(i);
            paraseViewTag(itemView);
            if (itemView instanceof ViewGroup) {
                paraseViewGroup((ViewGroup) itemView);
            }
        }
    }

    private boolean paraseViewTag(View view) {
        String tag = (String) view.getTag();
        if(TextUtils.isEmpty(tag)){
           return false;
        }
        if (!tag.startsWith("@{") && !tag.endsWith("}")) {
            return false;
        }

        tag = tag.substring(2, tag.length() - 1);
        Log.d(TAG, tag);
        String[] params = tag.split(";");
        try {
            NgBindTag ngBindTag = new NgBindTag();
            for(String item : params){
                String[] data = item.split("->");
                if(data[0].equals("model")){
                    String[] modelAndProperty = data[1].split("\\.");
                    ngBindTag.setModel(modelAndProperty[0]);
                    ngBindTag.setProperty(modelAndProperty[1]);
                }else if(data[0].equals("click")){
                    ngBindTag.setClick(data[1]);
                }else if(data[0].equals("longClick")){
                    ngBindTag.setLongClick(data[1]);
                }
            }

            //获取该view对应的NgModel
            EventSubject subject = subjects.get(ngBindTag.getModel());

            //将该view和该对象关联起来 (根据view类型，创建不同的ViewObserver)
            ViewObserver viewObserver = ViewObserverFactory.getInstance().createViewObserver(view, (NgModel) subject, ngBindTag.getProperty());
            if (viewObserver == null) {
                Log.d(TAG, "没有匹配的viewobserver");
            }
            if (viewObserver != null && subject != null) {
                subject.registe(viewObserver);
            }

            if (viewObserver instanceof NgRecyclerViewObserver) {
                view = ((NgRecyclerViewObserver) viewObserver).getRecyclerView();
                if (view.getId() != View.NO_ID) {
                    adapters.put(view.getId(), ((NgRecyclerViewObserver) viewObserver)
                            .getCommonAdapter());
                }
            }

            if (!TextUtils.isEmpty(ngBindTag.getClick())) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mActionContainer == null){
                            return;
                        }
                        Class clazz = mActionContainer.getClass();
                        try {
                            Method method = clazz.getDeclaredMethod(ngBindTag.getClick(), View.class);
                            method.invoke(mActionContainer, v);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            if (!TextUtils.isEmpty(ngBindTag.getLongClick())) {
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if(mActionContainer == null){
                            return false;
                        }
                        Class clazz = mActionContainer.getClass();
                        try {
                            Method method = clazz.getDeclaredMethod(ngBindTag.getLongClick(), View.class);
                            method.invoke(mActionContainer, v);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;
                    }
                });
            }
        } catch (Exception e) {
            Log.d(TAG, "view 的tag格式错误");
        }

        return true;
    }
}
