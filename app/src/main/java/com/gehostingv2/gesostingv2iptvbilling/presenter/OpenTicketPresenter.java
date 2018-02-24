package com.gehostingv2.gesostingv2iptvbilling.presenter;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetDepartmentCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetOpenTicketCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.OpenTicketInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenTicketPresenter {
    private OpenTicketInterface openTicketInterface;
    public OpenTicketPresenter(OpenTicketInterface openTicketInterface){
        this.openTicketInterface= openTicketInterface;
    }
    public void getSupportDepartmetn(int clientid){
        openTicketInterface.atStart();
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_SUPPORT_DEPARTMEENT);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientid);
        jsonObject.add("params", params);
        Call<GetDepartmentCallback> call = service.getSupportDepartment(jsonObject);
        call.enqueue(new Callback<GetDepartmentCallback>() {
            @Override
            public void onResponse(Call<GetDepartmentCallback> call, Response<GetDepartmentCallback> response) {
                openTicketInterface.onFinish();
                if (response.body()!=null &&
                        response.body().getResult().equals("success")) {
                    openTicketInterface.getSupportDepartmet(response.body());
                } else if (response.body()!=null &&
                        response.body().getResult().equals("error")) {
                    openTicketInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetDepartmentCallback> call, Throwable t) {
                openTicketInterface.onFinish();
                openTicketInterface.onFailed(t.getMessage());
            }
        });
    }

    public void openSupportDepartment(int deptId,
                                      int clientId,
                                      String message,
                                      final String subject, String priority) {




        openTicketInterface.atStart();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.BASE_URL_WHMCS).client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();



        RetrofitPost service =  retrofit.create(RetrofitPost.class);



//        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_OPEN_TICKET);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientId);
        params.addProperty("deptid", deptId);
        params.addProperty("subject", subject);
        params.addProperty("message", message);
        params.addProperty("priority", priority);
        jsonObject.add("params", params);
        Call<GetOpenTicketCallback> call = service.openSupportDept(jsonObject);
        call.enqueue(new Callback<GetOpenTicketCallback>() {
            @Override
            public void onResponse(Call<GetOpenTicketCallback> call, Response<GetOpenTicketCallback> response) {
                openTicketInterface.onFinish();
                if (response.body().getResult().equals("success")) {
                    openTicketInterface.OpenSupportDeartment(response.body());
                } else if (response.body().getResult().equals("error")) {
                    openTicketInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetOpenTicketCallback> call, Throwable t) {
                openTicketInterface.onFinish();
                openTicketInterface.onFailed(t.getMessage());
            }
        });
    }
}
