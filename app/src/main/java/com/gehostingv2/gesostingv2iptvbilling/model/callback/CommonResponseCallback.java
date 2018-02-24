package com.gehostingv2.gesostingv2iptvbilling.model.callback;


import com.google.gson.annotations.SerializedName;

public class CommonResponseCallback {
    @SerializedName("result")
    private String result="";
    @SerializedName("message")
    private String message="";
    @SerializedName("totalresults")
    private String toatlResult="";

    public String getToatlResult() {
        return toatlResult;
    }

    public void setToatlResult(String toatlResult) {
        this.toatlResult = toatlResult;
    }


    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

}
