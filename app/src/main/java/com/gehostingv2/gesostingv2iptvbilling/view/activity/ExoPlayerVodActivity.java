package com.gehostingv2.gesostingv2iptvbilling.view.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;


import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.presenter.LoginIPTVPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LoginIPTVInterface;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.crashlytics.android.Crashlytics;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.LoadingSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


public class ExoPlayerVodActivity extends Activity implements LoginIPTVInterface, View.OnClickListener {
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    private SharedPreferences loginPreferencesSharedPref;
    private String mFilePath;
    private Button retryButton;
    VideoView videoView = null;
    private Context context;
    private LoginIPTVPresenter loginPresenter;
    private ProgressDialog progressDialog;

    public LoadingSpinner video_loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exoplayer_vod);
        ButterKnife.bind(this);
        changeStatusBarColor();
        initialize();
    }

    private void initialize() {
        context = this;
        loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
        String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
        String serverUrl = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String serverPort = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        int opened_stream_id = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
        String containerExtension = getIntent().getStringExtra("CONTAINER_EXTENSION");
        String streamType = getIntent().getStringExtra("STREAM_TYPE");
        mFilePath = "http://" + serverUrl + ":" + serverPort + "/" + AppConst.STREAM_TYPE_MOVIE + "/" + username + "/" + password + "/" + opened_stream_id + "." + containerExtension;

        videoView = (VideoView) findViewById(R.id.videoView);
        retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);
        video_loader = (LoadingSpinner) findViewById(R.id.iv_video_loader);
        preparePlayer();
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

    public void preparePlayer() {
        //Creating MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri = Uri.parse(mFilePath);
        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        retryButton.setVisibility(View.GONE);
//        atStart();
        video_loader.setVisibility(View.VISIBLE);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        video_loader.setVisibility(View.GONE);
//                        onFinish();
                        mp.start();
                    }
                });
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                video_loader.setVisibility(View.GONE);
//                onFinish();
                retryButton.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view == retryButton) {
            preparePlayer();
        }
    }

    @Override
    public void validateLogin(ValidationIPTVCallback loginCallback, String validateLogin) {
        if (progressDialog != null)
            progressDialog.dismiss();
        if (loginCallback != null) {
            Integer auth = loginCallback.getUserLoginInfo().getAuth();
            if (auth == 1) {
                String userStatus = loginCallback.getUserLoginInfo().getStatus();
                if (!userStatus.equals("Active")) {
                    if (context != null)
                        Utils.showToast(context, getResources().getString(R.string.invalid_status) + userStatus);
                    logoutUser();
                }
            }
        }
    }

    private void logoutUser() {
//        Toast.makeText(this, AppConst.LOGGED_OUT, Toast.LENGTH_SHORT).show();
//        Intent intentLogout = new Intent(this, LoginWelcomeActivity.class);
//        SharedPreferences loginPreferences;
//        SharedPreferences.Editor loginPreferencesEditor;
//        loginPreferences = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
//        loginPreferencesEditor = loginPreferences.edit();
//        loginPreferencesEditor.clear();
//        loginPreferencesEditor.commit();
//        startActivity(intentLogout);
//

        SharedPreferences loginPreferences;
        SharedPreferences.Editor loginPreferencesEditor;
        loginPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        loginPreferencesEditor = loginPreferences.edit();
        loginPreferencesEditor.clear();
        loginPreferencesEditor.commit();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intentLogout = new Intent(this, LoginWelcomeActivity.class);
        SharedPreferences loginPreferencesClientid;
        SharedPreferences.Editor loginPreferencesClientidEditor;
        loginPreferencesClientid = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        loginPreferencesClientidEditor = loginPreferencesClientid.edit();
        loginPreferencesClientidEditor.clear();
        loginPreferencesClientidEditor.commit();
        startActivity(intentLogout);
        finish();
    }

    @Override
    public void atStart() {
        if (pbLoader != null)
            pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        if (pbLoader != null)
            pbLoader.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFailed(String message) {
        Utils.showToast(context, AppConst.NETWORK_ERROR_OCCURED);

    }

    @Override
    public void onResume() {
        super.onResume();
        context = this;
        loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        if (loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "").equals("") && loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "").equals("")) {
            Intent intent = new Intent(this, LoginWelcomeActivity.class);
            startActivity(intent);
        } else {
            loginPresenter = new LoginIPTVPresenter(ExoPlayerVodActivity.this, context);
            loginPresenter.validateLogin(loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, ""), loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, ""));
        }
    }
}