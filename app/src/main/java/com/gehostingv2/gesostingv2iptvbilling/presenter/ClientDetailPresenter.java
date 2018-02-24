package com.gehostingv2.gesostingv2iptvbilling.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetIPTVCredentialsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.MyDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ClientDetailInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailPresenter {
    private ClientDetailInterface clientDetailInterface;
    private Context context;

    public ClientDetailPresenter(ClientDetailInterface clientDetailInterface, Context context){
        this.clientDetailInterface= clientDetailInterface;
        this.context = context;
    }

    public void getMyDetail(int clientid){
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_CLIENTS_DETAIL);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientid);
        jsonObject.add("params", params);
        Call<MyDetailsCallback> call = service.getClientDetail(jsonObject);

        call.enqueue(new Callback<MyDetailsCallback>() {
            @Override
            public void onResponse(Call<MyDetailsCallback> call, Response<MyDetailsCallback> response) {
                if (response != null) {
                    clientDetailInterface.getClientDetails(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<MyDetailsCallback> call, Throwable t) {

            }
        });
    }

    public void getIPTVServices(int clientid){
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_IPTV_CREDENTIALS);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("custom", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientid);
        jsonObject.add("params", params);
        Call<GetIPTVCredentialsCallback> call = service.getIPTVCredentials(jsonObject);
        call.enqueue(new Callback<GetIPTVCredentialsCallback>() {
            @Override
            public void onResponse(Call<GetIPTVCredentialsCallback> call, Response<GetIPTVCredentialsCallback> response) {
                if (response != null) {
                    clientDetailInterface.getIPTVCredentials(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<GetIPTVCredentialsCallback> call, Throwable t) {

            }
        });
    }

    public void getIPTVDetails(String username, String password){
        clientDetailInterface.atStart();
        RetrofitPost service =  Utils.retrofitObjectIPTV().create(RetrofitPost.class);

        Call<ValidationIPTVCallback> call = service.validateIPTVLogin(AppConst.CONTENT_TYPE,username,password);
        call.enqueue(new Callback<ValidationIPTVCallback>() {
            @Override
            public void onResponse(Call<ValidationIPTVCallback> call, Response<ValidationIPTVCallback> response) {
                clientDetailInterface.onFinish();
                if(response!=null && response.isSuccessful()){
                    clientDetailInterface.getIPTVDetails(response.body(),AppConst.VALIDATE_LOGIN);
                }
                else if(response.body()==null){
                    clientDetailInterface.onFailed(context.getResources().getString(R.string.invalid_request));
                }
            }

            @Override
            public void onFailure(Call<ValidationIPTVCallback> call, Throwable t) {
                clientDetailInterface.onFinish();
                clientDetailInterface.onFailed(t.getMessage());
            }
        });
    }
}
