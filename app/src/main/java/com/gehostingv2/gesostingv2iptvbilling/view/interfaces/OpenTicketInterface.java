package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetDepartmentCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetOpenTicketCallback;

public interface OpenTicketInterface extends BaseInterface{
    void getSupportDepartmet(GetDepartmentCallback getDepartmentCallback);
    void OpenSupportDeartment(GetOpenTicketCallback getOpenTicketCallback);
}
