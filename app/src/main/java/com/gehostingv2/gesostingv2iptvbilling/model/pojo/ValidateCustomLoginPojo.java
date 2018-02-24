package com.gehostingv2.gesostingv2iptvbilling.model.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateCustomLoginPojo {
    @SerializedName("clientid")
    @Expose
    private Integer clientid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
