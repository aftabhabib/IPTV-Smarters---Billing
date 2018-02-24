package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetInvoicesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.InvoicesDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.InvoicesPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.InvoicesActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.InvoicesAdaper;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.InvoicesInterface;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InvoicesPaidFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoicesPaidFragment extends Fragment implements InvoicesInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.empty_view)
    TextView emptyView;
    Unbinder unbinder;

    InvoicesActivity dashboardActivity;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    private ArrayList<InvoicesDetailPojo> arraylist;
    private InvoicesPresenter invoicesPresenter;
    private FragmentActivity myContext;
    private InvoicesAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private Context context;
    private Typeface fontOPenSansBold;
    private Toolbar toolbar;
    private SearchView searchView;
    private static int totalCount;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InvoicesPaidFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoicesPaidFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoicesPaidFragment newInstance(String param1, String param2) {
        InvoicesPaidFragment fragment = new InvoicesPaidFragment();
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
    public void getInvoicesTotalCount(CommonResponseCallback commonResponseCallback) {
        if(commonResponseCallback.getResult().equals("success")){
            totalCount= Integer.parseInt(commonResponseCallback.getToatlResult());
            SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
            int clientId = preferences.getInt("clientid", -1);
            if (clientId != 0 && clientId != -1) {
                invoicesPresenter.getInvices(clientId, totalCount );
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fabric.with(getContext(), new Crashlytics());
        View view = inflater.inflate(R.layout.fragment_invoices_paid, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        setMenuBar();
        return view;
    }

    private void initialize() {
        context = getContext();

        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        dashboardActivity = (InvoicesActivity) context;
        invoicesPresenter = new InvoicesPresenter(this);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
        int clientId = preferences.getInt("clientid", -1);
        if (clientId != 0 && clientId != -1) {
//            int invoicesTotalCount= 100;
//            invoicesPresenter.getInvices(clientId, invoicesTotalCount );
            invoicesPresenter.getInvicesTotalCount(clientId);
        }
        myRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void setMenuBar() {
        setHasOptionsMenu(true);
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
        setHasOptionsMenu(false);
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getInvoices(GetInvoicesCallback getInvoicesCallback) {
        if (getInvoicesCallback != null) {
            ArrayList<InvoicesDetailPojo> invoicesDetailPojoArrayList = getInvoicesCallback.getInvoices().getInvoice();
            ArrayList<InvoicesDetailPojo> paidInvoicesList = new ArrayList<InvoicesDetailPojo>();
            for (InvoicesDetailPojo invoicesDetailPojo : invoicesDetailPojoArrayList) {
                String status = invoicesDetailPojo.getStatus();
                if (status.equals("Paid"))
                    paidInvoicesList.add(invoicesDetailPojo);
            }
            if (pbLoader != null)
                pbLoader.setVisibility(View.INVISIBLE);
            if (paidInvoicesList.size() > 0 && myRecyclerView != null && emptyView != null) {
                myRecyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                mAdapter = new InvoicesAdaper(paidInvoicesList, getContext(), dashboardActivity, fontOPenSansBold);
                myRecyclerView.setAdapter(mAdapter);

            } else if (paidInvoicesList.isEmpty()) {
                if (myRecyclerView != null && emptyView != null) {
                    myRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    if (context != null)
                        emptyView.setText(context.getResources().getString(R.string.no_paid_invoices));
                }
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
        if (pbLoader != null)
            pbLoader.setVisibility(View.INVISIBLE);
        if (myRecyclerView != null && emptyView != null) {
            myRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            if (context != null)
                emptyView.setText(context.getResources().getString(R.string.no_paid_invoices));
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
                if (context != null)
                    searchView.setQueryHint(context.getResources().getString(R.string.search_paid_invoices));
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

                        if (pbLoader != null)
                            pbLoader.setVisibility(View.INVISIBLE);
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        if (emptyView != null && mAdapter != null) {
                            emptyView.setVisibility(View.GONE);
                            mAdapter.filter(newText, emptyView, progressDialog);
                        }
                        return true;
                    }
                });

                return true;

            default:
                break;
        }
        return false;
    }

}
