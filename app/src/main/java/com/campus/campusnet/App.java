package com.campus.campusnet;

import android.app.Application;
import android.os.HandlerThread;
import android.widget.ImageView;

import com.campus.android_bind.util.ImageLoader;
import com.campus.campusnet.model.User;
import com.campus.event_filter.filter.IServelet;
import com.campus.william.user.internal.storage.StorageFactory;
import com.squareup.picasso.Picasso;

import java.io.File;

public class App extends Application {
    private User mUser;
    private static App sInstance;
    private boolean mInit;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App getInstance(){
        return sInstance;
    }

    public void init(){
        if(mInit){
            return;
        }
        mInit = true;
        StorageFactory.getInstance().init(this);
        HandlerThread io = new HandlerThread("IO");
        HandlerThread camputation = new HandlerThread("Camputation");
        io.start();
        camputation.start();
        IServelet.getInstance().init(this, getMainLooper(), io.getLooper(), camputation.getLooper());

        ImageLoader.getInstance().setListener(new ImageLoader.Listener() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                if(url.startsWith("http://") || url.startsWith("https://")){
                    Picasso.get().load(url).into(imageView);
                }else if(url.startsWith("file://")){
                    Picasso.get().load(new File(url)).into(imageView);
                }else{
                    Picasso.get().load(Integer.valueOf(url)).into(imageView);
                }
            }
        });
    }

    public void setUser(User user){
        mUser = user;
    }

    public User currentUser(){
        return mUser;
    }
}
