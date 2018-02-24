package com.gehostingv2.gesostingv2iptvbilling.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LoginWHMCSCallback;
import com.gehostingv2.gesostingv2iptvbilling.presenter.LoginWHMCSPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ForgetPasswordActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LoginWHMCSInterface;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.MODE_PRIVATE;
import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils.isValidEmail;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginWHMCSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginWHMCSFragment extends Fragment implements LoginWHMCSInterface {


    @BindView(R.id.iv_logo)
    ImageView yourLogioTV;
    @BindView(R.id.tv_enter_credentials)
    TextView loginTV;
    @BindView(R.id.et_email)
    EditText emailIdET;
    @BindView(R.id.et_password)
    EditText passwordET;
    @BindView(R.id.et_submit)
    Button loginBT;
    @BindView(R.id.tv_forget_password)
    TextView forgetPassword;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    @BindView(R.id.cb_remember_me)
    CheckBox cbRememberMe;
    @BindView(R.id.tv_signup)
    TextView tvSignup;
    Unbinder unbinder;

    private LoginWHMCSPresenter loginPresenter;

    private Context context;
    private String emailId;
    private String password;
    private boolean emailCheck;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPref;
    private SharedPreferences loginPreferences;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private View layout;

    private static final String TAG = DashboardActivity.class.getSimpleName();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginWHMCSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginWHMCSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginWHMCSFragment newInstance(String param1, String param2) {
        LoginWHMCSFragment fragment = new LoginWHMCSFragment();
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
        View view = inflater.inflate(R.layout.fragment_whmcslogin, container, false);
        unbinder = ButterKnife.bind(this, view);

        initialize();
        return view;
    }

    private void initialize() {
        context = getContext();
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        sharedPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("api_username", AppConst.API_USERNAME);
        editor.putString("api_password", AppConst.API_PASSWORD);
        editor.commit();
        emailIdET.requestFocus();
        emailId = emailIdET.getText().toString();
        password = passwordET.getText().toString();
        loginPresenter = new LoginWHMCSPresenter(this);
        loginPreferences = getActivity().getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        loginPreferencesSharedPref = getActivity().getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        int clientId = loginPreferencesSharedPref.getInt(AppConst.PREF_CLIENT_ID, -1);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferencesSharedPref.getBoolean(AppConst.PREF_SAVE_LOGIN_WHMCS, false);
        if (saveLogin) {
            loginPreferences.getInt(AppConst.PREF_CLIENT_ID, -1);
            loginPreferences.getString(AppConst.PREF_PASSWORD_WHMCS, "");
            if (loginPreferencesSharedPref.getInt(AppConst.PREF_CLIENT_ID, -1) != 0 &&
                    loginPreferencesSharedPref.getInt(AppConst.PREF_CLIENT_ID, -1) != -1) {//&& loginPreferencesSharedPref.getString(AppConst.PREF_PASSWORD_WHMCS, "")!=""
                Intent intent = new Intent(context, DashboardActivity.class);
                startActivity(intent);
            }
        } else if (!saveLogin) {
            emailIdET.setText(loginPreferences.getString(AppConst.PREF_USERNAME_WHMCS, ""));
            passwordET.setText(loginPreferences.getString(AppConst.PREF_PASSWORD_WHMCS, ""));
            if (!loginPreferences.getString(AppConst.PREF_USERNAME_WHMCS, "").equals("") &&
                    !loginPreferences.getString(AppConst.PREF_PASSWORD_WHMCS, "").equals(""))
                cbRememberMe.setChecked(true);
            else
                cbRememberMe.setChecked(false);
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

    @Override
    public void atStart() {

    }

    @Override
    public void onFinish() {
        if (progressDialog != null)
            progressDialog.dismiss();

    }

    @Override
    public void onFailed(String errorMessage) {
        if (context != null && !errorMessage.isEmpty() &&
                errorMessage.contains(AppConst.WHMCS_URL_ERROR)||
                errorMessage.contains("Error code 213 occurs")) {
            Utils.showToast(context, AppConst.WHMCS_URL_ERROR_MESSAGE);
        }
        else if (context != null && !errorMessage.isEmpty()) {
            Utils.showToast(context, context.getResources().getString(R.string.network_error));
        }

    }

    @Override
    public void validateLogin(LoginWHMCSCallback loginWHMCSCallback) {
        if (progressDialog != null)
            progressDialog.dismiss();
        if (loginWHMCSCallback != null && context != null) {
            String result = loginWHMCSCallback.getResult();
            String passwordHash = loginWHMCSCallback.getPasswordHash();
            int userId = loginWHMCSCallback.getUserId();
            if (result.equals("success") && !(userId == 0)) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(AppConst.CLIENT_ID, userId);
                editor.putString(AppConst.PASSWORD_HASH, passwordHash);
                editor.putBoolean(AppConst.PREF_SAVE_LOGIN_WHMCS, true);
                loginPrefsEditor.putInt(AppConst.PREF_CLIENT_ID, userId);
                loginPrefsEditor.putString(AppConst.PREF_PASSWORD_WHMCS, passwordHash);
                editor.commit();

                SharedPreferences sharedPreferencesLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_WHMCS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferencesLogin.edit();
                editor1.putInt(AppConst.CLIENT_ID, userId);
                editor1.commit();

                Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DashboardActivity.class);
                startActivity(intent);
            }else if(result.equals("error")){
                Toast.makeText(context, loginWHMCSCallback.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }



    @Override
    public void sendNotification(CommonResponseCallback commonResponseCallback) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Optional
    @OnClick({R.id.et_submit, R.id.cb_remember_me, R.id.tv_forget_password, R.id.content,R.id.tv_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_submit:
//                rplBGSubmit.startRippleAnimation();
                progressDialog.show();
                emailId = emailIdET.getText().toString();
                password = passwordET.getText().toString();
                emailCheck = isValidEmail(emailId);
                if (!emailCheck) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
//                    rplBGSubmit.stopRippleAnimation();
                    if (context != null)
                        Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }
                if (emailCheck && password.isEmpty()) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
//                    rplBGSubmit.stopRippleAnimation();
                    if (context != null)
                        Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();
                }
                if (emailCheck && (emailId != null && !emailId.isEmpty()) && (password != null && !password.isEmpty())) {

                    if (cbRememberMe.isChecked() && context != null) {
                        loginPrefsEditor.putBoolean(AppConst.PREF_SAVE_LOGIN_WHMCS, true);
                        loginPrefsEditor.putString(AppConst.PREF_USERNAME_WHMCS, emailId);
                        loginPrefsEditor.putString(AppConst.PREF_PASSWORD_WHMCS, password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
//                    rplBGSubmit.stopRippleAnimation();
                    loginPresenter.validateLogin(emailId, password);
                }


                break;
            case R.id.cb_remember_me:
                break;
            case R.id.tv_forget_password:
//                rplBG.startRippleAnimation();
//                rplBG.stopRippleAnimation();
                if (context != null) {
                    Intent intentForgetPassword = new Intent(context, ForgetPasswordActivity.class);
                    startActivity(intentForgetPassword);
                }
                break;
            case R.id.content:
                break;
            case R.id.tv_signup:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConst.FULL_CART_PAGE_URL));
                if (browserIntent.resolveActivity(context.getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
                break;
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
}
