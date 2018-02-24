package com.gehostingv2.gesostingv2iptvbilling.model.callback;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.SITCountPojo;

public class GetSITCountCallback extends CommonResponseCallback{
    @SerializedName("data")
    @Expose
    public SITCountPojo sitCount;

    public SITCountPojo getSitCount() {
        return sitCount;
    }

    public void setSitCount(SITCountPojo sitCount) {
        this.sitCount = sitCount;
    }
}
