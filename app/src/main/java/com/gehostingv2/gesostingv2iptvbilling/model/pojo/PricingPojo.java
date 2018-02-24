package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PricingPojo {
    @SerializedName("USD")
    @Expose
    public USDPojo uSD;

    public USDPojo getuSD() {
        return uSD;
    }

    public void setuSD(USDPojo uSD) {
        this.uSD = uSD;
    }
}
