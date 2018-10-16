package com.campus.chat.internal.database.model;

public interface MessageStatus {
    public final static int NONE = 0;//还未发送
    public final static int SENDING = 1;//发送中
    public final static int SUCCESS = 2;//发送成功
    public final static int FAILED = 3;//发送失败
}
