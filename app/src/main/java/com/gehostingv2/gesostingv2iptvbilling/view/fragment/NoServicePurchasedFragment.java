package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
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
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoServicePurchasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoServicePurchasedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    @BindView(R.id.tv_open_ticket)
    TextView tvOpenTicket;
    @BindView(R.id.v_open_ticket)
    View vOpenTicket;
    @BindView(R.id.rl_open_ticket)
    RelativeLayout rlOpenTicket;
    @BindView(R.id.tv_reasons)
    TextView tvReasons;
    @BindView(R.id.tv_open_ticket_brief)
    TextView tvOpenTicketBrief;
    @BindView(R.id.tv_open_ticket_brief2)
    TextView tvOpenTicketBrief2;
    @BindView(R.id.tv_open_ticket_brief3)
    TextView tvOpenTicketBrief3;
    @BindView(R.id.rl_open_ticket_dept)
    RelativeLayout rlOpenTicketDept;
    @BindView(R.id.cv_manage_domain_header)
    CardView cvManageDomainHeader;
    @BindView(R.id.cv_manage_domain_header2)
    CardView cvManageDomainHeader2;

    @BindView(R.id.tv_purchase)
    TextView tvPurchase;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    Unbinder unbinder;
    private Context context;

    private Typeface fontOPenSansBold;
    private String clientEmail;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NoServicePurchasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoServicePurchasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoServicePurchasedFragment newInstance(String param1, String param2) {
        NoServicePurchasedFragment fragment = new NoServicePurchasedFragment();
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
        View view = inflater.inflate(R.layout.fragment_no_service_purchased, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        context = getContext();
        setHeaderFont();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_purchase,R.id.iv_back_press})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_purchase:
                cvManageDomainHeader.setVisibility(View.GONE);
                mWebView.setWebViewClient(new WebViewClient());
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.loadUrl(AppConst.BASE_URL_WHMCS + "/cart.php");

                loadUrlToPurchase();
                break;
            case R.id.iv_back_press:
                Intent dashBoadIntent = new Intent(getContext(), DashboardActivity.class);
                startActivity(dashBoadIntent);
                break;
        }
    }

    private void loadUrlToPurchase() {
        context = getContext();
        if(pbLoader!=null) {
            pbLoader.setVisibility(View.VISIBLE);
            pbLoader.getIndeterminateDrawable().setColorFilter(0x004d91, android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        SharedPreferences pref = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        clientEmail = pref.getString(AppConst.PREF_EMAIL_WHMCS, "");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (!clientEmail.isEmpty() && clientEmail != null) {
            mWebView.loadUrl(AppConst.BASE_URL_WHMCS + AppConst.SUB_FOLDERS +"/"+AppConst.CART_PAGE_URL+"loginemail="+
                    clientEmail+"&api_username="+AppConst.API_USERNAME+"&gotourl="+AppConst.CART_PAGE_URL);
            mWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if(progress == 100)
                        if(pbLoader!=null)
                            pbLoader.setVisibility(View.INVISIBLE);
                }
            });
        }
        else{
            mWebView.loadUrl(AppConst.BASE_URL_WHMCS + AppConst.SUB_FOLDERS +"/"+AppConst.CART_PAGE_URL);
        }
    }
    private void setHeaderFont() {
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        tvOpenTicket.setTypeface(fontOPenSansBold);
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






//        mWebView.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//
//            public void onPageFinished(WebView view, String url) {
//                Toast.makeText(getContext(),"Page Finished",Toast.LENGTH_LONG).show();
//            }
//
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(getContext(),"Error", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mWebView.loadUrl(AppConst.BASE_URL_WHMCS+"/cart.php");
//
//
//


//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.loadUrl("http://www.example.com");   //add http at front
//        mWebView.getSettings().setUseWideViewPort(true);
//        mWebView.getSettings().setLoadWithOverviewMode(true);


//        mWebView.loadUrl("https://google.com");
//
//        // Enable Javascript
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        // Force links and redirects to open in the WebView instead of in a browser
//        mWebView.setWebViewClient(new WebViewClient());


//
//        String url = "http://appprice.appday.de";
//
//        WebView wv = new WebView(context);
//// or
//// WebView wv = (WebView)findViewById(R.id.my_webview);
//
//        wv.loadUrl(url);
//        wv.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

