package com.gehostingv2.gesostingv2iptvbilling.view.nstplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.XMLTVProgrammePojo;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.SearchableAdapter;
import com.squareup.picasso.Picasso;

import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class NSTPlayerActivity extends Activity implements View.OnClickListener {

    public Context context;
    public String mFilePath;
    public Activity activity;
    public String scaleType;
    public View playButton;
    public View pauseButton;
    public View forwardButton;
    public View rewindButton;
    public View nextButton;
    public View prevButton;
    public View channelListButton;
    public EditText et_search;
    public ImageView channelLogo;
    public TextView currentProgram;
    public TextView currentProgramTime;
    public TextView nextProgram;
    public TextView nextProgramTime;
    public LinearLayout ll_seekbar_time;
    ProgressBar progressBar;


    //    public LinearLayout ll_root;
    public RelativeLayout rl_middle;
    public ListView listChannels;
    public ArrayList<LiveStreamsDBModel> allStreams;
    public boolean fullScreenOnly = true;
    public long defaultRetryTime = 5 * 1000;
    public String title;
    public String url;
    public boolean showNavIcon = true;
    NSTPlayer player;
    LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    SearchableAdapter adapter;
    private SharedPreferences loginPreferencesAfterLogin;
    private SimpleDateFormat programTimeFormat ;//
    public String allowedFormat;
    private static SharedPreferences loginPreferencesSharedPref_time_format;


//    public boolean clicked;
    /**
     * play video
     *
     * @param context
     * @param url     url,title
     */
    public static void play(Activity context, String... url) {
        Intent intent = new Intent(context, NSTPlayerActivity.class);
        intent.putExtra("url", url[0]);
        if (url.length > 1) {
            intent.putExtra("title", url[1]);
        }
        context.startActivity(intent);
    }

    public static Config configPlayer(Activity activity) {
        return new Config(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nst_player_activity);

//        Config config = getIntent().getParcelableExtra("config");
//        if (config == null || TextUtils.isEmpty(config.url)) {
//            Toast.makeText(this, R.string.giraffe_player_url_empty, Toast.LENGTH_SHORT).show();
//        } else {

        context = this;
        loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        loginPreferencesAfterLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);

        String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
        final String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
        allowedFormat = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        String serverUrl = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String serverPort = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        int opened_stream_id = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
        int currentWindowIndex = getIntent().getIntExtra("VIDEO_NUM", 0);
        String streamType = getIntent().getStringExtra("STREAM_TYPE");
        String videoTitle = getIntent().getStringExtra("VIDEO_TITLE");
        final String epgChannelID = getIntent().getStringExtra("EPG_CHANNEL_ID");
        String epgChannelLogo = getIntent().getStringExtra("EPG_CHANNEL_LOGO");

//        mFilePath = "http://iptv.dguk.co.uk/timeshift/test1234/4321test/2/2017-10-27:05-00/1313.ts";
        mFilePath = "http://" + serverUrl + ":" + serverPort + "/live/" + username + "/" + password + "/";
        liveStreamDBHandler = new LiveStreamDBHandler(this);
        allStreams =
                liveStreamDBHandler.getAllLiveStreasWithCategoryId("0", "live");
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        ll_seekbar_time = (LinearLayout)findViewById(R.id.ll_seekbar_time);
        channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
        currentProgram = (TextView) findViewById(R.id.tv_current_program);
        currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
        nextProgram = (TextView) findViewById(R.id.tv_next_program);
        nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);

        int positionToSelect = 0;
        positionToSelect = getIndexOfStreams(allStreams,currentWindowIndex);
        currentWindowIndex = positionToSelect;

        player = new NSTPlayer(this);
        player.setCurrentWindowIndex(currentWindowIndex);
//        player.setTitle(videoTitle + " -- " + Integer.toString(opened_stream_id) + " -- " + currentWindowIndex);
        player.setTitle(videoTitle);
        player.setDefaultRetryTime(defaultRetryTime);
        player.setFullScreenOnly(fullScreenOnly);
        player.showAll();
        player.setScaleType(TextUtils.isEmpty(scaleType) ? NSTPlayer.SCALETYPE_FITPARENT : scaleType);
