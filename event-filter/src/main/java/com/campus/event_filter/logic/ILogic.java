package com.campus.event_filter.logic;

import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;

public abstract class ILogic {
    public abstract IResponse onRequest(IRequest iRequest);
}
