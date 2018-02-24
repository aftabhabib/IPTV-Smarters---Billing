package com.gehostingv2.gesostingv2iptvbilling.presenter;

import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetTicketsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.TicketInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketPresenter {
    TicketInterface ticketInterface;
    public TicketPresenter(TicketInterface ticketInterface){
        this.ticketInterface= ticketInterface;
    }
    public void getTickets(int clientid, final RecyclerView myRecyclerView){
        ticketInterface.atStart();
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_TICKETS);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientid);
        jsonObject.add("params", params);
        Call<GetTicketsCallback> call = service.getTickets(jsonObject);
        call.enqueue(new Callback<GetTicketsCallback>() {
            @Override
            public void onResponse(Call<GetTicketsCallback> call, Response<GetTicketsCallback> response) {
                ticketInterface.onFinish();
                if (response.body().getResult().equals("success")) {
                    ticketInterface.getTickets(response.body(), myRecyclerView);
                } else if (response.body().getResult().equals("error")) {
                    ticketInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetTicketsCallback> call, Throwable t) {
                ticketInterface.onFinish();
                ticketInterface.onFailed(t.getMessage());
            }
        });
    }
}
