package com.gehostingv2.gesostingv2iptvbilling.presenter;

import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetClientCurrencyCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.CurrenciesInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrenciesPresenter {
    CurrenciesInterface currenciesInterface;
    public CurrenciesPresenter(CurrenciesInterface currenciesInterface){
        this.currenciesInterface = currenciesInterface;

    }


    public void getClientCurrencies(){
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_CURRENCY_WHMCS);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();

        jsonObject.add("params", params);
        Call<GetClientCurrencyCallback> call = service.getCurrency(jsonObject);

        call.enqueue(new Callback<GetClientCurrencyCallback>() {
            @Override
            public void onResponse(Call<GetClientCurrencyCallback> call, Response<GetClientCurrencyCallback> response) {
                if (response != null) {
                    currenciesInterface.getCurrencies(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<GetClientCurrencyCallback> call, Throwable t) {

            }
        });
    }


}
