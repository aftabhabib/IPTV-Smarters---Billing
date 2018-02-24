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
import android.widget.Toast;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseUpdatedStatusDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.EpgChannelModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.PasswordStatusDBModel;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ImportEPGActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ImportStreamsActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWelcomeActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.TVArchiveAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public class TVArchiveFragment extends Fragment
        implements
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
    @BindView(R.id.tv_egp_required)
    TextView tvEgpRequired;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences loginPreferencesSharedPref;
    private TVArchiveAdapter TVArchiveAdapter;
//    private LiveStreamListViewAdapter liveStreamsListViewAdapter;
    //    private ArrayList<LiveStreamsCallback> favouriteStreams = new ArrayList<>();
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();

    private Toolbar toolbar;
    SearchView searchView;
    public Context context;
    DatabaseHandler database;

    TypedValue tv;
    int actionBarHeight;
    Unbinder unbinder;

    private String getActiveLiveStreamCategoryId;
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
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private PopupWindow changeSortPopUp;
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;

    public static TVArchiveFragment newInstance(String category_id) {
        Bundle args = new Bundle();
        args.putString(ACTIVE_LIVE_STREAM_CATEGORY_ID, category_id);
        TVArchiveFragment fragment = new TVArchiveFragment();
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
        View view = inflater.inflate(R.layout.fragment_live_streams, container, false);
        unbinder = ButterKnife.bind(this, view);
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();

        SharedPreferencesSort  = getActivity().getSharedPreferences(AppConst.LOGIN_PREF_SORT,MODE_PRIVATE);
        SharedPreferencesSortEditor=SharedPreferencesSort.edit();
        String sort_string = SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT,"");
        if(sort_string.equals("")){
            SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT , "0");
            SharedPreferencesSortEditor.commit();
        }


        pref = getActivity().getSharedPreferences(AppConst.LIST_GRID_VIEW, MODE_PRIVATE);
        editor = pref.edit();
        AppConst.LIVE_FLAG = pref.getInt(AppConst.LIVE_STREAM, 0);



        if (AppConst.LIVE_FLAG == 1) {
            initialize();
        } else {
            initialize1();
        }
