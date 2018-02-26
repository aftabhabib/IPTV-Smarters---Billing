package com.gehostingv2.gesostingv2iptvbilling.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.AdapterSectionRecycler;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetIPTVCredentialsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamCategoriesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsEpgCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.MyDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.VodInfoCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.ExpandedMenuModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ClientDetailPresenter;
import com.gehostingv2.gesostingv2iptvbilling.presenter.VodPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.ExpandndableListAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.VodAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.VodAdapterNewFlow;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.VodSubCatAdpaterNew;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.ActiveServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.CancelledServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.HomeFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.InvoicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.InvoicesPaidFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.InvoicesUnpaidFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.MakePaymentFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.OpenTicketFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.OpenTicketGeneralEnquiriesDepartmentFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.PaymentSuccessfulFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.PendingServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.ServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.SubscriptionProductFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.SuspendedServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.TerminatedServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.TicketsFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.TicketsHomeFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.ViewTicketsRequestFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ClientDetailInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LiveStreamsInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.VodInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst.CURRENT_TAG;
import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst.TAG_HOME;
import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst.TAG_MY_DOMAINS;

public class VoDListViewCatActivity extends AppCompatActivity implements VodInterface,
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        LiveStreamsInterface,
        ClientDetailInterface,
        HomeFragment.OnFragmentInteractionListener,
        ServicesFragment.OnFragmentInteractionListener,
        InvoicesFragment.OnFragmentInteractionListener,
        TicketsFragment.OnFragmentInteractionListener,
        ViewTicketsRequestFragment.OnFragmentInteractionListener,
        CancelledServicesFragment.OnFragmentInteractionListener,
        ActiveServicesFragment.OnFragmentInteractionListener,
        PendingServicesFragment.OnFragmentInteractionListener,
        SuspendedServicesFragment.OnFragmentInteractionListener,
        TerminatedServicesFragment.OnFragmentInteractionListener,
        SubscriptionProductFragment.OnFragmentInteractionListener,
        InvoicesPaidFragment.OnFragmentInteractionListener,
        InvoicesUnpaidFragment.OnFragmentInteractionListener,
        OpenTicketFragment.OnFragmentInteractionListener,
        OpenTicketGeneralEnquiriesDepartmentFragment.OnFragmentInteractionListener,
        MakePaymentFragment.OnFragmentInteractionListener,
        PaymentSuccessfulFragment.OnFragmentInteractionListener,
        TicketsHomeFragment.OnFragmentInteractionListener {
    private static final String JSON = "";
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    //    @BindView(R.id.sliding_tabs)
//    TabLayout slidingTabs;
//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
    @BindView(R.id.tv_header_title)
    ImageView tvHeaderTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_divider)
    ImageView tvDivider;
    @BindView(R.id.content_drawer)
    RelativeLayout contentDrawer;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationmenu)
    ExpandableListView navigationmenu;
    @BindView(R.id.navigation_drawer_bottom)
    NavigationView navigationDrawerBottom;

//    @BindView(R.id.pb_paging_loader)
//    ProgressBar pbPagingLoader;

    @BindView(R.id.tv_noStream)
    TextView tvNoStream;

    private SearchView searchView;
    private int clientId;
    private String clientEmail;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;


    private ClientDetailPresenter clientDetailPresenter;
    private TextView clientNanmeTv;
    private TextView clientEmailTv;
    private TextView clientremaingCreditsTv;
    private ExpandndableListAdapter mMenuAdapter;
    private List<ExpandedMenuModel> listDataHeader;
    private HashMap<ExpandedMenuModel, List<String>> listDataChild;
    private String selectedItem = TAG_HOME;
    private Handler mHandler;
    private ImageView logoHomeIV;
    private boolean condition_true = false;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private VodPresenter vodCategoriesPresenter;

    private Context context;
    private ProgressDialog progressDialog;
    private SharedPreferences loginPreferencesAfterLogin;
    private TextView clientNameTv;
    private TypedValue tv;
    private int actionBarHeight;

    ArrayList<LiveStreamCategoryIdDBModel> categoriesList;


    private LiveStreamDBHandler liveStreamDBHandler;

    private LinearLayoutManager linearLayoutManager;


    private RecyclerView.LayoutManager mLayoutManager;


    private String getActiveLiveStreamCategoryId = "";
    private String getActiveLiveStreamCategoryName = "";
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryList = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal_menu = new ArrayList<>();
    private boolean isSubcaetgroy = false;
    private AdapterSectionRecycler adapterRecycler;


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences loginPreferencesSharedPref;
    private VodAdapter vodAdapter;
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();
    DatabaseHandler database;
    private VodAdapterNewFlow mAdapter;
    static ProgressBar pbPagingLoader1;
    boolean isSubcaetgroyAvail = false;
    private PopupWindow changeSortPopUp;
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
    GridLayoutManager gridLayoutManager;

    private VodSubCatAdpaterNew vodSubCatAdpaterNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        SharedPreferencesSort = getSharedPreferences(AppConst.LOGIN_PREF_SORT, MODE_PRIVATE);
        SharedPreferencesSortEditor = SharedPreferencesSort.edit();
        String sort_string = SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "");
        if (sort_string.equals("")) {
            SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "0");
            SharedPreferencesSortEditor.commit();
        }

        Intent intent = getIntent();
        if (intent != null) {
            getActiveLiveStreamCategoryId = intent.getStringExtra(AppConst.CATEGORY_ID);
            getActiveLiveStreamCategoryName = intent.getStringExtra(AppConst.CATEGORY_NAME);
        }
        context = this;
        mAdapter = new VodAdapterNewFlow();


        liveStreamDBHandler = new LiveStreamDBHandler(context);

        switch (getActiveLiveStreamCategoryId) {
            case "-1":
                setContentView(R.layout.activity_vo_dcategory);
                ButterKnife.bind(this);
                atStart();
                setLayout();
                getFavourites();
                break;
            case "0":
                setContentView(R.layout.activity_vo_dcategory);
                ButterKnife.bind(this);
                atStart();
                setLayout();
                break;
            default:
                subCategoryListFinal = liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(getActiveLiveStreamCategoryId);
                if (subCategoryListFinal != null && subCategoryListFinal.size() == 0) {  //!isSubcaetgroy
                    setContentView(R.layout.activity_vo_dcategory);
                    ButterKnife.bind(this);
                    atStart();
                    setLayout();
                } else {
                    setContentView(R.layout.activity_vod_new_flow_subcategroires);
                    ButterKnife.bind(this);
                    isSubcaetgroyAvail = true;
                    atStart();
                    setSubCategoryLayout(subCategoryListFinal);
                }
                break;
        }
        changeStatusBarColor();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        setDrawerToggle();

