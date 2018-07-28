package com.campus.william.user.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;

import java.util.HashMap;

public class RegisteLogic extends ILogic{

    @Override
    public IResponse onRequest(IRequest iRequest) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> params = iRequest.getParams();
        params.put("account", "156701295139");
        params.put("password", "123456");
        return new IResponse(params, null);
    }
}
