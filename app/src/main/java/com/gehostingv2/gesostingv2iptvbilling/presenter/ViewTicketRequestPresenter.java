package com.gehostingv2.gesostingv2iptvbilling.presenter;

import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetViewRequestReplyCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.adapter.TicketsAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.ViewTicketRequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTicketRequestPresenter {
    ViewTicketRequestInterface viewTicketRequestInterface;
    public  ViewTicketRequestPresenter(ViewTicketRequestInterface viewTicketRequestInterface){
        this.viewTicketRequestInterface = viewTicketRequestInterface;
    }

    public void getTickets(int ticketId) {
        viewTicketRequestInterface.atStart();
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_TICKET);
        jsonObject.addProperty("responsetype","json") ;
        JsonObject params = new JsonObject();
        params.addProperty("ticketid",ticketId) ;
        jsonObject.add("params", params) ;
        Call<GetViewRequestReplyCallback> call = service.getTicket(jsonObject);
        call.enqueue(new Callback<GetViewRequestReplyCallback>() {
            @Override
            public void onResponse(Call<GetViewRequestReplyCallback> call, Response<GetViewRequestReplyCallback> response) {
                viewTicketRequestInterface.onFinish();
                if(response!=null&& response.isSuccessful()&&response.body().getResult().equals("success")) {
                    viewTicketRequestInterface.getTicket(response.body());
                }
                else if(response.body()==null|| response.body().getResult().equals("error")){
                    viewTicketRequestInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetViewRequestReplyCallback> call, Throwable t) {
                viewTicketRequestInterface.onFinish();
                viewTicketRequestInterface.onFailed(t.getMessage());
            }
        });
    }
    public void getTicketsForDepttName(int ticketId, final TicketsAdapter.ViewHolder holder, final int position) {
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_TICKET);
        jsonObject.addProperty("responsetype","json") ;
        JsonObject params = new JsonObject();
        params.addProperty("ticketid",ticketId) ;
        jsonObject.add("params", params) ;
        Call<GetViewRequestReplyCallback> call = service.getTicket(jsonObject);
        call.enqueue(new Callback<GetViewRequestReplyCallback>() {
            @Override
            public void onResponse(Call<GetViewRequestReplyCallback> call, Response<GetViewRequestReplyCallback> response) {
                viewTicketRequestInterface.atStart();
                if(response!=null&& response.isSuccessful()&&response.body().getResult().equals("success")) {
                    viewTicketRequestInterface.getTicketForDepptName(response.body(),holder,position);
                    viewTicketRequestInterface.onFinish();
                }
                else if(response.body()==null|| response.body().getResult().equals("error")){
                    viewTicketRequestInterface.onFinish();
                    viewTicketRequestInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetViewRequestReplyCallback> call, Throwable t) {
                viewTicketRequestInterface.onFinish();
                viewTicketRequestInterface.onFailed(t.getMessage());
            }
        });
    }
    public void addTicketReply(int ticketid, final String message, int clientId){ //, String b, final File file
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_ADD_TICKET_REPLY);
        jsonObject.addProperty("responsetype","json") ;
        JsonObject params = new JsonObject();
        params.addProperty("ticketid",ticketid) ;
        params.addProperty("message",message) ;
        params.addProperty("clientid",clientId) ;
        jsonObject.add("params", params) ;
        Call<CommonResponseCallback> call = service.addTicketReply(jsonObject);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                viewTicketRequestInterface.atStart();
                if (response.body() != null) {
                    viewTicketRequestInterface.onFinish();
                    viewTicketRequestInterface.addTicketReply(response.body(),message);

                } else {
                    viewTicketRequestInterface.onFinish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                viewTicketRequestInterface.onFinish();
                viewTicketRequestInterface.onFailed(t.getMessage());
            }
        });
    }
    public void updateTicket(int tickketId, int clientId) {
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_UPDATE_TICKET);
        jsonObject.addProperty("responsetype","json") ;
        JsonObject params = new JsonObject();
        params.addProperty("ticketid",tickketId) ;
        params.addProperty("clientid",clientId) ;
        jsonObject.add("params", params) ;
        Call<CommonResponseCallback> call = service.updateTicket(jsonObject);

        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                viewTicketRequestInterface.atStart();
                if (response.body() != null) {
                    viewTicketRequestInterface.atStart();
                    viewTicketRequestInterface.updateTicketReply(response.body());
                    viewTicketRequestInterface.onFinish();
                } else {
                    viewTicketRequestInterface.onFinish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                viewTicketRequestInterface.onFinish();
            }
        });
    }

    public void updateTicketClosed(int tickketId, int clientId, String status) {
        RetrofitPost service =  Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_UPDATE_TICKET);
        jsonObject.addProperty("responsetype","json") ;
        JsonObject params = new JsonObject();
        params.addProperty("ticketid",tickketId) ;
        params.addProperty("clientid",clientId) ;
        params.addProperty("status",status) ;
        jsonObject.add("params", params) ;
        Call<CommonResponseCallback> call = service.updateTicket(jsonObject);

        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                viewTicketRequestInterface.atStart();
                if (response.body() != null) {
                    viewTicketRequestInterface.atStart();
                    viewTicketRequestInterface.updateTicketClose(response.body());
                    viewTicketRequestInterface.onFinish();
                } else {
                    viewTicketRequestInterface.onFinish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                viewTicketRequestInterface.onFinish();
            }
        });
    }
}
