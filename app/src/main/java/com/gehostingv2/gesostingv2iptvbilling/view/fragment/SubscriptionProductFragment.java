package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.crashlytics.android.Crashlytics;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;

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
 * Use the {@link SubscriptionProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscriptionProductFragment extends Fragment implements View.OnKeyListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String Status = "";
    String registrationDate = "";
    String nextDueDate = "";
    String recurringAmount = "";
    String billingCycle = "";
    String paymentMethod = "";
    String firstTimePayment = "";

    String macAddress = "";
    String productName = "";
    String groupName = "";
    String userName = "";
    String password = "";
    String domain = "";
    String serviceId = "";
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.tv_manage_products)
    TextView tvManageProducts;
    @BindView(R.id.view_line_my_services_ticket)
    View viewLineMyServicesTicket;
    @BindView(R.id.rl_manage_products)
    RelativeLayout rlManageProducts;
    @BindView(R.id.tv_alert_message_manage_products)
    TextView tvAlertMessageManageProducts;
    @BindView(R.id.iv_subscription_product)
    ImageView ivSubscriptionProduct;
    @BindView(R.id.tv_iptv_services_name)
    TextView tvIptvServicesName;
    @BindView(R.id.view_line_iptv_manage_product)
    View viewLineIptvManageProduct;
    @BindView(R.id.tv_iptv_name)
    TextView tvIptvName;
    @BindView(R.id.iv_iptv_status)
    TextView ivIptvStatus;
    @BindView(R.id.rl_iptv_subscription)
    RelativeLayout rlIptvSubscription;
    @BindView(R.id.cv_iptv)
    CardView cvIptv;
    @BindView(R.id.tv_product_detail)
    TextView tvProductDetail;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.tv_product_value)
    TextView tvProductValue;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_status_value)
    TextView tvStatusValue;
    @BindView(R.id.tv_registartion_date)
    TextView tvRegistartionDate;
    @BindView(R.id.tv_registration_date_value)
    TextView tvRegistrationDateValue;
    @BindView(R.id.tv_next_due_date)
    TextView tvNextDueDate;
    @BindView(R.id.tv_next_due_date_value)
    TextView tvNextDueDateValue;
    @BindView(R.id.tv_recurring_amount)
    TextView tvRecurringAmount;
    @BindView(R.id.tv_recurring_amount_value)
    TextView tvRecurringAmountValue;
    @BindView(R.id.tv_billing_cycle)
    TextView tvBillingCycle;
    @BindView(R.id.tv_billing_cycle_value)
    TextView tvBillingCycleValue;
    @BindView(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.tv_payment_method_value)
    TextView tvPaymentMethodValue;
    @BindView(R.id.tv_first_time_payment)
    TextView tvFirstTimePayment;
    @BindView(R.id.tv_first_time_payment_value)
    TextView tvFirstTimePaymentValue;
    @BindView(R.id.rl_product_detail)
    RelativeLayout rlProductDetail;
    @BindView(R.id.cv_product_detail)
    CardView cvProductDetail;
//    @BindView(R.id.tv_password)
//    TextView tvPassword;
//    @BindView(R.id.tv_password_value)
//    TextView tvPasswordValue;
    CardView cvIptvDetail;
    @BindView(R.id.sv_manage_products)
    ScrollView svManageProducts;
    Unbinder unbinder;
    @BindView(R.id.bt_resuest_canellation)
    Button btResuestCanellation;
//    @BindView(R.id.tv_user_id_value)
//    TextView tvUserIdValue;
//    @BindView(R.id.tb_show_password)
//    ToggleButton tbShowPassword;
//    @BindView(R.id.tv_app_key_value)
//    TextView tvAppKeyValue;
//    @BindView(R.id.expandableLayout1)
//    ExpandableLinearLayout expandableLayout1;
//    @BindView(R.id.rl_reply_ticket)
//    RelativeLayout rlExapndLayout;
//    @BindView(R.id.iv_arrow_downward)
//    ImageView ivDownarrow;

    @BindView(R.id.rl_cancellation_request)
    RelativeLayout rlCancellationRequest;
    //    @BindView(R.id.tv_whmcs_sip_credit_value)
