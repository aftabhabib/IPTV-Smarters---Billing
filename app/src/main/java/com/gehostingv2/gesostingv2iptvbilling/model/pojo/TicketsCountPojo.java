package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketsCountPojo {
    public String getTotalresults() {
        return totalresults;
    }

    public void setTotalresults(String totalresults) {
        this.totalresults = totalresults;
    }

    @SerializedName("totalresults")
    @Expose
    public String totalresults;
}

