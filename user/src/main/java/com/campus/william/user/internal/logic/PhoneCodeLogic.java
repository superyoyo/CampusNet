package com.campus.william.user.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;

@Logic(action = "sendPhoneCode"
        , desc = "发送手机验证码"
        , params = {"phoneNumber"}
        , paramsDesc = {"电话号码"}
        , paramsType = {ParamType.String}
        , canNull = {false})
public class PhoneCodeLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        try {
            Thread.sleep(300);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new IResponse(null, null);
    }
}
