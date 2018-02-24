package com.gehostingv2.gesostingv2iptvbilling.view.player;


/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.view.AppPref;
import com.gehostingv2.gesostingv2iptvbilling.view.app_shell.AppController;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;


import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;

//import com.nst.iptv.view.api_model.XC_LiveStream;
//import com.nst.iptv.view.database.User;
//import app.xcplayer.com.nst.fragments.RadioFragment;
//import app.xcplayer.com.nst.services.RadioService;
//import com.nst.iptv.view.utils.Utility;

//import static app.xcplayer.com.nst.activity.ChannelsActivity.KEY_CATEGORY_ID;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class PlayerVODActivity extends Activity implements OnClickListener, ExoPlayer.EventListener,
        PlaybackControlView.VisibilityListener, AudioManager.OnAudioFocusChangeListener {

    public static final String KEY_CATEGORY_ID = "com.nst.category.id";
    // App
    public static final String ACTION_VIEW = "com.nst.exoplayer.app.action.VIEW";
    public static final String EXTENSION_EXTRA = "extension";
    public static final String EXTENSION_TYPE = "com.nst.exoplayer.app.extension_type";
    public static final String IS_VALID_URL =
            "com.nst.exoplayer.app.is_valid_url";
    public static final String STREAM_TYPE =
            "com.nst.exoplayer.app.is_stream_type";
    public static final String VIDEO_TITLE =
            "com.nst.exoplayer.app.video.title";
    public static final String VIDEO_ID =
            "com.nst.exoplayer.app.video._id";
    public static final String VIDEO_NUM =
            "com.nst.exoplayer.app.video._num";
    /// Exo Player
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String DRM_LICENSE_URL = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES = "drm_key_request_properties";
    public static final String PREFER_EXTENSION_DECODERS = "prefer_extension_decoders";
    public static final String ACTION_VIEW_LIST =
            "com.nst.exoplayer.app.action.VIEW_LIST";
    public static final String URI_LIST_EXTRA = "uri_list";
    public static final String EXTENSION_LIST_EXTRA = "extension_list";
    private static final Map<Integer, Integer> RESIZE_MODE =
            Collections.unmodifiableMap(new HashMap<Integer, Integer>() {{
                put(AspectRatioFrameLayout.RESIZE_MODE_FILL, R.drawable.ic_center_focus_strong_black_24dp);
                put(AspectRatioFrameLayout.RESIZE_MODE_FIT, R.drawable.ic_fullscreen_black_24dp);
                put(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH, R.drawable.ic_center_focus_strong_black_24dp);
                put(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT, R.drawable.ic_zoom_out_map_black_24dp);
            }});
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final CookieManager DEFAULT_COOKIE_MANAGER;
    private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private TrackGroupArray lastSeenTrackGroupArray;
    private int CURRENT_RESIZE_MODE = 0;
    private int video_id;
    private int video_num;
    private String container_extension;
    private String stream_type;
    private ProgressBar progress;
    private AppCompatTextView video_title;
    private AudioManager mAudioManager;
    private TelephonyManager mgr;
    private int isBanner = 0;
    private Handler mainHandler;
    private EventLogger eventLogger;
    private SimpleExoPlayerView simpleExoPlayerView;
    private LinearLayout debugRootView;
    private TextView debugTextView;
    private Button retryButton;
    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private TrackSelectionHelper trackSelectionHelper;
    private DebugTextViewHelper debugViewHelper;
    private boolean needRetrySource;
    private boolean shouldAutoPlay;
    private int resumeWindow;
    private long resumePosition;
    private AppCompatImageView btn_settings;
    private AppCompatImageView btn_sub;
//    private RealmResults<XC_LiveStream> Xc_liveStreams;
//    private User user;
    private Realm realm;
    private AppCompatImageView btn_screen;
    private SharedPreferences loginPreferencesSharedPref;
    LiveStreamDBHandler liveStreamDBHandler;
    private ArrayList<LiveStreamsDBModel> allStreams;


    // Activity lifecycle
    private PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                //Incoming call: Pause music
                releasePlayer();
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                //Not in call: Play music
                initializePlayer();
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                //A call is dialing, active or on hold
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }

        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        /////////////////////////////////////////////////////////
