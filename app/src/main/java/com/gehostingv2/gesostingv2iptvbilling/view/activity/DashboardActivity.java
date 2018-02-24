package com.gehostingv2.gesostingv2iptvbilling.view.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessaging;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.loadlivemovieepgdata.MultiDexApplication;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetIPTVCredentialsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidateCustomLoginCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;

import com.gehostingv2.gesostingv2iptvbilling.presenter.NotificationPresenter;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ValidateCustomLoginPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.PaymentSuccessfulFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.TicketsHomeFragment;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.database.ExpandedMenuModel;

import com.gehostingv2.gesostingv2iptvbilling.model.callback.MyDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ClientDetailPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.ExpandndableListAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.ActiveServicesFragment;


import com.gehostingv2.gesostingv2iptvbilling.view.fragment.CancelledServicesFragment;

import com.gehostingv2.gesostingv2iptvbilling.view.fragment.HomeFragment;

import com.gehostingv2.gesostingv2iptvbilling.view.fragment.InvoicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.InvoicesPaidFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.InvoicesUnpaidFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.MakePaymentFragment;

import com.gehostingv2.gesostingv2iptvbilling.view.fragment.OpenTicketFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.OpenTicketGeneralEnquiriesDepartmentFragment;

import com.gehostingv2.gesostingv2iptvbilling.view.fragment.PendingServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.ServicesFragment;

import com.gehostingv2.gesostingv2iptvbilling.view.fragment.SubscriptionProductFragment;

import com.gehostingv2.gesostingv2iptvbilling.view.fragment.SuspendedServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.TerminatedServicesFragment;
import com.gehostingv2.gesostingv2iptvbilling.view.fragment.TicketsFragment;

import com.gehostingv2.gesostingv2iptvbilling.view.fragment.ViewTicketsRequestFragment;

import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ClientDetailInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.NotificationInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.WHMCSDetailsInterface;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst.CURRENT_TAG;

import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst.TAG_HOME;

import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst.TAG_MY_DOMAINS;


public class DashboardActivity extends AppCompatActivity implements ClientDetailInterface,
        HomeFragment.OnFragmentInteractionListener, View.OnClickListener,
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
        NavigationView.OnNavigationItemSelectedListener,
        MakePaymentFragment.OnFragmentInteractionListener,
        PaymentSuccessfulFragment.OnFragmentInteractionListener,
        TicketsHomeFragment.OnFragmentInteractionListener,
        WHMCSDetailsInterface, NotificationInterface

