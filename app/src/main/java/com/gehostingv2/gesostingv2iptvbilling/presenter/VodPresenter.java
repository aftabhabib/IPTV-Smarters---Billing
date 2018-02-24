package com.gehostingv2.gesostingv2iptvbilling.presenter;


import android.content.Context;


import com.gehostingv2.gesostingv2iptvbilling.R;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.AppConst;
import com.gehostingv2.gesostingv2iptvbilling.miscelleneious.common.Utils;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.VodCategoriesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.VodInfoCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.VodStreamsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;
import com.gehostingv2.gesostingv2iptvbilling.model.webrequest.RetrofitPost;
//import com.lucassuto.lucassutoiptvbilling.view.adapter.FavouritesAdapter;
import com.gehostingv2.gesostingv2iptvbilling.view.interfaces.VodInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class VodPresenter {
    private VodInterface vodInteface;
    private Context context;

    public VodPresenter(VodInterface vodInteface, Context context) {
        this.vodInteface = vodInteface;
        this.context = context;
    }


    public void vodCategories(String username, String password) {
        vodInteface.atStart();
        RetrofitPost service = Utils.retrofitObjectIPTV().create(RetrofitPost.class);

        Call<List<VodCategoriesCallback>> call = service.vodCategories(AppConst.CONTENT_TYPE, username, password, AppConst.ACTION_GET_VOD_CATEGORIES);
        call.enqueue(new Callback<List<VodCategoriesCallback>>() {
            @Override
            public void onResponse(Call<List<VodCategoriesCallback>> call, Response<List<VodCategoriesCallback>> response) {
                if (response != null && response.isSuccessful()) {
                    vodInteface.onFinish();
//                    vodInteface.vodCategories(response.body());
                } else if (response.body() == null) {
                    vodInteface.onFinish();
                    if (context != null)
                        vodInteface.onFailed(context.getResources().getString(R.string.invalid_request));
                }
            }

            @Override
            public void onFailure(Call<List<VodCategoriesCallback>> call, Throwable t) {
                vodInteface.onFinish();
                vodInteface.onFailed(t.getMessage());
            }
        });
    }

    public void vodStreams(String username, String password, String category_id,
                           final ArrayList<FavouriteDBModel> allFavouritesVOD) {
        vodInteface.atStart();
        RetrofitPost service = Utils.retrofitObjectIPTV().create(RetrofitPost.class);
        Call<List<VodStreamsCallback>> call;
        call = service.vodStreams(AppConst.CONTENT_TYPE, username, password, AppConst.ACTION_GET_VOD_STREAMS, category_id);
        call.enqueue(new Callback<List<VodStreamsCallback>>() {
            @Override
            public void onResponse(Call<List<VodStreamsCallback>> call, Response<List<VodStreamsCallback>> response) {
                vodInteface.onFinish();
                if (response != null && response.isSuccessful()) {
//                    vodInteface.vodStreams(response.body(),allFavouritesVOD);
                } else if (response.body() == null) {
                    if (context != null)
                        vodInteface.onFailed(context.getResources().getString(R.string.invalid_request));
                }
            }

            @Override
            public void onFailure(Call<List<VodStreamsCallback>> call, Throwable t) {
                vodInteface.onFinish();
                vodInteface.onFailed(t.getMessage());
            }
        });
    }

    //    public void vodInfo(String username, String password, int vod_id, final VodAdapter.MyViewHolder holder,
//                        final FavouritesAdapter.MyViewHolderVOD viewHolderVOD){
//        vodInteface.atStart();
//        RetrofitPost service =  Utils.retrofitObjectIPTV().create(RetrofitPost.class);
//        final Call<VodInfoCallback> call;
//        call = service.vodInfo(AppConst.CONTENT_TYPE, username, password, AppConst.ACTION_GET_VOD_INFO, vod_id);
//        call.enqueue(new Callback<VodInfoCallback>() {
//            @Override
//            public void onResponse(Call<VodInfoCallback> call, Response<VodInfoCallback> response) {
//                vodInteface.onFinish();
//                if(response!=null && response.isSuccessful()){
//                    vodInteface.vodInfo(response.body(),holder,viewHolderVOD);
//                }
//                else if(response.body()==null){
//                    vodInteface.onFailed(AppConst.INVALID_REQUEST);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<VodInfoCallback> call, Throwable t) {
//                vodInteface.onFinish();
//                vodInteface.onFailed(t.getMessage());
//            }
//        });
//    }
    public void vodInfo(String username,
                        String password,
                        int streamId) {
        vodInteface.atStart();

//        RetrofitPost service =  Utils.retrofitObject().create(RetrofitPost.class);
        Retrofit retrofitObject = Utils.retrofitObject(context);
        if (retrofitObject != null) {
            RetrofitPost service = retrofitObject.create(RetrofitPost.class);
            final Call<VodInfoCallback> call;
            call = service.vodInfo(AppConst.CONTENT_TYPE, username, password, AppConst.ACTION_GET_VOD_INFO, streamId);
            call.enqueue(new Callback<VodInfoCallback>() {
                @Override
                public void onResponse(Call<VodInfoCallback> call, Response<VodInfoCallback> response) {
                    vodInteface.onFinish();
                    if (response != null && response.isSuccessful()) {
                        vodInteface.vodInfo(response.body());
                    } else if (response.body() == null) {
                        if (context != null)
                            vodInteface.onFailed(context.getResources().getString(R.string.invalid_request));
                    }
                }

                @Override
                public void onFailure(Call<VodInfoCallback> call, Throwable t) {
                    vodInteface.onFinish();
                    vodInteface.onFailed(t.getMessage());
                }
            });
        }
    }
}