//        Intent stopIntent = new Intent(this, RadioService.class);
//        stopIntent.setAction(RadioService.STOPFOREGROUND_ACTION);
//        startService(stopIntent);
        ////////////////////////////////////////////////////////

        liveStreamDBHandler = new LiveStreamDBHandler(this);
//        allStreams =
//                liveStreamDBHandler.getAllLiveStreasWithCategoryId("0", "live");



        stream_type = getIntent().getStringExtra(STREAM_TYPE);
        mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        if (mgr != null) {
//            try {
//                mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
//            }catch (SecurityException e){
//                showToast(e.getMessage());
//            }
//        }

        video_id = getIntent().getIntExtra(VIDEO_ID, 0);
        video_num = getIntent().getIntExtra(VIDEO_NUM, 0);
//        if(video_num!=0){
//            video_num--;
//        }


        shouldAutoPlay = true;
        clearResumePosition();
        mediaDataSourceFactory = buildDataSourceFactory(true);
        mainHandler = new Handler();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        setContentView(R.layout.player_vod_activity);
        View rootView = findViewById(R.id.root);
//        rootView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE
//                        || keyCode == KeyEvent.KEYCODE_MENU) {
//                    return false;
//                }
//                if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_CHANNEL_UP) {
//                   findViewById(R.id.exo_next).performClick();
//                 }
//                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN) {
//                    findViewById(R.id.exo_prev).performClick();
//                }
//                return true;
//            }
//        });


//        rootView.setOnClickListener(this);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        progress = (ProgressBar) findViewById(R.id.progress);
        debugRootView = (LinearLayout) findViewById(R.id.controls_root);
        debugTextView = (TextView) findViewById(R.id.debug_text_view);
        retryButton = (AppCompatButton) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);
        if (progress != null) progress.setVisibility(View.VISIBLE);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.setControllerVisibilityListener(this);
        simpleExoPlayerView.requestFocus();
        CURRENT_RESIZE_MODE = AppPref.getInstance(this).getResizeMode();
        simpleExoPlayerView.setResizeMode((Integer) RESIZE_MODE.keySet().toArray()[CURRENT_RESIZE_MODE]);
        video_title = (AppCompatTextView) findViewById(R.id.title);
        btn_settings = (AppCompatImageView) findViewById(R.id.btn_settings);
        AppCompatImageView btn_back = (AppCompatImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_settings.setOnClickListener(this);
        btn_sub = (AppCompatImageView) findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(this);
//        findViewById(R.id.exo_next).setOnClickListener(this);
//        findViewById(R.id.exo_prev).setOnClickListener(this);
        btn_screen = (AppCompatImageView) findViewById(R.id.btn_screen);
        btn_screen.setImageResource(RESIZE_MODE.get(CURRENT_RESIZE_MODE));
        btn_screen.setOnClickListener(this);
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_UP:
//                findViewById(R.id.exo_next).performClick();
//                 return true;
//            case KeyEvent.KEYCODE_CHANNEL_UP:
//                findViewById(R.id.exo_next).performClick();
//                return true;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                findViewById(R.id.exo_prev).performClick();
//                 return true;
//            case KeyEvent.KEYCODE_CHANNEL_DOWN:
//                findViewById(R.id.exo_prev).performClick();
//                 return true;
//            default:
//                return super.onKeyUp(keyCode, event);
//        }
//    }

    @Override
    public void onNewIntent(Intent intent) {
        releasePlayer();
        shouldAutoPlay = true;
        clearResumePosition();
        setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (realm == null || realm.isClosed()) realm = Realm.getDefaultInstance();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
        realm.close();
    }

    // Activity input

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializePlayer();
        } else {
            showToast(R.string.storage_permission_denied);
            finish();
        }
    }

    // OnClickListener methods

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Show the controls on any key event.
        simpleExoPlayerView.showController();
        // If the event was not handled then see if the player view can handle it as a media key event.
        return super.dispatchKeyEvent(event) || simpleExoPlayerView.dispatchMediaKeyEvent(event);
    }

    // PlaybackControlView.VisibilityListener implementation

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_settings:
                try {
                    MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                    if (mappedTrackInfo != null) {
                        trackSelectionHelper.showSelectionDialog(this, "Select",
                                trackSelector.getCurrentMappedTrackInfo(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onClick: " + e.getMessage());
                }

                break;
            case R.id.retry_button:
                initializePlayer();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_sub:
                try {
                    MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                    if (mappedTrackInfo != null) {
                        trackSelectionHelper.showSelectionDialog(this, "Audio",
                                trackSelector.getCurrentMappedTrackInfo(), C.TRACK_TYPE_AUDIO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onClick: " + e.getMessage());
                }
                break;
            case R.id.exo_prev:
//                previousLive();
//                int indexPrev = player.getCurrentWindowIndex();
//                Log.e("TAG", "indexPrev: " + indexPrev);
//                if (indexPrev >= 0) {
//                    video_title.setText(allStreams.get(indexPrev).getName());
//                    if (needRetrySource) {
//                        clearResumePosition();
//                        getIntent().putExtra(VIDEO_ID, allStreams.get(indexPrev).getStreamId());
//                        initializePlayer();
//                    }
//                }
                break;
            case R.id.exo_next:
//                next();
//                int indexNext = player.getCurrentWindowIndex();
//                Log.e("TAG", "indexNext: " + indexNext);
//                if (indexNext <= allStreams.size() - 1) {
//                    video_title.setText(allStreams.get(indexNext).getName());
//                    if (needRetrySource) {
//                        clearResumePosition();
//                        getIntent().putExtra(VIDEO_ID, allStreams.get(indexNext).getStreamId());
//                        initializePlayer();
//                    }
//                }

                break;

            case R.id.btn_screen:
                toggleResizeMode();
                break;
        }


       /* if (view == retryButton) {

        } else if (view.getParent() == debugRootView) {
            MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                trackSelectionHelper.showSelectionDialog(this, ((Button) view).getText(),
                        trackSelector.getCurrentMappedTrackInfo(), (int) view.getTag());
            }
        }
        if (view == retryButton) {
            initializePlayer();
        } else if (view.getParent() == debugRootView) {
            MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                trackSelectionHelper.showSelectionDialog(this, ((Button) view).getText(),
                        trackSelector.getCurrentMappedTrackInfo(), (int) view.getTag());
            }
        }*/
    }

    /**
     * Toggle screen ratio mode
     */
    private void toggleResizeMode() {

        CURRENT_RESIZE_MODE = CURRENT_RESIZE_MODE + 1;
        if (CURRENT_RESIZE_MODE >= RESIZE_MODE.size()) {
            CURRENT_RESIZE_MODE = 0;
        }
        AppPref.getInstance(PlayerVODActivity.this).setResizeMode(CURRENT_RESIZE_MODE);
        simpleExoPlayerView.setResizeMode((Integer) RESIZE_MODE.keySet().toArray()[CURRENT_RESIZE_MODE]);
        btn_screen.setImageResource(RESIZE_MODE.get(CURRENT_RESIZE_MODE));
    }

    /***
     * Play previous track if any
     */
    private void previous() {
        Timeline currentTimeline = simpleExoPlayerView.getPlayer().getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return;
        }
        int currentWindowIndex = simpleExoPlayerView.getPlayer().getCurrentWindowIndex();

        Timeline.Window currentWindow = currentTimeline.getWindow(currentWindowIndex, new Timeline.Window());
        if (currentWindowIndex > 0 && (player.getCurrentPosition() <= MAX_POSITION_FOR_SEEK_TO_PREVIOUS
                || (currentWindow.isDynamic && !currentWindow.isSeekable))) {
            player.seekTo(currentWindowIndex - 1, C.TIME_UNSET);
        } else {
            player.seekTo(0);
        }
    }
    private void previousLive() {
        Timeline currentTimeline = simpleExoPlayerView.getPlayer().getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return;
        }
        int currentWindowIndex = simpleExoPlayerView.getPlayer().getCurrentWindowIndex();
        if(currentWindowIndex == 0){
            player.seekTo(allStreams.size() - 1, C.TIME_UNSET);
            return;
        }
//        int currentWindowIndex = video_num;
//        if (currentWindowIndex < currentTimeline.getWindowCount() - 1) {
            player.seekTo(currentWindowIndex - 1, C.TIME_UNSET);
//        } else if (currentTimeline.getWindow(currentWindowIndex, new Timeline.Window(), false).isDynamic) {
//            player.seekTo(currentWindowIndex, C.TIME_UNSET);
//        }
    }

    /***
     * Play next track if any
     */
    private void next() {
        Timeline currentTimeline = simpleExoPlayerView.getPlayer().getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return;
        }
        int currentWindowIndex = simpleExoPlayerView.getPlayer().getCurrentWindowIndex();
        if(currentWindowIndex == allStreams.size()-1){
            player.seekTo(0, C.TIME_UNSET);
            return;
        }
//        int currentWindowIndex = video_num;
//        if (currentWindowIndex < currentTimeline.getWindowCount() - 1) {
            player.seekTo(currentWindowIndex + 1, C.TIME_UNSET);
//        } else if (currentTimeline.getWindow(currentWindowIndex, new Timeline.Window(), false).isDynamic) {
//            player.seekTo(currentWindowIndex, C.TIME_UNSET);
//        }
    }


    /***
     * Build stream url
     * @param stream_type
     * @param stream_id
     * @param container_extension
     * @return
     */
    private Uri buildURI(String stream_type, int stream_id, String container_extension) {
        loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
        String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
        String allowedFormat = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        String serverUrl = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String serverPort = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");

        Uri.Builder builder = new Uri.Builder();
        try {
                builder.scheme("http")
                        .encodedAuthority(serverUrl+":"+serverPort)
                        .appendPath(stream_type)
                        .appendPath(username)
                        .appendPath(password)
                        .appendPath(Integer.toString(stream_id) + "."+ container_extension);
                return builder.build();

        } catch (Exception e) {
            Log.e("DB", "initializePlayer: " + e.getMessage());
        }

        return null;
    }
    // Internal methods

    @Override
    public void onVisibilityChange(int visibility) {
        debugRootView.setVisibility(visibility);
    }