//        if(getActiveLiveStreamCategoryId.equals("-1")){
//            getFavourites();
//        }

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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        toolbar.inflateMenu(R.menu.menu_search_text_icon);  //menu_search
//        toolbar.inflateMenu(R.menu.menu_layout_view);
        MenuItem item = menu.findItem(R.id.layout_view_grid);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout1) {
            logoutUser();
        }
        if (id == R.id.action_search) {
            searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setQueryHint(getResources().getString(R.string.search_channel));
            searchView.setIconifiedByDefault(false);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //filters list items from adapter
                    tvNoRecordFound.setVisibility(View.GONE);
                    if (TVArchiveAdapter != null) {
                        if (tvNoStream != null) {
                            if (tvNoStream.getVisibility() != View.VISIBLE) {
                                TVArchiveAdapter.filter(newText, tvNoRecordFound);
                            }
                        }
                    }

                    return false;
                }
            });

            return true;
        }
        if (id == R.id.menu_load_channels_vod1) {
//            Utils.showAlertBox(context, "");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            // Setting Dialog Title
            alertDialog.setTitle("Confirm to Refreshing...");
            // Setting Dialog Message
            alertDialog.setMessage("Do you want to proceed ?");
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.questionmark);
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    loadChannelsAndVod();
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    dialog.cancel();
                }
            });
            // Showing Alert Message
            alertDialog.show();
            return true;
        }
        if (id == R.id.menu_load_tv_guide1) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            // Setting Dialog Title
            alertDialog.setTitle("Confirm to Refreshing...");
            // Setting Dialog Message
            alertDialog.setMessage("Do you want to proceed ?");
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.questionmark);
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    loadTvGuid();
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    dialog.cancel();
                }
            });
            // Showing Alert Message
            alertDialog.show();
            return true;
        }

        if (id == R.id.layout_view_grid) {
            // myRecyclerView.setVisibility(View.GONE);

            editor.putInt(AppConst.LIVE_STREAM, 1);
            editor.commit();
//            AppConst.LIVE_FLAG = 0;
//            initialize();
            initialize();
            //myRecyclerView.setVisibility(View.VISIBLE);
        }
        if (id == R.id.layout_view_linear) {
            //myRecyclerView.setVisibility(View.GONE);
            editor.putInt(AppConst.LIVE_STREAM, 0);
            editor.commit();

//            AppConst.LIVE_FLAG = 1;
//            initialize1();
            initialize1();
        }

        if (id == R.id.menu_sort){

            showSortPopup( getActivity());



        }

        return super.onOptionsItemSelected(item);
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
                    initialize();
                } else {
                    initialize1();
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
                    loginPrefsEditor.putString(AppConst.SKIP_BUTTON_PREF, "autoLoad");
                    loginPrefsEditor.commit();
                    String skipButton = loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF, "");
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
                if (databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null || databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH)
                        || databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED)) {
                    return true;
                } else {
                    return (databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null ||
                            databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(""));
                }
            }
        }
        return false;
    }

    private boolean getChannelEPGUpdateStatus() {

        if (liveStreamDBHandler != null &&
                databaseUpdatedStatusDBModelLive != null &&
                databaseUpdatedStatusDBModelEPG != null
                ) {
            databaseUpdatedStatusDBModelLive =
                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, AppConst.DB_CHANNELS_ID);
            databaseUpdatedStatusDBModelEPG =
                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, AppConst.DB_EPG_ID);
            if (databaseUpdatedStatusDBModelLive != null && databaseUpdatedStatusDBModelEPG != null) {
                if (databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null || databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState() == null) {
                    return true;
                } else {
                    return databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH) && databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH)
                            || databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED) && databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED)
                            || databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH) && databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED)
                            || databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED) && databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH);
                }
            }

        }
        return false;
    }

    private void logoutUser() {
        if (context != null)
            Toast.makeText(context, getResources().getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
        Intent intentLogout = new Intent(context, LoginWelcomeActivity.class);
        SharedPreferences loginPreferences;
        SharedPreferences.Editor loginPreferencesEditor;
        loginPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        loginPreferencesEditor = loginPreferences.edit();
        loginPreferencesEditor.clear();
        loginPreferencesEditor.commit();
        startActivity(intentLogout);
    }

    private void initialize() {
        context = getContext();
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        if (myRecyclerView != null && context != null) {
            myRecyclerView.setHasFixedSize(true);
            int noofCloumns = Utils.getNumberOfColumns(context);
            layoutManager = new GridLayoutManager(getContext(), noofCloumns + 1);
//            layoutManager = new LinearLayoutManager(getContext());
            myRecyclerView.setLayoutManager(layoutManager);
            myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            setUpdatabaseResult();
        }
    }

    private void initialize1() {
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
            LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
            categoryWithPasword = new ArrayList<PasswordStatusDBModel>();
            liveListDetailUnlcked = new ArrayList<LiveStreamsDBModel>();
            liveListDetailUnlckedDetail = new ArrayList<LiveStreamsDBModel>();
            liveListDetailAvailable = new ArrayList<LiveStreamsDBModel>();
            liveListDetail = new ArrayList<LiveStreamsDBModel>();
            ArrayList<LiveStreamsDBModel> channelAvailable =
                    liveStreamDBHandler.getAllLiveStreamsArchive(getActiveLiveStreamCategoryId);

//            if(getActiveLiveStreamCategoryId.equals("-2")) {
//                ArrayList<LiveStreamsDBModel> channelAvailable1 =
//                        liveStreamDBHandler.getAllLiveStreasWithCategoryId("null", "live");
//            }



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
//            if (getActiveLiveStreamCategoryId.equals("1")) {
//                onFinish();
//
//            }else {
                if (liveListDetailAvailable != null && myRecyclerView != null && liveListDetailAvailable.size() != 0) {
                    onFinish();

                    TVArchiveAdapter = new TVArchiveAdapter(liveListDetailAvailable, getContext());
                    myRecyclerView.setAdapter(TVArchiveAdapter);
                    TVArchiveAdapter.notifyDataSetChanged();
                } else {
                    onFinish();
                    if (tvNoStream != null) {
                        tvNoStream.setText(context.getResources().getString(R.string.no_record_found));
                        tvNoStream.setVisibility(View.VISIBLE);
                    }
                    tvEgpRequired.setVisibility(View.VISIBLE);
                }
//            }
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


//    public void getFavourites() {
//        favouriteStreams.clear();
////        TVArchiveAdapter = new TVArchiveAdapter(favouriteStreams, getContext());
//        if (myRecyclerView != null)
//            myRecyclerView.setAdapter(TVArchiveAdapter);
//        if (context != null) {
//            database = new DatabaseHandler(context);
//            ArrayList<FavouriteDBModel> allFavourites = database.getAllFavourites("live");
//            int favIndex = 0;
//            for (FavouriteDBModel favListItem : allFavourites) {
//                LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
//                LiveStreamsDBModel channelAvailable = liveStreamDBHandler.getLiveStreamFavouriteRow(favListItem.getCategoryID(),
//                        String.valueOf(favListItem.getStreamID()));
//                if (channelAvailable != null) {
//                    favouriteStreams.add(channelAvailable);
//                }
////                favIndex++;
//            }
//            if (myRecyclerView != null && favouriteStreams != null && favouriteStreams.size() != 0) {
//                onFinish();
//                TVArchiveAdapter = new TVArchiveAdapter(favouriteStreams, getContext());
//                myRecyclerView.setAdapter(TVArchiveAdapter);
//                TVArchiveAdapter.notifyDataSetChanged();
//                tvNoStream.setVisibility(View.INVISIBLE);
//            }
//            if (tvNoStream != null && favouriteStreams != null && favouriteStreams.size() == 0) {
//                onFinish();
//                if (myRecyclerView != null)
//                    myRecyclerView.setAdapter(TVArchiveAdapter);
//                tvNoStream.setText(getResources().getString(R.string.no_fav_channel_found));
//                tvNoStream.setVisibility(View.VISIBLE);
//            }
//        }
//    }


    public void atStart() {
        if (pbLoader != null)
            pbLoader.setVisibility(View.VISIBLE);
    }


    public void onFinish() {
        if (pbLoader != null)
            pbLoader.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}
