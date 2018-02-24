package com.gehostingv2.gesostingv2iptvbilling.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.skyfishjy.library.RippleBackground;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LoginWHMCSCallback;
import com.gehostingv2.gesostingv2iptvbilling.presenter.LoginWHMCSPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LoginWHMCSInterface;
import com.gehostingv2.gesostingv2iptvbilling.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils.isValidEmail;


public class LoginWHMCSActivity extends AppCompatActivity implements LoginWHMCSInterface {
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
    @Nullable
    @BindView(R.id.content)
    RippleBackground rplBG;
    @BindView(R.id.content_submit)
    RippleBackground rplBGSubmit;

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
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login_whmcs);
        ButterKnife.bind(this);
        initialize();
        changeStatusBarColor();
    }

    private void changeStatusBarColor() {

        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);
        startDashboard();
        super.onBackPressed();

    }

    private void startDashboard() {
        Intent intentDashboard = new Intent(this, DashboardActivity.class);
        startActivity(intentDashboard);
    }


    private void initialize() {
        context = this;
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("api_username", AppConst.API_USERNAME);
        editor.putString("api_password", AppConst.API_PASSWORD);
        editor.commit();
        emailIdET.requestFocus();
        emailId = emailIdET.getText().toString();
        password = passwordET.getText().toString();
        loginPresenter = new LoginWHMCSPresenter(LoginWHMCSActivity.this);
        loginPreferences = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        loginPreferencesSharedPref = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, MODE_PRIVATE);
        int clientId = loginPreferencesSharedPref.getInt(AppConst.PREF_CLIENT_ID, -1);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferencesSharedPref.getBoolean(AppConst.PREF_SAVE_LOGIN_WHMCS, false);
        if (saveLogin) {
            loginPreferences.getInt(AppConst.PREF_CLIENT_ID, -1);
            loginPreferences.getString(AppConst.PREF_PASSWORD_WHMCS, "");
            if (loginPreferencesSharedPref.getInt(AppConst.PREF_CLIENT_ID, -1) != 0 &&
                    loginPreferencesSharedPref.getInt(AppConst.PREF_CLIENT_ID, -1) != -1) {//&& loginPreferencesSharedPref.getString(AppConst.PREF_PASSWORD_WHMCS, "")!=""
                Intent intent = new Intent(this, DashboardActivity.class);
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


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
//        Log.e("DEBUG", "OnPause of loginFragment");
//        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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
    public void onFailed(String message) {
        if (context != null && !message.isEmpty() &&
                message.contains(AppConst.WHMCS_URL_ERROR)||
                message.contains("Error code 213 occurs")) {
            Utils.showToast(context, AppConst.WHMCS_URL_ERROR_MESSAGE);
        }
        else if (context != null && !message.isEmpty()) {
            Utils.showToast(context, getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void validateLogin(LoginWHMCSCallback loginWHMCSCallback) {
        if (progressDialog != null)
            progressDialog.dismiss();
        if (loginWHMCSCallback != null) {
            String result = loginWHMCSCallback.getResult();
            String passwordHash = loginWHMCSCallback.getPasswordHash();
            int userId = loginWHMCSCallback.getUserId();
            if (result.equals("success") && !(userId == 0)) {
                SharedPreferences sharedPreferences = getSharedPreferences(AppConst.SHARED_PREFERENCE_WHMCS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(AppConst.CLIENT_ID, userId);
                editor.putString(AppConst.PASSWORD_HASH, passwordHash);
                editor.putBoolean(AppConst.PREF_SAVE_LOGIN_WHMCS, true);
                loginPrefsEditor.putInt(AppConst.PREF_CLIENT_ID, userId);
                loginPrefsEditor.putString(AppConst.PREF_PASSWORD_WHMCS, passwordHash);
                editor.commit();

                SharedPreferences sharedPreferencesLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_WHMCS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferencesLogin.edit();
                editor1.putInt(AppConst.CLIENT_ID, userId);
                editor1.commit();

                Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                startActivity();
            } else if(result.equals("error")){
                Toast.makeText(context, loginWHMCSCallback.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startActivity() {
        Intent intVa = getIntent();
        String service = intVa.getStringExtra(AppConst.ACTIVITY_SERVICES);
        String invoice =intVa.getStringExtra( AppConst.ACTIVITY_INVOICES);
        String ticket = intVa.getStringExtra(AppConst.ACTIVITY_TICKETS);
        String openticket = intVa.getStringExtra( AppConst.ACTIVITY_OPEN_TICKETS);
        if(intVa!=null && service!=null && service.equals( AppConst.ACTIVITY_SERVICES)){
            Intent intent = new Intent(this, ServicesActivity.class);
            startActivity(intent);
        }else if(intVa!=null && invoice!=null && invoice.equals( AppConst.ACTIVITY_INVOICES)){
            Intent invoiceActivityIntent = new Intent(context, InvoicesActivity.class);
            startActivity(invoiceActivityIntent);
        }
        else if(intVa!=null && ticket!=null && ticket.equals( AppConst.ACTIVITY_TICKETS)){
            Intent ticketsActivityIntent = new Intent(context, TicketsActivity.class);
            startActivity(ticketsActivityIntent);
        }
        else if(intVa!=null && openticket!=null && openticket.equals( AppConst.ACTIVITY_OPEN_TICKETS)){
            Intent ticketsActivityIntent = new Intent(context, OpenTicketActivity.class);
            ticketsActivityIntent.putExtra("LoginCheck","openTicket");
            startActivity(ticketsActivityIntent);
        }
        else {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void sendNotification(CommonResponseCallback commonResponseCallback) {

    }

    @OnClick({R.id.et_submit, R.id.tv_forget_password,R.id.tv_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_submit:
                rplBGSubmit.startRippleAnimation();
                progressDialog.show();
                emailId = emailIdET.getText().toString();
                password = passwordET.getText().toString();
                emailCheck = isValidEmail(emailId);
                if (!emailCheck) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    rplBGSubmit.stopRippleAnimation();
                    Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }
                if (emailCheck && password.isEmpty()) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    rplBGSubmit.stopRippleAnimation();
                    Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }
                if (emailCheck && (emailId != null && !emailId.isEmpty()) && (password != null && !password.isEmpty())) {

                    if (cbRememberMe.isChecked()) {
                        loginPrefsEditor.putBoolean(AppConst.PREF_SAVE_LOGIN_WHMCS, true);
                        loginPrefsEditor.putString(AppConst.PREF_USERNAME_WHMCS, emailId);
                        loginPrefsEditor.putString(AppConst.PREF_PASSWORD_WHMCS, password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                        rplBGSubmit.stopRippleAnimation();
                        loginPresenter.validateLogin(emailId, password);
                }

                break;
            case R.id.tv_forget_password:
                rplBG.startRippleAnimation();
                rplBG.stopRippleAnimation();
                Intent intentForgetPassword = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intentForgetPassword);
                break;
            case R.id.tv_signup:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConst.FULL_CART_PAGE_URL));
                if (browserIntent.resolveActivity(context.getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
                break;
        }
    }

    @OnClick(R.id.cb_remember_me)
    public void onViewClicked() {
    }
}



