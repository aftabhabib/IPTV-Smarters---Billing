package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.VodInfoPojo;


public class VodInfoCallback {

    @SerializedName("info")
    @Expose
    private VodInfoPojo info;

    public VodInfoPojo getInfo() {
        return info;
    }

    public void setInfo(VodInfoPojo info) {
        this.info = info;
    }

}