package com.campus.event_filter.logic;

import android.support.v4.util.LruCache;
import android.util.SparseArray;
import android.widget.Toast;

import com.campus.event_filter.filter.IServelet;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;

import java.util.HashMap;

public class LogicFactory {
    private static class InstanceHolder{
        public static LogicFactory sInstance = new LogicFactory();
    }

    public static LogicFactory getInstance(){
        return InstanceHolder.sInstance;
    }

    private HashMap<String, Class<? extends ILogic>> mLogicClasses;
    private LruCache<String, ILogic> mLogics;

    private LogicFactory() {
        mLogicClasses = new HashMap<>();
        mLogics = new LruCache<>(10);
    }

    public void registeLogic(String action, Class<? extends ILogic> logic){
        mLogicClasses.put(action, logic);
    }

    public IResponse dealRequest(IRequest request){
        String action = request.getAction();
        ILogic logic = mLogics.get(action);
        if(logic == null){
            try {
                Class<? extends ILogic> logicClass = mLogicClasses.get(action);
                if(logicClass == null){
                    Toast.makeText(IServelet.getInstance().getContext(),
                            "没有找到此action对应的Logic类", Toast.LENGTH_SHORT).show();
                    return new IResponse(null, new Exception("没有找到此" + action + "对应的Logic类"));
                }
                logic = logicClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(IServelet.getInstance().getContext(),
                        "实例化此action对应的Logic类失败", Toast.LENGTH_SHORT).show();
                return new IResponse(null, new Exception("实例化此action对应的Logic类失败"));
            }
        }

        return logic.onRequest(request);
    }
}
