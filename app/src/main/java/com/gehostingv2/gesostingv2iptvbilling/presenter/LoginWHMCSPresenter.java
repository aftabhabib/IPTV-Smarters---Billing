package com.gehostingv2.gesostingv2iptvbilling.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LoginWHMCSCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LoginWHMCSInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginWHMCSPresenter {
    private LoginWHMCSInterface loginInteface;
    private Context context;
    public LoginWHMCSPresenter(LoginWHMCSInterface loginInteface){
        this.loginInteface = loginInteface;
    }
    public void validateLogin(String email, String password2){
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_VALIDATE_LOGIN);
        jsonObject.addProperty("responsetype","json") ;
        JsonObject params = new JsonObject();
        params.addProperty("email",email) ;
        params.addProperty("password2",password2) ;
        jsonObject.add("params", params) ;
        Call<LoginWHMCSCallback> call = service.validateLogin(jsonObject);
        call.enqueue(new Callback<LoginWHMCSCallback>() {
            @Override
            public void onResponse(Call<LoginWHMCSCallback> call, Response<LoginWHMCSCallback> response) {
                loginInteface.atStart();
                if(response!=null&& response.isSuccessful()&&response.body()!=null){ //.getResult().equals("success")) {
                    loginInteface.onFinish();
                    loginInteface.validateLogin(response.body());

                }
                else if(response.body()==null && response.code()==404){
                    loginInteface.onFinish();
                    loginInteface.onFailed(AppConst.WHMCS_URL_ERROR_MESSAGE);
                }
                else if(response.body()==null|| response.body().getResult().equals("error")){
                    loginInteface.onFinish();
                    loginInteface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginWHMCSCallback> call, Throwable t) {
                loginInteface.onFinish();
                loginInteface.onFailed(t.getMessage());
            }
        });
    }
}
