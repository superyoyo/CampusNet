package com.campus.chat.internal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.campus.chat.internal.database.model.DaoMaster;
import com.campus.chat.internal.database.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class DBUtil {
    private MyOpenHelper mMyOpenHelperl;
    private Database db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static class InstanceHolder{
        private static DBUtil sInstance = new DBUtil();
    }

    public static DBUtil getInstance(){
        return InstanceHolder.sInstance;
    }

    public DBUtil() {}

    public void init(Context context){
        mMyOpenHelperl = new MyOpenHelper(context, "chat-db", null);
        db = mMyOpenHelperl.getEncryptedWritableDb("15513164627");
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(mMyOpenHelperl.getEncryptedReadableDb("15513164627"));
        mDaoSession = mDaoMaster.newSession();
    }

    public Database getDb() {
        return db;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    private class MyOpenHelper extends DaoMaster.OpenHelper {

        public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            super.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
