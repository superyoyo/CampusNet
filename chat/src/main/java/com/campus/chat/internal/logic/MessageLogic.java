package com.campus.chat.internal.logic;

import android.util.LruCache;

import com.campus.chat.internal.database.DBUtil;
import com.campus.chat.internal.database.model.Message;
import com.campus.chat.internal.database.model.MessageDao;
import com.campus.chat.internal.database.model.MessageStatus;
import com.campus.chat.internal.database.model.MessageType;

import java.util.List;

public class MessageLogic {
    private LruCache<String, List<Message>> mMessages;

    private static class InstanceHolder{
        private static MessageLogic sIntance = new MessageLogic();
    }

    public static MessageLogic getInstance(){
        return InstanceHolder.sIntance;
    }

    private MessageLogic(){
        mMessages = new LruCache<>(20);
    }

    public List<Message> queryMessagesByConversation(long lastMessageId, String conversationId, boolean findOld){
        if(lastMessageId == 0){
            List<Message> messages = mMessages.get(conversationId);
            if(messages == null){
                List<Message> list = queryMessages(lastMessageId, conversationId, findOld);
                mMessages.put(conversationId, list);
                return list;
            }else{
                return messages;
            }
        }else{
            return queryMessages(lastMessageId, conversationId, findOld);
        }
    }

    public long sendMessage(int type, String content, String fromAccount, String receivedAccount, String conversationId){
        Message message = new Message();
        message.setContent(content);
        message.setFromAccount(fromAccount);
        message.setReceivedAccount(receivedAccount);
        message.setSessionId(conversationId);
        message.setUpdateTime(System.currentTimeMillis());
        message.setType(type);
        message.setStatus(MessageStatus.NONE);

        long id = DBUtil.getInstance().getmDaoSession().getMessageDao().insert(message);
        //TODO 进行消息发送
        return id;
    }

    private List<Message> queryMessages(long lastMessageId, String conversationId, boolean findOld){
        Message message = DBUtil.getInstance().getmDaoSession().getMessageDao().loadByRowId(lastMessageId);
        List<Message> messages = DBUtil.getInstance()
                .getmDaoSession()
                .getMessageDao()
                .queryBuilder()
                .where(MessageDao.Properties.SessionId.eq(conversationId))
                .where(findOld ? MessageDao.Properties.UpdateTime.le(message.getUpdateTime())
                        : MessageDao.Properties.UpdateTime.ge(message.getUpdateTime()))
                .orderAsc(MessageDao.Properties.UpdateTime)
                .limit(100)
                .build()
                .list();
        return messages;
    }
}
