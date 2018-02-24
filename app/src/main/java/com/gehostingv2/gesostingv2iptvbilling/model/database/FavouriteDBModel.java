package com.gehostingv2.gesostingv2iptvbilling.model.database;


public class FavouriteDBModel {

    private int id;
    private int streamID;
    private String type;
    private String categoryID;

    public FavouriteDBModel(){}
    public FavouriteDBModel(int streamID, String categoryID, String type ) {
        this.streamID = streamID;
        this.categoryID = categoryID;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStreamID() {
        return streamID;
    }
    public String getType() {
        return type;
    }
    public void setStreamID(int streamID) {
        this.streamID = streamID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}