package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InvoiceItemListPojo {
    @SerializedName("item")
    @Expose
    public ArrayList<InvoiceItemPojo> invoiceItemArrayList = null;

    public ArrayList<InvoiceItemPojo> getInvoiceItemArrayList() {
        return invoiceItemArrayList;
    }

    public void setInvoiceItemArrayList(ArrayList<InvoiceItemPojo> invoiceItemArrayList) {
        this.invoiceItemArrayList = invoiceItemArrayList;
    }
}
