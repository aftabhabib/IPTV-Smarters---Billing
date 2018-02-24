package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelAvailableChannelsPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelCategoriesPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelServerInfoPojo;
import com.gehostingv2.gesostingv2iptvbilling.model.pojo.PanelUserInfoPojo;

import java.util.Map;

public class XtreamPanelAPICallback {

    @SerializedName("user_info")
    @Expose
    private PanelUserInfoPojo userInfo;
    @SerializedName("server_info")
    @Expose
    private PanelServerInfoPojo serverInfo;
    @SerializedName("categories")
    @Expose
    private PanelCategoriesPojo categories;
    @SerializedName("available_channels")
    @Expose
//    private PanelAvailableChannelsPojo availableChannels;
    public Map<String, PanelAvailableChannelsPojo> availableChannels;


    public PanelUserInfoPojo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(PanelUserInfoPojo userInfo) {
        this.userInfo = userInfo;
    }

    public PanelServerInfoPojo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(PanelServerInfoPojo serverInfo) {
        this.serverInfo = serverInfo;
    }

    public PanelCategoriesPojo getCategories() {
        return categories;
    }

    public void setCategories(PanelCategoriesPojo categories) {
        this.categories = categories;
    }

    public Map<String, PanelAvailableChannelsPojo> getAvailableChannels() {
        return  availableChannels;
    }

    public void setAvailableChannels(Map<String, PanelAvailableChannelsPojo> availableChannels) {
        this.availableChannels = (Map<String, PanelAvailableChannelsPojo>) availableChannels;
    }

}
