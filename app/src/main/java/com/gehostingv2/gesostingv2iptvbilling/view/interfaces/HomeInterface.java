package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;


import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetSITCountCallback;

public interface HomeInterface extends BaseInterface {
    void getServices(CommonResponseCallback getClientProductsCallback);
    void getClientDomains(CommonResponseCallback getQuotesCallback);
    void getInvoices(CommonResponseCallback commonResponseCallback);
    void getTickets(CommonResponseCallback commonResponseCallback);
    void sendNotification(CommonResponseCallback commonResponseCallback);
    void getSitCount(GetSITCountCallback getSITCountCallback);
}
