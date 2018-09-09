package com.campus.william.user.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;
import com.campus.william.net.model.IUser;
import com.campus.william.user.internal.model.StorageConstant;
import com.campus.william.user.internal.model.User;
import com.campus.william.user.internal.storage.Storage;
import com.campus.william.user.internal.storage.StorageFactory;

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
                IUser iUser = IUser.signInByMobilePhone(iRequest.getString("phoneNumber"),
                        iRequest.getString("password"));

                User user = new User();
                user.setUserId("asdfasdf");
                user.setName("William Liu");
                user.setHead("https://upload.jianshu.io/users/upload_avatars/12250573/c2470451-4b20-46f7-a9a2-cc76ff17e5cd.jpg");
                user.setAge(23);
                user.setSex("male");
                user.setSign("我淋过最大的雨，就是烈日下的不回头");
                Storage storage = StorageFactory.getInstance()
                        .obtainStorage(StorageFactory.Stragegy.XML);
                storage.insert(StorageConstant.USER, user);
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
