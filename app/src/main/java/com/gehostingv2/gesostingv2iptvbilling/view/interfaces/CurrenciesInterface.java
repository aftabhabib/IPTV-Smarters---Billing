package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetClientCurrencyCallback;

public interface CurrenciesInterface extends BaseInterface{
    void getCurrencies(GetClientCurrencyCallback getClientCurrencyCallback);
}
