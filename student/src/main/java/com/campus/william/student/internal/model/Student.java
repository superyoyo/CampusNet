package com.campus.william.student.internal.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class Student {
    public static final String STUDENT_ID = "student_ID";

    @JsonField(name = "userId")
    private String mUserId;
    @JsonField(name = "account")
    private String mAccount;
    @JsonField(name = "schoolId")
    private String mSchoolId;
    @JsonField(name = "schoolName")
    private String mSchoolName;
    @JsonField(name = "majorId")
    private String mMajorId;
    @JsonField(name = "majorName")
    private String mMajorName;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    public String getSchoolId() {
        return mSchoolId;
    }

    public void setSchoolId(String schoolId) {
        mSchoolId = schoolId;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public void setSchoolName(String schoolName) {
        mSchoolName = schoolName;
    }

    public String getMajorId() {
        return mMajorId;
    }

    public void setMajorId(String majorId) {
        mMajorId = majorId;
    }

    public String getMajorName() {
        return mMajorName;
    }

    public void setMajorName(String majorName) {
        mMajorName = majorName;
    }
}
