package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseUpdatedStatusDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.EPG;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.EPGClickListener;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.EPGData;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain.EPGChannel;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.domain.EPGEvent;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.misc.EPGDataListener;
import com.gehostingv2.gesostingv2iptvbilling.view.utility.epg.service.EPGService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class NewEPGFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ACTIVE_LIVE_STREAM_CATEGORY_ID = "";
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_view_provider)
    TextView tvViewProvider;
    @BindView(R.id.rl_newepg_fragment)
    RelativeLayout epgFragment;
    @BindView(R.id.ll_epg_view)
    RelativeLayout epgView;
    @BindView(R.id.current_event_time)
    TextView currentEventTime;
    @BindView(R.id.epg)
    EPG epg;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;



    @BindView(R.id.current_event)
    TextView currentEvent;
    @BindView(R.id.current_time)
    TextView currentTime;
    @BindView(R.id.current_event_description)
    TextView currentEventDescription;


    private Toolbar toolbar;
    SearchView searchView;
    public Context context;
    private TextView currentEventTimeTextView;
    private TextView currentEventTextView;
    private TextView currentEventDescriptionTextView;
    private SimpleDateFormat currentTimeFormat;//
    private Handler periodicTaskHandler;
    private TypedValue tv;
    int actionBarHeight;
    Unbinder unbinder;
    private String getActiveLiveStreamCategoryId;
    private static SharedPreferences loginPreferencesSharedPref_time_format;


    private TextView currentTimeTextView;
    private LiveStreamDBHandler liveStreamDBHandler;

    private SharedPreferences loginPreferencesSharedPref;

    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive =
            new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG =
            new DatabaseUpdatedStatusDBModel();

    public static NewEPGFragment newInstance(String category_id) {
        Bundle args = new Bundle();
        args.putString(ACTIVE_LIVE_STREAM_CATEGORY_ID, category_id);
        NewEPGFragment fragment = new NewEPGFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActiveLiveStreamCategoryId = getArguments().getString(ACTIVE_LIVE_STREAM_CATEGORY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_epg_streams, container, false);
        unbinder = ButterKnife.bind(this, view);
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        initialize();
        return view;
    }


    //    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //        epg.requestFocus();

        super.onViewCreated(view, savedInstanceState);
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();

        if(getView()!=null)
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if(event.getAction()!=KeyEvent.ACTION_UP) {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                            if (epg != null) {
                                return epg.onKeyDown(keyCode, event);
                            }
                        }

                        if(keyCode == KeyEvent.KEYCODE_BACK){
                            Intent dashboardIntent = new Intent(context, DashboardActivity.class);
                            startActivity(dashboardIntent);
                        }
                    }

                    return false;
                }
            });


//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                //  This is the filter
//                if (event.getAction()!=KeyEvent.ACTION_UP)
//                    return true;
//
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_DPAD_DOWN :
//                        if (epg != null) {
//                            return epg.onKeyUp(keyCode, event);
//                        }
//                        break;
//                    case KeyEvent.KEYCODE_DPAD_UP :
//                        if (epg != null) {
//                            return epg.onKeyUp(keyCode, event);
//                        }
//                        break;
//                    case KeyEvent.KEYCODE_DPAD_RIGHT :
//                        if (epg != null) {
//                            return epg.onKeyUp(keyCode, event);
//                        }
//                        break;
//                    case KeyEvent.KEYCODE_DPAD_LEFT :
//                        if (epg != null) {
//                            return epg.onKeyUp(keyCode, event);
//                        }
//                    case KeyEvent.KEYCODE_DPAD_CENTER :
//                        if (epg != null) {
//                            return epg.onKeyUp(keyCode, event);
//                        }
//                    case KeyEvent.KEYCODE_ENTER :
//                        if (epg != null) {
//                            return epg.onKeyUp(keyCode, event);
//                        }
//                        break;
//
//                }
//                return true;
//            }
//        });
    }

    private void updateTime() {
        Date now = new Date();
        loginPreferencesSharedPref_time_format = context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, MODE_PRIVATE);
        String timeFormat = loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");


        currentTimeFormat = new SimpleDateFormat(timeFormat);

        currentTimeTextView.setText(currentTimeFormat.format(now));
    }



    void onCreateEPG() {
        loginPreferencesSharedPref = context.getSharedPreferences(AppConst.SETTINGS_PREFERENCE, context.MODE_PRIVATE);
        final String selectedPlayer = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER_IPTV, "");


        currentTimeTextView = currentTime;
        currentEventTextView = currentEvent;
        currentEventTimeTextView = currentEventTime;
        currentEventDescriptionTextView = currentEventDescription;


        epg.setCurrentEventTextView(currentEventTextView);
        epg.setCurrentEventTimeTextView(currentEventTimeTextView);
        epg.setCurrentEventDescriptionTextView(currentEventDescriptionTextView);