//            player.setTitle(TextUtils.isEmpty(Integer.toString(opened_stream_id)) ? "" : Integer.toString(opened_stream_id));
//        player.setShowNavIcon(showNavIcon);
        player.play(mFilePath, opened_stream_id,allowedFormat);
        updateEPGData(epgChannelID,epgChannelLogo);

        findViewById(R.id.exo_next).setOnClickListener(this);
        findViewById(R.id.exo_prev).setOnClickListener(this);


        listChannels = (ListView) findViewById(R.id.lv_ch);
        et_search = (EditText) findViewById(R.id.et_search);

        playButton = findViewById(R.id.exo_play);
        if (playButton != null) {
            playButton.setOnClickListener(this);
        }
        pauseButton = findViewById(R.id.exo_pause);
        if (pauseButton != null) {
            pauseButton.setOnClickListener(this);
        }
//        playerStartIconsUpdate();

//        forwardButton = findViewById(R.id.exo_ffwd);
//        if (forwardButton != null) {
//            forwardButton.setOnClickListener(this);
//        }
//        rewindButton = findViewById(R.id.exo_rew);
//        if (rewindButton != null) {
//            rewindButton.setOnClickListener(this);
//        }
        prevButton = findViewById(R.id.exo_prev);
        if (prevButton != null) {
            prevButton.setOnClickListener(this);
        }
        nextButton = findViewById(R.id.exo_next);
        if (nextButton != null) {
            nextButton.setOnClickListener(this);
        }
        channelListButton = findViewById(R.id.btn_list);
        if (channelListButton != null) {
            channelListButton.setOnClickListener(this);
        }

        rl_middle = (RelativeLayout) findViewById(R.id.middle);
//        if (rl_middle != null && listChannels.getVisibility()==View.VISIBLE) {
//                rl_middle.setOnClickListener(this);
//        }

//        if(rl_middle!=null && listChannels!=null && listChannels.getVisibility()==View.VISIBLE) {
//            rl_middle.setOnClickListener(new View.OnClickListener(){
//                                           public void onClick(View v){
//                                                 et_search.setVisibility(View.GONE);
//                                                 listChannels.setVisibility(View.GONE);
//                                                rl_middle.setClickable(false);
//                                               rl_middle.setOnClickListener(null);
//                                           }
//
//                }
//            );
//            rl_middle.setOnClickListener(this);
//        }else if(rl_middle!=null){
//            rl_middle.setClickable(false);
//            rl_middle.setOnClickListener(null);
//        }

//        case R.id.middle:
//        if(rl_middle!=null && listChannels!=null && listChannels.getVisibility()==View.VISIBLE) {
////                    player.setShowNavIcon(true);
//            et_search.setVisibility(View.GONE);
//            listChannels.setVisibility(View.GONE);
////                    toggleView(listChannels);
//        }
//        break;
//        ll_root  = (LinearLayout) findViewById(R.id.root);
//        if (ll_root != null) {
//            ll_root.setOnClickListener(this);
//        }



//        List<String> channelList = new ArrayList<String>();
//        int positionToSelect = 0;
//        for (int i = 0; i < allStreams.size(); i++) {
//            if(Integer.parseInt(allStreams.get(i).getNum()) == currentWindowIndex){
//                positionToSelect = i;
//                break;
//            }
////            channelList.add(allStreams.get(i).getName());
//        }
//        adapter = new ArrayAdapter<String>(this,
//                R.layout.channel_list, channelList);
        adapter = new SearchableAdapter(this,allStreams);
