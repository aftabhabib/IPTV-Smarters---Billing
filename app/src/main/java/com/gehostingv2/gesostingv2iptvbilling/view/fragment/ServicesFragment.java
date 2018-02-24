package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ActionMenuView;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetSITCountCallback;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWHMCSActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetClientProductsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ProductDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ProductPojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.HomePresenter;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ServicesPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.ServicesPagerAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.HomeInterface;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ServicesInterface;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicesFragment extends Fragment implements ServicesInterface, HomeInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.tv_my_services)
    TextView tvMyServices;
    @BindView(R.id.view_line_my_services_ticket)
    View viewLineMySupportTicket;
    @BindView(R.id.rl_my_services)
    RelativeLayout rlMyServices;
    @BindView(R.id.tab_layout_services)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.fl_myservice)
    FrameLayout flMyservice;

    @BindView(R.id.iv_back_press)
    ImageView ivBackpress;

    private FragmentActivity myContext;
    private Context context;
    private Toolbar toolbar;
    private SearchView searchView;

    private ArrayList<ProductDetailPojo> activeServiceList = new ArrayList<>();
    private ArrayList<ProductDetailPojo> pendingServiceList = new ArrayList<>();
    private ArrayList<ProductDetailPojo> cancelledServiceList = new ArrayList<>();
    private ArrayList<ProductDetailPojo> suspendedServicesList = new ArrayList<>();
    private ArrayList<ProductDetailPojo> terminatedServicesList = new ArrayList<>();

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ServicesPresenter servicesPresenter;
    private HomePresenter homePresenter;

    int activeCount = 0;
    int pendingCount = 0;
    int suspendedCount = 0;
    int terminatedCount = 0;
    int cancelledCountl = 0;

    ArrayList<Integer> tabServicesTotalCount = new ArrayList<>();
    Typeface fontOPenSansBold;
    Typeface fontOPenSansRegular;


