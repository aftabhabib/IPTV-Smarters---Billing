package com.gehostingv2.gesostingv2iptvbilling.miscelleneious.loadlivemovieepgdata;

import android.app.Application;
import android.content.res.Configuration;

public class MultiDexApplication  extends Application {

    private static MultiDexApplication singleton;

    public static MultiDexApplication getInstance(){
        return singleton;
    }
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        singleton = this;
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
//        Foreground.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}