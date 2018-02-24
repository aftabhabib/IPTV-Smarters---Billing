package com.gehostingv2.gesostingv2iptvbilling.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ClientDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.presenter.ForgotPasswordPresenter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ForgotPasswordInterface;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgotPasswordInterface {
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_lost_password_reset)
    TextView tvLostPasswordReset;
    @BindView(R.id.tv_lost_password_brief)
    TextView tvLostPasswordBrief;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.cv_lost_password_reset)
    CardView cvLostPasswordReset;
    @BindView(R.id.activity_forget_password)
    RelativeLayout activityForgetPassword;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.wrong_email)
    TextView wrongEmail;
    @BindView(R.id.rl_email_validation)
    RelativeLayout rlEmailValidation;
    @BindView(R.id.iv_success)
    ImageView ivSuccess;
    @BindView(R.id.iv_success_email)
    TextView ivSuccessEmail;
    @BindView(R.id.rl_email_validation_success)
    RelativeLayout rlEmailValidationSuccess;
    @BindView(R.id.tv_reset_password_process_brief)
    TextView tvResetPasswordProcessBrief;
    @BindView(R.id.et_otp)
    EditText etOtp;
    @BindView(R.id.bt_verify_code)
    Button btVerifyCode;
    @BindView(R.id.cv_reset_password_process)
    CardView cvResetPasswordProcess;
    @BindView(R.id.et_otp1)
    EditText etOtp1;
    @BindView(R.id.et_otp2)
    EditText etOtp2;
    @BindView(R.id.et_otp3)
    EditText etOtp3;
    @BindView(R.id.et_otp4)
    EditText etOtp4;
    @BindView(R.id.rl_otp)
    RelativeLayout rlOtp;
    @BindView(R.id.tv_new_password_brief)
    TextView tvNewPasswordBrief;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_new_password)
    EditText etConfirmNewPassword;
    @BindView(R.id.tv_tip_password)
    RelativeLayout tvTipPassword;
    @BindView(R.id.bt_submit_confirm_password)
    Button btSubmitConfirmPassword;
    @BindView(R.id.bt_cancel_confirm_password)
    Button btCancelConfirmPassword;
    @BindView(R.id.cv_new_password)
    CardView cvNewPassword;
    @BindView(R.id.iv_cicle_ref)
    ImageView ivCicleRef;
    @BindView(R.id.iv_cicle_full)
    ImageView ivCicleFull;
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.iv_cicle_ref_center)
    ImageView ivCicleRefCenter;
    @BindView(R.id.iv_cicle_full_center)
    ImageView ivCicleFullCenter;
    @BindView(R.id.iv_line_second)
    ImageView ivLineSecond;
    @BindView(R.id.iv_cicle_hollow_last)
    ImageView ivCicleHollowLast;
    @BindView(R.id.iv_cicle_full_last)
    ImageView ivCicleFullLast;
    @BindView(R.id.rl_state_progress_bar)
    RelativeLayout rlStateProgressBar;
    @BindView(R.id.otp1_line)
    View otp1Line;
    @BindView(R.id.otp2_line)
    View otp2Line;
    @BindView(R.id.otp3_line)
    View otp3Line;
    @BindView(R.id.otp4_line)
    View otp4Line;
