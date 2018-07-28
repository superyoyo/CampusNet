package com.campus.campusnet;

import android.app.Application;
import android.os.HandlerThread;

import com.campus.event_filter.filter.IServelet;
import com.campus.william.user.UserManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread io = new HandlerThread("IO");
        HandlerThread camputation = new HandlerThread("Camputation");
        io.start();
        camputation.start();
        IServelet.getInstance().init(this, getMainLooper(), io.getLooper(), camputation.getLooper());
        UserManager.getInstance().init();
    }
}
