package com.campus.william.user.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;

import java.util.HashMap;

@Logic(action = "RegisteLogic"
        , desc = "用户注册"
        , params = {"account", "password"}
        , paramsType = {ParamType.Int, ParamType.String}
        , canNull = {false, false}
        , paramsDesc = {"用户的账号", "用户的密码"})
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
