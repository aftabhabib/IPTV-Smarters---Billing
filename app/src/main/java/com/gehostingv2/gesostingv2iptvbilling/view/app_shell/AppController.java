package com.gehostingv2.gesostingv2iptvbilling.view.app_shell;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.gehostingv2.gesostingv2iptvbilling.BuildConfig;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;


import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AppController extends Application {

    public static AppController sInstance;
    protected String userAgent;

    /**
     * Gets the instance of AppController throughout the App
     *
     * @return AppController
     */
    public static AppController getInstance() {
        return sInstance;
    }

    /**
     * Get the context from AppController   throughout the App
     *
     * @return Context
     */
    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    /**
     * Gets the instance of AppController throughout the App(Sync)
     *
     * @return Application
     */
    public static synchronized AppController getInstanceSyn() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        MultiDex.install(this);

        sInstance = this;
        userAgent = Util.getUserAgent(this, getString(R.string.app_name));

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(AppConst.PACKAGE_NMAE)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);


    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(this, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }

    public boolean useExtensionRenderers() {
        return BuildConfig.FLAVOR.equals("withExtensions");
    }

    @Override
    public void onLowMemory() {
        Glide.get(this).clearMemory();
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Glide.get(this).trimMemory(level);
        super.onTrimMemory(level);
    }
}