//            ListView listView = (ListView)
//                    findViewById(R.id.lv_ch);
        // Assign adapter to ListView
        if (listChannels != null){
            listChannels.setAdapter(adapter);
            listChannels.setSelection(positionToSelect);
//            listChannels.requestFocus();
//            listChannels.setFocusable(true);
//            listChannels.getChildAt(0).setFocusable(true);


//            int count = listChannels.getChildCount();
//            for (int i = 0; i <= listChannels.getLastVisiblePosition() - listChannels.getFirstVisiblePosition(); i++){
//
//            }

//            for(int j=0; j<count; j++){
//
//            }
//            View abc = listChannels.getFocusedChild();
//            abc.setBackgroundResource(R.drawable.bg_key);            //listChannels.setSelector(R.drawable.selector_search_fields);
//            listChannels.setAdapter(adapter);
//            listChannels.setTextFilterEnabled(true);
            listChannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Utils.showToast(context, "Testing: "+position);
//                String a = (String) parent.getAdapter().getItem(position);
//                view.setBackgroundColor(Color.BLUE);
                view.setSelected(true);
                    ArrayList<LiveStreamsDBModel> filteredData = adapter.getFilteredData();
                    if(filteredData!=null){
                        int num = Integer.parseInt(filteredData.get(position).getNum());
                        String epgChannelID = filteredData.get(position).getEpgChannelId();
                        String channelLogo = filteredData.get(position).getStreamIcon();
                        num = getIndexOfStreams(allStreams,num);
                        player.setCurrentWindowIndex(num);
//                        player.setTitle(filteredData.get(position).getName() + " -- " + filteredData.get(position).getStreamId() + " -- " + num);
                        player.setTitle(filteredData.get(position).getName());
                        player.play(mFilePath, Integer.parseInt(filteredData.get(position).getStreamId()),allowedFormat);
                        updateEPGData(epgChannelID,channelLogo);
                    }else{
    //                    player.setCurrentWindowIndex(position + 1);
                        int num = Integer.parseInt(allStreams.get(position).getNum());
                        String epgChannelID = allStreams.get(position).getEpgChannelId();
                        String channelLogo = allStreams.get(position).getStreamIcon();
                        num = getIndexOfStreams(allStreams,num);
                        player.setCurrentWindowIndex(num);
//                        player.setTitle(allStreams.get(position).getName() + " -- " + allStreams.get(position).getStreamId() + " -- " + num);
                        player.setTitle(allStreams.get(position).getName());
                        player.play(mFilePath, Integer.parseInt(allStreams.get(position).getStreamId()),allowedFormat);
                        updateEPGData(epgChannelID,channelLogo);

                    }
                    listChannels.setVisibility(View.GONE);
                    et_search.setVisibility(View.GONE);


                }
            });

            et_search.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    if(adapter!=null) {
                        adapter.getFilter().filter(cs.toString());
                    }

                    // When user changed the Text
//                    Filter Abc = adapter.getFilter();
//                    Abc.filter(cs);
//                    Utils.showToast(context, "Testing: "+ Abc);

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });


        }






