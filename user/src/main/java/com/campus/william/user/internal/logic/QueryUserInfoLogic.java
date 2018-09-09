package com.campus.william.user.internal.logic;

import com.bluelinelabs.logansquare.LoganSquare;
import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.user.internal.model.StorageConstant;
import com.campus.william.user.internal.model.User;
import com.campus.william.user.internal.storage.Storage;
import com.campus.william.user.internal.storage.StorageFactory;

import java.io.IOException;
import java.util.HashMap;

@Logic(action = "currentUser"
        , desc = "获取当前登录用户"
        , params = {}
        , paramsDesc = {}
        , paramsType = {}
        , canNull = {})
public class QueryUserInfoLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        IResponse response = null;
        Storage storage = StorageFactory.getInstance().obtainStorage(StorageFactory.Stragegy.XML);
        try {
            User user = storage.query(User.class, StorageConstant.USER);
            user.setHead("https://tva3.sinaimg.cn/crop.14.0.722.722.180/bde6c83cjw8faew50il88j20ku0k275q.jpg");
            if(user == null){
                response = new IResponse(null, new Exception("当前没有登录用户"));
            }else{
                HashMap data = new HashMap();
                data.put("data", LoganSquare.serialize(user));
                response = new IResponse(data, null);
            }

        } catch (IOException e) {
            e.printStackTrace();
            response = new IResponse(null, e);
        }
        return response;
    }
}
