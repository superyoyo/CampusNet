package com.campus.william.user.internal.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

public abstract class Storage {
    private Context mContext;
    protected SharedPreferences mSharedPreferences;
    private String mStragegy;

    public Storage(Context context, String stragegy) {
        mContext = context;
        mStragegy = stragegy;
        mSharedPreferences = mContext.getSharedPreferences(mStragegy, Context.MODE_PRIVATE);
    }

    protected String getStragegy() {
        return mStragegy;
    }

    public abstract void insert(String ID, Object obj) throws IOException;

    public abstract void delete(String ID) throws IOException;

    public abstract void update(String ID, Object object) throws IOException;

    public abstract <T> T query(Class<T> clazz, String ID) throws IOException;
}
