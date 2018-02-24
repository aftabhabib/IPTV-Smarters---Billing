package com.gehostingv2.gesostingv2iptvbilling.model.callback;


import com.google.gson.annotations.SerializedName;

public class LoginWHMCSCallback extends CommonResponseCallback {
    @SerializedName("userid")
    private int userId;
    @SerializedName("passwordhash")
    private String passwordHash;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
