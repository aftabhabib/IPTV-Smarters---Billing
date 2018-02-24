package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.gehostingv2.gesostingv2iptvbilling.view.activity.ServicesActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetClientProductsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ProductDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ProductPojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ServicesPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.ServicesAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ServicesInterface;
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
 * Use the {@link CancelledServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelledServicesFragment extends Fragment implements ServicesInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    private ArrayList<ProductDetailPojo> arraylist;
    private ServicesPresenter servicesPresenter;
    private FragmentActivity myContext;
    private ServicesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private Context context;
    //    private DashboardActivity dashboardActivity;
    private ServicesActivity dashboardActivity;
    private Toolbar toolbar;
    private SearchView searchView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CancelledServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelledServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelledServicesFragment newInstance(String param1, String param2) {
        CancelledServicesFragment fragment = new CancelledServicesFragment();
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
        View view = inflater.inflate(R.layout.fragment_active_services, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        setMenuBar();
        return view;
    }

    private void initialize() {
        context = getContext();
        dashboardActivity = (ServicesActivity) context;
        if (context != null)
            progressDialog = Utils.showProgressDWithSpinner(progressDialog, context);
        servicesPresenter = new ServicesPresenter(this);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Context.MODE_PRIVATE);
        int clientId = preferences.getInt("clientid", -1);
        if (clientId != 0 && clientId != -1) {
            servicesPresenter.getCLientProducts(clientId);
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
    public void getMyServices(GetClientProductsCallback getClientProductsCallback) {
        if (getClientProductsCallback != null) {
            ProductPojo arrayList = getClientProductsCallback.getProductts();
            ArrayList<ProductDetailPojo> productDeailArrayList = getClientProductsCallback.getProductts().getProductDeailArrayList();
            ArrayList<ProductDetailPojo> cancelledServiceArrayList = new ArrayList<ProductDetailPojo>();


            for (ProductDetailPojo productDetailPojo : productDeailArrayList) {
                String status = productDetailPojo.getStatus();
                if (status.equals("Cancelled"))
                    cancelledServiceArrayList.add(productDetailPojo);
            }
            if (cancelledServiceArrayList.size() > 0) {
                myRecyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                mAdapter = new ServicesAdapter(cancelledServiceArrayList, getContext(), dashboardActivity);
                myRecyclerView.setAdapter(mAdapter);
            } else if (cancelledServiceArrayList.isEmpty()) {
                if (myRecyclerView != null && emptyView != null) {
                    myRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText(context.getResources().getString(R.string.no_cancelled_services));
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
        if (myRecyclerView != null && emptyView != null) {
            myRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            if (context != null)
                emptyView.setText(context.getResources().getString(R.string.no_cancelled_services));
        }
//        if(context!=null&&!errorMessage.isEmpty())
//            Utils.showToast(context,errorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                    searchView.setQueryHint(context.getResources().getString(R.string.search_services));
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
                        if (emptyView != null && mAdapter != null) {
                            emptyView.setVisibility(View.GONE);
                            mAdapter.filter(newText, emptyView);
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