//        setUpDatabaseResults();

    }


    private void setSubCategoryLayout(ArrayList<LiveStreamCategoryIdDBModel> subCategoryList) {
//        ActivityCompat.invalidateOptionsMenu(getActivity());
//        setHasOptionsMenu(true);
//        setToolbarLogoImagewithSearchView();
        initializeSubCat(subCategoryList);
    }

    private void initializeSubCat(ArrayList<LiveStreamCategoryIdDBModel> subCategoryList) {

        if (myRecyclerView != null && context != null) {
            myRecyclerView.setHasFixedSize(true);
            if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                gridLayoutManager = new GridLayoutManager(this, 2);
            } else {
                gridLayoutManager = new GridLayoutManager(this, 1);
            }
            myRecyclerView.setLayoutManager(gridLayoutManager);
            myRecyclerView.setHasFixedSize(true);
            onFinish();
            vodSubCatAdpaterNew = new VodSubCatAdpaterNew(subCategoryList, context, liveStreamDBHandler);
            myRecyclerView.setAdapter(vodSubCatAdpaterNew);


        }
    }

    private void setDrawerToggle() {
        context = this;
        clientDetailPresenter = new ClientDetailPresenter(this, context);
        mHandler = new Handler();
        if (navView != null) {
            setupDrawerContent(navView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                setupDrawerContent(navView);
            }
        }
        prepareListData();
        mMenuAdapter = new ExpandndableListAdapter(context, listDataHeader, listDataChild, navigationmenu);
        // setting list adapter
        navigationmenu.setAdapter(mMenuAdapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        setToggleIconPosition();
        changeStatusBarColor();
        toolbarSet();
        headerView();

        SharedPreferences pref1 = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        clientId = pref1.getInt(AppConst.CLIENT_ID, -1);
        clientEmail = pref1.getString(AppConst.PREF_EMAIL_WHMCS, "");

        SharedPreferences prefUsername = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        String username = prefUsername.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
        String password = prefUsername.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
        if (clientId != -1 && clientId != 0) {
            if (!clientEmail.isEmpty() && clientEmail != null && clientNanmeTv != null) {
                clientNanmeTv.setText(clientEmail);
            } else {
                clientDetailPresenter.getMyDetail(clientId);
            }
            if (!username.isEmpty() && !username.equals("") &&
                    !password.isEmpty() && !password.equals("")) {
                clientDetailPresenter.getIPTVDetails(username, password);
            } else {
                clientDetailPresenter.getIPTVServices(clientId);
            }
        } else if (username != null && !username.isEmpty()) {
            clientNanmeTv.setText(username);
        }

        if (clientDetailPresenter != null) {
            if (!username.isEmpty() && !username.equals("") &&
                    !password.isEmpty() && !password.equals(""))
                clientDetailPresenter.getIPTVDetails(username, password);
        }
    }

    private void setLayout() {
//        ActivityCompat.invalidateOptionsMenu(getActivity());
//        setHasOptionsMenu(true);
//        setToolbarLogoImagewithSearchView();
        pref = getSharedPreferences(AppConst.LIST_GRID_VIEW, MODE_PRIVATE);
        editor = pref.edit();
        AppConst.LIVE_FLAG_VOD = pref.getInt(AppConst.VOD, 0);
        if (AppConst.LIVE_FLAG_VOD == 1) {
            initialize1();
        } else {
            initialize();
        }
    }

    private void initialize1() {
        context = this;
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        if (myRecyclerView != null && context != null) {
            myRecyclerView.setHasFixedSize(true);
            // int noofCloumns = Utils.getNumberOfColumns(context);
            layoutManager = new LinearLayoutManager(context);
            myRecyclerView.setLayoutManager(layoutManager);
            myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            setUpdatabaseResult();
        }
    }

    private void initialize() {
        context = this;
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        if (myRecyclerView != null && context != null) {
            myRecyclerView.setHasFixedSize(true);
            int noofCloumns = Utils.getNumberOfColumns(context);
            layoutManager = new GridLayoutManager(context, noofCloumns + 1);
            myRecyclerView.setLayoutManager(layoutManager);
            myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            setUpdatabaseResult();
        }
    }

    private void setUpdatabaseResult() {

        if (context != null) {
            LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
            if (getActiveLiveStreamCategoryId.equals("-1")) {

            } else {
                ArrayList<LiveStreamsDBModel> channelAvailable = liveStreamDBHandler.getAllLiveStreasWithCategoryId(getActiveLiveStreamCategoryId, "movie");
                onFinish();
                if (progressDialog != null)
                    progressDialog.dismiss();
                if (channelAvailable != null && myRecyclerView != null && channelAvailable.size() != 0) {


                    vodAdapter = new VodAdapter(channelAvailable, context);
                    myRecyclerView.setAdapter(vodAdapter);
                } else {

                    if (tvNoStream != null) {
                        tvNoStream.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        if (progressDialog != null)
            progressDialog.dismiss();
    }


    public void getFavourites() {
        favouriteStreams.clear();
//        vodAdapter = new VodAdapter(favouriteStreams, getContext());
        if (myRecyclerView != null)
            myRecyclerView.setAdapter(vodAdapter);
        if (context != null) {
            database = new DatabaseHandler(context);
            ArrayList<FavouriteDBModel> allFavourites = database.getAllFavourites("vod");

            for (FavouriteDBModel favListItem : allFavourites) {
                LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
                LiveStreamsDBModel channelAvailable = liveStreamDBHandler.getLiveStreamFavouriteRow(favListItem.getCategoryID(),
                        String.valueOf(favListItem.getStreamID()));
                if (channelAvailable != null) {
                    favouriteStreams.add(channelAvailable);
                }
            }
            onFinish();

            if (myRecyclerView != null && favouriteStreams != null && favouriteStreams.size() != 0) {

                vodAdapter = new VodAdapter(favouriteStreams, context);
                myRecyclerView.setAdapter(vodAdapter);
                vodAdapter.notifyDataSetChanged();
                tvNoStream.setVisibility(View.INVISIBLE);
            }
            if (tvNoStream != null && favouriteStreams != null && favouriteStreams.size() == 0) {

                if (myRecyclerView != null)
                    myRecyclerView.setAdapter(vodAdapter);
                tvNoStream.setText(getResources().getString(R.string.no_fav_vod_found));
                tvNoStream.setVisibility(View.VISIBLE);
            }
        }
    }


    private void toolbarSet() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        logoHomeIV = (ImageView) toolbar.findViewById(R.id.tv_header_title);
        if (logoHomeIV != null)
            logoHomeIV.setImageResource(R.drawable.logo_home_new);
    }



    private HomeFragment getHomeFragment() {
        selectedItem = TAG_HOME;
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    private void setupDrawerContent(NavigationView navigationView) {

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };


        navigationmenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                //Log.d("DEBUG", "submenu item clicked");

                selectedItem = (String) expandableListView.getExpandableListAdapter().getChild(i, i1);
//                loadFragment(selectedItem);
                if ((selectedItem.equalsIgnoreCase(getString(R.string.drawer_billing_my_invoices))
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_services_order_new_services))
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_support_ticket))
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_support_open_ticket))
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_services_my_services)))) {
                    drawerLayout.closeDrawers();
                }
                selectedFragment(selectedItem);
                return false;
            }
        });
        navigationmenu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                //Log.d("DEBUG", "heading clicked");
                int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(i));
                selectedItem = listDataHeader.get(i).getIconName();
                if (selectedItem.equalsIgnoreCase(getString(R.string.drawer_home))
                        || selectedItem.equalsIgnoreCase(TAG_MY_DOMAINS)
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_live_tv))
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_live_tv_guide))
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_account_info))
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_vod))
                        || selectedItem.equalsIgnoreCase(getString(R.string.drawer_settings))) {
                    drawerLayout.closeDrawers();
                }
                selectedFragment(selectedItem);
                return false;
            }
        });

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        invalidateOptionsMenu();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.closeDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (drawerView != null && drawerView == navigationmenu) {
                    super.onDrawerSlide(drawerView, 0);
                } else {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void selectedFragment(String selectedItem) {
        toolbarSet();
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            String name = fragmentManager.getBackStackEntryAt(0).getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();

        Utils.startActivityOtherDash(context,selectedItem);

//        if (selectedItem.equals(getResources().getString(R.string.drawer_home))) {
//            Intent dashboardActivtyIntent = new Intent(this, DashboardActivity.class);
//            startActivity(dashboardActivtyIntent);
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_services_my_services))) {
//            SharedPreferences prefServices = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
//            clientId = prefServices.getInt(AppConst.CLIENT_ID, -1);
//            if (clientId == -1 || clientId == 0) {
//                Intent intentLogin = new Intent(this, LoginWHMCSActivity.class);
//                intentLogin.putExtra(AppConst.ACTIVITY_SERVICES, AppConst.ACTIVITY_SERVICES);
//                startActivity(intentLogin);
//            } else {
//                Intent servicesIntent = new Intent(this, ServicesActivity.class);
//                startActivity(servicesIntent);
//            }
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_billing_my_invoices))) {
//            SharedPreferences prefInvoice = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
//            clientId = prefInvoice.getInt(AppConst.CLIENT_ID, -1);
//            if (clientId == -1 || clientId == 0) {
//                Intent intentLogin = new Intent(this, LoginWHMCSActivity.class);
//                intentLogin.putExtra(AppConst.ACTIVITY_INVOICES, AppConst.ACTIVITY_INVOICES);
//                startActivity(intentLogin);
//            } else {
//                Intent invoiceActivityIntent = new Intent(this, InvoicesActivity.class);
//                startActivity(invoiceActivityIntent);
//            }
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_services_order_new_services))) {
//            SharedPreferences prefOrderNewServices = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
//            clientId = prefOrderNewServices.getInt(AppConst.CLIENT_ID, -1);
//            if (clientId == -1 || clientId == 0) {
//                Intent intentLogin = new Intent(this, LoginWHMCSActivity.class);
//                intentLogin.putExtra(AppConst.ACTIVITY_INVOICES, AppConst.ACTIVITY_INVOICES);
//                startActivity(intentLogin);
//            } else {
//                Intent invoiceActivityIntent = new Intent(this, OrderNewServicesActivtiy.class);
//                startActivity(invoiceActivityIntent);
//            }
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_support_ticket))) {
//            SharedPreferences prefTicket = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
//            clientId = prefTicket.getInt(AppConst.CLIENT_ID, -1);
//            if (clientId == -1 || clientId == 0) {
//                Intent intentLogin = new Intent(this, LoginWHMCSActivity.class);
//                intentLogin.putExtra(AppConst.ACTIVITY_TICKETS, AppConst.ACTIVITY_TICKETS);
//                startActivity(intentLogin);
//            } else {
//                Intent ticketsActivityIntent = new Intent(this, TicketsActivity.class);
//                startActivity(ticketsActivityIntent);
//            }
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_support_open_ticket))) {
//            SharedPreferences prefOpenTicket = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
//            clientId = prefOpenTicket.getInt(AppConst.CLIENT_ID, -1);
//            if (clientId == -1 || clientId == 0) {
//                Intent intentLogin = new Intent(this, LoginWHMCSActivity.class);
//                intentLogin.putExtra(AppConst.ACTIVITY_OPEN_TICKETS, AppConst.ACTIVITY_OPEN_TICKETS);
//                startActivity(intentLogin);
//            } else {
//                Intent openTicketIntent = new Intent(this, OpenTicketActivity.class);
//                startActivity(openTicketIntent);
//            }
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_live_tv))) {
//            Intent liveStreamActivity = new Intent(this, LiveTVTabViewActivity.class);
//            startActivity(liveStreamActivity);
//        }
//        else if (selectedItem.equals(getResources().getString(R.string.drawer_live_tv_guide))) {
//            startDashboardActivty();
//        }
//
//        else if (selectedItem.equals(getResources().getString(R.string.drawer_account_info))) {
//            Intent accountInfoIntent = new Intent(this, AccountInfoActivity.class);
//            startActivity(accountInfoIntent);
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_vod))) {
//            Intent intent = new Intent(this, VodTabViewActivity.class);
//            startActivity(intent);
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_settings))) {
//            Intent settingsIntent = new Intent(this, SettingssActivity.class);
//            startActivity(settingsIntent);
//        }
//        else if (selectedItem.equals(getResources().getString(R.string.drawer_logout))) {
//                SharedPreferences loginPreferences;
//                SharedPreferences.Editor loginPreferencesEditor;
//                loginPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
//                loginPreferencesEditor = loginPreferences.edit();
//                loginPreferencesEditor.clear();
//                loginPreferencesEditor.commit();
//
//                Toast.makeText(this, context.getResources().getString(R.string.logout_successfully), Toast.LENGTH_SHORT).show();
//                Intent intentLogout = new Intent(this, LoginWelcomeActivity.class);
//                SharedPreferences loginPreferencesClientid;
//                SharedPreferences.Editor loginPreferencesClientidEditor;
//                loginPreferencesClientid = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
//                loginPreferencesClientidEditor = loginPreferencesClientid.edit();
//                loginPreferencesClientidEditor.clear();
//                loginPreferencesClientidEditor.commit();
//                startActivity(intentLogout);
//                finish();
//        }
    }
