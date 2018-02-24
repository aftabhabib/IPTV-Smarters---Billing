package com.gehostingv2.gesostingv2iptvbilling.presenter;


import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetClientProductsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ServicesInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesPresenter {
    private ServicesInterface servicesInterface;
    public ServicesPresenter(ServicesInterface servicesInterface){
        this.servicesInterface = servicesInterface;
    }

    public void getCLientProducts(int clientid) {
        servicesInterface.atStart();
        final RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_SERVICES);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientid);
        jsonObject.add("params", params);
        Call<GetClientProductsCallback> call = service.getMyservices(jsonObject);
        call.enqueue(new Callback<GetClientProductsCallback>() {
            @Override
            public void onResponse(Call<GetClientProductsCallback> call, Response<GetClientProductsCallback> response) {
                servicesInterface.onFinish();
                if (response.body()!= null&&response.body().getResult().equals("success")) {
                    servicesInterface.getMyServices(response.body());
                }
                else if(response.body().getResult().equals("error")){
                    servicesInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetClientProductsCallback> call, Throwable t) {
                servicesInterface.onFinish();
                servicesInterface.onFailed(t.getMessage());

            }
        });
    }

}
