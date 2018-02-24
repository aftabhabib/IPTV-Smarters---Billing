package com.gehostingv2.gesostingv2iptvbilling.view;

import android.content.Context;
import android.content.SharedPreferences;

import com.gehostingv2.gesostingv2iptvbilling.R;


public class AppPref {

    private static AppPref appPref;
    private static Context appContext;

    // class member
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    /**
     * Initialize object
     */
    public AppPref() {
        preferences = appContext.getSharedPreferences(appContext.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Singleton method to get object
     *
     * @return {@link AppPref}
     */
    public static AppPref getInstance(Context appContext) {
        AppPref.appContext = appContext;
        if (appPref != null) {
            return appPref;
        }

        appPref = new AppPref();
        return appPref;
    }

    public int getResizeMode() {
        return preferences.getInt("resize_mode", 0);
    }

    public void setResizeMode(int mode) {
        editor.putInt("resize_mode", mode);
        editor.commit();
    }

}
