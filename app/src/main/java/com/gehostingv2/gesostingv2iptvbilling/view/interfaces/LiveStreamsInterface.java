package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;

import android.widget.TextView;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamCategoriesCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LiveStreamsEpgCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.database.FavouriteDBModel;

import java.util.ArrayList;
import java.util.List;


public interface LiveStreamsInterface extends BaseInterface{
    void liveStreamCategories(List<LiveStreamCategoriesCallback> liveStreamCategoriesCallback);
    void liveStreams(List<LiveStreamsCallback> liveStreamsCallback, ArrayList<FavouriteDBModel> allFavourites);
    void liveStreamsEpg(LiveStreamsEpgCallback liveStreamsEpgCallback, TextView tvActiveChannel, TextView tvNextChannel);
}
