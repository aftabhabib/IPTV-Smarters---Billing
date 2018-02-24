package com.gehostingv2.gesostingv2iptvbilling.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class USDPojo {
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("suffix")
    @Expose
    public String suffix;
    @SerializedName("msetupfee")
    @Expose
    public String msetupfee;
    @SerializedName("qsetupfee")
    @Expose
    public String qsetupfee;
    @SerializedName("ssetupfee")
    @Expose
    public String ssetupfee;
    @SerializedName("asetupfee")
    @Expose
    public String asetupfee;
    @SerializedName("bsetupfee")
    @Expose
    public String bsetupfee;
    @SerializedName("tsetupfee")
    @Expose
    public String tsetupfee;
    @SerializedName("monthly")
    @Expose
    public String monthly;
    @SerializedName("quarterly")
    @Expose
    public String quarterly;
    @SerializedName("semiannually")
    @Expose
    public String semiannually;
    @SerializedName("annually")
    @Expose
    public String annually;
    @SerializedName("biennially")
    @Expose
    public String biennially;
    @SerializedName("triennially")
    @Expose
    public String triennially;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMsetupfee() {
        return msetupfee;
    }

    public void setMsetupfee(String msetupfee) {
        this.msetupfee = msetupfee;
    }

    public String getQsetupfee() {
        return qsetupfee;
    }

    public void setQsetupfee(String qsetupfee) {
        this.qsetupfee = qsetupfee;
    }

    public String getSsetupfee() {
        return ssetupfee;
    }

    public void setSsetupfee(String ssetupfee) {
        this.ssetupfee = ssetupfee;
    }

    public String getAsetupfee() {
        return asetupfee;
    }

    public void setAsetupfee(String asetupfee) {
        this.asetupfee = asetupfee;
    }

    public String getBsetupfee() {
        return bsetupfee;
    }

    public void setBsetupfee(String bsetupfee) {
        this.bsetupfee = bsetupfee;
    }

    public String getTsetupfee() {
        return tsetupfee;
    }

    public void setTsetupfee(String tsetupfee) {
        this.tsetupfee = tsetupfee;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public String getQuarterly() {
        return quarterly;
    }

    public void setQuarterly(String quarterly) {
        this.quarterly = quarterly;
    }

    public String getSemiannually() {
        return semiannually;
    }

    public void setSemiannually(String semiannually) {
        this.semiannually = semiannually;
    }

    public String getAnnually() {
        return annually;
    }

    public void setAnnually(String annually) {
        this.annually = annually;
    }

    public String getBiennially() {
        return biennially;
    }

    public void setBiennially(String biennially) {
        this.biennially = biennially;
    }

    public String getTriennially() {
        return triennially;
    }

    public void setTriennially(String triennially) {
        this.triennially = triennially;
    }
}
