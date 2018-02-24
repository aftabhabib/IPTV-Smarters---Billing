package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.InvoicesPojo;

public class GetInvoicesCallback extends CommonResponseCallback{

    @SerializedName("startnumber")
    @Expose
    private Integer startnumber;
    @SerializedName("numreturned")
    @Expose
    private Integer numreturned;
    @SerializedName("invoices")
    @Expose
    private InvoicesPojo invoices;

    public Integer getStartnumber() {
        return startnumber;
    }

    public void setStartnumber(Integer startnumber) {
        this.startnumber = startnumber;
    }

    public Integer getNumreturned() {
        return numreturned;
    }

    public void setNumreturned(Integer numreturned) {
        this.numreturned = numreturned;
    }

    public InvoicesPojo getInvoices() {
        return invoices;
    }

    public void setInvoices(InvoicesPojo invoices) {
        this.invoices = invoices;
    }
}
