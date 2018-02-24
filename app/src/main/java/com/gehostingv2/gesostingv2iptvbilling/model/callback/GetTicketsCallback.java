package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.TicketsPojo;

public class GetTicketsCallback extends CommonResponseCallback {

    @SerializedName("startnumber")
    @Expose
    public Integer startnumber;
    @SerializedName("numreturned")
    @Expose
    public Integer numreturned;
    @SerializedName("tickets")
    @Expose
    public TicketsPojo tickets;


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

    public TicketsPojo getTickets() {
        return tickets;
    }

    public void setTickets(TicketsPojo tickets) {
        this.tickets = tickets;
    }
}
