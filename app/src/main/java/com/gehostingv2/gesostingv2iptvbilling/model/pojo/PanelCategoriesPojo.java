package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PanelCategoriesPojo {

    @SerializedName("movie")
    @Expose
    private ArrayList<PanelMoviePojo> movie = null;
    @SerializedName("live")
    @Expose
    private ArrayList<PanelLivePojo> live = null;

    public ArrayList<PanelMoviePojo> getMovie() {
        return movie;
    }

    public void setMovie(ArrayList<PanelMoviePojo> movie) {
        this.movie = movie;
    }

    public ArrayList<PanelLivePojo> getLive() {
        return live;
    }

    public void setLive(ArrayList<PanelLivePojo> live) {
        this.live = live;
    }

}