//    @BindView(R.id.sb_password_strength)
//    SeekBar sbPasswordStrength;

    private ForgotPasswordPresenter forgotPasswordPresenter;
    private boolean email = false;
    private Context context;
    private ProgressDialog progressDialog;
    private int clientID;
    private String newPassword = "";
    private String confirmPassword = "";

    View layout;
    View layoutEnterEmail;
    View layoutUnauthorisedClient;
    View layoutValidationMailSent;
    View layoutPasscodeNotMatch;
    View layoutConfirmPasscodeNotMatch;
    View layoutUpdatedSuccessfully;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        forgotPasswordPresenter = new ForgotPasswordPresenter(this);
        context = this;
        progressDialog = Utils.showProgressD(progressDialog, context);
        cvLostPasswordReset.setCardBackgroundColor(Color.parseColor("#6ee1e1e1"));  //  #    6ee1e1e1     75bec8
        toastWithImageandMessageRed();
        toastWithImageandMessageGreen();

        otp1Line.setVisibility(View.VISIBLE);
        otp2Line.setVisibility(View.VISIBLE);
        otp3Line.setVisibility(View.VISIBLE);
        otp4Line.setVisibility(View.VISIBLE);
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

    private void toastWithImageandMessageRed() {
        LayoutInflater inflater = getLayoutInflater();
        layoutUnauthorisedClient = inflater.inflate(R.layout.custom_toast_client_validation, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView txt = (TextView) layoutUnauthorisedClient.findViewById(R.id.toastmsg);
        txt.setText(AppConst.UNAUTHORISED_USER);
        ImageView image = (ImageView) layoutUnauthorisedClient.findViewById(R.id.image);
        image.setImageResource(R.drawable.cancel);
    }

    private void toastWithImageandMessageGreen() {
        LayoutInflater inflater = getLayoutInflater();
        layoutValidationMailSent = inflater.inflate(R.layout.custom_toast_validation_email_sent, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView txt = (TextView) layoutValidationMailSent.findViewById(R.id.toastmsg);
        txt.setText(AppConst.VALIDATION_EMAIL_SENT);
        ImageView image = (ImageView) layoutValidationMailSent.findViewById(R.id.image);
        image.setImageResource(R.drawable.tick);
    }

    private void toastUpadteSuccessfullyMessageGreen() {
        LayoutInflater inflater = getLayoutInflater();
        layoutUpdatedSuccessfully = inflater.inflate(R.layout.custom_toast_validation_email_sent, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView txt = (TextView) layoutUpdatedSuccessfully.findViewById(R.id.toastmsg);
        txt.setText(getResources().getString(R.string.password_updated));
        ImageView image = (ImageView) layoutUpdatedSuccessfully.findViewById(R.id.image);
        image.setImageResource(R.drawable.tick);
    }

    private void toastWithMessageRed() {
        LayoutInflater inflater = getLayoutInflater();
        layoutEnterEmail = inflater.inflate(R.layout.custom_toast_enter_mail, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView txt = (TextView) layoutEnterEmail.findViewById(R.id.toastmsg);
        txt.setText(getResources().getString(R.string.enter_valid_email));
        ImageView image = (ImageView) layoutEnterEmail.findViewById(R.id.image);
        image.setImageResource(R.drawable.cancel);
    }

    private void toastWithMessageRedPasscodeNotMatch() {
        LayoutInflater inflater = getLayoutInflater();
        layoutPasscodeNotMatch = inflater.inflate(R.layout.custom_toast_client_validation, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView txt = (TextView) layoutPasscodeNotMatch.findViewById(R.id.toastmsg);
        txt.setText(AppConst.PASSCODE_VALIDATION);
        ImageView image = (ImageView) layoutPasscodeNotMatch.findViewById(R.id.image);
        image.setImageResource(R.drawable.cancel);
    }

    private void toastWithMessageRedCOnfirmPasscodeNotMatch() {
        LayoutInflater inflater = getLayoutInflater();
        layoutConfirmPasscodeNotMatch = inflater.inflate(R.layout.custom_toast_client_validation, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView txt = (TextView) layoutConfirmPasscodeNotMatch.findViewById(R.id.toastmsg);
        txt.setText(getResources().getString(R.string.password_mismatch_error));
        ImageView image = (ImageView) layoutConfirmPasscodeNotMatch.findViewById(R.id.image);
        image.setImageResource(R.drawable.cancel);
    }

    @OnClick({R.id.bt_submit, R.id.bt_verify_code, R.id.bt_submit_confirm_password, R.id.bt_cancel_confirm_password})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                toastWithMessageRed();
                if (progressDialog != null)
                    progressDialog.show();
                String emailAddress = etEmail.getText().toString();

                if (!emailAddress.isEmpty() && emailAddress != null) {
                    if (!email) {
                        email = Utils.isValidEmail(emailAddress);
                        if (!email) {
                            email = false;
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            setToast(); // this is to custom toast for valid email
                        }
                        if (email) {
                            email = false;
                            forgotPasswordPresenter.getCleintDetail(emailAddress);
                        }
                    }
                } else {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    setToast();
//                    Utils.showToast(context, AppConst.ENTER_EMAIL);
                }
                break;
            case R.id.bt_verify_code:
                String otp = etOtp1.getText().toString() + etOtp2.getText().toString() +
                        etOtp3.getText().toString() + etOtp4.getText().toString();
                forgotPasswordPresenter.validatePasscode(clientID, otp);
                break;
            case R.id.bt_submit_confirm_password:
                if (progressDialog != null)
                    progressDialog.show();
                newPassword = etNewPassword.getText().toString();
                confirmPassword = etConfirmNewPassword.getText().toString();
                boolean checkPasswoprd = checkPassWordAndConfirmPassword(newPassword, confirmPassword);
                if (checkPasswoprd && clientID != 0) {
                    forgotPasswordPresenter.updatePassword(clientID, newPassword);

                } else {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    toastWithMessageRedCOnfirmPasscodeNotMatch();
                    setToastConfirmPasswordUnmatched();

//                    Toast.makeText(context, "Your Password and Confirm Password do not match ", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_cancel_confirm_password:
                Intent intentStartLoginActivity = new Intent(this, LoginWHMCSActivity.class);
                startActivity(intentStartLoginActivity);
                break;
        }
    }

    @Override
    public void getClientsDetail(ClientDetailsCallback clientDetailsCallback, String email) {
        if (clientDetailsCallback !=null &&
                clientDetailsCallback.getResult().equals("success")) {
            int id = clientDetailsCallback.getId();
            forgotPasswordPresenter.sendMail(id);
        } else if (clientDetailsCallback !=null &&
                clientDetailsCallback.getResult().equals("error")) {
            toastWithImageandMessageRed();
            setToastUnauthorsed();
            cvResetPasswordProcess.setVisibility(View.INVISIBLE);
            cvLostPasswordReset.setVisibility(View.VISIBLE);
            rlOtp.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void sendMail(CommonResponseCallback commonResponseCallback, int id) {
        if (commonResponseCallback.getResult().equals("success")) {
            clientID = id;

            toastWithImageandMessageGreen();
            setToastValidationMailSent();

            ivCicleFullCenter.setVisibility(View.VISIBLE);
            ivCicleFull.setVisibility(View.INVISIBLE);
//            rlEmailValidationSuccess.setVisibility(View.VISIBLE);
//            rlEmailValidation.setVisibility(View.INVISIBLE);


//            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);


            cvResetPasswordProcess.setVisibility(View.VISIBLE);
            cvLostPasswordReset.setVisibility(View.INVISIBLE);
            rlOtp.setVisibility(View.VISIBLE);
            cvResetPasswordProcess.setCardBackgroundColor(Color.parseColor("#6ee1e1e1"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etOtp.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));
                etOtp1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));
                etOtp2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));
                etOtp3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));
                etOtp4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));
            }

            etOtp1.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etOtp1.getText().toString().length() == 1)     //size as per your requirement
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            otp1Line.setVisibility(View.INVISIBLE);
//                            etOtp1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));
                        }
                        etOtp2.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }

            });
            etOtp2.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etOtp2.getText().toString().length() == 1)     //size as per your requirement
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            otp2Line.setVisibility(View.INVISIBLE);
//                            etOtp2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));


                        }
                        etOtp3.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }

            });
            etOtp3.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etOtp3.getText().toString().length() == 1)     //size as per your requirement
                    {
                        otp3Line.setVisibility(View.INVISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            etOtp3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));
                        }
                        etOtp4.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }


            });

            etOtp4.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etOtp4.getText().toString().length() == 1)     //size as per your requirement
                    {
                        otp4Line.setVisibility(View.INVISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            etOtp4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ffffff")));
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }


            });
            etOtp1.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        otp1Line.setVisibility(View.VISIBLE);
                        etOtp1.requestFocus();
                    }
                    return false;
                }
            });

            etOtp2.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    String otp4Text = etOtp2.getText().toString();
                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (otp4Text.isEmpty()) {
                            etOtp1.requestFocus();
                        } else if (!otp4Text.isEmpty()) {
                            otp2Line.setVisibility(View.VISIBLE);
                            etOtp2.getText().clear();
                            etOtp2.requestFocus();
                        }
                    }

