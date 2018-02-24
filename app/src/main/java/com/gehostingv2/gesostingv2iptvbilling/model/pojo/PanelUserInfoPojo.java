package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PanelUserInfoPojo {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("auth")
    @Expose
    private Integer auth;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("exp_date")
    @Expose
    private Object expDate;
    @SerializedName("is_trial")
    @Expose
    private String isTrial;
    @SerializedName("active_cons")
    @Expose
    private String activeCons;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("max_connections")
    @Expose
    private String maxConnections;
    @SerializedName("allowed_output_formats")
    @Expose
    private List<String> allowedOutputFormats = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getExpDate() {
        return expDate;
    }

    public void setExpDate(Object expDate) {
        this.expDate = expDate;
    }

    public String getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(String isTrial) {
        this.isTrial = isTrial;
    }

    public String getActiveCons() {
        return activeCons;
    }

    public void setActiveCons(String activeCons) {
        this.activeCons = activeCons;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(String maxConnections) {
        this.maxConnections = maxConnections;
    }

    public List<String> getAllowedOutputFormats() {
        return allowedOutputFormats;
    }

    public void setAllowedOutputFormats(List<String> allowedOutputFormats) {
        this.allowedOutputFormats = allowedOutputFormats;
    }

}