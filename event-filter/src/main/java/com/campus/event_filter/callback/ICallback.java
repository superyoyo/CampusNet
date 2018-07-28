package com.campus.event_filter.callback;

import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;

public class ICallback {
    public void done(IResponse response){};

    public IRequest next(IResponse response){
        return null;
    };
}
