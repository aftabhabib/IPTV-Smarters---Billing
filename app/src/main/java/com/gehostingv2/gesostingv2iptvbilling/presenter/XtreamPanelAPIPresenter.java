package com.gehostingv2.gesostingv2iptvbilling.presenter;

import android.content.Context;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.XtreamPanelAPICallback;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.XtreamPanelAPIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class XtreamPanelAPIPresenter {
    private XtreamPanelAPIInterface xtreamPanelAPIInterface;
    private Context context;
    public XtreamPanelAPIPresenter(XtreamPanelAPIInterface xtreamPanelAPIInterface){
        this.xtreamPanelAPIInterface = xtreamPanelAPIInterface;
    }

    public XtreamPanelAPIPresenter(XtreamPanelAPIInterface xtreamPanelAPIInterface,
                                   Context context){
        this.xtreamPanelAPIInterface = xtreamPanelAPIInterface;
        this.context = context;
    }

    public void panelAPI(final String username, String password){
        Retrofit retrofitObject = Utils.retrofitObjectIPTVWithTimeRaise();
        if (retrofitObject != null) {
            RetrofitPost service = retrofitObject.create(RetrofitPost.class);
            final Call<XtreamPanelAPICallback> call = service.panelAPI(AppConst.CONTENT_TYPE, username, password);
            call.enqueue(new Callback<XtreamPanelAPICallback>() {
                @Override
                public void onResponse(Call<XtreamPanelAPICallback> call, Response<XtreamPanelAPICallback> response) {
                    if (response != null && response.body()!=null && response.isSuccessful()) {
                        xtreamPanelAPIInterface.panelAPI(response.body(),username);
                    } else if (response.body() == null) {
                        xtreamPanelAPIInterface.panelApiFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        xtreamPanelAPIInterface.onFinish();
                        if (context != null)
                            xtreamPanelAPIInterface.onFailed(context.getResources().getString(R.string.invalid_request));
                    }
                }

                @Override
                public void onFailure(Call<XtreamPanelAPICallback> call, Throwable t) {
                    xtreamPanelAPIInterface.panelApiFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    xtreamPanelAPIInterface.onFinish();
                    xtreamPanelAPIInterface.onFailed(t.getMessage());
                }
            });
        }
    }

//    public void epgXMLTV(String username, String password){
//        xtreamPanelAPIInterface.atStart();
//        Retrofit retrofitObject = Utils.retrofitObjectXML();
//        if (retrofitObject != null) {
//            RetrofitPost service = retrofitObject.create(RetrofitPost.class);
//            final Call<XMLTVCallback> call = service.epgXMLTV(AppConst.CONTENT_TYPE, username, password);
//            call.enqueue(new Callback<XMLTVCallback>() {
//                @Override
//                public void onResponse(Call<XMLTVCallback> call, Response<XMLTVCallback> response) {
////                    xmlTvInterface.onFinish();
//                    if (response != null && response.isSuccessful()) {
//                        xtreamPanelAPIInterface.epgXMLTV(response.body());
//                    } else if (response.body() == null) {
////                        xmlTvInterface.onFinish();
//                        xtreamPanelAPIInterface.onFailed(AppConst.INVALID_REQUEST);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<XMLTVCallback> call, Throwable t) {
//                    xtreamPanelAPIInterface.onFinish();
//                    xtreamPanelAPIInterface.onFailed(t.getMessage());
//                }
//            });
//        }
//    }


    //code for RxJava to fetch details

//    public void panelAPIRx(String username, String password){
//        Retrofit retrofitObject = Utils.retrofitObjectIPTVWithTimeRaiseRx();
//        if (retrofitObject != null) {
//            RetrofitPost service = retrofitObject.create(RetrofitPost.class);
//            service.panelAPIRx(AppConst.CONTENT_TYPE, username, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<XtreamPanelAPICallback>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(XtreamPanelAPICallback xtreamPanelAPICallback) {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        }
//    }
}
