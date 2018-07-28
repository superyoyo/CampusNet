package com.campus.event_filter.response;

import java.util.HashMap;
import java.util.List;

public class IResponse {
    private HashMap<String, Object> mData;
    private Exception mException;

    public IResponse(HashMap<String, Object> data, Exception exception) {
        mData = data == null ? new HashMap<String, Object>() : data;
        mException = exception;
    }

    public int getInt(String key){
        return (int)mData.get(key);
    }

    public float getFloat(String key){
        return (float)mData.get(key);
    }

    public long getLong(String key){
        return (long)mData.get(key);
    }

    public String getString(String key){
        return (String)mData.get(key);
    }

    public List getList(String key){
        return (List)mData.get(key);
    }

    public Object get(String key){
        return mData.get(key);
    }

    public HashMap<String, Object> getData() {
        return mData;
    }

    public Exception getException() {
        return mException;
    }
}
