package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;

import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetIPTVCredentialsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.MyDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;

public interface ClientDetailInterface extends  BaseInterface{
    void getClientDetails(MyDetailsCallback myDetailsCallback);
    void getIPTVCredentials(GetIPTVCredentialsCallback getIPTVCredentialsCallback);
    void getIPTVDetails(ValidationIPTVCallback validationIPTVCallback, String validateLogin);
}
