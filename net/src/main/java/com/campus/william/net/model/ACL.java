package com.campus.william.net.model;

import android.util.SparseArray;

import java.util.HashMap;

public class ACL {
    private boolean mIsPublicRead;
    private boolean mIsPublicWrite;
    private HashMap<String, Boolean> mReadAccess;
    private HashMap<String, Boolean> mWriteAccess;

    public ACL() {
        mIsPublicRead = true;
        mIsPublicWrite = true;
        mReadAccess = new HashMap<>();
        mWriteAccess = new HashMap<>();
    }

    public void setPublicReadAccess(boolean access){
        mIsPublicRead = access;
    }

    public void setReadAccess(String userId, boolean access){
        mReadAccess.put(userId, access);
    }

    public void setPublicWriteAccess(boolean access){
        mIsPublicWrite = access;
    }

    public void setWriteAccess(String userId, boolean access){
        mWriteAccess.put(userId, access);
    }


}