//    private int getIndex(final int video_id) {
//        int index = 0;
//        XC_LiveStream stream = Xc_liveStreams.where().equalTo("stream_id", video_id).findFirst();
//        if (stream != null)
//            index = Xc_liveStreams.indexOf(stream);
//        return index;
//    }

    /***
     * InitializePlayer
     */
    private void initializePlayer() {
        Intent intent = getIntent();

        video_id = intent.getIntExtra(VIDEO_ID, 0);
        stream_type = intent.getStringExtra(STREAM_TYPE);
        container_extension = intent.getStringExtra(EXTENSION_TYPE);
        boolean needNewPlayer = player == null;
//        int category_id = getIntent().getIntExtra(KEY_CATEGORY_ID, 0);
        if (realm == null || realm.isClosed()) realm = Realm.getDefaultInstance();
//        Xc_liveStreams = realm.where(XC_LiveStream.class).notEqualTo("stream_type", RadioFragment.RADIO_STREAM).equalTo("category_id", category_id).findAll();
//        Xc_liveStreams = realm.where(XC_LiveStream.class).equalTo("category_id", category_id).findAll();
//        user = realm.where(User.class).findFirst();
        if (video_title != null) {
            String vtitle = intent.getStringExtra(VIDEO_TITLE);
            if (!Utils.isEmpty(vtitle)) video_title.setText(vtitle);
        }
        if (needNewPlayer) {
            boolean preferExtensionDecoders = intent.getBooleanExtra(PREFER_EXTENSION_DECODERS, false);
            UUID drmSchemeUuid = intent.hasExtra(DRM_SCHEME_UUID_EXTRA)
                    ? UUID.fromString(intent.getStringExtra(DRM_SCHEME_UUID_EXTRA)) : null;
            DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
            if (drmSchemeUuid != null) {
                String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL);
                String[] keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES);
                try {
                    drmSessionManager = buildDrmSessionManager(drmSchemeUuid, drmLicenseUrl,
                            keyRequestPropertiesArray);
                } catch (UnsupportedDrmException e) {
                    int errorStringId = Util.SDK_INT < 18 ? R.string.error_drm_not_supported
                            : (e.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME
                            ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown);
                    showToast(errorStringId);
                    return;
                }
            }

            @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
                    ((AppController) getApplication()).useExtensionRenderers()
                            ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this, drmSessionManager, extensionRendererMode);
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            trackSelectionHelper = new TrackSelectionHelper(trackSelector, videoTrackSelectionFactory);
            lastSeenTrackGroupArray = null;

            DefaultAllocator defaultAllocator = new DefaultAllocator(true, 1024);
            LoadControl loadControl = new DefaultLoadControl(defaultAllocator // allocator
                    , 15000 // Min buffer duration
                    , 30000 // Max buffer duration
                    , 2500 // min buffer to start playback
                    , 5000 // min buffer to resume playback
            );

            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            player.addListener(this);

            eventLogger = new EventLogger(trackSelector);
            player.addListener(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);
            player.setMetadataOutput(eventLogger);
            simpleExoPlayerView.setPlayer(player);
            player.setPlayWhenReady(shouldAutoPlay);
            debugViewHelper = new DebugTextViewHelper(player, debugTextView);
            debugViewHelper.start();
        }
        if (needNewPlayer || needRetrySource) {
            String action = intent.getAction();
            Uri[] uris;
            String[] extensions;

            if (ACTION_VIEW.equals(action)) {


                Uri stream_uri = buildURI(stream_type, video_id,container_extension);


                uris = new Uri[]{stream_uri};
                extensions = new String[uris.length];
//                findViewById(R.id.exo_next).setEnabled(false);
//                findViewById(R.id.exo_prev).setEnabled(false);
            } else if (ACTION_VIEW_LIST.equals(action)) {
                uris = new Uri[allStreams.size()];
                extensions = new String[allStreams.size()];
                for (int i = 0; i < allStreams.size(); i++) {
                    uris[i] = buildURI(stream_type, Integer.parseInt(allStreams.get(i).getStreamId()),"ts");
                 }

            } else {
                showToast(getString(R.string.unexpected_intent_action, action));
                return;
            }
            /////////////////////////////////////////////////////////
//            if (Util.maybeRequestReadExternalStoragePermission(this, uris)) {
//                // The player will be reinitialized if the permission is granted.
//                return;
//            }
            ///////////////////////////////////////////////////////
           /* uris = new Uri[]{Uri.fromFile(new File("/storage/extSdCard/ColdplayParadise.mp4"))};
            extensions = new String[]{""};*/
            MediaSource[] mediaSources = new MediaSource[uris.length];
            for (int i = 0; i < uris.length; i++) {
                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
            }
            MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                    : new ConcatenatingMediaSource(mediaSources);

            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            } else {
//                resumeWindow = getIndex(video_id);
//                resumeWindow = video_num-1;
                resumeWindow = video_num-1;
                player.seekToDefaultPosition(resumeWindow);
            }
            //////////////
