package com.gehostingv2.gesostingv2iptvbilling.model.callback;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.ValidateCustomLoginPojo;

public class ValidateCustomLoginCallback {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private ValidateCustomLoginPojo data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ValidateCustomLoginPojo getData() {
        return data;
    }

    public void setData(ValidateCustomLoginPojo data) {
        this.data = data;
    }
}
