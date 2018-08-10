package com.campus.event_filter.request;

import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.filter.IServelet;
import com.campus.event_filter.response.IResponse;

import java.util.HashMap;

public class IRequest {
    private HashMap<String, Object> mParams;
    private int mAction;
    private int mInMode;
    private int mOutMode;
    private IRequest mNext;
    private IRequest mFirst;
    private ICallback mICallback;

    private IRequest() {
        mParams = new HashMap<>();
        mInMode = MODE.UI;
        mOutMode = MODE.UI;
        mAction = -1;
    }

    public static IRequest obtain(){
        return new IRequest();
    }

    public IRequest action(int action) {
        mAction = action;
        return this;
    }

    public IRequest add(String key, Object value){
        mParams.put(key, value);
        return this;
    }

    public IRequest initParams(HashMap<String, Object> params){
        mParams.putAll(params);
        return this;
    }

    public int getAction() {
        return mAction;
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

    public Object get(String key){
        return mParams.get(key);
    }

    public ICallback getICallback() {
        return mICallback;
    }

    private void setICallback(ICallback ICallback) {
        mICallback = ICallback;
    }

    public IRequest getNext() {
        return mNext;
    }

    public int getInMode() {
        return mInMode;
    }

    public int getOutMode() {
        return mOutMode;
    }

    public HashMap<String, Object> getParams() {
        return mParams;
    }

    public IResponse execute(){
        return IServelet.getInstance().onRequest(this);
    }

    public void submit(@MODE_RANGE int inMode, @MODE_RANGE int outMode, ICallback callback){
        mICallback = callback;
        mInMode = inMode;
        mOutMode = outMode;
        IServelet.getInstance().onRequest(this, inMode, outMode);
    }

    public IRequest next(@MODE_RANGE int inMode, @MODE_RANGE int outMode, ICallback callback){
        if(mFirst == null){
            mFirst = this;
        }
        mInMode = inMode;
        mOutMode = outMode;
        mICallback = callback;
        IRequest request = IRequest.obtain();
        request.mFirst = mFirst;
        mNext = request;
        return mNext;
    }

    public void submit(){
        IServelet.getInstance().onRequest(mFirst, mFirst.getInMode(), mFirst.getOutMode());
    }
}
