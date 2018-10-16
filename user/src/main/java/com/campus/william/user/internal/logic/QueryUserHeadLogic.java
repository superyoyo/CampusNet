package com.campus.william.user.internal.logic;

import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;

import java.util.HashMap;

@Logic(action = "queryUserHead"
        , desc = "根据用户Id查询用户头像"
        , params = {"userId"}
        , paramsType = {ParamType.String}
        , paramsDesc = {"要查询用户的userId"}
        , canNull = {false})
public class QueryUserHeadLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        HashMap data = new HashMap();
        data.put("data", "https://tva3.sinaimg.cn/crop.14.0.722.722.180/bde6c83cjw8faew50il88j20ku0k275q.jpg");
        return new IResponse(data, null);
    }
}
