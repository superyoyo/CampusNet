package com.campus.chat.internal.logic;

import com.campus.chat.internal.database.DBUtil;
import com.campus.chat.internal.database.model.Conversation;
import com.campus.chat.internal.database.model.ConversationDao;

import java.util.ArrayList;
import java.util.List;

public class ConversationLogic {
    private String mUserId;
    private List<Conversation> mConversations;

    private static class InstanceHolder{
        private static ConversationLogic sInstance = new ConversationLogic();
    }

    public static ConversationLogic getInstance(){
        return InstanceHolder.sInstance;
    }

    public List<Conversation> queryConversations(String userId){
        if(mUserId == null || !mUserId.equals(userId) || (mConversations == null || mConversations.size() == 0)){
            /*mConversations = DBUtil.getInstance()
                    .getmDaoSession()
                    .getConversationDao()
                    .queryBuilder()
                    .where(ConversationDao.Properties.OwnId.eq(userId))
                    .orderAsc(ConversationDao.Properties.LastTime)
                    .build()
                    .list();*/
            mConversations = new ArrayList<>();
        }
        for(int i = 0; i < 10; i ++){
            Conversation conversation = new Conversation();
            conversation.setConversationId("a");
            conversation.setIcon("a");
            conversation.setLastMessage("干嘛呢？");
            conversation.setLastTime(System.currentTimeMillis());
            conversation.setNotification(true);
            conversation.setTop( i% 2 == 0);
            conversation.setTitle("闫学莉");
            conversation.setUnReadNum(i%3);
            mConversations.add(conversation);
        }
        return mConversations;
    }


}
