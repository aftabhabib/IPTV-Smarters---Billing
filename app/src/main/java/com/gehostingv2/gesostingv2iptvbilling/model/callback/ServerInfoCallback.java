package com.gehostingv2.gesostingv2iptvbilling.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerInfoCallback {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("port")
    @Expose
    private String port;
    @SerializedName("rtmp_port")
    @Expose
    private String rtmpPort;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("time_now")
    @Expose
    private String timeNow;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRtmpPort() {
        return rtmpPort;
    }

    public void setRtmpPort(String rtmpPort) {
        this.rtmpPort = rtmpPort;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

}