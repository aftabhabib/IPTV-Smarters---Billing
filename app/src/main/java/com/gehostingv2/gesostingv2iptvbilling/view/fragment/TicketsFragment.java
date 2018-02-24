package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ActionMenuView;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.TicketsActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetTicketsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.TicketDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.TicketPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWHMCSActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.TicketsAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.TicketInterface;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

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
 * Use the {@link TicketsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketsFragment extends Fragment implements TicketInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.tv_my_support_tickets)
    TextView tvMySupportTickets;
    @BindView(R.id.view_line_my_support_ticket)
    View viewLineMySupportTicket;
    @BindView(R.id.rl_my_support_ticket)
    RelativeLayout rlMySupportTicket;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.tv_no_support_ticket_found)
    TextView tvNoSupportTicketFound;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;

    @BindView(R.id.fab)
    com.github.clans.fab.FloatingActionButton fabBt;

    private TicketsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TicketPresenter ticketPresenter;
    private Context context;
    int clientId;
    ArrayList<TicketDetailPojo> ticketDetailPojos;
    ProgressDialog progressDialog;
    ProgressDialog progressDialogN;
    TicketsActivity dashboardActivity;
    Toolbar toolbar;
    ImageView logoHomeIV;
    SearchView searchView;

    int actionBarHeight;
    private Typeface fontOPenSansBold;
    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    private FragmentActivity myContext;


    public TicketsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketsFragment newInstance(String param1, String param2) {
        TicketsFragment fragment = new TicketsFragment();
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
        View view = inflater.inflate(R.layout.fragment_tickets_frafment, container, false);
        unbinder = ButterKnife.bind(this, view);
        setToolbarLogoImagewithSearchView();
        intialize();


        return view;
    }


    private void setToolbarLogoImagewithSearchView() {
        setHasOptionsMenu(true);
        setMenuVisibility(true);
        setRetainInstance(true);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        logoHomeIV = (ImageView) toolbar.findViewById(R.id.tv_header_title);
        if (logoHomeIV != null)
            logoHomeIV.setImageResource(R.drawable.logo_home_new);

//        searchView= (SearchView) toolbar.findViewById(R.id.action_search);
    }

    private void intialize() {
        mLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(mLayoutManager);
        ticketPresenter = new TicketPresenter(this);
        context = getContext();
        dashboardActivity = (TicketsActivity) context;
        progressDialog = Utils.showProgressD(progressDialog, context);
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        tvMySupportTickets.setTypeface(fontOPenSansBold);
        SharedPreferences pref = getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        clientId = pref.getInt(AppConst.CLIENT_ID, -1);
        if (clientId == -1 || clientId == 0) {
            Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
            startActivity(intentLogin);
        } else {
            ticketPresenter.getTickets(clientId, myRecyclerView);
        }
        fabBt.setVisibility(View.VISIBLE);
        fabBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });
    }

    private void call() {
        setMenuVisibility(false);
        Fragment openTicketFr = new OpenTicketGeneralEnquiriesDepartmentFragment();
        this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out)
                .replace(R.id.fl_tickets_fragment, openTicketFr, AppConst.TAG_HOME)
                .addToBackStack(AppConst.TAG_HOME)
                .commit();
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

    @OnClick({R.id.iv_back_press, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_press:
                fabBt.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                startActivity(intent);
//                Fragment homeFragment = new HomeFragment();
//                this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
//                        android.R.anim.fade_out)
//                        .replace(R.id.fl_tickets_fragment, homeFragment, AppConst.TAG_HOME)
//                        .addToBackStack(AppConst.TAG_HOME)
//                        .commit();
                break;
            case R.id.fab:
                Fragment openTicketFr = new OpenTicketFragment();
                this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out)
                        .replace(R.id.fl_tickets_fragment, openTicketFr, AppConst.TAG_HOME)
                        .addToBackStack(AppConst.TAG_HOME)
                        .commit();
        }
    }

    @Override
    public void onBackPressed() {
        fabBt.setVisibility(View.INVISIBLE);
        Fragment homeFragment = new HomeFragment();
        this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out)
                .replace(R.id.fl_tickets_fragment, homeFragment, AppConst.TAG_HOME)
                .addToBackStack(AppConst.TAG_HOME)
                .commit();

    }

    @Override
    public void onDetach() {

        super.onDetach();
/**
 * this section of code is done so that when user moves back from the current fragment
 * logo image again convert to home screen image.
 */
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        logoHomeIV = (ImageView) toolbar.findViewById(R.id.tv_header_title);
        logoHomeIV.setImageResource(R.drawable.logo_home_new);

        if (fabBt != null)
            fabBt.setVisibility(View.INVISIBLE);
        setMenuVisibility(false);
        mListener = null;
    }

    @Override
    public void getTickets(GetTicketsCallback getTicketsCallback, RecyclerView myRecyclerView) {
        if (getTicketsCallback.getResult().equals("success") && getTicketsCallback.getTickets() == null) {
//            Utils.showAlertBox(context, "No Records Found");
            tvNoSupportTicketFound.setVisibility(View.VISIBLE);
        } else if (getTicketsCallback.getResult().equals("success")
                && getTicketsCallback.getTickets() != null
                && !getTicketsCallback.getToatlResult().equals("0")) {
            ticketDetailPojos = getTicketsCallback.getTickets().getTicket();
            myRecyclerView.getRecycledViewPool().clear();
            mAdapter = new TicketsAdapter(context, ticketDetailPojos, dashboardActivity, getActivity());
            if (myRecyclerView != null && mAdapter != null) {
                myRecyclerView.setAdapter(null);
                myRecyclerView.setLayoutManager(null);
                myRecyclerView.setAdapter(mAdapter);
                myRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter.notifyDataSetChanged();
            }
        }
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
    public void onFailed(String errorMessage) {
        if (context != null && !errorMessage.isEmpty()) {
            Utils.showToast(context, context.getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);

        /**
         * This code used here to put menu item in center vertical using instanceof Action menu
         * view
         */
        toolbar.inflateMenu(R.menu.menu_search_view);
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
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
                if(context!=null)
                searchView.setQueryHint(context.getResources().getString(R.string.search_support_ticket));
                SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
                searchView.setIconifiedByDefault(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (pbLoader != null) {
                            pbLoader.setVisibility(View.INVISIBLE);
                        }
                        if (tvNoSupportTicketFound != null && mAdapter != null) {
                            tvNoSupportTicketFound.setVisibility(View.GONE);
                            mAdapter.filter(newText, tvNoSupportTicketFound);
                        }
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
