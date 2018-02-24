package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamCategoriesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsEpgCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.EpgListingPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.XMLTVProgrammePojo;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.SubTVArchiveAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LiveStreamsInterface;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

//import com.expresstvbox.expresstvboxiptv.presenter.LiveStreamsPresenter;


public class SubTVArchiveFragment extends Fragment implements LiveStreamsInterface, NavigationView.OnNavigationItemSelectedListener {
    public static final String ACTIVE_LIVE_STREAM_CATEGORY_ID = "";
    //    public static final String ACTIVE_LIVE_STREAM_ID = "";
    public static final String ACTIVE_LIVE_STREAM_NUM = "";
    public static final String ACTIVE_LIVE_STREAM_NAME = "";
    public static final String ACTIVE_LIVE_STREAM_ICON = "";
    public static final String ACTIVE_LIVE_STREAM_CHANNEL_ID = "";

    public static final String LIVE_STREAMS_EPG = "";
    //    @BindView(R.id.pb_loader)
//    ProgressBar pbLoader;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences loginPreferencesSharedPref;
    //    public LiveStreamsPresenter liveStreamsPresenter;
    private SubTVArchiveAdapter LiveStreamsEpgAdapter;
    private ArrayList<LiveStreamsCallback> favouriteStreams = new ArrayList<>();
    private List<EpgListingPojo> dataSet;

    private Toolbar toolbar;
    SearchView searchView;
    public Context context;
    DatabaseHandler database;

    TypedValue tv;
    int actionBarHeight;
    Unbinder unbinder;

    private String getActiveLiveStreamCategoryId;
    private int getActiveLiveStreamId;
    private String getActiveLiveStreamNum;
    private String getActiveLiveStreamName;
    private String getActiveLiveStreamIcon;
    private String getActiveLiveStreamChannelId;
    private String getActiveLiveStreamChannelDuration;

