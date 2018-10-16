package com.campus.chat.internal.database.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
@JsonObject
public class Conversation implements Serializable{
    private static final long serialVersionUID = 2206972195410699504L;
    @Id(autoincrement = true)
    private Long id;
    @JsonField
    private String conversationId;//服务器的ID
    @JsonField
    private int type;//会话类型 0单聊 1群聊 3系统消息
    @JsonField
    private String lastMessage;//最后一条消息
    @JsonField
    private String icon;//会话的icon json格式[]
    @JsonField
    private String title;//会话的title
    @JsonField
    private long lastTime;//会话最后一条消息的时间
    @JsonField
    private boolean notification;//绘话是否通知
    @JsonField
    private boolean top;//绘话是否置顶
    @JsonField
    private String ownId;//是谁的会话
    @JsonField
    private String creator;//谁创建的改会话
    @JsonField
    private String frendId;//如果时单聊的话，则为好友的id
    @JsonField
    private int unReadNum;//消息的未读数量


    @Generated(hash = 842285376)
    public Conversation(Long id, String conversationId, int type,
            String lastMessage, String icon, String title, long lastTime,
            boolean notification, boolean top, String ownId, String creator,
            String frendId, int unReadNum) {
        this.id = id;
        this.conversationId = conversationId;
        this.type = type;
        this.lastMessage = lastMessage;
        this.icon = icon;
        this.title = title;
        this.lastTime = lastTime;
        this.notification = notification;
        this.top = top;
        this.ownId = ownId;
        this.creator = creator;
        this.frendId = frendId;
        this.unReadNum = unReadNum;
    }

    @Generated(hash = 1893991898)
    public Conversation() {
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public String getOwnId() {
        return ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getFrendId() {
        return frendId;
    }

    public void setFrendId(String frendId) {
        this.frendId = frendId;
    }

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    public boolean getNotification() {
        return this.notification;
    }

    public boolean getTop() {
        return this.top;
    }
}