//        }
    }


    public void updateEPGData(String epgChannelID, String channel_logo){
        boolean hideEPGData = true;
        if(liveStreamDBHandler!=null && loginPreferencesAfterLogin!=null) {
            String savedEPGShift = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_SELECTED_EPG_SHIFT, "");
            if (epgChannelID != null && !epgChannelID.equals("")) {
                ArrayList<XMLTVProgrammePojo> xmltvProgrammePojos = liveStreamDBHandler.getEPG(epgChannelID);
                String startDateTime;
                String stopDateTime;
                String Title = "";
                int epgPercentage = 0;
                if (xmltvProgrammePojos != null) {
                    for (int j = 0; j < xmltvProgrammePojos.size(); j++) {
                        startDateTime = xmltvProgrammePojos.get(j).getStart();
                        stopDateTime = xmltvProgrammePojos.get(j).getStop();
                        Title = xmltvProgrammePojos.get(j).getTitle();
                        Long epgStartDateToTimestamp = Utils.epgTimeConverter(startDateTime);
                        Long epgStopDateToTimestamp = Utils.epgTimeConverter(stopDateTime);
                        if (isEventVisible(epgStartDateToTimestamp, epgStopDateToTimestamp, savedEPGShift)) {
                            epgPercentage = getPercentageLeft(epgStartDateToTimestamp, epgStopDateToTimestamp);
                            if (epgPercentage != 0) {
                                epgPercentage = 100 - epgPercentage;
                                if (epgPercentage!=0 && Title!=null && !Title.equals("")) {
                                    hideEPGData = false;
                                    //ll_seekbar_time.setVisibility(View.VISIBLE);

                                    progressBar.setVisibility(View.VISIBLE);
                                    progressBar.setProgress(epgPercentage);

                                    currentProgram.setVisibility(View.VISIBLE);
                                    currentProgram.setText(Title);

                                    currentProgramTime.setVisibility(View.VISIBLE);

                                    loginPreferencesSharedPref_time_format = context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, MODE_PRIVATE);
                                    String timeFormat = loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
                                    programTimeFormat = new SimpleDateFormat(timeFormat);

                                    currentProgramTime.setText(programTimeFormat.format(epgStartDateToTimestamp) + " - " + programTimeFormat.format(epgStopDateToTimestamp));

                                    channelLogo.setVisibility(View.VISIBLE);
                                    if (channel_logo != null && !channel_logo.equals("")) {
                                        Picasso.with(context).load(channel_logo).placeholder(R.drawable.iptv_placeholder).into(channelLogo);
                                    } else {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.iptv_placeholder, null));
                                        }
                                    }
                                } else {
                                    hideEPGData = true;

                                }
                                break;
                            }

                        }
                    }
                }else{
                    hideEPGData = true;
                }
            }else{
                hideEPGData = true;

            }
        }
        if(hideEPGData){
            //ll_seekbar_time.setVisibility(View.GONE);

            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(0);

            currentProgram.setVisibility(View.GONE);
            currentProgram.setText("");

            currentProgramTime.setVisibility(View.GONE);
            currentProgramTime.setText("");

            channelLogo.setVisibility(View.GONE);

        }
    }


    private boolean isEventVisible(final long start, final long end , final String savedEPGShift) {
        long currentTime = LocalDateTime.now().toDateTime().getMillis();
        int TimeShiftMilliSeconds = Utils.getMilliSeconds(savedEPGShift);
        currentTime = currentTime + TimeShiftMilliSeconds;

        return start <= currentTime && end >= currentTime;
        //        return (start >= mTimeLowerBoundary && start <= mTimeUpperBoundary)
//                || (end >= mTimeLowerBoundary && end <= mTimeUpperBoundary)
//                || (start <= mTimeLowerBoundary && end >= mTimeUpperBoundary);
    }
    private static int getPercentageLeft(final long start, final long end) {
        long now = LocalDateTime.now().toDateTime().getMillis();
//        int TimeShiftMilliSeconds = Utils.getMilliSeconds(savedEPGShift);
//        now = now + TimeShiftMilliSeconds;

        if (start >= end || now >= end) {
            return 0;
        }
        if (now <= start) {
            return 100;
        }
        return (int) ((end - now) * 100 / (end - start));
    }

    public int getIndexOfStreams(ArrayList<LiveStreamsDBModel> allStreams, int num) {
        int positionToSelect = 0;
        for (int i = 0; i < allStreams.size(); i++) {
            if (Integer.parseInt(allStreams.get(i).getNum()) == num) {
                positionToSelect = i;
                break;
            }
//            channelList.add(allStreams.get(i).getName());
        }
        return positionToSelect;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (listChannels != null && listChannels.getVisibility() == View.VISIBLE) {
//                    listChannels.setFocusable(true);
//                    listChannels.requestFocus();
                } else {
                    showTitleBarAndFooter();
                    findViewById(R.id.exo_prev).performClick();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                if(listChannels!=null && listChannels.getVisibility() == View.VISIBLE){
//                    listChannels.setFocusable(true);
//                    listChannels.requestFocus();
                }else{
                    showTitleBarAndFooter();
                    findViewById(R.id.exo_next).performClick();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }

    }

        @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final boolean uniqueDown = event.getRepeatCount() == 0;

        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                if(listChannels!=null && listChannels.getVisibility() == View.VISIBLE){
//                    listChannels.setFocusable(true);
//                    listChannels.requestFocus();
//                }else{
//                    showTitleBarAndFooter();
//                    findViewById(R.id.exo_prev).performClick();
//                }
//                return true;
            case KeyEvent.KEYCODE_CHANNEL_UP:
                showTitleBarAndFooter();
                findViewById(R.id.exo_next).performClick();
                return true;

            case KeyEvent.KEYCODE_CHANNEL_DOWN:
                showTitleBarAndFooter();
                findViewById(R.id.exo_prev).performClick();
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                showTitleBarAndFooter();
                listChannels.setVisibility(View.VISIBLE);
                et_search.setVisibility(View.VISIBLE);
                listChannels.setFocusable(true);
                listChannels.requestFocus();
                return true;
            case KeyEvent.KEYCODE_HEADSETHOOK:
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_SPACE:
                if (uniqueDown) {
                    showTitleBarAndFooter();
                    player.doPauseResume();
                }
                return true;
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                if (uniqueDown && !player.isPlaying()) {
                    showTitleBarAndFooter();
                    player.start();
                    playerStartIconsUpdate();
                }
                return true;
            case KeyEvent.KEYCODE_MEDIA_STOP:
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                if (uniqueDown && player.isPlaying()) {
                    showTitleBarAndFooter();
                    player.pause();
                    playerPauseIconsUpdate();
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
//    private void doPauseResume() {
//        if (player == null) {
//            return;
//        }
//
//        if (player.isPlaying()) {
//            player.pause();
//            playerPauseIconsUpdate();
//        } else {
//            player.start();
//            playerStartIconsUpdate();
//        }
////        updatePausePlay();
//    }
    private void playerStartIconsUpdate(){
        playButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
    }
    private void playerPauseIconsUpdate(){
        pauseButton.setVisibility(View.GONE);
        playButton.setVisibility(View.VISIBLE);
    }


//    public class Reminder {
//
//        Timer timer;
//
//        public Reminder(int seconds) {
//            timer = new Timer();
//            timer.schedule(new RemindTask(), seconds*1000);
//        }
//
//        class RemindTask extends TimerTask {
//
//            public void run() {
//                if(!clicked){
//                    runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            findViewById(R.id.app_video_top_box).setVisibility(View.GONE);
//                            findViewById(R.id.controls).setVisibility(View.GONE);
//                            System.out.format("Time's up!%n");
//                            timer.cancel(); //Terminate the timer thread
//
//                        }
//
//                    });
//                }
//
//            }
//        }
//    }

    public void showTitleBarAndFooter(){
        findViewById(R.id.app_video_top_box).setVisibility(View.VISIBLE);
        findViewById(R.id.controls).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_seekbar_time).setVisibility(View.VISIBLE);
    }

    public void hideTitleBarAndFooter() {
        findViewById(R.id.app_video_top_box).setVisibility(View.GONE);
        findViewById(R.id.controls).setVisibility(View.GONE);
        findViewById(R.id.ll_seekbar_time).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exo_play:
                if (player!=null && playButton != null) {
                    player.start();
                    playerStartIconsUpdate();

                }
                break;
            case R.id.exo_pause:
                if (player!=null && pauseButton != null) {
                    player.pause();
                    playerPauseIconsUpdate();
                }
                break;
//            case R.id.exo_ffwd:
//                if(player!=null){
//                    player.forward(0.2f);
//                }
//                break;
//            case R.id.exo_rew:
//                if(player!=null){
//                    player.forward(-0.2f);
//                }
//                break;
            case R.id.exo_prev:
                if(player!=null){
                    previous();
                    int indexPrev = player.getCurrentWindowIndex();
                    if (indexPrev <= allStreams.size() - 1) {
//                        player.setTitle(allStreams.get(indexPrev).getName() + " -- " +  allStreams.get(indexPrev).getStreamId() +" -- "+indexPrev);
                        player.setTitle(allStreams.get(indexPrev).getName());
                        player.play(mFilePath, Integer.parseInt(allStreams.get(indexPrev).getStreamId()),allowedFormat);
                        updateEPGData(allStreams.get(indexPrev).getEpgChannelId(),allStreams.get(indexPrev).getStreamIcon());
                    }
                }
                break;
            case R.id.exo_next:
                if(player!=null){
                    next();
                    int indexNext = player.getCurrentWindowIndex();
                    if (indexNext <= allStreams.size() - 1) {
                        player.setTitle(allStreams.get(indexNext).getName());
//                        player.setTitle(allStreams.get(indexNext).getName() + " -- " +  allStreams.get(indexNext).getStreamId() +" -- "+indexNext);
                        player.play(mFilePath, Integer.parseInt(allStreams.get(indexNext).getStreamId()),allowedFormat);
                        updateEPGData(allStreams.get(indexNext).getEpgChannelId(),allStreams.get(indexNext).getStreamIcon());
                    }
                }
                break;
            case R.id.btn_list:
                if(listChannels!=null && et_search!=null) {
                    toggleView(listChannels);
                    toggleView(et_search);
                    listChannels.setFocusable(true);
                    listChannels.requestFocus();
                }
                break;

//            case R.id.middle:
//                if(listChannels!=null && et_search!=null) {
//                    et_search.setVisibility(View.GONE);
//                    listChannels.setVisibility(View.GONE);
//                }
//                break;

//            case R.id.root:
//                if(ll_root!=null && listChannels!=null) {
//                    ll_root.setVisibility(View.GONE);
//                    toggleView(listChannels);
//                }
//                break;

        }
    }

    public void toggleView(View view){
        if(view.getVisibility()==View.GONE) {
            view.setVisibility(View.VISIBLE);

        }
        else if(view.getVisibility()==View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }

    private void previous(){
        int currentWindowIndex = player.getCurrentWindowIndex();
        if(currentWindowIndex == 0){
            player.setCurrentWindowIndex(allStreams.size() - 1);
            return;
        }
        player.setCurrentWindowIndex(currentWindowIndex-1);
    }
    private void next() {
        int currentWindowIndex = player.getCurrentWindowIndex();
        if(currentWindowIndex == allStreams.size()-1){
            player.setCurrentWindowIndex(0);
            return;
        }
        player.setCurrentWindowIndex(currentWindowIndex+1);
    }

    @Override
    public void onBackPressed() {
        if(listChannels!=null) {
            listChannels.setFocusable(true);
            listChannels.requestFocus();
        }
        if(listChannels!=null && listChannels.getVisibility() == View.VISIBLE){
            listChannels.setVisibility(View.GONE);
            if(et_search!=null && et_search.getVisibility() == View.VISIBLE) {
                et_search.setVisibility(View.GONE);
            }
            if(findViewById(R.id.app_video_top_box).getVisibility() == View.VISIBLE) {
                hideTitleBarAndFooter();
            }
            return;
        }
        if(findViewById(R.id.app_video_top_box).getVisibility() == View.VISIBLE) {
            hideTitleBarAndFooter();
            if(listChannels!=null && listChannels.getVisibility() == View.VISIBLE) {
                listChannels.setVisibility(View.GONE);
                if (et_search != null && et_search.getVisibility() == View.VISIBLE) {
                    et_search.setVisibility(View.GONE);
                }
            }
            return;
        }


        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    public static class Config implements Parcelable {

        public static final Creator<Config> CREATOR = new Creator<Config>() {
            public Config createFromParcel(Parcel in) {
                return new Config(in);
            }

            public Config[] newArray(int size) {
                return new Config[size];
            }
        };
        private static boolean debug=true;
        private Activity activity;
        private String scaleType;
        private boolean fullScreenOnly = true;
        private long defaultRetryTime = 5 * 1000;
        private String title;
        private String url;
        private boolean showNavIcon = true;

        public Config(Activity activity) {
            this.activity = activity;
        }

        private Config(Parcel in) {
            scaleType = in.readString();
            fullScreenOnly = in.readByte() != 0;
            defaultRetryTime = in.readLong();
            title = in.readString();
            url = in.readString();
            showNavIcon = in.readByte() != 0;
        }

        public static boolean isDebug() {
            return debug;
        }

        public  Config debug(boolean debug) {
            Config.debug = debug;
            return this;
        }

        public Config setTitle(String title) {
            this.title = title;
            return this;
        }

        public Config setDefaultRetryTime(long defaultRetryTime) {
            this.defaultRetryTime = defaultRetryTime;
            return this;
        }

        public void play(String url) {
            this.url = url;
            Intent intent = new Intent(activity, NSTPlayerActivity.class);
            intent.putExtra("config", this);
            activity.startActivity(intent);
        }

        public Config setScaleType(String scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Config setFullScreenOnly(boolean fullScreenOnly) {
            this.fullScreenOnly = fullScreenOnly;
            return this;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(scaleType);
            dest.writeByte((byte) (fullScreenOnly ? 1 : 0));
            dest.writeLong(defaultRetryTime);
            dest.writeString(title);
            dest.writeString(url);
            dest.writeByte((byte) (showNavIcon ? 1 : 0));
        }
    }
}
