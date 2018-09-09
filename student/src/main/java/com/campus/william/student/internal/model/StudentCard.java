package com.campus.william.student.internal.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class StudentCard {
    @JsonField(name = "userId")
    private String mUserId;
    @JsonField(name = "studentId")
    private String mStudentId;
    @JsonField(name = "head")
    private String mHead;
    @JsonField(name = "name")
    private String mName;
    @JsonField(name = "schoolName")
    private String mSchoolName;
    @JsonField(name = "distance")
    private String mDistance;
    @JsonField(name = "cover")
    private String mCover;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        mStudentId = studentId;
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

    public String getSchoolName() {
        return mSchoolName;
    }

    public void setSchoolName(String schoolName) {
        mSchoolName = schoolName;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }
}
