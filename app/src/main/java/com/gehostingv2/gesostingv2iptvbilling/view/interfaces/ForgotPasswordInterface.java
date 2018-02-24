package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.ClientDetailsCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;

public interface ForgotPasswordInterface extends BaseInterface{
    void getClientsDetail(ClientDetailsCallback myDetailsCallback, String email);
    void sendMail(CommonResponseCallback commonResponseCallback, int id);
    void validatePasscode(CommonResponseCallback commonResponseCallback);
    void updatePassword(CommonResponseCallback commonResponseCallback);
}
