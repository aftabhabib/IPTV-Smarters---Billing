package com.gehostingv2.gesostingv2iptvbilling.presenter;

import com.google.gson.JsonObject;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.NotificationInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter {
    private NotificationInterface notificationInterface;

    public NotificationPresenter(NotificationInterface notificationInterface){
        this.notificationInterface = notificationInterface;
    }


    public void sendNotification(String regId, int clientId) {
        RetrofitPost service = Utils.retrofitObjectWHMCS().create(RetrofitPost.class);
        JsonObject postParam = new JsonObject();
        JsonObject jsonObject = Utils.jsonDataToSend(postParam);
        jsonObject.addProperty("command", AppConst.ACTION_GET_NOTIFICATION);
        jsonObject.addProperty("responsetype", AppConst.RESPONSE_TYPE);
        jsonObject.addProperty("custom", AppConst.STATS);
        JsonObject params = new JsonObject();
        params.addProperty("appkey", regId);
        params.addProperty("userid", clientId);
        params.addProperty("firebaseNotificationWebApiKey", AppConst.FIREBASE_NOTIFICATION_WEB_API_KEY);
        jsonObject.add("params", params);
        Call<CommonResponseCallback> call = service.getNotification(jsonObject);
        call.enqueue(new Callback<CommonResponseCallback>() {
            @Override
            public void onResponse(Call<CommonResponseCallback> call, Response<CommonResponseCallback> response) {
                if (response.body().getResult().equals("success")&&response.body()!=null) {
                    notificationInterface.sendNotification(response.body());
                } else if (response.body().getResult().equals("error")&&response.body()!=null) {
                    notificationInterface.onFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseCallback> call, Throwable t) {
                notificationInterface.onFailed(t.getMessage());
            }
        });
    }
}
