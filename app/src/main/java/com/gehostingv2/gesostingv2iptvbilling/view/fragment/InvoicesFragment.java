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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWHMCSActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.InvoicesPagerAdapter;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InvoicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoicesFragment extends Fragment  {   //implements InvoicesInterface, HomeInterface
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.tv_my_invoices)
    TextView tvMyInvoices;
    @BindView(R.id.view_line_my_invoices)
    View viewLineMyInvoices;
    @BindView(R.id.tab_layout_invoices)
    TabLayout tabLayoutInvoices;
    @BindView(R.id.line_below_tabs)
    View lineBelowTabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.rl_my_invoices)
    RelativeLayout rlMyInvoices;
    Unbinder unbinder;
    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;

    private FragmentActivity myContext;
    private Context context;
    private Toolbar toolbar;
    private SearchView searchView;
    ArrayList<Integer> tabInvoicesTotalCount = new ArrayList<>();
    Typeface fontOPenSansBold;
    Typeface fontOPenSansRegular;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InvoicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoicesFragment newInstance(String param1, String param2) {
        InvoicesFragment fragment = new InvoicesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fabric.with(getContext(), new Crashlytics());
        setHasOptionsMenu(false);
        View view = inflater.inflate(R.layout.fragment_invoices, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        context = getContext();
        setMenuBar();
        setHeaderFont();
        SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
        int clientId = preferences.getInt("clientid", -1);
        if (clientId == -1 || clientId == 0) {
            Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
            startActivity(intentLogin);
        }
        else if (clientId != 0 && clientId != -1) {
            setInvoicesTab();
        }
    }

    private void setInvoicesTab() {
        tabLayoutInvoices.addTab(tabLayoutInvoices.newTab().setText(context.getString(R.string.invoices_paid)));
        tabLayoutInvoices.addTab(tabLayoutInvoices.newTab().setText(context.getString(R.string.invoices_unpaid)));
        tabLayoutInvoices.setTabGravity(TabLayout.GRAVITY_FILL);
        final InvoicesPagerAdapter adapter = new InvoicesPagerAdapter(getChildFragmentManager(), tabLayoutInvoices.getTabCount(), //myContext.getSupportFragmentManager()
                getContext(), tabInvoicesTotalCount);
        pager.setAdapter(adapter);
        tabLayoutInvoices.setupWithViewPager(pager);
        for (int i = 0; i < tabLayoutInvoices.getTabCount(); i++) {
            TabLayout.Tab tab1 = tabLayoutInvoices.getTabAt(i);
            tab1.setCustomView(adapter.getTabView(i));
        }

        View viewDefaultOPeningTab = tabLayoutInvoices.getTabAt(0).getCustomView();
        adapter.setDefaultOpningViewTab(viewDefaultOPeningTab, fontOPenSansBold);
        pager.setCurrentItem(0);
        tabLayoutInvoices.setSelectedTabIndicatorColor(Color.parseColor("#ff6f43"));

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutInvoices));
        tabLayoutInvoices.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                int postion = tab.getPosition();
                View view = tab.getCustomView();
                switch (postion) {
                    case 0:
                        tabLayoutInvoices.setSelectedTabIndicatorColor(Color.parseColor("#0cdc78"));
                        adapter.selectPaidTabView(view, fontOPenSansBold, postion);
                        break;
                    case 1:
                        tabLayoutInvoices.setSelectedTabIndicatorColor(Color.parseColor("#ff6f43"));
                        adapter.selectUnpaidTabView(view, fontOPenSansBold);
                        break;
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int postion = tab.getPosition();
                View view = tab.getCustomView();
                switch (postion) {
                    case 0:
                        adapter.unselectPaidTabView(view, fontOPenSansRegular);
                        break;
                    case 1:
                        adapter.unselectUnpaidTabView(view, fontOPenSansRegular);
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
        tvMyInvoices.setTypeface(fontOPenSansBold);
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
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_back_press)
    public void onViewClicked() {
        myContext.onBackPressed();
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
                searchView.setQueryHint("Search Invoice");
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
