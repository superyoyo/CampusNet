package com.campus.william.student.internal.storage;

import android.app.Application;
import android.content.Context;

public class StorageFactory {
    private Context mContext;
    private Storage mStorage;

    private StorageFactory() {}

    private static class InstanceHolder{
        private static StorageFactory sIntance = new StorageFactory();
    }

    public static StorageFactory getInstance(){
        return InstanceHolder.sIntance;
    }

    public void init(Application context){
        mContext = context;
    }

    public Storage obtainStorage(String stragegy){
        if(mStorage != null && mStorage.getStragegy().equals(stragegy)){
            return mStorage;
        }
        switch (stragegy){
            case Stragegy.XML:
                mStorage = new SharePreferenceStorage(mContext, stragegy);
                break;
            case Stragegy.DATA_BASE:
                mStorage = new SharePreferenceStorage(mContext, stragegy);
                break;
            case Stragegy.FILE:
                mStorage = new SharePreferenceStorage(mContext, stragegy);
                break;
        }

        return mStorage;
    }

    public interface Stragegy{
        public final static String XML = "xml";
        public final static String FILE = "file";
        public final static String DATA_BASE = "database";
    }
}
