package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TicketsPojo {
    @SerializedName("ticket")
    public ArrayList<TicketDetailPojo> ticket = null;

    public ArrayList<TicketDetailPojo> getTicket() {
        return ticket;
    }

    public void setTicket(ArrayList<TicketDetailPojo> ticket) {
        this.ticket = ticket;
    }
}
