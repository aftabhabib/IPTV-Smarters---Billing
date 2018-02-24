//package com.happyboxtv.happyboxtviptvbilling.presenter;
//
//import android.content.Context;
//import android.widget.TextView;
//
//
//import com.happyboxtv.happyboxtviptvbilling.miscelleneious.common.AppConst;
//import com.happyboxtv.happyboxtviptvbilling.miscelleneious.common.Utils;
//import com.happyboxtv.happyboxtviptvbilling.model.callback.LiveStreamCategoriesCallback;
//import com.happyboxtv.happyboxtviptvbilling.model.callback.LiveStreamsCallback;
//import com.happyboxtv.happyboxtviptvbilling.model.callback.LiveStreamsEpgCallback;
//import com.happyboxtv.happyboxtviptvbilling.model.database.FavouriteDBModel;
//import com.happyboxtv.happyboxtviptvbilling.model.webrequest.RetrofitPost;
//import com.happyboxtv.happyboxtviptvbilling.view.interfaces.LiveStreamsInterface;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class LiveStreamsPresenter {
//    private LiveStreamsInterface liveStreamsInteface;
//    private Context context;
//    public LiveStreamsPresenter(LiveStreamsInterface liveStreamsInteface){
//        this.liveStreamsInteface = liveStreamsInteface;
//    }
//
//
//    public void liveStreamCategories(String username, String password){
//        liveStreamsInteface.atStart();
//        RetrofitPost service =  Utils.retrofitObjectIPTV().create(RetrofitPost.class);
//
//
//        Call<List<LiveStreamCategoriesCallback>> call = service.liveStreamCategories(AppConst.CONTENT_TYPE,username,password,AppConst.ACTION_GET_LIVE_CATEGORIES);
//        call.enqueue(new Callback<List<LiveStreamCategoriesCallback>>() {
//            @Override
//            public void onResponse(Call<List<LiveStreamCategoriesCallback>> call, Response<List<LiveStreamCategoriesCallback>> response) {
//                if(response!=null && response.isSuccessful()){
//                    liveStreamsInteface.onFinish();
//                    liveStreamsInteface.liveStreamCategories(response.body());
//                }
//                else if(response.body()==null){
//                    liveStreamsInteface.onFinish();
//                    liveStreamsInteface.onFailed(AppConst.INVALID_REQUEST);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<LiveStreamCategoriesCallback>> call, Throwable t) {
//                liveStreamsInteface.onFinish();
//                liveStreamsInteface.onFailed(t.getMessage());
//            }
//        });
//    }
//
//    public void liveStreams(String username, String password, String category_id, final ArrayList<FavouriteDBModel> allFavourites){
//        liveStreamsInteface.atStart();
//        RetrofitPost service =  Utils.retrofitObjectIPTV().create(RetrofitPost.class);
//        Call<List<LiveStreamsCallback>> call;
//        call = service.liveStreams(AppConst.CONTENT_TYPE, username, password, AppConst.ACTION_GET_LIVE_STREAMS, category_id);
//        call.enqueue(new Callback<List<LiveStreamsCallback>>() {
//            @Override
//            public void onResponse(Call<List<LiveStreamsCallback>> call, Response<List<LiveStreamsCallback>> response) {
//                liveStreamsInteface.onFinish();
//                if(response!=null && response.isSuccessful()){
//                    liveStreamsInteface.liveStreams(response.body(),allFavourites);
//                }
//                else if(response.body()==null){
//                    liveStreamsInteface.onFailed(AppConst.INVALID_REQUEST);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<LiveStreamsCallback>> call, Throwable t) {
//                liveStreamsInteface.onFinish();
//                liveStreamsInteface.onFailed(t.getMessage());
//            }
//        });
//    }
//    public void liveStreamsEpg(String username, String password, Integer stream_id, final TextView tvActiveChannel, final TextView tvNextChannel){
//        liveStreamsInteface.atStart();
//        RetrofitPost service =  Utils.retrofitObjectIPTV().create(RetrofitPost.class);
//
//        final Call<LiveStreamsEpgCallback> call = service.liveStreamsEpg(AppConst.CONTENT_TYPE,username,password,AppConst.ACTION_GET_LIVE_STREAMS_EPG,stream_id);
//        call.enqueue(new Callback<LiveStreamsEpgCallback>() {
//            @Override
//            public void onResponse(Call<LiveStreamsEpgCallback> call, Response<LiveStreamsEpgCallback> response) {
//                liveStreamsInteface.onFinish();
//                if(response!=null && response.isSuccessful()){
//                    liveStreamsInteface.liveStreamsEpg(response.body(),tvActiveChannel, tvNextChannel);
//                }
//                else if(response.body()==null){
//                    liveStreamsInteface.onFailed(AppConst.INVALID_REQUEST);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LiveStreamsEpgCallback> call, Throwable t) {
//                liveStreamsInteface.onFinish();
//                liveStreamsInteface.onFailed(t.getMessage());
//            }
//        });
//    }
//}