package com.gehostingv2.gesostingv2iptvbilling.presenter;


import android.content.Context;

import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidateCustomLoginCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.WHMCSDetailsInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ValidateCustomLoginPresenter {
    private static WHMCSDetailsInterface whmcsDetailsInterface;
    private Context context;

    public ValidateCustomLoginPresenter(WHMCSDetailsInterface whmcsDetailsInterface) {
        this.whmcsDetailsInterface = whmcsDetailsInterface;
    }

    public static void getWHMCSClientDetials(String username, String password) {
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_WHMCS_VALIDATE_CUSTOM_LOGIN);
        jsonObject.addProperty("responsetype", "json");
        jsonObject.addProperty("custom", true);
        JsonObject params = new JsonObject();
        params.addProperty("username", username);
        params.addProperty("password", password);
        jsonObject.add("params", params);
        Call<ValidateCustomLoginCallback> call = service.validateCustomLogin(jsonObject);
        call.enqueue(new Callback<ValidateCustomLoginCallback>() {
            @Override
            public void onResponse(Call<ValidateCustomLoginCallback> call, Response<ValidateCustomLoginCallback> response) {
                whmcsDetailsInterface.atStart();
                if (response != null && response.isSuccessful() && response.body().getResult().equals("success")) {
                    whmcsDetailsInterface.onFinish();
                    whmcsDetailsInterface.validateCustomLogin(response.body());

                }else if(response.body()==null && response.code()==404){
                    whmcsDetailsInterface.onFinish();
                    whmcsDetailsInterface.onFailed(AppConst.WHMCS_URL_ERROR_MESSAGE);
                }
                else if (response.body() == null || response.body().getResult().equals("error")) {
                    whmcsDetailsInterface.onFinish();
                    whmcsDetailsInterface.validateCustomLogin(response.body());
                }
            }

            @Override
            public void onFailure(Call<ValidateCustomLoginCallback> call, Throwable t) {
                whmcsDetailsInterface.onFinish();
                whmcsDetailsInterface.onFailed(t.getMessage());
            }
        });
    }
}