//
//    private void startDashboardActivty() {
//        Intent dashboardActitvityIntent = new Intent(this, DashboardActivity.class);
//        dashboardActitvityIntent.putExtra(AppConst.LAUNCH_TV_GUIDE, AppConst.LAUNCH_TV_GUIDE);
//        startActivity(dashboardActitvityIntent);
//        finish();
//    }

    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        ExpandedMenuModel homeHeader = new ExpandedMenuModel();
        homeHeader.setIconName(getString(R.string.drawer_home));
        homeHeader.setIconImg(R.drawable.drawer_home);
        // Adding data header
        listDataHeader.add(homeHeader);

        ExpandedMenuModel liveTv = new ExpandedMenuModel();
        liveTv.setIconName(getString(R.string.drawer_live_tv));
        liveTv.setIconImg(R.drawable.livetv_icon); //livetv_icon
        // Adding data header
        listDataHeader.add(liveTv);

        ExpandedMenuModel liveTvWihGuie = new ExpandedMenuModel();
        liveTvWihGuie.setIconName(getString(R.string.drawer_live_tv_guide));
        liveTvWihGuie.setIconImg(R.drawable.guide_icon);
        // Adding data header
        listDataHeader.add(liveTvWihGuie);

        ExpandedMenuModel accountInfo = new ExpandedMenuModel();
        accountInfo.setIconName(getString(R.string.drawer_account_info));
        accountInfo.setIconImg(R.drawable.account_info);
        // Adding data header
        listDataHeader.add(accountInfo);

        ExpandedMenuModel videoOnDemand = new ExpandedMenuModel();
        videoOnDemand.setIconName(getString(R.string.drawer_vod));
        videoOnDemand.setIconImg(R.drawable.vod_icon);
        // Adding data header
        listDataHeader.add(videoOnDemand);

        ExpandedMenuModel tvArchive = new ExpandedMenuModel();
        tvArchive.setIconName(getString(R.string.drawer_tv_archieve));
        tvArchive.setIconImg(R.drawable.tv_archive);
        // Adding data header
        listDataHeader.add(tvArchive);

        ExpandedMenuModel settings = new ExpandedMenuModel();
        settings.setIconName(getString(R.string.drawer_settings));
        settings.setIconImg(R.drawable.settings_icon);
        // Adding data header
        listDataHeader.add(settings);

        ExpandedMenuModel servicesHeader = new ExpandedMenuModel();
        servicesHeader.setIconName(getString(R.string.drawer_services));
        servicesHeader.setIconImg(R.drawable.drawer_services);
        // Adding data header
        listDataHeader.add(servicesHeader);

        ExpandedMenuModel billingHeader = new ExpandedMenuModel();
        billingHeader.setIconName(getString(R.string.drawer_billing));
        billingHeader.setIconImg(R.drawable.drawer_billing);
        // Adding data header
        listDataHeader.add(billingHeader);

        ExpandedMenuModel supportHeader = new ExpandedMenuModel();
        supportHeader.setIconName(getString(R.string.drawer_support));
        supportHeader.setIconImg(R.drawable.drawer_suppport);
        // Adding data header
        listDataHeader.add(supportHeader);

        ExpandedMenuModel log_out = new ExpandedMenuModel();
        log_out.setIconName(getString(R.string.drawer_logout));
        log_out.setIconImg(R.drawable.logout_icon);
        // Adding data header
        listDataHeader.add(log_out);


        List<String> servicesList = new ArrayList<String>();
        servicesList.add(getString(R.string.drawer_services_my_services));
        servicesList.add(getString(R.string.drawer_services_order_new_services));