    public static SubTVArchiveFragment newInstance(String category_id, ArrayList<XMLTVProgrammePojo> liveStreamEpgCallback, int opened_stream_id, String opened_num, String opened_channel_name, String opened_channel_icon, String opened_channel_id, String opened_channel_duration) {
        Bundle args = new Bundle();
        args.putString("ACTIVE_LIVE_STREAM_CATEGORY_ID", category_id);
        args.putInt("ACTIVE_LIVE_STREAM_ID", opened_stream_id);
        args.putString("ACTIVE_LIVE_STREAM_NUM", opened_num);
        args.putString("ACTIVE_LIVE_STREAM_NAME", opened_channel_name);
        args.putString("ACTIVE_LIVE_STREAM_ICON", opened_channel_icon);
        args.putString("ACTIVE_LIVE_STREAM_CHANNEL_ID", opened_channel_id);
        args.putString("ACTIVE_LIVE_STREAM_CHANNEL_DURATION", opened_channel_duration);


        args.putSerializable("LIVE_STREAMS_EPG", liveStreamEpgCallback);
        SubTVArchiveFragment fragment = new SubTVArchiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setEpgtoAdapter() {
        getActiveLiveStreamCategoryId = getArguments().getString("ACTIVE_LIVE_STREAM_CATEGORY_ID");
        getActiveLiveStreamId = getArguments().getInt("ACTIVE_LIVE_STREAM_ID");
        getActiveLiveStreamNum = getArguments().getString("ACTIVE_LIVE_STREAM_NUM");
        getActiveLiveStreamName = getArguments().getString("ACTIVE_LIVE_STREAM_NAME");
        getActiveLiveStreamIcon = getArguments().getString("ACTIVE_LIVE_STREAM_ICON");
        getActiveLiveStreamChannelId = getArguments().getString("ACTIVE_LIVE_STREAM_CHANNEL_ID");
        getActiveLiveStreamChannelDuration = getArguments().getString("ACTIVE_LIVE_STREAM_CHANNEL_DURATION");

//        int newStreamID = Integer.parseInt(getActiveLiveStreamId);

        Serializable getAllLiveStreamsEpg = getArguments().getSerializable("LIVE_STREAMS_EPG");
        if (getActiveLiveStreamCategoryId != null && getAllLiveStreamsEpg != null) {
            ArrayList<XMLTVProgrammePojo> liveStreamEpgCallback = (ArrayList<XMLTVProgrammePojo>) getAllLiveStreamsEpg;
//            String singleDateTime;
//            String singleDate;
//            String newDate = null;

            String startDateTime;
            String stopDateTime;
            String Title = "";
            String Desc;
            int epgPercentage = 0;
            int nowPlayingPosition = 0;
            boolean nowPlayingFlag = false;
            int j = 0;

            ArrayList sortedArray = new ArrayList();

//            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

            for (int i = 0; i < liveStreamEpgCallback.size(); i++) {


                startDateTime = liveStreamEpgCallback.get(i).getStart();
                stopDateTime = liveStreamEpgCallback.get(i).getStop();
                Title = liveStreamEpgCallback.get(i).getTitle();
                Desc = liveStreamEpgCallback.get(i).getDesc();
                Long epgStartDateToTimestamp = Utils.epgTimeConverter(startDateTime);
                Long epgStopDateToTimestamp = Utils.epgTimeConverter(stopDateTime);
                String currentFormatDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(epgStartDateToTimestamp);


                if (currentFormatDate != null && currentFormatDate.equals(getActiveLiveStreamCategoryId)) {
                    sortedArray.add(liveStreamEpgCallback.get(i));
                    j++;
                }

                if (Utils.isEventVisible(epgStartDateToTimestamp, epgStopDateToTimestamp)) {
                    nowPlayingFlag = true;
                    nowPlayingPosition = j - 1;
                    break;
                }
            }
//
//            if (sortedArray != null && myRecyclerView != null) {
//                int nowPlaying;
//                int nowPlayingPosition=0;
//                dataSet = sortedArray;
//                for(int j=0;j<sortedArray.size();j++) {
//                    nowPlaying = dataSet.get(j).getNowPlaying();
//                    if(nowPlaying ==1){
//                        nowPlayingPosition = j;
//                        break;
//                    }
//                }
//                nowPlayingPosition = sortedArray.size()-1;
            LiveStreamsEpgAdapter = new SubTVArchiveAdapter(sortedArray, nowPlayingPosition, nowPlayingFlag, getActiveLiveStreamCategoryId, getActiveLiveStreamId, getActiveLiveStreamNum, getActiveLiveStreamName, getActiveLiveStreamIcon, getActiveLiveStreamChannelId, getActiveLiveStreamChannelDuration, getContext());
//            LiveStreamsEpgAdapter = new SubTVArchiveAdapter(sortedArray,nowPlayingPosition,nowPlayingFlag,getActiveLiveStreamCategoryId,newStreamID,getActiveLiveStreamNum,getActiveLiveStreamName,getActiveLiveStreamIcon,getActiveLiveStreamChannelId, getContext());
            myRecyclerView.setAdapter(LiveStreamsEpgAdapter);

            initialize(nowPlayingPosition + 1);

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_epg, container, false);
        unbinder = ButterKnife.bind(this, view);
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        setEpgtoAdapter();
        return view;
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

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//        toolbar.inflateMenu(R.menu.menu_search);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout1) {
            logoutUser();
        }
//        if (id == R.id.action_search) {
//            searchView = (SearchView) MenuItemCompat.getActionView(item);
//            searchView.setQueryHint(AppConst.SEARCH_CHANNEL);
//            searchView.setIconifiedByDefault(false);
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    //filters list items from adapter
//                    tvNoRecordFound.setVisibility(View.GONE);
//                    if (SubTVArchiveAdapter != null) {
//                        if (tvNoStream != null) {
//                            if (tvNoStream.getVisibility() != View.VISIBLE) {
//                                SubTVArchiveAdapter.filter(newText, tvNoRecordFound);
//                            }
//                        }
//                    }
//
//                    return false;
//                }
//            });
//
//            return true;
//        }


        return false;
    }

    private void logoutUser() {
//        Toast.makeText(context, AppConst.LOGGED_OUT, Toast.LENGTH_SHORT).show();
//        Intent intentLogout = new Intent(context, LoginActivity.class);
//        SharedPreferences loginPreferences;
//        SharedPreferences.Editor loginPreferencesEditor;
//        loginPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, MODE_PRIVATE);
//        loginPreferencesEditor = loginPreferences.edit();
//        loginPreferencesEditor.clear();
//        loginPreferencesEditor.commit();
//        startActivity(intentLogout);
    }

    private void initialize(int nowPlayingPosition) {
        context = getContext();
//        liveStreamDBHandler = new LiveStreamDBHandler(context);
        if (myRecyclerView != null && context != null) {
            myRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getContext());
//            LinearLayoutManager.setReverseLayout(true);

            myRecyclerView.setLayoutManager(layoutManager);
            myRecyclerView.smoothScrollToPosition(nowPlayingPosition);
//            myRecyclerView.setStackFromEnd(true);
            myRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, MODE_PRIVATE);
//            String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME, "");
//            String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD, "");

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void liveStreamCategories(List<LiveStreamCategoriesCallback> liveStreamCategoriesCallback) {

    }

    @Override
    public void liveStreams(List<LiveStreamsCallback> liveStreams, ArrayList<FavouriteDBModel> allFavourites) {

    }


    @Override
    public void liveStreamsEpg(LiveStreamsEpgCallback liveStreamsEpgCallback, TextView tvActiveChannel,
                               TextView tvNextChannel) {


    }


    @Override
    public void atStart() {
//        if (pbLoader != null)
//            pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinish() {
//        if (pbLoader != null)
//            pbLoader.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFailed(String message) {
        Utils.showToast(context, AppConst.NETWORK_ERROR_OCCURED);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}

