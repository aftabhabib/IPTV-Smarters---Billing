package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesCountPojo {
    @SerializedName("Active")
    @Expose
    public Integer active;
    @SerializedName("Pending")
    @Expose
    public Integer pending;

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

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
