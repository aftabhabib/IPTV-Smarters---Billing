package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWHMCSActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetDepartmentCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetOpenTicketCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.DepartmentDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.OpenTicketPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.OpenTicketInterface;
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
 * Use the {@link OpenTicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenTicketFragment extends Fragment implements OpenTicketInterface, View.OnKeyListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.tv_open_ticket)
    TextView tvOpenTicket;
    @BindView(R.id.v_open_ticket)
    View vOpenTicket;
    @BindView(R.id.rl_open_ticket)
    RelativeLayout rlOpenTicket;
    @BindView(R.id.tv_open_ticket_brief)
    TextView tvOpenTicketBrief;
    @BindView(R.id.iv_email)
    ImageView ivEmail;
    @BindView(R.id.tv_first_dept_name)
    TextView tvFirstDeptName;
//    @BindView(R.id.ll_first_department)
//    LinearLayout llFirstDepartment;
    @BindView(R.id.rl_first)
    RelativeLayout rlFirst;
    @BindView(R.id.iv_email_second)
    ImageView ivEmailSecond;
    @BindView(R.id.tv_second_dept_name)
    TextView tvSecondDeptName;
//    @BindView(R.id.ll_second_department)
//    LinearLayout llSecondDepartment;
    @BindView(R.id.rl_second)
    RelativeLayout rlSecond;
    @BindView(R.id.rl_open_ticket_dept)
    RelativeLayout rlOpenTicketDept;
    @BindView(R.id.cv_manage_domain_header)
    CardView cvManageDomainHeader;


    @BindView(R.id.rl_first_dept)
    RelativeLayout rlFirsDept;
    @BindView(R.id.rl_second_dept)
    RelativeLayout rlSecondDept;
    Unbinder unbinder;
    Bundle b;

    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    private FragmentActivity myContext;


    private OpenTicketPresenter openTicketPresenter;
    private  Context context;
    private int clientId;
    Bundle bundleFlag;
    private Typeface fontOPenSansBold;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OpenTicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenTicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenTicketFragment newInstance(String param1, String param2) {
        OpenTicketFragment fragment = new OpenTicketFragment();
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
        View view = inflater.inflate(R.layout.fragment_open_ticket, container, false);
        unbinder = ButterKnife.bind(this, view);
        intialize();
        return view;
    }

    private void intialize() {
        context =getContext();
        openTicketPresenter = new OpenTicketPresenter(this);
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        tvOpenTicket.setTypeface(fontOPenSansBold);

        SharedPreferences pref = getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        clientId = pref.getInt(AppConst.CLIENT_ID, -1);
        if (clientId == -1 || clientId == 0) {
            Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
            startActivity(intentLogin);
        } else {
            openTicketPresenter.getSupportDepartmetn(clientId);
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
        myContext = (FragmentActivity) getActivity();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @OnClick(R.id.iv_back_press)
    public void onViewClicked() {
        myContext.onBackPressed();
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

    @OnClick({R.id.rl_first, R.id.rl_second, R.id.rl_first_dept,R.id.rl_second_dept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_first:
                Fragment openTicketGeneralEnquiriesDepartmentFragment = new OpenTicketGeneralEnquiriesDepartmentFragment();

                if(bundleFlag!=null)
                    openTicketGeneralEnquiriesDepartmentFragment.setArguments(bundleFlag);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.fl_open_ticket, openTicketGeneralEnquiriesDepartmentFragment, AppConst.TAG_OPEN_TICKET_GEN_ENQ)
                        .addToBackStack(AppConst.TAG_OPEN_TICKET_GEN_ENQ)
                        .commit();
                break;
            case R.id.rl_second:
//                Fragment openTicketSupportFragment = new OpenTicketSupportFragment();
//                if(bundleFlag!=null)
//                    openTicketSupportFragment.setArguments(bundleFlag);
//                this.getFragmentManager().beginTransaction()
//                        .replace(R.id.fl_open_ticket, openTicketSupportFragment, AppConst.TAG_MY_INVOICES)
//                        .addToBackStack(AppConst.TAG_MY_INVOICES)
//                        .commit();
                break;
            //rl_first_dept and rl_second_dept are used for the keyboard listoners

            case R.id.rl_first_dept:
                Fragment openTicketGeneralEnquiriesDepartmentFragment1 = new OpenTicketGeneralEnquiriesDepartmentFragment();

                if(bundleFlag!=null)
                    openTicketGeneralEnquiriesDepartmentFragment1.setArguments(bundleFlag);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.fl_open_ticket, openTicketGeneralEnquiriesDepartmentFragment1, AppConst.TAG_OPEN_TICKET_GEN_ENQ)
                        .addToBackStack(AppConst.TAG_OPEN_TICKET_GEN_ENQ)
                        .commit();
                break;
            case R.id.rl_second_dept:
//                Fragment openTicketSupportFragment1 = new OpenTicketSupportFragment();
//                if(bundleFlag!=null)
//                    openTicketSupportFragment1.setArguments(bundleFlag);
//                this.getFragmentManager().beginTransaction()
//                        .replace(R.id.fl_open_ticket, openTicketSupportFragment1, AppConst.TAG_MY_INVOICES)
//                        .addToBackStack(AppConst.TAG_MY_INVOICES)
//                        .commit();
                break;
        }
    }

    @Override
    public void getSupportDepartmet(GetDepartmentCallback getDepartmentCallback) {
        if(getDepartmentCallback!=null&&getDepartmentCallback.getResult().equals("success")) {
            ArrayList<DepartmentDetailPojo> departmentDetailPojosList = getDepartmentCallback.getDepartments().getDepartment();

            for (DepartmentDetailPojo departmentDetailPojo : departmentDetailPojosList) {
                String departmtId = departmentDetailPojo.getDepartmentId();
                String deptartId =  departmentDetailPojo.getDepartmentId();
                bundleFlag = new Bundle();
                if (departmtId.equals("1")) {
                    bundleFlag.putInt("dept_id_flag",1);
                    bundleFlag.putString("dept_id_value", deptartId);
                    tvFirstDeptName.setText(departmentDetailPojo.getDepartmentName().toUpperCase()+" DEPARTMENT");
                } else if (departmtId.equals("2")) {
                    bundleFlag.putInt("dept_id_flag",2);
                    bundleFlag.putString("dept_id_value", deptartId);
                    tvSecondDeptName.setText(departmentDetailPojo.getDepartmentName().toUpperCase()+" DEPARTMENT");
                }
            }
        }
    }

    @Override
    public void OpenSupportDeartment(GetOpenTicketCallback getOpenTicketCallback) {

    }

    @Override
    public void atStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailed(String errorMessage) {
        if (context != null && !errorMessage.isEmpty()) {
            Utils.showToast(context, getResources().getString(R.string.network_error));
        }

    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
//                            holder.ivServicesListItemForwardArrow.requestFocus();
                return true;
            }
        }
        return false;
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
