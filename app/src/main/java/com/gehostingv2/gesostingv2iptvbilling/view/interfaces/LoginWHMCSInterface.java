package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;

import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.LoginWHMCSCallback;
public interface LoginWHMCSInterface extends BaseInterface{
    void validateLogin(LoginWHMCSCallback loginWHMCSCallback);
    void sendNotification(CommonResponseCallback commonResponseCallback);
}
