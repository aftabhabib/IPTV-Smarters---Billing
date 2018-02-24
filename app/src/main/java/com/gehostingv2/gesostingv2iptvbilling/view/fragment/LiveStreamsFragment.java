package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;

import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseUpdatedStatusDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.EpgChannelModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamCategoriesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsEpgCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;

import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.PasswordStatusDBModel;
//import com.happyboxtv.happyboxtviptvbilling.presenter.LiveStreamsPresenter;

import com.gehostingv2.gesostingv2iptvbilling.view.activity.ImportEPGActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ImportStreamsActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.LiveStreamsAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LiveStreamsInterface;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.MODE_PRIVATE;

public class LiveStreamsFragment extends Fragment
        implements LiveStreamsInterface,
        NavigationView.OnNavigationItemSelectedListener {
    public static final String ACTIVE_LIVE_STREAM_CATEGORY_ID = "";
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences loginPreferencesSharedPref;
    private LiveStreamsAdapter liveStreamsAdapter;
    private Toolbar toolbar;
    private SearchView searchView;
    public Context context;

    private TypedValue tv;
    int actionBarHeight;
    private Unbinder unbinder;
    private DatabaseHandler database;
    //    private ArrayList<LiveStreamsCallback> favouriteStreams = new ArrayList<>();
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();

    private String getActiveLiveStreamCategoryId;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int LIVE_FLAG ;
    private PopupWindow changeSortPopUp;


    LiveStreamDBHandler liveStreamDBHandler;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive =
            new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG =
            new DatabaseUpdatedStatusDBModel();
    LiveStreamsDBModel favouriteStream = new LiveStreamsDBModel();
    ArrayList<EpgChannelModel> epgChannelModelList = new ArrayList<>();


    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetail;
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamsDBModel> liveListDetail;

    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;


    public static LiveStreamsFragment newInstance(String category_id) {
        Bundle args = new Bundle();
        args.putString(ACTIVE_LIVE_STREAM_CATEGORY_ID, category_id);
        LiveStreamsFragment fragment = new LiveStreamsFragment();
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
        Fabric.with(getContext(), new Crashlytics());
        View view = inflater.inflate(R.layout.fragment_live_streams, container, false);
        unbinder = ButterKnife.bind(this, view);

        SharedPreferencesSort  = getActivity().getSharedPreferences(AppConst.LOGIN_PREF_SORT,MODE_PRIVATE);
        SharedPreferencesSortEditor=SharedPreferencesSort.edit();
        String sort_string = SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT,"");
        if(sort_string.equals("")){
            SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT , "0");
            SharedPreferencesSortEditor.commit();
        }

        ActivityCompat.invalidateOptionsMenu(getActivity());

        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        pref = getActivity().getSharedPreferences(AppConst.LIST_GRID_VIEW, MODE_PRIVATE);
        editor = pref.edit();
        AppConst.LIVE_FLAG = pref.getInt(AppConst.LIVE_STREAM, 0);
        if (AppConst.LIVE_FLAG == 1) {
            initialize1();
        } else {
            initialize();

        }
        return view;
    }

    private void setToolbarLogoImagewithSearchView() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        toolbar.inflateMenu(R.menu.menu_search_text_icon);
