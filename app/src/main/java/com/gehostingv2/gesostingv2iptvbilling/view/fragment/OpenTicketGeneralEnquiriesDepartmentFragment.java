package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.LoginWHMCSActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.TicketsActivity;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetDepartmentCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetOpenTicketCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.DepartmentDetailPojo;
import com.gehostingv2.gesostingv2iptvbilling.presenter.OpenTicketPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.OpenTicketInterface;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.HashMap;

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
 * Use the {@link OpenTicketGeneralEnquiriesDepartmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenTicketGeneralEnquiriesDepartmentFragment extends Fragment implements OpenTicketInterface {  //, View.OnKeyListener
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
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_value)
    EditText tvNameValue;
    @BindView(R.id.tv_mail)
    TextView tvMail;
    @BindView(R.id.tv_mail_value)
    EditText tvMailValue;
    @BindView(R.id.tv_subject)
    TextView tvSubject;
    @BindView(R.id.tv_subject_value)
    EditText tvSubjectValue;
    @BindView(R.id.tv_departement)
    TextView tvDepartement;
    @BindView(R.id.sp_department_value)
    Spinner spDepartmentValue;
    @BindView(R.id.tv_related_services)
    TextView tvRelatedServices;
    @BindView(R.id.sp_related_value)
    Spinner spRelatedValue;
    @BindView(R.id.tv_priority)
    TextView tvPriority;
    @BindView(R.id.sp_priority)
    Spinner spPriority;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.rl_open_support_ticket)
    RelativeLayout rlOpenSupportTicket;
    @BindView(R.id.cv_manage_domain_header)
    CardView cvManageDomainHeader;
    Unbinder unbinder;
    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.ll_close)
    LinearLayout llClose;

    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    private FragmentActivity myContext;

    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;


    private OpenTicketPresenter openTicketPresenter;
    private Context context;
    int clientId;
    ArrayList<DepartmentDetailPojo> departmentDetailPojosList;
    ArrayList<String> departmetnName = new ArrayList<>();
    String SelectedDepartment;
    String relatedServives;
    String priority;
    int departmetnId = -1;
    Bundle bundleflag;
    private String departIdValue;

    String[] spinnerArray;
    HashMap<Integer, String> spinnerMap;
    private Typeface fontOPenSansBold;

    ProgressDialog dialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OpenTicketGeneralEnquiriesDepartmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenTicketGeneralEnquiriesDepartmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenTicketGeneralEnquiriesDepartmentFragment newInstance(String param1, String param2) {
        OpenTicketGeneralEnquiriesDepartmentFragment fragment = new OpenTicketGeneralEnquiriesDepartmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_open_ticket_general_enquiries_department, container, false);
        unbinder = ButterKnife.bind(this, view);


        intialize();
        return view;
    }

    private void intialize() {


        context = getContext();


        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loader_submit_ticket));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


        openTicketPresenter = new OpenTicketPresenter(this);
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        tvOpenTicket.setTypeface(fontOPenSansBold);
        SharedPreferences pref = getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        clientId = pref.getInt(AppConst.CLIENT_ID, -1);
        if (clientId == -1 || clientId == 0) {
            Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
            startActivity(intentLogin);
        } else {
            if (dialog != null)
                dialog.show();
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
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
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

    @Override
    public void getSupportDepartmet(GetDepartmentCallback getDepartmentCallback) {
        if (getDepartmentCallback != null
                && getDepartmentCallback.getResult().equals("success") && getDepartmentCallback != null) {
//            if(departmentDetailPojosList!=null) {

            departmentDetailPojosList = getDepartmentCallback.getDepartments().getDepartment();

            if (departmentDetailPojosList != null) {
                for (DepartmentDetailPojo departmentDetailPojo : departmentDetailPojosList) {
                    String departmtId = departmentDetailPojo.getDepartmentId();
                    departmetnName.add(departmentDetailPojo.getDepartmentName());
                }


                spinnerArray = new String[departmentDetailPojosList.size()];
                spinnerMap = new HashMap<Integer, String>();
                for (int i = 0; i < departmentDetailPojosList.size(); i++) {
                    spinnerMap.put(i, String.valueOf(departmentDetailPojosList.get(i).getDepartmentId()));
                    spinnerArray[i] = String.valueOf(departmentDetailPojosList.get(i).getDepartmentName());
                }


                if (getContext() != null && spDepartmentValue != null) {  //&& bundleflag!=null

                    ArrayAdapter departmentAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, spinnerArray); //
                    departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDepartmentValue.setAdapter(departmentAdapter);


                    ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.priority, android.R.layout.simple_spinner_item);
                    priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPriority.setAdapter(priorityAdapter);

                    spDepartmentValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                            SelectedDepartment = spDepartmentValue.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
                    spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                            priority = spPriority.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }
//            }
        }
    }

    @Override
    public void OpenSupportDeartment(GetOpenTicketCallback getOpenTicketCallback) {
        if (getOpenTicketCallback.getResult().equals("success")) {
            Toast.makeText(getContext(), context.getResources().getString(R.string.ticket_submitted), Toast.LENGTH_SHORT).show();

            if (context != null)
                callActivity();
//            Fragment ticketFragment = new TicketsFragment();
//            this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
//                    android.R.anim.fade_out)
//                    .replace(R.id.fl_open_ticket, ticketFragment, AppConst.TAG_MY_TICKETS)
//                    .addToBackStack(AppConst.TAG_MY_TICKETS)
//                    .commit();
        }
    }

    private void callActivity() {
        Intent intentTciket = new Intent(context, TicketsActivity.class);
        startActivity(intentTciket);
    }

    @Override
    public void atStart() {
        if (dialog != null)
            dialog.show();
//
//        if (pbLoader != null)
//            pbLoader.setVisibility(View.VISIBLE);

    }

    @Override
    public void onFinish() {
        if (dialog != null)
            dialog.dismiss();
//
//        if (pbLoader != null)
//            pbLoader.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFailed(String errorMessage) {
        if (context != null && !errorMessage.isEmpty()) {
            Utils.showToast(context, context.getResources().getString(R.string.network_error));
        }

    }

    @OnClick({R.id.bt_submit, R.id.bt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                int selectedDepartmentId;
//                String name = tvNameValue.getText().toString();
//                String email = tvMailValue.getText().toString();
                String message = etMessage.getText().toString();
                String subject = tvSubjectValue.getText().toString();
//                boolean emailFlag = Utils.isValidEmail(email);
                if (subject.isEmpty()) {
                    Toast.makeText(getContext(), context.getResources().getString(R.string.enter_subject_text), Toast.LENGTH_SHORT).show();
                } else if (!subject.isEmpty() && message.isEmpty()) {
                    Toast.makeText(getContext(), context.getResources().getString(R.string.enter_message), Toast.LENGTH_SHORT).show();
                } else if (!subject.isEmpty() && !message.isEmpty()) {

                    SelectedDepartment = spDepartmentValue.getSelectedItem().toString();
                    priority = spPriority.getSelectedItem().toString();
                    String name1 = spDepartmentValue.getSelectedItem().toString();
                    selectedDepartmentId = Integer.parseInt(spinnerMap.get(spDepartmentValue.getSelectedItemPosition()));
                    SharedPreferences pref = getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
                    int clientId = pref.getInt(AppConst.CLIENT_ID, -1);
                    if (clientId == -1 || clientId == 0) {
                        Intent intentLogin = new Intent(context, LoginWHMCSActivity.class);
                        startActivity(intentLogin);
                    } else {
                        openTicketPresenter.openSupportDepartment(selectedDepartmentId, clientId, message, subject, priority);
                    }
                }
                break;
            case R.id.bt_cancel:
                final InputMethodManager imm2 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                Intent intentHome = new Intent(getActivity(), DashboardActivity.class);
                startActivity(intentHome);

//                Fragment homeFragment = new HomeFragment();
//                this.getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,
//                        android.R.anim.fade_out)
//                        .replace(R.id.fl_open_ticket, homeFragment, AppConst.TAG_HOME)
//                        .addToBackStack(AppConst.TAG_HOME)
//                        .commit();
                break;
        }
    }
//
//    @Override
//    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//            if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
////                            holder.ivServicesListItemForwardArrow.requestFocus();
//                return true;
//            }
//        }
//        return false;
//    }

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
