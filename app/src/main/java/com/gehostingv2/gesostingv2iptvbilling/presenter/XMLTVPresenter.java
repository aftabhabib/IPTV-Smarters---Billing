package com.gehostingv2.gesostingv2iptvbilling.presenter;
import android.content.Context;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.XMLTVCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.XMLTVInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class XMLTVPresenter {
    private XMLTVInterface xmlTvInterface;
    private Context context;
    public XMLTVPresenter(XMLTVInterface xmlTvInterface){
        this.xmlTvInterface = xmlTvInterface;
    }

    public XMLTVPresenter(XMLTVInterface xmlTvInterface,
                                   Context context){
        this.xmlTvInterface = xmlTvInterface;
        this.context = context;
    }

    public void epgXMLTV(String username, String password){
        xmlTvInterface.atStart();
//        RetrofitPost service =  Utils.retrofitObject().create(RetrofitPost.class);
        Retrofit retrofitObject = Utils.retrofitObjectXML();
        if (retrofitObject != null) {
            RetrofitPost service = retrofitObject.create(RetrofitPost.class);
            final Call<XMLTVCallback> call = service.epgXMLTV(AppConst.CONTENT_TYPE, username, password);
            call.enqueue(new Callback<XMLTVCallback>() {
                @Override
                public void onResponse(Call<XMLTVCallback> call, Response<XMLTVCallback> response) {
//                    xmlTvInterface.onFinish();
                    if (response != null && response.isSuccessful()) {
                        xmlTvInterface.epgXMLTV(response.body());
                    } else if (response.body() == null) {
                        xmlTvInterface.epgXMLTVUpdateFailed(AppConst.DB_UPDATED_STATUS_FAILED);
//                        xmlTvInterface.onFinish();
                        if (context != null)
                            xmlTvInterface.onFailed(context.getResources().getString(R.string.invalid_request));
//                        xmlTvInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override
                public void onFailure(Call<XMLTVCallback> call, Throwable t) {
                    xmlTvInterface.epgXMLTVUpdateFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    xmlTvInterface.onFinish();
                    xmlTvInterface.onFailed(t.getMessage());
                }
            });
        }
    }
}
