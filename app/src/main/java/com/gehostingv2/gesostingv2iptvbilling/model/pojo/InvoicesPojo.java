package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InvoicesPojo {
    @SerializedName("invoice")
    @Expose
    private ArrayList<InvoicesDetailPojo> invoice = null;

    public ArrayList<InvoicesDetailPojo> getInvoice() {
        return invoice;
    }

    public void setInvoice(ArrayList<InvoicesDetailPojo> invoice) {
        this.invoice = invoice;
    }
}
