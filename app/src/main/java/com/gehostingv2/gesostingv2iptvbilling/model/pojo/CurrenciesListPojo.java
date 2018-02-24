package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrenciesListPojo {
    public ArrayList<CurrencyPojo> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(ArrayList<CurrencyPojo> currencyList) {
        this.currencyList = currencyList;
    }

    @SerializedName("currency")
    @Expose
    public ArrayList<CurrencyPojo> currencyList = null;

}
