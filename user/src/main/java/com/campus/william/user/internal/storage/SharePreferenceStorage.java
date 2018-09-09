package com.campus.william.user.internal.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;

public class SharePreferenceStorage extends Storage{

    public SharePreferenceStorage(Context context, String stragegy) {
        super(context, stragegy);
    }

    @Override
    public void insert(String ID, Object obj) throws IOException{
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ID, LoganSquare.serialize(obj));
        editor.apply();
    }

    @Override
    public void delete(String ID) throws IOException{
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(ID);
        editor.apply();
    }

    @Override
    public void update(String ID, Object object) throws IOException{
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        try {
            editor.putString(ID, LoganSquare.serialize(object));
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T query(Class<T> clazz, String ID) throws IOException{
        try {
            String content = mSharedPreferences.getString(ID, "");
            T t = LoganSquare.parse(content, clazz);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
