package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
 * {@link InvoiceViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InvoiceViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceViewFragment extends Fragment {

    @BindView(R.id.tv_invoice_detail)
    TextView tvInvoiceDeatil;

    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    @BindView(R.id.webview)
    WebView mWebView;
    Unbinder unbinder;
    private Context context;
    private String clientEmail;
    private String invoiceId = "";
    private FragmentActivity myContext;
    Typeface fontOPenSansBold;
    ProgressDialog progressDialog;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InvoiceViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceViewFragment newInstance(String param1, String param2) {
        InvoiceViewFragment fragment = new InvoiceViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_invoice_view, container, false);
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
        final Bundle bundle = getArguments();
        if (bundle != null)
            invoiceId = bundle.getString("invoice_id");
        SharedPreferences pref = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        if (pref != null)
            clientEmail = pref.getString(AppConst.PREF_EMAIL_WHMCS, "");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (invoiceId!=null && !clientEmail.isEmpty() && clientEmail != null) {
            mWebView.loadUrl(AppConst.BASE_URL_WHMCS + AppConst.SUB_FOLDERS + "/" + AppConst.INVOICE_PAGE_URL + "?id=" + invoiceId + "&loginemail=" +
                    clientEmail + "&api_username=" + AppConst.API_USERNAME + "&gotourl=" + AppConst.INVOICE_PAGE_URL + "?id=" + invoiceId);
            mWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if(progress == 100) {
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            mWebView.loadUrl(AppConst.BASE_URL_WHMCS + AppConst.SUB_FOLDERS + "/" + AppConst.INVOICE_PAGE_URL + "?id=" + invoiceId);
            progressDialog.dismiss();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void setHeaderFont() {
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        tvInvoiceDeatil.setTypeface(fontOPenSansBold);
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

    public class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
