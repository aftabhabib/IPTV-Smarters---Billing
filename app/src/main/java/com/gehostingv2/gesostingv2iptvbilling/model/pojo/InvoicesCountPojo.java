package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoicesCountPojo {
    @SerializedName("Paid")
    @Expose
    public Integer paid;
    @SerializedName("Unpaid")
    @Expose
    public Integer unpaid;
    @SerializedName("totalresults")
    @Expose
    public String totalresults;

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(Integer unpaid) {
        this.unpaid = unpaid;
    }

    public String getTotalresults() {
        return totalresults;
    }

    public void setTotalresults(String totalresults) {
        this.totalresults = totalresults;
    }
}
