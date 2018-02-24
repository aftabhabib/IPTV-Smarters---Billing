package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.ValidationIPTVCallback;

public interface LoginIPTVInterface extends BaseInterface{
    void validateLogin(ValidationIPTVCallback validationIPTVCallback, String validateLogin);
}
