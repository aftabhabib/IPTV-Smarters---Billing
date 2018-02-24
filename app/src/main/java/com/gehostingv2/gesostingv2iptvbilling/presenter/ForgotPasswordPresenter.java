package com.gehostingv2.gesostingv2iptvbilling.presenter;



import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ClientDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ForgotPasswordInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ForgotPasswordPresenter {
    private ForgotPasswordInterface forgotPasswordInterface;

    public ForgotPasswordPresenter(ForgotPasswordInterface forgotPasswordInterface){
        this.forgotPasswordInterface = forgotPasswordInterface;
    }


    public void getCleintDetail(final String email) {
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        postParam = Utils.jsonDataToSend(postParam);
        postParam.addProperty("command", AppConst.ACTION_GET_CLIENTS_DETAIL);
        postParam.addProperty("responsetype",AppConst.RESPONSE_TYPE) ;
        JsonObject params = new JsonObject();
        params.addProperty("email",email) ;
        postParam.add("params", params) ;
        Call<ClientDetailsCallback> call = service.getClientsDetails(postParam);

        call.enqueue(new Callback<ClientDetailsCallback>() {
            @Override
            public void onResponse(Call<ClientDetailsCallback> call, Response<ClientDetailsCallback> response) {
                forgotPasswordInterface.onFinish();
                if (response.body()!=null && response.isSuccessful()) {
                    forgotPasswordInterface.getClientsDetail(response.body(), email);
                }
            }

            @Override
            public void onFailure(Call<ClientDetailsCallback> call, Throwable t) {
                forgotPasswordInterface.onFinish();
                forgotPasswordInterface.onFailed(t.getMessage());
            }
        });
    }


    public void sendMail(final int id) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.BASE_URL_WHMCS).client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();



        RetrofitPost service =  retrofit.create(RetrofitPost.class);

//        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);

        forgotPasswordInterface.atStart();
        JsonObject postParam = new JsonObject();
        postParam = Utils.jsonDataToSend(postParam);
        postParam.addProperty("command",AppConst.ACTION_SEND_MAIL);
        postParam.addProperty("responsetype",AppConst.RESPONSE_TYPE) ;
        JsonObject params = new JsonObject();
        params.addProperty("id",id) ;
        params.addProperty("messagename","Passcode Verification");
        postParam.add("params", params) ;
        Call<CommonResponseCallback> call = service.sendmail(postParam);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                forgotPasswordInterface.onFinish();
                if (response != null) {
                    forgotPasswordInterface.sendMail(response.body(),id);

                } else {

                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                forgotPasswordInterface.onFinish();
                forgotPasswordInterface.onFailed(t.getMessage());
            }
        });
    }

    public void validatePasscode(int clientID, String otp) {
        forgotPasswordInterface.atStart();
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        postParam = Utils.jsonDataToSend(postParam);
        postParam.addProperty("command",AppConst.ACTION_VALIDATE_PASSCODE);
        postParam.addProperty("responsetype",AppConst.RESPONSE_TYPE) ;
        postParam.addProperty("custom",AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid",clientID) ;
        params.addProperty("passcode",otp) ;
        postParam.add("params", params) ;
        Call<CommonResponseCallback> call = service.validatePasscode(postParam);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                forgotPasswordInterface.onFinish();
                if (response != null) {
                    forgotPasswordInterface.validatePasscode(response.body());

                } else {

                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                forgotPasswordInterface.onFinish();
                forgotPasswordInterface.onFailed(t.getMessage());
            }
        });
    }

    public void updatePassword(int clientId, String newPassword){
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        postParam = Utils.jsonDataToSend(postParam);
        postParam.addProperty("command",AppConst.ACTION_UPADTE_CLIENT);
        postParam.addProperty("responsetype",AppConst.RESPONSE_TYPE) ;
        JsonObject params = new JsonObject();
        params.addProperty("clientid",clientId) ;
        params.addProperty("password2",newPassword) ;
        postParam.add("params", params) ;
        Call<CommonResponseCallback> call = service.updateClient(postParam);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                forgotPasswordInterface.onFinish();
                if (response.isSuccessful()) {
                    forgotPasswordInterface.updatePassword(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                forgotPasswordInterface.onFinish();
                forgotPasswordInterface.onFailed(t.getMessage());
            }
        });
    }
}
