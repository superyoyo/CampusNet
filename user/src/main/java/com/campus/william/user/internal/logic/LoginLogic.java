package com.campus.william.user.internal.logic;

import com.campus.event_filter.annotation.Action;
import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.net.model.IUser;
//TODO 通过注解与Transform Api的方式，为
@Action("Login")
public class LoginLogic extends ILogic {
    @Override
    public IResponse onRequest(IRequest iRequest) {
        try {
            IUser.signInByMobilePhone(iRequest.getString("phoneNumber"),
                    iRequest.getString("password"));
            return new IResponse(null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new IResponse(null, e);
        }
    }
}
