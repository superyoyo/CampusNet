package com.campus.chat.logic;

import com.bluelinelabs.logansquare.LoganSquare;
import com.campus.chat.internal.database.model.Message;
import com.campus.chat.internal.logic.MessageLogic;
import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;

import java.util.HashMap;
import java.util.List;

@Logic(action = "queryMessagesLogic"
        , desc = "通过绘话Id查询该绘话的聊天记录"
        , params = {"lastMessageId", "conversationId", "findOld"}
        , paramsDesc = {"锚点消息的ID", "药查询的绘话ID", "是否查询之前的消息，true为查询LastMessageId之前的，false则是之后的"}
        , paramsType = {ParamType.Long, ParamType.String, ParamType.Boolean}
        , canNull = {true, false, true})
public class QueryMessagesLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        long lastMessageId = iRequest.getLong("lastMessageId");
        String conversationId = iRequest.getString("conversationId");
        boolean findOld = iRequest.getBoolean("findOld");
        List<Message> messages = MessageLogic.getInstance()
                .queryMessagesByConversation(lastMessageId, conversationId, findOld);
        HashMap<String, Object> res = new HashMap<>();
        try {
            res.put("data", LoganSquare.serialize(messages));
            return new IResponse(res, null);
        }catch (Exception e){
            return new IResponse(null, e);
        }
    }
}
