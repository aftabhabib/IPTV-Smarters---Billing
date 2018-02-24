package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderNewServicesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderNewServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderNewServicesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.tv_shopping_cart)
    TextView tvShoppingCart;
    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;

    Unbinder unbinder;
    private Context context;
    private String clientEmail;
    Typeface fontOPenSansBold;

    ProgressDialog progressDialog;


    public OrderNewServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderNewServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderNewServicesFragment newInstance(String param1, String param2) {
        OrderNewServicesFragment fragment = new OrderNewServicesFragment();
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
        View view = inflater.inflate(R.layout.fragment_order_new_services, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        context = getContext();
        progressDialog = new ProgressDialog(getActivity());
        if(context!=null) {
            progressDialog.setMessage(context.getResources().getString(R.string.please_wait_invoice));
            progressDialog.show();
        }
        setHeaderFont();
        SharedPreferences pref = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        clientEmail = pref.getString(AppConst.PREF_EMAIL_WHMCS, "");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (!clientEmail.isEmpty() && clientEmail != null) {
            mWebView.loadUrl(AppConst.BASE_URL_WHMCS + AppConst.SUB_FOLDERS +"/"+AppConst.CART_PAGE_URL+"loginemail="+
                    clientEmail+"&api_username="+AppConst.API_USERNAME+"&gotourl="+AppConst.CART_PAGE_URL);
            mWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if(progress == 100) {
                        progressDialog.dismiss();
//                        if (pbLoader != null)
//                            pbLoader.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        else{
            mWebView.loadUrl(AppConst.BASE_URL_WHMCS + AppConst.SUB_FOLDERS +"/"+AppConst.CART_PAGE_URL);
            progressDialog.dismiss();
        }
    }

    private void setHeaderFont() {
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        tvShoppingCart.setTypeface(fontOPenSansBold);
    }


    @OnClick(R.id.iv_back_press)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_press:
                Intent dashBoadIntent = new Intent(getContext(), DashboardActivity.class);
                startActivity(dashBoadIntent);
                break;
        }
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
}