//        servicesList.add("ORDER NEW SERVICES");

        List<String> billingList = new ArrayList<String>();
        billingList.add(getString(R.string.drawer_billing_my_invoices));
//        billingList.add("ORDER NEW SERVICES");
//        billingList.add("MY CREDITS");

        List<String> supportList = new ArrayList<String>();
        supportList.add(getString(R.string.drawer_support_ticket));
        supportList.add(getString(R.string.drawer_support_open_ticket));
        listDataChild.put(listDataHeader.get(7), servicesList);
        listDataChild.put(listDataHeader.get(8), billingList);
        listDataChild.put(listDataHeader.get(9), supportList);



    }

    private void setToggleIconPosition() {
        tv = new TypedValue();
        if (this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, this.getResources().getDisplayMetrics());
        }
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            // make toggle drawable center-vertical, you can make each view alignment whatever you want
            if (toolbar.getChildAt(i) instanceof ImageButton) {
                Toolbar.LayoutParams lp = (Toolbar.LayoutParams) toolbar.getChildAt(i).getLayoutParams();
                lp.gravity = Gravity.CENTER_VERTICAL;
            }
        }
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //moveTaskToBack(true);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void vodInfo(VodInfoCallback vodInfoCallback) {

    }

    public void onResume() {
        super.onResume();
        mAdapter.setVisibiltygone(pbPagingLoader1);
        setDrawerToggle();
    }
