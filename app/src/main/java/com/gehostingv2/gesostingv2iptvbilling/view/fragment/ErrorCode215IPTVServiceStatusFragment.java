package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ErrorCode215IPTVServiceStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ErrorCode215IPTVServiceStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ErrorCode215IPTVServiceStatusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    @BindView(R.id.tv_reasons)
    TextView errorCodeMessage;
    Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

    public ErrorCode215IPTVServiceStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ErrorCode215IPTVServiceStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ErrorCode215IPTVServiceStatusFragment newInstance(String param1, String param2) {
        ErrorCode215IPTVServiceStatusFragment fragment = new ErrorCode215IPTVServiceStatusFragment();
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
        View view = inflater.inflate(R.layout.fragment_error_code215_iptvservice_status, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        String iptvServiceStatus = "";
        Bundle bundleIPTVServiceStatus = getArguments();
        if (bundleIPTVServiceStatus != null)
            iptvServiceStatus = bundleIPTVServiceStatus.getString(AppConst.IPTV_SERVICE_STATUS);
        if (iptvServiceStatus != null && !iptvServiceStatus.isEmpty())
            errorCodeMessage.setText("Error code 215 occur." + "Your IPTV Service status is " +
                    ""+iptvServiceStatus+ ". Please contact to your administrator.");
        else
            errorCodeMessage.setText("Error code 215 occur. Please contact to your administrator.");
    }

    @OnClick(R.id.iv_back_press)
    public void onViewClicked() {
        Intent dashBoardIntent = new Intent(getContext(), DashboardActivity.class);
        startActivity(dashBoardIntent);
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
