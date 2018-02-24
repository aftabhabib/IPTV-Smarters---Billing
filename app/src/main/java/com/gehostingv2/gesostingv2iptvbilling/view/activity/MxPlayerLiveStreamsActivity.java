package com.gehostingv2.gesostingv2iptvbilling.view.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class MxPlayerLiveStreamsActivity extends AppCompatActivity {
    private String mFilePath;
    private Uri contentUri;
    private Context context;

    private SharedPreferences loginPreferencesSharedPref;
    private static final String _MX_PLAYER_PACKAGE_NAME = "com.mxtech.videoplayer.ad";
    private static final String _MX_PLAYER_CLASS_NAME = _MX_PLAYER_PACKAGE_NAME + ".ActivityScreen";
    private static final String _MX_PLAYER_PACKAGE_NAME_PRO = "com.mxtech.videoplayer.pro";
    private static final String _MX_PLAYER_CLASS_NAME_PRO = _MX_PLAYER_PACKAGE_NAME_PRO + ".ActivityScreen";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        changeStatusBarColor();
        initialize();
    }

    private void initialize() {
        context = this; 
        loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
        String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
        String allowedFormat = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        String serverUrl = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String serverPort = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        int opened_stream_id = getIntent().getIntExtra("OPENED_STREAM_ID",0);
        String streamType = getIntent().getStringExtra("STREAM_TYPE");

        mFilePath = "http://"+serverUrl+":"+serverPort+"/"+AppConst.STREAM_TYPE_LIVE+"/"+username+"/"+password+"/"+opened_stream_id+"."+allowedFormat;
        contentUri = Uri.parse(mFilePath);

        boolean isAppInstalled = appInstalledOrNot("com.mxtech.videoplayer.pro");
        if (isAppInstalled) {
            //This intent will help you to launch if the package is already installed
            if (context != null) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName(_MX_PLAYER_PACKAGE_NAME_PRO, _MX_PLAYER_CLASS_NAME_PRO);
                    intent.putExtra("package", getPackageName());
                    intent.setDataAndType(contentUri, "application/x-mpegURL");
                    intent.setPackage(_MX_PLAYER_PACKAGE_NAME_PRO);
                    startActivity(intent);
                    finish();
                }
                catch (ActivityNotFoundException e) {
                    // MX Player app is not installed, let's ask the user to install it.
                    mxPlayerNotFoundDialogBox();
                }
            }
        } else {
            try {
                Intent intent = new Intent();
                intent.setClassName(_MX_PLAYER_PACKAGE_NAME, _MX_PLAYER_CLASS_NAME);
                intent.putExtra("package", getPackageName());
                intent.setDataAndType(contentUri, "application/x-mpegURL");
                intent.setPackage("com.mxtech.videoplayer.pro");
                startActivity(intent);
                finish();
            } catch (ActivityNotFoundException e) {
                // MX Player app is not installed, let's ask the user to install it.
                mxPlayerNotFoundDialogBox();
            }
        }
    }
    private boolean appInstalledOrNot(String uri) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
            }
        } else {
            return false;
        }
        return false;
    }


    public void mxPlayerNotFoundDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Media Player");
        builder.setMessage("MX Player needs to be installed!");
        builder.setPositiveButton("Install it",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id)
                    {
                        try {
                            // try to open Google Play app first
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + _MX_PLAYER_PACKAGE_NAME)));
                        } catch (ActivityNotFoundException e) {
                            // if Google Play is not found for some reason, let's open browser
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + _MX_PLAYER_PACKAGE_NAME)));
                            }catch(ActivityNotFoundException exception){
                                Utils.showToast(context, String.valueOf(exception));
                            }
                        }
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id)
                    {
                        // if cancelled - just close the app
                        finish();
                    }
                });
        builder.setCancelable(false);
        builder.create().show();
    }
    private void changeStatusBarColor() {

        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }
}
