package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginCallback {

    @SerializedName("user_info")
    @Expose
    private UserLoginInfoCallback userLoginInfo;
    @SerializedName("server_info")
    @Expose
    private ServerInfoCallback serverInfo;

    public UserLoginInfoCallback getUserLoginInfo() {
        return userLoginInfo;
    }

    public void setUserInfo(UserLoginInfoCallback userLoginInfo) {
        this.userLoginInfo = userLoginInfo;
    }
    public ServerInfoCallback getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfoCallback serverInfo) {
        this.serverInfo = serverInfo;
    }

}