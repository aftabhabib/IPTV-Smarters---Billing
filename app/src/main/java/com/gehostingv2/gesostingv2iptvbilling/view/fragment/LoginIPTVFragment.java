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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.LiveStreamDBHandler;
import com.gehostingv2.gesostingv2iptvbilling.presenter.LoginIPTVPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.DashboardActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.activity.ImportStreamsActivity;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LoginIPTVInterface;

import java.util.List;

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
 * Use the {@link LoginIPTVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginIPTVFragment extends Fragment implements LoginIPTVInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.et_activation_code)
    EditText etActivationCode;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_remember_me)
    CheckBox cbRememberMe;
    @BindView(R.id.activity_login)
    RelativeLayout iptvLoginRL;
    Unbinder unbinder;

    private LoginIPTVPresenter loginPresenter;
    private Context context;
    private String username;
    private String password;
    private String activationCode;
    private String loginWith;
    private ProgressDialog progressDialog;
    private SharedPreferences loginPreferences;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences.Editor loginPrefsEditorBeforeLogin;
    private Boolean saveLogin;
    private LiveStreamDBHandler liveStreamDBHandler;

    private SharedPreferences loginPreferencesSharedPref_allowed_format;
    private SharedPreferences loginPreferencesSharedPref_time_format;
    private SharedPreferences loginPreferencesSharedPref_epg_channel_update;
    private SharedPreferences.Editor loginPrefsEditor_timefomat;
    private SharedPreferences.Editor loginPrefsEditor_epgchannelupdate;
    private SharedPreferences.Editor loginPrefsEditor_fomat;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginIPTVFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginIPTVFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginIPTVFragment newInstance(String param1, String param2) {
        LoginIPTVFragment fragment = new LoginIPTVFragment();
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
        View view = inflater.inflate(R.layout.fragment_iptvlogin, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        context = getContext();
        liveStreamDBHandler = new LiveStreamDBHandler(context);
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        etEmail.requestFocus();
        username = etEmail.getText().toString();
        password = etPassword.getText().toString();
        loginPresenter = new LoginIPTVPresenter(this, context);
        loginPreferences = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        loginPreferencesAfterLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, MODE_PRIVATE);
        loginPrefsEditorBeforeLogin = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean(AppConst.PREF_SAVE_LOGIN_IPTV, false);
        loginCheck();
    }

    void loginCheck() {
        loginWith = loginPreferences.getString(AppConst.PREF_LOGIN_WITH, "");
        if (loginWith.equals(AppConst.LOGIN_WITH_ACTIVATION_CODE)) {
            if (saveLogin) {
                if (!loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "").equals("") && !loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "").equals("")) {
                    Intent intent = new Intent(context, DashboardActivity.class);
                    startActivity(intent);
                } else {
                    etActivationCode.setText(loginPreferences.getString(AppConst.PREF_ACTIVATION_CODE_IPTV, ""));
                    cbRememberMe.setChecked(true);
                }
            } else {
                etActivationCode.setText(loginPreferences.getString(AppConst.PREF_ACTIVATION_CODE_IPTV, ""));
                cbRememberMe.setChecked(false);
            }
        }

        if (loginWith.equals(AppConst.LOGIN_WITH_DETAILS)) {
            if (saveLogin) {
                if (!loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_USERNAME_IPTV, "").equals("")
                        && !loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_PASSWORD_IPTV, "").equals("")) {
                    if (context != null && liveStreamDBHandler != null &&
                            liveStreamDBHandler.getAvailableChannelsCount() > 0) {
                        if (context != null) {
                            Intent intent = new Intent(context, DashboardActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        if (context != null) {
                            Intent intent = new Intent(context, ImportStreamsActivity.class);
                            startActivity(intent);
                        }
                    }


//                    if (context != null && liveStreamDBHandler != null &&
//                            liveStreamDBHandler.getAvailableChannelsCount() > 0) {
//                        int epgCounts = liveStreamDBHandler.getEPGCount();
//                        if (epgCounts == 0) {
//                            Intent intent = new Intent(context, ImportEPGActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Intent intent = new Intent(context, DashboardActivity.class);
//                            startActivity(intent);
//                        }
//                    } else {
//                        if (context != null) {
//                            Intent intent = new Intent(context, ImportStreamsActivity.class);
//                            startActivity(intent);
//                        }
//                    }

                } else {
                    etEmail.setText(loginPreferences.getString(AppConst.PREF_USERNAME_IPTV, ""));
                    etPassword.setText(loginPreferences.getString(AppConst.PREF_PASSWORD_IPTV, ""));
                    cbRememberMe.setChecked(true);
                }
            } else {
                etEmail.setText(loginPreferences.getString(AppConst.PREF_USERNAME_IPTV, ""));
                etPassword.setText(loginPreferences.getString(AppConst.PREF_PASSWORD_IPTV, ""));
                cbRememberMe.setChecked(false);
            }
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_submit, R.id.tv_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                progressDialog.show();
                username = etEmail.getText().toString();
                password = etPassword.getText().toString();
                activationCode = etActivationCode.getText().toString();
                if (!activationCode.isEmpty()) {
                    etEmail.setText("");
                    etPassword.setText("");
                    if (cbRememberMe.isChecked()) {
                        loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN_IPTV, true);
                        loginPrefsEditorBeforeLogin.putString(AppConst.PREF_ACTIVATION_CODE_IPTV, activationCode);
                        loginPrefsEditorBeforeLogin.putString(AppConst.PREF_USERNAME_IPTV, "");
                        loginPrefsEditorBeforeLogin.putString(AppConst.PREF_PASSWORD_IPTV, "");
                        loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_ACTIVATION_CODE);
                        loginPrefsEditorBeforeLogin.commit();
                    } else {
                        loginPrefsEditorBeforeLogin.clear();
                        loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN_IPTV, false);
                        loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_ACTIVATION_CODE);
                        loginPrefsEditorBeforeLogin.commit();
                    }
                    loginPresenter.validateActivationCode(activationCode);

                } else {
                    if (username.isEmpty()) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        Toast.makeText(context, context.getResources().getString(R.string.enter_username_error), Toast.LENGTH_SHORT).show();
                    }
                    if (!username.isEmpty() && password.isEmpty()) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        Toast.makeText(context, context.getResources().getString(R.string.enter_password_error), Toast.LENGTH_SHORT).show();
                    }
                    if ((username != null && !username.isEmpty()) && (password != null && !password.isEmpty())) {

                        if (cbRememberMe.isChecked()) {
                            loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN_IPTV, true);
                            loginPrefsEditorBeforeLogin.putString(AppConst.PREF_USERNAME_IPTV, username);
                            loginPrefsEditorBeforeLogin.putString(AppConst.PREF_PASSWORD_IPTV, password);
                            loginPrefsEditorBeforeLogin.putString(AppConst.PREF_ACTIVATION_CODE_IPTV, "");
                            loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_DETAILS);
                            loginPrefsEditorBeforeLogin.commit();
                        } else {
                            loginPrefsEditorBeforeLogin.clear();
                            loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN_IPTV, false);
                            loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_DETAILS);
                            loginPrefsEditorBeforeLogin.commit();
                        }
                        loginPresenter.validateLogin(username, password);
                    }
                }
                break;
            case R.id.tv_signup:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConst.FULL_CART_PAGE_URL));
                if (browserIntent.resolveActivity(context.getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
                break;
        }
    }

    @Override
    public void atStart() {
        if (progressDialog != null)
            progressDialog.show();
    }

    @Override
    public void onFinish() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void onFailed(String errorMessage) {
//        if(errorMessage!=null && !errorMessage.isEmpty()
//                && !errorMessage.equals("")&& errorMessage.equals("Invalid Username and Password")){
//            Utils.showToast(context,"Invalid CODE ATLAS PRO");
//        }
        if (context != null && !errorMessage.isEmpty()) {
            Utils.showToast(context, context.getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void validateLogin(ValidationIPTVCallback validationIPTVCallback, String validateLogin) {
        if (progressDialog != null)
            progressDialog.dismiss();
        if (validationIPTVCallback != null && context != null) {
            Integer auth = validationIPTVCallback.getUserLoginInfo().getAuth();
            if (auth == 1) {
                String userStatus = validationIPTVCallback.getUserLoginInfo().getStatus();
                if (userStatus.equals("Active")) {
                    String username = validationIPTVCallback.getUserLoginInfo().getUsername();
                    String password = validationIPTVCallback.getUserLoginInfo().getPassword();
                    String serverPort = validationIPTVCallback.getServerInfo().getPort();
                    String serverUrl = validationIPTVCallback.getServerInfo().getUrl();
                    String expDate = validationIPTVCallback.getUserLoginInfo().getExpDate();
                    String isTrial = validationIPTVCallback.getUserLoginInfo().getIsTrial();
                    String activeCons = validationIPTVCallback.getUserLoginInfo().getActiveCons();
                    String createdAt = validationIPTVCallback.getUserLoginInfo().getCreatedAt();
                    String maxConnections = validationIPTVCallback.getUserLoginInfo().getMaxConnections();
                    List<String> allowedFormatList = validationIPTVCallback.getUserLoginInfo().getAllowedOutputFormats();
                    String allowedFormat = null;
                    if (allowedFormatList.size() != 0) {
                        allowedFormat = allowedFormatList.get(0);
                    }
                    SharedPreferences sharedPreferencesLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IPTV, Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferencesLogin.edit();
                    editor1.putString(AppConst.LOGIN_PREF_USERNAME_IPTV, username);
                    editor1.putString(AppConst.LOGIN_PREF_PASSWORD_IPTV, password);
                    editor1.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, AppConst.ALLOWED_FORMAT);
                    editor1.putString(AppConst.LOGIN_PREF_SERVER_PORT, serverPort);
                    editor1.putString(AppConst.LOGIN_PREF_SERVER_URL, serverUrl);
                    editor1.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate);
                    editor1.putString(AppConst.LOGIN_PREF_IS_TRIAL, isTrial);
                    editor1.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, activeCons);
                    editor1.putString(AppConst.LOGIN_PREF_CREATE_AT, createdAt);
                    editor1.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, maxConnections);
                    editor1.commit();


                    loginPreferencesSharedPref_allowed_format = context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, MODE_PRIVATE);
                    loginPreferencesSharedPref_time_format = context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, MODE_PRIVATE);
                    loginPreferencesSharedPref_epg_channel_update = context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, MODE_PRIVATE);


                    loginPrefsEditor_fomat = loginPreferencesSharedPref_allowed_format.edit();
                    loginPrefsEditor_timefomat = loginPreferencesSharedPref_time_format.edit();
                    loginPrefsEditor_epgchannelupdate = loginPreferencesSharedPref_epg_channel_update.edit();


                    String allowedFormat1 = loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                    if (allowedFormat1 != null && allowedFormat1.equals("")) {
                        loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                        loginPrefsEditor_fomat.commit();
                    }

                    String timeFormat = loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
                    if (timeFormat != null && timeFormat.equals("")) {
                        loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                        loginPrefsEditor_timefomat.commit();
                    }
                    String channelupdate = loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "");
                    if (channelupdate != null && channelupdate.equals("")) {
                        loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "withepg");
                        loginPrefsEditor_epgchannelupdate.commit();
                    }




                    Toast.makeText(context, context.getResources().getString(R.string.logged_in), Toast.LENGTH_SHORT).show();
                    if (context != null && liveStreamDBHandler != null &&
                            liveStreamDBHandler.getAvailableChannelsCount() > 0) {
//                        int epgCounts1 = liveStreamDBHandler.getEPGCount();
//                        if (epgCounts1 == 0) {
//                            Intent intent = new Intent(context, ImportEPGActivity.class);
//                            startActivity(intent);
//                        } else {
                        Intent intent = new Intent(context, DashboardActivity.class);
                        startActivity(intent);
//                        }
                    } else {
                        if (context != null) {
                            Intent intent = new Intent(context, ImportStreamsActivity.class);
                            startActivity(intent);
                        }
                    }

                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.invalid_status) + userStatus, Toast.LENGTH_SHORT).show();
                }
            } else {
                if (context != null) {
                    if (validateLogin == AppConst.ACTIVATION_CODE) {
                        Toast.makeText(context, context.getResources().getString(R.string.invalid_activation_code), Toast.LENGTH_SHORT).show();
                    }
                    if (validateLogin == AppConst.VALIDATE_LOGIN) {
                        Toast.makeText(context, context.getResources().getString(R.string.invalid_details), Toast.LENGTH_SHORT).show();
                    }
                }
            }
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
