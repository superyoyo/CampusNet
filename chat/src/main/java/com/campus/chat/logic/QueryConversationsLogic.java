package com.campus.chat.logic;

import com.bluelinelabs.logansquare.LoganSquare;
import com.campus.chat.internal.database.model.Conversation;
import com.campus.chat.internal.logic.ConversationLogic;
import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;

import java.util.HashMap;
import java.util.List;

@Logic(action = "queryConversations"
        , desc = "查询最近的绘话列表"
        , params = {"userId"}
        , paramsType = {ParamType.String}
        , paramsDesc = {"当前用户的ID"}
        , canNull = {false})
public class QueryConversationsLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        HashMap data = new HashMap();
        try {
            String userId = iRequest.getString("userId");
            List<Conversation> conversations = ConversationLogic.getInstance().queryConversations(userId);
            data.put("data", LoganSquare.serialize(conversations));
            return new IResponse(data, null);
        }catch (Exception e){
            return new IResponse(null, e);
        }
    }
}
