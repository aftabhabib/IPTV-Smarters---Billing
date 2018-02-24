package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.CurrenciesListPojo;

public class GetClientCurrencyCallback extends CommonResponseCallback {

    public CurrenciesListPojo getCurrenciesListCallback() {
        return currenciesListCallback;
    }

    public void setCurrenciesListCallback(CurrenciesListPojo currenciesListCallback) {
        this.currenciesListCallback = currenciesListCallback;
    }

    @SerializedName("currencies")
    @Expose
    public CurrenciesListPojo currenciesListCallback;

}
