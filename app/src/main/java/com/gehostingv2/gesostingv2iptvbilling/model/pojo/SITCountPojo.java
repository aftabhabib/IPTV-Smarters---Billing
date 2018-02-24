package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SITCountPojo {
    @SerializedName("servicescount")
    @Expose
    public ServicesCountPojo servicescount;
    @SerializedName("invoicescount")
    @Expose
    public InvoicesCountPojo invoicescount;
    @SerializedName("ticketscount")
    @Expose
    public TicketsCountPojo ticketscount;

    public ServicesCountPojo getServicescount() {
        return servicescount;
    }

    public void setServicescount(ServicesCountPojo servicescount) {
        this.servicescount = servicescount;
    }

    public InvoicesCountPojo getInvoicescount() {
        return invoicescount;
    }

    public void setInvoicescount(InvoicesCountPojo invoicescount) {
        this.invoicescount = invoicescount;
    }

    public TicketsCountPojo getTicketscount() {
        return ticketscount;
    }

    public void setTicketscount(TicketsCountPojo ticketscount) {
        this.ticketscount = ticketscount;
    }
}
