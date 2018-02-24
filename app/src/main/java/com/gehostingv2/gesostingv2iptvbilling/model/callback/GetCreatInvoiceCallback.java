package com.gehostingv2.gesostingv2iptvbilling.model.callback;


import com.google.gson.annotations.SerializedName;

public class GetCreatInvoiceCallback extends CommonResponseCallback{
    @SerializedName("invoiceid")
    private int  invoiceId;
    @SerializedName("status")
    private String  stausInvoice;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getStausInvoice() {
        return stausInvoice;
    }

    public void setStausInvoice(String stausInvoice) {
        this.stausInvoice = stausInvoice;
    }
}
