package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;

import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;


public interface NotificationInterface extends BaseInterface{
    void sendNotification(CommonResponseCallback commonResponseCallback);
}
