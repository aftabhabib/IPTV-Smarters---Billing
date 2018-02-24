package com.gehostingv2.gesostingv2iptvbilling.presenter;

import android.content.Context;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LoginIPTVInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginIPTVPresenter {
    private LoginIPTVInterface loginInteface;
    private Context context;

    public LoginIPTVPresenter(LoginIPTVInterface loginInteface, Context context) {
        this.loginInteface = loginInteface;
        this.context = context;
    }

    public void validateLogin(String username, String password) {
        RetrofitPost service = Utils.retrofitObjectIPTV().create(RetrofitPost.class);

        Call<ValidationIPTVCallback> call = service.validateIPTVLogin(AppConst.CONTENT_TYPE, username, password);
        call.enqueue(new Callback<ValidationIPTVCallback>() {
            @Override
            public void onResponse(Call<ValidationIPTVCallback> call, Response<ValidationIPTVCallback> response) {

                loginInteface.atStart();
                if (response != null && response.isSuccessful()) {
                    loginInteface.validateLogin(response.body(), AppConst.VALIDATE_LOGIN);
                    loginInteface.onFinish();
                } else if (response.body() == null) {
                    loginInteface.onFinish();
                    if (context != null)
                        loginInteface.onFailed(context.getResources().getString(R.string.invalid_request));
                }
            }

            @Override
            public void onFailure(Call<ValidationIPTVCallback> call, Throwable t) {
                loginInteface.onFinish();
                loginInteface.onFailed(t.getMessage());
            }
        });
    }

    public void validateActivationCode(String activationCode) {
        RetrofitPost service = Utils.retrofitObjectIPTV().create(RetrofitPost.class);

        Call<ValidationIPTVCallback> call = service.validateIPTVLogin(AppConst.CONTENT_TYPE, activationCode, activationCode);
        call.enqueue(new Callback<ValidationIPTVCallback>() {
            @Override
            public void onResponse(Call<ValidationIPTVCallback> call, Response<ValidationIPTVCallback> response) {

                loginInteface.atStart();
                if (response != null && response.isSuccessful()) {
                    loginInteface.validateLogin(response.body(), AppConst.ACTIVATION_CODE);
                    loginInteface.onFinish();
                } else if (response.body() == null) {
                    loginInteface.onFinish();
                    if (context != null)
                        loginInteface.onFailed(context.getResources().getString(R.string.invalid_request));
                }
            }

            @Override
            public void onFailure(Call<ValidationIPTVCallback> call, Throwable t) {
                loginInteface.onFinish();
                loginInteface.onFailed(t.getMessage());
            }
        });
    }


}
