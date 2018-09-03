package com.campus.william.user.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;
import com.campus.william.net.model.IUser;

@Logic(action = "userLogin"
        , desc = "用户通过手机号跟密码进行登陆"
        , params = {"phoneNumber", "token"}
        , paramsDesc = {"电话号码", "验证码"}
        , paramsType = {ParamType.String, ParamType.String}
        , canNull = {false, false})
public class LoginLogic extends ILogic {
    @Override
    public IResponse onRequest(IRequest iRequest) {
        try {
            if(iRequest.getString("phoneNumber").equals("15601295139")
                    && iRequest.getString("token").equals("1234")){
                IUser user = IUser.signInByMobilePhone(iRequest.getString("phoneNumber"),
                        iRequest.getString("password"));
                return new IResponse(null, null);
            }else{
                return new IResponse(null, new Exception("手机号跟验证码不匹配"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new IResponse(null, new Exception("用户登陆失败"));
        }
    }
}