//        toolbar.inflateMenu(R.menu.menu_reload_cat_epg);
//        toolbar.inflateMenu(R.menu.menu_layout_view);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load_channels_vod1:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                // Setting Dialog Title
                alertDialog.setTitle(context.getResources().getString(R.string.confirm_refresh));
                // Setting Dialog Message
                alertDialog.setMessage(context.getResources().getString(R.string.proceed));
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.questionmark);
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        // Write your code here to invoke YES event
                        loadChannelsAndVod();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
                break;
            case R.id.menu_load_tv_guide1:
                //            Utils.showAlertBox(context, "");
                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(getActivity());
                // Setting Dialog Title
                alertDialog1.setTitle(context.getResources().getString(R.string.confirm_refresh));
                // Setting Dialog Message
                alertDialog1.setMessage(context.getResources().getString(R.string.proceed));
                // Setting Icon to Dialog
                alertDialog1.setIcon(R.drawable.questionmark);
                // Setting Positive "Yes" Button
                alertDialog1.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        // Write your code here to invoke YES event
                        loadTvGuid();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog1.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialog1.show();
                break;
            case R.id.action_search:
                searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setQueryHint(context.getResources().getString(R.string.search_channel));
                searchView.setIconifiedByDefault(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (tvNoStream.getText().toString().equals(context.getResources().getString(R.string.no_fav_channel_found))) {

                        } else {
                            tvNoRecordFound.setVisibility(View.GONE);
                        }
                        //filters list items from adapter
                        if (tvNoStream.getVisibility() == View.VISIBLE) {

                        } else {
                            if (liveStreamsAdapter != null) {
                                liveStreamsAdapter.filter(newText, tvNoRecordFound);
                            }
                        }
                        return false;
                    }
                });
                return true;
            case R.id.layout_view_grid:
                editor.putInt(AppConst.LIVE_STREAM, 1);
                editor.commit();
                initialize1();
                break;
            case R.id.layout_view_linear:
                editor.putInt(AppConst.LIVE_STREAM, 0);
                editor.commit();
                initialize();

                break;
            case R.id.menu_sort:
                showSortPopup( getActivity());
        }
        return false;
    }

    private void showSortPopup(final Activity context) {
        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.rl_password_prompt);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.sort_layout, viewGroup);

        // Creating the PopupWindow
        changeSortPopUp = new PopupWindow(context);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setFocusable(true);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = -20;
        int OFFSET_Y = 95;

        // Clear the default translucent background
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button savePasswordBT = (Button) layout.findViewById(R.id.bt_save_password);
        Button closedBT = (Button) layout.findViewById(R.id.bt_close);
        final RadioGroup rgRadio = (RadioGroup) layout.findViewById(R.id.rg_radio);
        RadioButton normal = (RadioButton) layout.findViewById(R.id.rb_normal);
        RadioButton last_added = (RadioButton) layout.findViewById(R.id.rb_lastadded);
        RadioButton atoz = (RadioButton) layout.findViewById(R.id.rb_atoz);
        RadioButton ztoa = (RadioButton) layout.findViewById(R.id.rb_ztoa);
        String sort = SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "");
        if (sort.equals("1")) {
            last_added.setChecked(true);
        } else if (sort.equals("2")) {
            atoz.setChecked(true);
        } else if (sort.equals("3")) {
            ztoa.setChecked(true);
        } else {
            normal.setChecked(true);
        }




        closedBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSortPopUp.dismiss();
            }
        });

        savePasswordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgRadio.getCheckedRadioButtonId();
                final RadioButton selectedPlayer1 = (RadioButton) layout.findViewById(selectedId);

                if (selectedPlayer1.getText().toString().equals(getResources().getString(R.string.sort_last_added))) {
                    SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "1");
                    SharedPreferencesSortEditor.commit();
                } else if (selectedPlayer1.getText().toString().equals(getResources().getString(R.string.sort_atoz))) {
                    SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "2");
                    SharedPreferencesSortEditor.commit();
                }else if (selectedPlayer1.getText().toString().equals(getResources().getString(R.string.sort_ztoa))) {
                    SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "3");
                    SharedPreferencesSortEditor.commit();
                } else {
                    SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "0");
                    SharedPreferencesSortEditor.commit();
                }

                pref = getActivity().getSharedPreferences(AppConst.LIST_GRID_VIEW, MODE_PRIVATE);
                editor = pref.edit();
                AppConst.LIVE_FLAG = pref.getInt(AppConst.LIVE_STREAM, 0);

                if (AppConst.LIVE_FLAG == 1) {
                    initialize1();
                } else {
                    initialize();
                }

                changeSortPopUp.dismiss();
            }


        });

    }




    private void loadChannelsAndVod() {
        if (context != null) {
            boolean isChannelVODUpdating = false;
            isChannelVODUpdating = getChannelVODUpdateStatus();
            if (isChannelVODUpdating) {
                LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
                liveStreamDBHandler.makeEmptyAllChannelsVODTablesRecords();
                Intent intentLoadingApiActivity = new Intent(context, ImportStreamsActivity.class);
                startActivity(intentLoadingApiActivity);
            } else {
                if (context != null)
                    Utils.showToast(context, getResources().getString(R.string.upadating_channels_vod));
            }
        }
    }

    private boolean getChannelVODUpdateStatus() {
        if (liveStreamDBHandler != null &&
                databaseUpdatedStatusDBModelLive != null) {
            databaseUpdatedStatusDBModelLive =
                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, AppConst.DB_CHANNELS_ID);
            if (databaseUpdatedStatusDBModelLive != null) {
                if (databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState() == null) {
                    return true;
                } else {
                    return databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH)
                            || databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED);
                }
            }
        }
        return false;
    }

    private void loadTvGuid() {
        if (context != null) {
            boolean isChannelEPGUpdating = false;
            isChannelEPGUpdating = getEPGUpdateStatus();
            if (isChannelEPGUpdating) {
                SharedPreferences loginPreferencesAfterLogin;
                SharedPreferences.Editor loginPrefsEditor;
                loginPreferencesAfterLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
                loginPrefsEditor = loginPreferencesAfterLogin.edit();
                if (loginPrefsEditor != null) {
                    loginPrefsEditor.putString(AppConst.SKIP_BUTTON_PREF_IPTV, "autoLoad");
                    loginPrefsEditor.commit();
                    String skipButton = loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF_IPTV, "");
                    LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
                    liveStreamDBHandler.makeEmptyEPG();
                    Intent intentLoadingApiActivity = new Intent(context, ImportEPGActivity.class);
                    startActivity(intentLoadingApiActivity);
                }
            } else {
                if (context != null)
                    Utils.showToast(context, getResources().getString(R.string.upadating_tv_guide));
            }
        }
    }

    private boolean getEPGUpdateStatus() {
        if (liveStreamDBHandler != null &&
                databaseUpdatedStatusDBModelEPG != null
                ) {
            databaseUpdatedStatusDBModelEPG =
                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, AppConst.DB_EPG_ID);
            if (databaseUpdatedStatusDBModelEPG != null) {
                if(databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null ||
                        databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH)
                        || databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED)) {
                    return true;
                }
                else {
                    return (databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null ||
                            databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(""));
                }
            }
        }
        return false;
    }


    private boolean getChannelEPGUpdateStatus() {
        if (context != null) {
            LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
            if (liveStreamDBHandler != null) {
                DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive =
                        new DatabaseUpdatedStatusDBModel();
                databaseUpdatedStatusDBModelLive =
                        liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, AppConst.DB_CHANNELS_ID);
                DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG =
                        new DatabaseUpdatedStatusDBModel();
                databaseUpdatedStatusDBModelEPG = liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, AppConst.DB_EPG_ID);
                if (databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().
                        equals(AppConst.DB_UPDATED_STATUS_FINISH) &&
                        databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().
                                equals(AppConst.DB_UPDATED_STATUS_FINISH)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    private void initialize1() {
        context = getContext();

        if (myRecyclerView != null && context != null) {
            myRecyclerView.setHasFixedSize(true);
            int noofCloumns = Utils.getNumberOfColumns(context);
            layoutManager = new GridLayoutManager(getContext(),noofCloumns+1);
            myRecyclerView.setLayoutManager(layoutManager);
            myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");

            setUpdatabaseResult();
        }
    }



    private void initialize() {
        context = getContext();
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        if (myRecyclerView != null && context != null) {
            myRecyclerView.setHasFixedSize(true);
            //int noofCloumns = Utils.getNumberOfColumns(context);
            layoutManager = new LinearLayoutManager(getContext());
//            layoutManager = new LinearLayoutManager(getContext());
            myRecyclerView.setLayoutManager(layoutManager);
            myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            setUpdatabaseResult();

        }
    }

    private void setUpdatabaseResult() {
        atStart();
        if (context != null) {
            liveStreamDBHandler = new LiveStreamDBHandler(context);
            categoryWithPasword = new ArrayList<PasswordStatusDBModel>();
            liveListDetailUnlcked = new ArrayList<LiveStreamsDBModel>();
            liveListDetailUnlckedDetail = new ArrayList<LiveStreamsDBModel>();
            liveListDetailAvailable = new ArrayList<LiveStreamsDBModel>();
            liveListDetail = new ArrayList<LiveStreamsDBModel>();
            ArrayList<LiveStreamsDBModel> channelAvailable =
                    liveStreamDBHandler.getAllLiveStreasWithCategoryId(getActiveLiveStreamCategoryId, "live");

            int parentalStatusCount = liveStreamDBHandler.getParentalStatusCount();
            if (parentalStatusCount > 0 && channelAvailable != null) {
                listPassword = getPasswordSetCategories();
                if (listPassword != null) {
                    liveListDetailUnlckedDetail = getUnlockedCategories(channelAvailable,
                            listPassword);
                }
                liveListDetailAvailable = liveListDetailUnlckedDetail;
            } else {
                liveListDetailAvailable = channelAvailable;
            }
            if (liveListDetailAvailable != null && myRecyclerView != null && liveListDetailAvailable.size() != 0) {
                onFinish();
                liveStreamsAdapter = new LiveStreamsAdapter(liveListDetailAvailable, getContext());
                myRecyclerView.setAdapter(liveStreamsAdapter);
                liveStreamsAdapter.notifyDataSetChanged();
            } else {
                onFinish();
                if (tvNoStream != null) {
                    tvNoStream.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    private ArrayList<LiveStreamsDBModel> getUnlockedCategories(ArrayList<LiveStreamsDBModel> liveListDetail,

                                                                ArrayList<String> listPassword) {
        for (LiveStreamsDBModel user1 : liveListDetail) {
            boolean flag = false;
            for (String user2 : listPassword) {
                if (user1.getCategoryId().equals(user2)) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                liveListDetailUnlcked.add(user1);
            }
        }
        return liveListDetailUnlcked;
    }

    private ArrayList<String> getPasswordSetCategories() {
        if (liveStreamDBHandler != null)
            categoryWithPasword =
                    liveStreamDBHandler.getAllPasswordStatus();
        if (categoryWithPasword != null) {
            for (PasswordStatusDBModel listItemLocked : categoryWithPasword) {
                if (listItemLocked.getPasswordStatus().equals(AppConst.PASSWORD_SET)) {
                    listPassword.add(listItemLocked.getPasswordStatusCategoryId());
                }
            }
        }
        return listPassword;

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
        if (liveStreams != null && liveStreams.size() != 0 && allFavourites != null && favouriteStreams != null) {
            database = new DatabaseHandler(context);
            allFavourites = database.getAllFavourites("live");
            for (int i = 0; i < allFavourites.size(); i++) {
                for (int j = 0; j < liveStreams.size(); j++) {
                    if (liveStreams.get(j).getStreamId() == allFavourites.get(i).getStreamID()) {
//                        favouriteStreams.add(liveStreams.get(j));
                    }
                }
            }

            if (liveStreams != null && myRecyclerView != null && favouriteStreams.size() != 0 && favouriteStreams != null) {
                liveStreamsAdapter = new LiveStreamsAdapter(favouriteStreams, getContext());
                myRecyclerView.setAdapter(liveStreamsAdapter);
                database.getAllFavourites("live");
                liveStreamsAdapter.notifyDataSetChanged();
            }
        } else {
            if (liveStreams != null && myRecyclerView != null && liveStreams.size() != 0) {
//                liveStreamsAdapter = new LiveStreamsAdapter(liveStreams, getContext());
                myRecyclerView.setAdapter(liveStreamsAdapter);
            } else {
                if (tvNoStream != null) {
                    tvNoStream.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void liveStreamsEpg(LiveStreamsEpgCallback liveStreamsEpgCallback, TextView tvActiveChannel, TextView tvNextChannel) {

    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (context != null && !message.isEmpty()) {
            Utils.showToast(context, getResources().getString(R.string.network_error));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }




    public void getFavourites() {
        favouriteStreams.clear();
//        liveStreamsAdapter = new LiveStreamsAdapter(favouriteStreams, getContext());
        if (myRecyclerView != null)
            myRecyclerView.setAdapter(liveStreamsAdapter);
        if (context != null) {
            database = new DatabaseHandler(context);
            ArrayList<FavouriteDBModel> allFavourites = database.getAllFavourites("live");
            int favIndex = 0;
            for (FavouriteDBModel favListItem : allFavourites) {
                LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
                LiveStreamsDBModel channelAvailable = liveStreamDBHandler.getLiveStreamFavouriteRow(favListItem.getCategoryID(),
                        String.valueOf(favListItem.getStreamID()));
                favouriteStreams.add(channelAvailable);
//                favIndex++;
            }
            if (myRecyclerView != null && favouriteStreams.size() != 0 && favouriteStreams != null) {
                onFinish();
                liveStreamsAdapter = new LiveStreamsAdapter(favouriteStreams, getContext());
                myRecyclerView.setAdapter(liveStreamsAdapter);
                liveStreamsAdapter.notifyDataSetChanged();
                tvNoStream.setVisibility(View.INVISIBLE);
            }
            if (tvNoStream != null && favouriteStreams.size() == 0) {
                onFinish();
                if (myRecyclerView != null)
                    myRecyclerView.setAdapter(liveStreamsAdapter);
                if(context!=null)
                tvNoStream.setText(context.getResources().getString(R.string.no_fav_channel_found));
                tvNoStream.setVisibility(View.VISIBLE);
            }
        }
    }
}
