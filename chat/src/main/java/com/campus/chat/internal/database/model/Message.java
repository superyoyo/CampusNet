package com.campus.chat.internal.database.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

@Entity
@JsonObject
public class Message {
    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;
    @JsonField(name = "sessionId")
    @Property(nameInDb = "sessionId")
    private String sessionId;//
    @JsonField(name = "fromAccount")
    @Property(nameInDb = "fromAccount")
    private String fromAccount;//这个信息是谁发的
    @JsonField(name = "receivedAccount")
    @Property(nameInDb = "receivedAccount")
    private String receivedAccount;//这个信息是发给谁的
    @JsonField(name = "type")
    @Property(nameInDb = "type")
    private int type;//这个消息是什么类型
    @JsonField(name = "content")
    @Property(nameInDb = "content")
    private String content;//消息的内容
    @JsonField(name = "updateTime")
    @Property(nameInDb = "updateTime")
    private Long updateTime;//消息的发送时间
    @JsonField(name = "status")
    @Property(nameInDb = "status")
    private int status;//消息的发送状态
    @Generated(hash = 11178106)
    public Message(Long id, String sessionId, String fromAccount,
            String receivedAccount, int type, String content, Long updateTime,
            int status) {
        this.id = id;
        this.sessionId = sessionId;
        this.fromAccount = fromAccount;
        this.receivedAccount = receivedAccount;
        this.type = type;
        this.content = content;
        this.updateTime = updateTime;
        this.status = status;
    }
    @Generated(hash = 637306882)
    public Message() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getFromAccount() {
        return this.fromAccount;
    }
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }
    public String getReceivedAccount() {
        return this.receivedAccount;
    }
    public void setReceivedAccount(String receivedAccount) {
        this.receivedAccount = receivedAccount;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