{
    @BindView(R.id.tv_header_title)
    ImageView tvHeaderTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.navigationmenu)
    ExpandableListView navigationmenu;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_dashboard)
    RelativeLayout activityDashboard;
    @BindView(R.id.navigation_drawer_bottom)
    NavigationView navigationDrawerBottom;

    private int clientId;
    private String clientEmail;
    private ClientDetailPresenter clientDetailPresenter;
    private TextView clientNanmeTv;
    private ExpandndableListAdapter mMenuAdapter;
    private List<ExpandedMenuModel> listDataHeader;
    private HashMap<ExpandedMenuModel, List<String>> listDataChild;
    private String selectedItem = TAG_HOME;
    private Handler mHandler;
    private Context context;
    private ImageView logoHomeIV;
    private boolean condition_true = false;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TypedValue tv;
    private int actionBarHeight;
    private SharedPreferences loginPreferencesAfterLogin;
    private ValidateCustomLoginPresenter validateCustomLoginPresenter;
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private NotificationPresenter notificationPresenter;

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        SharedPreferences pref1 = getSharedPreferences("flags", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref1.edit();
        editor.putInt("Flag1", 0);
        editor.putInt("Flag2", 0);


        context = this;

        //for api level 23 and above using wakefulbroadcast receiver
//        autoRelaod(context);

        // for api level 19
//        liveMovieEPGAutoUpadateApi19(context);

        checkLoginCredentials();
        clientDetailPresenter = new ClientDetailPresenter(this, context);
        validateCustomLoginPresenter = new ValidateCustomLoginPresenter(this);
        notificationPresenter = new NotificationPresenter(this);
        notifiaction();
        mHandler = new Handler();
        if (navView != null) {
            setupDrawerContent(navView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                setupDrawerContent(navView);
            }
        }
        prepareListData();



        mMenuAdapter = new ExpandndableListAdapter(context, listDataHeader, listDataChild, navigationmenu);
        // setting list adapter
        navigationmenu.setAdapter(mMenuAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        setToggleIconPosition();
        changeStatusBarColor();
        toolbarSet();
        headerView();
        SharedPreferences pref = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        clientId = pref.getInt(AppConst.CLIENT_ID, -1);
        clientEmail = pref.getString(AppConst.PREF_EMAIL_WHMCS, "");
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
        } else if (!username.isEmpty() && !username.equals("") &&
                !password.isEmpty() && !password.equals("")) {
            clientNanmeTv.setText(username);
            validateCustomLoginPresenter.getWHMCSClientDetials(username, password);
        }

    }



    private void liveMovieEPGAutoUpadateApi19(Context context) {
        // Retrieve a PendingIntent that will perform a broadcast
//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//        startAlarm();
    }

    public void startAlarm() {
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long currentTime = System.currentTimeMillis();
        long oneMinute = 60 * 1000;
        long oneHour = oneMinute * 60;
        long oneday = oneHour * 12;
        long onedayNight = oneday * 2;
        long timeInterval = currentTime + onedayNight;
        int interval = (int) (oneMinute * 5);
        long intervalTime = (oneMinute * 20);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervalTime, pendingIntent);
        Toast.makeText(this, "AUto Update Set", Toast.LENGTH_SHORT).show();
    }


    private void autoRelaod(final Context context) {
        MultiDexApplication multiDexApplication = new MultiDexApplication();
        String applicationStaus = "";
//        Foreground foreground = new Foreground();
//        foreground.init((Application) getApplicationContext());
        startDataLoadThread();


    }

    public void startDataLoadThread() {
        long currentTime = System.currentTimeMillis();
        long oneMinute = 60 * 1000;
        long time = currentTime + oneMinute;
//        AutoLoadService autoLoadService = new AutoLoadService(context);
//        NotificationEventReceiver.setupAlarm(getApplicationContext());
    }


    private void notifiaitonCall(int clientId, String regId) {
        if (regId != null && !regId.isEmpty() && clientId != -1 && clientId != 0)
            notificationPresenter.sendNotification(regId, clientId);
    }

    private void notifiaction() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(AppConst.Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(AppConst.Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(AppConst.Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
//                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConst.Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);
        SharedPreferences prefClientId = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        clientId = prefClientId.getInt(AppConst.CLIENT_ID, -1);
        notifiaitonCall(clientId, regId);
        if (!TextUtils.isEmpty(regId)) ;
//            txtRegId.setText("Firebase Reg Id: " + regId);
        else ;
//            txtRegId.setText("Firebase Reg Id is not received yet!");
    }


    private void setToggleIconPosition() {
        tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            // make toggle drawable center-vertical, you can make each view alignment whatever you want
            if (toolbar.getChildAt(i) instanceof ImageButton) {
                Toolbar.LayoutParams lp = (Toolbar.LayoutParams) toolbar.getChildAt(i).getLayoutParams();
                lp.gravity = Gravity.CENTER_VERTICAL;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Fragment newFragment = new PaymentSuccessfulFragment();
            DashboardActivity activity = this;
            FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();
            t.replace(R.id.frame, newFragment, AppConst.TAG_PAYMENT_SUCCESS);
            t.addToBackStack(AppConst.TAG_PAYMENT_SUCCESS
            );
            t.commit();
        }
    }

    private void toolbarSet() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        logoHomeIV = (ImageView) toolbar.findViewById(R.id.tv_header_title);
        if (logoHomeIV != null)
            logoHomeIV.setImageResource(R.drawable.logo_home_new);
    }

    private void headerView() {
        View header = navView.getHeaderView(0);
        ImageView closeDrawerIV = (ImageView) header.findViewById(R.id.iv_close_drawer);
        clientNanmeTv = (TextView) header.findViewById(R.id.tv_client_name);
        closeDrawerIV.setOnClickListener(this);
        View footer = navigationDrawerBottom.getHeaderView(0);
        TextView logoutUserTV = (TextView) footer.findViewById(R.id.tv_logout);
        logoutUserTV.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        condition_true = true;
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                return super.onOptionsItemSelected(item);
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;

        }
        return false;
    }


    private Fragment getHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupDrawerContent(NavigationView navigationView) {
        Intent intent = getIntent();
        if (intent != null) {
            String service = intent.getStringExtra("service");
            if (service != null && !service.isEmpty() && service.equals("service")) {
//                selectedItem = TAG_MY_SERVICES;
                Fragment mFragment = null;
                mFragment = new ServicesFragment();
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                fragmentManager1.beginTransaction()
                        .replace(R.id.frame, mFragment).commit();
            }
        }


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

    private void checkLoginCredentials() {
        SharedPreferences pref = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        int clientId = pref.getInt(AppConst.CLIENT_ID, -1);
        SharedPreferences prefUsername = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        String username = prefUsername.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
        if (clientId != -1 && clientId != 0) {
        } else if (!username.equals("") || username != null && !username.isEmpty()) {
        } else {
            Intent intentLogin = new Intent(this, LoginWelcomeActivity.class);
            startActivity(intentLogin);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        context = this;
        checkLoginCredentials();

        clientDetailPresenter = new ClientDetailPresenter(this, context);
        mHandler = new Handler();
        prepareListData();
//        listDataHeader = Utils.drawerMenuHeaderList(context);
//        listDataChild = Utils.drawerMenuChildList(context);
        mMenuAdapter = new ExpandndableListAdapter(context, listDataHeader, listDataChild, navigationmenu);
        // setting list adapter
        navigationmenu.setAdapter(mMenuAdapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        setToggleIconPosition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeStatusBarColor();
        }
        toolbarSet();
        headerView();
        if (clientDetailPresenter != null) {
            loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String userName = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            if (!userName.isEmpty() && !userName.equals("") &&
                    !password.isEmpty() && !password.equals(""))
                clientDetailPresenter.getIPTVDetails(userName, password);
        }


    }

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

    private void selectedFragment(String selectedItem) {
        toolbarSet();
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            String name = fragmentManager.getBackStackEntryAt(0).getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();

        Utils.startActivity(context,selectedItem);

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
//            if (context != null)
//                Utils.set_layout_live(context);
////            Intent liveStreamActivity = new Intent(this, LiveTVTabViewActivity.class);
////            startActivity(liveStreamActivity);
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_live_tv_guide))) {
//            Intent liveStreamTVGuideActivity = new Intent(this, LiveStreamEpgAcitivity.class);
//            startActivity(liveStreamTVGuideActivity);
//            finish();
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_account_info))) {
//            Intent accountInfoIntent = new Intent(this, AccountInfoActivity.class);
//            startActivity(accountInfoIntent);
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_vod))) {
//            Intent intent = new Intent(this, VodTabViewActivity.class);
//            startActivity(intent);
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_tv_archieve))) {
//            Intent intent = new Intent(this, TVArchiveActivity.class);
//            startActivity(intent);
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_settings))) {
//            Intent settingsIntent = new Intent(this, SettingssActivity.class);
//            startActivity(settingsIntent);
//        } else if (selectedItem.equals(getResources().getString(R.string.drawer_logout))) {
//            SharedPreferences loginPreferences;
//            SharedPreferences.Editor loginPreferencesEditor;
//            loginPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
//            loginPreferencesEditor = loginPreferences.edit();
//            loginPreferencesEditor.clear();
//            loginPreferencesEditor.commit();
//
//            Toast.makeText(this, context.getResources().getString(R.string.logout_successfully), Toast.LENGTH_SHORT).show();
//            Intent intentLogout = new Intent(this, LoginWelcomeActivity.class);
//            SharedPreferences loginPreferencesClientid;
//            SharedPreferences.Editor loginPreferencesClientidEditor;
//            loginPreferencesClientid = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
//            loginPreferencesClientidEditor = loginPreferencesClientid.edit();
//            loginPreferencesClientidEditor.clear();
//            loginPreferencesClientidEditor.commit();
//            startActivity(intentLogout);
//            finish();
//        }
    }

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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        } else if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            moveTaskToBack(true);
//            finish();
//            homeFragmentToLoad();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
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

    @Override
    public void getClientDetails(MyDetailsCallback myDetailsCallback) {
        if (myDetailsCallback != null && myDetailsCallback.getResult().equals("success")) {
            String email = myDetailsCallback.getEmail();
            int currency = myDetailsCallback.getCurrency();
            SharedPreferences loginPreferencesEmail;
            SharedPreferences.Editor loginPreferencesClientidEditor;
            loginPreferencesEmail = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
            loginPreferencesClientidEditor = loginPreferencesEmail.edit();
            loginPreferencesClientidEditor.putString(AppConst.PREF_EMAIL_WHMCS, email);
            loginPreferencesClientidEditor.putInt(AppConst.PREF_CURRENCIES_WHMCS, currency);
            loginPreferencesClientidEditor.commit();
            if (!email.isEmpty() && email != null) {
                clientNanmeTv.setText(email);
            }
        } else if (myDetailsCallback == null) {
            if (context != null)
                Utils.showToast(context, AppConst.SOMETHING_WRONG);
        }
    }


    @Override
    public void getIPTVCredentials(GetIPTVCredentialsCallback getIPTVCredentialsCallback) {
        if (getIPTVCredentialsCallback != null &&
                getIPTVCredentialsCallback.getResult().equals("success") &&
                getIPTVCredentialsCallback.getMessage().equals("active services")) {
            String username = getIPTVCredentialsCallback.getUsername();
            String password = getIPTVCredentialsCallback.getPassword();
            if (username.equals("") || password.equals("") ||
                    username.isEmpty() || password.isEmpty()) {
            } else if (!username.equals("") && !password.equals("")) {
                clientDetailPresenter.getIPTVDetails(username, password);
            }
        } else if (getIPTVCredentialsCallback != null &&
                getIPTVCredentialsCallback.getResult().equals("success") &&
                getIPTVCredentialsCallback.getMessage().equals("no active iptv services found")) {

        } else if (getIPTVCredentialsCallback != null &&
                getIPTVCredentialsCallback.getResult().equals("success") &&
                getIPTVCredentialsCallback.getMessage().equals("no iptv services found")) {

        } else if (getIPTVCredentialsCallback == null) {
        }

    }

    @Override
    public void getIPTVDetails(ValidationIPTVCallback validationIPTVCallback, String validateLogin) {
        if (validationIPTVCallback != null) {
            Integer auth = validationIPTVCallback.getUserLoginInfo().getAuth();
            if (auth == 1) {
                String userStatus = validationIPTVCallback.getUserLoginInfo().getStatus();
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

                }
            }
        }
    }


    @Override
    public void atStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailed(String errorMessage) {
        if (context != null &&
                errorMessage != null &&
                !errorMessage.isEmpty() &&
                errorMessage.contains(AppConst.WHMCS_URL_ERROR) ||
                errorMessage.contains("Error code 213 occurs")) {
            Utils.showToast(context, AppConst.WHMCS_URL_ERROR_MESSAGE);
        } else if (context != null &&
                errorMessage != null &&
                !errorMessage.isEmpty()) {
            Utils.showToast(context, getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void validateCustomLogin(ValidateCustomLoginCallback validateCustomLoginCallback) {
        if (validateCustomLoginCallback != null && validateCustomLoginCallback.getResult().equals("success")) {
            String email = validateCustomLoginCallback.getData().getEmail();
            int clientId = validateCustomLoginCallback.getData().getClientid();
            SharedPreferences loginPreferencesEmail;
            SharedPreferences.Editor loginPreferencesClientidEditor;
            loginPreferencesEmail = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
            loginPreferencesClientidEditor = loginPreferencesEmail.edit();
            loginPreferencesClientidEditor.putString(AppConst.PREF_EMAIL_WHMCS, email);
            loginPreferencesClientidEditor.putInt(AppConst.PREF_CLIENT_ID, clientId);
            loginPreferencesClientidEditor.commit();
            if (!email.isEmpty() && email != null) {
                clientNanmeTv.setText(email);
            }
        }
    }

    @Override
    public void sendNotification(CommonResponseCallback commonResponseCallback) {

    }
}
