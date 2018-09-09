package com.campus.campusnet.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class User {
    @JsonField(name = "userId")
    private String mUserId;
    @JsonField(name = "head")
    private String mHead;
    @JsonField(name = "name")
    private String mName;
    @JsonField(name = "sex")
    private String mSex;
    @JsonField(name = "age")
    private int mAge;
    @JsonField(name = "sign")
    private String mSign;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getHead() {
        return mHead;
    }

    public void setHead(String head) {
        mHead = head;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        mSign = sign;
    }
}