//    public void progressBar(ProgressBar pbPagingLoader) {
//        this.pbPagingLoader1 = pbPagingLoader;
//
//    }


    private void headerView() {
        View header = navView.getHeaderView(0);
        ImageView closeDrawerIV = (ImageView) header.findViewById(R.id.iv_close_drawer);
        clientNanmeTv = (TextView) header.findViewById(R.id.tv_client_name);
        closeDrawerIV.setOnClickListener(this);
        View footer = navigationDrawerBottom.getHeaderView(0);
        TextView logoutUserTV = (TextView) footer.findViewById(R.id.tv_logout);
        logoutUserTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close_drawer:
                drawerLayout.closeDrawers();
                break;
            case R.id.tv_logout:
                SharedPreferences loginPreferences;
                SharedPreferences.Editor loginPreferencesEditor;
                loginPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
                loginPreferencesEditor = loginPreferences.edit();
                loginPreferencesEditor.clear();
                loginPreferencesEditor.commit();

                Toast.makeText(this, context.getResources().getString(R.string.logout_successfully), Toast.LENGTH_SHORT).show();
                Intent intentLogout = new Intent(this, LoginWelcomeActivity.class);
                SharedPreferences loginPreferencesClientid;
                SharedPreferences.Editor loginPreferencesClientidEditor;
                loginPreferencesClientid = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                loginPreferencesClientidEditor = loginPreferencesClientid.edit();
                loginPreferencesClientidEditor.clear();
                loginPreferencesClientidEditor.commit();
                startActivity(intentLogout);
                finish();
                break;
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        condition_true = true;
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//                    drawerLayout.closeDrawer(Gravity.LEFT);
//                } else {
//                    drawerLayout.openDrawer(Gravity.LEFT);
//                }
//                return super.onOptionsItemSelected(item);
//            case R.id.action_search:
//                // Not implemented here
//                return false;
//            default:
//                break;
//
//        }
//        return false;
//    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.

        if (actionBarDrawerToggle != null)
            actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        actionBarDrawerToggle.onConfigurationChanged(newConfig);
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
    public void getClientDetails(MyDetailsCallback myDetailsCallback) {
        if (myDetailsCallback != null && myDetailsCallback.getResult().equals("success")) {
            String email = myDetailsCallback.getEmail();
            String userName = myDetailsCallback.getFullname();
            if (!email.isEmpty() && !userName.isEmpty()
                    && email != null && userName != null) {
                clientNanmeTv.setText(email);
            }
        }
    }

    @Override
    public void getIPTVCredentials(GetIPTVCredentialsCallback getIPTVCredentialsCallback) {
        if (getIPTVCredentialsCallback != null) {
            if (getIPTVCredentialsCallback != null && getIPTVCredentialsCallback.getResult().equals("success") &&
                    getIPTVCredentialsCallback.getMessage().equals("active services")) {
                String username = getIPTVCredentialsCallback.getUsername();
                String password = getIPTVCredentialsCallback.getPassword();
                if (username.equals("") || username.isEmpty()) {
                    Intent invoiceIntent = new Intent(this, ErrorCode210IPTVUserNameActivity.class);
                    startActivity(invoiceIntent);
                } else if (username != null && !username.isEmpty() && !username.equals("")
                        && password == null || password.equals("") || password.isEmpty()) {
                    Intent invoiceIntent = new Intent(this, ErrorCode211IPTVPasswordActivity.class);
                    startActivity(invoiceIntent);
                } else if (!username.equals("") && !password.equals("")
                        && !username.isEmpty() && !password.isEmpty()) {
                    clientDetailPresenter.getIPTVDetails(username, password);
                }
            } else if (getIPTVCredentialsCallback != null &&
                    getIPTVCredentialsCallback.getResult().equals("success") &&
                    getIPTVCredentialsCallback.getMessage().equals("no active iptv services found")) {

            } else if (getIPTVCredentialsCallback != null &&
                    getIPTVCredentialsCallback.getResult().equals("success") &&
                    getIPTVCredentialsCallback.getMessage().equals("no iptv services found")) {
                String totalResutl = getIPTVCredentialsCallback.getToatlResult();
                if (totalResutl.equals("0")) {
                    Intent noSubsriptionIntent = new Intent(this, NoServicePurchasedActivity.class);
                    startActivity(noSubsriptionIntent);
                }
            }
        } else if (getIPTVCredentialsCallback == null) {
            Intent invoiceIntent = new Intent(this, AuthorizationIssueActivity.class);
            startActivity(invoiceIntent);
        }
    }

    @Override
    public void getIPTVDetails(ValidationIPTVCallback validationIPTVCallback, String validateLogin) {
        if (validationIPTVCallback != null) {
            Integer auth = validationIPTVCallback.getUserLoginInfo().getAuth();
            String userStatus = validationIPTVCallback.getUserLoginInfo().getStatus();
            if (auth == 1) {

                if (userStatus.equals("Active")) {
                    String username = validationIPTVCallback.getUserLoginInfo().getUsername();
                    String password = validationIPTVCallback.getUserLoginInfo().getPassword();
                    String serverPort = validationIPTVCallback.getServerInfo().getPort();
                    String serverUrl = validationIPTVCallback.getServerInfo().getUrl();
                    String expDate = validationIPTVCallback.getUserLoginInfo().getExpDate();
                    String isTrial = validationIPTVCallback.getUserLoginInfo().getIsTrial();
                    String activeCons = validationIPTVCallback.getUserLoginInfo().getActiveCons();
                    String createdAt = validationIPTVCallback.getUserLoginInfo().getCreatedAt();
                    String maxConnections = validationIPTVCallback.getUserLoginInfo().getMaxConnections();
                    List<String> allowedFormatList = validationIPTVCallback.getUserLoginInfo().getAllowedOutputFormats();
                    String allowedFormat = null;
                    if (allowedFormatList.size() != 0) {
                        allowedFormat = allowedFormatList.get(0);
                    }

                    SharedPreferences sharedPreferencesLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferencesLogin.edit();
                    editor1.putString(AppConst.LOGIN_PREF_USERNAME_IPTV, username);
                    editor1.putString(AppConst.LOGIN_PREF_PASSWORD_IPTV, password);
//                    editor1.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, allowedFormat);
                    editor1.putString(AppConst.LOGIN_PREF_SERVER_PORT, serverPort);
                    editor1.putString(AppConst.LOGIN_PREF_SERVER_URL, serverUrl);
                    editor1.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate);
                    editor1.putString(AppConst.LOGIN_PREF_IS_TRIAL, isTrial);
                    editor1.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, activeCons);
                    editor1.putString(AppConst.LOGIN_PREF_CREATE_AT, createdAt);
                    editor1.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, maxConnections);
                    editor1.commit();
                } else if (!userStatus.equals("Active")) {
                    Intent invoiceIntent = new Intent(this, ErrorCode215IPTVServiceStatusActivity.class);
                    invoiceIntent.putExtra(AppConst.IPTV_SERVICE_STATUS, userStatus);
                    startActivity(invoiceIntent);
                }
            } else if (auth == 0) {
                Intent invoiceIntent = new Intent(this, AuthorizationIssueActivity.class);
                startActivity(invoiceIntent);
            }
        }
    }

    @Override
    public void liveStreamCategories(List<LiveStreamCategoriesCallback> liveStreamCategoriesCallback) {

    }

    @Override
    public void liveStreams(List<LiveStreamsCallback> liveStreamsCallback, ArrayList<FavouriteDBModel> allFavourites) {

    }

    @Override
    public void liveStreamsEpg(LiveStreamsEpgCallback liveStreamsEpgCallback, TextView tvActiveChannel, TextView tvNextChannel) {

    }

    @OnClick(R.id.tv_header_title)
    public void onViewClicked() {

        Intent dasIntent1 = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(dasIntent1);
    }

    public void progressBar(ProgressBar pbPagingLoader) {
        this.pbPagingLoader1 = pbPagingLoader;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        menu.clear();


/**
 * commented code used here to can be used if have use default menu
 * inflater
 */
/**
 * This code used here to put menu item in center vertical using instanceof Action menu
 * view inflating menu view using toolbar
 */


        if (isSubcaetgroyAvail) {
            toolbar.inflateMenu(R.menu.menu_search_refresh_live_vod_tvguide); //menu_search
        } else {
            toolbar.inflateMenu(R.menu.menu_search_text_icon); //
        }

        TypedValue tv = new TypedValue();
        if (this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, this.getResources().getDisplayMetrics());
        }

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            // make toggle drawable center-vertical, you can make each view alignment whatever you want
            if (toolbar.getChildAt(i) instanceof ActionMenuView) {
                Toolbar.LayoutParams lp = (Toolbar.LayoutParams) toolbar.getChildAt(i).getLayoutParams();
                lp.gravity = Gravity.CENTER_VERTICAL;
            }
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (isSubcaetgroyAvail) {
            toolbar.inflateMenu(R.menu.menu_search_refresh_live_vod_tvguide); //
            return true;
        } else {
            toolbar.inflateMenu(R.menu.menu_search_text_icon); //
            return true;
        }