//    ServiceAdapter mAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServicesFragment newInstance(String param1, String param2) {
        ServicesFragment fragment = new ServicesFragment();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fabric.with(getContext(), new Crashlytics());
        View view = inflater.inflate(R.layout.fragment_my_services, container, false);
        ButterKnife.bind(this, view);
        initialize();

        return view;
    }

    private void initialize() {
        context = getContext();
        servicesPresenter = new ServicesPresenter(this);
        homePresenter = new HomePresenter(this);
        setMenuBar();
        setHeaderFont();
        SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
        int clientId = preferences.getInt("clientid", -1);
        if (clientId == -1 || clientId == 0) {
            Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
            startActivity(intentLogin);
        }
        else if (homePresenter!=null && servicesPresenter!=null &&
                clientId != 0 && clientId != 0) {
//            homePresenter.getCLientProducts(clientId);
//            servicesPresenter.getCLientProducts(clientId);

            setTabView();
        }
    }

    private void setTabView() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_service_active)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_service_cancelled)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_service_pending)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_service_suspended)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_service_terminated)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        final ServicesPagerAdapter adapter = new ServicesPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), //myContext.getSupportFragmentManager()
                getContext(), tabServicesTotalCount);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab1 = tabLayout.getTabAt(i);
            tab1.setCustomView(adapter.getTabView(i));
        }

        View viewDefaultOPeningTab = tabLayout.getTabAt(0).getCustomView();
        adapter.setDefaultOpningViewTab(viewDefaultOPeningTab, fontOPenSansBold);


        pager.setCurrentItem(0);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0cdc78"));

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                int postion = tab.getPosition();
                View view = tab.getCustomView();
                switch (postion) {
                    case 0:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0cdc78"));
                        adapter.setActiveTabView(view, fontOPenSansBold);
                        break;
                    case 1:

                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0c99e2"));
                        adapter.setCancelledTabView(view, fontOPenSansBold);
                        break;
                    case 2:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#e4cc00"));
                        adapter.setPendingView(view, fontOPenSansBold);
                        break;
                    case 3:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ff6f43"));
                        adapter.setSuspendedTabView(view, fontOPenSansBold);
                        break;
                    case 4:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffc600"));
                        adapter.setTerminatedTabView(view, fontOPenSansBold);
                        break;
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int postion = tab.getPosition();
                View view = tab.getCustomView();
                switch (postion) {
                    case 0:
                        adapter.setActiveTabUnselectedView(view, fontOPenSansRegular);
                        break;
                    case 1:
                        adapter.setCancelledTabUnselectedView(view, fontOPenSansRegular);
                        break;
                    case 2:
                        adapter.setPendingUnselectedView(view, fontOPenSansRegular);
                        break;
                    case 3:
                        adapter.setSuspendedTabUnselectedView(view, fontOPenSansRegular);
                        break;
                    case 4:
                        adapter.setTerminatedTabUnselectedView(view, fontOPenSansRegular);
                        break;
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setHeaderFont() {
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        fontOPenSansRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_regular.ttf");
        tvMyServices.setTypeface(fontOPenSansBold);
    }

    private void setMenuBar() {
        setHasOptionsMenu(false);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
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
        myContext = (FragmentActivity) getActivity();
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
//        rplBGSubmit.stopRippleAnimation();
        setHasOptionsMenu(false);
        mListener = null;
    }

    @Override
    public void getMyServices(GetClientProductsCallback getClientProductsCallback) {
        if (getClientProductsCallback.getResult().equals("success")) {
            if (!isAdded()) return;
            else {
                ProductPojo arrayList = getClientProductsCallback.getProductts();
                ArrayList<ProductDetailPojo> productDeailArrayList = getClientProductsCallback.getProductts().getProductDeailArrayList();
                for (ProductDetailPojo productDetailPojo : productDeailArrayList) {
                    if (!tabServicesTotalCount.isEmpty()) {
                        tabServicesTotalCount.clear();
                        tabLayout.removeAllTabs();
                        activeCount = 0;
                        pendingCount = 0;
                        suspendedCount = 0;
                        terminatedCount = 0;
                        cancelledCountl = 0;
                        if (activeServiceList != null && pendingServiceList != null && suspendedServicesList != null && terminatedServicesList != null &&
                                cancelledServiceList != null) {
                            activeServiceList.clear();
                            pendingServiceList.clear();
                            suspendedServicesList.clear();
                            terminatedServicesList.clear();
                            cancelledServiceList.clear();
                        }
                    }

                    String status = productDetailPojo.getStatus();
                    if (status.equals("Active")) {
                        activeServiceList.add(productDetailPojo);
                        activeCount++;
                    }
                    if (status.equals("Pending")) {
                        pendingServiceList.add(productDetailPojo);
                        pendingCount++;
                    }
                    if (status.equals("Suspended")) {
                        suspendedServicesList.add(productDetailPojo);
                        suspendedCount++;
                    }
                    if (status.equals("Terminated")) {
                        terminatedServicesList.add(productDetailPojo);
                        terminatedCount++;
                    }
                    if (status.equals("Cancelled")) {
                        cancelledServiceList.add(productDetailPojo);
                        cancelledCountl++;
                    }
                }
                tabServicesTotalCount.add(cancelledCountl);
                tabServicesTotalCount.add(activeCount);
                tabServicesTotalCount.add(pendingCount);
                tabServicesTotalCount.add(suspendedCount);
                tabServicesTotalCount.add(terminatedCount);
                tabLayout.addTab(tabLayout.newTab().setText("CANCELLED()"));
                tabLayout.addTab(tabLayout.newTab().setText("ACTIVE()"));
                tabLayout.addTab(tabLayout.newTab().setText("PENDING()"));
                tabLayout.addTab(tabLayout.newTab().setText("SUSPENDED()"));
                tabLayout.addTab(tabLayout.newTab().setText("TERMINATED()"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                final ServicesPagerAdapter adapter = new ServicesPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), //myContext.getSupportFragmentManager()
                        getContext(), tabServicesTotalCount);
                pager.setAdapter(adapter);
                tabLayout.setupWithViewPager(pager);
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab1 = tabLayout.getTabAt(i);
                    tab1.setCustomView(adapter.getTabView(i));
                }

                View viewDefaultOPeningTab = tabLayout.getTabAt(1).getCustomView();
                adapter.setDefaultOpningViewTab(viewDefaultOPeningTab, fontOPenSansBold);


                pager.setCurrentItem(1);
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0cdc78"));

                pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        pager.setCurrentItem(tab.getPosition());
                        int postion = tab.getPosition();
                        View view = tab.getCustomView();
                        switch (postion) {
                            case 0:
                                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0c99e2"));
                                adapter.setCancelledTabView(view, fontOPenSansBold);
                                break;
                            case 1:
                                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0cdc78"));
                                adapter.setActiveTabView(view, fontOPenSansBold);
                                break;
                            case 2:
                                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#e4cc00"));
                                adapter.setPendingView(view, fontOPenSansBold);
                                break;
                            case 3:
                                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ff6f43"));
                                adapter.setSuspendedTabView(view, fontOPenSansBold);
                                break;
                            case 4:
                                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffc600"));
                                adapter.setTerminatedTabView(view, fontOPenSansBold);
                                break;
                        }
                    }


                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        int postion = tab.getPosition();
                        View view = tab.getCustomView();
                        switch (postion) {
                            case 0:
                                adapter.setCancelledTabUnselectedView(view, fontOPenSansRegular);
                                break;
                            case 1:
                                adapter.setActiveTabUnselectedView(view, fontOPenSansRegular);
                                break;
                            case 2:
                                adapter.setPendingUnselectedView(view, fontOPenSansRegular);
                                break;
                            case 3:
                                adapter.setSuspendedTabUnselectedView(view, fontOPenSansRegular);
                                break;
                            case 4:
                                adapter.setTerminatedTabUnselectedView(view, fontOPenSansRegular);
                                break;
                        }

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

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
    public void onFailed(String message) {
        if (context != null && !message.isEmpty()) {
            Utils.showToast(context, context.getResources().getString(R.string.network_error));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void getServices(CommonResponseCallback commonResponseCallback) {
        if (commonResponseCallback.getResult().equals("success") &&
                commonResponseCallback.getToatlResult().equals("0")) {
            servicesWithZeroCount();
        } else if (commonResponseCallback.getResult().equals("success") && !commonResponseCallback.getToatlResult().equals("0")) {
//            SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);

            if (context != null) {
                SharedPreferences preferences = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
                if (preferences != null) {
                    int clientId = preferences.getInt("clientid", -1);
                    if (clientId != 0 && clientId != -1) {
                        servicesPresenter.getCLientProducts(clientId);
                    }
                }
            }
        }
    }

    void servicesWithZeroCount() {
        if (!tabServicesTotalCount.isEmpty())
            tabServicesTotalCount.clear();
        tabServicesTotalCount.add(cancelledCountl);
        tabServicesTotalCount.add(activeCount);
        tabServicesTotalCount.add(pendingCount);
        tabServicesTotalCount.add(suspendedCount);
        tabServicesTotalCount.add(terminatedCount);
        tabLayout.addTab(tabLayout.newTab().setText("CANCELLED()"));
        tabLayout.addTab(tabLayout.newTab().setText("ACTIVE()"));
        tabLayout.addTab(tabLayout.newTab().setText("PENDING()"));
        tabLayout.addTab(tabLayout.newTab().setText("SUSPENDED()"));
        tabLayout.addTab(tabLayout.newTab().setText("TERMINATED()"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        final ServicesPagerAdapter adapter = new ServicesPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), //myContext.getSupportFragmentManager()
                getContext(), tabServicesTotalCount);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab1 = tabLayout.getTabAt(i);
            tab1.setCustomView(adapter.getTabView(i));
        }

        View viewDefaultOPeningTab = tabLayout.getTabAt(1).getCustomView();
        adapter.setDefaultOpningViewTab(viewDefaultOPeningTab, fontOPenSansBold);


        pager.setCurrentItem(1);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0cdc78"));

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                int postion = tab.getPosition();
                View view = tab.getCustomView();
                switch (postion) {
                    case 0:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0c99e2"));
                        adapter.setCancelledTabView(view, fontOPenSansBold);
                        break;
                    case 1:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0cdc78"));
                        adapter.setActiveTabView(view, fontOPenSansBold);
                        break;
                    case 2:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#e4cc00"));
                        adapter.setPendingView(view, fontOPenSansBold);
                        break;
                    case 3:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ff6f43"));
                        adapter.setSuspendedTabView(view, fontOPenSansBold);
                        break;
                    case 4:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffc600"));
                        adapter.setTerminatedTabView(view, fontOPenSansBold);
                        break;
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int postion = tab.getPosition();
                View view = tab.getCustomView();
                switch (postion) {
                    case 0:
                        adapter.setCancelledTabUnselectedView(view, fontOPenSansRegular);
                        break;
                    case 1:
                        adapter.setActiveTabUnselectedView(view, fontOPenSansRegular);
                        break;
                    case 2:
                        adapter.setPendingUnselectedView(view, fontOPenSansRegular);
                        break;
                    case 3:
                        adapter.setSuspendedTabUnselectedView(view, fontOPenSansRegular);
                        break;
                    case 4:
                        adapter.setTerminatedTabUnselectedView(view, fontOPenSansRegular);
                        break;
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    }

    @OnClick(R.id.iv_back_press)
    public void onViewClicked() {
//        rplBGSubmit.startRippleAnimation();
        myContext.onBackPressed();
//        rplBGSubmit.stopRippleAnimation();
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        /**
         * This code used here to put menu item in center vertical using instanceof Action menu
         * view
         */
        toolbar.inflateMenu(R.menu.menu_search_view);
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
            case R.id.action_search:
                searchView = (SearchView) (SearchView) MenuItemCompat.getActionView(item);
                searchView.setQueryHint("Search Services");
                SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
//                searchView.setSearchableInfo(searchManager.getSearchableInfo(ticketDetailPojos().to));
                searchView.setIconifiedByDefault(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        //filters list items from adapter
//                        mAdapter.filter(newText);
                        return false;
                    }
                });

                return true;

            default:
                break;
        }
        return false;
    }
}
