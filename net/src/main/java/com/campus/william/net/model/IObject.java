package com.campus.william.net.model;

import android.text.TextUtils;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class IObject {
    private final String TAG = "IObject";

    private String mName;
    private String mObjectId;
    private HashMap<String, Object> mParams;
    private ACL mACL;

    private IObject(String name) {
        mName = name;
        mParams = new HashMap<>();
    }

    public static IObject obtain(String objectName){
        return new IObject(objectName);
    }

    public IObject put(String key, Object value){
        mParams.put(key, value);
        return this;
    }

    public int getInt(String key){
        return (int) mParams.get(key);
    }

    public long getLong(String key){
        return (long) mParams.get(key);
    }

    public float getFloat(String key){
        return (float) mParams.get(key);
    }

    public double getDouble(String key){
        return (float) mParams.get(key);
    }

    public String getString(String key){
        return (String) mParams.get(key);
    }

    public boolean getBoolean(String key){
        return (boolean) mParams.get(key);
    }

    public Date getDate(String key){
        return (Date) mParams.get(key);
    }

    public IObject setACL(ACL acl){
        mACL = acl;
        return this;
    }

    public static IObject fetch(String objectName, String objectId){
        //TODO 进行同步获取
        return new IObject(objectName);
    }

    public static IObject fetch(String objectName, String objectId, String[] keys){
        //TODO 进行同步获取
        return new IObject(objectName);
    }

    public void save(){
        if(TextUtils.isEmpty(mName)){
            Log.d(TAG, "objectName不能为空");
            return;
        }

        //TODO 保存改对象到服务器
    }

    public IObject fetchSave(){
        if(TextUtils.isEmpty(mName)){
            Log.d(TAG, "objectName不能为空");
            return null;
        }

        //TODO 保存改对象到服务器,并获取最新的对象
        return new IObject("");
    }

    public static void saveAll(List<IObject> objects){
        //TODO 保存所有对象
    }

    public void update(String objectId){
        if(TextUtils.isEmpty(mName)){
            Log.d(TAG, "objectName不能为空");
            return;
        }

        if(TextUtils.isEmpty(objectId)){
            Log.d(TAG, "objectId不能为空");
            return;
        }

        //TODO 更新制定ID的Object
    }

    public void fetchUpdate(String objectId){
        if(TextUtils.isEmpty(mName)){
            Log.d(TAG, "objectName不能为空");
            return;
        }

        if(TextUtils.isEmpty(objectId)){
            Log.d(TAG, "objectId不能为空");
            return;
        }

        //TODO 更新制定ID的Object,并把更新获取到本地
    }

    public void update(IQuery query){
        //TODO 更新满足查询条件的所有对象
    }

    public static void delete(String objectName, String objectId){
        //TODO 删除制定Id的Object
    }

    public static void delete(IQuery query){
        //TODO 删除满足查询条件的所有对象
    }
}
