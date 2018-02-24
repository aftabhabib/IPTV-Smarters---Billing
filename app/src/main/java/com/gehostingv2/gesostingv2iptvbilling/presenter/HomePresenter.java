package com.gehostingv2.gesostingv2iptvbilling.presenter;


import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetSITCountCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.HomeInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePresenter {
    private HomeInterface homeInterface;

    public HomePresenter(HomeInterface homeInterface) {
        this.homeInterface = homeInterface;
    }

    public void getCLientProducts(int clientid) {
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_CLIENT_PRODUCTS_COUNT);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientid);
        jsonObject.add("params", params);
        Call<CommonResponseCallback> call = service.getCLientHomeCount(jsonObject);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                if (response.body().getResult().equals("success")&&response.body()!=null) {
                    homeInterface.getServices(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    homeInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                homeInterface.onFailed(t.getMessage());
            }
        });
    }

    public void getClientDomainCount(int clientId) {
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_CLIENT_DOMAIN_COUNT);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientId);
        jsonObject.add("params", params);
        Call<CommonResponseCallback> call = service.getCLientHomeCount(jsonObject);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                if (response.body().getResult().equals("success") && response.body()!=null) {
                    homeInterface.getClientDomains(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    homeInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                homeInterface.onFailed(t.getMessage());
            }
        });
    }

    public void getInvoices(int userID) {
        homeInterface.atStart();
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_CLIENT_INVOICES_COUNT);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("userid", userID);
        jsonObject.add("params", params);
        Call<CommonResponseCallback> call = service.getCLientHomeCount(jsonObject);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                homeInterface.onFinish();
                if (response.body().getResult().equals("success")&&response.body()!=null) {
                    homeInterface.getInvoices(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    homeInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                homeInterface.onFailed(t.getMessage());
            }
        });
    }

    public void getTickets(int clientId) {
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_CLIENT_TICKET_COUNT);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("stats", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientId);
        jsonObject.add("params", params);
        Call<CommonResponseCallback> call = service.getCLientHomeCount(jsonObject);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                if (response.body().getResult().equals("success")&&response.body()!=null) {
                    homeInterface.getTickets(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    homeInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                homeInterface.onFailed(t.getMessage());
            }
        });
    }

    public void sendNotification(String username, String regId, int clientId) {

        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_NOTIFICATION);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("custom", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("name", username);
        params.addProperty("appkey", regId);
        params.addProperty("userid", clientId);
        jsonObject.add("params", params);
        Call<CommonResponseCallback> call = service.getNotification(jsonObject);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                if (response.body().getResult().equals("success")&&response.body()!=null) {
                    homeInterface.sendNotification(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    homeInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                homeInterface.onFailed(t.getMessage());
            }
        });
    }
    public void getSITCount(int clientId){
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_SIT_COUNT);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("custom", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("clientid", clientId);
        jsonObject.add("params", params);
        Call<GetSITCountCallback> call = service.getSITCount(jsonObject);
        call.enqueue(new Callback<GetSITCountCallback>() {
            @Override
            public void onResponse(Call<GetSITCountCallback> call, Response<GetSITCountCallback> response) {
                if (response.body().getResult().equals("success")&&response.body()!=null) {
                    homeInterface.getSitCount(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    homeInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetSITCountCallback> call, Throwable t) {
                homeInterface.onFailed(t.getMessage());
            }
        });

    }
}

