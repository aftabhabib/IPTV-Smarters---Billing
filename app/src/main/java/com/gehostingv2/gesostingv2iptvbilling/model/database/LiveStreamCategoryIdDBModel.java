package com.gehostingv2.gesostingv2iptvbilling.model.database;


import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LiveStreamCategoryIdDBModel implements Serializable{

    private int id;
    private String liveStreamCategoryID;
    private String liveStreamCategoryName;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    private int parentId;


    public LiveStreamCategoryIdDBModel(){}
    public LiveStreamCategoryIdDBModel(String liveStreamCategoryID, String liveStreamCategoryName, int parentId) {
        this.liveStreamCategoryID = liveStreamCategoryID;
        this.liveStreamCategoryName = liveStreamCategoryName;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLiveStreamCategoryID() {
        return liveStreamCategoryID;
    }

    public void setLiveStreamCategoryID(String liveStreamCategoryID) {
        this.liveStreamCategoryID = liveStreamCategoryID;
    }

    public String getLiveStreamCategoryName() {
        return liveStreamCategoryName;
    }

    public void setLiveStreamCategoryName(String liveStreamCategoryName) {
        this.liveStreamCategoryName = liveStreamCategoryName;
    }



    public static List<LiveStreamCategoryIdDBModel> createMovies(int itemCount,
                                                                 Context context,
                                                                 LiveStreamDBHandler liveStreamDBHandler,
                                                                 int startValue,
                                                                 int endValue) {

        ArrayList<LiveStreamCategoryIdDBModel> movieList = liveStreamDBHandler.getMovieCategoriesinRange(startValue, endValue);
        ArrayList<LiveStreamCategoryIdDBModel> movieList1 = listOfMOview(movieList,
                context, liveStreamDBHandler);
        return movieList1;
    }

    public static ArrayList<LiveStreamCategoryIdDBModel> listOfMOview(ArrayList<LiveStreamCategoryIdDBModel> movieList, Context context,
                                                                      LiveStreamDBHandler liveStreamDBHandler) {

        if (context != null) {
            liveStreamDBHandler = new LiveStreamDBHandler(context);
            ArrayList<LiveStreamCategoryIdDBModel> moviesCategory = movieList;
            ArrayList<LiveStreamCategoryIdDBModel> moviesCategoryFinal = new ArrayList<>();
            for (LiveStreamCategoryIdDBModel list : moviesCategory) {
                String categoryID = list.getLiveStreamCategoryID();
                ArrayList<LiveStreamsDBModel> listChannels = liveStreamDBHandler.getAllLiveStreasWithCategoryId(categoryID, "movie");
                ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal = liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(categoryID);

                for (LiveStreamCategoryIdDBModel listSub : subCategoryListFinal) {
                    String parentId = listSub.getLiveStreamCategoryID();
                    ArrayList<LiveStreamsDBModel> listChannels1 = liveStreamDBHandler.getAllLiveStreasWithCategoryId(String.valueOf(parentId), "movie");
                    if (listChannels1.size() > 0) {
                        moviesCategoryFinal.add(list);
                        break;
                    }
                }
                if (listChannels.size() > 0) {   // || subCategoryListFinal.size()>0
                    moviesCategoryFinal.add(list);
                }
            }
            return moviesCategoryFinal;
        }
        return null;
    }
}
