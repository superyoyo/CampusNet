package com.campus.event_filter.filter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.campus.event_filter.callback.ICallback;
import com.campus.event_filter.logic.LogicFactory;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.request.MODE;
import com.campus.event_filter.request.MODE_RANGE;
import com.campus.event_filter.response.IResponse;

public class IServelet implements Handler.Callback {
    private boolean mInit;
    private Context mContext;
    private Handler mUIHandler;
    private Handler mIOHandler;
    private Handler mCamputationHandler;
    private final int ONCE = 1;

    private static class InstanceHolder {
        public static IServelet sInstance = new IServelet();
    }

    public static IServelet getInstance() {
        return InstanceHolder.sInstance;
    }

    public void init(Context context, Looper uiLooper, Looper ioLooper, Looper camputationLooper) {
        mContext = context;
        mUIHandler = new Handler(uiLooper, this);
        mIOHandler = new Handler(ioLooper, this);
        mCamputationHandler = new Handler(camputationLooper, this);

    }

    public IResponse onRequest(IRequest request) {
        return LogicFactory.getInstance().dealRequest(request);
    }

    public void onRequest(IRequest request, @MODE_RANGE int inMode, @MODE_RANGE int outMode) {
        Message msg = Message.obtain();
        msg.obj = request;
        msg.arg1 = outMode;
        switch (inMode) {
            case MODE.UI:
                mUIHandler.sendMessage(msg);
                break;
            case MODE.IO:
                mIOHandler.sendMessage(msg);
                break;
            case MODE.CAMPUTATION:
                mCamputationHandler.sendMessage(msg);
                break;
        }

    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public boolean handleMessage(Message message) {
        final IRequest request = (IRequest) message.obj;
        final IResponse response = LogicFactory.getInstance().dealRequest(request);
        Handler handler = null;
        switch (message.arg1) {
            case MODE.UI:
                handler = mUIHandler;
                break;
            case MODE.IO:
                handler = mIOHandler;
                break;
            case MODE.CAMPUTATION:
                handler = mCamputationHandler;
                break;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(request.getICallback() != null){
                    if(request.getNext() == null){
                        request.getICallback().done(response);
                    }else{
                        IRequest result = request.getICallback().next(response);
                        IRequest next = request.getNext();
                        if(next == null || result == null){
                            return;
                        }
                        if(result.getParams().size() > 0){
                            next.initParams(result.getParams());
                        }
                        if(TextUtils.isEmpty(result.getAction())){
                            next.action(result.getAction());
                        }
                        onRequest(next, next.getInMode(), next.getOutMode());
                    }

                }
            }
        });
        return true;
    }
}
