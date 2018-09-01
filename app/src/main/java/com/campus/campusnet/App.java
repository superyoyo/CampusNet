package com.campus.campusnet;

import android.app.Application;
import android.os.HandlerThread;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.campus.android_bind.util.ImageLoader;
import com.campus.event_filter.filter.IServelet;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread io = new HandlerThread("IO");
        HandlerThread camputation = new HandlerThread("Camputation");
        io.start();
        camputation.start();
        IServelet.getInstance().init(this, getMainLooper(), io.getLooper(), camputation.getLooper());
        ImageLoader.getInstance().setListener(new ImageLoader.Listener() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Glide.with(getApplicationContext()).load(url).into(imageView);
            }
        });
    }
}
