package com.gehostingv2.gesostingv2iptvbilling.presenter;

import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetInvoicesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.InvoicesInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoicesPresenter {
    private InvoicesInterface invoicesInterface;
    public InvoicesPresenter(InvoicesInterface invoicesInterface){
        this.invoicesInterface= invoicesInterface;
    }
    public void getInvices(int userID, int invoicesTotalCount){
        invoicesInterface.atStart();
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_CLIENT_INVOICES_COUNT);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("userid", userID);
        params.addProperty("limitstart", 0);
        params.addProperty("limitnum", invoicesTotalCount);
        jsonObject.add("params", params);

        Call<GetInvoicesCallback> call = service.getInvioces(jsonObject);
        call.enqueue(new Callback<GetInvoicesCallback>() {
            @Override
            public void onResponse(Call<GetInvoicesCallback> call, Response<GetInvoicesCallback> response) {
                invoicesInterface.onFinish();
                if (response.body().getResult().equals("success")&&response.body()!=null) {
                    invoicesInterface.getInvoices(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    invoicesInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetInvoicesCallback> call, Throwable t) {
                invoicesInterface.onFinish();
                invoicesInterface.onFailed(t.getMessage());
            }
        });
    }




    public void getInvicesTotalCount(int userID){
        invoicesInterface.atStart();
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_CLIENT_INVOICES_COUNT);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("userid", userID);
        jsonObject.add("params", params);
        Call<CommonResponseCallback> call = service.getInviocesTotalCount(jsonObject);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                invoicesInterface.onFinish();
                if (response.body().getResult().equals("success")&&response.body()!=null) {
                    invoicesInterface.getInvoicesTotalCount(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    invoicesInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                invoicesInterface.onFinish();
                invoicesInterface.onFailed(t.getMessage());
            }
        });
    }
}