//    TextView tvwhmcsCreditValue;
    @BindView(R.id.iv_back_press)
    ImageView ivBackPress;
    private Context context;
    private Typeface fontOPenSansBold;
    private Toolbar toolbar;
    private SearchView searchView;

    private FragmentActivity myContext;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SubscriptionProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscriptionProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscriptionProductFragment newInstance(String param1, String param2) {
        SubscriptionProductFragment fragment = new SubscriptionProductFragment();
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
        View view = inflater.inflate(R.layout.fragment_subscription_product, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        setMenuBar();
        return view;
    }

    private void setMenuBar() {
        setHasOptionsMenu(true);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    private void initialize() {
        context = getContext();
        fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        tvManageProducts.setTypeface(fontOPenSansBold);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            productName = bundle.getString("product_name", "");
            Status = bundle.getString("status", "");
            registrationDate = bundle.getString("reg_date", "");
            nextDueDate = bundle.getString("next_due_date", "");
            recurringAmount = bundle.getString("recurring_amount", "");
            billingCycle = bundle.getString("billing_cycle", "");
            paymentMethod = bundle.getString("payment_method", "");
            firstTimePayment = bundle.getString("first_time_payment", "");
            macAddress = bundle.getString("mac_address", "");
            groupName = bundle.getString("group_name", "");
            serviceId = bundle.getString("service_id", "");
            userName = bundle.getString("user_name", "");
            password = bundle.getString("password", "");
            domain = bundle.getString("domain", "");
        }
        setTextView(productName, Status, registrationDate, nextDueDate, recurringAmount,
                billingCycle, paymentMethod, firstTimePayment, userName, password, macAddress, groupName, domain, serviceId);

        cvIptv.setOnKeyListener(this);
//        keylistnerEffect();
    }

    private void keylistnerEffect() {
        cvIptv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
//                    rlCancellationRequest.requestFocus();
//
                    if (keyCode == android.view.KeyEvent.KEYCODE_DPAD_RIGHT) {

                        return true;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setTextView(String productName,
                             String status,
                             String registrationDate,
                             String nextDueDate,
                             String recurringAmount,
                             String billingCycle,
                             String paymentMethod,
                             String firstTimePayment,
                             String userName,
                             String password,
                             String macAddress,
                             String groupName, String domain, String serviceId) {
        if (!productName.equals("")) {
            tvProductValue.setText(productName);
            tvIptvServicesName.setText(productName);
        }
        if (!status.equals("")) {
            tvStatusValue.setText(status);
            ivIptvStatus.setText(status.toUpperCase());
            if (status.equalsIgnoreCase("pending") && context != null) {
//                ivIptvStatus.setBackgroundResource(R.color.service_status_pending);
//                ivIptvStatus.setTextColor(getResources().getColor(R.color.service_status_pending));
                ivIptvStatus.setTextColor(ContextCompat.getColor(context, R.color.service_status_pending));
            } else if (status.equalsIgnoreCase("active") && context != null) {
//                ivIptvStatus.setBackgroundResource(R.color.service_status_active);
//                ivIptvStatus.setTextColor(getResources().getColor(R.color.service_status_active));
                ivIptvStatus.setTextColor(ContextCompat.getColor(context, R.color.service_status_active));
            } else if (status.equalsIgnoreCase("cancelled") && context != null) {
//                ivIptvStatus.setBackgroundResource(R.color.service_status_cancelled);
//                ivIptvStatus.setTextColor(getResources().getColor(R.color.service_status_cancelled));
                ivIptvStatus.setTextColor(ContextCompat.getColor(context, R.color.service_status_cancelled));
            } else if (status.equalsIgnoreCase("suspended") && context != null) {
//                ivIptvStatus.setBackgroundResource(R.color.service_status_suspended);
//                ivIptvStatus.setTextColor(getResources().getColor(R.color.service_status_suspended));
                ivIptvStatus.setTextColor(ContextCompat.getColor(context, R.color.service_status_suspended));
            } else if (status.equalsIgnoreCase("terminated") && context != null) {
//                ivIptvStatus.setBackgroundResource(R.color.service_status_terminated);
//                ivIptvStatus.setTextColor(getResources().getColor(R.color.service_status_terminated));
                ivIptvStatus.setTextColor(ContextCompat.getColor(context, R.color.service_status_terminated));
            }
        }
        if (!registrationDate.equals(""))
            tvRegistrationDateValue.setText(registrationDate);
        if (!nextDueDate.equals("") && !billingCycle.equals("One Time"))
            tvNextDueDateValue.setText(nextDueDate);
        else if (!nextDueDate.equals("") && billingCycle.equals("One Time")) {
            tvNextDueDateValue.setText("-");
        }
        SharedPreferences loginPreferencesEmail = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        String currencyPrefix = loginPreferencesEmail.getString(AppConst.PREF_CURRENCIES_PREFIX_WHMCS, "");
        String currencySuffix = loginPreferencesEmail.getString(AppConst.PREF_CURRENCIES_SUFFIX_WHMCS, "");

        if (!recurringAmount.equals(""))
            tvRecurringAmountValue.setText(currencyPrefix + recurringAmount + " "+currencySuffix);
        if (!billingCycle.equals(""))
            tvBillingCycleValue.setText(billingCycle);
        if (!paymentMethod.equals(""))
            tvPaymentMethodValue.setText(paymentMethod);
        if (!firstTimePayment.equals(""))
            tvFirstTimePaymentValue.setText(currencyPrefix + firstTimePayment + " "+currencySuffix);


        if (!groupName.equals(""))
            tvIptvName.setText(groupName);
//        if (!userName.isEmpty() && !userName.equals("")) {
//            tvUserIdValue.setText(userName);
//        } else if (userName.isEmpty()) {
//            tvUserIdValue.setText("-");
//        }
//        if (!password.isEmpty() && !password.equals("")) {
//            tvPasswordValue.setText(password);
//        } else if (password.isEmpty()) {
//            tvPasswordValue.setText("-");
//            tbShowPassword.setVisibility(View.GONE);
//        }
//        if (!domain.isEmpty() && !domain.equals("")) {
//            tvAppKeyValue.setText(domain);
//        } else if (domain.isEmpty()) {
//            tvAppKeyValue.setText("-");
//        }
        if (!status.equals("Active")) {
//            rlExapndLayout.setVisibility(View.GONE);
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

    @OnClick({R.id.bt_resuest_canellation})  //  R.id.tb_show_password,,R.id.rl_reply_ticket
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_resuest_canellation:
                Intent intent = new Intent(getContext(), DashboardActivity.class);
                startActivity(intent);
                break;
//            case R.id.tb_show_password:
////                if (tbShowPassword.getText().equals("HIDE")) {
////                    // hide password
//////                    tvPasswordValue.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
////                } else if (tbShowPassword.getText().equals("SHOW")) {
////                    // show password
//////                    tvPasswordValue.setTransformationMethod(PasswordTransformationMethod.getInstance());
////                }
//                break;
//            case R.id.rl_reply_ticket:
//                if (expandableLayout1.isExpanded()) {
//                    expandableLayout1.collapse();
//                    ivDownarrow.setImageResource(R.drawable.down_arrow_white);
//                } else {
//                    expandableLayout1.expand();
//                    ivDownarrow.setImageResource(R.drawable.up_arrow_white);
//                }
//                break;

        }

    }


    @OnClick(R.id.iv_back_press)
    public void onViewClicked() {
        myContext.onBackPressed();
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == android.view.KeyEvent.ACTION_DOWN) {
            rlCancellationRequest.requestFocus();

            if (keyCode == android.view.KeyEvent.KEYCODE_DPAD_RIGHT) {

                return true;
            }
            return true;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (menu != null)
            menu.clear();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