//        toolbar.inflateMenu(R.menu.menu_search_text_icon);
////        toolbar.inflateMenu(R.menu.menu_reload_cat_epg);
////        toolbar.inflateMenu(R.menu.menu_layout_view);
//        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        condition_true = true;
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                return super.onOptionsItemSelected(item);
            case R.id.menu_load_channels_vod1:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                // Setting Dialog Title
                alertDialog.setTitle(context.getResources().getString(R.string.confirm_refresh));
                // Setting Dialog Message
                alertDialog.setMessage(context.getResources().getString(R.string.proceed));
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.questionmark);
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

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
                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(this);
                // Setting Dialog Title
                alertDialog1.setTitle(context.getResources().getString(R.string.confirm_refresh));
                // Setting Dialog Message
                alertDialog1.setMessage(context.getResources().getString(R.string.proceed));
                // Setting Icon to Dialog
                alertDialog1.setIcon(R.drawable.questionmark);
                // Setting Positive "Yes" Button
                alertDialog1.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

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
                if (isSubcaetgroyAvail) {
                    searchView = (SearchView) MenuItemCompat.getActionView(item);
                    searchView.setQueryHint("Search Sub Categories");
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
                            if (vodSubCatAdpaterNew != null) {
                                if (tvNoStream != null) {
                                    if (tvNoStream.getVisibility() != View.VISIBLE) {
                                        vodSubCatAdpaterNew.filter(newText, tvNoRecordFound);
                                    }
                                }
                            }
                            return false;
                        }
                    });

                    return true;
                } else {
                    searchView = (SearchView) MenuItemCompat.getActionView(item);
                    searchView.setQueryHint(getResources().getString(R.string.search_vod));
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
                            if (vodAdapter != null) {
                                if (tvNoStream != null) {
                                    if (tvNoStream.getVisibility() != View.VISIBLE) {
                                        vodAdapter.filter(newText, tvNoRecordFound);
                                    }
                                }
                            }
                            return false;
                        }
                    });

                    return true;
                }

            case R.id.menu_load_channels_vod:
                AlertDialog.Builder alertDialogSub = new AlertDialog.Builder(this);
                // Setting Dialog Title
                alertDialogSub.setTitle(context.getResources().getString(R.string.confirm_refresh));
                // Setting Dialog Message
                alertDialogSub.setMessage(context.getResources().getString(R.string.proceed));
                // Setting Icon to Dialog
                alertDialogSub.setIcon(R.drawable.questionmark);
                // Setting Positive "Yes" Button
                alertDialogSub.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        // Write your code here to invoke YES event
                        loadChannelsAndVod();
                    }
                });

                // Setting Negative "NO" Button
                alertDialogSub.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialogSub.show();
                break;
            case R.id.menu_load_tv_guide:
                //            Utils.showAlertBox(context, "");
                AlertDialog.Builder alertDialogSub1 = new AlertDialog.Builder(this);
                // Setting Dialog Title
                alertDialogSub1.setTitle(context.getResources().getString(R.string.confirm_refresh));
                // Setting Dialog Message
                alertDialogSub1.setMessage(context.getResources().getString(R.string.proceed));
                // Setting Icon to Dialog
                alertDialogSub1.setIcon(R.drawable.questionmark);
                // Setting Positive "Yes" Button
                alertDialogSub1.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        // Write your code here to invoke YES event
                        loadTvGuid();
                    }
                });

                // Setting Negative "NO" Button
                alertDialogSub1.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialogSub1.show();
            case R.id.layout_view_grid:
                if (getActiveLiveStreamCategoryId.equals("0")) {
                    editor.putInt(AppConst.VOD, 0);
                    editor.commit();
                    initialize();
                    getAllMovies();
                } else if (getActiveLiveStreamCategoryId.equals("-1")) {
                    editor.putInt(AppConst.VOD, 0);
                    editor.commit();
                    initialize();
                } else {
                    subCategoryListFinal_menu.clear();
                    subCategoryListFinal_menu = liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(getActiveLiveStreamCategoryId);
                    if (subCategoryListFinal_menu.size() > 0) {
                    } else {
                        editor.putInt(AppConst.VOD, 0);
                        editor.commit();
                        initialize();
                    }
                }
                break;
            case R.id.layout_view_linear:
                if (getActiveLiveStreamCategoryId.equals("0")) {
                    editor.putInt(AppConst.VOD, 1);
                    editor.commit();
                    getAllMovies();
                    initialize1();
                } else if (getActiveLiveStreamCategoryId.equals("-1")) {
                    editor.putInt(AppConst.VOD, 1);
                    editor.commit();
                    initialize1();
                } else {
                    subCategoryListFinal_menu = liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(getActiveLiveStreamCategoryId);
                    if (subCategoryListFinal_menu.size() > 0) {
                    } else {
                        editor.putInt(AppConst.VOD, 1);
                        editor.commit();
                        initialize1();
                    }
                }
                break;
            case R.id.menu_sort:
                showSortPopup(this);
        }
        return false;
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
//        if (liveStreamDBHandler != null &&
//                databaseUpdatedStatusDBModelLive != null) {
//            databaseUpdatedStatusDBModelLive =
//                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, AppConst.DB_CHANNELS_ID);
//            if (databaseUpdatedStatusDBModelLive != null) {
//                if (databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState() == null) {
//                    return true;
//                } else {
//                    return databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH)
//                            || databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED);
//                }
//            }
//        }
        return true;
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
//        if (liveStreamDBHandler != null &&
//                databaseUpdatedStatusDBModelEPG != null
//                ) {
//            databaseUpdatedStatusDBModelEPG =
//                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, AppConst.DB_EPG_ID);
//            if (databaseUpdatedStatusDBModelEPG != null) {
//                if(databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null ||
//                        databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH)
//                        || databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED)) {
//                    return true;
//                }
//                else {
//                    return (databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null ||
//                            databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(""));
//                }
//            }
//        }
        return true;
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
                } else if (selectedPlayer1.getText().toString().equals(getResources().getString(R.string.sort_ztoa))) {
                    SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "3");
                    SharedPreferencesSortEditor.commit();
                } else {
                    SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "0");
                    SharedPreferencesSortEditor.commit();
                }
                pref = getSharedPreferences(AppConst.LIST_GRID_VIEW, MODE_PRIVATE);
                editor = pref.edit();
                AppConst.LIVE_FLAG_VOD = pref.getInt(AppConst.VOD, 0);
                if (AppConst.LIVE_FLAG_VOD == 1) {
                    initialize1();
                } else {
                    initialize();
                }
                changeSortPopUp.dismiss();
            }


        });

    }


    public void getAllMovies() {
        atStart();
        pref = getSharedPreferences(AppConst.LIST_GRID_VIEW, MODE_PRIVATE);
        editor = pref.edit();
        AppConst.LIVE_FLAG_VOD = pref.getInt(AppConst.VOD, 0);
        if (AppConst.LIVE_FLAG_VOD == 1) {
            context = this;
            liveStreamDBHandler = new LiveStreamDBHandler(context);
            if (myRecyclerView != null && context != null) {
                myRecyclerView.setHasFixedSize(true);
                // int noofCloumns = Utils.getNumberOfColumns(context);
                layoutManager = new LinearLayoutManager(context);
                myRecyclerView.setLayoutManager(layoutManager);
                myRecyclerView.setItemAnimator(new DefaultItemAnimator());

            }
        } else {
            context = this;
            liveStreamDBHandler = new LiveStreamDBHandler(context);
            if (myRecyclerView != null && context != null) {
                myRecyclerView.setHasFixedSize(true);
                int noofCloumns = Utils.getNumberOfColumns(context);
                layoutManager = new GridLayoutManager(context, noofCloumns + 1);
                myRecyclerView.setLayoutManager(layoutManager);
                myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        }


        if (context != null) {
            LiveStreamDBHandler liveStreamDBHandler = new LiveStreamDBHandler(context);
            ArrayList<LiveStreamsDBModel> channelAvailable = liveStreamDBHandler.getAllLiveStreasWithCategoryId("0", "movie");
            onFinish();
            if (channelAvailable != null && myRecyclerView != null && channelAvailable.size() != 0) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                vodAdapter = new VodAdapter(channelAvailable, context);
                myRecyclerView.setAdapter(vodAdapter);
            } else {
                if (progressDialog != null)
                    progressDialog.dismiss();
                if (tvNoStream != null) {
                    tvNoStream.setVisibility(View.VISIBLE);
                }
            }
        }
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}

