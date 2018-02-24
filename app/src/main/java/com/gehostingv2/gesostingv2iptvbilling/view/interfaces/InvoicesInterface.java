package com.gehostingv2.gesostingv2iptvbilling.view.interfaces;

import com.gehostingv2.gesostingv2iptvbilling.model.callback.CommonResponseCallback;
import com.gehostingv2.gesostingv2iptvbilling.model.callback.GetInvoicesCallback;

public interface InvoicesInterface extends BaseInterface{
    void getInvoices(GetInvoicesCallback getInvoicesCallback);
    void getInvoicesTotalCount(CommonResponseCallback commonResponseCallback);
}
