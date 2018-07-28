package com.campus.william.user;

import com.campus.event_filter.logic.LogicFactory;
import com.campus.william.user.internal.logic.LoginLogic;
import com.campus.william.user.internal.logic.RegisteLogic;

public class UserManager {
    private static class InstanceHolder{
        public static UserManager sInstance = new UserManager();
    }

    public static UserManager getInstance(){
        return InstanceHolder.sInstance;
    }

    private boolean mInit;

    private UserManager() {}

    public void init(){
        if(mInit){
            return;
        }
        mInit = true;
        LogicFactory.getInstance().registeLogic(1, RegisteLogic.class);
        LogicFactory.getInstance().registeLogic(2, LoginLogic.class);
    }
}