//                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
//                    if (keyCode == KeyEvent.KEYCODE_DEL) {
//                        otp2Line.setVisibility(View.VISIBLE);
//                        etOtp2.requestFocus();
//                    }
                    return false;
                }
            });
            etOtp3.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    String otp4Text = etOtp3.getText().toString();
                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (otp4Text.isEmpty()) {
                            etOtp2.requestFocus();
                        } else if (!otp4Text.isEmpty()) {
                            otp3Line.setVisibility(View.VISIBLE);
                            etOtp3.getText().clear();
                            etOtp3.requestFocus();
                        }
                    }
                    return false;
                }
            });
            etOtp4.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    String otp4Text = etOtp4.getText().toString();
                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (otp4Text.isEmpty()) {
                            etOtp3.requestFocus();
                        } else if (!otp4Text.isEmpty()) {
                            otp4Line.setVisibility(View.VISIBLE);
                            etOtp4.getText().clear();
                            etOtp4.requestFocus();
                        }
                    }
                    return false;
                }
            });

        } else if (commonResponseCallback.getResult().equals("error")) {

        }
    }

    @Override
    public void validatePasscode(CommonResponseCallback commonResponseCallback) {
        if (commonResponseCallback.getResult().equals("success")) {
            ivCicleFullCenter.setVisibility(View.INVISIBLE);
            ivCicleFullLast.setVisibility(View.VISIBLE);
            cvNewPassword.setVisibility(View.VISIBLE);
            cvNewPassword.setCardBackgroundColor(Color.parseColor("#6ee1e1e1"));
            rlEmailValidationSuccess.setVisibility(View.INVISIBLE);
            rlEmailValidation.setVisibility(View.INVISIBLE);
            cvLostPasswordReset.setVisibility(View.INVISIBLE);
            cvResetPasswordProcess.setVisibility(View.INVISIBLE);
        } else if (commonResponseCallback.getResult().equals("error")) {
            toastWithMessageRedPasscodeNotMatch();
            setToastPasscodeUnmatched();
            etOtp1.getText().clear();
            etOtp2.getText().clear();
            etOtp3.getText().clear();
            etOtp4.getText().clear();
            etOtp1.requestFocus();
            otp1Line.setVisibility(View.VISIBLE);
            otp2Line.setVisibility(View.VISIBLE);
            otp3Line.setVisibility(View.VISIBLE);
            otp4Line.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updatePassword(CommonResponseCallback commonResponseCallback) {
        if (commonResponseCallback.getResult().equals("success")) {
            toastUpadteSuccessfullyMessageGreen();
            setToastPasswordUpdated();
            Utils.showToast(context, commonResponseCallback.getMessage());
            Intent intentStartLoginActivity = new Intent(this, LoginWelcomeActivity.class);
            intentStartLoginActivity.putExtra(AppConst.FORGETPASS, AppConst.FORGETPASS);
            startActivity(intentStartLoginActivity);
        } else if (commonResponseCallback.getResult().equals("error")) {
            toastWithMessageRedCOnfirmPasscodeNotMatchServer(commonResponseCallback.getMessage());
            setToastConfirmPasswordUnmatched();
        }
    }

    private void toastWithMessageRedCOnfirmPasscodeNotMatchServer(String message) {
        LayoutInflater inflater = getLayoutInflater();
        layoutConfirmPasscodeNotMatch = inflater.inflate(R.layout.custom_toast_client_validation, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView txt = (TextView) layoutConfirmPasscodeNotMatch.findViewById(R.id.toastmsg);
        txt.setText(message);
        ImageView image = (ImageView) layoutConfirmPasscodeNotMatch.findViewById(R.id.image);
        image.setImageResource(R.drawable.cancel);
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

    public boolean checkPassWordAndConfirmPassword(String password, String confirmPassword) {
        boolean pstatus = false;
        if (confirmPassword != null && password != null &&
                !confirmPassword.isEmpty() && !password.isEmpty()) {
            if (password.equals(confirmPassword)) {
                pstatus = true;
            }
        }
        return pstatus;
    }

    @Override
    public void onFailed(String errorMessage) {
        if (context != null && !errorMessage.isEmpty()) {
            Utils.showToast(context,getResources().getString(R.string.network_error));
        }
    }

    public void setToast() {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layoutEnterEmail);
        toast.show();
    }

    private void setToastUnauthorsed() {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layoutUnauthorisedClient);
        toast.show();

    }

    private void setToastValidationMailSent() {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layoutValidationMailSent);
        toast.show();

    }

    private void setToastPasscodeUnmatched() {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layoutPasscodeNotMatch);
        toast.show();

    }

    private void setToastConfirmPasswordUnmatched() {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layoutConfirmPasscodeNotMatch);
        toast.show();

    }

    private void setToastPasswordUpdated() {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layoutUpdatedSuccessfully);
        toast.show();

    }
}


