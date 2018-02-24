package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOpenTicketCallback extends CommonResponseCallback{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("tid")
    @Expose
    private int ticketId;
    @SerializedName("c")
    @Expose
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
