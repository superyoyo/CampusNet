package com.campus.chat.logic;

import com.campus.chat.internal.logic.MessageLogic;
import com.campus.event_filter.logic.ILogic;
import com.campus.event_filter.request.IRequest;
import com.campus.event_filter.response.IResponse;
import com.campus.william.annotationprocessor.annotation.Logic;
import com.campus.william.annotationprocessor.annotation.ParamType;

import java.util.HashMap;
@Logic(action = "sendMessageLogic"
        , desc = "发送消息"
        , params = {"type", "content", "fromAccount", "receivedAccount", "conversationId"}
        , paramsDesc = {"消息类型", "消息内容", "消息发送者ID", "消息接受者ID", "绘话ID"}
        , paramsType = {ParamType.Int, ParamType.String, ParamType.String, ParamType.String, ParamType.String}
        , canNull = {false, false, false, false, false})
public class SendMessageLogic extends ILogic{
    @Override
    public IResponse onRequest(IRequest iRequest) {
        int type = iRequest.getInt("type");
        String content = iRequest.getString("content");
        String fromAccount = iRequest.getString("fromAccount");
        String receivedAccount = iRequest.getString("receivedAccount");
        String conversationId = iRequest.getString("conversationId");
        long id = MessageLogic.getInstance()
                .sendMessage(type, content, fromAccount, receivedAccount, conversationId);

        HashMap<String, Object> data = new HashMap<>();
        data.put("data", id);
        return new IResponse(data, null);
    }
}
