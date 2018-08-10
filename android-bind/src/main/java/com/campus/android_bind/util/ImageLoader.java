package com.campus.android_bind.util;

import android.widget.ImageView;

public class ImageLoader {
    private Listener mListener;
    private static class InstanceHolder{
        public static ImageLoader sInstance = new ImageLoader();
    }

    public static ImageLoader getInstance(){
        return InstanceHolder.sInstance;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void loadImage(ImageView imageView, String url){
        mListener.loadImage(imageView, url);
    }

    public interface Listener{
        public void loadImage(ImageView imageView, String url);
    }
}
