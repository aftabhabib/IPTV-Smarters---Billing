package com.gehostingv2.gesostingv2iptvbilling.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorPojo {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("CanContinue")
    @Expose
    private boolean  canContinue;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCanContinue() {
        return canContinue;
    }

    public void setCanContinue(boolean canContinue) {
        this.canContinue = canContinue;
    }
}
