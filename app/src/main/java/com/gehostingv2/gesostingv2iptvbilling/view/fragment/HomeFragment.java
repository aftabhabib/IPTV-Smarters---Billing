package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetSITCountCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidateCustomLoginCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.XMLTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.XtreamPanelAPICallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.DatabaseUpdatedStatusDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamCategoryIdDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamsDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelAvailableChannelsPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelLivePojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelMoviePojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.HomePresenter;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ValidateCustomLoginPresenter;
import com.gehostingv2.gesostingv2iptvbilling.presenter.XMLTVPresenter;
import com.gehostingv2.gesostingv2iptvbilling.presenter.XtreamPanelAPIPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.AccountInfoActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ImportEPGActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ImportStreamsActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.InvoicesActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LiveStreamEpgAcitivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWHMCSActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ServicesActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.SettingssActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.TVArchiveActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.TicketsActivity;

import com.gehostingv2.gesostingv2iptvbilling.view.adapter.PromoAddAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.HomeInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.WHMCSDetailsInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.XMLTVInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.XtreamPanelAPIInterface;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HomeInterface,
        WHMCSDetailsInterface, XMLTVInterface,
        XtreamPanelAPIInterface {
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.iv_services_icon)
    ImageView ivServicesIcon;
    @BindView(R.id.tv_services)
    TextView tvServices;
    @BindView(R.id.iv_service_count)
    ImageView ivServiceCount;
    @BindView(R.id.tv_service_count)
    TextView tvServiceCount;
    @BindView(R.id.iv_service_forward_arrow)
    ImageView ivServiceForwardArrow;
    @BindView(R.id.detail)
    RelativeLayout detail;
    @BindView(R.id.rl_services)
    RelativeLayout rlServices;
    @BindView(R.id.iv_domain_icon)
    ImageView ivDomainIcon;
    @BindView(R.id.tv_domain)
    TextView tvDomain;
    @BindView(R.id.iv_domain_count)
    ImageView ivDomainCount;
    @BindView(R.id.iv_domain_forward_arrow)
    ImageView ivDomainForwardArrow;
    @BindView(R.id.tv_domain_count)
    TextView tvDomainCount;
    @BindView(R.id.detail_domain)
    RelativeLayout detailDomain;
    @BindView(R.id.rl_domains)
    RelativeLayout rlDomains;
    @BindView(R.id.iv_invoices_icon)
    ImageView ivInvoicesIcon;
    @BindView(R.id.tv_invoices)
    TextView tvInvoices;
    @BindView(R.id.iv_invoice_count)
    ImageView ivInvoiceCount;
    @BindView(R.id.tv_invoice_count)
    TextView tvInvoiceCount;
    @BindView(R.id.iv_invoice_forward_arrow)
    ImageView ivInvoiceForwardArrow;
    @BindView(R.id.detail_invoices)
    RelativeLayout detailInvoices;
    @BindView(R.id.rl_invoices)
    RelativeLayout rlInvoices;
    @BindView(R.id.iv_ticket_icon)
    ImageView ivTicketIcon;
    @BindView(R.id.tv_ticket)
    TextView tvTicket;
    @BindView(R.id.iv_ticket_count)
    ImageView ivTicketCount;
    @BindView(R.id.tv_ticket_count)
    TextView tvTicketCount;
    @BindView(R.id.iv_ticket_forward_arrow)
    ImageView ivTicketForwardArrow;
    @BindView(R.id.detail_tickets)
    RelativeLayout detailTickets;
    @BindView(R.id.rl_tickets)
    RelativeLayout rlTickets;
    @BindView(R.id.fl_home_fragment)
    FrameLayout flHomeFragment;
    Unbinder unbinder;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.iv_forward_arrow)
    ImageView ivForwardArrow;
    @BindView(R.id.rl_home)
    RelativeLayout rlHome;

    @BindView(R.id.live_tv)
    RelativeLayout rlLiveTV;


    @BindView(R.id.txt_push_message)
    TextView PushMessageTV;
    @BindView(R.id.txt_reg_id)
    TextView regIdTV;
    private static final String TAG = DashboardActivity.class.getSimpleName();

    @BindView(R.id.sv_home)
    ScrollView svHome;
    @BindView(R.id.tv_importing_streams)
    TextView tvImportingStreams;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_percentage)
    TextView tvPercentage;
    @BindView(R.id.tv_countings)
    TextView tvCountings;
    @BindView(R.id.rl_import_process)
    RelativeLayout rlImportProcess;

    @BindView(R.id.tv_live_count)
    TextView tvLiveCount;
    @BindView(R.id.tv_epg_count)
    TextView tvEPGCount;
    @BindView(R.id.tv_vod_count)
    TextView tvVodCount;



    @BindView(R.id.pb_loader_service)
    AVLoadingIndicatorView serviceCountLoader;
    @BindView(R.id.pb_loader_domain)
    AVLoadingIndicatorView domainCountLoader;
    @BindView(R.id.pb_loader_invoice)
    AVLoadingIndicatorView invoiceCountLoader;
    @BindView(R.id.pb_loader_ticket)
    AVLoadingIndicatorView ticketCountLoader;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private HomePresenter homePresenter;
    private Context context;
    private PromoAddAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Integer> myImageList;
    private SharedPreferences loginPreferences;
    private ValidateCustomLoginPresenter validateCustomLoginPresenter;
    private XMLTVPresenter xmlTvPresenter;
    private XtreamPanelAPIPresenter xtreamPanelAPIPresenter;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences.Editor loginPrefsEditor;
    DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive =
            new DatabaseUpdatedStatusDBModel();
    DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG =
            new DatabaseUpdatedStatusDBModel();
    String currentDate ="";
    String userName="";
    String userPassword ="";


    LinearLayoutManager layoutManager;
    Toolbar toolbar;
    ImageView logoHomeIV;
    String domainCount = "";
    String invoiceCount = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fabric.with(getContext(), new Crashlytics());
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        logoHomeIV = (ImageView) toolbar.findViewById(R.id.tv_header_title);
        if (logoHomeIV != null)
            logoHomeIV.setImageResource(R.drawable.logo_home_new);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        myRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myRecyclerView.setLayoutManager(layoutManager);
        initialize();
        return view;
    }

    private void initialize() {
        context = getContext();
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        homePresenter = new HomePresenter(this);
        xmlTvPresenter = new XMLTVPresenter(this, context);
        xtreamPanelAPIPresenter = new XtreamPanelAPIPresenter(this, context);
        validateCustomLoginPresenter = new ValidateCustomLoginPresenter(this);

        updateTotalCount(context);
        launchTVGuideFromDifferentActivity();
        updateChannelsandEPG(context);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
        int clientId = preferences.getInt("clientid", -1);
        SharedPreferences prefUsername = this.getActivity().getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        String username = prefUsername.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
        String password = prefUsername.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
        myImageList = new ArrayList<>();
        myImageList.add(R.drawable.banner1);
        myImageList.add(R.drawable.banner2);
        myImageList.add(R.drawable.banner3);
        mAdapter = new PromoAddAdapter(myImageList, getContext());
        myRecyclerView.setAdapter(mAdapter);
        setHasOptionsMenu(true);
        setMenu();
        setMenuVisibility(true);
        if (clientId == 0 || clientId == -1) {
            ivServiceCount.setVisibility(View.GONE);
            ivInvoiceCount.setVisibility(View.GONE);
            ivTicketCount.setVisibility(View.GONE);
            ValidateCustomLoginPresenter.getWHMCSClientDetials(username, password);
        } else {
            updateDashboardData();
        }
    }
    private void updateTotalCount(Context context) {
        if (context != null) {
            liveStreamDBHandler = new LiveStreamDBHandler(context);
            if (liveStreamDBHandler.getStreamsCount("live") > 0 && tvLiveCount!=null){
                tvLiveCount.append(" : "+liveStreamDBHandler.getStreamsCount("live"));
                tvLiveCount.setVisibility(View.VISIBLE);
            }

            if (liveStreamDBHandler.getStreamsCount("movie") > 0 && tvVodCount!=null){
                tvVodCount.append(" : "+liveStreamDBHandler.getStreamsCount("movie"));
                tvVodCount.setVisibility(View.VISIBLE);
            }

            if (liveStreamDBHandler.getEPGCount() > 0 && tvEPGCount!=null){
                tvEPGCount.append(" : "+liveStreamDBHandler.getEPGCount());
                tvEPGCount.setVisibility(View.VISIBLE);
            }
        }
    }


    private void launchTVGuideFromDifferentActivity() {
        Intent intentOtherActiviy = getActivity().getIntent();
        String launchTvGuid = "";
        if(intentOtherActiviy!=null) {
            launchTvGuid = intentOtherActiviy.getStringExtra(AppConst.LAUNCH_TV_GUIDE);
            if(launchTvGuid != null && !launchTvGuid.equals("") &&
                    launchTvGuid.equals(AppConst.LAUNCH_TV_GUIDE)) {
                launchTvGuide();
            }
        }
    }


    private void launchTvGuide() {
        currentDate = currentDateValue();
        startXMLTV(getUserName(),getUserPassword(), currentDate);
    }

    public void startXMLTV(String userName, String userPassword, String currentDate){
        String status = "";
        String lastUpdatedStatusdate = "";
        int epgCount = liveStreamDBHandler.getEPGCount();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        databaseUpdatedStatusDBModelEPG = liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, AppConst.DB_EPG_ID);
        if (databaseUpdatedStatusDBModelEPG != null) {
            status = databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState();
            lastUpdatedStatusdate = databaseUpdatedStatusDBModelEPG.getDbLastUpdatedDate();
        }
        long dateDifference = getDateDiff(simpleDateFormat, lastUpdatedStatusdate, currentDate);
        if (epgCount == 0) {
            startImportTvGuideActivity();
        }
        else if(dateDifference>=0 && dateDifference < 2){
            startTvGuideActivity();
        }
        else if (dateDifference >= 2) {
            executeXMLTV(userName, userPassword,status, currentDate);
        }
    }

    private void executeXMLTV(String userName, String userPassword, String status, String currentDate) {
        if (!userName.equals("") &&
                !userPassword.equals("") &&
                status != null &&
                !status.equals("") &&
                !status.isEmpty() &&
                status.equals(AppConst.DB_UPDATED_STATUS_FINISH) ||
                !userName.equals("") &&
                        !userPassword.equals("") &&
                        status != null &&
                        !status.equals("") &&
                        !status.isEmpty() &&
                        status.equals(AppConst.DB_UPDATED_STATUS_FAILED)) {
            liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG,
                    AppConst.DB_EPG_ID, AppConst.DB_UPDATED_STATUS_PROCESSING, currentDate);
            if (rlImportProcess != null) {
                rlImportProcess.setVisibility(View.VISIBLE);
            }
            xmlTvPresenter.epgXMLTV(userName, userPassword);
        }
    }

    public void startTvGuideActivity() {
        if(context !=null) {
            Intent epgIntent = new Intent(context, LiveStreamEpgAcitivity.class);
            startActivity(epgIntent);
            getActivity().finish();
        }
    }
    public void startImportTvGuideActivity() {
        if(context !=null) {
            Intent epgIntent = new Intent(context, ImportEPGActivity.class);
            startActivity(epgIntent);
            getActivity().finish();
        }
    }

    public String getUserName() {
        if (context != null) {
            loginPreferencesAfterLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            return username;
        }
        return userName;
    }

    public String getUserPassword() {
        if (context != null) {
            loginPreferencesAfterLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String password = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            return password;
        }
        return userPassword;
    }

    public void updateChannelsandEPG(Context context) {
        currentDate = currentDateValue();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (context != null && liveStreamDBHandler != null && rlImportProcess != null) {
            rlImportProcess.setVisibility(View.GONE);
            loginPreferencesAfterLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
            String username = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "");
            String password = loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "");
            int count = liveStreamDBHandler.getDBStatusCount();
            if (count == 0) {
            } else {
                if (liveStreamDBHandler != null &&
                        rlImportProcess != null &&
                        xtreamPanelAPIPresenter != null &&
                        xmlTvPresenter != null) {
                    String lastUpdatedStatusdate = "";
                    String status = "";
                    databaseUpdatedStatusDBModelLive = liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, AppConst.DB_CHANNELS_ID);
                    if (databaseUpdatedStatusDBModelLive != null) {
                        status = databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState();
                        lastUpdatedStatusdate = databaseUpdatedStatusDBModelLive.getDbLastUpdatedDate();
                    }
                    long diffChannelsDate = getDateDiff(simpleDateFormat, lastUpdatedStatusdate, currentDate);
                    if (diffChannelsDate >= 1) {
                        rlImportProcess.setVisibility(View.VISIBLE);
                        if (!username.equals("") &&
                                !password.equals("") &&
                                !status.equals("") &&
                                !status.isEmpty() &&
                                status.equals(AppConst.DB_UPDATED_STATUS_FINISH) ||

                                !username.equals("") &&
                                        !password.equals("") &&
                                        status != null &&
                                        !status.equals("") &&
                                        !status.isEmpty() &&
                                        status.equals(AppConst.DB_UPDATED_STATUS_FAILED)) {
                            liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS,
                                    AppConst.DB_CHANNELS_ID, AppConst.DB_UPDATED_STATUS_PROCESSING, currentDate);
                            xtreamPanelAPIPresenter.panelAPI(username, password);
                        }
                    }
                }
            }
        }
    }


    private String currentDateValue() {
        Date currentTime = Calendar.getInstance().getTime();
        String dateValue = currentTime.toString();
        String currentDate = Utils.parseDateToddMMyyyy(dateValue);
//        String currentDate = "21/12/2017";
        return currentDate;
    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    private void setMenu() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        logoHomeIV = (ImageView) toolbar.findViewById(R.id.tv_header_title);
        if (logoHomeIV != null)
            logoHomeIV.setImageResource(R.drawable.logo_home_new);
        mListener = null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void getServices(CommonResponseCallback commonResponseCallback) {

    }

    int pixelDpConversion() {
        int dpValue = 36; // margin in dips
        float d = context.getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d); // margin in pixels
        return margin;
    }

    @Override
    public void getClientDomains(CommonResponseCallback commonResponseCallback) {
    }

    @Override
    public void getInvoices(CommonResponseCallback commonResponseCallback) {
    }

    @Override
    public void getTickets(CommonResponseCallback commonResponseCallback) {

    }

    @Override
    public void sendNotification(CommonResponseCallback commonResponseCallback) {

    }

    @Override
    public void getSitCount(GetSITCountCallback getSITCountCallback) {
        if (getSITCountCallback.getResult().equals("success")) {
            if (getSITCountCallback.getSitCount().getServicescount() != null &&
                    getSITCountCallback.getSitCount().getInvoicescount() != null &&
                    getSITCountCallback.getSitCount().getTicketscount() != null) {
                int serviceCount = getSITCountCallback.getSitCount().getServicescount().getActive();
                int invoiceCount = getSITCountCallback.getSitCount().getInvoicescount().getUnpaid();
                String ticketCount = getSITCountCallback.getSitCount().getTicketscount().getTotalresults();
                if (tvServiceCount != null) {
                    serviceCountLoader.hide();
                    tvServiceCount.setText(String.valueOf(serviceCount));
                    if (serviceCount > 9) {
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tvServiceCount
                                .getLayoutParams();
                        mlp.setMargins(0, 0, pixelDpConversion(), 0); //106 for this emulator screen,70
                    }
                }
                if (tvInvoiceCount != null) {
                    invoiceCountLoader.hide();
                    tvInvoiceCount.setText(String.valueOf(invoiceCount));
                    if (invoiceCount > 9) {
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tvInvoiceCount
                                .getLayoutParams();
                        mlp.setMargins(0, 0, pixelDpConversion(), 0);
                    }
                }
                if (tvTicketCount != null) {
                    ticketCountLoader.hide();
                    tvTicketCount.setText(ticketCount);
                    if (ticketCount.length() > 1) {
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tvTicketCount
                                .getLayoutParams();
                        mlp.setMargins(0, 0, pixelDpConversion(), 0);
                    }
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
        if (context != null && !errorMessage.isEmpty() &&
                errorMessage.contains(AppConst.WHMCS_URL_ERROR) ||
                errorMessage.contains("Error code 213 occurs")) {
//            Utils.showToast(context, AppConst.WHMCS_URL_ERROR_MESSAGE);
        } else if (context != null && !errorMessage.isEmpty()) {
            Utils.showToast(context, context.getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_back_arrow,
            R.id.iv_forward_arrow,
            R.id.detail,
            R.id.detail_domain,
            R.id.detail_invoices,
            R.id.detail_tickets,
            R.id.live_tv,
            R.id.detail_tv_archive,
            R.id.account_info,
            R.id.detail_vod,
            R.id.detail_view_epg,
            R.id.detail_settings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
                myRecyclerView.getLayoutManager().scrollToPosition(layoutManager.findFirstVisibleItemPosition() - 1);
                break;
            case R.id.iv_forward_arrow:
                myRecyclerView.getLayoutManager().scrollToPosition(layoutManager.findLastVisibleItemPosition() + 1);
                break;
            case R.id.detail:
                if (context != null) {
                    SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
                    int clientId = preferences.getInt("clientid", -1);
                    if (clientId == -1 || clientId == 0) {
                        Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
                        intentLogin.putExtra(AppConst.ACTIVITY_SERVICES, AppConst.ACTIVITY_SERVICES);
                        startActivity(intentLogin);
                    } else {
                        Intent servicesIntent = new Intent(context, ServicesActivity.class);
                        startActivity(servicesIntent);
                    }
                }
                break;
            case R.id.detail_invoices:
                if (context != null) {
                    SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
                    int clientId = preferences.getInt("clientid", -1);
                    if (clientId == -1 || clientId == 0) {
                        Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
                        intentLogin.putExtra(AppConst.ACTIVITY_INVOICES, AppConst.ACTIVITY_INVOICES);
                        startActivity(intentLogin);
                    } else {
                        Intent invoiceActivityIntent = new Intent(context, InvoicesActivity.class);
                        startActivity(invoiceActivityIntent);
                    }
                }
                break;
            case R.id.detail_tickets:
                if (context != null) {
                    SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
                    int clientId = preferences.getInt("clientid", -1);
                    if (clientId == -1 || clientId == 0) {
                        Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
                        intentLogin.putExtra(AppConst.ACTIVITY_TICKETS, AppConst.ACTIVITY_TICKETS);
                        startActivity(intentLogin);
                    } else {
                        Intent ticketsActivityIntent = new Intent(context, TicketsActivity.class);
                        startActivity(ticketsActivityIntent);
                    }
                }
                break;
            case R.id.live_tv:
                if (context != null)
                    Utils.set_layout_live(context);
//                Intent liveStreamIntent = new Intent(getActivity(), LiveTVTabViewActivity.class);
//                startActivity(liveStreamIntent);
                break;
            case R.id.account_info:
                Intent AccountInfoIntent = new Intent(getActivity(), AccountInfoActivity.class);
                startActivity(AccountInfoIntent);
                break;
            case R.id.detail_vod:
                if (context != null)
                    Utils.set_layout_vod(context);

//                Intent vodIntent = new Intent(getActivity(), VodActivityNewFlow.class);
//                startActivity(vodIntent);
//                Intent vodIntent = new Intent(getActivity(), VodTabViewActivity.class);
//                startActivity(vodIntent);
                break;
            case R.id.detail_tv_archive:

                Intent detail_tv_archive = new Intent(getActivity(), TVArchiveActivity.class);
                startActivity(detail_tv_archive);
                break;
            case R.id.detail_view_epg:
                if(context !=null) {
                    launchTvGuide();
                }
                break;
            case R.id.detail_settings:
                Intent setting = new Intent(getActivity(), SettingssActivity.class);
                startActivity(setting);
                break;
        }
    }

    @Override
    public void panelAPI(XtreamPanelAPICallback xtreamPanelAPICallback, String username) {
        if (xtreamPanelAPICallback != null && context != null && xtreamPanelAPICallback.getCategories() != null &&
                xtreamPanelAPICallback.getAvailableChannels() != null) {
            if (rlImportProcess != null) {
                rlImportProcess.setVisibility(View.VISIBLE);
            }
            final ArrayList<PanelMoviePojo> movieList = xtreamPanelAPICallback.getCategories().getMovie();
            final ArrayList<PanelLivePojo> liveList = xtreamPanelAPICallback.getCategories().getLive();
            final Map<String, PanelAvailableChannelsPojo> availableChanelsList =
                    (Map<String, PanelAvailableChannelsPojo>) xtreamPanelAPICallback.getAvailableChannels();
            int totalVod = 0;
            int totalLive = 0;
            int totalLiveAndVod = 0;

            if (movieList != null) {
                totalVod = movieList.size();
            }
            if (liveList != null) {
                totalLive = liveList.size();
            }
            if (liveStreamDBHandler!=null && liveStreamDBHandler.getStreamsCount("live") > 0 && tvLiveCount!=null){
                tvLiveCount.setText(getResources().getString(R.string.total));
                tvLiveCount.append(" : "+liveStreamDBHandler.getStreamsCount("live"));
                tvLiveCount.setVisibility(View.VISIBLE);
            }

            if (liveStreamDBHandler!=null && liveStreamDBHandler.getStreamsCount("movie") > 0 && tvVodCount!=null){
                tvVodCount.setText(getResources().getString(R.string.total));
                tvVodCount.append(" : "+liveStreamDBHandler.getStreamsCount("movie"));
                tvVodCount.setVisibility(View.VISIBLE);
            }

            if (availableChanelsList != null) {
                totalLiveAndVod = availableChanelsList.size();
            }
//            final int totalLive = liveList.size();
//            final int totalLiveAndVod = availableChanelsList.size();
            final LiveStreamCategoryIdDBModel movieCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
            final LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
            final LiveStreamsDBModel availableChannel = new LiveStreamsDBModel();


//            Utils.showToast(context, "Records Fetched:" + xmltvCallback.programmePojos.size());

            final int finalTotalLiveAndVod = totalLiveAndVod;
            @SuppressLint("StaticFieldLeak")
            class AllChannelsAndVodAsyncTask extends AsyncTask<String, Integer, Boolean> {
                Context mcontext = null;
                final int ITERATIONS = finalTotalLiveAndVod;

                AllChannelsAndVodAsyncTask(Context context) {
                    mcontext = context;
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    publishProgress(0);
                    int i = 0;

                    try{
                        if (liveStreamDBHandler != null) {
                            liveStreamDBHandler.makeEmptyChanelsRecord();
                        }

                    }catch(IllegalStateException e){
                        Log.e("Exception", "IllegalState Exception:", e);
                    }

                    try{
                        if (liveStreamDBHandler != null) {
                            liveStreamDBHandler.addAllAvailableChannel(availableChanelsList);
                        }

                    }catch(IllegalStateException e){
                        Log.e("Exception", "IllegalState Exception:", e);
                    }


                    return true;
                }


                @Override
                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null &&
//                            tvImportingStreams != null &&
//                            progressBar != null &&
//                            tvPercentage != null &&
//                            tvCountings != null) {
//                        float progressCount = progress[0];
//                        float iterations = ITERATIONS;
//                        float percentage = ((progressCount / iterations) * 100);
//                        tvImportingStreams.setText(getResources().getString(R.string.refreshing_all_channels));
//                        progressBar.setProgress((int) percentage);
//                        tvCountings.setText(progress[0] + "/" + ITERATIONS);
//                        tvPercentage.setText(String.format("%.2f", percentage) + "%");


//                        ((DashboardActivity) context).tvImportingStreams.setText(AppConst.IMPORTING_ALL_CHANNELS);
//                        ((ImportStreamsActivity) context).progressBar.setProgress((int) percentage);
//                        ((ImportStreamsActivity) context).tvCountings.setText(progress[0] + "/" + ITERATIONS);
//                        ((ImportStreamsActivity) context).tvPercentage.setText(String.format("%.2f", percentage) + "%");
//                    }
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Boolean result) {
                    currentDate = currentDateValue();
                    if (currentDate != null && liveStreamDBHandler != null) {


                        try{
                            liveStreamDBHandler.updateDBStatus(AppConst.DB_CHANNELS,
                                    AppConst.DB_CHANNELS_ID,
                                    AppConst.DB_UPDATED_STATUS_FINISH);

                        }catch(IllegalStateException e){
                            Log.e("Exception", "IllegalState Exception:", e);
                        }

                    }
                    if (rlImportProcess != null) {
                        rlImportProcess.setVisibility(View.GONE);
                    }


                    Utils.showToast(context, getResources().getString(R.string.update_livestreams_vod_success));
//                    if (context != null) {
//                        Intent importEPGIntent = new Intent(context, ImportEPGActivity.class);
//                        startActivity(importEPGIntent);
//                        finish();
//                    }
                }
            }


            final int finalTotalLive = totalLive;
            @SuppressLint("StaticFieldLeak")
            class LiveAsyncTask extends AsyncTask<String, Integer, Boolean> {

                Context mcontext = null;

                final int ITERATIONS = finalTotalLive;

                LiveAsyncTask(Context context) {
                    mcontext = context;
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    publishProgress(0);

                    try{
                        liveStreamDBHandler.makeEmptyLiveCategory();
                    }catch(IllegalStateException e){
                        Log.e("Exception", "IllegalState Exception:", e);
                    }

                    try{
                        if (liveStreamDBHandler != null) {
                            liveStreamDBHandler.addLiveCategories(liveList);
                        }
                    }catch(IllegalStateException e){
                        Log.e("Exception", "IllegalState Exception:", e);
                    }



                    return true;
                }


                @Override
                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null &&
//                            tvImportingStreams != null &&
//                            progressBar != null &&
//                            tvPercentage != null &&
//                            tvCountings != null) {
//                        float progressCount = progress[0];
//                        float iterations = ITERATIONS;
//                        float percentage = ((progressCount / iterations) * 100);
//                        tvImportingStreams.setText(getResources().getString(R.string.refreshing_livestreams_cat));
//                        progressBar.setProgress((int) percentage);
//                        tvCountings.setText(progress[0] + "/" + ITERATIONS);
//                        tvPercentage.setText(String.format("%.2f", percentage) + "%");
//                    }
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Boolean result) {
//                    Utils.showToast(context, "LiveStreams Categories Imported Successfully!");
                    if (context != null) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                            AllChannelsAndVodAsyncTask ourAsyncTask = new AllChannelsAndVodAsyncTask(context);
                            ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        } else {
                            AllChannelsAndVodAsyncTask ourAsyncTask = new AllChannelsAndVodAsyncTask(context);
                            ourAsyncTask.execute();
                        }

                    }
                }
            }


            final int finalTotalVod = totalVod;

            @SuppressLint("StaticFieldLeak")
            class VodAsyncTask extends AsyncTask<String, Integer, Boolean> {

                Context mcontext = null;

                final int ITERATIONS = finalTotalVod;

                private VodAsyncTask(Context context) {
                    mcontext = context;
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    publishProgress(0);

                    try{
                        liveStreamDBHandler.makeEmptyMovieCategory();

                    }catch(IllegalStateException e){
                        Log.e("Exception", "IllegalState Exception:", e);
                    }

                    try{
                        if (liveStreamDBHandler != null) {
                            liveStreamDBHandler.addMovieCategories(movieList);
                        }
                    }catch(IllegalStateException e){
                        Log.e("Exception", "IllegalState Exception:", e);
                    }


                    return true;
                }


                @Override
                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null && tvImportingStreams != null && progressBar != null && tvPercentage != null && tvCountings != null) {
//                        float progressCount = progress[0];
//                        float iterations = ITERATIONS;
//                        float percentage = ((progressCount / iterations) * 100);
//                        tvImportingStreams.setText(getResources().getString(R.string.refreshing_vod_cat));
//                        progressBar.setProgress((int) percentage);
//                        tvCountings.setText(progress[0] + "/" + ITERATIONS);
//                        tvPercentage.setText(String.format("%.2f", percentage) + "%");
//                    }
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Boolean result) {
//                    Utils.showToast(context, "VOD Categories Imported Successfully!");
                    if (context != null) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                            LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
                            ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
                            ourAsyncTask.execute();
                        }
                    }
                }
            }


            if (finalTotalVod != 0) {

                if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                    VodAsyncTask ourAsyncTask = new VodAsyncTask(context);
                    ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {
                    VodAsyncTask ourAsyncTask = new VodAsyncTask(context);
                    ourAsyncTask.execute();
                }
            } else {
                if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                    LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
                    ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    LiveAsyncTask ourAsyncTask = new LiveAsyncTask(context);
                    ourAsyncTask.execute();
                }
            }
        }

    }


    @Override
    public void panelApiFailed(String updateDBFailed) {
        if (updateDBFailed.equals(AppConst.DB_UPDATED_STATUS_FAILED) &&
                liveStreamDBHandler != null) {
            liveStreamDBHandler.updateDBStatus(AppConst.DB_CHANNELS,
                    AppConst.DB_CHANNELS_ID,
                    AppConst.DB_UPDATED_STATUS_FAILED);
        }
    }

    @Override
    public void epgXMLTV(final XMLTVCallback xmltvCallback) {
        if (xmltvCallback != null && context != null && xmltvCallback.programmePojos != null) {
            if (rlImportProcess != null && tvImportingStreams != null) {
                rlImportProcess.setVisibility(View.VISIBLE);
                //tvImportingStreams.setText(getResources().getString(R.string.importing_epg));
            }

            final int totalEPGSize = xmltvCallback.programmePojos.size();

            if (totalEPGSize > 0 && tvEPGCount!=null){
                tvEPGCount.setText(getResources().getString(R.string.total));
                tvEPGCount.append(" : "+totalEPGSize);
                tvEPGCount.setVisibility(View.VISIBLE);
            }
            liveStreamDBHandler.makeEmptyEPG();
            @SuppressLint("StaticFieldLeak")
            class NewAsyncTask extends AsyncTask<String, Integer, Boolean> {

                Context mcontext = null;
                final int ITERATIONS = xmltvCallback.programmePojos.size();

                NewAsyncTask(Context context) {
                    mcontext = context;
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    publishProgress(0);

                    try{
                        if (liveStreamDBHandler != null) {
                            liveStreamDBHandler.addEPG(xmltvCallback.programmePojos);
                        }
                    }catch(IllegalStateException e){
                        Log.e("Exception", "IllegalState Exception:", e);
                    }



                    return true;
                }


                @Override
                protected void onProgressUpdate(Integer... progress) {
//                    if (context != null && tvImportingStreams != null && progressBar != null && tvCountings != null && tvPercentage != null
//                            ) {
//                        float progressCount = progress[0];
//                        float iterations = ITERATIONS;
//                        float percentage = ((progressCount / iterations) * 100);
//                        tvImportingStreams.setText(getResources().getString(R.string.refreshing_epg));
//                        progressBar.setProgress((int) percentage);
//                        tvCountings.setText(progress[0] + "/" + ITERATIONS);
//                        tvPercentage.setText(String.format("%.2f", percentage) + "%");
////                        rlImportProcess.setVisibility(View.GONE);
//                    }
                }

                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (rlImportProcess != null) {
                        rlImportProcess.setVisibility(View.GONE);
                    }
//                    String currentDate = currentDateValue();
//
//                    liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG,
//                            AppConst.DB_EPG_ID, AppConst.DB_UPDATED_STATUS_FINISH, currentDate);

                    liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG,
                            AppConst.DB_EPG_ID,
                            AppConst.DB_UPDATED_STATUS_FINISH);
//                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, AppConst.DB_EPG_ID);

                    if (context != null) {
                        Utils.showToast(context, getResources().getString(R.string.update_tv_guide_success) +" ("+totalEPGSize+")");
                    }
                }
            }


            if (Build.VERSION.SDK_INT >= 11/*HONEYCOMB*/) {
                NewAsyncTask ourAsyncTask = new NewAsyncTask(context);
                ourAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            } else {
                NewAsyncTask ourAsyncTask = new NewAsyncTask(context);
                ourAsyncTask.execute();

            }
        }
    }

    @Override
    public void epgXMLTVUpdateFailed(String failedUpdate) {
        if (failedUpdate.equals(AppConst.DB_UPDATED_STATUS_FAILED) &&
                liveStreamDBHandler != null) {
            liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG,
                    AppConst.DB_EPG_ID,
                    AppConst.DB_UPDATED_STATUS_FAILED);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        boolean onNavigationItemSelected(MenuItem item);

        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void validateCustomLogin(ValidateCustomLoginCallback validateCustomLoginCallback) {
        if (validateCustomLoginCallback != null && validateCustomLoginCallback.getResult().equals("success") && !validateCustomLoginCallback.getData().getClientid().equals("")) {
            int userId = validateCustomLoginCallback.getData().getClientid();
            String email = validateCustomLoginCallback.getData().getEmail();
            loginPreferences = this.getActivity().getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();
            SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(AppConst.CLIENT_ID, userId);
            editor.putString(AppConst.PREF_EMAIL_WHMCS, email);
            loginPrefsEditor.putInt(AppConst.PREF_CLIENT_ID, userId);
            editor.commit();
        }
        updateDashboardData();
    }

    public void updateDashboardData() {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
            int clientId = preferences.getInt("clientid", -1);
            if (clientId != -1 && clientId != 0 && homePresenter != null) {

                ivServiceCount.setVisibility(View.VISIBLE);
                ivInvoiceCount.setVisibility(View.VISIBLE);
                ivTicketCount.setVisibility(View.VISIBLE);
                homePresenter.getSITCount(clientId);
            }
            if (clientId == 0 ||
                    clientId == -1 &&
                            ivServiceCount != null &&
                            ivInvoiceCount != null &&
                            ivTicketCount != null) {
                ivServiceCount.setVisibility(View.GONE);
                ivInvoiceCount.setVisibility(View.GONE);
                ivTicketCount.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);

        /**
         * This code used here to put menu item in center vertical using instanceof Action menu
         * view
         */
        toolbar.inflateMenu(R.menu.menu_text_icon);
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


        /**
         * This line of code simple direct menu inflater \
         * without any change in its position
         */
//        inflater.inflate(R.menu.menu_search_view, menu);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           case R.id.menu_load_channels_vod:
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
            case R.id.menu_load_tv_guide:
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
        }
        return false;


    }

    private void loadChannelsAndVod() {
        if (context != null) {
            boolean isChannelVODUpdating = false;
//            isChannelVODUpdating = getChannelVODUpdateStatus();
            if (!isChannelVODUpdating) {
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


//    private boolean getChannelEPGUpdateStatus() {
//        if (liveStreamDBHandler != null &&
//                databaseUpdatedStatusDBModelLive != null &&
//                databaseUpdatedStatusDBModelEPG != null
//                ) {
//            databaseUpdatedStatusDBModelLive =
//                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, AppConst.DB_CHANNELS_ID);
//            databaseUpdatedStatusDBModelEPG =
//                    liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, AppConst.DB_EPG_ID);
//            if (databaseUpdatedStatusDBModelLive !=null &&
//                    databaseUpdatedStatusDBModelEPG !=null &&
//                    databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().
//                    equals(AppConst.DB_UPDATED_STATUS_FINISH) &&
//                    databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().
//                            equals(AppConst.DB_UPDATED_STATUS_FINISH)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
//    }
}
