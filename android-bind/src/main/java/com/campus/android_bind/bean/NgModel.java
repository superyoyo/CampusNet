package com.campus.android_bind.bean;

import com.campus.android_bind.subject.EventSubject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jacklee on 17/1/12.
 */

public class NgModel extends EventSubject {
    private String mTag;
    private HashMap<String, Object> mParams;

    public NgModel(String tag) {
        mTag = tag;
        mParams = new HashMap<>();
    }

    public String getTag() {
        return mTag;
    }

    //为该对象，添加属性
    public NgModel addParams(String property, Object value){
        if(value instanceof List){
            if(mParams.get(property) != null && mParams.get(property) instanceof List){
                List list = (List) mParams.get(property);
                list.clear();
                list.addAll((List) value);
            }else{
                mParams.put(property, value);
            }
        }else{
            mParams.put(property, value);
        }

        //调用该方法，通知所有与该属性关联的view更新UI
        notifyData(property, value);
        return this;
    }

    //获取对应属性值
    public Object getValue(String property){
        if(mParams.containsKey(property)){
            return mParams.get(property);
        }
        return null;
    }

    public HashMap<String, Object> getParams(){
        return mParams;
    }
}
