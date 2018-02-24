package com.gehostingv2.gesostingv2iptvbilling.presenter;

import android.content.Context;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsEpgCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.LiveStreamsEpgInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveStreamsEpgPresenter {
    private LiveStreamsEpgInterface liveStreamsEpgInteface;
    private Context context;


    public LiveStreamsEpgPresenter(LiveStreamsEpgInterface liveStreamsEpgInteface, Context context) {
        this.liveStreamsEpgInteface = liveStreamsEpgInteface;
        this.context = context;
    }

    public void liveStreamsEpg(String username, String password, Integer stream_id){
        liveStreamsEpgInteface.atStart();
        RetrofitPost service =  Utils.retrofitObjectIPTV().create(RetrofitPost.class);

        final Call<LiveStreamsEpgCallback> call = service.liveStreamsEpg(AppConst.CONTENT_TYPE,username,password,AppConst.ACTION_GET_LIVE_STREAMS_EPG,stream_id);
        call.enqueue(new Callback<LiveStreamsEpgCallback>() {
            @Override
            public void onResponse(Call<LiveStreamsEpgCallback> call, Response<LiveStreamsEpgCallback> response) {
                liveStreamsEpgInteface.onFinish();
                if(response!=null && response.isSuccessful()){
                    liveStreamsEpgInteface.liveStreamsEpg(response.body());
                }
                else if(response.body()==null){
                    liveStreamsEpgInteface.onFailed(context.getResources().getString(R.string.invalid_request));
                }
            }

            @Override
            public void onFailure(Call<LiveStreamsEpgCallback> call, Throwable t) {
                liveStreamsEpgInteface.onFinish();
                liveStreamsEpgInteface.onFailed(t.getMessage());
            }
        });
    }
}
