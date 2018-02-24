package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RepliesListPojo {
    @SerializedName("reply")
    @Expose
    public ArrayList<ReplyPojo> reply = null;

    public ArrayList<ReplyPojo> getReply() {
        return reply;
    }

    public void setReply(ArrayList<ReplyPojo> reply) {
        this.reply = reply;
    }
}