//            video_title.setText(Xc_liveStreams.get(resumeWindow).getName());
            ////////////////////////////
            player.prepare(mediaSource, !haveResumePosition, false);

            needRetrySource = false;
            updateButtonVisibilities();
        }
    }

    /**
     * Build media sourses and track renderer
     *
     * @param uri
     * @param overrideExtension
     * @return
     */
    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                return new SsMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
            case C.TYPE_DASH:
                return new DashMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), mainHandler, eventLogger);
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, mediaDataSourceFactory, mainHandler, eventLogger);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
                        mainHandler, eventLogger);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    /**
     * Manage DRM if available
     *
     * @param uuid
     * @param licenseUrl
     * @param keyRequestPropertiesArray
     * @return
     * @throws UnsupportedDrmException
     */
    private DrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManager(UUID uuid,
                                                                           String licenseUrl, String[] keyRequestPropertiesArray) throws UnsupportedDrmException {
        if (Util.SDK_INT < 18) {
            return null;
        }
        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(licenseUrl,
                buildHttpDataSourceFactory(false));
        if (keyRequestPropertiesArray != null) {
            for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                drmCallback.setKeyRequestProperty(keyRequestPropertiesArray[i],
                        keyRequestPropertiesArray[i + 1]);
            }
        }
        return new DefaultDrmSessionManager<>(uuid,
                FrameworkMediaDrm.newInstance(uuid), drmCallback, null, mainHandler, eventLogger);
    }

    /**
     * ReleasePlayer
     */
    private void releasePlayer() {
        if (player != null) {
            debugViewHelper.stop();
            debugViewHelper = null;
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
            trackSelectionHelper = null;
            eventLogger = null;
        }
    }

    /**
     * UpdateResumePosition
     */
    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition())
                : C.TIME_UNSET;
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        AppController abbc = AppController.getInstance();
         return  abbc.buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    // ExoPlayer.EventListener implementation

    /**
     * Returns a new HttpDataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new HttpDataSource factory.
     */
    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
        return AppController.getInstance()
                .buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing.
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            // ideal
            case ExoPlayer.STATE_IDLE:
                if (progress != null) progress.setVisibility(View.GONE);
                break;
            case ExoPlayer.STATE_BUFFERING:
                if (progress != null) progress.setVisibility(View.VISIBLE);
                break;
            case ExoPlayer.STATE_READY:
                if (progress != null) progress.setVisibility(View.GONE);
                break;
            case ExoPlayer.STATE_ENDED:
                if (progress != null) progress.setVisibility(View.GONE);
                initializePlayer();
                showControls();
                break;
            default:
                if (progress != null) progress.setVisibility(View.GONE);

                break;
        }
        updateButtonVisibilities();

    }

    @Override
    public void onPositionDiscontinuity() {
        if (needRetrySource) {
            // This will only occur if the user has performed a seek whilst in the error state. Update the
            // resume position so that if the user then retries, playback will resume from the position to
            // which they seeked.
            updateResumePosition();
        }
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        // Do nothing.
    }

    @Override
    public void onPlayerError(ExoPlaybackException e) {
        String errorString = null;
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = e.getRendererException();
            if (cause instanceof DecoderInitializationException) {
                // Special case for decoder initialization failures.
                DecoderInitializationException decoderInitializationException =
                        (DecoderInitializationException) cause;
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.getCause() instanceof DecoderQueryException) {
                        errorString = getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString = getString(R.string.error_no_secure_decoder,
                                decoderInitializationException.mimeType);
                    } else {
                        errorString = getString(R.string.error_no_decoder,
                                decoderInitializationException.mimeType);
                    }
                } else {
                    errorString = getString(R.string.error_instantiating_decoder,
                            decoderInitializationException.decoderName);
                }
            }
        }
        if (errorString != null) {
            showToast(errorString);
        }
        needRetrySource = true;
        if (isBehindLiveWindow(e)) {
            clearResumePosition();
            initializePlayer();
        } else {
            updateResumePosition();
            updateButtonVisibilities();
            showControls();
        }
    }

    // User controls

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        updateButtonVisibilities();
        if (trackGroups != lastSeenTrackGroupArray) {
            MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_VIDEO)
                        == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                    showToast(R.string.error_unsupported_video);
                }
                if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_AUDIO)
                        == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                    showToast(R.string.error_unsupported_audio);
                }
            }
            lastSeenTrackGroupArray = trackGroups;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    /**
     * Update visibility of controller view
     */
    private void updateButtonVisibilities() {
        debugRootView.removeAllViews();

        retryButton.setVisibility(needRetrySource ? View.VISIBLE : View.GONE);
        debugRootView.addView(retryButton);

        if (player == null) {
            return;
        }

        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            return;
        }

        for (int i = 0; i < mappedTrackInfo.length; i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                Button button = new Button(this);
                int label;
                switch (player.getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        label = R.string.audio;
                        btn_sub.setVisibility(View.VISIBLE);
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        label = R.string.video;
                        break;
                    case C.TRACK_TYPE_TEXT:
                        label = R.string.text;

                        break;
                    default:
                        continue;
                }
                button.setText(label);
                button.setTag(i);
                button.setOnClickListener(this);
                //debugRootView.addView(button, debugRootView.getChildCount() - 1);
            }
        }
    }

    private void showControls() {
        debugRootView.setVisibility(View.VISIBLE);
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                //resumePlayer(); // Resume your media player here
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                //pausePlayer();// Pause your media player here
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle intent) {
        super.onSaveInstanceState(intent);
    }
}