//        currentEventTimeTextView = currentEventTime;
//        epg.setCurrentEventTimeTextView(currentEventTimeTextView);

        periodicTaskHandler = new Handler();
        epg.setEPGClickListener(new EPGClickListener() {
            @Override
            public void onChannelClicked(int channelPosition, EPGChannel epgChannel) {
                int streamID = Integer.parseInt(epgChannel.getStreamID());
                String name = epgChannel.getName();
                String num = epgChannel.getNum();
                String epgChannelId = epgChannel.getEpgChannelID();
                String channelLogo = epgChannel.getImageURL();
                Utils.playWithPlayer(context,selectedPlayer,streamID,"live",num,name,epgChannelId,channelLogo);
            }

            @Override
            public void onEventClicked(int channelPosition, int programPosition, EPGEvent epgEvent) {
                int streamID = Integer.parseInt(epgEvent.getChannel().getStreamID());
                String name = epgEvent.getChannel().getName();
                String num = epgEvent.getChannel().getNum();
                String epgChannelId = epgEvent.getChannel().getEpgChannelID();
                String channelLogo = epgEvent.getChannel().getImageURL();
                epg.selectEvent(epgEvent, true);
                Utils.playWithPlayer(context,selectedPlayer,streamID,"live",num,name,epgChannelId,channelLogo);
            }

            @Override
            public void onResetButtonClicked() {
                epg.recalculateAndRedraw(null, true, epgFragment, epg);
            }
        });
    }

    private void setToolbarLogoImagewithSearchView() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (context != null && toolbar != null) {

/**
 * This code used here to put menu item in center vertical using instanceof Action menu
 * view inflating menu view using toolbar
 */
//        toolbar.inflateMenu(R.menu.menu_search);
//        toolbar.inflateMenu(R.menu.menu_dashboard_logout);
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            }

            for (int i = 0; i < toolbar.getChildCount(); i++) {
                // make toggle drawable center-vertical, you can make each view alignment whatever you want
                if (toolbar.getChildAt(i) instanceof ActionMenuView) {
                    Toolbar.LayoutParams lp = (Toolbar.LayoutParams) toolbar.getChildAt(i).getLayoutParams();
                    lp.gravity = Gravity.CENTER_VERTICAL;
                }
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
//        toolbar.inflateMenu(R.menu.menu_search);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return false;
    }

    private void initialize() {
        context = getContext();
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        if (context != null) {
            onCreateEPG();

            int totalStreams =  liveStreamDBHandler.getLiveStreamsCount(getActiveLiveStreamCategoryId);
            if(totalStreams!=0 || getActiveLiveStreamCategoryId.equals("0")) {
                onWindowFocusChanged(getActiveLiveStreamCategoryId,epgFragment,R.id.epg);
            }else{
                if(pbLoader!=null){
                    pbLoader.setVisibility(View.INVISIBLE);
                }
                if(tvNoStream!=null) {
                    tvNoStream.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    @Override
    public void onDestroyView() {
        if (epg != null) {
            epg.clearEPGImageCache();
        }
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        periodicTaskHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void onWindowFocusChanged(final String categoryID, final RelativeLayout epgFragment, int epg) {
        int hrs = (TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings()) / 1000 / 60 / 60;
        class AsyncLoadEPGData extends AsyncTask<Void, Void, EPGData> {

            EPG epg;

            public AsyncLoadEPGData(EPG epg) {
                this.epg = epg;
            }

            @Override
            protected EPGData doInBackground(Void... voids) {
                EPGDataListener epgDataListener = new EPGDataListener(epg);
                return new EPGService(context).getData(epgDataListener, 0 , categoryID);
            }

            @Override
            protected void onPostExecute(EPGData epgData) {
//                epg.setEPGData(epgData);
//                epg.recalculateAndRedraw(null, false,epgFragment,epg);
//                if(epgView!=null) {
//                    epgView.setVisibility(View.VISIBLE);
//                }
//                if(pbLoader!=null){
//                    pbLoader.setVisibility(View.INVISIBLE);
//                }

                int totalChannels = 0;
                if(epgData!=null) {
                    totalChannels = epgData.getChannelCount();
                }
                epg.setEPGData(epgData);
                epg.recalculateAndRedraw(null, false,epgFragment,epg);
                if(epgView!=null && totalChannels>0) {
                    epgView.setVisibility(View.VISIBLE);
                }else if(epgView!=null){
                    epgView.setVisibility(View.GONE);
                    tvNoRecordFound.setVisibility(View.VISIBLE);
                    tvNoRecordFound.setText(getResources().getString(R.string.no_epg_guide_found));
                }
                if(pbLoader!=null){
                    pbLoader.setVisibility(View.INVISIBLE);
                }
            }
        }

        new AsyncLoadEPGData(this.epg).execute();
        updateTime();
    }
}
